package kr.blugon.moreworld.commands

import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand

class MoreWorldCommand(plugin : JavaPlugin) {

    init {
        plugin.kommand {
            register("moreworld", "mw") {
                requires {
                    hasPermission(4, "moreworld.command")
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