package base.logger

import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

/**
 *  Exposed 日志
 *
 *  @author pickjob@126.com
 *  @date 2024-07-25
 **/
class Log4j2SqlLogger(private val module: String) : SqlLogger{
    private val logger = logger()

    override fun log(context: StatementContext, transaction: Transaction) {
        logger.info("$module sql: ${context.expandArgs(transaction)}")
    }
}