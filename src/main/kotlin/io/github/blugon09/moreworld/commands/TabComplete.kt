package io.github.blugon09.moreworld.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class TabComplete : TabCompleter {


    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if(command.name == "moreworld" || command.aliases.contains("mw")) {
            val hasCreatePermission = sender.hasPermission("moreworld.create")
            val hasRemovePermission = sender.hasPermission("moreworld.remove")
            val hasTeleportPermission = sender.hasPermission("moreworld.teleport")
            val hasLoadPermission = sender.hasPermission("moreworld.load")
            val hasUnloadPermission = sender.hasPermission("moreworld.unload")

            val returns = mutableListOf<String>()

            when(args.size) {
                1 -> {
                    for(r in arrayListOf("create", "remove", "teleport", "tp", "load", "unload")) {
                        if(r.startsWith(args[0].lowercase())) {
                            if(r == "create" && !hasCreatePermission) continue
                            if(r == "remove" && !hasRemovePermission) continue
                            if(r == "teleport" || r == "tp") if(!hasTeleportPermission) continue
                            if(r == "load" && !hasLoadPermission) continue
                            if(r == "unload" && !hasUnloadPermission) continue
                            returns.add(r)
                        }
                    }
                    return returns
                }
                2 -> {
                    when(args[0]) {
                        "create" -> {
                            for(r in arrayListOf("default", "flat", "large_bioms", "amplified")) {
                                if(r.startsWith(args[1].lowercase())) {
                                    if(!hasCreatePermission) continue
                                    returns.add(r.lowercase())
                                }
                            }
                            return returns
                        }
                        "remove" -> {
                            for(r in Bukkit.getWorlds()) {
                                if(r.name.startsWith(args[1].lowercase())) {
                                    if(!hasRemovePermission) continue
                                    returns.add(r.name)
                                }
                            }
                            return returns
                        }
                        "unload" -> {
                            for(r in Bukkit.getWorlds()) {
                                if(r.name.startsWith(args[1].lowercase())) {
                                    if(!hasUnloadPermission) continue
                                    returns.add(r.name)
                                }
                            }
                            return returns
                        }
                        "teleport", "tp" -> {
                            for(r in Bukkit.getOnlinePlayers()) {
                                if(r.name.lowercase().startsWith(args[1].lowercase())) {
                                    if(!hasTeleportPermission) continue
                                    returns.add(r.name)
                                }
                            }
                            return returns
                        }
                    }
                }
                3 -> {
                    when(args[0]) {
                        "create" -> {
                            for(r in arrayListOf("default", "nether", "the_end")) {
                                if(r.startsWith(args[2].lowercase())) {
                                    if(!hasCreatePermission) continue
                                    returns.add(r.lowercase())
                                }
                            }
                            return returns
                        }
                        "teleport", "tp" -> {
                            for(r in Bukkit.getWorlds()) {
                                if(r.name.lowercase().startsWith(args[2].lowercase())) {
                                    if(!hasTeleportPermission) continue
                                    returns.add(r.name)
                                }
                            }
                            return returns
                        }
                    }
                }
            }
        }
        return Collections.emptyList()
    }
}