package com.lesson.bindservice;

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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    protected boolean binded=false;
    private WeatherService weatherService;
    private TextView weather;
    private EditText textLocation;
    private Button button;
    ServiceConnection weatherServiceConnection=new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
          WeatherService.LocalWeatherBinder binder= (WeatherService.LocalWeatherBinder) iBinder;
          weatherService=binder.getService();
          binded=true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binded=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weather=findViewById(R.id.weather);
        textLocation=findViewById(R.id.editTextLocation);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeather();
            }
        });
    }
    public void showWeather()
    {
        String location=textLocation.getText().toString();
        String weatherCurrent=weatherService.getWeatherToday(location);
        weather.setText(weatherCurrent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,WeatherService.class);
        bindService(intent,weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(binded){
            unbindService(weatherServiceConnection);
            binded=false;
        }
    }
}