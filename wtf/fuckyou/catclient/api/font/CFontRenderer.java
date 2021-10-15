/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.StringUtils
 *  org.lwjgl.opengl.GL11
 *  org.newdawn.slick.Color
 *  org.newdawn.slick.SlickException
 *  org.newdawn.slick.UnicodeFont
 *  org.newdawn.slick.font.effects.ColorEffect
 */
package wtf.fuckyou.catclient.api.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import wtf.fuckyou.catclient.api.wrapper.Wrapper;

public final class CFontRenderer
implements Wrapper {
    public static final char COLOR_CHAR = '\u00a7';
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("\u00a7[0123456789abcdefklmnor]");
    private final int[] colorCodes = new int[]{0, 170, 43520, 43690, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};
    private final Map<String, Float> cachedStringWidth = new HashMap<String, Float>();
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor;
    private final float size;

    public CFontRenderer(float fontSize) {
        this.size = fontSize;
        ScaledResolution resolution = new ScaledResolution(mc);
        try {
            this.prevScaleFactor = resolution.getScaleFactor();
            this.unicodeFont = new UnicodeFont(CFontRenderer.getFont().deriveFont(fontSize * (float)this.prevScaleFactor / 2.0f));
            this.unicodeFont.addAsciiGlyphs();
            this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            this.unicodeFont.loadGlyphs();
        }
        catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
            this.prevScaleFactor = resolution.getScaleFactor();
            try {
                this.unicodeFont = new UnicodeFont(CFontRenderer.getFont().deriveFont(fontSize * (float)this.prevScaleFactor / 2.0f));
                this.unicodeFont.addAsciiGlyphs();
                this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
                this.unicodeFont.loadGlyphs();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        this.antiAliasingFactor = resolution.getScaleFactor();
    }

    public static Font getFont() throws IOException, FontFormatException {
        return Font.createFont(0, CFontRenderer.class.getResourceAsStream("/assets/fonts/Verdana.ttf"));
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }
        ScaledResolution resolution = new ScaledResolution(mc);
        try {
            if (resolution.getScaleFactor() != this.prevScaleFactor) {
                this.prevScaleFactor = resolution.getScaleFactor();
                this.unicodeFont = new UnicodeFont(CFontRenderer.getFont().deriveFont(this.size * (float)this.prevScaleFactor / 2.0f));
                this.unicodeFont.addAsciiGlyphs();
                this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
                this.unicodeFont.loadGlyphs();
            }
        }
        catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }
        this.antiAliasingFactor = resolution.getScaleFactor();
        GL11.glPushMatrix();
        GlStateManager.scale((float)(1.0f / this.antiAliasingFactor), (float)(1.0f / this.antiAliasingFactor), (float)(1.0f / this.antiAliasingFactor));
        y *= this.antiAliasingFactor;
        float originalX = x *= this.antiAliasingFactor;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        int currentColor = color;
        char[] characters = text.toCharArray();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.blendFunc((int)770, (int)771);
        String[] parts = COLOR_CODE_PATTERN.split(text);
        int index = 0;
        for (String s : parts) {
            char colorCode;
            for (String s2 : s.split("\n")) {
                for (String s3 : s2.split("\r")) {
                    this.unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(currentColor));
                    x += (float)this.unicodeFont.getWidth(s3);
                    if ((index += s3.length()) >= characters.length || characters[index] != '\r') continue;
                    x = originalX;
                    ++index;
                }
                if (index >= characters.length || characters[index] != '\n') continue;
                x = originalX;
                y += this.getHeight(s2) * 2.0f;
                ++index;
            }
            if (index >= characters.length || (colorCode = characters[index]) != '\u00a7') continue;
            char colorChar = characters[index + 1];
            int codeIndex = "0123456789abcdef".indexOf(colorChar);
            if (codeIndex < 0) {
                if (colorChar == 'r') {
                    currentColor = color;
                }
            } else {
                currentColor = this.colorCodes[codeIndex];
            }
            index += 2;
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.bindTexture((int)0);
        GlStateManager.popMatrix();
        return (int)this.getWidth(text);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        if (text == null || text == "") {
            return;
        }
        this.drawString(StringUtils.stripControlCodes((String)text), x + 0.5f, y + 0.5f, 0);
        this.drawString(text, x, y, color);
    }

    public float getWidth(String text) {
        if (this.cachedStringWidth.size() > 1000) {
            this.cachedStringWidth.clear();
        }
        return this.cachedStringWidth.computeIfAbsent(text, e -> Float.valueOf((float)this.unicodeFont.getWidth(CFontRenderer.stripColor(text)) / this.antiAliasingFactor)).floatValue();
    }

    public float getHeight(String s) {
        return (float)this.unicodeFont.getHeight(s) / 2.0f;
    }

    public float getStringWidth(String text) {
        return this.unicodeFont.getWidth(CFontRenderer.stripColor(text)) / 2;
    }

    public float getStringHeight(String text) {
        return this.getHeight(text);
    }

    public static String stripColor(String input) {
        return input == null ? null : Pattern.compile("(?i)\u00a7[0-9A-FK-OR]").matcher(input).replaceAll("");
    }
}

