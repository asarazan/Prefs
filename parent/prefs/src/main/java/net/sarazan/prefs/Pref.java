package net.sarazan.prefs;

import android.content.Context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
* Created by Aaron Sarazan on 6/22/14
* Copyright(c) 2014 Manotaur, LLC.
*/
public interface Pref<T> {

    @NotNull
    String getKey(@NotNull Context c);

    @Nullable
    T get(@NotNull Context c);

    @NotNull
    T get(@NotNull Context c, @NotNull T defaultValue);

    void put(@NotNull Context c, @Nullable T value, boolean commit);

    void remove(@NotNull Context c, boolean commit);

    void commit(@NotNull Context c);

    void revert(@NotNull Context c);
}
