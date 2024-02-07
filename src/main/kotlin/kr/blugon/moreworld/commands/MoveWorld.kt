package kr.blugon.moreworld.commands

import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.getValue
import xyz.icetang.lib.kommand.node.RootNode

class MoveWorld(plugin : JavaPlugin, rn : RootNode) {

    //mw move [Player] [World] [x] [y] [z] [yaw] [pitch]
    init {
        rn.then("move") {
            requires {
                hasPermission(4, "moreworld.move")
            }
            
            then("targets" to entities()) {
                then("world" to dimension()) {
                    executes {
                        val targets : Collection<Entity> by it
                        val world : World by it

                        if(targets.size == 1) {
                            targets.forEach { entity ->
                                entity.teleport(world.spawnLocation)
                                if(entity is Player) sender.sendMessage("${entity.name}님을 ${world.name}세계로 순간이동 시켰습니다")
                                else sender.sendMessage("${entity.type.name}을(를) ${world.name}세계로 순간이동 시켰습니다")
                            }
                            return@executes
                        } else {
                            targets.forEach { entity ->
                                entity.teleport(world.spawnLocation)
                            }
                            sender.sendMessage("개체 ${targets.size}개를 ${world.name}세계로 순간이동 시켰습니다")
                        }
                    }
                    then("x" to double()) {
                        then("y" to double()) {
                            then("z" to double()) {
                                executes {
                                    val targets : Collection<Entity> by it
                                    val x : Double by it
                                    val y : Double by it
                                    val z : Double by it

                                    if(targets.size == 1) {
                                        targets.forEach { entity ->
                                            entity.teleport(world.spawnLocation.apply {
                                                this.x = x
                                                this.y = y
                                                this.z = z
                                            })
                                            if(entity is Player) sender.sendMessage("${entity.name}님을 ${world.name}세계 ${x}, ${y}, ${z}로 순간이동 시켰습니다")
                                            else sender.sendMessage("${entity.type.name}을(를) ${world.name}세계 ${x}, ${y}, ${z}로 순간이동 시켰습니다")
                                        }
                                        return@executes
                                    } else {
                                        targets.forEach { entity ->
                                            entity.teleport(world.spawnLocation.apply {
                                                this.x = x
                                                this.y = y
                                                this.z = z
                                            })
                                        }
                                        sender.sendMessage("개체 ${targets.size}개를 ${world.name}세계 ${x}, ${y}, ${z}로 순간이동 시켰습니다")
                                    }
                                }
                                then("yaw" to float()) {
                                    then("pitch" to float()) {
                                        executes {
                                            val targets : Collection<Entity> by it
                                            val x : Double by it
                                            val y : Double by it
                                            val z : Double by it
                                            val yaw : Float by it
                                            val pitch : Float by it

                                            if(targets.size == 1) {
                                                targets.forEach { entity ->
                                                    entity.teleport(world.spawnLocation.apply {
                                                        this.x = x
                                                        this.y = y
                                                        this.z = z
                                                        this.yaw = yaw
                                                        this.pitch = pitch
                                                    })
                                                    if(entity is Player) sender.sendMessage("${entity.name}님을 ${world.name}세계 ${x}, ${y}, ${z}로 순간이동 시켰습니다")
                                                    else sender.sendMessage("${entity.type.name}을(를) ${world.name}세계 ${x}, ${y}, ${z}로 순간이동 시켰습니다")
                                                }
                                                return@executes
                                            } else {
                                                targets.forEach { entity ->
                                                    entity.teleport(world.spawnLocation.apply {
                                                        this.x = x
                                                        this.y = y
                                                        this.z = z
                                                        this.yaw = yaw
                                                        this.pitch = pitch
                                                    })
                                                }
                                                sender.sendMessage("개체 ${targets.size}개를 ${world.name}세계 ${x}, ${y}, ${z}로 순간이동 시켰습니다")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}