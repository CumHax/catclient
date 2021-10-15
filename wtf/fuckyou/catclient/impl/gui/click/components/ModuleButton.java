/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.gui.click.components;

import java.awt.Color;
import java.util.ArrayList;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.font.FontUtil;
import wtf.fuckyou.catclient.api.module.Module;
import wtf.fuckyou.catclient.api.settings.Setting;
import wtf.fuckyou.catclient.impl.gui.click.Component;
import wtf.fuckyou.catclient.impl.gui.click.Frame;
import wtf.fuckyou.catclient.impl.gui.click.components.settings.BooleanButton;
import wtf.fuckyou.catclient.impl.gui.click.components.settings.KeyButton;
import wtf.fuckyou.catclient.impl.gui.click.components.settings.ModeButton;
import wtf.fuckyou.catclient.impl.gui.click.components.settings.SliderButton;

public class ModuleButton
extends Component {
    public Module module;
    public Frame frame;
    public int offset;
    private boolean isHovered;
    private final ArrayList<Component> components;
    public boolean open;
    private final int height;

    public ModuleButton(Module module, Frame frame, int offset) {
        this.module = module;
        this.frame = frame;
        this.offset = offset;
        this.components = new ArrayList();
        this.open = false;
        this.height = 16;
        int settingY = this.offset + 16;
        if (!CatClient.settingManager.getSettings(module).isEmpty()) {
            block5: for (Setting s : CatClient.settingManager.getSettings(module)) {
                switch (s.getType()) {
                    case BOOLEAN: {
                        this.components.add(new BooleanButton(s, this, settingY));
                        continue block5;
                    }
                    case NUMBER: {
                        this.components.add(new SliderButton(s, this, settingY));
                        continue block5;
                    }
                    case MODE: {
                        this.components.add(new ModeButton(s, this, settingY));
                        continue block5;
                    }
                }
            }
        }
        this.components.add(new KeyButton(this, settingY));
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
        int settingY = this.offset + 16;
        for (Component c : this.components) {
            c.setOffset(settingY);
            settingY += 16;
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 16 * (this.components.size() + 1);
        }
        return 16;
    }

    @Override
    public void updateComponent(double mouseX, double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        if (!this.components.isEmpty()) {
            this.components.forEach(c -> c.updateComponent(mouseX, mouseY));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0) {
            this.module.toggle();
        }
        if (this.isHovered(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.frame.update();
        }
        this.components.forEach(c -> c.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        this.components.forEach(c -> c.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void keyTyped(int key) {
        this.components.forEach(c -> c.keyTyped(key));
    }

    @Override
    public void render() {
        CatClient.gui.drawGradient(this.frame.getX(), this.frame.getY() + this.offset, this.frame.getX() + this.frame.getWidth(), this.frame.getY() + this.offset + 16, this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB(), this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB());
        FontUtil.drawCFontText(this.module.getName(), this.frame.getX() + 3, this.frame.getY() + this.offset + 1, this.module.isEnabled() ? -1 : new Color(170, 170, 170).getRGB());
        if (this.open) {
            this.components.forEach(Component::render);
        }
    }

    public boolean isHovered(double x, double y) {
        return x > (double)this.frame.getX() && x < (double)(this.frame.getX() + this.frame.getWidth()) && y > (double)(this.frame.getY() + this.offset) && y < (double)(this.frame.getY() + 16 + this.offset);
    }
}

