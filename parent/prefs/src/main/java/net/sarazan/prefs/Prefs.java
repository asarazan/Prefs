package net.sarazan.prefs;

import android.content.SharedPreferences;

/**
 * Created by Aaron Sarazan on 6/22/14
 * Copyright(c) 2014 Manotaur, LLC.
 */
public final class Prefs {

    private static final String TAG = "Prefs";

    private Prefs() {}

    // Stores integers as longs, fyi.
    public static <T> void putGeneric(SharedPreferences.Editor editor, String key, T value) {
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
    }

    @SuppressWarnings("unchecked")
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

}
