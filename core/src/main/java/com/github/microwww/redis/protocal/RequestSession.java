package com.github.microwww.redis.protocal;

import com.github.microwww.redis.util.Assert;
import com.github.microwww.redis.util.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestSession extends ConcurrentHashMap<String, Object> implements Closeable {
    public static final String ADDRESS = RequestSession.class.getName() + ".ADDRESS";
    public static final String NAME = RequestSession.class.getName() + ".NAME";
    public static final String INFO = RequestSession.class.getName() + ".INFO";
    private static final AtomicInteger inc = new AtomicInteger(1);
    private final SocketChannel channel;
    private int database = 0;
    public final int ID;

    public RequestSession(SocketChannel channel) {
        this.channel = channel;
        ID = inc.getAndIncrement();
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        Assert.isTrue(database >= 0, "database >= 0");
        this.database = database;
    }

    @NotNull
    public Optional<String> getName() {
        return Optional.ofNullable((String) this.get(NAME));
    }

    public void setName(String name) {
        this.put(NAME, name);
    }

    @NotNull
    public Map<String, String> getInfo() {
        Map<String, String> info = (Map<String, String>) this.get(INFO);
        if (info == null) {
            return Collections.EMPTY_MAP;
        }
        return Collections.unmodifiableMap(info);
    }

    public void setInfo(String key, String value) {
        Map<String, String> info = (Map<String, String>) this.get(INFO);
        if(Objects.isNull(info)){
            info = new HashMap<>();
            this.put(INFO, info);
        }
        info.put(key, value);
    }

    @NotNull
    public String getAddress() {
        return withDefault(ADDRESS, "127.0.0.1:6379");
    }

    public <T> T withDefault(String key, T def) {
        Assert.isNotNull(def, "Not null");
        return (T) this.getOrDefault(key, def);
    }

    @Override
    public void close() {
        this.clear();
    }
}
