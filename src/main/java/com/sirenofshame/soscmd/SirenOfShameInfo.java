package com.sirenofshame.soscmd;

import java.time.Duration;

public class SirenOfShameInfo {
    /// <summary>
    /// The firmware version of the connected device.
    /// </summary>
    public short FirmwareVersion;

    /// <summary>
    /// The type of hardware of the connected device. See <see cref="HardwareType"/>.
    /// </summary>
    public HardwareType HardwareType;

    /// <summary>
    /// The hardware version of the connected device. This value will not change unless you buy new hardware.
    /// </summary>
    public byte HardwareVersion;

    /// <summary>
    /// The current audio mode. <see cref="AudioModeOff"/> if the audio is off. >=1 if the audio is currently playing.
    /// </summary>
    public byte AudioMode;

    /// <summary>
    /// The time remaining on the current audio output.
    /// </summary>
    public Duration AudioPlayDuration;

    /// <summary>
    /// The current LED mode. <see cref="LedModeOff"/> if the LEDs are off. <see cref="LedModeManual"/> if the LEDs are being
    /// controlled manually. >=2 if the LEDs are currently playing a pattern.
    /// </summary>
    public byte LedMode;

    /// <summary>
    /// The time remaining on the current LED output.
    /// </summary>
    public Duration LedPlayDuration;

    /// <summary>
    /// If this is a <see cref="HardwareType"/>.Pro model this will return the number of bytes to store additional LED and audio
    /// patterns. If this is a <see cref="HardwareType"/>.Standard model this will return 0.
    /// </summary>
    public int ExternalMemorySize;

    public SirenOfShameInfo(byte[] infoPacket)
    {
        FirmwareVersion = (short)((infoPacket[1] * 256) + infoPacket[0]);
        HardwareType = infoPacket[2] == 1 ? HardwareType.Standard : HardwareType.Pro;
        HardwareVersion = infoPacket[3];
        ExternalMemorySize = infoPacket[4] + infoPacket[5] << 8 + infoPacket[6] << 16 + infoPacket[7] << 24;
        AudioMode = infoPacket[8];
        short audioPlayDuration = bytesToShort(infoPacket[10], infoPacket[9]);
        AudioPlayDuration = Duration.ofMillis(audioPlayDuration * 100);
        LedMode = infoPacket[11];
        short ledPlayDuration = bytesToShort(infoPacket[13], infoPacket[12]);
        LedPlayDuration = Duration.ofMillis(ledPlayDuration * 100);
    }

    private short bytesToShort(byte hi, byte low) {
        return (short) ((hi & 0xFF) << 8 | (low & 0xFF));
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\tFirmware Version: " + this.FirmwareVersion + System.lineSeparator());
        stringBuffer.append("\tHardware Type: " + this.HardwareType + System.lineSeparator());
        stringBuffer.append("\tHardware Version: " + this.HardwareVersion + System.lineSeparator());
        stringBuffer.append("\tAudio Mode: " + this.AudioMode + System.lineSeparator());
        stringBuffer.append("\tAudio Play Seconds Remaining: " + this.AudioPlayDuration.getSeconds() + System.lineSeparator());
        stringBuffer.append("\tLed Mode: " + this.LedMode + System.lineSeparator());
        stringBuffer.append("\tLed Play Seconds Remaining: " + this.LedPlayDuration.getSeconds() + System.lineSeparator());
        stringBuffer.append("\tExternal Memory Size: " + this.ExternalMemorySize + System.lineSeparator());
        return stringBuffer.toString();
    }
}
