package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeatherService extends Service {
    private static String LOG_TAG="WeatherService";
    private static final Map<String,String> weatherData=new HashMap<String, String>();
    public WeatherService() {
    }
    public final IBinder binder=new LocalWeatherBinder();
    public class LocalWeatherBinder extends Binder
    {
        public WeatherService getService()
        {
            return WeatherService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(LOG_TAG,"onBind");
        return  this.binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG,"onUnBind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG,"onReBind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }
    public String getWeatherToday(String location)
    {
        Date now=new Date();
        DateFormat dt=new SimpleDateFormat("dd-MM-yyyy");
        String datString=dt.format(now);
        String keyLovAndDay=location+"$"+datString;
        String weather=weatherData.get(keyLovAndDay);
        if(weather!=null) return weather;
        String[] weathers=new String[]{"Rainy","Hot","Cool","Warm","Snowy"};
        int i=new Random().nextInt(5);
        weather=weathers[i];
        weatherData.put(keyLovAndDay,weather);
        return weather;

    }
}
