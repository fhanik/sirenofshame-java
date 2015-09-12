package com.hanik.usb.examples;

import java.nio.ByteBuffer;

/**
 * Created by fhanik on 9/11/15.
 */
public class PacketUtils {

    public static byte[] getControlMessage(SirenControlPacket packet) {
        byte[] message = new byte[38];
        int i = 0;
        message[i++] = packet.getReportId();
        message[i++] = packet.getControlByte1();
        message[i++] = packet.getAudioMode();
        message[i++] = packet.getLedMode();
        message[i++] = (byte)((packet.getAudioPlayDuration()>> 8) & 0xff);
        message[i++] = (byte)(packet.getAudioPlayDuration() & 0xff);
        message[i++] = (byte)((packet.getLedPlayDuration() >> 8) & 0xff);
        message[i++] = (byte)(packet.getLedPlayDuration() & 0xff);
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
        packet.setAudioPlayDuration((short)((message[i++] << 8 | message[i++] & 0xFF) & 0xFFFF));
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

    public static String toDescriptiveString(byte[] message) {
        return null;
    }
    public static String toHexString(byte[] message) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : message) {
            buffer.append(pad(Integer.toHexString(b & 0x000000ff)));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public static String pad(String s) {
        if (s==null || s.length()==0) {
            return "00";
        } else if (s.length()==1) {
            return "0"+s;
        }
        return s;
    }
}
