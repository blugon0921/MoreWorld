package kr.blugon.moreworld.commands

import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.StringArgumentType.string
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.*
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver
import io.papermc.paper.math.BlockPosition
import kr.blugon.kotlinbrigadier.BrigadierNode
import kr.blugon.kotlinbrigadier.get
import kr.blugon.kotlinbrigadier.getValue
import kr.blugon.kotlinbrigadier.player
import net.minecraft.commands.arguments.DimensionArgument.dimension
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MoveWorld(plugin : JavaPlugin, node : BrigadierNode) {

    //mw move [Player] [World] [x] [y] [z] [yaw] [pitch]
    init {
        node.then("move") {
            requires {
                listOf(sender.hasPermission("moreworld.move"))
            }
            
            then("targets" to entities()) {
                then("worldName" to string()) {
                    suggests(Bukkit.getWorlds().map { it.name })
                    executes {
                        val targets = (it.get<EntitySelectorArgumentResolver>("targets")).resolve(it.source)
                        val worldName : String by it
                        val world = Bukkit.getWorld(worldName)
                        if(world == null) {
                            sender.sendMessage("${worldName}은(는) 존재하지 않는 월드입니다")
                            return@executes
                        }

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
                    then("loc" to blockPosition()) {
                        executes {
                            val targets = (it.get<EntitySelectorArgumentResolver>("targets")).resolve(it.source)
                            val loc: BlockPositionResolver by it
                            val worldName : String by it
                            val world = Bukkit.getWorld(worldName)
                            if(world == null) {
                                sender.sendMessage("${worldName}은(는) 존재하지 않는 월드입니다")
                                return@executes
                            }
                            val location = loc.resolve(it.source).toLocation(world)
                            val x = location.x
                            val y = location.y
                            val z = location.z

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
                        then("yaw" to FloatArgumentType.floatArg()) {
                            then("pitch" to FloatArgumentType.floatArg()) {
                                executes {
                                    val targets = (it.get<EntitySelectorArgumentResolver>("targets")).resolve(it.source)
                                    val loc: BlockPositionResolver by it
                                    val worldName : String by it
                                    val world = Bukkit.getWorld(worldName)
                                    if(world == null) {
                                        sender.sendMessage("${worldName}은(는) 존재하지 않는 월드입니다")
                                        return@executes
                                    }
                                    val location = loc.resolve(it.source).toLocation(world)
                                    val x = location.x
                                    val y = location.y
                                    val z = location.z
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