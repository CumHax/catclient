/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.modules.movement;

import wtf.fuckyou.catclient.api.module.Module;

public class Sprint
extends Module {
    public Sprint() {
        super("Sprint", "You can't press control? Whats next...", Module.Category.Movement);
    }

    @Override
    public void onUpdate() {
        Sprint.mc.player.setSprinting(true);
    }
}

