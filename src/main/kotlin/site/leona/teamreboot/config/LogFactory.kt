package site.leona.teamreboot.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface LogFactory {
    val log: Logger
        get() = LoggerFactory.getLogger(this::class.java)
}