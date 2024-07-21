package kr.blugon.moreworld.commands

import com.mojang.brigadier.arguments.StringArgumentType.string
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.world
import kr.blugon.kotlinbrigadier.BrigadierNode
import kr.blugon.kotlinbrigadier.getValue
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin

class UnloadWorld(plugin : JavaPlugin, node : BrigadierNode) {

    //mw load [WorldName(String)]
    init {
        node.then("unload") {
            requires {
                listOf(sender.hasPermission("moreworld.unload"))
            }

            then("worldName" to string()) {
                suggests(Bukkit.getWorlds().map { it.name })
                executes {
                    val worldName : String by it
                    val world = Bukkit.getWorld(worldName)
                    if(world == null) {
                        sender.sendMessage("${worldName}은(는) 존재하지 않는 월드입니다")
                        return@executes
                    }

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