/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package wtf.fuckyou.catclient.impl.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.fuckyou.catclient.api.event.events.EventPacket;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;
import wtf.fuckyou.catclient.api.utils.chat.BlockUtil;
import wtf.fuckyou.catclient.api.utils.chat.CooldownUtil;
import wtf.fuckyou.catclient.api.utils.chat.CrystalPosition;
import wtf.fuckyou.catclient.api.utils.chat.CrystalUtil;
import wtf.fuckyou.catclient.api.utils.chat.InventoryUtil;
import wtf.fuckyou.catclient.api.utils.chat.PlayerUtil;
import wtf.fuckyou.catclient.api.utils.chat.TargetUtil;

public final class CrystalAura
extends Module {
    private Setting<Boolean> BreakPlace;
    private Setting<Boolean> PlaceBreak;
    private Setting<Boolean> crystalBreak;
    private Setting<BreakModes> breakMode;
    private Setting<SwingModes> swingMode;
    private Setting<SyncModes> syncMode;
    private Setting<Boolean> throughWalls;
    private Setting<Double> breakAttempts;
    private Setting<Double> breakDelay;
    private Setting<Boolean> crystalPlace;
    private Setting<PlaceModes> placeMode;
    private Setting<Boolean> autoSwitch;
    private Setting<Boolean> raytrace;
    private Setting<Boolean> multiPlace;
    private Setting<Boolean> predictPlace;
    private Setting<Boolean> verifyPlace;
    private Setting<Double> placeRange;
    private Setting<Double> wallRange;
    private Setting<Double> breakRange;
    private Setting<Double> minDamage;
    private Setting<Double> maxSelfPlaceDamage;
    private Setting<Double> placeDelay;
    private Setting<Boolean> crystalCalculations;
    private Setting<LogicModes> logicMode;
    private Setting<BlockLogicModes> blockLogicMode;
    private Setting<Double> targetRange;
    private Setting<Boolean> crystalPause;
    private Setting<Boolean> pauseHealthAllow;
    private Setting<Double> pauseHealth;
    private Setting<Boolean> pauseWhileMining;
    private Setting<Boolean> pauseWhileEating;
    private Setting setting;
    private final CooldownUtil breakTimer = new CooldownUtil();
    private final CooldownUtil placeTimer = new CooldownUtil();
    private EntityPlayer playerTarget = null;
    private CrystalPosition crystalTarget = new CrystalPosition(null, 0.0, 0.0);

    public CrystalAura() {
        super("Crystal Aura", Module.Category.Combat);
        this.crystalBreak = this.register("Break", true);
        this.crystalPlace = this.register("Place", true);
        this.throughWalls = this.register("Thru Walls", true);
        this.breakMode = this.register("BreakMode", BreakModes.Packet);
        this.swingMode = this.register("SwingMode", SwingModes.None);
        this.syncMode = this.register("SyncMode", SyncModes.Attack);
        this.placeMode = this.register("PlaceMode", PlaceModes.Standard);
        this.autoSwitch = this.register("Auto Switch", true);
        this.raytrace = this.register("RayTrace", true);
        this.multiPlace = this.register("Multi Place", true);
        this.predictPlace = this.register("Predict", true);
        this.verifyPlace = this.register("Verify", false);
        this.breakAttempts = this.register("Break Attempts", 1.0, 0.0, 5.0, 0);
        this.placeRange = this.register("Place Range", 6.0, 0.0, 6.0, 2);
        this.breakRange = this.register("Break Range", 5.9, 0.0, 6.0, 2);
        this.wallRange = this.register("Wall Range", 3.5, 0.0, 5.5, 2);
        this.targetRange = this.register("Target Range", 15.0, 0.0, 15.0, 1);
        this.minDamage = this.register("Min Dmg", 8.0, 0.0, 36.0, 0);
        this.maxSelfPlaceDamage = this.register("Self Place Dmg", 8.0, 0.0, 36.0, 0);
        this.placeDelay = this.register("Place Delay", 1.0, 0.0, 20.0, 0);
        this.breakDelay = this.register("Break Delay", 1.0, 0.0, 20.0, 0);
        this.logicMode = this.register("Logic", LogicModes.PlaceBreak);
        this.blockLogicMode = this.register("Block Logic", BlockLogicModes.Thirteen);
        this.crystalCalculations = this.register("Calculationz", true);
        this.crystalPause = this.register("Pause", false);
        this.pauseHealthAllow = this.register("Pause Health Allowed", false);
        this.pauseHealth = this.register("Pause Health", 0.0, 0.0, 36.0, 0);
        this.pauseWhileMining = this.register("Pause Mine", false);
        this.pauseWhileEating = this.register("Pause Eat", false);
    }

    private Setting<BlockLogicModes> register(String name, BlockLogicModes thirteen) {
        return this.setting;
    }

    private Setting<LogicModes> register(String name, LogicModes placeBreak) {
        return this.setting;
    }

    private Setting<PlaceModes> register(String name, PlaceModes standard) {
        return this.setting;
    }

    private Setting<SyncModes> register(String name, SyncModes attack) {
        return this.setting;
    }

    private Setting<SwingModes> register(String name, SwingModes mode) {
        return this.setting;
    }

    private Setting<BreakModes> register(String name, BreakModes mode) {
        return this.setting;
    }

    @Override
    public void onDisable() {
        this.playerTarget = null;
        this.crystalTarget = null;
    }

    @Override
    public void onUpdate() {
        this.playerTarget = TargetUtil.getClosestPlayer(this.targetRange.getValue());
        this.implementLogic();
    }

    private void implementLogic() {
        switch (this.logicMode.getValue()) {
            case BreakPlace: {
                this.breakCrystal();
                this.placeCrystal();
                break;
            }
            case PlaceBreak: {
                this.placeCrystal();
                this.breakCrystal();
            }
        }
    }

    private void breakCrystal() {
        EntityEnderCrystal entityEnderCrystal;
        if (this.handlePause()) {
            return;
        }
        if (this.crystalBreak.getValue().booleanValue() && (entityEnderCrystal = (EntityEnderCrystal)CrystalAura.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(entity -> Float.valueOf(CrystalAura.mc.player.getDistance(entity)))).orElse(null)) != null && !entityEnderCrystal.isDead && this.breakTimer.passed(this.breakDelay.getValue() * 60.0)) {
            if (!((double)CrystalAura.mc.player.getDistance((Entity)entityEnderCrystal) <= this.breakRange.getValue())) {
                return;
            }
            if (!CrystalAura.mc.player.canEntityBeSeen((Entity)entityEnderCrystal) && !this.throughWalls.getValue().booleanValue()) {
                return;
            }
            this.handleBreak(entityEnderCrystal);
            if (this.syncMode.getValue() == SyncModes.Attack) {
                entityEnderCrystal.setDead();
            }
            this.breakTimer.reset();
        }
    }

    private void placeCrystal() {
        if (this.handlePause()) {
            return;
        }
        if (this.crystalPlace.getValue().booleanValue()) {
            ArrayList<CrystalPosition> crystalPositions = new ArrayList<CrystalPosition>();
            for (BlockPos blockPos : CrystalAura.crystalBlocks((EntityPlayer)CrystalAura.mc.player, this.placeRange.getValue(), this.predictPlace.getValue(), this.multiPlace.getValue() == false, this.blockLogicMode.getValue() == BlockLogicModes.Thirteen)) {
                double calculatedSelfDamage;
                if (PlayerUtil.isInViewFrustrum(blockPos) && CrystalAura.mc.player.getDistanceSq(blockPos) >= Math.pow(this.wallRange.getValue(), 2.0) || this.verifyPlace.getValue().booleanValue() && CrystalAura.mc.player.getDistanceSq(blockPos) >= Math.pow(this.breakRange.getValue(), 2.0)) continue;
                double calculatedTargetDamage = CrystalUtil.calculateDamage((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5, (Entity)this.playerTarget);
                double d = calculatedSelfDamage = CrystalAura.mc.player.capabilities.isCreativeMode ? 0.0 : (double)CrystalUtil.calculateDamage((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5, (Entity)CrystalAura.mc.player);
                if (calculatedTargetDamage < this.minDamage.getValue() || calculatedSelfDamage > this.maxSelfPlaceDamage.getValue()) continue;
                crystalPositions.add(new CrystalPosition(blockPos, calculatedTargetDamage, calculatedSelfDamage));
            }
            CrystalPosition tempPosition = crystalPositions.stream().max(Comparator.comparing(CrystalPosition::getTargetDamage)).orElse(null);
            if (tempPosition == null) {
                this.crystalTarget = null;
                return;
            }
            this.crystalTarget = tempPosition;
            if (this.crystalTarget.getPosition() != null && this.placeTimer.passed(this.placeDelay.getValue() * 60.0) && this.autoSwitch.getValue().booleanValue()) {
                InventoryUtil.switchToSlot(ItemEndCrystal.class);
            }
            if (CrystalAura.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || CrystalAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                CrystalUtil.placeCrystal(this.crystalTarget.getPosition(), CrystalUtil.getEnumFacing(this.raytrace.getValue(), this.crystalTarget.getPosition()), this.placeMode.getValue() == PlaceModes.Packet);
            }
            this.placeTimer.reset();
        }
    }

    private void handleBreak(EntityEnderCrystal entityEnderCrystal) {
        int i = 0;
        while ((double)i < this.breakAttempts.getValue()) {
            switch (this.breakMode.getValue()) {
                case Swing: {
                    CrystalUtil.breakCrystal(entityEnderCrystal, false);
                    break;
                }
                case Packet: {
                    CrystalUtil.breakCrystal(entityEnderCrystal, true);
                }
            }
            ++i;
        }
        switch (this.swingMode.getValue()) {
            case Mainhand: {
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                break;
            }
            case Offhand: {
                CrystalAura.mc.player.swingArm(EnumHand.OFF_HAND);
                break;
            }
            case Spam: {
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                break;
            }
            case Both: {
                CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                CrystalAura.mc.player.swingArm(EnumHand.OFF_HAND);
            }
        }
    }

    private boolean handlePause() {
        if ((double)(CrystalAura.mc.player.getHealth() + CrystalAura.mc.player.getAbsorptionAmount()) <= this.pauseHealth.getValue() && this.pauseHealthAllow.getValue().booleanValue()) {
            return true;
        }
        if (CrystalAura.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && CrystalAura.mc.player.isHandActive() && this.pauseWhileEating.getValue().booleanValue()) {
            return true;
        }
        return CrystalAura.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE && CrystalAura.mc.player.isHandActive() && this.pauseWhileMining.getValue() != false;
    }

    public static List<BlockPos> crystalBlocks(EntityPlayer entityPlayer, double placeRange, boolean prediction, boolean multiPlace, boolean thirteen) {
        return BlockUtil.getNearbyBlocks(entityPlayer, placeRange, prediction).stream().filter(blockPos -> CrystalUtil.canPlaceCrystal(blockPos, multiPlace, thirteen)).collect(Collectors.toList());
    }

    public void onPacketSend(EventPacket.Send event) {
        CPacketUseEntity cPacketUseEntity;
        if (this.syncMode.getValue() == SyncModes.Packet && event.getPacket() instanceof CPacketUseEntity && (cPacketUseEntity = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && cPacketUseEntity.getEntityFromWorld((World)CrystalAura.mc.world) instanceof EntityEnderCrystal && this.breakMode.getValue() == BreakModes.Packet) {
            Objects.requireNonNull(cPacketUseEntity.getEntityFromWorld((World)CrystalAura.mc.world)).setDead();
        }
    }

    public void onPacketReceive(EventPacket.Receive event) {
        SPacketSoundEffect sPacketSoundEffect;
        if (this.syncMode.getValue() == SyncModes.Sound && event.getPacket() instanceof SPacketSoundEffect && (sPacketSoundEffect = (SPacketSoundEffect)event.getPacket()).getCategory() == SoundCategory.BLOCKS && sPacketSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            for (Entity entity : CrystalAura.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistance(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) <= this.breakRange.getValue())) continue;
                entity.setDead();
            }
        }
    }

    public String getArraylistInfo() {
        if (this.playerTarget != null) {
            return this.playerTarget.getName();
        }
        return "";
    }

    public static enum BlockLogicModes {
        Twelve,
        Thirteen;

    }

    public static enum LogicModes {
        BreakPlace,
        PlaceBreak;

    }

    public static enum SyncModes {
        None,
        Attack,
        Packet,
        Sound;

    }

    public static enum PlaceModes {
        Standard,
        Packet;

    }

    public static enum SwingModes {
        Mainhand,
        Offhand,
        Spam,
        Both,
        None;

    }

    public static enum BreakModes {
        Swing,
        Packet;

    }
}

