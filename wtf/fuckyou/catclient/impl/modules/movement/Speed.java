/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.fuckyou.catclient.impl.modules.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.fuckyou.catclient.api.event.events.EventMove;
import wtf.fuckyou.catclient.api.event.events.EventPacket;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;

public class Speed
extends Module {
    public Setting<String> mode = this.register("Mode", "StrictStrafe", "Strafe", "StrictStrafe", "YPort");
    public Setting<Double> speed = this.register("Speed", 2.6, 1.0, 10.0, 1).withDesc("It's being ignored if mode is StrictStrafe!");
    public Setting<Boolean> liquids = this.register("Liquids", true);
    private double moveSpeed = 0.0;
    private double lastDist = 0.0;
    private int stage = 4;

    public Speed() {
        super("Speed", "Move faster!", Module.Category.Movement);
    }

    @Override
    public void onUpdate() {
        if (!this.liquids.getValue().booleanValue() && (Speed.mc.player.isInLava() || Speed.mc.player.isInWater())) {
            return;
        }
        this.lastDist = Math.sqrt(Math.pow(Speed.mc.player.posX - Speed.mc.player.prevPosX, 2.0) + Math.pow(Speed.mc.player.posZ - Speed.mc.player.prevPosZ, 2.0));
    }

    @Subscribe
    public void onMove(EventMove event) {
        int amplifier;
        if (this.mode.getValue().equalsIgnoreCase("YPort")) {
            Speed.mc.player.motionY = -0.4;
        }
        if ((Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f) && Speed.mc.player.onGround) {
            this.stage = 2;
        }
        if (this.stage == 1) {
            this.moveSpeed = this.mode.getValue().equalsIgnoreCase("StrictStrafe") ? 0.33119999999999994 : 1.38 * (this.speed.getValue() / 10.0);
            if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = Speed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
            ++this.stage;
        } else if (this.stage == 2) {
            if (Speed.mc.player.movementInput.moveStrafe != 0.0f || Speed.mc.player.movementInput.moveForward != 0.0f) {
                Speed.mc.player.motionY = 0.3995f;
                event.motionY = 0.3995f;
            }
            this.moveSpeed *= 2.149;
            if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = Speed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
            ++this.stage;
        } else if (this.stage == 3) {
            this.moveSpeed = this.mode.getValue().equalsIgnoreCase("StrictStrafe") ? this.lastDist - 0.66 * (this.lastDist - 0.24) : this.lastDist - 0.66 * (this.lastDist - this.speed.getValue() / 10.0);
            if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = Speed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
            ++this.stage;
        } else {
            if (Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0)).size() > 0 || Speed.mc.player.collidedVertically) {
                this.stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = this.mode.getValue().equalsIgnoreCase("StrictStrafe") ? Math.min(Math.max(this.moveSpeed, 0.24), 0.551) : Math.min(Math.max(this.moveSpeed, this.speed.getValue() / 10.0), 0.551);
        float forward = Speed.mc.player.movementInput.moveForward;
        float strafe = Speed.mc.player.movementInput.moveStrafe;
        float yaw = Speed.mc.player.rotationYaw;
        if ((double)Speed.mc.player.moveForward == 0.0 && (double)Speed.mc.player.moveStrafing == 0.0) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        event.motionX = (double)forward * this.moveSpeed * cos + (double)strafe * this.moveSpeed * sin;
        event.motionZ = (double)forward * this.moveSpeed * sin - (double)strafe * this.moveSpeed * cos;
        if ((double)Speed.mc.player.moveForward == 0.0 && (double)Speed.mc.player.moveStrafing == 0.0) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
        }
    }

    @SubscribeEvent
    public void onPacketReceive(EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.moveSpeed = 0.0;
            this.lastDist = 0.0;
            this.stage = 4;
        }
    }
}

