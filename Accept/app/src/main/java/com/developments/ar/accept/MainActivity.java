package com.developments.ar.accept;
import android.animation.ArgbEvaluator;
        import android.animation.ValueAnimator;
        import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
        import android.content.res.Resources;
        import android.graphics.Color;
        import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v4.widget.DrawerLayout;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.CompoundButton;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.ToggleButton;
        import java.io.IOException;
        import java.util.Timer;
        import java.util.TimerTask;


public class MainActivity extends Activity implements MediaPlayer.OnPreparedListener {
    protected DisplayMetrics displayMetrics;
    protected int i = 0;
    String colr[]={"#A7A7C0","#AC98D1","#C89F78","#A7BEC3","#ACC8C2","#B0C598","#C3B48A","#D3A680","#7ecbf7","#47b5f3","#C8B585","#BACB9F","#A8E3C1","#BEB990","#C8A3A2","#6CC1C7","#93D494","#B4E06C","#AB90B8","#9697C7"};
    String status[]={"When you own your breath, nobody can steal your peace.","What we call ’I’ is just a swinging door, which moves when we inhale and when we exhale.",
            "When the breath wanders the mind also is unsteady. But when the breath is calmed the mind too will be still, and the yogi achieves long life. Therefore, one should learn to control the breath.","A healthy mind has an easy breath.","If you woke up breathing, congratulations! You have another chance.","Inhale, and God approaches you. Hold the inhalation, and God remains with you. Exhale, and you approach God. Hold the exhalation, and surrender to God.","Breathing is the greatest pleasure in life.","Smile, breathe and go slowly."};
    public ImageView relativeLayout;
    public Timer timer;
    public ImageView z_img;
    public int z_dp1=0;
    public int z_dp2=0;
    public int f_dp1=0;
    public int f_dp2=0;
    public Timer timer1;
    public int count=0;
    public TextView z_txt;
    public int s_in=0;
    public int s_out=0;
    public int s_hold1=0;
    public int s_hold2=0;
    public int opt1=0;
    public int opt2=0;
    public int opt3=0;
    public int initial_hint=0;
    public int[] s_seek;
    public int change_hint=0;
    private Sql_db db;
    private int choice=1;
    private int s_no=0;
    private int drawer_no=0;
    SharedPreferences prefs = null;
    public int[] values;
    public MediaPlayer player;
    public int tap=0;
    LinearLayout tap_view;
    private static BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, Splash_screen.class);
        startActivityForResult(intent, 3333);


        db = new Sql_db(this);
        prefs = getSharedPreferences("com.developments.ar.xhalr", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            db.insert_db();
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        db.update_band_feature();

        relativeLayout=(ImageView)findViewById(R.id.b_circle);
        z_txt=(TextView)findViewById(R.id.z_txt);
        z_img=(ImageView)findViewById(R.id.z_circle);
        tap_view=(LinearLayout)findViewById(R.id.tap_view);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        //Log.d("width",width+"...");
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.setFocusableInTouchMode(false);
        LinearLayout drawer = (LinearLayout) findViewById(R.id.drawer_div1);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) drawer.getLayoutParams();
        params.width = width;
        drawer.setLayoutParams(params);

        ImageView b_circle=(ImageView)findViewById(R.id.b_circle);

        if(width<=500){
            int b_dp1 = dpToPx(350);
            f_dp1= dpToPx(10);
            f_dp2=f_dp1*2;
            z_dp1 = dpToPx(120);
            z_dp2 =z_dp1*2;
            b_circle.getLayoutParams().height =b_dp1;
            b_circle.getLayoutParams().width =b_dp1;
            z_img.getLayoutParams().height =z_dp1;
            z_img.getLayoutParams().width =z_dp1;
            z_txt.getLayoutParams().height =z_dp1;
            z_txt.getLayoutParams().width =z_dp1;
            z_txt.setTextSize(f_dp1);

        }
        else if(width>500 && width<=600){
            int b_dp1 = dpToPx(370);
            f_dp1= dpToPx(12);
            f_dp2=f_dp1*2;
            z_dp1 = dpToPx(135);
            z_dp2 =z_dp1*2;
            b_circle.getLayoutParams().height =b_dp1;
            b_circle.getLayoutParams().width =b_dp1;
            z_img.getLayoutParams().height =z_dp1;
            z_img.getLayoutParams().width =z_dp1;
            z_txt.getLayoutParams().height =z_dp1;
            z_txt.getLayoutParams().width =z_dp1;
            z_txt.setTextSize(f_dp1);
        }

        else{
            int b_dp1 = dpToPx(390);
            f_dp1= dpToPx(14);
            f_dp2=f_dp1*2;
            z_dp1 = dpToPx(150);
            z_dp2 =z_dp1*2;
            b_circle.getLayoutParams().height =b_dp1;
            b_circle.getLayoutParams().width =b_dp1;
            z_img.getLayoutParams().height =z_dp1;
            z_img.getLayoutParams().width =z_dp1;
            z_txt.getLayoutParams().height =z_dp1;
            z_txt.getLayoutParams().width =z_dp1;
            z_txt.setTextSize(f_dp1);
        }


        initial();
    }

    public void initial(){
        initial_hint=1;
        z_txt=(TextView)findViewById(R.id.z_txt);
        Typeface font = Typeface.createFromAsset(getAssets(), "HelveticaNeueBold.ttf");
        z_txt.setTypeface(font);
        timer=new Timer();
        MyTimerTask myTimerTask=new MyTimerTask();
        timer.schedule(myTimerTask, 10, 4000);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, 400);


        values=db.get_value();
        s_in=values[0];
        s_hold1=values[1];
        s_out=values[2];
        s_hold2=values[3];
        opt1=values[4];
        opt2=values[5];
        opt3=values[6];
        s_no=values[7];

        s_seek= new int[]{values[0], values[1], values[2], values[3]};
        timer1 = new Timer();
        seekbar_fun();
        switch_fun();
        switch_day_night(opt1);
        tap=1;
        tap_fun();


    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    i++;
                    if (i==20){
                        i=1;
                    }
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), Color.parseColor(colr[i - 1]), Color.parseColor(colr[i]));
                    colorAnimation.setDuration(4000);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {

                            relativeLayout.setBackgroundColor((int) animator.getAnimatedValue());
                            z_txt.setTextColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                }
            });
        }
    }

    public void zoom_in(){
        Animation zoomin = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin);
        zoomin.setDuration(s_in * 1000);
        z_img.startAnimation(zoomin);
        show_word_fun();
        if (opt2==2){
            z_txt.setText("inhale");
            z_txt.startAnimation(zoomin);
        }
        play_audio(1,opt3);

    }

    public void zoom_out(){
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) z_img.getLayoutParams();
        params.width =z_dp1;
        params.height =z_dp1;
        z_img.setLayoutParams(params);

        Animation zoomout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomout);
        zoomout.setDuration(s_out * 1000);
        z_img.startAnimation(zoomout);
        show_word_fun();
        if (opt2==2){
            z_txt.setText("exhale");
            z_txt.setLayoutParams(params);
            z_txt.setTextSize(f_dp1);
            z_txt.startAnimation(zoomout);
        }
        play_audio(2,opt3);
    }

    public void hold1() {
        z_img.clearAnimation();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) z_img.getLayoutParams();
        params.width =z_dp2;
        params.height =z_dp2;
        z_img.setLayoutParams(params);
        show_word_fun();
        if (opt2==2){
            z_txt.setText("hold");
            z_txt.clearAnimation();
            z_txt.setLayoutParams(params);
            z_txt.setTextSize(f_dp2);
        }
        play_audio(3,opt3);

    }

    public void hold2() {
        z_img.clearAnimation();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) z_img.getLayoutParams();
        params.width =z_dp1;
        params.height =z_dp1;
        z_img.setLayoutParams(params);
        show_word_fun();
        if (opt2==2){
            z_txt.setText("hold");
            z_txt.clearAnimation();
            z_txt.setLayoutParams(params);
            z_txt.setTextSize(f_dp1);
        }
        play_audio(3, opt3);
    }

    public void hold() {
        z_img.clearAnimation();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) z_img.getLayoutParams();
        params.width =z_dp1;
        params.height =z_dp1;
        z_img.setLayoutParams(params);
        show_word_fun();
        if (opt2==2){
            z_txt.setText("hold");
            z_txt.clearAnimation();
            z_txt.setLayoutParams(params);
            z_txt.setTextSize(f_dp1);
        }
    }

    public void start_animation(int c) {
        count=c;
        z_img = (ImageView) findViewById(R.id.z_circle);
        final int total=s_in+s_out+s_hold1+s_hold2;
        final int h1=s_in;
        final int h2=s_in+s_hold1;
        final int h3=s_in+s_out+s_hold1;
        timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count == -4) {
                            z_txt.setText("Ready?");
                        } else if (count == -3) {
                            z_txt.setText("3");
                        } else if (count == -2) {
                            z_txt.setText("2");
                        } else if (count == -1) {
                            z_txt.setText("1");
                        } else if (count == 0) {
                            zoom_in();
                        } else if (count == h1 && s_hold1 != 0) {
                            hold1();
                        } else if (count == h2) {
                            zoom_out();
                        } else if (count == h3 && s_hold2 != 0) {
                            hold2();
                        }
                        count++;

                        if (count >= total) {
                            count = 0;
                        }


                    }
                });
            }
        }, 1000, 1000);
    }

    public void choice_fun(View view){
        TextView txt_in=(TextView)findViewById(R.id.txt_in);
        TextView txt_hold1=(TextView)findViewById(R.id.txt_hold1);
        TextView txt_out=(TextView)findViewById(R.id.txt_out);
        TextView txt_hold2=(TextView)findViewById(R.id.txt_hold2);

        final SeekBar d_in = (SeekBar) findViewById(R.id.d_seek_in);
        final SeekBar d_hold1 = (SeekBar) findViewById(R.id.d_seek_hold1);
        final SeekBar d_out = (SeekBar) findViewById(R.id.d_seek_out);
        final SeekBar d_hold2 = (SeekBar) findViewById(R.id.d_seek_hold2);


        TextView opt11=(TextView)findViewById(R.id.opt11);
        TextView opt22=(TextView)findViewById(R.id.opt22);
        TextView opt33=(TextView)findViewById(R.id.opt33);
        TextView opt44=(TextView)findViewById(R.id.opt44);
        if (opt1==1){
            opt11.setBackgroundColor(Color.WHITE);
            opt22.setBackgroundColor(Color.WHITE);
            opt33.setBackgroundColor(Color.WHITE);
            opt44.setBackgroundColor(Color.WHITE);
        }
        else{
            opt11.setBackgroundColor(getResources().getColor(R.color.black));
            opt22.setBackgroundColor(getResources().getColor(R.color.black));
            opt33.setBackgroundColor(getResources().getColor(R.color.black));
            opt44.setBackgroundColor(getResources().getColor(R.color.black));
        }

        switch(view.getId())
        {
            case R.id.opt1:
                choice=1;
                switch_seek(1);
                change_hint=1;
                opt11.setBackgroundResource(R.color.opt_col);
                txt_in.setText(s_seek[0]+"");
                txt_hold1.setText(s_seek[1]+"");
                txt_out.setText(s_seek[2]+ "");
                txt_hold2.setText(s_seek[3]+"");
                break;
            case R.id.opt2:
                choice=2;
                switch_seek(2);
                change_hint=0;
                opt22.setBackgroundResource(R.color.opt_col);
                d_in.setProgress(4);
                d_hold1.setProgress(4);
                d_out.setProgress(4);
                d_hold2.setProgress(4);
                txt_in.setText("4");
                txt_hold1.setText("4");
                txt_out.setText("4");
                txt_hold2.setText("4");
                break;
            case R.id.opt3:
                choice=3;
                switch_seek(2);
                change_hint=0;
                opt33.setBackgroundResource(R.color.opt_col);
                d_in.setProgress(7);
                d_hold1.setProgress(4);
                d_out.setProgress(8);
                d_hold2.setProgress(0);
                txt_in.setText("7");
                txt_hold1.setText("4");
                txt_out.setText("8");
                txt_hold2.setText("0");
                break;
            case R.id.opt4:
                choice=4;
                switch_seek(2);
                change_hint=0;
                opt44.setBackgroundResource(R.color.opt_col);
                d_in.setProgress(7);
                d_hold1.setProgress(0);
                d_out.setProgress(7);
                d_hold2.setProgress(0);
                txt_in.setText("7");
                txt_hold1.setText("0");
                txt_out.setText("7");
                txt_hold2.setText("0");
                break;
        }
    }

    public void switch_seek(int n){
        final SeekBar seek_in = (SeekBar) findViewById(R.id.seek_in);
        final SeekBar seek_out = (SeekBar) findViewById(R.id.seek_out);
        final SeekBar seek_hold1 = (SeekBar) findViewById(R.id.seek_hold1);
        final SeekBar seek_hold2 = (SeekBar) findViewById(R.id.seek_hold2);

        final SeekBar d_in = (SeekBar) findViewById(R.id.d_seek_in);
        final SeekBar d_hold1 = (SeekBar) findViewById(R.id.d_seek_hold1);
        final SeekBar d_out = (SeekBar) findViewById(R.id.d_seek_out);
        final SeekBar d_hold2 = (SeekBar) findViewById(R.id.d_seek_hold2);
        if (n==1){
            seek_in.setVisibility(View.VISIBLE);
            seek_out.setVisibility(View.VISIBLE);
            seek_hold1.setVisibility(View.VISIBLE);
            seek_hold2.setVisibility(View.VISIBLE);

            d_in.setVisibility(View.GONE);
            d_hold1.setVisibility(View.GONE);
            d_out.setVisibility(View.GONE);
            d_hold2.setVisibility(View.GONE);
        }
        else if (n==2){
            seek_in.setVisibility(View.GONE);
            seek_out.setVisibility(View.GONE);
            seek_hold1.setVisibility(View.GONE);
            seek_hold2.setVisibility(View.GONE);

            d_in.setVisibility(View.VISIBLE);
            d_hold1.setVisibility(View.VISIBLE);
            d_out.setVisibility(View.VISIBLE);
            d_hold2.setVisibility(View.VISIBLE);
            d_in.setEnabled(false);
            d_hold1.setEnabled(false);
            d_out.setEnabled(false);
            d_hold2.setEnabled(false);
        }
    }

    public void ham_open(View view) {
//        TextView txt=(TextView)findViewById(R.id.tap_txt);
        ImageView img=(ImageView)findViewById(R.id.tap_img);
        tap_view.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
//        txt.setVisibility(View.GONE);
        drawer_no=1;
        tap=1;
        tap_fun();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        ImageView ham_img = (ImageView) findViewById(R.id.ham_icon);
        ham_img.setVisibility(View.GONE);

        TextView status_txt=(TextView)findViewById(R.id.status_txt);
        status_txt.setText(""+status[s_no]);


        SeekBar seek_in = (SeekBar) findViewById(R.id.seek_in);
        SeekBar seek_hold1 = (SeekBar) findViewById(R.id.seek_hold1);
        SeekBar seek_out = (SeekBar) findViewById(R.id.seek_out);
        SeekBar seek_hold2 = (SeekBar) findViewById(R.id.seek_hold2);
        TextView txt_in=(TextView)findViewById(R.id.txt_in);
        TextView txt_out=(TextView)findViewById(R.id.txt_out);
        ToggleButton togg1=(ToggleButton)findViewById(R.id.togg1);
        ToggleButton togg2=(ToggleButton)findViewById(R.id.togg2);
        ToggleButton togg3=(ToggleButton)findViewById(R.id.togg3);

        if (opt1==2){
            togg1.setChecked(true);
        }
        if (opt2==2){
            togg2.setChecked(true);
        }
        if (opt3==2){
            togg3.setChecked(true);
        }


        seek_in.setProgress(values[0] - 1);
        seek_hold1.setProgress(values[1]);
        seek_out.setProgress(values[2] - 1);
        seek_hold2.setProgress(values[3]);

        if(choice==1){
            if (values[0]==1){
                txt_in.setText("1");
            }
            if (values[2]==1){
                txt_out.setText("1");
            }
        }

    }

    public void ham_close(){
        tap_view.setVisibility(View.VISIBLE);
        drawer_no=0;
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView ham_img = (ImageView) findViewById(R.id.ham_icon);
                ImageView img=(ImageView)findViewById(R.id.tap_img);
//                TextView txt=(TextView)findViewById(R.id.tap_txt);
                img.setVisibility(View.VISIBLE);
//                txt.setVisibility(View.VISIBLE);
                ham_img.setVisibility(View.VISIBLE);
            }
        }, 400);



        SeekBar seek_in = (SeekBar) findViewById(R.id.seek_in);
        SeekBar seek_hold1 = (SeekBar) findViewById(R.id.seek_hold1);
        SeekBar seek_out = (SeekBar) findViewById(R.id.seek_out);
        SeekBar seek_hold2 = (SeekBar) findViewById(R.id.seek_hold2);


        s_in = seek_in.getProgress()+1;
        s_hold1 = seek_hold1.getProgress();
        s_out = seek_out.getProgress()+1;
        s_hold2 = seek_hold2.getProgress();
        s_no++;
        if (status.length-1<s_no){
            s_no=0;
        }
        db.update_db(s_in, s_hold1, s_out, s_hold2, opt1, opt2, opt3, s_no);
        values=db.get_value();
        if (choice==2){
            s_in=4;
            s_hold1=4;
            s_out=4;
            s_hold2=4;
        }
        else if (choice==3){
            s_in=7;
            s_hold1=4;
            s_out=8;
            s_hold2=0;
        }
        else if (choice==4){
            s_in=7;
            s_hold1=0;
            s_out=7;
            s_hold2=0;
        }

        tap=1;
        tap_fun();

    }

    public void close_ham(View view) {
        ham_close();
    }

    public void tap(View view){
        tap_fun();
    }

    public void tap_fun(){
        ImageView img=(ImageView)findViewById(R.id.tap_img);
//        TextView txt=(TextView)findViewById(R.id.tap_txt);
        ImageView ham=(ImageView)findViewById(R.id.ham_icon);
        Log.d("tap", "go");
        if (tap==0){
            if (initial_hint==1){
                start_animation(4);
            }
            else{
                timer.cancel();
                timer1.cancel();
                hold();
                z_txt.setText("Start");
                timer=new Timer();
                MyTimerTask myTimerTask=new MyTimerTask();
                timer.schedule(myTimerTask, 10, 4000);
                start_animation(0);
            }

//            if (opt3==2){
//                play_audio(opt3);
//            }
//            txt.setText("Stop");
//            txt.setTextColor(getResources().getColor(R.color.black3));
            tap=2;
            tap_view.setBackgroundColor(Color.TRANSPARENT);
            img.setImageResource(R.drawable.stop);
            ham.setImageResource(R.drawable.ham1);

        }
        else if (tap==1){
            if (initial_hint!=1) {
                timer.cancel();
                timer1.cancel();
                hold();
                z_txt.setText("Start");

//                if (opt3==2){
//                    play_audio(1);
//                }
            }
            tap = 0;
//            txt.setText("Play");
            img.setImageResource(R.drawable.play);
//            txt.setTextColor(Color.WHITE);
            ham.setImageResource(R.drawable.ham2);


            if (opt1==1){;
                tap_view.setBackgroundResource(R.drawable.tap_background);
            }
            else{
                tap_view.setBackgroundResource(R.drawable.tap_background2);
            }

        }
        if (tap==2){
            tap=1;
        }

    }

    public void seekbar_fun() {
        final TextView txt_in = (TextView) findViewById(R.id.txt_in);
        final TextView txt_hold1 = (TextView) findViewById(R.id.txt_hold1);
        final TextView txt_out = (TextView) findViewById(R.id.txt_out);
        final TextView txt_hold2 = (TextView) findViewById(R.id.txt_hold2);

        final SeekBar seek1 = (SeekBar) findViewById(R.id.seek_in);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                change_choice();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int n = progress + 1;
                txt_in.setText("" + n);
                s_in=n;
                s_seek[0]=n;
            }
        });

        final SeekBar seek2 = (SeekBar) findViewById(R.id.seek_hold1);
        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                change_choice();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int n = progress;
                txt_hold1.setText("" + n);
                s_hold1 = n;
                s_seek[1] = n;
            }
        });

        final SeekBar seek3 = (SeekBar) findViewById(R.id.seek_out);
        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                change_choice();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int n = progress + 1;
                txt_out.setText("" + n);
                s_out = n;
                s_seek[2] = n;
            }
        });

        final SeekBar seek4 = (SeekBar) findViewById(R.id.seek_hold2);
        seek4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                change_choice();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int n = progress;
                txt_hold2.setText("" + n);
                s_hold2 = n;
                s_seek[3] = n;
            }
        });
    }

    public void switch_fun() {
        ToggleButton toggle1 = (ToggleButton)  findViewById(R.id.togg1);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    opt1 = 1;
                } else {
                    opt1 = 2;
                }
                switch_day_night(opt1);
            }

        });

        ToggleButton toggle2 = (ToggleButton)  findViewById(R.id.togg2);
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    opt2 = 1;
                } else {
                    opt2 = 2;
                }
                show_word_fun();
            }

        });

        ToggleButton toggle3 = (ToggleButton)  findViewById(R.id.togg3);
        toggle3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    opt3 = 1;
                } else {
                    opt3 = 2;
                }

            }

        });
    }

    public void switch_day_night(int n){
        FrameLayout content=(FrameLayout)findViewById(R.id.container);
        ImageView b_circle=(ImageView)findViewById(R.id.b_circle);
        ImageView z_circle=(ImageView)findViewById(R.id.z_circle);
        ImageView ham=(ImageView)findViewById(R.id.ham_icon);
        ImageView title=(ImageView)findViewById(R.id.title);
        LinearLayout d_body=(LinearLayout)findViewById(R.id.d_body);
        TextView b_txt=(TextView)findViewById(R.id.status_txt);
        TextView opt1=(TextView)findViewById(R.id.opt1);
        TextView opt2=(TextView)findViewById(R.id.opt2);
        TextView opt3=(TextView)findViewById(R.id.opt3);
        TextView opt4=(TextView)findViewById(R.id.opt4);
        TextView txt_in=(TextView)findViewById(R.id.txt_in);
        TextView txt_hold1=(TextView)findViewById(R.id.txt_hold1);
        TextView txt_out=(TextView)findViewById(R.id.txt_out);
        TextView txt_hold2=(TextView)findViewById(R.id.txt_hold2);
        TextView t1=(TextView)findViewById(R.id.t1);
        TextView t2=(TextView)findViewById(R.id.t2);
        TextView t3=(TextView)findViewById(R.id.t3);
        TextView t4=(TextView)findViewById(R.id.t4);
        TextView t5=(TextView)findViewById(R.id.t5);
        TextView t6=(TextView)findViewById(R.id.t6);
        TextView t7=(TextView)findViewById(R.id.t7);
        TextView t8=(TextView)findViewById(R.id.t8);
        TextView opt11=(TextView)findViewById(R.id.opt11);
        TextView opt22=(TextView)findViewById(R.id.opt22);
        TextView opt33=(TextView)findViewById(R.id.opt33);
        TextView opt44=(TextView)findViewById(R.id.opt44);

        if (n==1){
            content.setBackgroundColor(getResources().getColor(R.color.white1));
            d_body.setBackgroundColor(getResources().getColor(R.color.white1));
            b_txt.setTextColor(getResources().getColor(R.color.black1));
            opt1.setTextColor(getResources().getColor(R.color.black1));
            opt2.setTextColor(getResources().getColor(R.color.black1));
            opt3.setTextColor(getResources().getColor(R.color.black1));
            opt4.setTextColor(getResources().getColor(R.color.black1));
            t1.setTextColor(getResources().getColor(R.color.black1));
            t2.setTextColor(getResources().getColor(R.color.black1));
            t3.setTextColor(getResources().getColor(R.color.black1));
            t4.setTextColor(getResources().getColor(R.color.black1));
            t5.setTextColor(getResources().getColor(R.color.black1));
            t6.setTextColor(getResources().getColor(R.color.black1));
            t7.setTextColor(getResources().getColor(R.color.black1));
            t8.setTextColor(getResources().getColor(R.color.black1));
            txt_in.setTextColor(getResources().getColor(R.color.black1));
            txt_out.setTextColor(getResources().getColor(R.color.black1));
            txt_hold1.setTextColor(getResources().getColor(R.color.black1));
            txt_hold2.setTextColor(getResources().getColor(R.color.black1));
            b_circle.setImageResource(R.drawable.tes);
            z_circle.setImageResource(R.drawable.cirl1);
            ham.setImageResource(R.drawable.ham1);
            title.setImageResource(R.drawable.title);
            opt11.setBackgroundColor(Color.WHITE);
            opt22.setBackgroundColor(Color.WHITE);
            opt33.setBackgroundColor(Color.WHITE);
            opt44.setBackgroundColor(Color.WHITE);
        }
        else {
            content.setBackgroundColor(Color.BLACK);
            d_body.setBackgroundColor(getResources().getColor(R.color.black));
            b_txt.setTextColor(getResources().getColor(R.color.white));
            opt1.setTextColor(getResources().getColor(R.color.white));
            opt2.setTextColor(getResources().getColor(R.color.white));
            opt3.setTextColor(getResources().getColor(R.color.white));
            opt4.setTextColor(getResources().getColor(R.color.white));
            t1.setTextColor(getResources().getColor(R.color.white));
            t2.setTextColor(getResources().getColor(R.color.white));
            t3.setTextColor(getResources().getColor(R.color.white));
            t4.setTextColor(getResources().getColor(R.color.white));
            t5.setTextColor(getResources().getColor(R.color.white));
            t6.setTextColor(getResources().getColor(R.color.white));
            t7.setTextColor(getResources().getColor(R.color.white));
            t8.setTextColor(getResources().getColor(R.color.white));
            txt_in.setTextColor(getResources().getColor(R.color.white));
            txt_out.setTextColor(getResources().getColor(R.color.white));
            txt_hold1.setTextColor(getResources().getColor(R.color.white));
            txt_hold2.setTextColor(getResources().getColor(R.color.white));
            opt11.setBackgroundColor(getResources().getColor(R.color.black));
            opt22.setBackgroundColor(getResources().getColor(R.color.black));
            opt33.setBackgroundColor(getResources().getColor(R.color.black));
            opt44.setBackgroundColor(getResources().getColor(R.color.black));
            b_circle.setImageResource(R.drawable.tes2);
            z_circle.setImageResource(R.drawable.cirl2);
            ham.setImageResource(R.drawable.ham2);
            title.setImageResource(R.drawable.title);
        }

        if (choice==1)
        {
            opt11.setBackgroundResource(R.color.opt_col);
        }
        else if (choice==2){
            opt22.setBackgroundResource(R.color.opt_col);
        }
        else if(choice==3) {
            opt33.setBackgroundResource(R.color.opt_col);
        }
        else{
            opt44.setBackgroundResource(R.color.opt_col);
        }


    }

    public void show_word_fun(){
        if (opt2==1){
            z_txt.setVisibility(View.GONE);
        }
        else{
            z_txt.setVisibility(View.VISIBLE);
        }
    }

    public void play_audio(int n,int opt){
        if(opt==1) {
            // player.stop();
        }
        if(n==1 && opt==2){
            player = MediaPlayer.create(MainActivity.this, R.raw.inhale);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }

                ;
            });
        }
        else if(n==2 && opt==2){
            player = MediaPlayer.create(MainActivity.this, R.raw.exhale);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
                ;
            });
        }
        else if(n==3 && opt==2){
            player = MediaPlayer.create(MainActivity.this, R.raw.hold);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
                ;
            });
        }
    }

    public void change_choice(){
        if (change_hint==0){
            TextView opt11=(TextView)findViewById(R.id.opt11);
            TextView opt22=(TextView)findViewById(R.id.opt22);
            TextView opt33=(TextView)findViewById(R.id.opt33);
            TextView opt44=(TextView)findViewById(R.id.opt44);
            opt11.setBackgroundResource(R.color.opt_col);
            if(opt1==1){
                opt22.setBackgroundColor(Color.WHITE);
                opt33.setBackgroundColor(Color.WHITE);
                opt44.setBackgroundColor(Color.WHITE);
            }
            else{
                opt22.setBackgroundColor(getResources().getColor(R.color.black));
                opt33.setBackgroundColor(getResources().getColor(R.color.black));
                opt44.setBackgroundColor(getResources().getColor(R.color.black));
            }

            change_hint=1;
        }

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode==3333 && resultCode  == RESULT_CANCELED) {
               finish();
            }
            else if (requestCode==8888 && resultCode  == RESULT_CANCELED) {
                Blutooth_DialogBox dialogBox = new Blutooth_DialogBox(this);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
            }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (opt3==2 && tap==0){
//            player.pause();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        tap=1;
        tap_fun();
//        if (opt3==2 && tap==0){
//           // player.stop();
//        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        player = new MediaPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (initial_hint==1){
            initial_hint=0;
        }
        else {
            tap=1;
            tap_fun();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_no==1)
        {
            ham_close();
        }
        else{
            super.onBackPressed();
        }
    }


    // Version 2 process

    public void tap_connect_to_band(View view) {
//        Intent intent = new Intent(MainActivity.this, Bluetooth_Activity.class);
//        startActivityForResult(intent, 5555);
        String[] content={"",""};
        content=db.get_device();
        try{
            if (content[0].equals("")||content[1].equals("")){
                Blutooth_DialogBox dialogBox = new Blutooth_DialogBox(this);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
            }
            else{
                Intent intent = new Intent(this, Device_Activity.class);
                intent.putExtra("b_name",content[0]);
                intent.putExtra("b_addr", content[1]);
                intent.putExtra("from", 1);
                startActivityForResult(intent, 8888);
            }
        }
        catch (Exception e){
            Blutooth_DialogBox dialogBox = new Blutooth_DialogBox(this);
            dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogBox.show();
        }
    }




    public static boolean setBluetooth(boolean enable) {
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }

        if (bluetoothAdapter == null)
        {
            Log.e("errror", "This device does not have a bluetooth adapter");
        }
        return true;
    }

    //A6:A9:C5:65:92:74



}