/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.MoverType
 */
package wtf.fuckyou.catclient.api.event.events;

import net.minecraft.entity.MoverType;
import wtf.fuckyou.catclient.api.event.Event;

public class EventMove
extends Event {
    public MoverType moverType;
    public double motionX;
    public double motionY;
    public double motionZ;

    public EventMove(MoverType moverType, double motionX, double motionY, double motionZ) {
        this.moverType = moverType;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
}

