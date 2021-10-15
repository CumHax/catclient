/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.module;

import java.util.Arrays;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.settings.Setting;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;

public class Module
implements Wrapper {
    private final String name;
    private final String description;
    private final Category category;
    private int key = -1;
    private boolean enabled = false;

    public Module(String name, Category category) {
        this.name = name;
        this.description = "No description provided.";
        this.category = category;
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        if (this.isEnabled()) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public void enable() {
        this.setEnabled(true);
        this.onEnable();
    }

    public void disable() {
        this.setEnabled(false);
        this.onDisable();
    }

    public void onEnable() {
        CatClient.EVENTBUS.register((Object)this);
    }

    public void onDisable() {
        CatClient.EVENTBUS.unregister((Object)this);
    }

    public void onUpdate() {
    }

    public void onRender() {
    }

    public Setting<Boolean> register(String name, boolean value) {
        Setting<Boolean> setting = new Setting<Boolean>(name, this, this.getCategory(), value);
        CatClient.settingManager.register(setting);
        return setting;
    }

    public Setting<Double> register(String name, double value, double min, double max, int decimalPlaces) {
        Setting<Double> setting = new Setting<Double>(name, this, this.getCategory(), value, min, max, decimalPlaces);
        CatClient.settingManager.register(setting);
        return setting;
    }

    public Setting<String> register(String name, String value, String ... modes) {
        Setting<String> setting = new Setting<String>(name, this, this.getCategory(), Arrays.asList(modes), value);
        CatClient.settingManager.register(setting);
        return setting;
    }

    public static enum Category {
        Combat,
        Movement,
        Render,
        Player,
        World,
        Client;

    }
}

