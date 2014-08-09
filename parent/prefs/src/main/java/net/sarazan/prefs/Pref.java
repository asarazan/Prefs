package net.sarazan.prefs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
* Created by Aaron Sarazan on 6/22/14
* Copyright(c) 2014 Manotaur, LLC.
*/
public interface Pref<T> {

    @NotNull
    String getKey();

    @Nullable
    T get();

    @NotNull
    T get(@NotNull T defaultValue);

    void put(@Nullable T value, boolean commit);

    void commit();
}
