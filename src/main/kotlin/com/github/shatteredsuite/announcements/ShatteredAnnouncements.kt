package com.github.shatteredsuite.announcements

import com.github.shatteredsuite.announcements.data.AnnouncementDTO
import com.github.shatteredsuite.announcements.data.ConfigDTO
import com.github.shatteredsuite.core.ShatteredPlugin
import org.bukkit.configuration.serialization.ConfigurationSerialization

class ShatteredAnnouncements : ShatteredPlugin() {
    init {
        internalConfig = true

    }

    override fun preload() {
        ConfigurationSerialization.registerClass(AnnouncementDTO::class.java)
        ConfigurationSerialization.registerClass(ConfigDTO::class.java)
    }
}