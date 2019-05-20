package atmaauto.atmaauto.com.atmaauto.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import atmaauto.atmaauto.com.atmaauto.MainActivity;

public class SessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AtmaAutoSession";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ROLE = "role";
    public static final String KEY_Cabang = "cabang";

    public SessionManager(Context context)
    {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSessions(String role, String username, String id,String idcabang)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_Cabang, idcabang);
        editor.commit();
    }

    public HashMap<String, String> getUserSessionsDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_Cabang, pref.getString(KEY_Cabang, null));

        return user;
    }

    public void checkLogin()
    {
        if (!this.isLoggedIn())
        {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getKeyId()
    {
        return pref.getString(KEY_ID,"id");
    }
    public String getIdCabang()
    {
        return pref.getString(KEY_Cabang,"cabang");
    }
    public String getIdRole(){
        return pref.getString(KEY_ROLE,"role");
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
