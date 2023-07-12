# **Parking-Jam** #

This repository contains a Java project to play the game called **parking-jam**. Parking-jam is a game were the goal is to make the red car get out of the parking lot. To do so, you have to move the rest of the cars horizontally or vertically as long as there is nothing with which it can collide (cars or walls). Play and try to pass the different levels.

## Table of Contents ##

* [Introduction](#introduction)
  * [Download the source code](#download-the-source-code)
  * [Run tests](#run-tests)
  * [Directory Layout](#directory-layout)
  * [Implementation](#implementation)
* [Run the game](#run-the-game)
  * [How to play](#how-to-play)
  * [Load and save](#load-and-save)
* [Authors](#authors)

## Introduction ##

### Download the source code ###

1. [Install Java](<http://www.oracle.com/java/technologies/downloads/>)
2. [Install Maven](<https://maven.apache.org/download.cgi>)
3. Clone the git repository `https://costa.ls.fi.upm.es/gitlab/200320/parking-jam.git`

### Run tests ###

1. Change to the root directory of the repository
2. Create the project build `mvn clean install`
3. Run the tests with `mvn test`

### Directory Layout ###
``` terminal
    parking-jam
    ├── README.md
    ├── deliverables
    │   ├── backlog1.csv
    │   ├── backlog2.csv
    │   ├── sprint1.csv
    │   └── sprint2.csv
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── es
        │   │       └── upm
        │   │           └── pproject
        │   │               └── parkingjam
        │   │                   ├── App.java
        │   │                   ├── common
        │   │                   │   ├── Coordinates.java
        │   │                   │   └── Pair.java
        │   │                   ├── controller
        │   │                   │   └── Controller.java
        │   │                   ├── exceptions
        │   │                   │   ├── LevelNotFoundException.java
        │   │                   │   └── WrongLevelFormatException.java
        │   │                   ├── interfaces
        │   │                   │   ├── IController.java
        │   │                   │   └── Resetable.java
        │   │                   ├── models
        │   │                   │   ├── Car.java
        │   │                   │   ├── Game.java
        │   │                   │   ├── Level.java
        │   │                   │   └── Parking.java
        │   │                   └── view
        │   │                       ├── MainFrame.java
        │   │                       ├── panels
        │   │                       │   ├── CarPanel.java
        │   │                       │   ├── ImagePanel.java
        │   │                       │   └── ParkingPanel.java
        │   │                       └── utils
        │   │                           ├── Constants.java
        │   │                           └── MusicPlayer.java
        │   └── resources
        │       ├── data
        │       │   ├── board.txt
        │       │   └── score.txt
        │       ├── img
        │       │   ├── backgrounds
        │       │   │   └── background.png
        │       │   ├── carIcon.png
        │       │   ├── cars
        │       │   │   ├── car1.png
        │       │   │   ├── car10.png
        │       │   │   ├── car11.png
        │       │   │   ├── car2.png
        │       │   │   ├── car3.png
        │       │   │   ├── car4.png
        │       │   │   ├── car5.png
        │       │   │   ├── car6.png
        │       │   │   ├── car7.png
        │       │   │   ├── car8.png
        │       │   │   ├── car9.png
        │       │   │   └── redcar.png
        │       │   ├── congrats.png
        │       │   └── wall
        │       │       └── wall.png
        │       ├── levels
        │       │   ├── level_1.txt
        │       │   ├── level_2.txt
        │       │   └── level_3.txt
        │       ├── levels4GameTest
        │       │   ├── level_1.txt
        │       │   └── level_2.txt
        │       ├── levels4LevelTest
        │       │   ├── levelTest1.txt
        │       │   ├── levelTest2.txt
        │       │   ├── levelTest3.txt
        │       │   ├── levelTest4.txt
        │       │   ├── levelTest5.txt
        │       │   ├── levelTest6.txt
        │       │   ├── levelTest7.txt
        │       │   └── levelTest8.txt
        │       ├── log4j.properties
        │       ├── saveloadLevelTest
        │       │   └── test3.txt
        │       └── sounds
        │           ├── background.wav
        │           ├── default.wav
        │           ├── game_completed.wav
        │           ├── level_completed.wav
        │           ├── move_car.wav
        │           ├── new_game.wav
        │           ├── reset.wav
        │           └── undo.wav
        └── test
            └── java
                └── es
                    └── upm
                        └── pproject
                            └── parkingjam
                                ├── common
                                │   ├── CoordinatesTest.java
                                │   └── PairTest.java
                                └── models
                                    ├── CarTest.java
                                    ├── GameTest.java
                                    └── LevelTest.java
```

## Implementation ##

This project is oriented to emulate the game **parking-jam** using the **model-view-controller** arquitecture pattern.

**Controller** is the class that handless the communication between the model and the view. Is in charge of starting the game, handling the user input and updating the model.

**model** is formed by four classes. The first one is *car.java* that represents a car with all the characteristics. For example length, id, orientation... Another one is *level.java* is the class that represents a level in which we have the board, create the cars, verify if a movement is valid, reset the level and level up. Furthermore, *game.java* represents the game and is in charge of managing the different levels, create a new game, manage the score, and more. And last but not least, *parking.java* is the class that is representing the board.

**mainframe** is the class in charge of controlling the gui.

### Car Class

The `Car` class represents a car in the game. It implements the `Resetable` interface and provides methods for moving the car, checking if it is on the goal, and managing its state.

#### Features

- Stores the car's ID, length, orientation, initial position, and current position.
- Tracks whether the car is the red car (target car) or not.
- Moves the car based on a specified direction and distance.
- Checks if the car is on the goal (reached the exit).
- Provides methods for resetting the car's position.
- Implements the `Resetable` interface to support resetting the car's state.

### Parking Class

The `Parking` class represents the game board or parking lot in the Parking Jam application. It manages the layout of the parking lot, the position of vehicles, and provides methods for updating the parking state.

#### Features

- Stores the tiles representing the parking lot and vehicles on the board.
- Tracks the number of rows and columns in the parking lot.
- Updates the parking state when a vehicle is moved.
- Duplicates the parking to create a copy for analysis or simulation.
- Inserts a car into the parking lot.
- Deletes a car from the parking lot.
- Provides access to the tiles, number of rows, and number of columns.

### Level Class

The `Level` class represents a level in the game. It provides methods for loading a level from a file, managing cars and their movements, checking the status of the level, and resetting the level.

#### Features

- Stores the red car, the game board, the initial board state, and the list of vehicles.
- Manages the score, undo and redo movements, and the name of the level.
- Loads a level from a file, filling the board and creating cars.
- Validates the format and dimensions of the level file.
- Moves a car on the board and verifies the validity of the movement.
- Checks if the red car has reached the goal (exit).
- Resets the level to its initial state.
- Supports undo and redo movements.

### Game Class

The `Game` class represents a game in the Parking Jam application. It manages the game's levels and provides methods for moving cars, starting a new game, loading and saving game state, undoing and redoing movements, resetting the level, and retrieving game information.

#### Features

- Manages the game's levels and provides methods for interacting with the game.
- Moves cars within each level of the game based on user input.
- Starts a new game from the initial level.
- Loads a saved game state from a file.
- Saves the game state, including the board, scores, and movements, to a file.
- Supports undo and redo functionality for movements.
- Checks if the game has finished.
- Resets the level to its initial state.
- Prints the current level.
- Retrieves game information such as total score, level score, level name, and level dimensions.
- Sets the score and undo movements when loading a game state.
- Retrieves the ID of the car that has been moved in the last undo or redo movement.

### MainFrame Class

The `MainFrame` class represents the graphical user interface (GUI) of the game. It extends the `JFrame` class and provides methods for initializing the GUI, displaying the game level, handling user input, and managing the game menu and sound options.

#### Features

- Initializes the GUI components, including the game grid, stats panel, and background sprite.
- Displays the level and game scores in the stats panel.
- Paints the game parking area, cars, and walls on the grid.
- Manages the game menu options, including new game, reset level, load game, save game, and exit.
- Handles the mute sound option and controls the background music.
- Provides functionality for undoing and redoing car movements.
- Shows the help menu with instructions on how to play the game.
- Provides file chooser dialogs for loading and saving game files.

### ImagePanel

The `ImagePanel` class represents a tile on the game board. It extends the `JPanel` class and is responsible for displaying an image as a tile.

#### Features

- Displays an image as a tile on the game board.
- Supports custom dimensions for the tile.
- Provides access to the image used for the tile.

### ParkingPanel Class

The `ParkingPanel` class represents the parking area on the game board. It extends the `JPanel` class and is responsible for displaying the parking area where cars are placed.

#### Features

- Displays the parking area on the game board.
- Supports custom dimensions for the parking area.
- Sets the background color of the parking area.

### CarPanel Class

The `CarPanel` class represents a draggable car on the game board. It extends the `ImagePanel` class and is responsible for displaying a car image and handling mouse events for dragging and releasing the car. It interacts with the game controller to handle car movements.

#### Features

- Displays a car image on the game board.
- Handles mouse events for dragging and releasing the car.
- Calculates the movement of the car based on the mouse drag distance.
- Rotates the car image if the car is vertical.
- Interacts with the game controller to check the validity of car movements.
- Plays a sound effect when the car is successfully moved.
- Provides access to the ID and initial position of the car.

### Controller Class

The `Controller` class is responsible for handling the application's logic and communication between the model and the view. It manages user input, updates the model, and interacts with the GUI.

#### Features

- Initializes the game, GUI, and music player.
- Retrieves level dimensions and board tiles from the game model.
- Validates and performs car movements based on user input.
- Retrieves information about car positions, orientations, and lengths.
- Manages game score, level progress, and undo/redo functionality.
- Handles new game creation, game loading, level reset, and game saving.
- Controls the background music and plays sound effects.
- Manages game muting functionality.

### MusicPlayer Class ###

The `MusicPlayer` class is responsible for managing and playing different music tracks and sound effects in the application. It uses the Java Sound API to load and play audio files.

#### Features

- Utilizes the singleton design pattern to ensure only one instance of the `MusicPlayer` class is created.
- Loads music tracks from specified file paths, supporting audio formats such as WAV, AIFF, and AU.
- Plays background music in a loop.
- Plays various sound effects, including move car sound, new game sound, reset sound, undo sound, default sound, level sound, and game sound.
- Provides functionality to pause, resume, and restart the background music.

## Run the game ##

1. Change to the root directory of the repository
2. Compile the project with: `mvn clean compile`
3. Run the game with: `mvn exec:java`

<div style="text-align: center;">

  ![Parking-Jam](src/main/resources/img/parking-jam1.png)

</div>

### How to play ###

The objective of the game is to move the cars in order the red car can scape the parking lot. The cars can only be moved horizontally or vertically without colliding with other cars or walls. In order to move the cars you have to click the car you want to move and drag it.

### Load and save ###

The game can be saved and loaded from a file. The file is a txt file that contains the state of the game when it was saved. In this file you will find the positions of the cars, the level score, the global score and the stack of the moves

## Authors ##

200315 - Nihel Kella Bouziane

200320 - Julio Manso Sánchez-Tornero

200034 - Lucía Sánchez Navidad

200358 - Álvaro Dominguez Martín