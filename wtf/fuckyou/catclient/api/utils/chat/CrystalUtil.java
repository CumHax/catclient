/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.main.GameConfiguration
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.CombatRules
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package wtf.fuckyou.catclient.api.utils.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CrystalUtil
extends Minecraft {
    public CrystalUtil(GameConfiguration p_i45547_1_) {
        super(p_i45547_1_);
    }

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        try {
            double factor = (1.0 - entity.getDistance(posX, posY, posZ) / 12.0) * (double)entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());
            float calculatedDamage = (int)((factor * factor + factor) / 2.0 * 7.0 * 12.0 + 1.0);
            double damage = 1.0;
            if (entity instanceof EntityLivingBase) {
                Minecraft mc = Minecraft.getMinecraft();
                damage = CrystalUtil.getBlastReduction((EntityLivingBase)entity, calculatedDamage * (mc.world.getDifficulty().getId() == 0 ? 0.0f : (mc.world.getDifficulty().getId() == 2 ? 1.0f : (mc.world.getDifficulty().getId() == 1 ? 1.0f : (mc.world.getDifficulty().getId() == 1 ? 0.5f : 1.5f)))), new Explosion((World)mc.world, entity, posX, posY, posZ, 6.0f, false, true));
            }
            return (float)damage;
        }
        catch (Exception exception) {
            return 0.0f;
        }
    }

    public static float getBlastReduction(EntityLivingBase entityLivingBase, float damage, Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)entityLivingBase.getTotalArmorValue(), (float)((float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
            damage *= 1.0f - MathHelper.clamp((float)EnchantmentHelper.getEnchantmentModifierDamage((Iterable)entityLivingBase.getArmorInventoryList(), (DamageSource)DamageSource.causeExplosionDamage((Explosion)explosion)), (float)0.0f, (float)20.0f) / 25.0f;
            if (entityLivingBase.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)entityLivingBase.getTotalArmorValue(), (float)((float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        return damage;
    }

    public static void breakCrystal(EntityEnderCrystal entityEnderCrystal, boolean packet) {
        Minecraft mc = Minecraft.getMinecraft();
        if (packet) {
            mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)entityEnderCrystal));
        } else {
            mc.playerController.attackEntity((EntityPlayer)mc.player, (Entity)entityEnderCrystal);
        }
    }

    public static void placeCrystal(BlockPos blockPos, EnumFacing enumFacing, boolean packet) {
        Minecraft mc = Minecraft.getMinecraft();
        if (packet) {
            mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, mc.player.getHeldItemOffhand().getItem().equals((Object)Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        } else {
            mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos, enumFacing, new Vec3d(0.0, 0.0, 0.0), mc.player.getHeldItemOffhand().getItem().equals((Object)Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
    }

    public static boolean canPlaceCrystal(BlockPos blockPos, boolean multiPlace, boolean thirteen) {
        Minecraft mc = CrystalUtil.getMinecraft();
        try {
            if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (!thirteen && !mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().isReplaceable((IBlockAccess)mc.world, blockPos) || !mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().isReplaceable((IBlockAccess)mc.world, blockPos)) {
                return false;
            }
            for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos.add(0, 1, 0)))) {
                if (entity.isDead || multiPlace && entity instanceof EntityEnderCrystal) continue;
                return false;
            }
            if (!thirteen) {
                for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos.add(0, 2, 0)))) {
                    if (entity.isDead || multiPlace && entity instanceof EntityEnderCrystal) continue;
                    return false;
                }
            }
        }
        catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static EnumFacing getEnumFacing(boolean raytrace, BlockPos blockPos) {
        Minecraft mc = CrystalUtil.getMinecraft();
        RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5));
        if (blockPos.getY() == 255) {
            return EnumFacing.DOWN;
        }
        if (raytrace) {
            return result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
        }
        return EnumFacing.UP;
    }
}

