package com.puy.carbluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import custom.NumberSeekBar;
import custom.PopListButton;
import custom.SeekBarPressure;

/**
 * Created by puy on 2016-11-16.
 */

public class ActivitySESetting extends Activity {
    private App app;
    private SwitchButton switchButton_mode1;
    private TextView textView_on1;
    private TextView textView_off1;
    private SeekBarPressure seekBarPressure;
    private NumberSeekBar numberSeekBar;
    private PopListButton popListButton;
    private Button save;
    private double angle_Low;
    private double angle_High;
    private int Angle;
    private int Angle_base;
    private int Zero_angle;
    private int mPosition;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_setting);
        app = (App)getApplicationContext();
        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        app.sendData("A5"+app.getSE()+app.getZero_angle()+"EN");
        super.onDestroy();
    }

    private void initData() {
        Zero_angle = app.getZero_angle();
        angle_Low = app.getAngle_Low();
        angle_High = app.getAngle_High();
        seekBarPressure.setProgressLow(angle_Low/1.8);
        seekBarPressure.setProgressHigh(angle_High/1.8);
        numberSeekBar.setTextSize(15);
        numberSeekBar.setCzsize(16);
        numberSeekBar.setDvalue(100);
        numberSeekBar.setProgress(Zero_angle-100);
        if(app.getSE().equals("S1")){
            popListButton.setText("随动电机1");
        }else {
            popListButton.setText("随动电机2");
        }


        switchButton_mode1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    textView_on1.setTextColor(getResources().getColor(R.color.mywhite));
                    textView_off1.setTextColor(getResources().getColor(R.color.myblue));
                }else {
                    textView_on1.setTextColor(getResources().getColor(R.color.myblue));
                    textView_off1.setTextColor(getResources().getColor(R.color.mywhite));
                }
            }
        });


        seekBarPressure.setOnSeekBarChangeListener(new SeekBarPressure.OnSeekBarChangeListener() {
            @Override
            public void onProgressBefore() {

            }

            @Override
            public void onProgressChanged(SeekBarPressure seekBar, double progressLow, double progressHigh) {
                Log.d("puyang", "onProgressChanged: progressLow "+progressLow+"progressHigh"+progressHigh);
                Angle_base = (int) progressLow;
                Angle = (int) ((progressHigh-progressLow)/2);
                angle_Low = progressLow;
                angle_High = progressHigh;

            }

            @Override
            public void onProgressAfter() {

            }
        });


        numberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("puyang", "onProgressChanged: progress "+(progress+100));
                Zero_angle = progress+100;
                app.sendData("A5"+app.getSE()+String.format("%04d",Zero_angle)+"EN");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        popListButton.setOnPopItemSelectedListener(new PopListButton.OnPopItemSelectedListener() {
            @Override
            public void onPopItemSelected(int position) {
                Log.d("puyang", "onPopItemSelected: "+position);
                mPosition = position;

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setAngle_base(Angle_base);
                app.setAngle(Angle);
                app.setAngle_Low(angle_Low);
                app.setAngle_High(angle_High);
                app.setZero_angle(Zero_angle);
                switch (mPosition){
                    case 0:
                        app.setSE("S1");
                        break;
                    case 1:
                        app.setSE("S2");
                        break;
                }
                ActivityJeep.handler.obtainMessage(0).sendToTarget();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        switchButton_mode1 = (SwitchButton) findViewById(R.id.sb_md1);
        textView_on1 = (TextView) findViewById(R.id.text_no1);
        textView_off1 = (TextView) findViewById(R.id.text_off1);
        seekBarPressure = (SeekBarPressure) findViewById(R.id.seekBar_tg);
        numberSeekBar = (NumberSeekBar) findViewById(R.id.bar2);
        popListButton = (PopListButton) findViewById(R.id.popListButton);
        save = (Button) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.back);
    }
}
