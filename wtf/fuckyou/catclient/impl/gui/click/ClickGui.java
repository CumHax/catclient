/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package wtf.fuckyou.catclient.impl.gui.click;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.impl.gui.click.Component;
import wtf.fuckyou.catclient.impl.gui.click.Frame;
import wtf.fuckyou.catclient.impl.modules.client.CatGui;

public class ClickGui
extends GuiScreen {
    public static List<Frame> frames;
    public static Color color;

    public ClickGui() {
        frames = new ArrayList<Frame>();
        int frameX = 10;
        for (Module.Category category : Module.Category.values()) {
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth() + 10;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        color = new Color(CatGui.instance.red.getValue().intValue(), CatGui.instance.green.getValue().intValue(), CatGui.instance.blue.getValue().intValue());
        frames.forEach(frame -> {
            frame.renderFrame();
            frame.updatePosition(mouseX, mouseY);
            frame.getComponents().forEach(c -> c.updateComponent(mouseX, mouseY));
        });
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame : frames) {
            if (frame.isHover(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isHover(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) {
            frame.setDrag(false);
        }
        for (Frame frame : frames) {
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public void drawGradient(int left, int top, int right, int bottom, int startColor, int endColor) {
        this.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    public void keyTyped(char typedChar, int keyCode) {
        for (Frame frame : frames) {
            if (!frame.isOpen() || keyCode == 1 || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.keyTyped(keyCode);
            }
        }
        System.out.println(typedChar);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

