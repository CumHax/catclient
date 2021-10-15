/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.main.GameConfiguration
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 */
package wtf.fuckyou.catclient.api.utils.chat;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public final class InventoryUtil
extends Minecraft {
    public static Minecraft mc;

    public InventoryUtil(GameConfiguration p_i45547_1_) {
        super(p_i45547_1_);
    }

    public static void switchToSlot(Class<? extends Item> clazz) {
        if (InventoryUtil.mc.player.getHeldItemMainhand().getItem().getClass().isAssignableFrom(clazz)) {
            return;
        }
        int slot = InventoryUtil.getHotbarItemSlot(clazz);
        if (slot == -1) {
            return;
        }
        InventoryUtil.mc.player.inventory.currentItem = slot;
    }

    public static void switchToSlot(Item item) {
        if (InventoryUtil.mc.player.getHeldItemMainhand().getItem() == item) {
            return;
        }
        int slot = InventoryUtil.getHotbarItemSlot(item.getClass());
        if (slot == -1) {
            return;
        }
        InventoryUtil.mc.player.inventory.currentItem = slot;
    }

    public static void offhandItem(Item item) {
        int slot = InventoryUtil.getInventoryItemSlot(item);
        if (slot != -1) {
            InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
            InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
            InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
            InventoryUtil.mc.playerController.updateController();
        }
    }

    public static int getHotbarItemSlot(Class<? extends Item> item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (!InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem().getClass().isAssignableFrom(item)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    public static int getHotbarBlockSlot(Block block) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Item item = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (!(item instanceof ItemBlock) || !((ItemBlock)item).getBlock().equals((Object)block)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    public static int getHotbarItemSlot(Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Item selection = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (!selection.equals((Object)item)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    private static int getInventoryItemSlot(Item item) {
        for (int i = 0; i < 36; ++i) {
            Item cacheItem = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (cacheItem != item) continue;
            if (i < 9) {
                i += 36;
            }
            return i;
        }
        return -1;
    }
}

