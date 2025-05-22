package org.lsearch.LRequest.utils;

import org.lsearch.LRequest.models.User;

public class UserContextHolder {
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}
