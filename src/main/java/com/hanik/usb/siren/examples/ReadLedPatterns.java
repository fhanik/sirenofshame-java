/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren.examples;

import com.hanik.usb.siren.AudioPattern;
import com.hanik.usb.siren.JavaxSiren;
import com.hanik.usb.siren.PacketUtils;
import com.hanik.usb.siren.SirenControlPacket;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import java.util.ArrayList;
import java.util.List;

public class ReadLedPatterns {
    public static void main(String[] arg) throws UsbException, InterruptedException {
        UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
        UsbDevice siren = JavaxSiren.findSiren(hub);
        JavaxSiren.claimAndOpenSiren(siren);

        List<AudioPattern> audioPatterns = getAudioPatterns(siren);
        for (AudioPattern audioPattern : audioPatterns) {
            System.out.println("\t" + audioPattern.Id + " - " + audioPattern.Name);
        }
        JavaxSiren.release(siren);
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
        JavaxSiren.getInputReport(siren, PacketUtils.getControlMessage(sirenControlPacket), readData, (byte)0x03);
        return new AudioPattern(readData);
    }
}
