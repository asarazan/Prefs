package net.sarazan.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Aaron Sarazan on 6/22/14
 * Copyright(c) 2014 Manotaur, LLC.
 */
public final class Prefs {

    private static final String TAG = "Prefs";

    private Prefs() {}

    public static <T> Pref<T> sharedPreference(Context c, String key, Class<T> clazz) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        return new SharedPref<>(key, prefs, clazz);
    }

    public static <T> void putGeneric(Context c, String key, T value, boolean commit) {
        if (c == null) return;
        putGeneric(PreferenceManager.getDefaultSharedPreferences(c).edit(), key, value, commit);
    }

    // Stores integers as longs, fyi.
    public static <T> void putGeneric(SharedPreferences.Editor editor, String key, T value, boolean commit) {
        if (editor == null) return;
        if (value == null) {
            editor.remove(key);
        } else {
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Integer) {
                editor.putLong(key, ((Integer)value).longValue());
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else {
                throw new IllegalArgumentException("Cannot handle class: " + value.getClass());
            }
        }
        if (commit) {
            editor.commit();
        }
    }

    public static <T> T getGeneric(Context c, String key, Class<T> clazz) {
        if (c == null) return null;
        return getGeneric(PreferenceManager.getDefaultSharedPreferences(c), key, clazz);
    }

    public static <T> T getGeneric(SharedPreferences prefs, String key, Class<T> clazz) {
        if (prefs == null) return null;
        if (!prefs.contains(key)) return null;
        if (clazz == String.class) {
            return (T) prefs.getString(key, null);
        } else if (clazz == Long.class) {
            return (T) (Long)prefs.getLong(key, 0L);
        } else if (clazz == Integer.class) {
            return (T) (Integer)(int)prefs.getLong(key, 0L);
        } else if (clazz == Float.class) {
            return (T) (Float)prefs.getFloat(key, 0f);
        } else if (clazz == Boolean.class) {
            return (T) (Boolean)prefs.getBoolean(key, false);
        } else {
            throw new IllegalArgumentException("Cannot handle class: " + clazz);
        }
    }

    public static void remove(Context c, String key, boolean commit) {
        if (c == null) return;
        remove(PreferenceManager.getDefaultSharedPreferences(c), key, commit);
    }

    public static void remove(SharedPreferences prefs, String key, boolean commit) {
        if (prefs == null) return;
        SharedPreferences.Editor e = prefs.edit();
        e.remove(key);
        if (commit) {
            e.commit();
        }
    }

}
