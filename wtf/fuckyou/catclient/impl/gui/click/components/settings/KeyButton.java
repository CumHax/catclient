/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package wtf.fuckyou.catclient.impl.gui.click.components.settings;

import java.awt.Color;
import org.lwjgl.input.Keyboard;
import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.font.FontUtil;
import wtf.fuckyou.catclient.impl.gui.click.Component;
import wtf.fuckyou.catclient.impl.gui.click.components.ModuleButton;

public class KeyButton
extends Component {
    private boolean binding;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;

    public KeyButton(ModuleButton button, int offset) {
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
            this.binding = !this.binding;
        }
    }

    @Override
    public void keyTyped(int key) {
        if (this.binding) {
            if (key == 211) {
                this.button.module.setKey(-1);
            } else {
                this.button.module.setKey(key);
            }
            this.binding = false;
        }
    }

    @Override
    public void render() {
        CatClient.gui.drawGradient(this.button.frame.getX(), this.button.frame.getY() + this.offset, this.button.frame.getX() + this.button.frame.getWidth(), this.button.frame.getY() + this.offset + 16, this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB(), this.isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB());
        FontUtil.drawCFontText("Key", this.button.frame.getX() + 5, this.button.frame.getY() + this.offset + 1, -1);
        if (this.binding) {
            FontUtil.drawCFontText("...", (float)(this.button.frame.getX() + this.button.frame.getWidth() - 5) - FontUtil.getCFontTextWidth("..."), this.button.frame.getY() + this.offset + 1, -1);
        } else {
            String key;
            switch (this.button.module.getKey()) {
                case 345: {
                    key = "RCtrl";
                    break;
                }
                case 341: {
                    key = "Ctrl";
                    break;
                }
                case 346: {
                    key = "RAlt";
                    break;
                }
                case -1: {
                    key = "NONE";
                    break;
                }
                default: {
                    key = Keyboard.getKeyName((int)this.button.module.getKey());
                }
            }
            FontUtil.drawCFontText(key, (float)(this.button.frame.getX() + this.button.frame.getWidth() - 5) - FontUtil.getCFontTextWidth(key), this.button.frame.getY() + this.offset + 1, -1);
        }
    }

    public boolean isHovered(double x, double y) {
        return x > (double)this.x && x < (double)(this.x + 88) && y > (double)this.y && y < (double)(this.y + 16);
    }
}

