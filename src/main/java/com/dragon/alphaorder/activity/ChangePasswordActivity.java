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
import com.dragon.alphaorder.entity.User;
import com.dragon.alphaorder.greendao.UserDao;
import com.dragon.alphaorder.utils.CacheUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/11.
 */

public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.change_password_psd)
    EditText password;
    @BindView(R.id.change_password_confirm)
    EditText confirmPassword;
    String psd, cofPsd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.change_password_update)
    public void changePassword(Button button) {
        if (isPasswordValided()) {
            updatePassword();
            CacheUtil.getACache().remove("currentuser");
            LoginActivity.actionStart(ChangePasswordActivity.this);
        }
    }

    //输入密码是否有效
    @SuppressLint("WrongConstant")
    private boolean isPasswordValided() {
        psd = password.getText().toString();
        cofPsd = confirmPassword.getText().toString();
        if (TextUtils.isEmpty(psd) || TextUtils.isEmpty(cofPsd)) {
            Toast.makeText(this, "请输入密码和确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!psd.equals(cofPsd)) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //更新密码到数据库
    @SuppressLint("WrongConstant")
    public void updatePassword() {
        User user = getUserByName(CacheUtil.getACache().getAsString("currentuser"));
        if (TextUtils.isEmpty(user.getUserId())) {
            Toast.makeText(this, "修改密码失败", Toast.LENGTH_SHORT).show();
            return;
        }
        user.setUserPassword(cofPsd);
        UserDao dao = MyApplication.getInstance().getDaoSession().getUserDao();
        dao.update(user);
    }

    //通过用户名获取用户信息
    public User getUserByName(String username) {
        UserDao dao = MyApplication.getInstance().getDaoSession().getUserDao();
        List<User> result = dao.queryBuilder().where(UserDao.Properties.UserName.eq(username)).build().list();
        if (result.size() > 0) {
            return result.get(0);
        }
        return new User();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }
}
