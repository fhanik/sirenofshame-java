package com.hanik.usb.examples;

/**
 * Created by fhanik on 9/11/15.
 */
public class PacketUtils {

    public static byte[] getControlPacket(SirenControlPacket packet) {
        byte[] message = new byte[38];
        int i = 0;
        message[i++] = packet.reportId;
        message[i++] = packet.controlByte1;
        message[i++] = packet.audioMode;
        message[i++] = packet.ledMode;
        message[i++] = (byte)((packet.audioPlayDuration >> 8) & 0xff);
        message[i++] = (byte)(packet.audioPlayDuration & 0xff);
        message[i++] = (byte)((packet.ledPlayDuration >> 8) & 0xff);
        message[i++] = (byte)(packet.ledPlayDuration & 0xff);
        message[i++] = packet.readAudioIndex;
        message[i++] = packet.readLedIndex;
        message[i++] = packet.manualLeds0;
        message[i++] = packet.manualLeds1;
        message[i++] = packet.manualLeds2;
        message[i++] = packet.manualLeds3;
        message[i++] = packet.manualLeds4;
        return message;
    }

    public static SirenControlPacket initControlPacket() {
        final byte ff = (byte) 0xff;
        final byte ffff = (short) 0xffff;
        SirenControlPacket packet = new SirenControlPacket();
        packet.reportId = 0;
        packet.controlByte1 = ff;
        packet.audioMode = ff;
        packet.ledMode = ff;
        packet.audioPlayDuration = ffff;
        packet.ledPlayDuration = ffff;
        packet.readAudioIndex = ff;
        packet.readLedIndex = ff;
        packet.manualLeds0 = ff;
        packet.manualLeds1 = ff;
        packet.manualLeds2 = ff;
        packet.manualLeds3 = ff;
        packet.manualLeds4 = ff;
        return packet;
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
