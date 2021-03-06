/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren;

/**
 * Created by fhanik on 9/11/15.
 */
public class SirenInfoPacket {
    public short version;
    public byte hardwareType;
    public byte hardwareVersion;
    public int externalMemorySize;
    public byte audioMode;
    public short audioPlayDuration;
    public byte ledMode;
    public short ledPlayDuration;
}
