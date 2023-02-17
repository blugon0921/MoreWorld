package kr.blugon.moreworld.commands

import io.github.monun.kommand.StringType
import io.github.monun.kommand.getValue
import io.github.monun.kommand.node.RootNode
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class UnloadWorld(plugin : JavaPlugin, rn : RootNode) {

    //mw load [WorldName(String)]
    init {
        rn.then("unload") {
            requires {
                hasPermission(4, "moreworld.unload")
            }

            then("world" to dimension()) {
                executes {
                    val world : World by it

                    for(players in world.players) players.teleport(Bukkit.getWorlds()[0].spawnLocation)

                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        sender.sendMessage("${world.name}세계를 비활성화하였습니다")
                        Bukkit.unloadWorld(world, true)
                    }, 1)
                }
            }
        }
    }
}