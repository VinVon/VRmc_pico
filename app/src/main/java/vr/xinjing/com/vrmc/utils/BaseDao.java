//package vr.xinjing.com.vrmc.utils;
//
//import java.util.List;
//import android.content.Context;
//
//import vr.xinjing.com.vrmc.bean.VideoInfo;
//
///**
// * Created by raytine on 2017/2/20.
// */
//
//public class BaseDao {
//    public static final String DB_NAME = "sp.realm";
//    private Realm mRealm;
//
//
//    public BaseDao() {
//
//        mRealm = Realm.getDefaultInstance();
//    }
//
//    /**
//     * add （增）
//     */
//    public void addDog(final VideoInfo dog) {
//        mRealm.beginTransaction();
//        mRealm.copyToRealm(dog);
//        mRealm.commitTransaction();
//
//    }
//
//    /**
//     * delete （删）
//     */
//    public void deleteDog(String id) {
//        VideoInfo dog = mRealm.where(VideoInfo.class).equalTo("id", id).findFirst();
//        mRealm.beginTransaction();
//        dog.deleteFromRealm();
//        mRealm.commitTransaction();
//
//    }
//
//    /**
//     * update （改）
//     */
//    public void updateDog(String name, boolean newName) {
//        VideoInfo dog = mRealm.where(VideoInfo.class).equalTo("name", name).findFirst();
//        mRealm.beginTransaction();
//        dog.setState(newName);
//        mRealm.commitTransaction();
//    }
//
//    /**
//     * query （查询所有）
//     */
//    public List<VideoInfo> queryAllDog() {
//        RealmResults<VideoInfo> dogs = mRealm.where(VideoInfo.class).findAll();
//        return mRealm.copyToRealm(dogs);
//    }
//
//    /**
//     * query （根据Id（主键）查）
//     */
//    public VideoInfo queryDogById(String id) {
//        VideoInfo dog = mRealm.where(VideoInfo.class).equalTo("id", id).findFirst();
//        return dog;
//    }
//
//
//    /**
//     * query （根据name查）
//     */
//    public VideoInfo queryDobByAge(String name) {
//        VideoInfo dog = mRealm.where(VideoInfo.class).equalTo("name", name).findFirst();
//        return dog;
//    }
//
//    public boolean isDogExist(String id){
//        VideoInfo dog=mRealm.where(VideoInfo.class).equalTo("name",id).findFirst();
//        if (dog==null){
//            return false;
//        }else {
//            return  true;
//        }
//    }
//
//    public Realm getRealm(){
//
//        return mRealm;
//    }
//
//    public void close(){
//        if (mRealm!=null){
//            mRealm.close();
//
//        }
//    }
//}
