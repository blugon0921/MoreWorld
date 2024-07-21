package kr.blugon.moreworld.commands

import com.mojang.brigadier.arguments.StringArgumentType.string
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.world
import kr.blugon.kotlinbrigadier.BrigadierNode
import kr.blugon.kotlinbrigadier.getValue
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin

class RemoveWorld(plugin : JavaPlugin, node : BrigadierNode) {

    //mw remove [WorldName(String)]

    init {
        node.then("remove") {
            requires {
                listOf(sender.hasPermission("moreworld.remove"))
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