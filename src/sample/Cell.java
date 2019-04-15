package sample;

public class Cell {

    private int neighbors;
    private boolean alive;

    public Cell(double random) {

        if (random >= 0.51) {
            alive = true;
        } else {
            alive = false;
        }
    }


    public void update() {

        if (neighbors < 2 && alive) {
            alive = false;
        } else if(neighbors == 3 && !alive){
            alive = true;
        } else if (neighbors > 3 && alive) {
            alive = false;
        }





    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
