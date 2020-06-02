package com.github.shatteredsuite.announcements.data

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("AnnouncementConfig")
data class ConfigDTO(public var type: AnnouncementType) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> = ConfigUtil.reflectiveSerialize(this, ConfigDTO::class.java)

    constructor(map: Map<String, Any>) {
        this.type = ConfigUtil.getInEnum(map, "type", AnnouncementType::class.java, AnnouncementType.CHAT)
    }
}

class Config(public val type: AnnouncementType) {
    constructor(dto: ConfigDTO) {
        Config(dto.type)
    }
}


