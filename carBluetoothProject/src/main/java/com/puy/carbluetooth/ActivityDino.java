package com.puy.carbluetooth;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by puy on 2016-12-17.
 */

public class ActivityDino extends Activity implements View.OnClickListener{
    private App app;
    private ImageView image_left;
    private SeekBar SeekBarLeft1;
    private SeekBar SeekBarLeft2;
    private RelativeLayout RelativeLayout_left1;
    private SeekBar seekBarRight;
    private ProgressBar progressbar_bottom_right;
    private ProgressBar progressbar_top_right;
    private ImageView back;
    private ImageButton btn_off_on;
    private ImageButton info;
    private int angle  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino);
        app = (App) getApplicationContext();

        initView();
        initData();
    }

    private void initData() {
        ObjectAnimator.ofFloat(RelativeLayout_left1,"rotation",0f,-90f).setDuration(0).start();
        if (app.isBle_connect_state() == true){
            btn_off_on.setBackgroundResource(R.drawable.btn_click_on);
        }else {
            btn_off_on.setBackgroundResource(R.drawable.btn_click_off);
        }
        info.setOnClickListener(this);
        back.setOnClickListener(this);

        angle = app.getAngle();
        seekBarRight.setMax(angle*2);
        seekBarRight.setProgress(angle);
        progressbar_bottom_right.setMax(angle);
        progressbar_top_right.setMax(angle);


        SeekBarLeft1.setMax(angle*2);
        SeekBarLeft2.setMax(angle*2);

        SeekBarLeft1.setProgress(angle);
        SeekBarLeft2.setProgress(angle);

        seekBarRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress>angle){
                    progressbar_top_right.setProgress(progress-angle);
                    progressbar_bottom_right.setProgress(0);
                }else if(progress<angle){
                    progressbar_bottom_right.setProgress(angle-progress);
                    progressbar_top_right.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    seekBar.setProgress(angle);
                    progressbar_top_right.setProgress(0);
                    progressbar_bottom_right.setProgress(0);
            }
        });

        SeekBarLeft1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(angle);
            }
        });

        SeekBarLeft2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(angle);
            }
        });
    }

    private void initView() {
        RelativeLayout_left1 = (RelativeLayout) findViewById(R.id.RelativeLayout_left1);
        image_left = (ImageView) findViewById(R.id.image_left);
        SeekBarLeft1 = (SeekBar) findViewById(R.id.SeekBarLeft1);
        SeekBarLeft2 = (SeekBar) findViewById(R.id.SeekBarLeft2);
        progressbar_bottom_right = (ProgressBar) findViewById(R.id.progressbar_bottom_right);
        progressbar_top_right = (ProgressBar) findViewById(R.id.progressbar_top_right);
        seekBarRight = (SeekBar)findViewById(R.id.SeekBarRight);
        back = (ImageView) findViewById(R.id.back);
        btn_off_on = (ImageButton) findViewById(R.id.btn_off_on);
        info = (ImageButton) findViewById(R.id.info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.info:
                Intent intent = new Intent(getApplicationContext(), ActivityProblem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
        }
    }
}
