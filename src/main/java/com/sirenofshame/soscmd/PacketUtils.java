/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.sirenofshame.soscmd;

import java.util.Arrays;

public class PacketUtils {

    public static byte[] getControlMessage(SirenControlPacket packet) {
        byte[] message = new byte[38];
        int i = 0;
        message[i++] = packet.getReportId();
        message[i++] = packet.getControlByte1();
        message[i++] = packet.getAudioMode();
        message[i++] = packet.getLedMode();
        message[i++] = (byte)(packet.getAudioPlayDuration() & 0xff);
        message[i++] = (byte)((packet.getAudioPlayDuration()>> 8) & 0xff);
        message[i++] = (byte)(packet.getLedPlayDuration() & 0xff);
        message[i++] = (byte)((packet.getLedPlayDuration() >> 8) & 0xff);
        message[i++] = packet.getReadAudioIndex();
        message[i++] = packet.getReadLedIndex();
        message[i++] = packet.getManualLeds0();
        message[i++] = packet.getManualLeds1();
        message[i++] = packet.getManualLeds2();
        message[i++] = packet.getManualLeds3();
        message[i++] = packet.getManualLeds4();
        return message;
    }

    public static SirenControlPacket getControlPacket(byte[] message) {
        if (message==null) {
            throw new NullPointerException("Null message");
        }
        if (message.length<15) {
            throw new IllegalArgumentException(String.format("Packet too short:%s", message.length));
        }
        SirenControlPacket packet = new SirenControlPacket();
        int i=0;
        packet.setReportId(message[i++]);
        packet.setControlByte1(message[i++]);
        packet.setAudioMode(message[i++]);
        packet.setLedMode(message[i++]);
        packet.setAudioPlayDuration((short) ((message[i++] << 8 | message[i++] & 0xFF) & 0xFFFF));
        packet.setLedPlayDuration((short)((message[i++] << 8 | message[i++] & 0xFF) & 0xFFFF));
        packet.setReadAudioIndex(message[i++]);
        packet.setReadLedIndex(message[i++]);
        packet.setManualLeds0(message[i++]);
        packet.setManualLeds1(message[i++]);
        packet.setManualLeds2(message[i++]);
        packet.setManualLeds3(message[i++]);
        packet.setManualLeds4(message[i++]);
        return packet;
    }

    public static byte[] parseHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String toHexString(byte[] message) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : message) {
            buffer.append(toHexString(b));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public static String toHexString(byte b) {
        return pad(Integer.toHexString(b & 0x000000ff));
    }

    public static String toHexString(short s) {
        return pad(Integer.toHexString(s & 0x0000ffff),4);
    }

    public static String pad(String s) {
        return pad(s,2);
    }
    public static String pad(String s, int length) {
        return pad(s, '0', length);
    }
    public static String pad(String s, char c, int length) {
        if (s==null || s.length()==0) {
            return "00";
        } else if (s.length()<length) {
            char[] carr = new char[length-s.length()];
            Arrays.fill(carr, c);
            return new String(carr)+s;
        }
        return s;
    }
}
