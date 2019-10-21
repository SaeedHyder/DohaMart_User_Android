package com.ingic.ezhalbatek.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ingic.ezhalbatek.activities.MainActivity;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.retrofit.GsonFactory;

import java.util.Locale;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_GUEST_STATUS = "isGuest";

    private static final String FILENAME = "preferences";
    private static final String KeyIsVisit = "KeyIsVisit";
    private static final String KeyIsInProgress = "KeyIsInProgress";
    private static final String KeyIsCompleted = "KeyIsCompleted";
    private static final String KeyIsPending = "KeyIsPending";
    private static final String KeyIsServiceCompleted = "KeyIsServiceCompleted";

    protected static final String Firebase_TOKEN = "Firebasetoken";

    protected static final String NotificationCount = "NotificationCount";
    protected static final String KEY_USER = "KEY_USER";
    protected static final String KEY_DEFAULT_LANG = "keyLanguage";
    protected static final String KEY_LANGUAGE_STATUS = "Islanguage";


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isGuest() {
        return getBooleanPreference(context, FILENAME, KEY_GUEST_STATUS);
    }

    public void setGuestStatus( boolean isGuest ) {
        putBooleanPreference( context, FILENAME, KEY_GUEST_STATUS, isGuest );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }


    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }
    public int getNotificationCount() {
        return getIntegerPreference(context, FILENAME, NotificationCount);
    }

    public void setNotificationCount(int count) {
        putIntegerPreference(context, FILENAME, NotificationCount, count);
    }


    public boolean isLanguageArabic() {
        return false;
    }

    public UserEnt getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), UserEnt.class);
    }

    public void putUser(UserEnt user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public void setIsFromVisitSubscriber(boolean iFrom ) {
        putBooleanPreference( context, FILENAME, KeyIsVisit, iFrom );
    }

    public boolean isFromVisitSub() {
        return getBooleanPreference(context, FILENAME, KeyIsVisit);
    }

    public void setIsFromInProgressSubscriber(boolean iFrom ) {
        putBooleanPreference( context, FILENAME, KeyIsInProgress, iFrom );
    }

    public boolean isFromInProgressSub() {
        return getBooleanPreference(context, FILENAME, KeyIsInProgress);
    }
    public void setIsFromCompletedSubscriber(boolean iFrom ) {
        putBooleanPreference( context, FILENAME, KeyIsCompleted, iFrom );
    }

    public boolean isFromCompletedSub() {
        return getBooleanPreference(context, FILENAME, KeyIsCompleted);
    }

    public void setIsFromPending(boolean iFrom ) {
        putBooleanPreference( context, FILENAME, KeyIsPending, iFrom );
    }

    public boolean isFromPending() {
        return getBooleanPreference(context, FILENAME, KeyIsPending);
    }

    public void putLang(Activity activity, String lang) {
        Log.v("lang", "|" + lang);
        Resources resources = context.getResources();

        if (lang.equals("ar")){
            lang = "ar";}
        else{
            lang = "en";}

        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        Locale locale=new Locale(lang);
        // conf.setLayoutDirection(locale);
        conf.locale = locale;
        conf.setLayoutDirection(Locale.ENGLISH);
        resources.updateConfiguration(conf, dm);

        ((MainActivity) activity).restartActivity();

    }



    public String getLang() {
        return getStringPreference(context, FILENAME, KEY_DEFAULT_LANG);
    }

    public boolean isLanguageArabian() {
        return getLang().equalsIgnoreCase("ar");
    }

    public boolean isLanguageSelected() {
        return getBooleanPreference(context, FILENAME, KEY_LANGUAGE_STATUS);
    }


    public void setLanguageSelected( boolean isSocial ) {
        putBooleanPreference( context, FILENAME, KEY_LANGUAGE_STATUS, isSocial );
    }

}
