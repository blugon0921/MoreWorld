package kr.blugon.moreworld.commands

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.node.RootNode

class WorldList(plugin : JavaPlugin, rn : RootNode) {

    //mw list

    init {
        rn.then("list") {
            requires {
                hasPermission(4, "moreworld.list")
            }

            executes {
                val worldsName = ArrayList<String>()
                Bukkit.getWorlds().forEach {world ->
                    worldsName.add(world.name)
                }
                sender.sendMessage("현재 총 ${Bukkit.getWorlds().size}개의 세계가 있습니다\n${worldsName}")
            }
        }
    }
}