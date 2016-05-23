package com.hanik.usb.siren;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SirenOfShameDevice {
    public static final byte REPORTID_IN_INFO = (byte) 0x01;
    public static final byte REPORTID_IN_READAUDIOPACKET = (byte) 0x03;
    public static final byte REPORTID_IN_READLEDPACKET = (byte) 0x04;
    private static final byte LED_MODE_MANUAL = (byte) 0x01;
    private static final short DURATION_FOREVER = (short)0xfffe;
    private static final int PACKET_SIZE = 1 + 37; // report id + packet length

    UsbDevice _siren;

    public boolean tryConnect() {
        try {
            UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
            _siren = JavaxSiren.findSirenOrDefault(hub);
            if (_siren == null) return false;
            JavaxSiren.claimAndOpenSiren(_siren);
            return true;
        } catch (UsbException ex) {
            return false;
        }
    }

    public void disconnect() {
        try {
            JavaxSiren.release(_siren);
        } catch (UsbException ex) {
            System.out.println(ex);
        }
        _siren = null;
    }

    public List<AudioPattern> getAudioPatterns() throws UsbException {
        ArrayList<AudioPattern> audioPatterns = new ArrayList<AudioPattern>();
        int id = 0;
        while (true) {
            AudioPattern audioPattern = getAudioPattern(_siren, id);
            if (audioPattern.Id == -1) break;
            audioPatterns.add(audioPattern);
            id++;
        }
        return audioPatterns;
    }

    public List<LedPattern> getLedPatterns() throws UsbException {
        ArrayList<LedPattern> ledPatterns = new ArrayList<LedPattern>();
        int id = 0;
        while (true) {
            LedPattern ledPattern = getLedPattern(_siren, id);
            if (ledPattern.Id == -1) break;
            ledPatterns.add(ledPattern);
            id++;
        }
        return ledPatterns;
    }

    private LedPattern getLedPattern(UsbDevice siren, int index) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setReadLedIndex((byte) index);
        sirenControlPacket.setName("read.led.0");
        byte[] readData = new byte[PACKET_SIZE];
        JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(sirenControlPacket));
        JavaxSiren.getInputReport(siren, readData, REPORTID_IN_READLEDPACKET);
        return new LedPattern(readData);
    }

    private AudioPattern getAudioPattern(UsbDevice siren, int index) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setReadAudioIndex((byte) index);
        sirenControlPacket.setName("read.led.0");
        byte[] readData = new byte[PACKET_SIZE];
        JavaxSiren.sendMessage(siren, PacketUtils.getControlMessage(sirenControlPacket));
        JavaxSiren.getInputReport(siren, readData, REPORTID_IN_READAUDIOPACKET);
        return new AudioPattern(readData);
    }

    public SirenOfShameInfo readDeviceInfo() throws UsbException {
        byte[] readData = new byte[PACKET_SIZE];
        JavaxSiren.getInputReport(_siren, readData, REPORTID_IN_INFO);
        SirenOfShameInfo sirenOfShameInfo = new SirenOfShameInfo(readData);
        return sirenOfShameInfo;
    }

    public void manualControl(ManualControlData manualControlData) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setLedMode(LED_MODE_MANUAL);
        sirenControlPacket.setManualLeds0(manualControlData.Led0);
        sirenControlPacket.setManualLeds1(manualControlData.Led1);
        sirenControlPacket.setManualLeds2(manualControlData.Led2);
        sirenControlPacket.setManualLeds3(manualControlData.Led3);
        sirenControlPacket.setManualLeds4(manualControlData.Led4);
        JavaxSiren.sendMessage(_siren, PacketUtils.getControlMessage(sirenControlPacket));
    }

    public void playLightPattern(LedPattern ledPattern, Duration duration) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        if (ledPattern == null) {
            sirenControlPacket.setLedMode((byte)0x00);
            sirenControlPacket.setLedPlayDuration((short)0x0000);
        } else {
            sirenControlPacket.setLedMode((byte) ledPattern.Id);
            short durationBytes = calculateDurationFromDuration(duration);
            sirenControlPacket.setLedPlayDuration(durationBytes);
        }
        JavaxSiren.sendMessage(_siren, PacketUtils.getControlMessage(sirenControlPacket));
    }

    public void playAudioPattern(AudioPattern audioPattern, Duration duration) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        if (audioPattern == null) {
            sirenControlPacket.setAudioMode((byte)0x00);
            sirenControlPacket.setAudioPlayDuration((short)0x0000);
        } else {
            sirenControlPacket.setAudioMode((byte) audioPattern.Id);
            short durationBytes = calculateDurationFromDuration(duration);
            sirenControlPacket.setAudioPlayDuration(durationBytes);
        }
        JavaxSiren.sendMessage(_siren, PacketUtils.getControlMessage(sirenControlPacket));
    }

    public void stopAudioPattern() throws UsbException {
        playAudioPattern(null, null);
    }

    public void stopLightPattern() throws UsbException {
        playLightPattern(null, null);
    }

    private short calculateDurationFromDuration(Duration duration) {
        if (duration == null) {
            return DURATION_FOREVER;
        }
        long result = (duration.getSeconds() * 10);
        if (result > (Short.MAX_VALUE - 10)) {
            return DURATION_FOREVER;
        }
        long unsignedShort = result - 65536;
        return (short)unsignedShort;
    }
}
