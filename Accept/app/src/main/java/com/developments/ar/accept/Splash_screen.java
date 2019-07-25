package com.developments.ar.accept;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Admin on 2/10/2018.
 */
public class Splash_screen extends Activity  {
    protected DisplayMetrics displayMetrics;
    String status[]={"ACCEPT you are the greatest gift.","ACCEPT kindness, as it is the root cause of peace.","ACCEPT we are all interconnected and interrelated.","ACCEPT you are the only one who is responsible for your happiness.",
            "ACCEPT judgmentalism breaks relationship.","Pranayama is a practice of ACCEPTING the cosmic energy.","Let go of the life we have planned, so as to ACCEPT the one that is waiting for us.","Accept finite disappointment, but never lose infinite hope.","ACCEPT failure, everyone fails at something. Accept there is endless possibility to success.","The moment we ACCEPT our limits; we go beyond them.","ACCEPT the challenges so that you can feel the exhilaration of victory.","The curious paradox is that when I ACCEPT myself just as I am, then I can change.","ACCEPT yourself, love yourself.","Have the courage to ACCEPT what you couldn't change.","Sometimes you have to ACCEPT you can't win all the time.","You simply have to ACCEPT that your demons are a part of you.","Growth begins when we begin to ACCEPT our own weakness.","First, ACCEPT sadness. Realize that without losing, winning isn't so great.","ACCEPT what people offer, pea their hate and digest their love.","You don't need to be accepted by others. You need to ACCEPT yourself.","The keys to patience are acceptance and faith. ACCEPT things as they are.","Leader acts alone, by ACCEPTING everything around.","ACCEPT the consequences without complaining, then learning begins.","Listen to yourself, You're stupid, let's ACCEPT that and move on.",
            "See yourself as you really are. ACCEPT the good and the bad. Only then can you truly share yourself with another.","What you ACCEPT also ACCEPTS you.","Strength comes when you learn to deal with your loneliness and ACCEPT.","ACCEPT the present, don’t Attach with the past or Admire the future.","Know, ACCEPT and make peace with you own self to live soulfully.","Life and death, joy and sorrow, gain and loss; These dualities cannot be avoided. Learn to ACCEPT what you cannot change.","To realize that everything in the universe is connected is to both ACCEPT our insignificance and understand our importance in it.","My love, we can close our eyes but we cannot stop the sunrise, ACCEPT life as it is.","Don't expect anything from anyone unless you are prepared to ACCEPT denial.","The greatest loss lies in our inability to ACCEPT loss.","ACCEPT practice to enjoy development.","ACCEPT pressure has the power to create a diamond.","It is better to ACCEPT the truth than a temporary lie.","Truth was never meant to make you comfortable, unless you stand in the middle of it with ACCEPTANCE.","ACCEPT everyone with love, you will find that everyone is loving and kind.",
            "ACCEPT the changes with love.","ACCEPT sufferings with smile, as they are purifier and bring closer to God.","The heart cannot embrace what the mind does not ACCEPT."};
    private Sql_db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        db = new Sql_db(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "HelveticaNeueBold.ttf");
        Typeface font_t = Typeface.createFromAsset(getAssets(), "HelveticaNeue Thin.ttf");
        TextView txt1=(TextView)findViewById(R.id.sc_1);
        TextView txt2=(TextView)findViewById(R.id.sc_2);
        int s_no=db.get_splash();
        txt1.setTypeface(font_t);
        txt2.setTypeface(font);
        if(s_no<status.length){
            txt1.setText(""+status[s_no]);
            s_no++;
        }
        else{
            txt1.setText(""+status[0]);
            s_no=1;
        }
        db.update_splash(s_no);
    }

    public void tap_fun(View view){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
