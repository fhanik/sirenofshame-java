/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren.examples;

import com.hanik.usb.siren.*;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import java.util.ArrayList;
import java.util.List;

public class ReadLedPatterns {

    public static final byte REPORTID_IN_INFO = (byte) 0x01;
    public static final byte REPORTID_IN_READAUDIOPACKET = (byte) 0x03;
    public static final byte REPORTID_IN_READLEDPACKET = (byte) 0x04;

    public static void main(String[] arg) throws UsbException, InterruptedException {
        UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
        UsbDevice siren = JavaxSiren.findSiren(hub);
        JavaxSiren.claimAndOpenSiren(siren);

        System.out.println("Audio Patterns:");
        List<AudioPattern> audioPatterns = getAudioPatterns(siren);
        for (AudioPattern audioPattern : audioPatterns) {
            System.out.println("\t" + audioPattern.Id + " - " + audioPattern.Name);
        }

        System.out.println("LED Patterns:");
        List<LedPattern> ledPatterns = getLedPatterns(siren);
        for (LedPattern ledPattern : ledPatterns) {
            System.out.println("\t" + ledPattern.Id + " - " + ledPattern.Name);
        }
        JavaxSiren.release(siren);
    }

    private static List<LedPattern> getLedPatterns(UsbDevice siren) throws UsbException {
        ArrayList<LedPattern> ledPatterns = new ArrayList<LedPattern>();
        int id = 0;
        while (true) {
            LedPattern ledPattern = getLedPattern(siren, id);
            if (ledPattern.Id == -1) break;
            ledPatterns.add(ledPattern);
            id++;
        }
        return ledPatterns;
    }

    private static LedPattern getLedPattern(UsbDevice siren, int index) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setReadLedIndex((byte) index);
        sirenControlPacket.setName("read.led.0");
        byte[] readData = new byte[21];
        JavaxSiren.getInputReport(siren, PacketUtils.getControlMessage(sirenControlPacket), readData, REPORTID_IN_READLEDPACKET);
        return new LedPattern(readData);
    }

    private static List<AudioPattern> getAudioPatterns(UsbDevice siren) throws UsbException {
        ArrayList<AudioPattern> audioPatterns = new ArrayList<AudioPattern>();
        int id = 0;
        while (true) {
            AudioPattern audioPattern = getAudioPattern(siren, id);
            if (audioPattern.Id == -1) break;
            audioPatterns.add(audioPattern);
            id++;
        }
        return audioPatterns;
    }

    private static AudioPattern getAudioPattern(UsbDevice siren, int index) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setReadAudioIndex((byte) index);
        sirenControlPacket.setName("read.led.0");
        byte[] readData = new byte[21];
        JavaxSiren.getInputReport(siren, PacketUtils.getControlMessage(sirenControlPacket), readData, REPORTID_IN_READAUDIOPACKET);
        return new AudioPattern(readData);
    }
}
