/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package wtf.fuckyou.catclient.impl.modules.client;

import net.minecraft.client.gui.GuiScreen;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;

public class CatGui
extends Module {
    public static CatGui instance;
    public Setting<Double> red = this.register("Red", 145.0, 0.0, 255.0, 0);
    public Setting<Double> green = this.register("Green", 185.0, 0.0, 255.0, 0);
    public Setting<Double> blue = this.register("Blue", 170.0, 0.0, 255.0, 0);

    public CatGui() {
        super("CatGui", "M-mh... Click and drag panels... Meoo-oow...", Module.Category.Client);
        this.setKey(157);
        instance = this;
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen((GuiScreen)CatClient.gui);
        this.disable();
    }
}

