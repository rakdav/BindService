package com.example.bindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean binded=false;
    private WeatherService weatherService;
    private TextView weatherText;
    private EditText locationText;
    private Button bt;
    ServiceConnection weatherserviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherService.LocalWeatherBinder binder=(WeatherService.LocalWeatherBinder)service;
            weatherService=binder.getService();
            binded=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherText=findViewById(R.id.text_weather);
        locationText=findViewById(R.id.text_input_location);
        bt=findViewById(R.id.button_weather);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,WeatherService.class);
        this.bindService(intent,weatherserviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(binded)
        {
            this.unbindService(weatherserviceConnection);
            binded=false;
        }
    }
}
