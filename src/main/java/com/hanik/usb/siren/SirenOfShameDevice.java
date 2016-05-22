package com.hanik.usb.siren;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import java.util.ArrayList;
import java.util.List;

public class SirenOfShameDevice {
    public static final byte REPORTID_IN_INFO = (byte) 0x01;
    public static final byte REPORTID_IN_READAUDIOPACKET = (byte) 0x03;
    public static final byte REPORTID_IN_READLEDPACKET = (byte) 0x04;

    UsbDevice _siren;

    public boolean tryConnect() {
        try {
            UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
            _siren = JavaxSiren.findSiren(hub);
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
        byte[] readData = new byte[21];
        JavaxSiren.getInputReport(siren, PacketUtils.getControlMessage(sirenControlPacket), readData, REPORTID_IN_READLEDPACKET);
        return new LedPattern(readData);
    }

    private AudioPattern getAudioPattern(UsbDevice siren, int index) throws UsbException {
        SirenControlPacket sirenControlPacket = new SirenControlPacket();
        sirenControlPacket.setReadAudioIndex((byte) index);
        sirenControlPacket.setName("read.led.0");
        byte[] readData = new byte[21];
        JavaxSiren.getInputReport(siren, PacketUtils.getControlMessage(sirenControlPacket), readData, REPORTID_IN_READAUDIOPACKET);
        return new AudioPattern(readData);
    }
}
