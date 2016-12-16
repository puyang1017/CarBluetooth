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
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import custom.NumberSeekBar;
import custom.PopListButton;

/**
 * Created by puy on 2016-11-29.
 */

public class ActivityEMSetting extends Activity {
    private App app;
    private SwitchButton switchButton_mode1;
    private TextView textView_on1;
    private TextView textView_off1;
    private NumberSeekBar numberSeekBar1;
    private NumberSeekBar numberSeekBar2;
    private PopListButton popListButton;
    private Button save;
    private int speed_Low;//速度最小值
    private int speed_High;//速度最大值
    private int mPosition;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em_setting);
        app = (App)getApplicationContext();
        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        speed_Low = app.getSpeed_Low();
        speed_High = app.getSpeed_High();

        numberSeekBar1.setMax(999);
        numberSeekBar2.setMax(999);

        numberSeekBar1.setProgress(speed_Low);
        numberSeekBar2.setProgress(speed_High);

        if(app.getEM().equals("M1")){
            popListButton.setText("减速电机模式1");
        }else {
            popListButton.setText("减速电机模式2");
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




        numberSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("puyang", "onProgressChanged: progress "+(progress));
                speed_Low = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        numberSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("puyang", "onProgressChanged: progress "+(progress));
                speed_High = progress;
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
                if(speed_Low< speed_High){
                    app.setSpeed_Low(speed_Low);
                    app.setSpeed_High(speed_High);
                    app.setSpeed_base(speed_Low);
                    app.setSpeed_d((int)((speed_High- speed_Low)));
                    switch (mPosition){
                        case 0:
                            app.setEM("M1");
                            break;
                        case 1:
                            app.setEM("M2");
                            break;
                    }
                    ActivityJeep.handler.obtainMessage(0).sendToTarget();
                    finish();
                }else {
                    Toast.makeText(app,"速度最小值必须小于最大值",Toast.LENGTH_LONG).show();
                }

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
        numberSeekBar1 = (NumberSeekBar) findViewById(R.id.bar1);
        numberSeekBar2 = (NumberSeekBar) findViewById(R.id.bar2);
        popListButton = (PopListButton) findViewById(R.id.popListButton);
        save = (Button) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.back);
    }
}

