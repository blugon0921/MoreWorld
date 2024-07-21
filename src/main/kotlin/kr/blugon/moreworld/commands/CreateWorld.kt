package kr.blugon.moreworld.commands

import com.mojang.brigadier.arguments.StringArgumentType
import kr.blugon.kotlinbrigadier.BrigadierNode
import kr.blugon.kotlinbrigadier.getValue
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.plugin.java.JavaPlugin

class CreateWorld(plugin : JavaPlugin, node : BrigadierNode) {

    //mw create [WorldType] [WorldEnvironment] [WorldName(String)]

    init {
        node.then("create") {
            requires {
                listOf(sender.hasPermission("moreworld.create"))
            }

            for(dimension in arrayListOf("default", "nether", "the_end")) {
                then(dimension) {
                    for(worldTypeString in arrayListOf("default", "flat", "large_bioms", "amplified")) {
                        then(worldTypeString) {
                            then("worldName" to StringArgumentType.word()) {
                                executes {
                                    val worldName : String by it
                                    if(Bukkit.getWorld(worldName) != null) {
                                        sender.sendMessage(text("${worldName}은 이미 존재하는 세계입니다").color(NamedTextColor.RED))
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