package com.dragon.alphaorder.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/10.
 */
@Entity
public class Order implements Parcelable {
    @Id
    private String orderId;
    private String userId;
    private String orderTime;
    private double totalPrice;
    private String dishes;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.userId);
        dest.writeString(this.orderTime);
        dest.writeDouble(this.totalPrice);
        dest.writeString(this.dishes);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.orderId = in.readString();
        this.userId = in.readString();
        this.orderTime = in.readString();
        this.totalPrice = in.readDouble();
        this.dishes = in.readString();
    }

    @Generated(hash = 1092536439)
    public Order(String orderId, String userId, String orderTime, double totalPrice,
            String dishes) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.dishes = dishes;
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
