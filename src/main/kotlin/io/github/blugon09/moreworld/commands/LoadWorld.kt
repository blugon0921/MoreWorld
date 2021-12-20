package io.github.blugon09.moreworld.commands

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.File

class LoadWorld : CommandExecutor {

    //mw load [WorldName(String)]

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == "moreworld" || command.aliases.contains("mw")) {
            command(sender, args)
        }
        return false
    }

    fun command(sender : CommandSender, args : Array<out String>) {
        if(args[0] == "load") {
            if(!sender.hasPermission("moreworld.load")) {
                sender.sendMessage("${ChatColor.RED}권한이 부족합니다")
                return
            }

            if(args.size != 2) {
                sender.sendMessage("${ChatColor.RED}moreworld load [WorldName]")
                return
            }
            if(!File(args[1]).exists()) {
                sender.sendMessage("${ChatColor.RED}알 수 없는 세계 입니다")
                return
            }

            val world = WorldCreator(args[1]).createWorld()!!

            sender.sendMessage("${world.name}세계를 불러왔습니다")
        }
    }
}