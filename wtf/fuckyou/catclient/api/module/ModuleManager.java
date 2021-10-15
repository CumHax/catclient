/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;
import wtf.fuckyou.catclient.impl.modules.client.CatGui;
import wtf.fuckyou.catclient.impl.modules.client.Font;
import wtf.fuckyou.catclient.impl.modules.movement.Speed;
import wtf.fuckyou.catclient.impl.modules.movement.Sprint;
import wtf.fuckyou.catclient.impl.modules.movement.Step;
import wtf.fuckyou.catclient.impl.modules.render.Fullbright;
import wtf.fuckyou.catclient.impl.modules.world.FakePlayer;

public class ModuleManager
implements Wrapper {
    private final List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        this.modules.addAll(Arrays.asList(new Speed(), new Sprint(), new Step(), new Fullbright(), new FakePlayer(), new CatGui(), new Font()));
        this.modules.sort(Comparator.comparing(Module::getName));
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public List<Module> getModules(Module.Category category) {
        return this.modules.stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
    }

    public Module getModule(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

