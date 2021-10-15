/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.TextFormatting
 */
package wtf.fuckyou.catclient.api.utils.chat;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;

public enum MessageUtil implements Wrapper
{
    instance;


    public void addMessage(String message, boolean silent) {
        String prefix = (Object)TextFormatting.AQUA + "<CatClient> " + (Object)TextFormatting.RESET;
        if (silent) {
            MessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(prefix + message), 1);
        } else {
            MessageUtil.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(prefix + message));
        }
    }
}

