package com.github.shatteredsuite.announcements.data

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("Announcement")
data class AnnouncementDTO(public var content: String, public var chance: Int) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> = ConfigUtil.reflectiveSerialize(this, AnnouncementDTO::class.java)

    constructor(map: Map<String, Any>) {
        this.content = ConfigUtil.getIfValid(map, "content", String::class.java, "")
        this.chance = ConfigUtil.getIfValid(map, "chance", Int::class.java, 1)
    }
}

open class Announcement(val content: String, chance: Int) {
    constructor(dto: AnnouncementDTO) {
        Announcement(dto.content, dto.chance)
    }
}