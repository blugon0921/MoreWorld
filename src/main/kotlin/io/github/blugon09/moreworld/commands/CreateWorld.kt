package io.github.blugon09.moreworld.commands

import io.github.blugon09.pluginhelper.component.component
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CreateWorld : CommandExecutor {

    //mw create [WorldType] [WorldEnvironment] [WorldName(String)]

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == "moreworld" || command.aliases.contains("mw")) {
            command(sender, args)
        }
        return false
    }

    fun command(sender : CommandSender, args : Array<out String>) {
        if(args[0] == "create") {
            if(!sender.hasPermission("moreworld.create")) {
                sender.sendMessage("${ChatColor.RED}권한이 부족합니다")
                return
            }

            if(args.size != 4) {
                sender.sendMessage("${ChatColor.RED}moreworld create [WorldType] [WorldEnvironment] [WorldName]")
                return
            }
            if(Bukkit.getWorld(args[2]) != null) {
                sender.sendMessage("${ChatColor.RED}이미 존재하는 세계입니다")
                return
            }

            val worldEnvironment : World.Environment
            when(args[2]) {
                "default" -> worldEnvironment = World.Environment.NORMAL
                "nether" -> worldEnvironment = World.Environment.NETHER
                "the_end" -> worldEnvironment = World.Environment.THE_END
                else -> {
                    sender.sendMessage("WorldEnvironment : default, nether, the_end".component().color(
                        NamedTextColor.RED))
                    return
                }
            }
            val worldType : WorldType
            when(args[1]) {
                "default" -> worldType = WorldType.NORMAL
                "flat" -> worldType = WorldType.FLAT
                "large_bioms" -> worldType = WorldType.LARGE_BIOMES
                "amplified" -> worldType = WorldType.AMPLIFIED
                else -> {
                    sender.sendMessage("WorldType : default, flat, large_bioms, amplified".component().color(
                        NamedTextColor.RED))
                    return
                }
            }
            val worldName = args[3]

            //CreateWorld
            sender.sendMessage("${worldName}세계 생성중...")

            val worldCreator = WorldCreator(worldName)
            worldCreator.environment(worldEnvironment)
            worldCreator.type(worldType)
            Bukkit.createWorld(worldCreator)

            sender.sendMessage("${worldName}세계를 생성하였습니다")
        }
    }
}