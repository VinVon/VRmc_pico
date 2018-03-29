package vr.xinjing.com.vrmc.bluetooth.factory;

import java.nio.ByteBuffer;

/**
 * Created by raytine on 2017/12/28.
 */

public class BTFactory{
    public static CommandHolder createConnectCMD(ICommandCallback paramICommandCallback)
    {
        return CommandHolder.create((byte)1, (byte)90, (byte)1, createOnePacket((byte) 1, (byte)90,(byte)-127,(byte)8, new byte[] { (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8 }), paramICommandCallback);
    }
    private static byte[] createOnePacket(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte[] paramArrayOfByte)
    {
        ByteBuffer localByteBuffer = ByteBuffer.allocate(paramArrayOfByte.length + 4);
        localByteBuffer.put(paramByte1);
        localByteBuffer.put(paramByte2);
        localByteBuffer.put(paramByte3);
        localByteBuffer.put(paramByte4);
        localByteBuffer.put(paramArrayOfByte);
        paramArrayOfByte = localByteBuffer.array();
        localByteBuffer.clear();
        return paramArrayOfByte;
    }
}

