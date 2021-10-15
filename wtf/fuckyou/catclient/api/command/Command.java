/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.command;

public abstract class Command {
    private final String[] aliases;
    private final String description;
    private final String usage;

    public Command(String[] aliases, String description, String usage) {
        this.aliases = aliases;
        this.description = description;
        this.usage = usage;
    }

    public abstract void execute(String[] var1);

    public String[] getAliases() {
        return this.aliases;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUsage() {
        return this.usage;
    }
}

