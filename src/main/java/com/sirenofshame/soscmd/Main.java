package com.sirenofshame.soscmd;

import javax.usb.UsbException;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UsbException, InterruptedException {
        if (args.length == 0) {
            System.out.println("usage: soscmd -[i|ra|rl|m|l|a|sa|sl]");
            System.out.println("\t-i  Retrieve info about device");
            System.out.println("\t-ra List audio patterns");
            System.out.println("\t-rl List led patterns");
            System.out.println("\t-m  Manually set LED's");
            System.out.println("\t-l  Play an LED pattern");
            System.out.println("\t-a  Play an audio pattern");
            System.out.println("\t-sa Stop audio");
            System.out.println("\t-sl Stop LED's");
            return;
        }

        String arg1 = args[0];
        SirenOfShameDevice sirenOfShameDevice = new SirenOfShameDevice();
        boolean isConnected = sirenOfShameDevice.tryConnect();
        if (!isConnected) {
            System.out.println("No siren of shame device found");
            return;
        }
        if (arg1.startsWith("-ra")) {
            System.out.println("Audio Patterns:");
            List<AudioPattern> audioPatterns = sirenOfShameDevice.getAudioPatterns();
            for (AudioPattern audioPattern : audioPatterns) {
                System.out.println("\t" + audioPattern.Id + " - " + audioPattern.Name);
            }
        }
        if (arg1.startsWith("-rl")) {
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
        if (arg1.startsWith("-l")) {
            String errorMessage = "Please specify a pattern id (0-255) and duration in seconds (0 = off, 999 = forever)";
            if (args.length < 3) {
                System.out.println(errorMessage);
                return;
            }
            LedPattern ledPattern = new LedPattern();
            ledPattern.Id = parseByte(args, 1);
            int durationInSeconds = Integer.parseInt(args[2]);
            Duration duration = Duration.ofSeconds(durationInSeconds);
            sirenOfShameDevice.playLightPattern(ledPattern, duration);
        }
        if (arg1.startsWith("-a")) {
            String errorMessage = "Please specify a pattern id (0-255) and duration in seconds (0 = off, 999 = forever)";
            if (args.length < 3) {
                System.out.println(errorMessage);
                return;
            }
            AudioPattern audioPattern = new AudioPattern();
            audioPattern.Id = parseByte(args, 1);
            int durationInSeconds = Integer.parseInt(args[2]);
            Duration duration = Duration.ofSeconds(durationInSeconds);
            sirenOfShameDevice.playAudioPattern(audioPattern, duration);
        }
        if (arg1.startsWith("-sa")) {
            sirenOfShameDevice.stopAudioPattern();
        }
        if (arg1.startsWith("-sl")) {
            sirenOfShameDevice.stopLightPattern();
        }
        if (arg1.startsWith("-i")) {
            SirenOfShameInfo sirenOfShameInfo = sirenOfShameDevice.readDeviceInfo();
            System.out.println(sirenOfShameInfo);
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
