package com.example.pedometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener , View.OnClickListener {
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private static final String CALORIE_BAN = "Calorie BAN: ";
    private int numSteps;
    TextView TvSteps;
    ImageButton BtnStart;
    ImageButton  BtnStop;
    ImageButton button;
    TextView CalBurn;
    ImageButton map;
    ImageButton start,stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.b1);
        map=(ImageButton)findViewById(R.id.map);

        start=(ImageButton)findViewById(R.id.button);
        stop=(ImageButton) findViewById(R.id.button2);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2();
            }
        });


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (ImageButton) findViewById(R.id.btn_start);
        BtnStop = (ImageButton) findViewById(R.id.btn_stop);
        CalBurn = (TextView) findViewById(R.id.t1);



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);

            }
        });



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);

        float calCount = (float) (numSteps * (0.04));

       CalBurn.setText(CALORIE_BAN+calCount);

    }



    public void MainActivity2(){

        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if(v==start){startService(new Intent(this,MyService.class));
        }else if(v==stop){stopService(new Intent(this,MyService.class));
        }
    }

}