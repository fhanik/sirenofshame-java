/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren;

public class SirenConstants {
    public static final byte USB_REPORTID_OUT_CONTROL = 1;
    public static final byte USB_REPORTID_OUT_DATA_UPLOAD = 2;
    public static final byte USB_REPORTID_OUT_LED_CONTROL = 3;

    public static final byte USB_REPORTID_IN_INFO = 1;
    public static final byte USB_REPORTID_IN_READ_AUDIO = 3;
    public static final byte USB_REPORTID_IN_READ_LED = 4;

    public static final int  USB_NAME_SIZE = 20;

    public static final byte CONTROL_BYTE1_IGNORE = (byte) 0xff;
    public static final byte CONTROL_BYTE1_FIRMWARE_UPGRADE = 0x01;
    public static final byte CONTROL_BYTE1_ECHO_ON = 0x02;
    public static final byte CONTROL_BYTE1_DEBUG = 0x04;


    // audio modes
    public static final byte AUDIO_MODE_OFF = 0;
    public static final byte AUDIO_MODE_INTERNAL_START = 1;

    // led modes
    public static final byte LED_MODE_OFF = 0;
    public static final byte LED_MODE_MANUAL = 1;
    public static final byte LED_MODE_INTERNAL_START = 2;

    public static final short DURATION_FOREVER = (short)0xfeff;

    // play duration
    public static final short PLAY_DURATION_FOREVER = (short) 0xfffe;

    // hardware type
    public static final byte HARDWARE_TYPE_STANDARD = 1;
    public static final byte HARDWARE_TYPE_PRO = 2;

    public static final int  USB_DATA_SIZE = 32;

    public static final byte  INTERFACE_NUMBER = 0;

}
