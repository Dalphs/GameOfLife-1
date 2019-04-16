package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public class Controller {


    private int size = 15;
    private int gridSize = 50;

    private int numOfSims = 1;


    @FXML
    private GridPane gridPane;


    @FXML
    public void initialize() {

        Game game = new Game();
        Rectangle[][] grid = new Rectangle[gridSize][gridSize];



        AnchorPane anchor = new AnchorPane();
        GridPane.setRowIndex(anchor, 1);
        GridPane.setColumnIndex(anchor, numOfSims);
        gridPane.getChildren().add(anchor);

        setupCells(game, anchor, grid);


        setupButtons( gridPane, game, grid, anchor);


        numOfSims++;


    }

    private void setupButtons( GridPane gridPane, Game game, Rectangle[][] grid, AnchorPane anchor) {


        Button stopSimBtn = new Button();
        stopSimBtn.setText("Stop Sim");
        GridPane.setRowIndex(stopSimBtn, 2);
        GridPane.setColumnIndex(stopSimBtn, numOfSims);
        GridPane.setHalignment(stopSimBtn, HPos.RIGHT);
        gridPane.getChildren().add(stopSimBtn);

        Button startSimBTN = new Button();
        startSimBTN.setText("Start Sim");
        startSimBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startSim(stopSimBtn,game,grid);
            }
        });
        GridPane.setRowIndex(startSimBTN, 2);
        GridPane.setColumnIndex(startSimBTN, numOfSims);
        GridPane.setHalignment(startSimBTN, HPos.LEFT);
        gridPane.getChildren().add(startSimBTN);

        Button resetSimBTN = new Button();
        resetSimBTN.setText("Reset Sim");
        resetSimBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetSim(game, grid, anchor);
            }
        });
        GridPane.setRowIndex(resetSimBTN, 2);
        GridPane.setColumnIndex(resetSimBTN, numOfSims);
        GridPane.setHalignment(resetSimBTN, HPos.CENTER);
        gridPane.getChildren().add(resetSimBTN);

        Button clearSimBTN = new Button();
        clearSimBTN.setText("Clear Sim");
        clearSimBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearSim(game, grid);
            }
        });
        GridPane.setRowIndex(clearSimBTN, 3);
        GridPane.setColumnIndex(clearSimBTN, numOfSims);
        gridPane.getChildren().add(clearSimBTN);
    }

    private void setupCells(Game game, AnchorPane anchor, Rectangle[][] grid) {

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                Rectangle rect = new Rectangle(size * i, size * j, size, size);
                rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        onCellClicked(event.getX(), event.getY(), game, grid);
                    }
                });

                rect.fillProperty().setValue(fill(i, j, game));

                grid[i][j] = rect;
                anchor.getChildren().add(rect);
            }

        }
    }

    private void onCellClicked(double x, double y, Game game, Rectangle[][] grid) {

        int gridX = (int) (x / size);
        int gridY = (int) (y / size);

        game.getBoard()[gridX][gridY].setAlive(!game.getBoard()[gridX][gridY].isAlive());
        grid[gridX][gridY].fillProperty().setValue(fill(gridX, gridY, game));
    }


    private Color fill(int i, int j, Game game) {

        if (game.getBoard()[i][j].isAlive()) {
            return Color.RED;
        } else {
            return Color.GRAY;
        }
    }

    public void update(Game game, Rectangle[][] grid) {

        updateNeighbors(game);
        updateAlive(game, grid);


    }

    private void updateAlive(Game game, Rectangle[][] grid) {
        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                game.getBoard()[i][j].update();

                grid[i][j].fillProperty().setValue(fill(i, j, game));

            }
        }
    }

    private void updateNeighbors(Game game) {
        for (int i = 0; i < game.getBoard().length; i++) {

            for (int j = 0; j < game.getBoard()[0].length; j++) {

                int neighbors = 0;
                if (i != 0) {
                    if (j != 0) {
                        if (game.getBoard()[i - 1][j - 1].isAlive()) {
                            neighbors++;
                        }
                    }
                    if (game.getBoard()[i - 1][j].isAlive()) {
                        neighbors++;
                    }
                    if (j < gridSize - 1) {
                        if (game.getBoard()[i - 1][j + 1].isAlive()) {
                            neighbors++;
                        }
                    }
                }
                if (j != 0) {
                    if (game.getBoard()[i][j - 1].isAlive()) {
                        neighbors++;
                    }
                }
                if (j < gridSize - 1) {
                    if (game.getBoard()[i][j + 1].isAlive()) {
                        neighbors++;
                    }
                }
                if (i < gridSize - 1) {
                    if (j != 0) {
                        if (game.getBoard()[i + 1][j - 1].isAlive()) {
                            neighbors++;
                        }
                    }
                    if (game.getBoard()[i + 1][j].isAlive()) {
                        neighbors++;
                    }
                    if (j < gridSize - 1) {
                        if (game.getBoard()[i + 1][j + 1].isAlive()) {
                            neighbors++;
                        }
                    }
                }
                game.getBoard()[i][j].setNeighbors(neighbors);

            }

        }
    }

    public void startSim(Button stopSimBtn, Game game, Rectangle[][] grid) {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update(game, grid);
            }
        };


        stopSimBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopSim(timer);
            }
        });

        timer.scheduleAtFixedRate(task, 1000, 1000);

    }


    public void stopSim(Timer timer) {

        timer.cancel();

    }

    public void newSim(AnchorPane cellPane) {

        cellPane.getChildren().removeAll();

        initialize();
    }

    public void clearSim(Game game, Rectangle[][] grid) {

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                game.getBoard()[i][j].setAlive(false);
                grid[i][j].fillProperty().setValue(fill(i, j, game));

            }
        }
    }

    public void resetSim(Game game, Rectangle[][] grid, AnchorPane anchor) {

        anchor.getChildren().removeAll();
        setupCells(game, anchor, grid); //TODO this appears to do nothing
    }


    public void newSim(ActionEvent actionEvent) {
        initialize();
    }
}
