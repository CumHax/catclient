/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.font;

import wtf.fuckyou.catclient.CatClient;
import wtf.fuckyou.catclient.api.font.CFontRenderer;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;
import wtf.fuckyou.catclient.impl.modules.client.Font;

public class FontUtil
implements Wrapper {
    public static final CFontRenderer fontRenderer = new CFontRenderer(19.0f);

    public static void drawText(String text, float x, float y, int color) {
        if (Font.instance.shadow.getValue().booleanValue()) {
            if (CatClient.moduleManager.getModule("Font").isEnabled()) {
                fontRenderer.drawStringWithShadow(text, x, y, color);
            } else {
                FontUtil.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
            }
        } else if (CatClient.moduleManager.getModule("Font").isEnabled()) {
            fontRenderer.drawString(text, x, y, color);
        } else {
            FontUtil.mc.fontRenderer.drawString(text, (int)x, (int)y, color);
        }
    }

    public static void drawCFontText(String text, float x, float y, int color) {
        if (Font.instance.shadow.getValue().booleanValue()) {
            fontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            fontRenderer.drawString(text, x, y, color);
        }
    }

    public static float getCFontTextWidth(String text) {
        return fontRenderer.getWidth(text);
    }
}

