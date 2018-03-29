package vr.xinjing.com.vrmc.httpnet;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vr.xinjing.com.vrmc.bean.LoginInfo;


/**
 * Created by raytine on 2017/2/28.
 */

public interface MoveServe {
    @POST("appVrRoom/login")
    Call<LoginInfo> getTopMovie(@Body RequestBody userReq);
}
