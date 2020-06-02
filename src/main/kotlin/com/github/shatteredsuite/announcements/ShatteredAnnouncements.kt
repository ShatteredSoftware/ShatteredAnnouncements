package com.github.shatteredsuite.announcements

import com.github.shatteredsuite.announcements.data.*
import com.github.shatteredsuite.core.ShatteredPlugin
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.io.File
import kotlin.random.Random

class ShatteredAnnouncements : ShatteredPlugin() {
    lateinit var config: Config
    var announcementManager: AnnouncementManager = AnnouncementManager()

    init {
        internalConfig = true
    }

    override fun preload() {
        ConfigurationSerialization.registerClass(AnnouncementDTO::class.java)
        ConfigurationSerialization.registerClass(ConfigDTO::class.java)
    }

    override fun parseConfig(config: YamlConfiguration?) {
        this.config = Config(getConfig()["config"] as ConfigDTO)
    }

    override fun load() {
        val announcementsFile = File(dataFolder, "announcements.yml")
        val announcementsConfig = YamlConfiguration.loadConfiguration(announcementsFile)
        val announcementsList = announcementsConfig["announcements"] as List<*>
        for(announcement in announcementsList) {
            if(announcement is AnnouncementDTO) {
                announcementManager.register(Announcement(announcement))
            }
        }
    }

    override fun onFirstTick() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            announce()
        }, config.delay.toLong() * 20L, config.delay.toLong() * 20L)
    }

    fun announce() {
        // No announcements means no announcing.
        if(announcementManager.getAll().isEmpty()) {
            return
        }

        // Random picker. Each has a weight and we go until we surpass that weight.
        var target = Random.nextInt(announcementManager.announcementChanceTotal)
        var current : Announcement = announcementManager.getAll()[0]
        val iterator = announcementManager.getAll().iterator()
        while(current.chance > target && iterator.hasNext()) {
            target -= current.chance
            current = iterator.next()
        }

        // Announce the thing
        for(player in Bukkit.getOnlinePlayers()) {
            when(config.type) {
                AnnouncementType.CHAT -> player.sendMessage(current.content)
                AnnouncementType.ACTIONBAR -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        *TextComponent.fromLegacyText(current.content))
            }
        }
    }
}