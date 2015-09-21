/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren.examples;

import com.hanik.usb.siren.JavaxSiren;
import com.hanik.usb.siren.PacketUtils;
import com.hanik.usb.siren.SirenControlPacket;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import java.util.HashMap;
import java.util.Map;

public class TestSoundsAndLeds {
    public static void main(String[] arg) throws UsbException, InterruptedException {
        Map<String,String> examplesBits = new HashMap<>();
        examplesBits.put("lights.blink",       "01ffff02fffffeffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("lights.fade",        "01ffff03fffffeffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("lights.chase",       "01ffff04fffffeffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("lights.fade-chase",  "01ffff05fffffeffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("audio.sad-trombone", "01ff01fffeffffffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("audio.ding",         "01ff02fffeffffffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("audio.plunk",        "01ff03fffeffffffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("stop.packet.1",      "01ff00ff0000ffffffffffffffffff0000000000000000000000000000000000000000000000");
        examplesBits.put("stop.packet.2",      "01ffff00ffff0000ffffffffffffff0000000000000000000000000000000000000000000000");
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
            JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(sirenControlPacket));
            Thread.sleep(5000);
            JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(stop1));
            JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(stop2));
        }
        JavaxSiren.release(siren);
    }
}
