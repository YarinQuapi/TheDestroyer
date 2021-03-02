package xyz.yarinlevi.thedestroyer.utils;

import lombok.Getter;

import java.util.HashMap;

public abstract class Command {
    @Getter private final String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void run(HashMap<String, Object> data, String[] args);
}
