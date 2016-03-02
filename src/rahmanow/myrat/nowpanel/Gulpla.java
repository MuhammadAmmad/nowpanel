package rahmanow.myrat.nowpanel;

/**
 * Created by Administrator on 11.11.2015.
 */


import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class Gulpla extends Activity {
    DevicePolicyManager devman;
    ComponentName coname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devman = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        coname = new ComponentName(this, MenAdmin.class);
        boolean active = devman.isAdminActive(coname);

        if (active) {
            devman.lockNow();
            finish();
        }
        if (!active) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, coname);
            //intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, R.string.d);
            startActivity(intent);
            finish();
            if (active) {
                devman.lockNow();
                finish();
            }
        }
    }


}



