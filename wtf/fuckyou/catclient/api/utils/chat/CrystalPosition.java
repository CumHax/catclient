/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package wtf.fuckyou.catclient.api.utils.chat;

import net.minecraft.util.math.BlockPos;

public class CrystalPosition {
    private BlockPos position;
    private double targetDamage;
    private double selfDamage;

    public CrystalPosition(BlockPos position, double targetDamage, double selfDamage) {
        this.position = position;
        this.selfDamage = selfDamage;
        this.targetDamage = targetDamage;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public double getTargetDamage() {
        return this.targetDamage;
    }

    public double getSelfDamage() {
        return this.selfDamage;
    }
}

