/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren;

import javax.usb.UsbException;
import java.util.List;

public class Program {
    public static void main(String[] args) throws UsbException, InterruptedException {
        if (args.length == 0) {
            System.out.println("usage: soscmd [-al]");
            System.out.println("\t-a\tlist audio patterns");
            System.out.println("\t-l\tlist led patterns");
            return;
        }

        String arg1 = args[0];
        SirenOfShameDevice sirenOfShameDevice = new SirenOfShameDevice();
        boolean isConnected = sirenOfShameDevice.tryConnect();
        if (!isConnected) {
            System.out.println("No siren of shame device found");
            return;
        }
        if (arg1.startsWith("-a")) {
            System.out.println("Audio Patterns:");
            List<AudioPattern> audioPatterns = sirenOfShameDevice.getAudioPatterns();
            for (AudioPattern audioPattern : audioPatterns) {
                System.out.println("\t" + audioPattern.Id + " - " + audioPattern.Name);
            }
        }
        if (arg1.startsWith("-l")) {
            System.out.println("LED Patterns:");
            List<LedPattern> ledPatterns = sirenOfShameDevice.getLedPatterns();
            for (LedPattern ledPattern : ledPatterns) {
                System.out.println("\t" + ledPattern.Id + " - " + ledPattern.Name);
            }
        }
        if (arg1.startsWith("-m")) {
            String errorMessage = "Please specify a set of 5 bytes (0-255) e.g. '-m 128 0 255 0 0' will set the 1st strip to half brightness and the 3rd strip to full brightness and all others to off";
            if (args.length < 6) {
                System.out.println(errorMessage);
                return;
            }
            ManualControlData manualControlData = getManualControlFromArgs(args);
            if (manualControlData == null) {
                System.out.println(errorMessage);
                return;
            }
            sirenOfShameDevice.manualControl(manualControlData);
        }
        sirenOfShameDevice.disconnect();
    }

    private static ManualControlData getManualControlFromArgs(String[] args) {
        ManualControlData manualControlData = new ManualControlData();
        try {
            manualControlData.Led0 = parseByte(args, 1);
            manualControlData.Led1 = parseByte(args, 2);
            manualControlData.Led2 = parseByte(args, 3);
            manualControlData.Led3 = parseByte(args, 4);
            manualControlData.Led4 = parseByte(args, 5);
            manualControlData.Siren = false;
        } catch (NumberFormatException ex) {
            return null;
        }
        return manualControlData;
    }

    private static byte parseByte(String[] args, int position) {
        int i = Integer.parseInt(args[position]);
        return (byte)i;
    }
}
