package org.teamvoided.tweaked_trials.particle

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.util.math.Vec3d
import org.teamvoided.tweaked_trials.TTParticles

class SpawnerBeamEmitterParticleEffect(
    val pos: Vec3d,
    val ominous: Boolean,
) : ParticleEffect {

    override fun getType(): ParticleType<SpawnerBeamEmitterParticleEffect> = TTParticles.SPAWNER_BEAM_EMITTER

    companion object {
        val CODEC: MapCodec<SpawnerBeamEmitterParticleEffect> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Vec3d.CODEC.fieldOf("pos").forGetter { it.pos },
                    Codec.BOOL.fieldOf("particle").forGetter { it.ominous },
                ).apply(instance, ::SpawnerBeamEmitterParticleEffect)
            }

        val PACKET_CODEC: PacketCodec<RegistryByteBuf, SpawnerBeamEmitterParticleEffect> =
            PacketCodec.tuple(
                PacketCodecs.fromCodec(Vec3d.CODEC), { it.pos },
                PacketCodecs.BOOL, { it.ominous },
                ::SpawnerBeamEmitterParticleEffect
            )
    }
}