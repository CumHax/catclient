/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.utils.chat;

public class CooldownUtil {
    private long time = 1L;

    public boolean passed(double ms) {
        return (double)(System.currentTimeMillis() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return this.time;
    }

    public void setTime() {
        this.time = this.time;
    }
}

