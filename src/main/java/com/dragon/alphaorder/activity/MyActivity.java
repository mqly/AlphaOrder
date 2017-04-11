package com.dragon.alphaorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.utils.CacheUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/11.
 */

public class MyActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    @BindString(R.string.menu_my)
    String myTitle;
    @BindView(R.id.my_name)
    TextView nameTextView;
    String currentuser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        initToolbar();
        currentuser = CacheUtil.getACache().getAsString("currentuser");
        if (TextUtils.isEmpty(currentuser)) {
            LoginActivity.actionStart(MyActivity.this);
            return;
        }
        nameTextView.setText("欢迎你，" + currentuser);
    }

    private void initToolbar() {
        toolbar.setTitle(myTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //注销
    @OnClick(R.id.my_logout)
    public void logout(Button button) {
        CacheUtil.getACache().remove("currentuser");
        LoginActivity.actionStart(MyActivity.this);
    }

    //修改密码
    @OnClick(R.id.my_change_password)
    public void changePassword(Button button) {
        ChangePasswordActivity.actionStart(MyActivity.this);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyActivity.class);
        context.startActivity(intent);
    }
}
