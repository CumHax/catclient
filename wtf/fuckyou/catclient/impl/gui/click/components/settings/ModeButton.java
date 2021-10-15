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

public class ModeButton
extends Component {
    private final Setting<String> setting;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;
    private int modeIndex;

    public ModeButton(Setting<String> setting, ModuleButton button, int offset) {
        this.setting = setting;
        this.button = button;
        this.x = button.frame.getX() + button.frame.getWidth();
        this.y = button.frame.getY() + button.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && this.button.open) {
            int maxIndex = this.setting.getModes().size() - 1;
            if (button == 0) {
                ++this.modeIndex;
                if (this.modeIndex > maxIndex) {
                    this.modeIndex = 0;
                }
                this.setting.setValue(this.setting.getModes().get(this.modeIndex));
            }
            if (button == 1) {
                --this.modeIndex;
                if (this.modeIndex < 0) {
                    this.modeIndex = maxIndex;
                }
                this.setting.setValue(this.setting.getModes().get(this.modeIndex));
            }
        }
    }

    @Override
    public void updateComponent(double mouseX, double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        this.y = this.button.frame.getY() + this.offset;
        this.x = this.button.frame.getX();
    }

    @Override
    public void render() {
        CatClient.gui.drawGradient(this.button.frame.getX(), this.button.frame.getY() + this.offset, this.button.frame.getX() + this.button.frame.getWidth(), this.button.frame.getY() + this.offset + 16, this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB(), this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB());
        FontUtil.drawCFontText(this.setting.getName(), this.button.frame.getX() + 5, this.button.frame.getY() + this.offset + 1, -1);
        FontUtil.drawCFontText(this.setting.getValue(), (float)(this.button.frame.getX() + this.button.frame.getWidth() - 5) - FontUtil.getCFontTextWidth(this.setting.getValue()), this.button.frame.getY() + this.offset + 1, -1);
    }

    public boolean isHovered(double x, double y) {
        return x > (double)this.x && x < (double)(this.x + 88) && y > (double)this.y && y < (double)(this.y + 16);
    }
}

