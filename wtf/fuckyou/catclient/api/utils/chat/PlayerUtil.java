/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.main.GameConfiguration
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package wtf.fuckyou.catclient.api.utils.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil
extends Minecraft {
    public PlayerUtil(GameConfiguration p_i45547_1_) {
        super(p_i45547_1_);
    }

    public static boolean isInViewFrustrum(BlockPos blockPos) {
        Minecraft mc = PlayerUtil.getMinecraft();
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), false, true, false) == null;
    }

    public static AxisAlignedBB blockIntersectsPlayer(BlockPos blockPos) {
        Minecraft mc = PlayerUtil.getMinecraft();
        return new AxisAlignedBB(blockPos).intersect(mc.player.getEntityBoundingBox());
    }

    public static Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}

