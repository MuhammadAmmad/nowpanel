package rahmanow.myrat.nowpanel;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 11.02.2016.
 */
public class Cyra {
    private static Camera mCamera;
    private static String TAG;
    private static int fps;
    private static int mHeight;
    private static int mWidth;
    private static SurfaceTexture surfaceTexture;
    static {
        mCamera = null;
        TAG = Cyra.class.getSimpleName();
        surfaceTexture = new SurfaceTexture(0);
    }


    public static void kameranyTayyarla(){
        if(mCamera == null){
            try{
                mCamera = Camera.open();
                Log.d(TAG, "Kamera acyldy");

            }catch (Exception e){
                Log.d(TAG, "kamerany tayyarlamak basarmady"+e.getMessage());
            }
        }
    }

    public static boolean flashState(){
        if (mCamera == null)
            return false;
        if ("torch".equals(mCamera.getParameters().getFlashMode()))
            return true;
        return false;
    }
    public static boolean fokusBarmy(){
        if (mCamera==null)
            return false;
        List<String> sizeList = mCamera.getParameters().getSupportedFocusModes();
        if (sizeList==null)
            return false;

        for(String var : sizeList)
        {
            if ("infinity".equals(var)){
                Log.i(TAG, "set mFocusMode: "+var);
                return true;
            }
        }
        return false;
    }

    public static boolean mframebolyarmy(){
        if (mCamera==null)
            return false;
        List<int[]> sizeList = (List<int[]>) mCamera.getParameters().getSupportedPreviewFpsRange();
        if (sizeList == null)
            return false;
        fps = 30;
        int i = sizeList.size();
        for(int[] var:sizeList){

            if (var[i]<fps)
                fps=var[i];
            --i;
        }
        Log.i(TAG, "set FrameRates: fps: "+fps);
        return true;
    }

    public static boolean inKiciPrOlceg()
    {
        if (mCamera==null)
            return false;

        List<Camera.Size> sizeList = mCamera.getParameters().getSupportedPreviewSizes();
        if (sizeList==null)
            return false;
        mHeight = 720;
        mWidth = 1280;

        for (Camera.Size var:sizeList){
            if(mWidth>var.width){
                mWidth = var.width;
                mHeight  = var.height;
                Log.i(TAG, "Width: "+mWidth+" height: " +mHeight);
            }
        }
        Log.i(TAG, "Width: "+mWidth+" height: " +mHeight);
        return true;
    }

    public static void KameranyAc(){
        if (mCamera!=null)
        {
            try{
                Camera.Parameters lParam = mCamera.getParameters();
                if(!(lParam == null||lParam.getFlashMode()==null)){
                    lParam.setFlashMode("torch");
                    if (fokusBarmy())
                        lParam.setFocusMode("infinity");
                    if(mframebolyarmy())
                        lParam.setPreviewFrameRate(fps);
                    if (inKiciPrOlceg())
                        lParam.setPreviewSize(mWidth,mHeight);
                    mCamera.setParameters(lParam);
                    surfaceTexture.setDefaultBufferSize(0, 0);
                    try {
                        mCamera.setPreviewTexture(surfaceTexture);
                    } catch (IOException e) {
                        Log.i(TAG, "Yalnyslyk" + e.getMessage());
                    }
                    mCamera.startPreview();
                }
            }catch (Exception e)
            {
                Log.e(TAG, "Kamerany acmak basartmady"+e.getMessage());
            }
            Log.i(TAG,"Kamera ulanylyp baslandy");
        }
    }

    public static void KameranyYapmak()
    {
        if (mCamera!=null)
        {
            try {
                Camera.Parameters lparam = mCamera.getParameters();
                if (!(lparam == null||lparam.getFlashMode() == null))
                {
                    lparam.setFlashMode("off");
                    mCamera.setParameters(lparam);
                }
                mCamera.stopPreview();
            }catch (Exception e){
                Log.e(TAG, "Kamerany yapmakda yalnyslyk bar");
            }
            Log.i(TAG, "Kamera yapyldy");
        }
    }

    public static void KameranLEDniUlan()
    {
        if (mCamera!=null)
        {
            try {
                mCamera.release();
            }catch (Exception e)
            {
                Log.e(TAG, "Kameran LED-ni ulanmak basartmady");
            }
        }
        mCamera = null;
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fonarBarmy = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


        Camera kamera = null;
        Camera.Parameters kparam;
        if (fonarBarmy) {
            kamera = Camera.open();
            if (!fonarAcyk) {
                kparam = kamera.getParameters();
                kparam.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                kamera.setParameters(kparam);
                kamera.startPreview();
                fonarAcyk = true;
            } else {
                kparam = kamera.getParameters();
                kparam.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                kamera.setParameters(kparam);
                kamera.stopPreview();
                fonarAcyk = false;
            }
        }

    }*/
}
