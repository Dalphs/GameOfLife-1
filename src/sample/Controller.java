package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public class Controller {


    private int size = 15;
    private int gridSize = 50;

    private Rectangle[][] grid;


    private Game game;

    private Timer timer;
    private TimerTask task;

    @FXML
    private AnchorPane cellPane;


    @FXML
    public void initialize() {

        game = new Game();
        grid = new Rectangle[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                Rectangle rect = new Rectangle(size * i, size * j, size, size);
                rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        onCellClicked(event.getX(), event.getY());
                    }
                });

                rect.fillProperty().setValue(fill(i, j));

                grid[i][j] = rect;
                cellPane.getChildren().add(rect);
            }

        }
    }

    private void onCellClicked(double x, double y) {

        int gridX = (int) (x / size);
        int gridY = (int) (y / size);

        game.getBoard()[gridX][gridY].setAlive(!game.getBoard()[gridX][gridY].isAlive());
        grid[gridX][gridY].fillProperty().setValue(fill(gridX, gridY));
    }


    public void startSim() {

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

    }

    private Color fill(int i, int j) {

        if (game.getBoard()[i][j].isAlive()) {
            return Color.RED;
        } else {
            return Color.GRAY;
        }
    }

    public void update() {

        for (int i = 0; i < game.getBoard().length; i++) {

            for (int j = 0; j < game.getBoard()[0].length; j++) {

                int neighbors = 0;

                for (int m = -1; m < 2; m++) {
                    for (int n = -1; n < 2; n++) {

                        if ((m + i >= 0 && n + j >= 0) && (m+i<gridSize && n+j <gridSize)) {

                            if (m != 0 && n != 0) {
                                if (game.getBoard()[i + m][j + n].isAlive()) {
                                    neighbors++;    //TODO update of neighbors doesn't reflect the real number of neighbors
                                }
                            }

                        }
                    }
                }

                game.getBoard()[i][j].setNeighbors(neighbors);

            }

        }


        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                game.getBoard()[i][j].update();

                grid[i][j].fillProperty().setValue(fill(i, j));

            }
        }

    }


    public void stopSim() {

        timer.cancel();
        task.cancel();
    }

    public void newSim() {

        cellPane.getChildren().removeAll();

        initialize();
    }

    public void clearSim(ActionEvent actionEvent) {

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                game.getBoard()[i][j].setAlive(false);
                grid[i][j].fillProperty().setValue(fill(i, j));

            }
        }
    }
}
