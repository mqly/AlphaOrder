package com.dragon.alphaorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.adapter.OrderListAdapter;
import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.entity.Order;
import com.dragon.alphaorder.greendao.OrderDao;
import com.dragon.alphaorder.utils.CacheUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/11.
 */

public class OrderManageActivity extends BaseActivity {
    @BindView(R.id.order_manage_toolbar)
    Toolbar toolbar;
    @BindString(R.string.menu_order)
    String orderTitle;
    @BindView(R.id.order_manage_recyclerview)
    RecyclerView mRecyclerView;
    OrderListAdapter mOrderListAdapter;
    private List<Order> mOrders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        ButterKnife.bind(this);
        initToolbar();
        mOrders = getOrdersFromDB();
        mOrderListAdapter = new OrderListAdapter(OrderManageActivity.this, mOrders);
        mOrderListAdapter.setOnItemClickListener(new OrderListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Order data) {
                //                Toast.makeText(OrderManageActivity.this, data.getTotalPrice()+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderManageActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("order", data);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mOrderListAdapter);
    }

    //从数据库获取订单列表
    private List<Order> getOrdersFromDB() {
        String userName = CacheUtil.getACache().getAsString("currentuser");
        List<Order> orderList = new ArrayList<Order>();
        OrderDao dao = MyApplication.getInstance().getDaoSession().getOrderDao();
        orderList = dao.queryBuilder().where(OrderDao.Properties.UserId.eq(userName)).build().list();
        Collections.reverse(orderList);
        return orderList;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OrderManageActivity.class);
        context.startActivity(intent);
    }

    private void initToolbar() {
        toolbar.setTitle(orderTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //actionbar的左侧图标的点击事件处理
            case android.R.id.home:
                MainActivity.actionStart(OrderManageActivity.this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MainActivity.actionStart(OrderManageActivity.this);
    }
}
