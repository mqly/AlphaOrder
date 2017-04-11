package com.dragon.alphaorder.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.adapter.DishesListAdapter;
import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.entity.Dishes;
import com.dragon.alphaorder.entity.Order;
import com.dragon.alphaorder.greendao.DishesDao;
import com.dragon.alphaorder.greendao.OrderDao;
import com.dragon.alphaorder.utils.ActivityCollector;
import com.dragon.alphaorder.utils.CacheUtil;
import com.dragon.alphaorder.utils.UUIDBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.dishes_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.total_price)
    TextView totalPriceTextView;
    List<Dishes> mDishes;
    DishesListAdapter mDishesListAdapter;
    private double totalPrice;
    private List<Dishes> selectedDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        mDishes = getDishesFromDB();
        selectedDishes = new ArrayList<Dishes>();
        mDishesListAdapter = new DishesListAdapter(this, mDishes);
        mDishesListAdapter.setOnDishesSelectListener(new DishesListAdapter.OnDishesSelectListener() {

            @Override
            public void onDishesSelect(boolean isAdd, Dishes data) {
                if (isAdd) {
                    selectedDishes.add(data);
                    totalPrice = totalPrice + data.getDishesPrice();
                } else {
                    selectedDishes.remove(data);
                    totalPrice = totalPrice - data.getDishesPrice();
                }
                totalPriceTextView.setText(totalPrice + "");
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDishesListAdapter);
    }

    //初始化view
    private void initViews() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //从数据库读取菜品列表
    private List<Dishes> getDishesFromDB() {
        List<Dishes> dishesList = new ArrayList<Dishes>();
        DishesDao dao = MyApplication.getInstance().getDaoSession().getDishesDao();
        dishesList = dao.queryBuilder().build().list();
        return dishesList;
    }

    //提交订单
    @SuppressLint("WrongConstant")
    @OnClick(R.id.submit_order)
    public void submitOrder(Button button) {
        if (totalPrice <= 0) {
            Toast.makeText(this, "请先选择菜品", Toast.LENGTH_SHORT).show();
            return;
        }
        saveOrderToDB();
        showDialog();
    }

    //保存订单到数据库
    private void saveOrderToDB() {
        String orderId = UUIDBuilder.getUUID();
        String userId = CacheUtil.getACache().getAsString("currentuser");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTime = sDateFormat.format(new java.util.Date());
        StringBuilder dishesIds = new StringBuilder();
        for (int i = 0; i < selectedDishes.size(); i++) {
            dishesIds.append(selectedDishes.get(i).getDishesId());
            if (i < selectedDishes.size() - 1) {
                dishesIds.append(",");
            }
        }
        //        Logger.d(dishesIds.toString());
        Order order = new Order(orderId, userId, orderTime, totalPrice, dishesIds.toString());
        OrderDao dao = MyApplication.getInstance().getDaoSession().getOrderDao();
        dao.insert(order);
    }

    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提交成功");
        builder.setMessage("订单提交成功，请到我的订单查看");
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                OrderManageActivity.actionStart(MainActivity.this);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ActivityCollector.finishAll();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_order:
                OrderManageActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_my:
                MyActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_about:
                AboutActivity.actionStart(MainActivity.this);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
