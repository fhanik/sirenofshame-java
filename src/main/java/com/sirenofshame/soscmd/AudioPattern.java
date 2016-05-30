package com.sirenofshame.soscmd;

import java.lang.reflect.Array;
import java.util.Arrays;

public class AudioPattern {
    public AudioPattern() {

    }

    public AudioPattern(byte[] pattern) {
        Id = pattern[0];
        byte[] newArray = Arrays.copyOfRange(pattern, 1, pattern.length);
        Name = new String(newArray).trim();
    }

    public int Id;
    public String Name;
}
