/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.settings;

import java.util.List;
import wtf.fuckyou.catclient.api.module.Module;

public class Setting<T> {
    private final String name;
    public T getValue;
    private String description = null;
    private final Module module;
    private final Module.Category category;
    private final Type type;
    private T value;
    private double max;
    private double min;
    private int decimal;
    private List<String> modes;

    public Setting(String name, Module module, Module.Category category, T value) {
        this.name = name;
        this.module = module;
        this.category = category;
        this.value = value;
        this.type = Type.BOOLEAN;
    }

    public Setting(String name, Module module, Module.Category category, T value, double min, double max, int decimal) {
        this.name = name;
        this.module = module;
        this.category = category;
        this.value = value;
        this.min = min;
        this.max = max;
        this.decimal = decimal;
        this.type = Type.NUMBER;
    }

    public Setting(String name, Module module, Module.Category category, List<String> modes, T value) {
        this.name = name;
        this.module = module;
        this.category = category;
        this.value = value;
        this.modes = modes;
        this.type = Type.MODE;
    }

    public String getName() {
        return this.name;
    }

    public Module getModule() {
        return this.module;
    }

    public Type getType() {
        return this.type;
    }

    public Module.Category getCategory() {
        return this.category;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public List<String> getModes() {
        return this.modes;
    }

    public Setting withDesc(String desc) {
        this.description = desc;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public T getValueAsString() {
        return this.value;
    }

    public static enum Type {
        NUMBER,
        BOOLEAN,
        MODE;

    }
}

