/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.impl.gui.click.components.settings;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.font.FontUtil;
import wtf.fuckyou.catclient.api.settings.Setting;
import wtf.fuckyou.catclient.impl.gui.click.ClickGui;
import wtf.fuckyou.catclient.impl.gui.click.Component;
import wtf.fuckyou.catclient.impl.gui.click.components.ModuleButton;

public class SliderButton
extends Component {
    private final Setting<Double> setting;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double renderWidth;

    public SliderButton(Setting<Double> setting, ModuleButton button, int offset) {
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
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0 && this.button.open) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        this.dragging = false;
    }

    @Override
    public void updateComponent(double mouseX, double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        this.y = this.button.frame.getY() + this.offset;
        this.x = this.button.frame.getX();
        double diff = Math.min(88.0, Math.max(0.0, mouseX - (double)this.x));
        double min = this.setting.getMin();
        double max = this.setting.getMax();
        this.renderWidth = 88.0 * (this.setting.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.setting.setValue(this.setting.getMin());
            } else {
                double newValue = SliderButton.round(diff / 88.0 * (max - min) + min, this.setting.getDecimal());
                this.setting.setValue(newValue);
            }
        }
    }

    @Override
    public void render() {
        CatClient.gui.drawGradient(this.button.frame.getX(), this.button.frame.getY() + this.offset, this.button.frame.getX() + this.button.frame.getWidth(), this.button.frame.getY() + this.offset + 16, this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB(), this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB());
        CatClient.gui.drawGradient(this.button.frame.getX(), this.button.frame.getY() + this.offset, (int)((double)this.button.frame.getX() + this.renderWidth), this.button.frame.getY() + this.offset + 16, this.isHovered ? ClickGui.color.darker().getRGB() : ClickGui.color.getRGB(), this.isHovered ? ClickGui.color.darker().getRGB() : ClickGui.color.getRGB());
        FontUtil.drawCFontText(this.setting.getName(), this.button.frame.getX() + 5, this.button.frame.getY() + this.offset + 1, this.isHovered ? new Color(170, 170, 170).getRGB() : -1);
        FontUtil.drawCFontText(String.valueOf(SliderButton.round(this.setting.getValue(), this.setting.getDecimal())), (float)(this.button.frame.getX() + this.button.frame.getWidth() - 2) - FontUtil.getCFontTextWidth(String.valueOf(SliderButton.round(this.setting.getValue(), this.setting.getDecimal()))), this.button.frame.getY() + this.offset + 1, this.isHovered ? new Color(170, 170, 170).getRGB() : -1);
    }

    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean isHovered(double x, double y) {
        return x > (double)this.x && x < (double)(this.x + 88) && y > (double)this.y && y < (double)(this.y + 16);
    }
}

