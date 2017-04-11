package com.dragon.alphaorder.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.greendao.UserDao;
import com.dragon.alphaorder.utils.CacheUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/10.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_username)
    EditText username;
    @BindView(R.id.login_password)
    EditText password;
    String usn, psd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String user = CacheUtil.getACache().getAsString("currentuser");
        if (!TextUtils.isEmpty(user)) {
//            finish();
            MainActivity.actionStart(this);
        }
    }

    @SuppressLint("WrongConstant")
    @OnClick(R.id.login_regist)
    public void regist(Button button) {
        RegistActivity.actionStart(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick(R.id.login_login)
    public void login(Button button) {
        //        Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
        if (isUserInfoValided()) {
            if (login(usn, psd)) {
                CacheUtil.getACache().put("currentuser", usn);
                finish();
                MainActivity.actionStart(this);
            } else {
                Toast.makeText(this, "用户名密码错误", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //判断输入用户名密码是否有效
    @SuppressLint("WrongConstant")
    private boolean isUserInfoValided() {
        usn = username.getText().toString();
        psd = password.getText().toString();
        if (TextUtils.isEmpty(usn)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(psd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean login(String username, String password) {
        UserDao dao = MyApplication.getInstance().getDaoSession().getUserDao();
        List result = dao.queryBuilder().where(UserDao.Properties.UserName.eq(usn), UserDao.Properties.UserPassword.eq(psd)).build().list();
        if (result != null && result.size() > 0) {
            return true;
        }
        return false;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
