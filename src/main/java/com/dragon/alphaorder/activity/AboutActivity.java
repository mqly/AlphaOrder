package com.dragon.alphaorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.dragon.alphaorder.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/18.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.about_toolbar)
    Toolbar toolbar;
    @BindString(R.string.menu_about)
    String aboutTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(aboutTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
