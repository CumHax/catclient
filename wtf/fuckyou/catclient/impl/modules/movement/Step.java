/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.modules.movement;

import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;

public class Step
extends Module {
    public static Step instance;
    public final Setting<Double> height = this.register("Height", 2.0, 0.0, 2.0, 0);
    private Setting setting;

    public Step() {
        super("Step", "Steps", Module.Category.Movement);
    }

    @Override
    public void onUpdate() {
        Step.mc.player.stepHeight = 2.0f;
    }

    @Override
    public void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
    }
}

