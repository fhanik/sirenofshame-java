package com.hanik.usb.siren;

import java.util.Arrays;

public class LedPattern {
    public LedPattern() {

    }

    public LedPattern(byte[] pattern) {
        Id = pattern[0];
        byte[] newArray = Arrays.copyOfRange(pattern, 1, pattern.length);
        Name = new String(newArray).trim();
    }

    public int Id;
    public String Name;
}
