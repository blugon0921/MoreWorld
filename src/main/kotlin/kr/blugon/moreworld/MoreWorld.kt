package kr.blugon.moreworld

import kr.blugon.moreworld.commands.MoreWorldCommand
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class MoreWorld : JavaPlugin() {

    override fun onEnable() {
        logger.info("Plugin Enable")

        MoreWorldCommand(this)
    }

    override fun onDisable() {
        logger.info("Plugin Disable")
    }
}