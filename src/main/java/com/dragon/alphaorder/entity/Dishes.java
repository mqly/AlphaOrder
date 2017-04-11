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
public class Dishes implements Parcelable {
    @Id
    private String dishesId;
    private String dishesName;
    private String dishesImage;
    private double dishesPrice;

    public String getDishesId() {
        return dishesId;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public String getDishesImage() {
        return dishesImage;
    }

    public void setDishesImage(String dishesImage) {
        this.dishesImage = dishesImage;
    }

    public double getDishesPrice() {
        return dishesPrice;
    }

    public void setDishesPrice(double dishesPrice) {
        this.dishesPrice = dishesPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dishesId);
        dest.writeString(this.dishesName);
        dest.writeString(this.dishesImage);
        dest.writeDouble(this.dishesPrice);
    }

    public Dishes() {
    }

    protected Dishes(Parcel in) {
        this.dishesId = in.readString();
        this.dishesName = in.readString();
        this.dishesImage = in.readString();
        this.dishesPrice = in.readDouble();
    }

    @Generated(hash = 625515972)
    public Dishes(String dishesId, String dishesName, String dishesImage, double dishesPrice) {
        this.dishesId = dishesId;
        this.dishesName = dishesName;
        this.dishesImage = dishesImage;
        this.dishesPrice = dishesPrice;
    }

    public static final Parcelable.Creator<Dishes> CREATOR = new Parcelable.Creator<Dishes>() {
        @Override
        public Dishes createFromParcel(Parcel source) {
            return new Dishes(source);
        }

        @Override
        public Dishes[] newArray(int size) {
            return new Dishes[size];
        }
    };
}
