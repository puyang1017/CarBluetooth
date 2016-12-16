package com.puy.carbluetooth;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Handler;
import android.util.Log;

import bluetooth.BluetoothLeService;

/**
 * Created by puy on 2016-11-21.
 */

public class App extends Application{
    //蓝牙是否连接上了
    private boolean ble_connect_state=false;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothGattCharacteristic characteristic;


    private Handler ActivityMenuHanlder;
    private Handler DeviceScanActivityHanlder;

    private String EM = "M1"; //电机号
    private String SE = "S1"; //舵机号
    private int zero_angle = 1100;
    private int angle = 30;//角度  (angle_High - angle_Low)/2
    private int angle_base = 42;//角度基数
    private double angle_Low = 42;//最小角度
    private double angle_High = 102;//最大角度

    private int speed_d = 999;//速度差  (speed_High - speed_Low)
    private int speed_base = 0;//速度基数
    private int speed_Low = 0;//速度最小值
    private int speed_High = 999;//速度最大值



    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void clear(){
        ble_connect_state=false;
        mBluetoothLeService = null;
        characteristic = null;
        ActivityMenuHanlder = null;
        DeviceScanActivityHanlder = null;
    }

    public boolean isBle_connect_state() {
        return ble_connect_state;
    }

    public void setBle_connect_state(boolean ble_connect_state) {
        this.ble_connect_state = ble_connect_state;
    }


    public BluetoothGattCharacteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    public void sendData(byte[] WriteBytes){
        if(characteristic != null&&mBluetoothLeService!=null){
            characteristic.setValue(WriteBytes);
            mBluetoothLeService.writeCharacteristic(characteristic);
        }
    }

    public void sendData(String WriteBytes){
        if(characteristic == null){
            Log.d("App", "sendData:characteristic == null");
            return;
        }
        final int charaProp = characteristic.getProperties();
        Log.d("App", "sendData:charaProp "+charaProp);

        if((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0){
            if(characteristic != null&&mBluetoothLeService!=null){
                characteristic.setValue(WriteBytes.getBytes());
                mBluetoothLeService.writeCharacteristic(characteristic);
                Log.d("App", "sendData: "+WriteBytes);
            }
        }


    }

    public BluetoothLeService getmBluetoothLeService() {
        return mBluetoothLeService;
    }

    public void setmBluetoothLeService(BluetoothLeService mBluetoothLeService) {
        this.mBluetoothLeService = mBluetoothLeService;
    }

    public Handler getActivityMenuHanlder() {
        return ActivityMenuHanlder;
    }

    public void setActivityMenuHanlder(Handler activityMenuHanlder) {
        ActivityMenuHanlder = activityMenuHanlder;
    }

    public Handler getDeviceScanActivityHanlder() {
        return DeviceScanActivityHanlder;
    }

    public void setDeviceScanActivityHanlder(Handler deviceScanActivityHanlder) {
        DeviceScanActivityHanlder = deviceScanActivityHanlder;
    }

    public String getEM() {
        return EM;
    }

    public void setEM(String EM) {
        this.EM = EM;
    }

    public String getSE() {
        return SE;
    }

    public void setSE(String SE) {
        this.SE = SE;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getAngle_base() {
        return angle_base;
    }

    public void setAngle_base(int angle_base) {
        this.angle_base = angle_base;
    }

    public double getAngle_Low() {
        return angle_Low;
    }

    public void setAngle_Low(double angle_Low) {
        this.angle_Low = angle_Low;
    }

    public double getAngle_High() {
        return angle_High;
    }

    public void setAngle_High(double angle_High) {
        this.angle_High = angle_High;
    }

    public int getZero_angle() {
        return zero_angle;
    }

    public void setZero_angle(int zero_angle) {
        this.zero_angle = zero_angle;
    }

    public int getSpeed_Low() {
        return speed_Low;
    }

    public void setSpeed_Low(int speed_Low) {
        this.speed_Low = speed_Low;
    }

    public int getSpeed_High() {
        return speed_High;
    }

    public void setSpeed_High(int speed_High) {
        this.speed_High = speed_High;
    }

    public int getSpeed_base() {
        return speed_base;
    }

    public void setSpeed_base(int speed_base) {
        this.speed_base = speed_base;
    }

    public int getSpeed_d() {
        return speed_d;
    }

    public void setSpeed_d(int speed_d) {
        this.speed_d = speed_d;
    }
}
