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
    private native void setLockSettings(boolean lockSettings);
    private native boolean getLockSettings();
    private native void setLockToMix(String lockToMix);
    private native String getLockToMix();
    private native void setReconnect(int lockSettings);
    private native int getReconnect();

    private native void setChannelWidth(int channelWidth);
    private native int getChannelWidth();

    private native void setServer1(String server);
    private native String getServer1();

    private native void setServer2(String server);
    private native String getServer2();

    private native void setServer3(String server);
    private native String getServer3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgr = getResources().getAssets();
        load(mgr);
        sp = getSharedPreferences(pref,0);
        setLockSettings(sp.getBoolean("lockSettings",false));
        setLockToMix(sp.getString("lockToMix",""));
        setReconnect(sp.getInt("reconnect",10000));
        setChannelWidth(sp.getInt("channelWidth",0));
        setServer1(sp.getString("server1",""));
        setServer2(sp.getString("server2",""));
        setServer3(sp.getString("server3",""));
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("lockSettings", getLockSettings());
        editor.putString("lockToMix", getLockToMix());
        editor.putInt("reconnect", getReconnect());
        editor.putInt("channelWidth", getChannelWidth());
        editor.putString("server1", getServer1());
        editor.putString("server2", getServer2());
        editor.putString("server3", getServer3());
        editor.commit();
        super.onPause();
    }
}