/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren;

import static com.hanik.usb.siren.PacketUtils.toHexString;

public class SirenControlPacket {
    public static final byte  FF = (byte) 0xff;
    public static final short FFFF = (short) 0xffff;

    private byte reportId = 0;
    private byte controlByte1 = FF;
    private byte audioMode = FF;
    private byte ledMode = FF;
    private short audioPlayDuration = FFFF;
    private short ledPlayDuration = FFFF;
    private byte readAudioIndex = FF;
    private byte readLedIndex = FF;
    private byte manualLeds0 = FF;
    private byte manualLeds1 = FF;
    private byte manualLeds2 = FF;
    private byte manualLeds3 = FF;
    private byte manualLeds4 = FF;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SirenControlPacket(byte reportId,
                              byte controlByte1,
                              byte audioMode,
                              byte ledMode,
                              short audioPlayDuration,
                              short ledPlayDuration,
                              byte readAudioIndex,
                              byte readLedIndex,
                              byte manualLeds0,
                              byte manualLeds1,
                              byte manualLeds2,
                              byte manualLeds3,
                              byte manualLeds4) {
        this.audioMode = audioMode;
        this.audioPlayDuration = audioPlayDuration;
        this.controlByte1 = controlByte1;
        this.ledMode = ledMode;
        this.ledPlayDuration = ledPlayDuration;
        this.manualLeds0 = manualLeds0;
        this.manualLeds1 = manualLeds1;
        this.manualLeds2 = manualLeds2;
        this.manualLeds3 = manualLeds3;
        this.manualLeds4 = manualLeds4;
        this.readAudioIndex = readAudioIndex;
        this.readLedIndex = readLedIndex;
        this.reportId = reportId;
    }

    public SirenControlPacket() {
    }

    public byte getAudioMode() {
        return audioMode;
    }

    public void setAudioMode(byte audioMode) {
        this.audioMode = audioMode;
    }

    public short getAudioPlayDuration() {
        return audioPlayDuration;
    }

    public void setAudioPlayDuration(short audioPlayDuration) {
        this.audioPlayDuration = audioPlayDuration;
    }

    public byte getControlByte1() {
        return controlByte1;
    }

    public void setControlByte1(byte controlByte1) {
        this.controlByte1 = controlByte1;
    }

    public byte getLedMode() {
        return ledMode;
    }

    public void setLedMode(byte ledMode) {
        this.ledMode = ledMode;
    }

    public short getLedPlayDuration() {
        return ledPlayDuration;
    }

    public void setLedPlayDuration(short ledPlayDuration) {
        this.ledPlayDuration = ledPlayDuration;
    }

    public byte getManualLeds0() {
        return manualLeds0;
    }

    public void setManualLeds0(byte manualLeds0) {
        this.manualLeds0 = manualLeds0;
    }

    public byte getManualLeds1() {
        return manualLeds1;
    }

    public void setManualLeds1(byte manualLeds1) {
        this.manualLeds1 = manualLeds1;
    }

    public byte getManualLeds2() {
        return manualLeds2;
    }

    public void setManualLeds2(byte manualLeds2) {
        this.manualLeds2 = manualLeds2;
    }

    public byte getManualLeds3() {
        return manualLeds3;
    }

    public void setManualLeds3(byte manualLeds3) {
        this.manualLeds3 = manualLeds3;
    }

    public byte getManualLeds4() {
        return manualLeds4;
    }

    public void setManualLeds4(byte manualLeds4) {
        this.manualLeds4 = manualLeds4;
    }

    public byte getReadAudioIndex() {
        return readAudioIndex;
    }

    public void setReadAudioIndex(byte readAudioIndex) {
        this.readAudioIndex = readAudioIndex;
    }

    public byte getReadLedIndex() {
        return readLedIndex;
    }

    public void setReadLedIndex(byte readLedIndex) {
        this.readLedIndex = readLedIndex;
    }

    public byte getReportId() {
        return reportId;
    }

    public void setReportId(byte reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SirenControlPacket)) return false;

        SirenControlPacket that = (SirenControlPacket) o;

        if (getReportId() != that.getReportId()) return false;
        if (getControlByte1() != that.getControlByte1()) return false;
        if (getAudioMode() != that.getAudioMode()) return false;
        if (getLedMode() != that.getLedMode()) return false;
        if (getAudioPlayDuration() != that.getAudioPlayDuration()) return false;
        if (getLedPlayDuration() != that.getLedPlayDuration()) return false;
        if (getReadAudioIndex() != that.getReadAudioIndex()) return false;
        if (getReadLedIndex() != that.getReadLedIndex()) return false;
        if (getManualLeds0() != that.getManualLeds0()) return false;
        if (getManualLeds1() != that.getManualLeds1()) return false;
        if (getManualLeds2() != that.getManualLeds2()) return false;
        if (getManualLeds3() != that.getManualLeds3()) return false;
        return getManualLeds4() == that.getManualLeds4();

    }

    @Override
    public int hashCode() {
        int result = (int) getReportId();
        result = 31 * result + (int) getControlByte1();
        result = 31 * result + (int) getAudioMode();
        result = 31 * result + (int) getLedMode();
        result = 31 * result + (int) getAudioPlayDuration();
        result = 31 * result + (int) getLedPlayDuration();
        result = 31 * result + (int) getReadAudioIndex();
        result = 31 * result + (int) getReadLedIndex();
        result = 31 * result + (int) getManualLeds0();
        result = 31 * result + (int) getManualLeds1();
        result = 31 * result + (int) getManualLeds2();
        result = 31 * result + (int) getManualLeds3();
        result = 31 * result + (int) getManualLeds4();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SirenControlPacket{name:").append(getName());
        sb.append("\n\treportId         =").append(toHexString(getReportId()));
        sb.append("\n\tcontrolByte1     =").append(toHexString(getControlByte1()));
        sb.append("\n\taudioMode        =").append(toHexString(getAudioMode()));
        sb.append("\n\tledMode          =").append(toHexString(getLedMode()));
        sb.append("\n\taudioPlayDuration=").append(toHexString(getAudioPlayDuration()));
        sb.append("\n\tledPlayDuration  =").append(toHexString(getLedPlayDuration()));
        sb.append("\n\treadAudioIndex   =").append(toHexString(getReadAudioIndex()));
        sb.append("\n\treadLedIndex     =").append(toHexString(getReadLedIndex()));
        sb.append("\n\tmanualLeds0      =").append(toHexString(getManualLeds0()));
        sb.append("\n\tmanualLeds1      =").append(toHexString(getManualLeds1()));
        sb.append("\n\tmanualLeds2      =").append(toHexString(getManualLeds2()));
        sb.append("\n\tmanualLeds3      =").append(toHexString(getManualLeds3()));
        sb.append("\n\tmanualLeds4      =").append(toHexString(getManualLeds4()));
        sb.append("\n}");
        return sb.toString();
    }
}
