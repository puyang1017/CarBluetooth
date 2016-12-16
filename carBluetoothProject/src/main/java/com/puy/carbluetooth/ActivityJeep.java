package com.puy.carbluetooth;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import app.minimize.com.seek_bar_compat.SeekBarCompat;

public class ActivityJeep extends Activity implements View.OnClickListener{
    private App app;
    private SeekBar seekBarLeft;
    private SeekBar seekBarRight;
    private RelativeLayout Left;
    private RelativeLayout Right;
    private RelativeLayout RelativeLayout_mode1;
    private RelativeLayout RelativeLayout_mode2;
    private ImageButton btn_off_on;
    private ImageButton info;
    private ImageButton stop;
    private ImageButton start;
    private ImageView image_title;
    private ProgressBar progressbar_bottom_left;
    private ProgressBar progressbar_top_left;
    private ProgressBar progressbar_bottom_right;
    private ProgressBar progressbar_top_right;
    private Button save;
    private SwitchButton switchButton_mode1;
    private SwitchButton switchButton_mode2;
    private TextView textView_on1;
    private TextView textView_on2;
    private TextView textView_off1;
    private TextView textView_off2;
    private TextView speed_text;
    private TextView angle_text;

    private boolean lockLeftbuff =false;
    private boolean lockRightbuff =false;
    private boolean lockLeft =false;
    private boolean lockRight =false;
    private int angle;
    private int speed_d;
    public static Handler handler;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (App) getApplicationContext();
        initView();
        initData();

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        angle = app.getAngle();
                        speed_d = app.getSpeed_d();
                        seekBarLeft.setMax(speed_d*2);
                        seekBarLeft.setProgress(speed_d);
                        progressbar_bottom_left.setMax(speed_d);
                        progressbar_top_left.setMax(speed_d);
                        seekBarRight.setMax(angle*2);
                        seekBarRight.setProgress(angle);
                        progressbar_bottom_right.setMax(angle);
                        progressbar_top_right.setMax(angle);
                        seekBarLeft.setProgress(speed_d);
                        progressbar_top_left.setProgress(0);
                        progressbar_bottom_left.setProgress(0);
                        seekBarRight.setProgress(angle);
                        progressbar_top_right.setProgress(0);
                        progressbar_bottom_right.setProgress(0);
                        break;
                }

            }
        };
    }

    private void initData() {
        save.setText("设置");
        RelativeLayout_mode1.setVisibility(View.INVISIBLE);
        RelativeLayout_mode2.setVisibility(View.INVISIBLE);
        angle = app.getAngle();
        speed_d = app.getSpeed_d();
        seekBarLeft.setMax(speed_d*2);
        seekBarLeft.setProgress(speed_d);
        progressbar_bottom_left.setMax(speed_d);
        progressbar_top_left.setMax(speed_d);
        seekBarRight.setMax(angle*2);
        seekBarRight.setProgress(angle);
        progressbar_bottom_right.setMax(angle);
        progressbar_top_right.setMax(angle);
        ObjectAnimator.ofFloat(Left,"rotation",0f,-90f).setDuration(0).start();
        seekBarLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress>speed_d){
                    progressbar_top_left.setProgress(progress-speed_d);
                    progressbar_bottom_left.setProgress(0);
                    app.sendData("A5"+app.getEM()+"F"+String.format("%03d",progress-speed_d+app.getSpeed_base())+"EN");
                    app.sendData("A5"+app.getEM()+"B000EN");
                    Log.d("puyang", "onProgressChanged: "+String.format("%03d",progress-speed_d+app.getSpeed_base()));
                }else if(progress<speed_d){
                    progressbar_bottom_left.setProgress(speed_d-progress);
                    progressbar_top_left.setProgress(0);
                    app.sendData("A5"+app.getEM()+"B"+String.format("%03d",speed_d-progress+app.getSpeed_base())+"EN");
                    app.sendData("A5"+app.getEM()+"F000EN");
                    Log.d("puyang", "onProgressChanged: "+String.format("%03d",speed_d-progress+app.getSpeed_base()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(!lockLeft){
                    seekBar.setProgress(speed_d);
                    progressbar_top_left.setProgress(0);
                    progressbar_bottom_left.setProgress(0);
                    app.sendData("A5"+app.getEM()+"B000EN");
                    app.sendData("A5"+app.getEM()+"F000EN");

                    Log.d("puyang", "onStopTrackingTouch: "+String.format("%03d",app.getSpeed_Low()));
                }

            }
        });
        seekBarRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress>angle){
                    progressbar_top_right.setProgress(progress-angle);
                    progressbar_bottom_right.setProgress(0);
                    app.sendData("A5"+app.getSE()+"X"+String.format("%03d",progress+app.getAngle_base())+"EN");
                }else if(progress<angle){
                    progressbar_bottom_right.setProgress(angle-progress);
                    progressbar_top_right.setProgress(0);
                    app.sendData("A5"+app.getSE()+"X"+String.format("%03d",progress+app.getAngle_base())+"EN");
                }
                Log.d("puyang", "onProgressChanged: "+"A5"+app.getSE()+"X"+String.format("%03d",progress+app.getAngle_base())+"EN");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(!lockRight){
                    seekBar.setProgress(angle);
                    progressbar_top_right.setProgress(0);
                    progressbar_bottom_right.setProgress(0);
                    app.sendData("A5"+app.getSE()+"X"+String.format("%03d",app.getAngle()+app.getAngle_base())+"EN");
                    Log.d("puyang", "onStopTrackingTouch: "+String.format("%03d",app.getAngle()+app.getAngle_base()));
                }
            }
        });

        info.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        save.setOnClickListener(this);
        angle_text.setOnClickListener(this);
        speed_text.setOnClickListener(this);
        back.setOnClickListener(this);

        switchButton_mode1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lockLeftbuff = true;
                    textView_on1.setTextColor(getResources().getColor(R.color.mywhite));
                    textView_off1.setTextColor(getResources().getColor(R.color.myblue));
                }else {
                    lockLeftbuff = false;
                    textView_on1.setTextColor(getResources().getColor(R.color.myblue));
                    textView_off1.setTextColor(getResources().getColor(R.color.mywhite));
                }
            }
        });

        switchButton_mode2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lockRightbuff = true;
                    textView_on2.setTextColor(getResources().getColor(R.color.mywhite));
                    textView_off2.setTextColor(getResources().getColor(R.color.myblue));
                }else {
                    lockRightbuff = false;
                    textView_on2.setTextColor(getResources().getColor(R.color.myblue));
                    textView_off2.setTextColor(getResources().getColor(R.color.mywhite));
                }
            }
        });

        if (app.isBle_connect_state() == true){
            btn_off_on.setBackgroundResource(R.drawable.btn_click_on);
        }else {
            btn_off_on.setBackgroundResource(R.drawable.btn_click_off);
        }
    }

    private void initView() {
        seekBarLeft = (SeekBar)findViewById(R.id.SeekBarLeft);
        seekBarRight = (SeekBar)findViewById(R.id.SeekBarRight);
        RelativeLayout_mode1 = (RelativeLayout) findViewById(R.id.RelativeLayout_mode1);
        RelativeLayout_mode2 = (RelativeLayout) findViewById(R.id.RelativeLayout_mode2);
        Left = (RelativeLayout) findViewById(R.id.Left_RelativeLayout);
        Right = (RelativeLayout) findViewById(R.id.Right_RelativeLayout);
        btn_off_on = (ImageButton) findViewById(R.id.btn_off_on);
        info = (ImageButton) findViewById(R.id.info);
        start = (ImageButton) findViewById(R.id.start);
        stop = (ImageButton) findViewById(R.id.stop);
        progressbar_bottom_left = (ProgressBar) findViewById(R.id.progressbar_bottom_left);
        progressbar_top_left = (ProgressBar) findViewById(R.id.progressbar_top_left);
        progressbar_bottom_right = (ProgressBar) findViewById(R.id.progressbar_bottom_right);
        progressbar_top_right = (ProgressBar) findViewById(R.id.progressbar_top_right);
        save = (Button) findViewById(R.id.save);
        switchButton_mode1 = (SwitchButton) findViewById(R.id.sb_md1);
        switchButton_mode2 = (SwitchButton) findViewById(R.id.sb_md2);
        textView_on1 = (TextView) findViewById(R.id.text_no1);
        textView_on2 = (TextView) findViewById(R.id.text_no2);
        textView_off1 = (TextView) findViewById(R.id.text_off1);
        textView_off2 = (TextView) findViewById(R.id.text_off2);
        angle_text = (TextView) findViewById(R.id.angle);
        speed_text = (TextView) findViewById(R.id.speed);
        image_title = (ImageView) findViewById(R.id.image_title);
        back = (ImageView) findViewById(R.id.back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info:
                Intent intent = new Intent(getApplicationContext(), ActivityProblem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.stop:
                break;
            case R.id.start:
                break;
            case R.id.save:
                if(save.getText().equals("设置")){
                    save.setText("保存");
                    RelativeLayout_mode1.setVisibility(View.VISIBLE);
                    RelativeLayout_mode2.setVisibility(View.VISIBLE);
                    start.setVisibility(View.INVISIBLE);
                    stop.setVisibility(View.INVISIBLE);
                    speed_text.setVisibility(View.VISIBLE);
                    angle_text.setVisibility(View.VISIBLE);
                    btn_off_on.setVisibility(View.GONE);
                    info.setVisibility(View.GONE);
                    image_title.setBackgroundResource(R.mipmap.navigation);
                }else {
                    save.setText("设置");
                    RelativeLayout_mode1.setVisibility(View.INVISIBLE);
                    RelativeLayout_mode2.setVisibility(View.INVISIBLE);
                    start.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    speed_text.setVisibility(View.GONE);
                    angle_text.setVisibility(View.GONE);
                    btn_off_on.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    image_title.setBackgroundResource(R.mipmap.top_l);
                    lockLeft = lockLeftbuff;
                    lockRight = lockRightbuff;
                }
                seekBarLeft.setProgress(speed_d);
                progressbar_top_left.setProgress(0);
                progressbar_bottom_left.setProgress(0);
                seekBarRight.setProgress(angle);
                progressbar_top_right.setProgress(0);
                progressbar_bottom_right.setProgress(0);
                app.sendData("A5"+app.getEM()+"B000EN");
                app.sendData("A5"+app.getEM()+"F000EN");
                app.sendData("A5"+app.getSE()+"X000EN");
                break;
            case R.id.speed:
               Intent intentEM = new Intent(this, ActivityEMSetting.class);
                intentEM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intentEM);
                break;
            case R.id.angle:
                Intent intentSE = new Intent(this, ActivitySESetting.class);
                intentSE.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intentSE);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
