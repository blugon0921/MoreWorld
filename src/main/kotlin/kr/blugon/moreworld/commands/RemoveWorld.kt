package kr.blugon.moreworld.commands

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.getValue
import xyz.icetang.lib.kommand.node.RootNode

class RemoveWorld(plugin : JavaPlugin, rn : RootNode) {

    //mw remove [WorldName(String)]

    init {
        rn.then("remove") {
            requires {
                hasPermission(4, "moreworld.remove")
            }

            then("world" to dimension()) {
                executes {
                    val world : World by it
                    val worldFolder = world.worldFolder

                    for(players in world.players) {
                        players.teleport(Bukkit.getWorlds()[0].spawnLocation.add(.5, .0, .5))
                    }

                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        val worldName = world.name
                        Bukkit.unloadWorld(world, false)
                        if (worldFolder.exists()) {
                            if (worldFolder.deleteRecursively()) {
                                sender.sendMessage("${worldName}세계를 삭제하였습니다")
                            } else {
                                WorldCreator(worldName).createWorld()
                                sender.sendMessage(text("${worldName}세계 삭제에 실패하였습니다").color(NamedTextColor.RED))
                            }
                        }
                    }, 1)
                }
            }
        }
    }
}