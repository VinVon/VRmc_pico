//package vr.xinjing.com.vrmc.bluetooth;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.ServiceConnection;
//import android.content.pm.PackageManager;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import vr.xinjing.com.vrmc.MainActivity;
//import vr.xinjing.com.vrmc.bluetooth.factory.BTFactory;
//import vr.xinjing.com.vrmc.push.PushBean;
//import vr.xinjing.com.vrmc.utils.SpUtils;
//import vr.xinjing.com.vrmc.utils.ToHexByteUtils;
//
///**
// * 蓝牙数据操作辅助类
// * Created by raytine on 2017/12/14.
// */
//
//public class BLEHelper {
//    final String coon_diert = "015A81080102030405060708";
//    private final int RESULTCODE_TRUE_ON_BLUETOOTH = 0;
//    private BluetoothAdapter mBluetoothAdapter;
//    private BluetoothAdapter.LeScanCallback mBLEScanCallback;
//    private List<BluetoothDevice> mDataList;
//    private static final long SCAN_PERIOD = 10000; //5 seconds
//    private static BLEHelper mBLEHelper;
//    private Handler mHandler;
//    private BLEControlService mService = null;
//    private final int CONNECT_STATUS_CONNECTED = 1;
//    private final int CONNECT_STATUS_DISCONNECTED = 2;
//    BLEStatusChangeReceiver mBLEStatusChangeReceiver;
//    private int mConnectStatus = CONNECT_STATUS_DISCONNECTED;
//    public void setmHandler(Handler mHandler) {
//        this.mHandler = mHandler;
//    }
//
//    private BLEHelper() {
//        mDataList = new LinkedList<BluetoothDevice>();
//        mBLEStatusChangeReceiver = new BLEStatusChangeReceiver();
//    }
//
//    public static BLEHelper getInsrance(){
//        if (mBLEHelper == null){
//            synchronized (BLEHelper.class){
//                if (mBLEHelper == null){
//                    mBLEHelper = new BLEHelper();
//                   return mBLEHelper;
//                }
//            }
//        }
//        return mBLEHelper;
//    }
//    //检查蓝牙设备
//    public BluetoothAdapter checkBLEDevice(Context context) {
//        // Use this check to determine whether BLE is supported on the device.  Then you can
//        // selectively disable BLE-related features.
//        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            return null;
//        }
//        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
//        // BluetoothAdapter through BluetoothManager.
//        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = bluetoothManager.getAdapter();
//        // Checks if Bluetooth is supported on the device.
//        if (mBluetoothAdapter == null) {
//            return null;
//        }
//        return mBluetoothAdapter;
//    }
//    public BluetoothAdapter.LeScanCallback getBLEScanCallback() {
//        return new BluetoothAdapter.LeScanCallback() {
//            @Override
//            public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
//                addBLEDeviceData(device);
//            }
//        };
//    }
//    private void addBLEDeviceData(BluetoothDevice device) {
//        boolean deviceFound = false;
//        for (BluetoothDevice listDev : mDataList) {
//            if (listDev.getAddress().equals(device.getAddress())) {
//                deviceFound = true;
//                Message message = Message.obtain();
//                message.what = 2;
//                message.obj = mDataList;
//                mHandler.sendMessage(message);
//                break;
//            }
//        }
//        if (!deviceFound) {
//            if (device.getName() != null && device.getName().startsWith("Z")) {
//                mDataList.add(device);
//                Message message = Message.obtain();
//                message.what = 2;
//                message.obj = mDataList;
//                mHandler.sendMessage(message);
//            }
//        }
//    }
//    /**
//     * 初始化
//     */
//    private BluetoothDevice mBLEDevice = null;
//    private String mDeviceAddress;
//    public void initBLEControlService(Context context,String mDeviceAddress) {
//        this.mDeviceAddress = mDeviceAddress;
//        if (mService == null) {
//            //create BLEControService
//            Intent bindIntent = new Intent(context, BLEControlService.class);
//            //register listener that listen BLE status change callback
//            LocalBroadcastManager.getInstance(context).registerReceiver(mBLEStatusChangeReceiver, makeGattUpdateIntentFilter());
//            //binding BLEControService callback
//            context.bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
//            mBLEDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mDeviceAddress);
//            //connectBLEDevice();
//            mBLEStatusChangeReceiver.setOnBLEStatusChangeListener(new BLEStatusChangeReceiver.OnBLEStatusChangeListener() {
//                @Override
//                public void onConnected() {
//                   mHandler.sendEmptyMessage(3);
//                }
//
//                @Override
//                public void onDisConnected() {
//                    mService.disconnect();
//                    mHandler.sendEmptyMessage(4);
//                }
//
//
//                @Override
//                public void onGattServiceDiscovered() {
//                }
//
//                @Override
//                public void onDataChange(String uuid, byte[] value, String type) {
//                    String s = ToHexByteUtils.bytesToHexString(value);
//                    if (s.equals("0100")) {
//                        mService.writeRXCharacteristic(BTFactory.createConnectCMD(null));
//                    } else if (s.equals("015A810100")) {
//                        mHandler.sendEmptyMessage(5);
////                    startEcgTask();
//                    } else if (s.startsWith("89008110")) {
//                        Log.e("----xinlv",s);
//                        Message msg = Message.obtain();
//                        msg.what = 6;
//                        msg.obj = s;
//                        mHandler.sendMessage(msg);
//                        }
//                    }
//
////                        if (s.startsWith("8902")&& !s.equals("8902810100")) {
////                            //发送心率数据
////                            if (!stringBuffer.contains(s)){0
////                                stringBuffer+=s;
////                                if (s.startsWith("89028A08")){
////                                    Map<String, String> priArgs = new HashMap<>();
////                                    priArgs.put("bytes", stringBuffer.toString());
////                                    priArgs.put("clickRecordId", dataBean.getClickRecordId());
////                                    priArgs.put("patientcaseid", dataBean.getPatientcaseId());
////                                    priArgs.put("token", SpUtils.getInstance().getToken());
////                                    mEndTaskPresenter.sendRcgData(priArgs);
////                                }
////                            }
////
//////
//////                    Client.main(s);
////
////                        }
//                @Override
//                public void onRssiRead(int rssi, String type) {
//                }
//            });
//        } else {
//            connectBLEDevice();
//        }
//    }
//    private static IntentFilter makeGattUpdateIntentFilter() {
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BLEControlService.ACTION_GATT_CONNECTED);
//        intentFilter.addAction(BLEControlService.ACTION_GATT_DISCONNECTED);
//        intentFilter.addAction(BLEControlService.ACTION_GATT_SERVICES_DISCOVERED);
//        intentFilter.addAction(BLEControlService.ACTION_DATA_AVAILABLE);
//        intentFilter.addAction(BLEControlService.DEVICE_DOES_NOT_SUPPORT_UART);
//        return intentFilter;
//    }
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//        //绑定服务时
//        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
//            mService = ((BLEControlService.LocalBinder) rawBinder).getService();
//            mBLEStatusChangeReceiver.setBLEService(mService);
//            mConnectStatus = CONNECT_STATUS_CONNECTED;
//            if (!mService.initialize()) {
////                finish();
//            }
//            mHandler.sendEmptyMessage(7);
//
//            connectBLEDevice();
//        }
//        //断开服务时
//        public void onServiceDisconnected(ComponentName classname) {
//            mService = null;
//            mConnectStatus = CONNECT_STATUS_DISCONNECTED;
//            mHandler.sendEmptyMessage(8);
//        }
//    };
//    private void connectBLEDevice() {
//        if (mService != null) {
//            mService.connect(mDeviceAddress);
//        }
//    }
//    public void writeRXCharacteristic(byte[] bt){
//        if (mService != null)
//            mService.writeRXCharacteristic(bt);
//    }
//}
