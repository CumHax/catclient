/*
 * Decompiled with CFR 0.150.
 */
package wtf.fuckyou.catclient.api.friends;

import java.util.ArrayList;
import java.util.List;
import wtf.fuckyou.catclient.api.friends.Friend;

public class FriendManager {
    private final List<Friend> friends = new ArrayList<Friend>();

    public List<Friend> getFriends() {
        return this.friends;
    }

    public Friend getFriend(String name) {
        return this.friends.stream().filter(friend -> friend.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean isFriend(String name) {
        return this.getFriend(name) != null;
    }
}

