/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent
 *  org.lwjgl.input.Keyboard
 */
package wtf.fuckyou.catclient.api.event;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.event.events.EventChatMessage;
import wtf.fuckyou.catclient.api.event.events.EventKey;
import wtf.fuckyou.catclient.api.event.events.EventRenderWorldLast;
import wtf.fuckyou.catclient.api.event.events.EventTick;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;

public class EventHandler
implements Wrapper {
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            CatClient.EVENTBUS.post((Object)new EventKey(Keyboard.getEventKey()));
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent event) {
        CatClient.EVENTBUS.post((Object)new EventTick());
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        CatClient.EVENTBUS.post((Object)new EventRenderWorldLast(event.getContext(), event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onMessage(ClientChatEvent event) {
        CatClient.EVENTBUS.post((Object)new EventChatMessage(event.getMessage()));
        event.setCanceled(true);
    }
}

