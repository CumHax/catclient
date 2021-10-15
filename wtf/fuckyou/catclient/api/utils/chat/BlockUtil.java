/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.main.GameConfiguration
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package wtf.fuckyou.catclient.api.utils.chat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import wtf.fuckyou.catclient.api.utils.chat.MathsUtil;

public final class BlockUtil
extends Minecraft {
    public BlockUtil(GameConfiguration p_i45547_1_) {
        super(p_i45547_1_);
    }

    public static List<BlockPos> getNearbyBlocks(EntityPlayer entityPlayer, double blockRange, boolean motion) {
        ArrayList<BlockPos> nearbyBlocks = new ArrayList<BlockPos>();
        int range = (int)MathsUtil.roundNumber(blockRange, 0);
        if (motion) {
            entityPlayer.getPosition().add(new Vec3i(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ));
        }
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    nearbyBlocks.add(entityPlayer.getPosition().add(x, y, z));
                }
            }
        }
        return nearbyBlocks;
    }
}

