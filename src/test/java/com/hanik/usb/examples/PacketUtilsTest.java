package com.hanik.usb.examples;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by fhanik on 9/11/15.
 */
public class PacketUtilsTest {


    @Test
    public void testGetControlPacket() throws Exception {
        SirenControlPacket packet = new SirenControlPacket();
        byte[] message = PacketUtils.getControlMessage(packet);
        SirenControlPacket packet2 = PacketUtils.getControlPacket(message);
        Assert.assertEquals(packet, packet2);
    }

    @Test
    public void testGetControlPacket2() throws Exception {
        SirenControlPacket packet = new SirenControlPacket(
            (byte)1,
            (byte)24,
            (byte)34,
            (byte)45,
            (short)256,
            (short)567,
            (byte)78,
            (byte)89,
            (byte)99,
            (byte)101,
            (byte)111,
            (byte)11,
            (byte)5);

        byte[] message = PacketUtils.getControlMessage(packet);
        SirenControlPacket packet2 = PacketUtils.getControlPacket(message);
        Assert.assertEquals(packet, packet2);
    }
}