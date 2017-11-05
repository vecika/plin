package com.vecika.plin.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vecika on 04.11.2017..
 */

public class Workorder implements Parcelable{

    private String serialNumber;
    private String name;
    private String surname;
    private String address;
    private String lastRead;


    public Workorder(String serialNumber, String name, String surname, String address, String lastRead) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.lastRead = lastRead;
    }

    protected Workorder(Parcel in) {
        serialNumber = in.readString();
        name = in.readString();
        surname = in.readString();
        address = in.readString();
        lastRead = in.readString();
    }

    public static final Creator<Workorder> CREATOR = new Creator<Workorder>() {
        @Override
        public Workorder createFromParcel(Parcel in) {
            return new Workorder(in);
        }

        @Override
        public Workorder[] newArray(int size) {
            return new Workorder[size];
        }
    };

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastRead() {
        return lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serialNumber);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(address);
        dest.writeString(lastRead);
    }
}
