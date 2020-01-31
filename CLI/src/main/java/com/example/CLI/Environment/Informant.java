package com.example.CLI.Environment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public interface Informant {

    @NotNull
    String createConnection();

    void send(@NotNull String name, byte[] data) throws IOException;

    byte[] getAndClose(@NotNull String name) throws IOException;
}
