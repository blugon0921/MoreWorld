package kr.blugon.moreworld.commands

import io.github.monun.kommand.StringType
import io.github.monun.kommand.getValue
import io.github.monun.kommand.node.RootNode
import kr.blugon.pluginhelper.component.component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class CreateWorld(plugin : JavaPlugin, rn : RootNode) {

    //mw create [WorldType] [WorldEnvironment] [WorldName(String)]

    init {
        rn.then("create") {
            requires {
                hasPermission(4, "moreworld.create")
            }

            for(dimension in arrayListOf("default", "nether", "the_end")) {
                then(dimension) {
                    for(worldTypeString in arrayListOf("default", "flat", "large_bioms", "amplified")) {
                        then(worldTypeString) {
                            then("worldName" to string(StringType.SINGLE_WORD)) {
                                executes {
                                    val worldName : String by it
                                    if(Bukkit.getWorld(worldName) != null) {
                                        sender.sendMessage("${ChatColor.RED}${worldName}은 이미 존재하는 세계입니다")
                                        return@executes
                                    }
                                    val worldEnvironment = when(dimension) {
                                        "default" -> World.Environment.NORMAL
                                        "nether" -> World.Environment.NETHER
                                        "the_end" -> World.Environment.THE_END
                                        else -> World.Environment.NORMAL
                                    }
                                    val worldType = when(worldTypeString) {
                                        "default" -> WorldType.NORMAL
                                        "flat" -> WorldType.FLAT
                                        "large_bioms" -> WorldType.LARGE_BIOMES
                                        "amplified" -> WorldType.AMPLIFIED
                                        else -> WorldType.NORMAL
                                    }

                                    sender.sendMessage("${worldName}세계 생성중...")

                                    val worldCreator = WorldCreator(worldName)
                                    worldCreator.environment(worldEnvironment)
                                    worldCreator.type(worldType)
                                    Bukkit.createWorld(worldCreator)

                                    sender.sendMessage("${worldName}세계를 생성하였습니다")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}