package com.hanik.usb.examples;

/**
 * Created by fhanik on 9/11/15.
 */
public class SirenControlPacket {
    public byte reportId;
    //firmwareUpgrade : 1;
    //echoOn : 1;
    //debug : 1;
    public byte controlByte1;
    public byte audioMode;
    public byte ledMode;
    public short audioPlayDuration; // 1/10s
    public short ledPlayDuration;   // 1/10s
    public byte readAudioIndex;
    public byte readLedIndex;
    public byte manualLeds0;
    public byte manualLeds1;
    public byte manualLeds2;
    public byte manualLeds3;
    public byte manualLeds4;
}
