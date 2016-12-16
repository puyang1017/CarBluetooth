package com.puy.carbluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import bluetooth.BluetoothLeService;
import bluetooth.DeviceScanActivity;

/**
 * Created by Administrator on 2016-11-08.
 */
public class ActivityMenu extends Activity implements View.OnClickListener{
    private App app;
    private RelativeLayout car;
    private RelativeLayout dino;
    private String mDeviceName;
    private String mDeviceAddress;
    private Button sselto;
    private Handler handler;
    private RelativeLayout bluetooth;
    //写数据
    private BluetoothGattCharacteristic characteristic;
    private BluetoothGattService mnotyGattService;
    byte[] WriteBytes = new byte[20];

    public BluetoothLeService mBluetoothLeService;
    private  ServiceConnection mServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        app = (App) getApplicationContext();
        initView();
        initData();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        mServiceConnection = new ServiceConnection() {

                            @Override
                            public void onServiceConnected(ComponentName componentName, IBinder service) {
                                mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
                                if (!mBluetoothLeService.initialize()) {
                                    finish();
                                }
                                // Automatically connects to the device upon successful start-up initialization.
                                mBluetoothLeService.connect(mDeviceAddress);
                                app.setmBluetoothLeService(mBluetoothLeService);
                            }

                            @Override
                            public void onServiceDisconnected(ComponentName componentName) {
                                mBluetoothLeService = null;
                                app.setBle_connect_state(false);
                            }
                        };
                        mDeviceAddress = (String) msg.obj;
                        Intent gattServiceIntent = new Intent(ActivityMenu.this, bluetooth.BluetoothLeService.class);
                        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                        break;
                }
            }
        };
        app.setActivityMenuHanlder(handler);
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }


    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            if(mBluetoothLeService != null)
                unbindService(mServiceConnection);
            unregisterReceiver(mGattUpdateReceiver);
            app.clear();
            finish();
            System.exit(0);
        }


    }

    private void initData() {
        car.setOnClickListener(this);
        dino.setOnClickListener(this);
        sselto.setOnClickListener(this);
        bluetooth.setOnClickListener(this);
    }

    private void initView() {
        car = (RelativeLayout) findViewById(R.id.car);
        dino = (RelativeLayout) findViewById(R.id.dino);
        sselto = (Button) findViewById(R.id.toystudy);
        bluetooth = (RelativeLayout) findViewById(R.id.bluetooth);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.car:
                    intent = new Intent(this,ActivityCarSelect.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);
                break;
            case R.id.dino:
//                intent = new Intent(this,ActivitySESetting.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                this.startActivity(intent);
                break;
            case R.id.toystudy:
                intent = new Intent(this, ActivityRecommend.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            case R.id.bluetooth:
                intent = new Intent(this, DeviceScanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            default:
                break;

        }
    }





    // Handles various events fired by the Service.处理服务所激发的各种事件
    // ACTION_GATT_CONNECTED: connected to a GATT server.连接一个GATT服务
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.从GATT服务中断开连接
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.查找GATT服务
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.从服务中接受数据
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                app.setBle_connect_state(true);
                app.getDeviceScanActivityHanlder().obtainMessage(0).sendToTarget();
                Log.d("puyang", "onReceive: 连接成功");
                Toast.makeText(app,"连接成功",Toast.LENGTH_SHORT).show();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                app.setBle_connect_state(false);
                unbindService(mServiceConnection);
                Log.d("puyang", "onReceive: 连接失败");
//                Toast.makeText(app,"断开连接",Toast.LENGTH_SHORT).show();
            }
            //发现有可支持的服务
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //写数据的服务和characteristic
                try{
                    mnotyGattService = mBluetoothLeService.getSupportedGattServices(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));
                    characteristic = mnotyGattService.getCharacteristic(UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb"));
                    byte[] value = new byte[20];
                    value[0] = (byte) 0x00;
                    characteristic.setValue(value[0],BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    app.setCharacteristic(characteristic);
                    Log.d("puyang", "onReceive: characteristic设置成功");
                }catch(Exception e){
                    e.printStackTrace();
                }


                //读数据的服务和characteristic
//                readMnotyGattService = mBluetoothLeService.getSupportedGattServices(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
//                readCharacteristic = readMnotyGattService.getCharacteristic(UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb"));
            }
            //显示数据
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //将数据显示在mDataField上
            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
