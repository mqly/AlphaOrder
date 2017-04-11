package com.dragon.alphaorder.adapter;

import android.annotation.SuppressLint;
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
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/2/19.
 */

public class DishesListAdapter extends RecyclerView.Adapter<DishesListAdapter.MyViewHolder> {
    private List<Dishes> mDishesList;
    private Context context;
    private OnDishesSelectListener mOnDishesSelectListener = null;
    private Map selectMap;

    public DishesListAdapter(Context context, List<Dishes> dishesList) {
        this.mDishesList = dishesList;
        this.context = context;
        selectMap = new HashMap();
        for (int i = 0; i < mDishesList.size(); i++) {
            selectMap.put(i, false);
        }
    }

    public void setList(List<Dishes> dishesList) {
        this.mDishesList = dishesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dishes, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Dishes dishes = mDishesList.get(position);
        holder.nameTextView.setText(dishes.getDishesName());
        holder.priceTextView.setText(dishes.getDishesPrice() + "");
        Glide.with(context).load(getResourceByName(dishes.getDishesImage())).fitCenter().into(holder.imgImageView);
        holder.selectImageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (!(Boolean) selectMap.get(position)) {
//                    Logger.d(selectMap.get(position));
                    holder.selectImageView.setBackground(context.getResources().getDrawable(R.drawable.ic_after_select));
                    selectMap.remove(position);
                    selectMap.put(position, true);
                    if (mOnDishesSelectListener != null) {
                        mOnDishesSelectListener.onDishesSelect(true,dishes);
                    }
                } else {
//                    Logger.d(selectMap.get(position));
                    holder.selectImageView.setBackground(context.getResources().getDrawable(R.drawable.ic_before_select));
                    selectMap.remove(position);
                    selectMap.put(position, false);
                    if (mOnDishesSelectListener != null) {
                        mOnDishesSelectListener.onDishesSelect(false,dishes);
                    }
                }


            }
        });
        holder.itemView.setTag(dishes);
    }


    @Override
    public int getItemCount() {
        return mDishesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_dishes_name)
        public TextView nameTextView;
        @BindView(R.id.item_dishes_price)
        public TextView priceTextView;
        @BindView(R.id.item_dishes_img)
        public ImageView imgImageView;
        @BindView(R.id.item_dishes_select)
        public ImageView selectImageView;

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

    //item点击回调接口
    public static interface OnDishesSelectListener {
        void onDishesSelect(boolean isAdd,Dishes data);
    }

    public void setOnDishesSelectListener(OnDishesSelectListener listener) {
        this.mOnDishesSelectListener = listener;
    }
}
