package com.example.CLI.Environment;

import com.example.CLI.Commands.Result;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/**
 * Класс "информатор", описывающий обмен данными.
 */
public class SimpleInformant implements Informant {

    @NotNull private HashMap<String, byte[]> dataSource;

    public SimpleInformant() {
        dataSource = new HashMap<>();
    }

    @Override @NotNull
    public String createConnection() {
        var name = UUID.randomUUID().toString();
        dataSource.put(name, new byte[0]);
        return name;
    }

    @Override
    public void send(@NotNull String name, byte[] data) throws IOException {
        if (dataSource.containsKey(name)) {
            dataSource.put(name, data);
        } else {
            try (var writer = new FileOutputStream(new File(name))) {
                writer.write(data);
            }
        }
    }

    @Override
    public byte[] getAndClose(@NotNull String name) throws IOException {
        if (dataSource.containsKey(name)) {
            var data = dataSource.get(name);
            dataSource.remove(name);
            return data;
        } else {
            try (var reader = new FileInputStream(new File(name))) {
                return reader.readAllBytes();
            }
        }
    }
}
