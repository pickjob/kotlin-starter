package base.saveable.dao

import org.jetbrains.exposed.sql.Table

/**
 *  @author pickjob@126.com
 *  @date 2024-08-02
 **/
object UserConfigurationEntity: Table("user_configuration") {
    val configKey = text("config_key")
    val configVal = text("config_val")
    val createTime = text("create_time")
    val updateTime = text("update_time")

    override val primaryKey = PrimaryKey(configKey)
}