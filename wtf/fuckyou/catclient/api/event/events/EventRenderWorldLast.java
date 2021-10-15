/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 */
package wtf.fuckyou.catclient.api.event.events;

import net.minecraft.client.renderer.RenderGlobal;
import wtf.fuckyou.catclient.api.event.Event;

public class EventRenderWorldLast
extends Event {
    private final RenderGlobal context;
    private final float partialTicks;

    public EventRenderWorldLast(RenderGlobal context, float partialTicks) {
        this.context = context;
        this.partialTicks = partialTicks;
    }

    public RenderGlobal getContext() {
        return this.context;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

