package net.sarazan.prefs;

/**
* Created by Aaron Sarazan on 6/22/14
* Copyright(c) 2014 Manotaur, LLC.
*/
public interface Pref<T> {

    String getKey();

    T get();

    T get(T defaultValue);

    void put(T value, boolean commit);

    void remove(boolean commit);

    void commit();

    void revert();
}
