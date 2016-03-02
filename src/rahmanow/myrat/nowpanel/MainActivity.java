package rahmanow.myrat.nowpanel;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends FragmentActivity implements OnClickListener,OnLongClickListener {

    public static final String CMDTOGGLEPAUSE = "togglepause";
    public static final String CMDPREVIOUS = "previous";
    public static final String CMDNEXT = "next";
    public static final String CMDNAME = "command";
    public static final String CMDSTOP = "stop";
    public static final String CMDPLAY = "play";

    private ImageView b_info;
    private ImageView a_switch;
    private ImageView d_exit;
    private LinearLayout lmain;
    private SeekBar sBar;

    //Aydym player
    private ImageView bplay;
    private ImageView bprev;
    private ImageView bnext;
    private SeekBar   soundbar;

    private ImageView btn_settings;
    private ImageView btn_camera;
    private ImageView btn_calc;
    private ImageView btn_flash;
    private ImageView btn_clock;

    private ConnectivityManager conman;

    private TextView view;
    private TextView gun;

    //static final int RESULT_ENABLE = 1;
    //static final int NETIJE = 2;

    private boolean fonarBarmy;
    boolean isPlaying;

    private TelephonyManager tman;


    String[] ay_atlary;
    String[] gun_atlary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.hold, R.anim.abc_slide_in_bottom);
        final AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        TelephonyManager tman = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Time wagt = new Time(Time.getCurrentTimezone());
        wagt.setToNow();

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        view = (TextView) findViewById(R.id.textView);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        conman = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);




        a_switch = (ImageView) findViewById(R.id.aswitch);
        a_switch.setOnClickListener(this);

        lmain = (LinearLayout) findViewById(R.id.laymain);
        lmain.setOnClickListener(this);

        b_info = (ImageView) findViewById(R.id.about);
        b_info.setOnClickListener(this);


        d_exit = (ImageView) findViewById(R.id.down);
        d_exit.setOnClickListener(this);

        view.setOnClickListener(this);
        view.setText(tman.getNetworkOperatorName());

        //Aydym player
        bplay = (ImageView) findViewById(R.id.mplay);
        bprev = (ImageView) findViewById(R.id.mprev);
        bnext = (ImageView) findViewById(R.id.mnext);

        bplay.setOnClickListener(this);
        bprev.setOnClickListener(this);
        bnext.setOnClickListener(this);

        // Ashakdaky duwmeler
        btn_settings = (ImageView) findViewById(R.id.b_settings);
        btn_settings.setOnClickListener(this);
        btn_settings.setOnLongClickListener(this);

        btn_camera = (ImageView) findViewById(R.id.b_camera);
        btn_camera.setOnClickListener(this);

        btn_calc = (ImageView) findViewById(R.id.b_calc);
        btn_calc.setOnClickListener(this);

        btn_clock = (ImageView) findViewById(R.id.b_clock);
        btn_clock.setOnClickListener(this);
        btn_clock.setOnLongClickListener(this);

        btn_flash = (ImageView) findViewById(R.id.b_fonar);
        btn_flash.setOnClickListener(this);

        sBar = (SeekBar) findViewById(R.id.seekBar);
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue,
                                          boolean fromUser) {
                progress = progresValue;

                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        progress);
            }
        });

        if (isPlaying)
            bplay.setImageResource(R.drawable.m_pause);
        else
            bplay.setImageResource(R.drawable.m_play);

        soundbar = (SeekBar) findViewById(R.id.s_sound);
        soundbar.setMax(mAudioManager.getStreamMaxVolume(3));
        soundbar.setProgress(mAudioManager.getStreamVolume(3));

        soundbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue,
                                          boolean fromUser) {
                progress = progresValue;
                mAudioManager.setStreamVolume(3,progress,0);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setStreamVolume(3,progress,0);
            }
        });
        // Shu yerde ashakdaky duwmeler gutaryar

        gun = (TextView) findViewById(R.id.gun);
        String nj="-nji";
        ay_atlary = getResources().getStringArray(R.array.aylar);
        gun_atlary = getResources().getStringArray(R.array.gunler);
        if(wagt.monthDay==6||wagt.monthDay==9||wagt.monthDay==10||wagt.monthDay==16||wagt.monthDay==19||wagt.monthDay==26||wagt.monthDay==29)
            nj="-njy";
        gun.setText(wagt.monthDay+nj+" "+ay_atlary[wagt.month]+", "+gun_atlary[wagt.weekDay]);



        //ay.setText(ay_atlary[wagt.month]);
        //gun_ady.setText(gun_atlary[wagt.weekDay]);

        updateButtonStates();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return First.newInstance("0");
                case 1:
                    return Second.newInstance("1");
                default: return First.newInstance("0");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void updateButtonStates() {

        tman = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);


        //final String t = Settings.System.getString(this.getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT);
        //final String rs = Settings.System.getString(this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION);
        //rotate = (ImageView) findViewById(R.id.b_rotate);

        final int val = Settings.System.getInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        final String a = Settings.System.getString(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);


        btn_flash = (ImageView) findViewById(R.id.b_fonar);


        sBar = (SeekBar) findViewById(R.id.seekBar);
        a_switch = (ImageView) findViewById(R.id.aswitch);

        if (a.equals("1"))
            a_switch.setImageResource(R.drawable.s_1);


        if(Cyra.flashState())
            btn_flash.setImageResource(R.drawable.b_flash_p);

        sBar.setProgress(val);

    }

    @Override
    protected void onPause()
    {
        // TODO: Implement this method
        super.onPause();
        finish();
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.abc_slide_out_bottom);
    }


    @Override
    public void onClick(View v) {

        fonarBarmy = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        tman = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        final String a = Settings.System.getString(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE);

        //rotate = (ImageView) findViewById(R.id.b_rotate);


        if (v == a_switch)
        {
            ImageView b_a = (ImageView) findViewById(R.id.aswitch);
            if (a.equals("1"))
            {
                Settings.System.putString(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,"0");
                b_a.setImageResource(R.drawable.s_0);
            }else
            {
                Settings.System.putString(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,"1");
                b_a.setImageResource(R.drawable.s_1);
            }

        }

        if (v == b_info)
        {

            new maglumat().show(getFragmentManager().beginTransaction(), "about");
        }


        if (v == d_exit||v==lmain) {
            //Toast.makeText(this, R.string.s_habar, Toast.LENGTH_SHORT).show();

            finish();
            //onPause();
        }

        if (v == view) {
            String encodedHash = Uri.encode("#");
            Intent intt = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "*0800" + encodedHash));
            startActivity(intt);
        }

        //Player

        if (v == bplay)
        {
            AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            long eventtime = SystemClock.uptimeMillis();
            MediaPlayer mediaPlayer = new MediaPlayer();

            //mediaPlayer.

            //boolean isPlaying = mAudioManager.isMusicActive();
            //ComponentName localComponent = new ComponentName(Intent.CATEGORY_APP_MUSIC, Intent.CATEGORY_APP_MUSIC.getClass().getName());

            //remoteControl(CMDTOGGLEPAUSE);

            Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent upEvent = new KeyEvent(eventtime, eventtime, 0, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
            upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
            //upIntent.setComponent(localComponent);
            sendOrderedBroadcast(upIntent, null);
            //bplay.setImageResource(R.drawable.m_pause);
            upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            upEvent = new KeyEvent(eventtime, eventtime, 1, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
            upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
            //upIntent.setComponent(localComponent);
            sendOrderedBroadcast(upIntent, null);

            if (isPlaying)
                bplay.setImageResource(R.drawable.m_play);
            else
                bplay.setImageResource(R.drawable.m_pause);


         }

        if (v==bnext)
        {
            long eventtime = SystemClock.uptimeMillis();
            Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent downEvent = new KeyEvent(eventtime, eventtime, 0, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
            downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            sendOrderedBroadcast(downIntent, null);

            eventtime = SystemClock.uptimeMillis();
            downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            downEvent = new KeyEvent(eventtime, eventtime, 1, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
            downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            sendOrderedBroadcast(downIntent, null);
        }

        if (v==bprev)
        {
            long eventtime = SystemClock.uptimeMillis();
            Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent downEvent = new KeyEvent(eventtime, eventtime, 0,   KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
            downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            sendOrderedBroadcast(downIntent, null);

            eventtime = SystemClock.uptimeMillis();
            downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            downEvent = new KeyEvent(eventtime, eventtime, 1,   KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
            downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
            sendOrderedBroadcast(downIntent, null);
        }

        //Ashakdaky hatar ucin komandalar
        if(v == btn_settings)
        {
            Intent sIntent = new Intent("android.settings.SETTINGS");
            sIntent.setFlags(1);
            startActivity(sIntent);
            finish();
        }

        if(v == btn_camera)
        {
            Intent sIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            sIntent.setFlags(1);
            startActivity(sIntent);
            finish();
        }

        if (v == btn_flash) {
            ImageView bn_flash = (ImageView) findViewById(R.id.b_fonar);
            fonarBarmy = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

            if (fonarBarmy) {
                if(!(Cyra.flashState()))
                {
                    Cyra.kameranyTayyarla();
                    Cyra.KameranyAc();
                    bn_flash.setImageResource(R.drawable.b_flash_p);
                }else{
                    Cyra.KameranyYapmak();
                    bn_flash.setImageResource(R.drawable.b_flash_n);
                }
            }
            //finish();
        }

        if(v == btn_calc)
        {
            Intent sIntent = new Intent();
            sIntent.setFlags(1);
            sIntent.setAction(Intent.ACTION_MAIN);
            sIntent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
            try {
                startActivity(sIntent);
                finish();
            }catch (ActivityNotFoundException e)
            {
                e.printStackTrace();
            }
            finish();
        }

        if(v == btn_clock)
        {
            Intent sIntent = new Intent("android.intent.action.SHOW_ALARMS");
            sIntent.setFlags(1);
            try {
                startActivity(sIntent);
                finish();
            }catch (ActivityNotFoundException e)
            {
                e.printStackTrace();
            }
            finish();

        }
    }



   /* public void datastate(boolean statem)
    {
        try {
            tman = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            Method m = tman.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null !=m)
            {
                m.invoke(tman, statem);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/




    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                return;
            case NETIJE:
                //TextView view = (TextView) findViewById(R.id.textView);
                //view.setText("Sizin balansynyz: " + Integer.toString(requestCode) + " " + Integer.toString(resultCode) + " " + data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    public boolean onLongClick(View view) {

        if (view == btn_clock)
        {
            Intent intent = new Intent("android.settings.DATE_SETTINGS");
            intent.setFlags(1);
            startActivity(intent);
            finish();
        }

        if (view == btn_settings)
        {
            Intent intent = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
            intent.setFlags(1);
            startActivity(intent);
            finish();
        }

        return false;
    }


    boolean isPlaying()
    {
        MediaPlayer mediaPlayer = new MediaPlayer();
        //return ((AudioManager) getSystemService("audio")).isMusicActive();
        //mediaPlayer.

        //boolean isPlaying = mAudioManager.isMusicActive();

        return mediaPlayer.isPlaying();
    }

    private void remoteControl(String CMD) {
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", CMD);
        sendBroadcast(i);
    }


}
