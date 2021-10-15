/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.gui.click;

import java.util.ArrayList;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.font.FontUtil;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.impl.gui.click.ClickGui;
import wtf.fuckyou.catclient.impl.gui.click.Component;
import wtf.fuckyou.catclient.impl.gui.click.components.ModuleButton;

public class Frame {
    public Module.Category category;
    public ArrayList<Component> components;
    private boolean open;
    private final int width;
    private int y;
    private int x;
    private final int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private int height;

    public Frame(Module.Category category) {
        this.category = category;
        this.components = new ArrayList();
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 16;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int componentY = this.barHeight;
        for (Module m : CatClient.moduleManager.getModules(category)) {
            ModuleButton moduleButton = new ModuleButton(m, this, componentY);
            this.components.add(moduleButton);
            componentY += 16;
        }
        this.update();
    }

    public void renderFrame() {
        CatClient.gui.drawGradient(this.x, this.y, this.x + this.width, this.y + this.barHeight, ClickGui.color.getRGB(), ClickGui.color.getRGB());
        FontUtil.drawCFontText(this.category.name(), (float)(this.x + this.width / 2) - FontUtil.getCFontTextWidth(this.category.name()) / 2.0f, this.y + 1, -1);
        if (this.open && !this.components.isEmpty()) {
            this.components.forEach(Component::render);
        }
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void update() {
        int off = this.barHeight;
        for (Component comp : this.components) {
            comp.setOffset(off);
            off += comp.getHeight();
        }
        this.height = off;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }

    public boolean isHover(double x, double y) {
        return x >= (double)this.x && x <= (double)(this.x + this.width) && y >= (double)this.y && y <= (double)(this.y + this.barHeight);
    }
}

