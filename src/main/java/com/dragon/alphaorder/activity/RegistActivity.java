package com.dragon.alphaorder.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.entity.User;
import com.dragon.alphaorder.greendao.UserDao;
import com.dragon.alphaorder.utils.UUIDBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/10.
 */

public class RegistActivity extends BaseActivity {
    @BindView(R.id.regist_username)
    EditText username;
    @BindView(R.id.regist_password)
    EditText password;
    String u, p;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick(R.id.regist_regist)
    public void regist(Button button) {
        if (isUserInfoValided()) {
            if (isUserExist()) {
                Toast.makeText(this, "用户名已存在，请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                saveUser();
                showDialog();
            }
        }
    }

    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("注册成功,快去登录吧");
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                LoginActivity.actionStart(RegistActivity.this);
            }
        });
        builder.show();
    }

    //判断用户名是否已经被注册
    private boolean isUserExist() {
        UserDao dao = MyApplication.getInstance().getDaoSession().getUserDao();
        List result = dao.queryBuilder().where(UserDao.Properties.UserName.eq(u)).build().list();
        if (result != null && result.size() > 0) {
            return true;
        }
        return false;
    }

    //把用户注册信息存在数据库
    private void saveUser() {
        UserDao dao = MyApplication.getInstance().getDaoSession().getUserDao();
        dao.insert(new User(UUIDBuilder.getUUID(), u, p));
    }

    //判断输入用户名密码是否有效
    @SuppressLint("WrongConstant")
    private boolean isUserInfoValided() {
        u = username.getText().toString();
        p = password.getText().toString();
        if (TextUtils.isEmpty(u)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(p)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegistActivity.class);
        context.startActivity(intent);
    }
}
