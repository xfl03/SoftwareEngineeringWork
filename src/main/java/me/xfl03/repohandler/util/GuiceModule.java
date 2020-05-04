package me.xfl03.repohandler.util;

import com.google.inject.AbstractModule;

import java.util.HashMap;
import java.util.Map;

public class GuiceModule extends AbstractModule {
    HashMap<Class<?>, Object> map = new HashMap<>();

    public void add(Class<?> clazz, Object obj) {
        map.put(clazz, obj);
    }

    @Override
    protected void configure() {
        for (Map.Entry<Class<?>, Object> e : map.entrySet()) {
            bind((Class) e.getKey()).toInstance(e.getKey().cast(e.getValue()));
        }
    }
}
