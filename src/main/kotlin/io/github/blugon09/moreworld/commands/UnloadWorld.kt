package io.github.blugon09.moreworld.commands

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.File

class UnloadWorld : CommandExecutor {

    //mw unload [WorldName(String)]

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == "moreworld" || command.aliases.contains("mw")) {
            command(sender, args)
        }
        return false
    }

    fun command(sender : CommandSender, args : Array<out String>) {
        if(args[0] == "unload") {
            if(!sender.hasPermission("moreworld.unload")) {
                sender.sendMessage("${ChatColor.RED}권한이 부족합니다")
                return
            }

            if(args.size != 2) {
                sender.sendMessage("${ChatColor.RED}moreworld unload [WorldName]")
                return
            }
            if(Bukkit.getWorld(args[1]) == null) {
                sender.sendMessage("${ChatColor.RED}알 수 없는 세계 입니다")
                return
            }

            val world = Bukkit.getWorld(args[1])!!

            for(players in world.players) {
                players.teleport(Bukkit.getWorlds()[0].spawnLocation.add(.5, .0, .5))
            }

            Bukkit.unloadWorld(world, true)
            sender.sendMessage("${world.name}세계를 비활성화하였습니다")
        }
    }
}