/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.siren;


import javax.usb.UsbConfiguration;
import javax.usb.UsbConst;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbEndpoint;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbInterface;
import javax.usb.UsbPipe;
import java.util.List;

public class JavaxSiren {

    public static final short VENDOR_ID = 0x16d0;
    public static final short PRODUCT_ID = 0x0646;


    public static UsbDevice findSirenOrDefault(UsbHub hub) throws UsbException {
        return findSiren(hub, VENDOR_ID, PRODUCT_ID);
    }
    public static UsbDevice findSiren(UsbHub hub) throws UsbException {
        UsbDevice siren = findSiren(hub, VENDOR_ID, PRODUCT_ID);
        if (siren==null) {
            throw new UsbDisconnectedException(String.format("Device not found [vendorId:%s, productId:%s", VENDOR_ID, PRODUCT_ID));
        }
        return siren;
    }
    protected static UsbDevice findSiren(UsbHub hub, int vendorId, int productId) throws UsbException {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()) {
            if (device.isUsbHub()) {
                UsbDevice siren = findSiren((UsbHub) device, vendorId, productId);
                if (siren != null) return siren;
            } else {
                UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
                if (desc.idVendor() == vendorId &&
                    desc.idProduct() == productId) return device;
            }
        }
        return null;
    }


    public static void main(final String[] args) throws Exception {
        UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
        UsbDevice siren = findSiren(hub);
        claimAndOpenSiren(siren);
        SirenControlPacket packet = getBlinkingSiren();
        packet.setAudioMode(SirenConstants.AUDIO_MODE_INTERNAL_START);
        sendMessage(siren, PacketUtils.getControlMessage(packet));
        Thread.sleep(5000);
        packet.setLedMode(SirenConstants.LED_MODE_OFF);
        packet.setAudioMode(SirenConstants.AUDIO_MODE_OFF);
        sendMessage(siren, PacketUtils.getControlMessage(packet));
    }

    public static void claimAndOpenSiren(UsbDevice siren) throws UsbException {
        if (!OS.isLinux()) {
            return;
        }
        // Claim the interface
        UsbConfiguration configuration = siren.getUsbConfiguration((byte) 1);
        UsbInterface iface = configuration.getUsbInterface(SirenConstants.INTERFACE_NUMBER);
        iface.claim(usbInterface -> true);

        //open write pipe
        for (UsbEndpoint usbEndpoint : (List<UsbEndpoint>) iface.getUsbEndpoints()) {
            if (!usbEndpoint.getUsbPipe().isOpen()) {
                usbEndpoint.getUsbPipe().open();
            }
        }
    }

    public static void release(UsbDevice siren) throws UsbException {
        if (!OS.isLinux()) {
            return;
        }
        // Claim the interface
        UsbConfiguration configuration = siren.getUsbConfiguration((byte) 1);
        UsbInterface iface = configuration.getUsbInterface(SirenConstants.INTERFACE_NUMBER);
        //open write pipe
        for (UsbEndpoint usbEndpoint : (List<UsbEndpoint>) iface.getUsbEndpoints()) {
            if (usbEndpoint.getUsbPipe().isOpen()) {
                usbEndpoint.getUsbPipe().close();
            }
        }
        iface.release();

    }

    protected static SirenControlPacket getBlinkingSiren() {
        SirenControlPacket packet = new SirenControlPacket();
        packet.setReportId(SirenConstants.USB_REPORTID_OUT_CONTROL);
        packet.setLedMode(SirenConstants.LED_MODE_INTERNAL_START);
        packet.setLedPlayDuration(SirenConstants.DURATION_FOREVER);
        return packet;
    }

    public static void sendMessage(UsbDevice device, byte[] message) throws UsbException {
        UsbControlIrp irp = device.createUsbControlIrp(
            (byte) (UsbConst.REQUESTTYPE_TYPE_CLASS | UsbConst.REQUEST_CLEAR_FEATURE),
            UsbConst.REQUEST_SET_CONFIGURATION,
            (short) 0x0201,
            (short) 0
        );
        irp.setData(message);
        device.syncSubmit(irp);
    }

    public static int getInputReport(UsbDevice siren, byte[] sendMessage, byte[] readMessage, byte controlByte) throws UsbException {
        sendMessage(siren, sendMessage);
        UsbControlIrp irp = siren.createUsbControlIrp(
            (byte) (UsbConst.ENDPOINT_DIRECTION_IN | UsbConst.REQUESTTYPE_TYPE_CLASS | UsbConst.REQUESTTYPE_RECIPIENT_INTERFACE),
            UsbConst.REQUEST_CLEAR_FEATURE,
            (short) ((0x01 << 8 ) | controlByte),
            (short) 0
        );
        readMessage[0] = 1;
        irp.setData(readMessage);
        siren.syncSubmit(irp);
        return 0;
    }

}
