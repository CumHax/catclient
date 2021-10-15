/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandlerContext
 *  net.minecraft.network.NetworkManager
 *  net.minecraft.network.Packet
 */
package wtf.fuckyou.catclient.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.fuckyou.catclient.mixin.MixinHelper;

@Mixin(value={NetworkManager.class})
public class MixinNetworkManager {
    @Inject(method={"channelRead0"}, at={@At(value="HEAD")}, cancellable=true)
    public void onPacketReceive(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        MixinHelper.onPacketRecieve(context, packet, ci);
    }

    @Inject(method={"sendPacket(Lnet/minecraft/network/Packet;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void onPacketSend(Packet<?> packet, CallbackInfo ci) {
        MixinHelper.onPacketSend(packet, ci);
    }

    @Inject(method={"sendPacket(Lnet/minecraft/network/Packet;)V"}, at={@At(value="RETURN")})
    private void onPostSendPacket(Packet<?> packet, CallbackInfo ci) {
        MixinHelper.onPostPacket(packet, ci);
    }
}

