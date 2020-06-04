package com.example.CLI;

import com.example.CLI.Commands.Pwd;

import java.io.File;
import java.nio.file.Path;

public class PathUtils {
    public static String pathToAbsolute(String pathString) {
        var path = Path.of(pathString);
        if (path.isAbsolute()) {
            return pathString;
        } else {
            return new File(Pwd.currentDirectory(), pathString).getAbsolutePath();
        }
    }
}
