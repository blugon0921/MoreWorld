package io.github.blugon09.moreworld.commands

import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission

class Comand : CommandExecutor {



    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == "moreworld" || command.aliases.contains("mw")) {
            if(!sender.hasPermission("moreworld.command")) {
                sender.sendMessage("${ChatColor.RED}권한이 부족합니다")
                return false
            }

            CreateWorld().command(sender, args)
            RemoveWorld().command(sender, args)
            TeleportWorld().command(sender, args)
            LoadWorld().command(sender, args)
            UnloadWorld().command(sender, args)
        }
        return false
    }
}