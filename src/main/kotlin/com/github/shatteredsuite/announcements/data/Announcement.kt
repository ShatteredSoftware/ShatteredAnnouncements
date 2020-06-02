package com.github.shatteredsuite.announcements.data

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.ChatColor
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("Announcement")
data class AnnouncementDTO(var content: String, var chance: Int) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> = ConfigUtil.reflectiveSerialize(this, AnnouncementDTO::class.java)

    constructor(map: Map<String, Any>) {
        this.content = ChatColor.translateAlternateColorCodes('&',
            ConfigUtil.getIfValid(map, "content", String::class.java, ""))
        this.chance = ConfigUtil.getIfValid(map, "chance", Int::class.java, 1)
    }
}

open class Announcement(val content: String, val chance: Int) {
    constructor(dto: AnnouncementDTO) {
        Announcement(dto.content, dto.chance)
    }
}

class AnnouncementManager {
    var announcementChanceTotal: Int = 0
        private set
    private val announcements: MutableList<Announcement> = mutableListOf()

    fun register(element: Announcement) {
        fun register(element: Announcement) {
            announcements.add(element)
            announcementChanceTotal += element.chance
        }
    }

    fun getAll() : List<Announcement> {
        return this.announcements
    }
}