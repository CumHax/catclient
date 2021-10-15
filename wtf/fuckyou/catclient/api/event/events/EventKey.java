/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.event.events;

import wtf.fuckyou.catclient.api.event.Event;

public class EventKey
extends Event {
    private final int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}

