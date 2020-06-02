@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.github.shatteredsuite.announcements.data

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.ChatColor
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("Announcement")
data class AnnouncementDTO(var content: String, var chance: Integer) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> = ConfigUtil.reflectiveSerialize(this, AnnouncementDTO::class.java)

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): AnnouncementDTO {
            val content = ChatColor.translateAlternateColorCodes('&',
                    ConfigUtil.getIfValid(map, "content", String::class.java, ""))
            val chance = ConfigUtil.getIfValid(map, "chance", Integer::class.java, 1 as Integer)
            return AnnouncementDTO(content, chance)
        }
    }
}

open class Announcement(val content: String, val chance: Int) {
    constructor(dto: AnnouncementDTO) : this(dto.content, dto.chance as Int)
}

class AnnouncementManager {
    var announcementChanceTotal: Int = 0
        private set
    private val announcements: MutableList<Announcement> = mutableListOf()

    fun register(element: Announcement) {
        announcements.add(element)
        announcementChanceTotal += element.chance
    }

    fun getAll() : List<Announcement> {
        return this.announcements
    }
}