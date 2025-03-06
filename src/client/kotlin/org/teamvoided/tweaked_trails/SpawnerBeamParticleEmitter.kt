package org.teamvoided.tweaked_trails

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.particle.*
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d

class SpawnerBeamParticleEmitter(
    world: ClientWorld,
    x: Double,
    y: Double,
    z: Double,
    velocityX: Double,
    velocityY: Double,
    velocityZ: Double,
    private val targetPos: Vec3d,
    isOminous: Boolean,
) : NoRenderParticle(world, x, y, z, velocityX, velocityY, velocityZ) {
    private val startPos: Vec3d = Vec3d(x, y, z)
    private val particle: ParticleEffect

    init {
        val distance = (startPos.distanceTo(targetPos) * 2)
        maxAge = (5 * distance).toInt()
        particle = if (isOminous) ParticleTypes.SOUL_FIRE_FLAME else ParticleTypes.FLAME
    }

    override fun tick() {
        if (age++ >= maxAge) {
            world.addParticle(
                particle,
                targetPos.x, targetPos.y, targetPos.z,
                0.0, 0.0, 0.0
            )
            markDead()
        } else if (age % 5 == 0) {

            val lerp = age.toDouble() / maxAge
            prevPosX = x
            prevPosY = y
            prevPosZ = z
            x = MathHelper.lerp(lerp, startPos.x, targetPos.getX())
            y = MathHelper.lerp(lerp, startPos.y, targetPos.getY())
            z = MathHelper.lerp(lerp, startPos.z, targetPos.getZ())

            world.addParticle(
                particle,
                x + (random.nextDouble() - 0.5),
                y + (random.nextDouble() - 0.5),
                z + (random.nextDouble() - 0.5),
                0.0, 0.0, 0.0
            )
        }
    }

    @Environment(EnvType.CLIENT)
    class Factory : ParticleFactory<SpawnerBeamEmitterParticleEffect> {
        override fun createParticle(
            particleEffect: SpawnerBeamEmitterParticleEffect,
            world: ClientWorld,
            x: Double, y: Double, z: Double,
            velocityX: Double, velocityY: Double, velocityZ: Double
        ): Particle {
            return SpawnerBeamParticleEmitter(
                world,
                x, y, z,
                velocityX, velocityY, velocityZ,
                particleEffect.pos,
                particleEffect.ominous
            )
        }
    }
}