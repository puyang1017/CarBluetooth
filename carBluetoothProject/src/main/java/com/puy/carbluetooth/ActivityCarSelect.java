package com.puy.carbluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by puy on 2016-12-15.
 */

public class ActivityCarSelect extends Activity implements View.OnClickListener{
    private LinearLayout Racing;
    private LinearLayout Jeep;
    private LinearLayout Loader;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);
        Racing =(LinearLayout) findViewById(R.id.Racing);
        Jeep =(LinearLayout) findViewById(R.id.Jeep);
        Loader =(LinearLayout) findViewById(R.id.Loader);
        back = (ImageView) findViewById(R.id.back);
        Racing.setOnClickListener(this);
        Jeep.setOnClickListener(this);
        Loader.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.Racing:
                intent = new Intent(this,ActivityRacing.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            case R.id.Jeep:
                intent = new Intent(this,ActivityJeep.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            case R.id.Loader:
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
