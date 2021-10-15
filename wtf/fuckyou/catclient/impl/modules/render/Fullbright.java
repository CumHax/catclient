/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.modules.render;

import wtf.fuckyou.catclient.api.module.Module;

public class Fullbright
extends Module {
    public Fullbright() {
        super("Fullbright", "Gamma go brrrr", Module.Category.Render);
    }

    @Override
    public void onUpdate() {
        Fullbright.mc.gameSettings.gammaSetting = 1000.0f;
    }

    @Override
    public void onDisable() {
        Fullbright.mc.gameSettings.gammaSetting = 1.0f;
    }
}

