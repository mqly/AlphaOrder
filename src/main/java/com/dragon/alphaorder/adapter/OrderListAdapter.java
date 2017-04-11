package com.dragon.alphaorder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.alphaorder.R;
import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.entity.Order;
import com.dragon.alphaorder.greendao.OrderDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/19.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> implements View.OnClickListener {
    private List<Order> orders;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public OrderListAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.context = context;
    }

    public void setList(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_manage, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Order order = orders.get(position);
        holder.dateTextView.setText(order.getOrderTime());
        holder.priceTextView.setText(order.getTotalPrice() + "");
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                OrderDao dao = MyApplication.getInstance().getDaoSession().getOrderDao();
                dao.delete(order);
                orders.remove(order);
                notifyDataSetChanged();
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setTag(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Order) v.getTag());
        }
    }

    //item点击回调接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Order data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_order_manage_date)
        public TextView dateTextView;
        @BindView(R.id.item_order_manage_price)
        public TextView priceTextView;
        @BindView(R.id.item_order_manage_del)
        public ImageView deleteImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
