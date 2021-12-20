package io.github.blugon09.moreworld.commands

import io.github.blugon09.pluginhelper.component.component
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class TeleportWorld : CommandExecutor {

    //mw teleport(tp) [Player] [World]

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == "moreworld" || command.aliases.contains("mw")) {
            command(sender, args)
        }
        return false
    }

    fun command(sender : CommandSender, args : Array<out String>) {
        if(args[0] == "teleport" || args[0] == "tp") {
            if(!sender.hasPermission("moreworld.teleport")) {
                sender.sendMessage("${ChatColor.RED}권한이 부족합니다")
                return
            }

            if(Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage("알 수 없는 플레이어 입니다".component().color(NamedTextColor.RED))
                return
            }
            if(Bukkit.getWorld(args[2]) == null) {
                sender.sendMessage("알 수 없는 세계 입니다".component().color(NamedTextColor.RED))
                return
            }
            val player = Bukkit.getPlayer(args[1])!!
            val world = Bukkit.getWorld(args[2])!!
            player.teleport(world.spawnLocation.add(.5, .0, .5))
            sender.sendMessage("${player.name}님을 ${world.name}월드로 순간이동 시켰습니다")
        }
    }
}