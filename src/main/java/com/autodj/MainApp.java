package com.autodj;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Application.setUserAgentStylesheet(
                new PrimerDark().getUserAgentStylesheet()
        );

        BorderPane root = new BorderPane();

        root.setLeft(createSidebar());
        root.setCenter(createMainContent());
        root.setBottom(createNextTrackBar());

        Scene scene = new Scene(root, 1100, 700);

        stage.setTitle("Auto DJ");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createSidebar() {

        Label logo = new Label("AUTO DJ");
        logo.setStyle("""
                -fx-font-size: 26px;
                -fx-font-weight: bold;
                """);

        Button libraryButton = new Button("Library");
        Button songsButton = new Button("Songs");
        Button playlistsButton = new Button("Playlists");

        libraryButton.setMaxWidth(Double.MAX_VALUE);
        songsButton.setMaxWidth(Double.MAX_VALUE);
        playlistsButton.setMaxWidth(Double.MAX_VALUE);

        VBox sidebar = new VBox(
                15,
                logo,
                new Separator(),
                libraryButton,
                songsButton,
                playlistsButton
        );

        sidebar.setPadding(new Insets(25));
        sidebar.setPrefWidth(220);

        sidebar.setStyle("""
                -fx-background-color: rgba(0, 0, 0, 0.18);
                """);

        return sidebar;
    }

    private VBox createMainContent() {

        Label nowPlayingLabel = new Label("NOW PLAYING");

        nowPlayingLabel.setStyle("""
                -fx-font-size: 13px;
                -fx-font-weight: bold;
                -fx-opacity: 0.65;
                """);

        Label songTitle = new Label("No song selected");

        songTitle.setStyle("""
                -fx-font-size: 30px;
                -fx-font-weight: bold;
                """);

        Label artistLabel =
                new Label("Choose a song from your library");

        Slider progressSlider = new Slider();
        progressSlider.setPrefWidth(500);

        Button playButton = new Button("▶  Play");

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

        Label nextTrackLabel = new Label("NEXT TRACK");

        nextTrackLabel.setStyle("""
                -fx-font-size: 13px;
                -fx-font-weight: bold;
                -fx-opacity: 0.65;
                """);

        Label nextSong =
                new Label("Auto recommendation will appear here");

        VBox bottom = new VBox(
                8,
                nextTrackLabel,
                nextSong
        );

        bottom.setPadding(new Insets(20, 30, 20, 30));

        bottom.setStyle("""
                -fx-background-color: rgba(0, 0, 0, 0.15);
                """);

        return bottom;
    }

    public static void main(String[] args) {
        launch(args);
    }
}