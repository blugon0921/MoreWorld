package kr.blugon.moreworld.commands

import io.github.monun.kommand.getValue
import io.github.monun.kommand.node.RootNode
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin

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
                                sender.sendMessage("${ChatColor.RED}${worldName}세계 삭제에 실패하였습니다")
                            }
                        }
                    }, 1)
                }
            }
        }
    }
}