package io.github.blugon09.moreworld

import io.github.blugon09.moreworld.commands.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class MoreWorld : JavaPlugin(),Listener {

    override fun onEnable() {
        logger.info("Plugin Enable")
        Bukkit.getPluginManager().registerEvents(this, this)

        getCommand("moreworld")!!.setExecutor(Comand())
//        getCommand("moreworld")!!.setExecutor(CreateWorld())
//        getCommand("moreworld")!!.setExecutor(RemoveWorld())
//        getCommand("moreworld")!!.setExecutor(TeleportWorld())
//        getCommand("moreworld")!!.setExecutor(UnloadWorld())
        getCommand("moreworld")!!.tabCompleter = TabComplete()
    }

    override fun onDisable() {
        logger.info("Plugin Disable")
    }
}