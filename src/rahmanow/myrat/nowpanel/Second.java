package rahmanow.myrat.nowpanel;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 14.01.2016.
 */
public class Second extends Fragment implements View.OnClickListener,View.OnLongClickListener {


    private ImageView stime;

    private ImageView btn_blue;



    private BluetoothAdapter btadapter;
    //private ImageView gps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second, container, false);


        btadapter = BluetoothAdapter.getDefaultAdapter();



        final String t = Settings.System.getString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT);
        stime = (ImageView) v.findViewById(R.id.b_stime);
        stime.setOnClickListener(this);
        stime.setOnLongClickListener(this);


        btn_blue = (ImageView) v.findViewById(R.id.b_bluetooth);
        btn_blue.setOnClickListener(this);
        btn_blue.setOnLongClickListener(this);

        if (btadapter.isEnabled())
            btn_blue.setImageResource(R.drawable.t_bluetooth_p);

        /*gps  = (ImageView) v.findViewById(R.id.b_gps);
        gps.setOnClickListener(this);
        gps.setOnLongClickListener(this);*/

        if (t.equals("15000"))
        {
            stime.setImageResource(R.drawable.s15_n);
        }else if (t.equals("30000"))
        {
            stime.setImageResource(R.drawable.s30_n);
        }else if (t.equals("60000"))
        {
            stime.setImageResource(R.drawable.s60_n);
        }else if (t.equals("120000"))
        {
            stime.setImageResource(R.drawable.s120_n);
        }else if (t.equals("300000"))
        {
            stime.setImageResource(R.drawable.s300_n);
        }else if (t.equals("600000"))
        {
            stime.setImageResource(R.drawable.s600_n);
        }



        return v;
    }

    public static Second newInstance(String text) {

        Second f = new Second();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {

        final String t = Settings.System.getString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT);
        LocationManager loc = (LocationManager) getActivity().getSystemService(MainActivity.LOCATION_SERVICE);





        if (v == stime)
        {
            if (t.equals("15000"))
            {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT,"30000");
                stime.setImageResource(R.drawable.s30_n);
            }else if (t.equals("30000"))
            {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT,"60000");
                stime.setImageResource(R.drawable.s60_n);
            }else if (t.equals("60000"))
            {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT,"120000");
                stime.setImageResource(R.drawable.s120_n);
            }else if (t.equals("120000"))
            {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT,"300000");
                stime.setImageResource(R.drawable.s300_n);
            }else if (t.equals("300000"))
            {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT,"600000");
                stime.setImageResource(R.drawable.s600_n);
            }else if (t.equals("600000"))
            {
                Settings.System.putString(getActivity().getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT,"15000");
                stime.setImageResource(R.drawable.s15_n);
            }
        }

        if (v == btn_blue) {
            btadapter = BluetoothAdapter.getDefaultAdapter();
            //ImageView bt_btn = (ImageView) view.findViewById(R.id.b_bluetooth);

            if (btadapter.isEnabled())
            {
                btadapter.disable();
                btn_blue.setImageResource(R.drawable.t_bluetooth_n);
            }else{
                btadapter.enable();
                btn_blue.setImageResource(R.drawable.t_bluetooth_p);
            }
        }

        /*if (v == gps)
        {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            sendBroadcast(intent);

        }*/
    }

    @Override
    public boolean onLongClick(View v) {

        if(v == btn_blue)
        {
            Intent intent = new Intent("android.settings.BLUETOOTH_SETTINGS");
            intent.setFlags(1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        if (v == stime)
        {
            Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
            intent.setFlags(1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
        return false;
    }
}