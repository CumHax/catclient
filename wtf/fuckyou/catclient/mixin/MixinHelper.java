/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandlerContext
 *  net.minecraft.entity.MoverType
 *  net.minecraft.network.Packet
 */
package wtf.fuckyou.catclient.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.MoverType;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.event.events.EventMove;
import wtf.fuckyou.catclient.api.event.events.EventPacket;

public class MixinHelper {
    public static EventMove onMove(MoverType moverType, double x, double y, double z) {
        EventMove event = new EventMove(moverType, x, y, z);
        CatClient.EVENTBUS.post((Object)event);
        return event;
    }

    public static void onPacketRecieve(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        EventPacket.Receive event = new EventPacket.Receive(packet);
        CatClient.EVENTBUS.post((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    public static void onPacketSend(Packet<?> packet, CallbackInfo ci) {
        EventPacket.Send event = new EventPacket.Send(packet);
        CatClient.EVENTBUS.post((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    public static void onPostPacket(Packet<?> packet, CallbackInfo ci) {
        EventPacket.Post event = new EventPacket.Post(packet);
        CatClient.EVENTBUS.post((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}

