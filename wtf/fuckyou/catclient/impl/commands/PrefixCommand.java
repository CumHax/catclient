/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.commands;

import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.command.Command;
import wtf.fuckyou.catclient.api.utils.chat.MessageUtil;

public class PrefixCommand
extends Command {
    public PrefixCommand() {
        super(new String[]{"prefix", "p"}, "Set the prefix to whatever you want.", "prefix <char>");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            MessageUtil.instance.addMessage("Not enough arguments! Usage: " + this.getUsage(), true);
            return;
        }
        CatClient.commandManager.setPrefix(args[0]);
        MessageUtil.instance.addMessage("Prefix was set to " + CatClient.commandManager.getPrefix() + "!", true);
    }
}

