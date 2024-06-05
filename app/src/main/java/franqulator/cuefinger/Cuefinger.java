package franqulator.cuefinger;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.SharedPreferences;

import org.libsdl.app.SDLActivity;

/**
 * calls SDLActivity
 */

public class Cuefinger extends SDLActivity {
    private static String pref = "settings";
    private SharedPreferences sp;

    private AssetManager mgr;
    private native void load(AssetManager mgr);
    private native void loadSettings(String json);
    private native String getSettingsJSON();
    private native void loadServerSettings(String json);
    private native String getServerSettingsJSON();

    private native String cleanUp();
    private native String terminateAllPingThreads();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgr = getResources().getAssets();
        load(mgr);
        sp = getSharedPreferences(pref,0);
        String s = sp.getString("settings","");
        loadSettings(s);
        s = sp.getString("serverSettings","");
        loadServerSettings(s);
    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("settings", getSettingsJSON());
        editor.putString("serverSettings", getServerSettingsJSON());
        editor.commit();
        terminateAllPingThreads();
        super.onPause();
    }

    protected void onDestroy() {
        cleanUp();
    }
}