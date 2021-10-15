/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.event.events;

import wtf.fuckyou.catclient.api.event.Event;

public class EventChatMessage
extends Event {
    private final String message;

    public EventChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

