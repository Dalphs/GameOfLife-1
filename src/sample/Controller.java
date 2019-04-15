package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Controller {


    private int size = 5;
    private int gridSize = 100;

    private Game game;

    @FXML
    private AnchorPane cellPane;

    public Controller() {



        game = new Game();

    }

    public void startSim() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                Rectangle rect = new Rectangle(size*i,size*j,size,size);



                if (game.getBoard()[i][j].isAlive()) {
                    rect.fillProperty().setValue(Color.RED);
                } else {
                    rect.fillProperty().setValue(Color.GRAY);
                }

                cellPane.getChildren().add(rect);
            }

        }


    }

    public void update(){

    }


}
