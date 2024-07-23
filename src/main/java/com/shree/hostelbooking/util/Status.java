package com.shree.hostelbooking.util;

public enum Status {
    BOOKED("Booked"),
    CANCELED("Canceled"),

    COMPLETE("Complete");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
