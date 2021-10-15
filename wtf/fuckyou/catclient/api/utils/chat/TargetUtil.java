/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.main.GameConfiguration
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 */
package wtf.fuckyou.catclient.api.utils.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TargetUtil
extends Minecraft {
    private static EntityPlayer entityPlayer;

    public TargetUtil(GameConfiguration p_i45547_1_) {
        super(p_i45547_1_);
    }

    public static EntityPlayer getClosestPlayer(double range) {
        Minecraft mc = TargetUtil.getMinecraft();
        if (mc.world.getLoadedEntityList().size() == 0) {
            return null;
        }
        return mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.player).filter(entityPlayer -> !entityPlayer.isDead).filter(entityPlayer -> (double)mc.player.getDistance((Entity)entityPlayer) <= range).findFirst().orElse(null);
    }

    public static EntityPlayer getClosestPlayer(Object value) {
        return entityPlayer;
    }
}

