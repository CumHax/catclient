/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 */
package wtf.fuckyou.catclient.api.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.util.text.TextFormatting;
import wtf.fuckyou.catclient.api.command.Command;
import wtf.fuckyou.catclient.api.utils.chat.MessageUtil;
import wtf.fuckyou.catclient.impl.commands.PrefixCommand;

public class CommandManager {
    private final List<Command> commands = new ArrayList<Command>();
    private String prefix = "&";

    public CommandManager() {
        this.commands.addAll(Arrays.asList(new PrefixCommand()));
        this.commands.sort(Comparator.comparing(command -> command.getAliases()[0]));
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public void execute(String message) {
        String noPrefix = message.substring(this.prefix.length());
        Command command = this.getCommand(noPrefix.split(" ")[0]);
        if (command != null) {
            command.execute(Arrays.copyOfRange(noPrefix.split(" "), 1, noPrefix.split(" ").length));
            return;
        }
        MessageUtil.instance.addMessage("Command \"" + noPrefix.split(" ")[0] + (Object)TextFormatting.RED + "\" not found!", true);
    }

    public Command getCommand(String name) {
        return this.commands.stream().filter(command -> Arrays.stream(command.getAliases()).anyMatch(name::equalsIgnoreCase)).findFirst().orElse(null);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

