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
import java.util.Map;

public class ReadLedPatterns {
    public static void main(String[] arg) throws UsbException, InterruptedException {
        Map<String,String> examplesBits = new HashMap<>();
        examplesBits.put("read.led.0",    "01FFFFFFFFFFFFFFFF00FFFFFFFFFF0000000000000000000000000000000000000000000000");
        examplesBits.put("stop.packet.1", "01ff00ff0000ffffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("stop.packet.2", "01ffff00ffff0000ffffffffffffff0000000000000000000000000000000000000000000000");
        SirenControlPacket stop1 = PacketUtils.getControlPacket(PacketUtils.parseHexString(examplesBits.get("stop.packet.1")));
        SirenControlPacket stop2 = PacketUtils.getControlPacket(PacketUtils.parseHexString(examplesBits.get("stop.packet.2")));
        UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
        UsbDevice siren = JavaxSiren.findSiren(hub);
        JavaxSiren.claimAndOpenSiren(siren);
        for (Map.Entry<String,String> entry : examplesBits.entrySet()) {
            if(entry.getKey().startsWith("stop")) {
                continue;
            }
            byte[] packet = PacketUtils.parseHexString(entry.getValue());
            SirenControlPacket sirenControlPacket = PacketUtils.getControlPacket(packet);
            sirenControlPacket.setName(entry.getKey());
            System.out.println(sirenControlPacket);
            byte[] readData = new byte[21];
            JavaxSiren.readLedPattern(siren, PacketUtils.getControlMessage(sirenControlPacket), readData);
            AudioPattern audioPattern = new AudioPattern(readData);
            System.out.println("Read Led Pattern Data\n\t"+ audioPattern.Id + " - " + audioPattern.Name);
            Thread.sleep(500);
            JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(stop1));
            JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(stop2));
        }
        JavaxSiren.release(siren);
    }

}
