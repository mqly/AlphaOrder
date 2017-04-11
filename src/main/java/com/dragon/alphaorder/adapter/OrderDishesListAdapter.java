package com.dragon.alphaorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dragon.alphaorder.R;
import com.dragon.alphaorder.entity.Dishes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/2/19.
 */

public class OrderDishesListAdapter extends RecyclerView.Adapter<OrderDishesListAdapter.MyViewHolder> {
    private List<Dishes> mDishesList;
    private Context context;

    public OrderDishesListAdapter(Context context, List<Dishes> dishesList) {
        this.mDishesList = dishesList;
        this.context = context;
    }

    public void setList(List<Dishes> dishesList) {
        this.mDishesList = dishesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_dishes, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Dishes dishes = mDishesList.get(position);
        holder.nameTextView.setText(dishes.getDishesName());
        holder.priceTextView.setText(dishes.getDishesPrice() + "");
        Glide.with(context).load(getResourceByName(dishes.getDishesImage())).fitCenter().into(holder.imgImageView);
        holder.itemView.setTag(dishes);
    }


    @Override
    public int getItemCount() {
        return mDishesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_order_dishes_name)
        public TextView nameTextView;
        @BindView(R.id.item_order_dishes_price)
        public TextView priceTextView;
        @BindView(R.id.item_order_dishes_img)
        public ImageView imgImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //通过文件名获取到drawable资源int  id
    public int getResourceByName(String imageName) {
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return resId;
    }

}
