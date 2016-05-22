/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren;

import javax.usb.UsbException;
import java.util.List;

public class Program {
    public static void main(String[] arg) throws UsbException, InterruptedException {
        if (arg.length == 0) {
            System.out.println("usage: soscmd [-al]");
            System.out.println("\t-a\tlist audio patterns");
            System.out.println("\t-l\tlist led patterns");
        } else {
            String arg1 = arg[0];
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
            sirenOfShameDevice.disconnect();
        }
    }


}