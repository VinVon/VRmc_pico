package vr.xinjing.com.vrmc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import vr.xinjing.com.vrmc.activity.WelcomeActivity;
import vr.xinjing.com.vrmc.bean.IConstant;
import vr.xinjing.com.vrmc.bean.LocalInfo;
import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.bean.requestbody.UserReq;
import vr.xinjing.com.vrmc.httpnet.HttpMethods;
import vr.xinjing.com.vrmc.imp.LoginView;
import vr.xinjing.com.vrmc.presenter.LoginPresenter;

import vr.xinjing.com.vrmc.utils.SpUtils;
import vr.xinjing.com.vrmc.utils.ToastCommom;

/**
 * 授权页
 * Created by 123 on 2017/2/4.
 */

public class AuthorizationActivity extends AppCompatActivity implements LoginView{
    @BindView(R.id.phonenumber)
    EditText phonenumber;
    @BindView(R.id.hospital)
    EditText hospital;
    @BindView(R.id.img_1)
    TextView img1;
    private ProgressDialog dialog;
    private String path = "http://test.med-vision.cn/api/v1/appVrRoom/login";
    private String device_model = "";
    private String version_release = "";
    private String version ;
    private String device_id = "";
    private String username;
    private String passworld;
    private boolean isfirst;
    private LoginPresenter loginPresenter ;
    private CloudPushService pushService = PushServiceFactory.getCloudPushService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorrization);
        ButterKnife.bind(this);
        getData();
        if(isfirst){
        }else{
            phonenumber.setText(username);
            hospital.setText(passworld);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else
        {
            device_id = IConstant.devoceId(this);//String
        }
        version = IConstant.getVersion(this);
        device_model = IConstant.getModel();
        version_release = IConstant.getVersionRelease();
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = phonenumber.getText().toString();
                passworld = hospital.getText().toString();
                Map<String, String> priArgs = new HashMap<>();
                priArgs.put("appId", device_id);
                priArgs.put("pushId", pushService.getDeviceId());
                priArgs.put("appId", device_id);
                priArgs.put("appVersion", version);
                priArgs.put("channel", "null");
                priArgs.put("deviceModel", device_model);
                priArgs.put("deviceSystem", "android");
                priArgs.put("deviceVersion", version_release);
                priArgs.put("password", passworld);
                priArgs.put("username", username);
                loginPresenter = new LoginPresenter(AuthorizationActivity.this,priArgs);
                loginPresenter.Login();
//              HttpMethods.getInstance().getTopMovice(new UserReq(passworld,username));
            }
        });
    }


    @Override
    public void updateView(LoginInfo user) {
        if(isfirst){
            saveData(false,username,passworld,user.getData().getToken());
        }else{

        }
        Intent intent = new Intent(AuthorizationActivity.this,AuthorizationActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressDialog() {
        dialog = ProgressDialog.show(AuthorizationActivity.this, "账号登录中", "Please wait...", true, false);
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        ToastCommom.createInstance().ToastShow(AuthorizationActivity.this,msg);
    }

    public void  saveData(boolean st,String name,String pass,String token){
        LocalInfo info = new LocalInfo(name,pass,st,token);
        SpUtils instance = SpUtils.getInstance();
        instance.init(AuthorizationActivity.this);
        instance.saveUser(info);
    }
    public void getData(){
        SpUtils instance = SpUtils.getInstance();
        instance.init(AuthorizationActivity.this);
        LocalInfo user = instance.getUser();
        if (user == null){
            isfirst = true;
        }else{
            isfirst =  user.isFirstLogin();
            username=  user.getUsername();
            passworld= user.getPassword();
        }
    }
}
