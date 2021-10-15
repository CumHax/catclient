/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;

public class SettingManager {
    private final List<Setting> settings = new ArrayList<Setting>();

    public void register(Setting setting) {
        this.settings.add(setting);
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public List<Setting> getSettings(Module module) {
        return this.settings.stream().filter(s -> s.getModule() == module).collect(Collectors.toList());
    }

    public Setting getSetting(String name, Module module) {
        return this.settings.stream().filter(s -> s.getModule() == module).filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

