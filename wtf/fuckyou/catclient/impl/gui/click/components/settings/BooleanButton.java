/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.gui.click.components.settings;

import java.awt.Color;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.font.FontUtil;
import wtf.fuckyou.catclient.api.settings.Setting;
import wtf.fuckyou.catclient.impl.gui.click.Component;
import wtf.fuckyou.catclient.impl.gui.click.components.ModuleButton;

public class BooleanButton
extends Component {
    private final Setting<Boolean> setting;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;

    public BooleanButton(Setting<Boolean> setting, ModuleButton button, int offset) {
        this.setting = setting;
        this.button = button;
        this.x = button.frame.getX() + button.frame.getWidth();
        this.y = button.frame.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void updateComponent(double mouseX, double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        this.y = this.button.frame.getY() + this.offset;
        this.x = this.button.frame.getX();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0 && this.button.open) {
            this.setting.setValue(this.setting.getValue() == false);
        }
    }

    @Override
    public void render() {
        CatClient.gui.drawGradient(this.button.frame.getX(), this.button.frame.getY() + this.offset, this.button.frame.getX() + this.button.frame.getWidth(), this.button.frame.getY() + this.offset + 16, this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB(), this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB());
        FontUtil.drawCFontText(this.setting.getName(), this.button.frame.getX() + 5, this.button.frame.getY() + this.offset + 1, this.setting.getValue() != false ? -1 : new Color(170, 170, 170).getRGB());
    }

    public boolean isHovered(double x, double y) {
        return x > (double)this.x && x < (double)(this.x + 88) && y > (double)this.y && y < (double)(this.y + 16);
    }
}

