/*
 * Copyright (c) 2015.
 * Filip Hanik
 */

package com.hanik.usb.examples;


import javax.usb.UsbConfiguration;
import javax.usb.UsbConst;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbEndpoint;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbInterface;
import java.util.List;

public class JavaxSiren {

    public static UsbDevice findSiren(UsbHub hub, int vendorId, int productId) {
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
        short vendorId = 0x16d0;
        short productId = 0x0646;
        UsbHub hub = UsbHostManager.getUsbServices().getRootUsbHub();
        UsbDevice siren = findSiren(hub, vendorId, productId);

        if (siren == null) {
            System.err.println("Missile launcher not found.");
            System.exit(1);
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


        SirenControlPacket packet = getBlinkingSiren();
        packet.setAudioMode(SirenConstants.AUDIO_MODE_INTERNAL_START);
        sendMessage(siren, PacketUtils.getControlMessage(packet));
        Thread.sleep(5000);
        packet.setLedMode(SirenConstants.LED_MODE_OFF);
        packet.setAudioMode(SirenConstants.AUDIO_MODE_OFF);
        sendMessage(siren, PacketUtils.getControlMessage(packet));
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
        System.out.println(PacketUtils.toHexString(message));
        device.syncSubmit(irp);
    }

}
