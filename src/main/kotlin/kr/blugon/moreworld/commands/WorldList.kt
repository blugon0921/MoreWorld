package kr.blugon.moreworld.commands

import kr.blugon.kotlinbrigadier.BrigadierNode
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class WorldList(plugin : JavaPlugin, node : BrigadierNode) {

    //mw list

    init {
        node.then("list") {
            requires {
                listOf(sender.hasPermission("moreworld.list"))
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