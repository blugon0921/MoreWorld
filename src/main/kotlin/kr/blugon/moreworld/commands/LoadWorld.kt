package kr.blugon.moreworld.commands

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.StringType
import xyz.icetang.lib.kommand.getValue
import xyz.icetang.lib.kommand.node.RootNode
import java.io.File

class LoadWorld(plugin : JavaPlugin, rn : RootNode) {

    //mw load [WorldName(String)]
    init {
        rn.then("load") {
            requires {
                hasPermission(4, "moreworld.load")
            }

            then("worldName" to string(StringType.SINGLE_WORD)) {
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