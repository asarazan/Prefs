package net.sarazan.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
* Created by Aaron Sarazan on 6/22/14
* Copyright(c) 2014 Manotaur, LLC.
*/
public class SharedPref<T> implements Pref<T> {

    private final String mKey;

    private final Class<T> mClazz;

    private T mValue;

    private boolean mHasChanged = false;

    private SharedPreferences.Editor mEditor;

    public SharedPref(String key, Class<T> clazz) {
        mKey = key;
        mClazz = clazz;
    }

    @NotNull
    public String getKey(@NotNull Context c) {
        return mKey;
    }

    @Override
    @Nullable
    public T get(@NotNull Context c) {
        if (mHasChanged) return mValue;
        return Prefs.getGeneric(getSharedPreferences(c), mKey, mClazz);
    }

    @Override
    @NotNull
    public T get(@NotNull Context c, @NotNull T defaultValue) {
        T retval = get(c);
        return retval == null ? defaultValue : retval;
    }

    @Override
    @SuppressLint("CommitPrefEdits")
    public void put(@NotNull Context c, @Nullable T value, boolean commit) {
        SharedPreferences prefs = getSharedPreferences(c);
        if (prefs == null) return;
        if (mEditor == null) {
            mEditor = prefs.edit();
        }
        mHasChanged = !commit;
        mValue = mHasChanged ? value : null;
        Prefs.putGeneric(mEditor, mKey, value, commit);
    }

    @Override
    public void commit(@NotNull Context c) {
        if (mEditor != null) mEditor.commit();
        mHasChanged = false;
        mValue = null;
        mEditor = null;
    }

    @Override
    public void remove(@NotNull Context c, boolean commit) {
        put(c, null, commit);
    }

    @Override
    public void revert(@NotNull Context c) {
        mHasChanged = false;
        mValue = null;
        mEditor = null;
    }

    @Nullable
    protected SharedPreferences getSharedPreferences(@NotNull Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c);
    }
}
