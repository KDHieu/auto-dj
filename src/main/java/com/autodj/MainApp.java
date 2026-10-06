package com.autodj;

import atlantafx.base.theme.PrimerDark;
import com.autodj.model.Song;
import com.autodj.service.MusicLibraryService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {

    private final MusicLibraryService musicLibraryService =
            new MusicLibraryService();

    private ListView<Song> songListView;

    private Label libraryStatusLabel;

    @Override
    public void start(Stage stage) {

        Application.setUserAgentStylesheet(
                new PrimerDark().getUserAgentStylesheet()
        );

        BorderPane root = new BorderPane();

        root.setLeft(createSidebar(stage));
        root.setCenter(createMainContent());
        root.setBottom(createNextTrackBar());

        Scene scene = new Scene(root, 1100, 700);

        stage.setTitle("Auto DJ");
        stage.setMinWidth(900);
        stage.setMinHeight(600);

        stage.setScene(scene);
        stage.show();
    }

    private VBox createSidebar(Stage stage) {

        Label logo = new Label("AUTO DJ");

        logo.setStyle("""
                -fx-font-size: 26px;
                -fx-font-weight: bold;
                """);

        Button importButton =
                new Button("+ Import Music");

        importButton.setMaxWidth(Double.MAX_VALUE);

        importButton.setOnAction(
                event -> importMusic(stage)
        );

        Label libraryLabel = new Label("LIBRARY");

        libraryLabel.setStyle("""
                -fx-font-size: 12px;
                -fx-font-weight: bold;
                -fx-opacity: 0.65;
                """);

        songListView = new ListView<>();

        songListView.setPlaceholder(
                new Label("No songs imported")
        );

        VBox.setVgrow(
                songListView,
                javafx.scene.layout.Priority.ALWAYS
        );

        libraryStatusLabel =
                new Label("0 songs");

        VBox sidebar = new VBox(
                15,
                logo,
                new Separator(),
                importButton,
                libraryLabel,
                songListView,
                libraryStatusLabel
        );

        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(280);

        sidebar.setStyle("""
                -fx-background-color: rgba(0, 0, 0, 0.18);
                """);

        return sidebar;
    }

    private void importMusic(Stage stage) {

        DirectoryChooser directoryChooser =
                new DirectoryChooser();

        directoryChooser.setTitle(
                "Choose Music Folder"
        );

        File selectedDirectory =
                directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            return;
        }

        try {

            List<Song> songs =
                    musicLibraryService.scanDirectory(
                            selectedDirectory.toPath()
                    );

            songListView.getItems().setAll(songs);

            libraryStatusLabel.setText(
                    songs.size() + " songs"
            );

        } catch (IOException exception) {

            showError(
                    "Unable to import music",
                    exception.getMessage()
            );
        }
    }

    private VBox createMainContent() {

        Label nowPlayingLabel =
                new Label("NOW PLAYING");

        nowPlayingLabel.setStyle("""
                -fx-font-size: 13px;
                -fx-font-weight: bold;
                -fx-opacity: 0.65;
                """);

        Label songTitle =
                new Label("No song selected");

        songTitle.setStyle("""
                -fx-font-size: 30px;
                -fx-font-weight: bold;
                """);

        Label artistLabel =
                new Label(
                        "Choose a song from your library"
                );

        Slider progressSlider =
                new Slider();

        progressSlider.setPrefWidth(500);

        Button playButton =
                new Button("▶  Play");

        VBox content = new VBox(
                20,
                nowPlayingLabel,
                songTitle,
                artistLabel,
                progressSlider,
                playButton
        );

        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        return content;
    }

    private VBox createNextTrackBar() {

        Label nextTrackLabel =
                new Label("NEXT TRACK");

        nextTrackLabel.setStyle("""
                -fx-font-size: 13px;
                -fx-font-weight: bold;
                -fx-opacity: 0.65;
                """);

        Label nextSong =
                new Label(
                        "Auto recommendation will appear here"
                );

        VBox bottom = new VBox(
                8,
                nextTrackLabel,
                nextSong
        );

        bottom.setPadding(
                new Insets(20, 30, 20, 30)
        );

        bottom.setStyle("""
                -fx-background-color: rgba(0, 0, 0, 0.15);
                """);

        return bottom;
    }

    private void showError(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Auto DJ");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}