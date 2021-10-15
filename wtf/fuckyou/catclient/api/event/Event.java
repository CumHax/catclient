/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.event;

public class Event {
    private boolean cancelled = false;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}

