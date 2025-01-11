package franqulator.cuefinger;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;

import org.libsdl.app.SDLActivity;

/**
 * calls SDLActivity
 */

public class Cuefinger extends SDLActivity {

    static {
        System.loadLibrary("main");
    }
    private static String pref = "settings";
    private SharedPreferences sp;

    private AssetManager mgr;
    private native void load(AssetManager mgr);
    private native void loadSettings(String json);
    private native String getSettingsJSON();
    private native void loadServerSettings(String json);
    private native String getServerSettingsJSON();

    private native void exit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mgr = getResources().getAssets();
            load(mgr);
            sp = getSharedPreferences(pref,0);
            String s = sp.getString("settings","");
            loadSettings(s);
            s = sp.getString("serverSettings","");
            loadServerSettings(s);
        }
        catch(Exception e) {
            Log.w("franqulator.cuefinger","load settings failed");
        }
    }

    @Override
    protected void onPause() {

        try {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("settings", getSettingsJSON());
            editor.putString("serverSettings", getServerSettingsJSON());
            editor.apply();
        }
        catch(Exception e) {
            Log.w("franqulator.cuefinger","store settings failed");
        }
        super.onPause();
    }

    protected void onDestroy() {
        exit();
        super.onDestroy();
    }
}