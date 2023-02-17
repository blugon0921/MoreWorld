package kr.blugon.moreworld.commands

import io.github.monun.kommand.StringType
import io.github.monun.kommand.getValue
import io.github.monun.kommand.node.RootNode
import org.bukkit.ChatColor
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
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
                        sender.sendMessage("${ChatColor.RED}${worldName}세계가 존재하지 않습니다")
                        return@executes
                    }

                    val world = WorldCreator(worldName).createWorld()

                    if(world == null) {
                        sender.sendMessage("${ChatColor.RED}${worldName}세계가 존재하지 않습니다")
                        return@executes
                    }

                    sender.sendMessage("${world.name}세계를 불러왔습니다")
                }
            }
        }
    }
}