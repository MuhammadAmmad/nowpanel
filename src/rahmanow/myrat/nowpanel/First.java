package rahmanow.myrat.nowpanel;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 14.01.2016.
 */
public class First extends Fragment implements View.OnClickListener,View.OnLongClickListener {

    private WifiManager wman;
    private ImageView lock;

    private ImageView wifi;
    private ImageView btn_profile;
    private ImageView rotate;
    private ImageView btn_data;
    private ImageView bt_btn;


    private AudioManager manager_a;
    private ConnectivityManager conman;
    private TelephonyManager tman;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first, container, false);
        wman = (WifiManager) getActivity().getSystemService(MainActivity.WIFI_SERVICE);
        final String rs = Settings.System.getString(getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);

        manager_a = (AudioManager) getActivity().getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        conman = (ConnectivityManager) getActivity().getBaseContext().getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = conman.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        tman = (TelephonyManager) getActivity().getBaseContext().getSystemService(MainActivity.TELEPHONY_SERVICE);

        boolean isavaliable;
        int r_mode = manager_a.getRingerMode();
        isavaliable = ninfo.isAvailable();


        bt_btn = (ImageView) v.findViewById(R.id.bs_mode);

        btn_profile = (ImageView) v.findViewById(R.id.bs_mode);
        btn_profile.setOnClickListener(this);
        btn_profile.setOnLongClickListener(this);

        wifi = (ImageView) v.findViewById(R.id.wifiswitch);
        wifi.setOnClickListener(this);
        wifi.setOnLongClickListener(this);

        btn_data = (ImageView) v.findViewById(R.id.b_data);
        btn_data.setOnClickListener(this);
        btn_data.setOnLongClickListener(this);

        lock = (ImageView) v.findViewById(R.id.b_lock);
        lock.setOnClickListener(this);
        lock.setOnLongClickListener(this);




        rotate = (ImageView) v.findViewById(R.id.b_rotate);
        rotate.setOnClickListener(this);
        rotate.setOnLongClickListener(this);
        if (rs.equals("1"))
            rotate.setImageResource(R.drawable.t_rotate_p);



        if(wman.isWifiEnabled())
            wifi.setImageResource(R.drawable.t_wifi_p);


        if (r_mode==2)
        {
            bt_btn.setImageResource(R.drawable.t_sound_n);
        }else if (r_mode==1){

            bt_btn.setImageResource(R.drawable.t_sound_vibrate_p);
        }else if (r_mode==0)
        {
            bt_btn.setImageResource(R.drawable.t_sound_p);
        }

        if (tman.getDataState()==2)
        {
            btn_data.setImageResource(R.drawable.t_data_p);
        }else if (!isavaliable) {
            btn_data.setEnabled(false);
        }

        return v;
    }

    public static First newInstance(String text) {

        First f = new First();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {
        //View view = View.inflate(getActivity().getApplicationContext(), R.layout.first, null);
        final String rs = Settings.System.getString(getActivity().getContentResolver(),Settings.System.ACCELEROMETER_ROTATION);

        if (v == btn_profile) {
            //bt_btn = (ImageView) view.findViewById(R.id.bs_mode);
            manager_a = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

            int r_mode = manager_a.getRingerMode();

            if (r_mode==2)
            {
                manager_a.setRingerMode(1);
                bt_btn.setImageResource(R.drawable.t_sound_vibrate_p);
            }else if (r_mode==1){
                manager_a.setRingerMode(0);
                bt_btn.setImageResource(R.drawable.t_sound_p);
            }else if (r_mode==0)
            {
                manager_a.setRingerMode(2);
                bt_btn.setImageResource(R.drawable.t_sound_n);
            }
        }

        if (v == btn_data) {
            //btn_data = (ImageView) view.findViewById(R.id.b_data);
            if (tman.getDataState() == 0) {
                setdata(true);
                btn_data.setImageResource(R.drawable.t_data_p);
            } else if (tman.getDataState() == 2) {
                setdata(false);
                btn_data.setImageResource(R.drawable.t_data_n);

            }
        }

        if (v == rotate)
        {
            if (rs.equals("0"))
            {
                Settings.System.putString(getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, "1");
                rotate.setImageResource(R.drawable.t_rotate_p);
            }else {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,"0");
                rotate.setImageResource(R.drawable.t_rotate_n);
            }
        }

        if (v == lock) {
            hazirGulpla();
        }

        if (v == wifi) {
            //wifi = (ImageView) view.findViewById(R.id.wifiswitch);
            if (wman.isWifiEnabled()) {
                Toast.makeText(getActivity().getBaseContext(), R.string.w_yapyldy, Toast.LENGTH_LONG).show();
                wman.setWifiEnabled(false);
                wifi.setImageResource(R.drawable.t_wifi_n);
            } else {
                Toast.makeText(getActivity().getBaseContext(), R.string.w_acyldy, Toast.LENGTH_LONG).show();
                wman.setWifiEnabled(true);
                wifi.setImageResource(R.drawable.t_wifi_p);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {

        if (v == wifi)
        {
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            intent.setFlags(1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        if (v == btn_profile)
        {
            Intent intent = new Intent("android.settings.SOUND_SETTINGS");
            intent.setFlags(1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        if (v == btn_data)
        {
            Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            intent.setFlags(1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        if (v == rotate)
        {
            Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
            intent.setFlags(1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        if(v==lock)
        {
            DevicePolicyManager devman;
            ComponentName coname;

            devman = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
            coname = new ComponentName(getActivity(), MenAdmin.class);
            boolean active = devman.isAdminActive(coname);

            if(active)
                devman.removeActiveAdmin(coname);
        }

        return false;
    }

    public void hazirGulpla()
    {
        DevicePolicyManager devman;
        ComponentName coname;

        devman = (DevicePolicyManager) getActivity().getSystemService(MainActivity.DEVICE_POLICY_SERVICE);
        coname = new ComponentName(getActivity().getApplicationContext(), MenAdmin.class);
        boolean active = devman.isAdminActive(coname);

        if (active) {
            devman.lockNow();
            getActivity().finish();
        }
        if (!active) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, coname);
            //intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, R.string.d);
            startActivity(intent);

            if (active) {
                devman.lockNow();
                getActivity().finish();
            }
            getActivity().finish();
            
        }
    }

    public void setdata(boolean statedata)
    {
        conman = (ConnectivityManager) getActivity().getBaseContext().getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        Method m = null;
        try {
            m = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        }catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        try {
            m.invoke(conman, statedata);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}