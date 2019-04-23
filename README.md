# GameOfLife


This is a project which simulates the Game of Life will rules by Conway

each simulation runs via it's own timer, and each timer being it's own thread, each simulation does not interact with any other

you have the option to start and stop a simulation, set every cell to dead, and by clicking on a cell change it from dead to alive or alive to dead. you can also create new simulation which is placed beside any other, and you can scroll through the simulation if you create more than your screen/window width


The algorithm un the update method has an O-notaion of O(n^2) since the grid of cells is iterated through 2 times
