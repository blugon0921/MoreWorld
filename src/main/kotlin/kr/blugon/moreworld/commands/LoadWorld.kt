package kr.blugon.moreworld.commands

import com.mojang.brigadier.arguments.StringArgumentType
import kr.blugon.kotlinbrigadier.BrigadierNode
import kr.blugon.kotlinbrigadier.getValue
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class LoadWorld(plugin : JavaPlugin, node : BrigadierNode) {

    //mw load [WorldName(String)]
    init {
        node.then("load") {
            requires {
                listOf(sender.hasPermission("moreworld.load"))
            }

            then("worldName" to StringArgumentType.word()) {
                executes {
                    val worldName : String by it
                    if(!File(worldName).exists()) {
                        sender.sendMessage(text("세계가 존재하지 않습니다").color(NamedTextColor.RED))
                        return@executes
                    }

                    val world = WorldCreator(worldName).createWorld()

                    if(world == null) {
                        sender.sendMessage(text("세계가 존재하지 않습니다").color(NamedTextColor.RED))
                        return@executes
                    }

                    sender.sendMessage("${world.name}세계를 불러왔습니다")
                }
            }
        }
    }
}