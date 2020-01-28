package com.example.CLI.Environment;

import com.example.CLI.Commands.Result;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public interface Informant {

    @NotNull
    Result createConnection();

    void send(@NotNull String name, byte[] data) throws IOException;

    byte[] getAndClose(@NotNull String name) throws IOException;
}
