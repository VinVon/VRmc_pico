package vr.xinjing.com.vrmc.bluetooth.factory;

/**
 * Created by raytine on 2017/12/28.
 */

public class BHMessage {
    public int arg1;
    public int arg2;
    public Object obj;
    public int what1;
    public int what2;

    public BHMessage()
    {
    }

    public BHMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        this.what1 = paramInt1;
        this.what2 = paramInt2;
        this.arg1 = paramInt3;
        this.arg2 = paramInt4;
    }

    public BHMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject)
    {
        this.what1 = paramInt1;
        this.what2 = paramInt2;
        this.arg1 = paramInt3;
        this.arg2 = paramInt4;
        this.obj = paramObject;
    }
}
