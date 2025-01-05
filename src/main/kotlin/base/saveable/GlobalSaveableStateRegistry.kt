package base.saveable

import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry.Entry
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.contains
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import base.saveable.dao.UserConfigurationDao
import org.apache.logging.log4j.kotlin.logger

/**
 *  rememberSaveable 支持
 *
 *  @author pickjob@126.com
 *  @date 2024-07-25
 **/
class GlobalSaveableStateRegistry() : SaveableStateRegistry {
    private val logger = logger()
    private val configKey = "saveable"
    private val metaDelimiter = "-meta";
    private val objectMapper = jacksonObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
    private val restored: MutableMap<String, List<Any?>> = mutableMapOf()
    private val valueProviders = mutableMapOf<String, MutableList<() -> Any?>>()
    private val userConfigurationDao = UserConfigurationDao()

    init {
        val configValue = userConfigurationDao.queryUserConfiguration(configKey)
        if (configValue.isNotEmpty()) {
            val configNode = objectMapper.readTree(configValue) as ObjectNode
            restored.putAll(rebuildMap(configNode) as MutableMap<String, List<Any?>>)
        }
    }

    // 消费保存类型
    override fun consumeRestored(key: String): Any? {
        val list = restored.remove(key)
        return if (!list.isNullOrEmpty()) {
            if (list.size > 1) {
                restored[key] = list.subList(1, list.size)
            }
            list[0]
        } else {
            null
        }
    }

    // 注册保存类型
    override fun registerProvider(key: String, valueProvider: () -> Any?): Entry {
        require(key.isNotBlank()) {
            val errorMessage = "Registered key is empty or blank"
            logger.error(errorMessage)
            errorMessage
        }
        logger.info("Register Provider key: $key ")
        valueProviders.getOrPut(key) { mutableListOf() }.add(valueProvider)
        return object : Entry {
            override fun unregister() {
                val list = valueProviders.remove(key)
                list?.remove(valueProvider)
                if (!list.isNullOrEmpty()) {
                    valueProviders[key] = list
                }
            }
        }
    }

    // 任意类型均返回支持
    override fun canBeSaved(value: Any): Boolean {
        logger.debug("Checking saveable type: ${retrieveType(value)}")
        return true
    }

    // 保存配置
    override fun performSave(): Map<String, List<Any?>> {
        val map = restored.toMutableMap()
        valueProviders.forEach { (key, list) ->
            if (list.size == 1) {
                val value = list[0].invoke()
                if (value != null) {
                    map[key] = arrayListOf<Any?>(value)
                }
            } else {
                map[key] = List(list.size) { index ->
                    val value = list[index].invoke()
                    value
                }
            }
        }
        val configMap = packageMap(map);
        val configValue = objectMapper.writeValueAsString(configMap)
        userConfigurationDao.storeUserConfiguration(configKey, configValue)
        return map
    }

    private fun retrieveType(type: Any?): String {
        return when (type) {
            is Boolean -> SupportedType.Boolean.name
            is Int -> SupportedType.INT.name
            is Float -> SupportedType.FLOAT.name
            is Double -> SupportedType.DOUBLE.name
            is String -> SupportedType.STRING.name
            is List<Any?> -> "${SupportedType.LIST.name}[${type.forEach(::retrieveType)}]"
            is Map<*, *> -> SupportedType.MAP.name
            else -> "${SupportedType.UNKNOW.name}(${type!!::class.qualifiedName})"
        }
    }

    private fun packageMap(value: Map<*, *>): MutableMap<Any, Any?> {
        val result = mutableMapOf<Any, Any?>()
        value.forEach { (k, v) ->
            packageType("$k", v, result)
        }
        return result
    }

    private fun packageList(value: List<*>): MutableMap<Any, Any?> {
        val result = mutableMapOf<Any, Any?>()
        value.forEachIndexed { idx, v ->
            packageType("$idx", v, result);
        }
        return result
    }

    private fun packageType(key: String, value: Any?, result: MutableMap<Any, Any?>) {
        val type = when (value) {
            is Boolean -> {
                result[key] = value
                SupportedType.Boolean.name
            }
            is Int -> {
                result[key] = value
                SupportedType.INT.name
            }
            is Long -> {
                result[key] = value
                SupportedType.INT.name
            }
            is Float -> {
                result[key] = value
                SupportedType.FLOAT.name
            }
            is Double -> {
                result[key] = value
                SupportedType.DOUBLE.name
            }
            is String -> {
                result[key] = value
                SupportedType.STRING.name
            }
            is List<*> -> {
                result[key] = packageList(value)
                SupportedType.LIST.name
            }
            is Map<*, *> -> {
                result[key] = packageMap(value)
                SupportedType.MAP.name
            }
            else -> SupportedType.UNKNOW.name
        }
        result["$key$metaDelimiter"] = type
    }

    fun rebuildMap(valueNode: ObjectNode): MutableMap<Any, Any?> {
        val result = mutableMapOf<Any, Any?>()
        valueNode.fieldNames().forEach { key ->
            if (key.endsWith(metaDelimiter)) {
                val k = key.split(metaDelimiter)[0]
                val t = valueNode.get(key).textValue()
                val v = rebuildType(k, t, valueNode)
                result[k] = v
            }
        }
        return result
    }

    private fun rebuildList(valueNode: ObjectNode): MutableList<Any?> {
        val result = mutableListOf<Any?>()
        var idx = 0
        while (true) {
            if (valueNode.contains("$idx$metaDelimiter")) {
                val k = "$idx"
                val t = valueNode.get("$idx$metaDelimiter").textValue()
                val v = rebuildType(k, t, valueNode)
                result.add(v)
            } else {
                break;
            }
            idx++;
        }
        return result
    }

    private fun rebuildType(key: String, type: String, valueNode: ObjectNode): Any? {
        val v = when (type) {
            SupportedType.Boolean.name -> valueNode.get(key).booleanValue()
            SupportedType.INT.name -> valueNode.get(key).intValue()
            SupportedType.LONG.name -> valueNode.get(key).longValue()
            SupportedType.FLOAT.name -> valueNode.get(key).floatValue()
            SupportedType.DOUBLE.name -> valueNode.get(key).doubleValue()
            SupportedType.STRING.name -> valueNode.get(key).textValue()
            SupportedType.LIST.name -> rebuildList(valueNode.get(key) as ObjectNode)
            SupportedType.MAP.name -> rebuildMap(valueNode.get(key) as ObjectNode)
            SupportedType.NULL.name -> null
            else -> null
        }
        return v
    }

    enum class SupportedType {
        Boolean,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        LIST,
        MAP,
        NULL,
        UNKNOW,
    }
}