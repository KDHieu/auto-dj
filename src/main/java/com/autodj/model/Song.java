package com.autodj.model;

import java.nio.file.Path;

public class Song {

    private final String title;
    private final String extension;
    private final Path path;

    public Song(String title, String extension, Path path) {
        this.title = title;
        this.extension = extension;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String getExtension() {
        return extension;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return title;
    }
}