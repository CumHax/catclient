/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.modules.client;

import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;

public class Font
extends Module {
    public static Font instance;
    public Setting<Boolean> shadow = this.register("Shadow", true);

    public Font() {
        super("Font", "Using CFont, or not... Thats the question...", Module.Category.Client);
        instance = this;
        this.setEnabled(true);
    }
}

