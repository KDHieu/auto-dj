package com.autodj.service;

import com.autodj.model.Song;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MusicLibraryService {

    public List<Song> scanDirectory(Path directory) throws IOException {

        try (var paths = Files.walk(directory)) {

            return paths
                    .filter(Files::isRegularFile)
                    .filter(this::isSupportedAudioFile)
                    .map(this::createSong)
                    .sorted(Comparator.comparing(
                            Song::getTitle,
                            String.CASE_INSENSITIVE_ORDER
                    ))
                    .toList();
        }
    }

    private boolean isSupportedAudioFile(Path path) {

        String fileName = path
                .getFileName()
                .toString()
                .toLowerCase(Locale.ROOT);

        return fileName.endsWith(".mp3")
                || fileName.endsWith(".wav")
                || fileName.endsWith(".m4a");
    }

    private Song createSong(Path path) {

        String fileName = path.getFileName().toString();

        int lastDot = fileName.lastIndexOf('.');

        String title;
        String extension;

        if (lastDot > 0) {
            title = fileName.substring(0, lastDot);
            extension = fileName.substring(lastDot + 1);
        } else {
            title = fileName;
            extension = "";
        }

        return new Song(
                title,
                extension,
                path
        );
    }
}