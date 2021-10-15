/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.EventBus
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.core.Logger
 *  org.lwjgl.input.Keyboard
 */
package wtf.fuckyou.catclient;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.lwjgl.input.Keyboard;
import wtf.fuckyou.catclient.api.command.CommandManager;
import wtf.fuckyou.catclient.api.event.EventHandler;
import wtf.fuckyou.catclient.api.event.events.EventChatMessage;
import wtf.fuckyou.catclient.api.event.events.EventKey;
import wtf.fuckyou.catclient.api.event.events.EventRenderWorldLast;
import wtf.fuckyou.catclient.api.event.events.EventTick;
import wtf.fuckyou.catclient.api.friends.FriendManager;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.module.ModuleManager;
import wtf.fuckyou.catclient.api.settings.SettingManager;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;
import wtf.fuckyou.catclient.impl.gui.click.ClickGui;

@Mod(modid="catclient", name="CatClient", version="0.1")
public class CatClient
implements Wrapper {
    public static final String MOD_ID = "catclient";
    public static final String NAME = "CatClient";
    public static final String VERSION = "0.1";
    public static final Logger logger = (Logger)LogManager.getLogger((String)"catclient");
    public static final EventBus EVENTBUS = new EventBus();
    public static FriendManager friendManager;
    public static SettingManager settingManager;
    public static ModuleManager moduleManager;
    public static ClickGui gui;
    public static CommandManager commandManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new EventHandler());
        EVENTBUS.register((Object)this);
        logger.info("i like cats! - sleepy");
        logger.info("make me a cheese sandwich - bigjmuffin");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("CatClient started ratting you.");
        friendManager = new FriendManager();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        gui = new ClickGui();
        logger.info("CatClient finished ratting you.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    public void onUpdate(EventTick event) {
        moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public void onRender(EventRenderWorldLast eventRenderWorldLast) {
        moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onRender);
    }

    @SubscribeEvent
    public void onKey(EventKey event) {
        if (CatClient.mc.player != null && CatClient.mc.world != null) {
            moduleManager.getModules().stream().filter(module -> module.getKey() == Keyboard.getEventKey()).forEach(Module::toggle);
        }
    }

    public void onMessage(EventChatMessage event) {
        if (event.getMessage().startsWith(commandManager.getPrefix())) {
            commandManager.execute(event.getMessage());
        }
    }
}

