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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadLedPatterns {
    public static void main(String[] arg) throws UsbException, InterruptedException {
        UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
        UsbDevice siren = JavaxSiren.findSiren(hub);
        JavaxSiren.claimAndOpenSiren(siren);

        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setReadAudioIndex((byte)1);
        sirenControlPacket.setName("read.led.0");
        System.out.println(sirenControlPacket);
        byte[] readData = new byte[21];
        JavaxSiren.readLedPattern(siren, PacketUtils.getControlMessage(sirenControlPacket), readData);
        AudioPattern audioPattern = new AudioPattern(readData);
        System.out.println("Read Led Pattern Data\n\t"+ audioPattern.Id + " - " + audioPattern.Name);
        JavaxSiren.release(siren);
    }
}
