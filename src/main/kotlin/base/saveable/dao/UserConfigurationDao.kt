package base.saveable.dao

import base.logger.Log4j2SqlLogger
import kotlinx.datetime.Clock
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

/**
 *  @author pickjob@126.com
 *  @date 2024-08-02
 **/
class UserConfigurationDao {
    private val logger = logger()
    private val formatter = DateTimeComponents.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    fun storeUserConfiguration(key: String, value: String) {
        Database.connect("jdbc:sqlite:config.db", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction {
            addLogger(Log4j2SqlLogger("UserConfigurationEntity"))
            SchemaUtils.create(UserConfigurationEntity)
            val now = Clock.System.now()
            val record = UserConfigurationEntity.select(UserConfigurationEntity.configKey)
                .where(UserConfigurationEntity.configKey eq key)
            if (!record.empty()) {
                UserConfigurationEntity.update({ UserConfigurationEntity.configKey eq key}) {
                    it[configVal] = value
                    it[updateTime] = now.format(formatter)
                }
            } else {
                UserConfigurationEntity.insert {
                    it[configKey] = key
                    it[configVal] = value
                    it[createTime] = now.format(formatter)
                    it[updateTime] = now.format(formatter)
                }
            }
        }
    }

    fun queryUserConfiguration(key: String): String {
        var result = ""
        Database.connect("jdbc:sqlite:config.db", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction {
            addLogger(Log4j2SqlLogger("UserConfigurationEntity"))
            SchemaUtils.create(UserConfigurationEntity)
            UserConfigurationEntity.select(UserConfigurationEntity.configVal)
                .where(UserConfigurationEntity.configKey eq key)
                .forEach {
                    result = it[UserConfigurationEntity.configVal]
                }
        }
        return result
    }
}