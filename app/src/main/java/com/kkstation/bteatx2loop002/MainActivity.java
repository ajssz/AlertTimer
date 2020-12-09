package com.kkstation.bteatx2loop002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "artxxx" ;
    private CountDownTimer countDownTimer01,countDownTimer02,countDownTimer03;
    private AdView mAdView;
    private NumberPicker numberPicker01, numberPicker02;
    public Integer intNP01=0, intNP02=0, intNP03=0;
    public Integer temp01=0,temp02=0;
    private TextView time01,time02,time03;
    private SwitchCompat swB1Sound, swB1Flash, swB1Vibrate, swB2Sound, swB2Flash, swB2Vibrate,swB3Repeat;
    private Vibrator vibrator;
    private Boolean swB1SoundisChecked = false;
    private Boolean swB1FlashisChecked = false;
    private Boolean swB1VibratorisChecked = false;
    private MediaPlayer playB1,playB2,playEnd,playCancel;
    private Boolean swB2SoundisChecked = false;
    private Boolean swB2FlashisChecked = false;
    private Boolean swB2VibratorisChecked = false;
    private Boolean swB3RepeatisChecked = false;
    private Boolean checkRepeat = false;
    private Button btnStartPause, btnReset;
    private Boolean mTimerRunning=false;
    private Long mLongTemp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time01 = findViewById(R.id.tvB1);
        time02 = findViewById(R.id.tvB2);
        time03 = findViewById(R.id.tvB3);
        swB1Sound = findViewById(R.id.switch1);
        swB1Flash = findViewById(R.id.switch2);
        swB1Vibrate = findViewById(R.id.switch3);
        swB2Sound = findViewById(R.id.switch4);
        swB2Flash = findViewById(R.id.switch5);
        swB2Vibrate = findViewById(R.id.switch6);
        swB3Repeat = findViewById(R.id.swRepeat);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        playB1 = MediaPlayer.create(this,R.raw.button01a);
        playB2 = MediaPlayer.create(this,R.raw.button03a);
        playCancel = MediaPlayer.create(this,R.raw.button05);
        playEnd = MediaPlayer.create(this,R.raw.button01b);
        btnStartPause = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    if(mTimerRunning){
                        pauseTimer();

                    }else{
                        Log.d(TAG, "onClick: startTimer()");
                        if(intNP03 != 0){
                            startTimer();
                        }else{
                            Toast.makeText(MainActivity.this, "Need number to LOOP ~~~ ", Toast.LENGTH_LONG).show();
                        }

                    }
                    Log.d(TAG, "onClick: checkRepeat " + checkRepeat);
                    checkRepeat = swB3RepeatisChecked;

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });


        /*
        @artchou setOnChangeListener for all the switch
         */
        swB1Sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    playB1.start();
                    swB1SoundisChecked = true;
                    swB1Sound.setTextColor(Color.RED);
                }else{
                    swB1SoundisChecked = false;
                    swB1Sound.setTextColor(Color.BLACK);
                }
            }
        });
        swB2Sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    playB2.start();
                    swB2SoundisChecked = true;
                    swB2Sound.setTextColor(Color.RED);
                }else{
                    swB2SoundisChecked = false;
                    swB2Sound.setTextColor(Color.BLACK);
                }
            }
        });

        swB1Flash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    blinkFlash(20);
                    swB1FlashisChecked = true;
                    swB1Flash.setTextColor(Color.RED);
                }else{
                    swB1FlashisChecked = false;
                    swB1Flash.setTextColor(Color.BLACK);
                }
            }
        });
        swB2Flash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    blinkFlash(50);
                    swB2FlashisChecked = true;
                    swB2Flash.setTextColor(Color.RED);
                }else{
                    swB2FlashisChecked = false;
                    swB2Flash.setTextColor(Color.BLACK);
                }
            }
        });
        swB1Vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    vibrator.vibrate(80);
                    swB1VibratorisChecked = true;
                    swB1Vibrate.setTextColor(Color.RED);

                }else{
                    swB1VibratorisChecked = false;
                    swB1Vibrate.setTextColor(Color.BLACK);

                }
            }
        });
        swB2Vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    vibrator.vibrate(100);
                    swB2VibratorisChecked = true;
                    swB2Vibrate.setTextColor(Color.RED);
                }else{
                    swB2VibratorisChecked = false;
                    swB2Vibrate.setTextColor(Color.BLACK);

                }
            }
        });

        swB3Repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){

                    swB3RepeatisChecked = true;
                    swB3Repeat.setTextColor(Color.RED);
                    Log.d(TAG, "onCheckedChanged: RepeatCheck = " + swB3RepeatisChecked);

                }else{
                    swB3RepeatisChecked = false;
                    swB3Repeat.setTextColor(Color.BLACK);
                    Log.d(TAG, "onCheckedChanged: RepeatCheck = " + swB3RepeatisChecked);
                }
            }
        });



        /*
        @artchou numberPicker01
         */
        numberPicker01 = findViewById(R.id.numberPicker01);
        numberPicker01.setMinValue(0);
        numberPicker01.setMaxValue(100);
        numberPicker01.setValue(Integer.valueOf(intNP01));
        numberPicker01.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker01, int i, int i1) {
                //Log.d(TAG, "onValueChange: numberPicker01 " + i1);
                //time01.setText("Beat1 " + i1 + " sec.");
                time01.setText(i1 + " sec.");
                intNP01 = i1;
                intNP03 = intNP01 + intNP02;
                time03.setText( intNP03 +" s");

            }
        });


        /*
        @artchou numberPicker02
         */
        numberPicker02 = findViewById(R.id.numberPicker02);
        numberPicker02.setMinValue(0);
        numberPicker02.setMaxValue(100);
        numberPicker02.setValue(Integer.valueOf(intNP02));
        numberPicker02.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker02, int i, int i1) {
                //Log.d(TAG, "onValueChange: numberPicker02 " + i1);
                //time02.setText("Beat2 " + i1 + " sec.");
                time02.setText( i1 + " sec.");
                intNP02 = i1;
                 intNP03 = intNP01 + intNP02;
//                time03.setText("Total loop " + intNP03 + " s");
                time03.setText( intNP03 + " s");
            }
        });

        showAD();
        checkFlash();
        checkVibrator();
        btnReset.setEnabled(false);

    } //-----end of onCreate

    private void startTimer() {
        mLongTemp = Long.valueOf(intNP03*1000);
        Log.d(TAG, "startTimer: mLongTemp " + mLongTemp +"  "+ intNP03);
        countDownTimer03 = new CountDownTimer(mLongTemp, 1000) {
            @Override
            public void onTick(long l) {
                Log.d(TAG, "onTick: of timer : " + l);
                mLongTemp = l;
                updateTimer03();
                btnStartPause.setText("Pause");
                btnReset.setEnabled(true);
                mTimerRunning = true;
            }

            @Override
            public void onFinish() {
                btnReset.setEnabled(false);
                btnStartPause.setText("START");
                time03.setText(String.valueOf(intNP03));
                mTimerRunning = false;
                if(swB1SoundisChecked || swB2SoundisChecked ){playEnd.start();}
                Log.d(TAG, "onFinish: countDownTimer03 Finished.");
                Log.d(TAG, "startTimer: end of CountDown Timer, and check if repeat :" + swB3RepeatisChecked);
                if(swB3RepeatisChecked == true){
                    startTimer();
                }
            }
        }.start();

        Log.d(TAG, "startTimer: end of CountDown Timer, and check if repeat :" + checkRepeat);

//        if(checkRepeat == true){
//            startTimer();
//        }
    }

    private void updateTimer03() {
        int temp01 =(int) (mLongTemp / 1000);
        Log.d(TAG, "updateTimer03: " + mLongTemp.toString() + "  " + temp01);
        time03.setText(String.valueOf(temp01));
        
        if(temp01 >= intNP02){
            Log.d(TAG, "updateTimer03: 上面");
                if(swB1SoundisChecked){ playB1.start();}
                if(swB1FlashisChecked){blinkFlash(20);}
                if(swB1VibratorisChecked){vibrator.vibrate(50);}
        }else{
            Log.d(TAG, "updateTimer03: 下面");
                if(swB2SoundisChecked){ playB2.start();}
                if(swB2FlashisChecked){blinkFlash(50);}
                if(swB2VibratorisChecked){vibrator.vibrate(100);}
        }
    }

    private void pauseTimer(){
        playCancel.start();
        countDownTimer03.cancel();
        mTimerRunning = false;
        btnStartPause.setText("START");
        btnReset.setEnabled(true);


    }

    private void resetTimer(){
        numberPicker01.setValue(0);
        numberPicker02.setValue(0);
        intNP01 =0;
        intNP02 =0;
        intNP03 = 0;
        time01.setText("---");
        time02.setText("---");
        time03.setText("---");
        mTimerRunning = false;
        btnReset.setEnabled(false);

    }

    private void showAD() {
        /*
        @artchou 201207 ADMOB
         */
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //@artchou 201206 debug admob status
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d(TAG, "onAdFailedToLoad: " + loadAdError);
            }

            @Override
            public void onAdOpened() {
                Log.d(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "onAdClicked: ");
            }

            @Override
            public void onAdLeftApplication() {
                Log.d(TAG, "onAdLeftApplication: ");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed: ");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Like this app, ratting 5 star in Google play.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Intent intent = new Intent(this, AboutPage.class);
                startActivity(intent);
                Toast.makeText(this, "show about page.", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void blinkFlash(int x)
    {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String myString = "01";
        //x = 1000;
        long blinkDelay = x; //Delay in ms
        for (int i = 0; i < myString.length(); i++) {
            if (myString.charAt(i) == '0') {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, true);
                } catch (CameraAccessException e) {
                }
            } else {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                }
            }
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkFlash(){
        /*
        add by artchou 201129
        check if Flash available in the devices
         */
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Log.d(TAG, "has camera flash ~~~");
        }else{
            swB1Flash.setEnabled(false);
            swB2Flash.setEnabled(false);
            swB1Flash.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            swB2Flash.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            Log.d(TAG, "swFlash check : NO Flash");
            Toast.makeText(this, "Has no FLASH, disable FLASH SWITCH !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkVibrator(){
        /*
        add by artchou 201203
        Check if vibrator is available in the device
         */
        if(!vibrator.hasVibrator()){
            swB1Vibrate.setEnabled(false);
            swB2Vibrate.setEnabled(false);

            swB1Vibrate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            swB2Vibrate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            Log.d(TAG, "swVibrator check : No vibrator. disable vibrator switch ");
            Toast.makeText(this, "Devics has no vibrator, disable switch", Toast.LENGTH_SHORT).show();
        }else{

            Log.d(TAG, "has vibrator ");
        }

        btnReset.setEnabled(false);

    }
    }
