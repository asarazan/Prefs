package net.sarazan.prefs;

import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
* Created by Aaron Sarazan on 6/22/14
* Copyright(c) 2014 Manotaur, LLC.
*/
public class SharedPref<T> implements Pref<T> {

    @NotNull
    private final String mKey;

    @Nullable
    private final SharedPreferences mSharedPreferences;

    @NotNull
    private final Class<T> mClazz;

    SharedPref(@NotNull String key, @Nullable SharedPreferences sharedPreferences, @NotNull Class<T> clazz) {
        mKey = key;
        mSharedPreferences = sharedPreferences;
        mClazz = clazz;
    }

    @Nullable
    private SharedPreferences.Editor mEditor;

    @NotNull
    public String getKey() {
        return mKey;
    }

    @Nullable
    @Override
    public T get() {
        if (mSharedPreferences == null) return null;
        return Prefs.getGeneric(mSharedPreferences, mKey, mClazz);
    }

    @NotNull
    @Override
    public T get(@NotNull T defaultValue) {
        T retval = get();
        return retval == null ? defaultValue : retval;
    }

    @Override
    public void put(@Nullable T value, boolean commit) {
        if (mSharedPreferences == null) return;
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        Prefs.putGeneric(mEditor, mKey, value, commit);
    }

    @Override
    public void commit() {
        if (mEditor != null) mEditor.commit();
    }
}
