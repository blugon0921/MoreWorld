package kr.blugon.moreworld.commands

import kr.blugon.kotlinbrigadier.registerCommandHandler
import org.bukkit.plugin.java.JavaPlugin

class MoreWorldCommand(plugin : JavaPlugin) {

    init {
        plugin.lifecycleManager.registerCommandHandler {
            register("moreworld", "", "mw") {
                requires {
                    listOf(sender.hasPermission("moreworld.command"))
                }
                CreateWorld(plugin, this)
                RemoveWorld(plugin, this)
                MoveWorld(plugin, this)
                LoadWorld(plugin, this)
                UnloadWorld(plugin, this)
                WorldList(plugin, this)
            }
        }
    }
}