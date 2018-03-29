package vr.xinjing.com.vrmc.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2018/3/5.
 */

public class SdCardUtil {

    // 项目文件目录
    public static final String FILEDIR = "/vrmc/video/";

   /*

    * 检查sd卡是否可用

    * getExternalStorageState 获取状态

    * Environment.MEDIA_MOUNTED 直译  环境媒体登上  表示，当前sd可用

    */

    public static boolean checkSdCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            //sd卡可用
            return true;
        else
            //当前sd卡不可用
            return false;

    }

   /*

    * 获取sd卡的文件路径

    * getExternalStorageDirectory 获取路径

    */

    public static String getSdPath(Context context){
        String sDdir = getSDdir(context);
        String absolutePath = context.getExternalFilesDir(null).getAbsolutePath();
        File file = new File(sDdir+"/Android/data/"+context.getPackageName()+"/files/");
        Logger.e("是否存在SD卡"+file.exists());
        return sDdir+"/Android/data/"+context.getPackageName()+"/files/";
    }

   /*

    * 创建一个文件夹

    */

    public static  void  createFileDir(String fileDir,Context context){
        String path=getSdPath(context)+fileDir;
        File path1=new File(path);

        if(!path1.exists())
        {
            boolean mkdirs = path1.mkdirs();
            Logger.e("被创建了"+mkdirs);
        }else{
            Logger.e("存在了");
        }
        Logger.e("创建了"+path1.getAbsolutePath());
    }


    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSDCARDExists(Context mContext) {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean result = sDCardStatus.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable();
        if (!result) {
            String path = getROOTDIR(mContext);
            result = null != path && !"".equals(path);
        }
        return result;
    }


    public static String getFullPath(Context context, String fname) {

        String[] extSDCardPath = getExtSDCardPath(context);
        Logger.e("内部路径存在几个"+extSDCardPath.length+"个");
        for (int i = 0; i < extSDCardPath.length; i++) {
            Logger.e("内部路径存在几个"+extSDCardPath[i].toString());
        }
        if (null == fname) {
            return null;
        }
        // 统一folder为 '/somepath'的格式
        if (!fname.startsWith(File.separator)) {
            fname = File.separator + fname;
        }
        String fatherDirPath;
        if (SdCardUtil.checkSDCARDExists(context)) {
            Logger.e("存在SD卡");
            try {
                fatherDirPath = context.getExternalFilesDir("").getParentFile().getPath() + File.separator;
            } catch (Exception e) {
                e.printStackTrace();
                fatherDirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + File.separator;
            }
        } else {
            String rootDir = SdCardUtil.getROOTDIR(context);
            if (null == rootDir || "".equals(rootDir)) {
                try {
                    fatherDirPath = context.getCacheDir().getParentFile().getPath() + File.separator;
                } catch (Exception e) {
                    e.printStackTrace();
                    fatherDirPath = Environment.getDataDirectory() + "/data/" + context.getPackageName() + File.separator;
                }
            } else {
                fatherDirPath = rootDir;
            }
        }
        File file = new File(fatherDirPath + fname);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fatherDirPath + fname;
    }

    /**
     * @param mContext
     * @return
     * @description 获取根目录
     * @author wragony
     * @modifier
     * @modifier_date
     */
    public static String getROOTDIR(Context mContext) {
        String rootDir = "";
        try {
            StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
            String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[0]).invoke(sm, new Object[]{});
            for (String path : paths) {
                File dirFile = new File(path);
                if (dirFile.canWrite()) {
                    rootDir = dirFile.getPath();
                    break;
                }
            }
        } catch (Exception e1) {
        }
        return rootDir;
    }


    /**
     * 获取外置SD卡路径以及TF卡的路径
     * <p>
     * 返回的数据：paths.get(0)肯定是外置SD卡的位置，因为它是primary external storage.
     *
     * @return 所有可用于存储的不同的卡的位置，用一个List来保存
     */
    public static List<String> getExtSDCardPathList() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        //首先判断一下外置SD卡的状态，处于挂载状态才能获取的到
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED)
                && extFile.exists() && extFile.isDirectory()
                && extFile.canWrite()) {
            //外置SD卡的路径
            paths.add(extFile.getAbsolutePath());
        }
        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile
                        .getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                //扩展存储卡即TF卡或者SD卡路径
                paths.add(mountPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }


    /**
     * 获取外置SD卡路径
     *
     */
    public static String[] getExtSDCardPath(Context context) {
        StorageManager storageManager = (StorageManager) context.getSystemService(Context
                .STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            return (String[])invoke;
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String ExternalPath1;
    private static String ExternalPath2;
    /**
     * 获取sd卡文件的路径 通过反射获取到隐藏类来模仿系统源码获取到信息，而这个也可以用来获取到6.0以上被打乱了的sd卡名称。
     */
    public static String getSDdir(Context mContext) {

        getMountedSDCardCount(mContext);
        return ExternalPath1;

    }

    private static int getMountedSDCardCount(Context context) {
        ExternalPath1 = null;
        ExternalPath2 = null;
        int readyCount = 0;
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        if (storageManager == null)
            return 0;
        Method method;
        Object obj;
        try {
            method = storageManager.getClass().getMethod("getVolumePaths", (Class[]) null);
            obj = method.invoke(storageManager, (Object[]) null);

            String[] paths = (String[]) obj;
            if (paths == null)
                return 0;

            method = storageManager.getClass().getMethod("getVolumeState", new Class[] { String.class });
            for (String path : paths) {
                obj = method.invoke(storageManager, new Object[] { path });
                if (Environment.MEDIA_MOUNTED.equals(obj)) {
                    readyCount++;
                    if (2 == readyCount) {
                        ExternalPath1 = path;
                    }
                    if (3 == readyCount) {
                        ExternalPath2 = path;
                    }
                }
            }
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                readyCount = 1;
            }
            Logger.d(ex.getMessage());
            return readyCount;
        }

        Logger.d("mounted sdcard unmber: " + readyCount);
        return readyCount;
    }

    /**
     * 如果以上方法出现问题（获取到的结果为NULL），可以采用下面的方法，具体可以自己修改，我只对6.0进行的处理：
     *
     * @return 返回值即为sd卡的名称，可以自己再进行修改和包装一下
     */
    public static String getPath() {
        Runtime mRuntime = Runtime.getRuntime();
        try {
            Process mProcess = mRuntime.exec("ls /storage");
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            StringBuffer mRespBuff = new StringBuffer();
            char[] buff = new char[1024];
            int ch = 0;
            while ((ch = mReader.read(buff)) != -1) {
                mRespBuff.append(buff, 0, ch);
            }
            mReader.close();
            String[] result = mRespBuff.toString().trim().split("\n");
            for (String str : result) {
                if (str.equals("emulate") || str.equals("self"))
                    continue;
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
