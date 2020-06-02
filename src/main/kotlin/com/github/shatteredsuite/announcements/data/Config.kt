package com.github.shatteredsuite.announcements.data

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("AnnouncementConfig")
data class ConfigDTO(public var type: AnnouncementType, public var delay: Int) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> = ConfigUtil.reflectiveSerialize(this, ConfigDTO::class.java)

    constructor(map: Map<String, Any>) {
        this.type = ConfigUtil.getInEnum(map, "type", AnnouncementType::class.java, AnnouncementType.CHAT)
        this.delay = ConfigUtil.getIfValid(map, "delay", Int::class.java, 300)
    }
}

class Config(public val type: AnnouncementType, public val delay: Int) {
    constructor(dto: ConfigDTO) {
        Config(dto.type, dto.delay)
    }
}


