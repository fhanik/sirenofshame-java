package com.sirenofshame.soscmd;

import org.junit.Assert;
import org.junit.Test;

public class PacketUtilsTest {


    @Test
    public void testGetControlPacket() throws Exception {
        SirenControlPacket packet = new SirenControlPacket();
        byte[] message = PacketUtils.getControlMessage(packet);
        SirenControlPacket packet2 = PacketUtils.getControlPacket(message);
        Assert.assertEquals(packet, packet2);
    }

}