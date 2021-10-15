/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.utils.chat;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathsUtil {
    public static double roundNumber(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double roundAvoid(double number, int places) {
        double scale = Math.pow(10.0, places);
        return (double)Math.round(number * scale) / scale;
    }
}

