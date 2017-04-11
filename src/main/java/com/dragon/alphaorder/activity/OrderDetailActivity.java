package com.dragon.alphaorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.adapter.OrderDishesListAdapter;
import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.entity.Dishes;
import com.dragon.alphaorder.entity.Order;
import com.dragon.alphaorder.greendao.DishesDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/18.
 */

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.order_detail_toolbar)
    Toolbar toolbar;
    @BindString(R.string.order_detail)
    String detailTitle;
    @BindView(R.id.order_detail_recyclerview)
    RecyclerView mRecyclerView;
    List<Dishes> mDishes;
    OrderDishesListAdapter mOrderDishesListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initToolbar();
        Order order = getIntent().getBundleExtra("data").getParcelable("order");
        mDishes = getOrderDishesFromDB(order.getDishes());
        mOrderDishesListAdapter = new OrderDishesListAdapter(this, mDishes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mOrderDishesListAdapter);
    }

    //从数据库读取当前订单的菜品列表
    private List<Dishes> getOrderDishesFromDB(String dishesIds) {
        String[] ids = dishesIds.split(",");
        List<Dishes> dishesList = new ArrayList<Dishes>();
        DishesDao dao = MyApplication.getInstance().getDaoSession().getDishesDao();
        for (int i = 0; i < ids.length; i++) {
            dishesList.add(dao.queryBuilder().where(DishesDao.Properties.DishesId.eq(ids[i])).build().list().get(0));
        }
        return dishesList;
    }

    private void initToolbar() {
        toolbar.setTitle(detailTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        context.startActivity(intent);
    }
}
