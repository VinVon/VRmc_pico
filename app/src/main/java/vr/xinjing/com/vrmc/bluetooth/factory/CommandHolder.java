package vr.xinjing.com.vrmc.bluetooth.factory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/12/28.
 */

public class CommandHolder {
    private static final int BUFFER_ALLOCATE_BIGGER = 2048;
    private static final int BUFFER_ALLOCATE_NORMAL = 512;
    public static final int COMMANDRESPONSE_NONE = 4353;
    private static final int COMMANDRESPONSE_NORMAL = 4354;
    private ByteBuffer byteBuffer = null;
    private ICommandCallback callback;
    private byte cmd;
    public byte ext_cmd;
    private int operationId;
    public List<byte[]> packets;
    private Thread timeOutThread = null;
    public int type = 4354;

    public static CommandHolder create(byte paramByte1, byte paramByte2, int paramInt, List<byte[]> paramList, ICommandCallback paramICommandCallback) {
        CommandHolder localCommandHolder = new CommandHolder();
        localCommandHolder.cmd = paramByte1;
        localCommandHolder.ext_cmd = paramByte2;
        localCommandHolder.operationId = paramInt;
        localCommandHolder.packets = paramList;
        localCommandHolder.callback = paramICommandCallback;
        return localCommandHolder;
    }

    public static CommandHolder create(byte paramByte1, byte paramByte2, int paramInt, byte[] paramArrayOfByte, ICommandCallback paramICommandCallback) {
        CommandHolder localCommandHolder = new CommandHolder();
        localCommandHolder.cmd = paramByte1;
        localCommandHolder.ext_cmd = paramByte2;
        localCommandHolder.operationId = paramInt;
        localCommandHolder.packets = new ArrayList();
        localCommandHolder.packets.add(paramArrayOfByte);
        localCommandHolder.callback = paramICommandCallback;
        return localCommandHolder;
    }
}
