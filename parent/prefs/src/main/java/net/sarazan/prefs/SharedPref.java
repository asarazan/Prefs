package net.sarazan.prefs;

import android.content.SharedPreferences;

/**
* Created by Aaron Sarazan on 6/22/14
* Copyright(c) 2014 Manotaur, LLC.
*/
public class SharedPref<T> implements Pref<T> {

    private final String mKey;

    private final SharedPreferences mSharedPreferences;

    private final Class<T> mClazz;

    private T mValue;

    private boolean mHasChanged = false;

    public SharedPref( String key,  SharedPreferences sharedPreferences,  Class<T> clazz) {
        mKey = key;
        mSharedPreferences = sharedPreferences;
        mClazz = clazz;
    }

    private SharedPreferences.Editor mEditor;

    public String getKey() {
        return mKey;
    }

    @Override
    public T get() {
        if (mSharedPreferences == null) return null;
        if (mHasChanged) return mValue;
        return Prefs.getGeneric(mSharedPreferences, mKey, mClazz);
    }

    @Override
    public T get( T defaultValue) {
        T retval = get();
        return retval == null ? defaultValue : retval;
    }

    @Override
    public void put( T value, boolean commit) {
        if (mSharedPreferences == null) return;
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        mHasChanged = !commit;
        mValue = mHasChanged ? value : null;
        Prefs.putGeneric(mEditor, mKey, value, commit);
    }

    @Override
    public void commit() {
        if (mEditor != null) mEditor.commit();
        mHasChanged = false;
        mValue = null;
    }

    @Override
    public void remove(boolean commit) {
        put(null, commit);
    }

    @Override
    public void revert() {
        mEditor = null;
        mHasChanged = false;
        mValue = null;
    }
}
