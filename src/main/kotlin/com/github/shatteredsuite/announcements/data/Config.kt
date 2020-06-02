@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.github.shatteredsuite.announcements.data

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("AnnouncementConfig")
data class ConfigDTO(public var type: AnnouncementType, public var delay: Integer) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> = ConfigUtil.reflectiveSerialize(this, ConfigDTO::class.java)

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>) : ConfigDTO {
            val type = ConfigUtil.getInEnum(map, "type", AnnouncementType::class.java, AnnouncementType.CHAT)
            val delay = ConfigUtil.getIfValid(map, "delay", Integer::class.java, (300 as Integer))
            return ConfigDTO(type, delay)
        }
    }
}

class Config(public val type: AnnouncementType, public val delay: Int) {
    constructor(dto: ConfigDTO) : this(dto.type, dto.delay as Int)
}


