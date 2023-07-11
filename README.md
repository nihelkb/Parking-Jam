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
    │   └── sprint1.csv
    ├── dependency-reduced-pom.xml
    ├── log
    │   └── output.log
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── es
    │   │   │       └── upm
    │   │   │           └── pproject
    │   │   │               └── parkingjam
    │   │   │                   ├── App.java
    │   │   │                   ├── common
    │   │   │                   │   ├── Coordinates.java
    │   │   │                   │   └── Pair.java
    │   │   │                   ├── controller
    │   │   │                   │   └── Controller.java
    │   │   │                   ├── exceptions
    │   │   │                   │   ├── LevelNotFoundException.java
    │   │   │                   │   └── WrongLevelFormatException.java
    │   │   │                   ├── interfaces
    │   │   │                   │   ├── IController.java
    │   │   │                   │   └── Resetable.java
    │   │   │                   ├── models
    │   │   │                   │   ├── Car.java
    │   │   │                   │   ├── Game.java
    │   │   │                   │   ├── Level.java
    │   │   │                   │   └── Parking.java
    │   │   │                   └── view
    │   │   │                       ├── MainFrame.java
    │   │   │                       ├── panels
    │   │   │                       │   ├── CarPanel.java
    │   │   │                       │   ├── ImagePanel.java
    │   │   │                       │   └── ParkingPanel.java
    │   │   │                       └── utils
    │   │   │                           ├── Constants.java
    │   │   │                           └── MusicPlayer.java
    │   │   └── resources
    │   │       ├── data
    │   │       │   ├── board.txt
    │   │       │   └── score.txt
    │   │       ├── img
    │   │       │   ├── backgrounds
    │   │       │   │   └── background.png
    │   │       │   ├── carIcon.png
    │   │       │   ├── cars
    │   │       │   │   ├── car1.png
    │   │       │   │   ├── car10.png
    │   │       │   │   ├── car11.png
    │   │       │   │   ├── car2.png
    │   │       │   │   ├── car3.png
    │   │       │   │   ├── car4.png
    │   │       │   │   ├── car5.png
    │   │       │   │   ├── car6.png
    │   │       │   │   ├── car7.png
    │   │       │   │   ├── car8.png
    │   │       │   │   ├── car9.png
    │   │       │   │   └── redcar.png
    │   │       │   ├── congrats.png
    │   │       │   └── wall
    │   │       │       └── wall.png
    │   │       ├── levels
    │   │       │   ├── level_1.txt
    │   │       │   ├── level_2.txt
    │   │       │   └── level_3.txt
    │   │       ├── levels4GameTest
    │   │       │   ├── level_1.txt
    │   │       │   └── level_2.txt
    │   │       ├── levels4LevelTest
    │   │       │   ├── levelTest1.txt
    │   │       │   ├── levelTest2.txt
    │   │       │   ├── levelTest3.txt
    │   │       │   ├── levelTest4.txt
    │   │       │   ├── levelTest5.txt
    │   │       │   ├── levelTest6.txt
    │   │       │   ├── levelTest7.txt
    │   │       │   └── levelTest8.txt
    │   │       ├── log4j.properties
    │   │       ├── saveloadLevelTest
    │   │       │   └── test3.txt
    │   │       └── sounds
    │   │           ├── background.wav
    │   │           ├── default.wav
    │   │           ├── game_completed.wav
    │   │           ├── level_completed.wav
    │   │           ├── move_car.wav
    │   │           ├── new_game.wav
    │   │           ├── reset.wav
    │   │           └── undo.wav
    │   └── test
    │       └── java
    │           └── es
    │               └── upm
    │                   └── pproject
    │                       └── parkingjam
    │                           ├── common
    │                           │   ├── CoordinatesTest.java
    │                           │   └── PairTest.java
    │                           └── models
    │                               ├── GameTest.java
    │                               └── LevelTest.java
    └── target
        ├── classes
        │   ├── data
        │   │   ├── board.txt
        │   │   └── score.txt
        │   ├── es
        │   │   └── upm
        │   │       └── pproject
        │   │           └── parkingjam
        │   │               ├── App.class
        │   │               ├── common
        │   │               │   ├── Coordinates.class
        │   │               │   └── Pair.class
        │   │               ├── controller
        │   │               │   └── Controller.class
        │   │               ├── exceptions
        │   │               │   ├── LevelNotFoundException.class
        │   │               │   └── WrongLevelFormatException.class
        │   │               ├── interfaces
        │   │               │   ├── IController.class
        │   │               │   └── Resetable.class
        │   │               ├── models
        │   │               │   ├── Car.class
        │   │               │   ├── Game.class
        │   │               │   ├── Level.class
        │   │               │   └── Parking.class
        │   │               └── view
        │   │                   ├── MainFrame$1.class
        │   │                   ├── MainFrame$2.class
        │   │                   ├── MainFrame$UndoMenuOptions.class
        │   │                   ├── MainFrame.class
        │   │                   ├── panels
        │   │                   │   ├── CarPanel.class
        │   │                   │   ├── ImagePanel.class
        │   │                   │   └── ParkingPanel.class
        │   │                   └── utils
        │   │                       ├── Constants.class
        │   │                       └── MusicPlayer.class
        │   ├── img
        │   │   ├── backgrounds
        │   │   │   └── background.png
        │   │   ├── carIcon.png
        │   │   ├── cars
        │   │   │   ├── car1.png
        │   │   │   ├── car10.png
        │   │   │   ├── car11.png
        │   │   │   ├── car2.png
        │   │   │   ├── car3.png
        │   │   │   ├── car4.png
        │   │   │   ├── car5.png
        │   │   │   ├── car6.png
        │   │   │   ├── car7.png
        │   │   │   ├── car8.png
        │   │   │   ├── car9.png
        │   │   │   └── redcar.png
        │   │   ├── congrats.png
        │   │   ├── intro.jpg
        │   │   ├── start.png
        │   │   └── wall
        │   │       └── wall.png
        │   ├── levels
        │   │   ├── level_1.txt
        │   │   ├── level_2.txt
        │   │   └── level_3.txt
        │   ├── levels4GameTest
        │   │   ├── level_1.txt
        │   │   └── level_2.txt
        │   ├── levels4LevelTest
        │   │   ├── levelTest1.txt
        │   │   ├── levelTest2.txt
        │   │   ├── levelTest3.txt
        │   │   ├── levelTest4.txt
        │   │   ├── levelTest5.txt
        │   │   ├── levelTest6.txt
        │   │   ├── levelTest7.txt
        │   │   └── levelTest8.txt
        │   ├── log4j.properties
        │   ├── saveloadLevelTest
        │   │   └── test3.txt
        │   └── sounds
        │       ├── background.wav
        │       ├── default.wav
        │       ├── game_completed.wav
        │       ├── level_completed.wav
        │       ├── move_car.wav
        │       ├── new_game.wav
        │       ├── reset.wav
        │       └── undo.wav
        ├── generated-sources
        │   └── annotations
        ├── generated-test-sources
        │   └── test-annotations
        ├── jacoco.exec
        ├── maven-archiver
        │   └── pom.properties
        ├── maven-status
        │   └── maven-compiler-plugin
        │       ├── compile
        │       │   └── default-compile
        │       │       ├── createdFiles.lst
        │       │       └── inputFiles.lst
        │       └── testCompile
        │           └── default-testCompile
        │               ├── createdFiles.lst
        │               └── inputFiles.lst
        ├── original-parking-jam-0.0.1-SNAPSHOT.jar
        ├── parking-jam-0.0.1-SNAPSHOT.jar
        ├── site
        │   └── jacoco
        │       ├── es.upm.pproject.parkingjam
        │       │   ├── App.html
        │       │   ├── App.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.common
        │       │   ├── Coordinates.html
        │       │   ├── Coordinates.java.html
        │       │   ├── Pair.html
        │       │   ├── Pair.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.controller
        │       │   ├── Controller.html
        │       │   ├── Controller.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.exceptions
        │       │   ├── LevelNotFoundException.html
        │       │   ├── LevelNotFoundException.java.html
        │       │   ├── WrongLevelFormatException.html
        │       │   ├── WrongLevelFormatException.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.models
        │       │   ├── Car.html
        │       │   ├── Car.java.html
        │       │   ├── Game.html
        │       │   ├── Game.java.html
        │       │   ├── Level.html
        │       │   ├── Level.java.html
        │       │   ├── Parking.html
        │       │   ├── Parking.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.view
        │       │   ├── MainFrame$1.html
        │       │   ├── MainFrame$2.html
        │       │   ├── MainFrame.html
        │       │   ├── MainFrame.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.view.panels
        │       │   ├── CarPanel.html
        │       │   ├── CarPanel.java.html
        │       │   ├── ImagePanel.html
        │       │   ├── ImagePanel.java.html
        │       │   ├── ParkingPanel.html
        │       │   ├── ParkingPanel.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── es.upm.pproject.parkingjam.view.utils
        │       │   ├── Constants.html
        │       │   ├── Constants.java.html
        │       │   ├── index.html
        │       │   └── index.source.html
        │       ├── index.html
        │       ├── jacoco-resources
        │       │   ├── branchfc.gif
        │       │   ├── branchnc.gif
        │       │   ├── branchpc.gif
        │       │   ├── bundle.gif
        │       │   ├── class.gif
        │       │   ├── down.gif
        │       │   ├── greenbar.gif
        │       │   ├── group.gif
        │       │   ├── method.gif
        │       │   ├── package.gif
        │       │   ├── prettify.css
        │       │   ├── prettify.js
        │       │   ├── redbar.gif
        │       │   ├── report.css
        │       │   ├── report.gif
        │       │   ├── session.gif
        │       │   ├── sort.gif
        │       │   ├── sort.js
        │       │   ├── source.gif
        │       │   └── up.gif
        │       ├── jacoco-sessions.html
        │       ├── jacoco.csv
        │       └── jacoco.xml
        ├── surefire-reports
        │   ├── TEST-es.upm.pproject.parkingjam.AppTest.xml
        │   ├── TEST-es.upm.pproject.parkingjam.level.LevelTest.xml
        │   ├── es.upm.pproject.parkingjam.AppTest.txt
        │   └── es.upm.pproject.parkingjam.level.LevelTest.txt
        └── test-classes
            └── es
                └── upm
                    └── pproject
                        └── parkingjam
                            ├── common
                            │   ├── CoordinatesTest.class
                            │   └── PairTest.class
                            └── models
                                ├── GameTest.class
                                └── LevelTest.class
```

### Implementation ###

This project is oriented to emulate the game **parking-jam** using the **model-view-controller** arquitecture pattern

**Controller** is the class that handless the communication between the model and the view. Is in charge of starting the game, handling the user input and updating the model.

**model** is formed by four classes. The first one is *car.java* that represents a car with all the characteristics. For example length, id, orientation... Another one is *level.java* this class represents a level in which we have the board, create the cars, verifies if a movement is valid, resets the level and level up. Furthermore, *game.java* which represents the game. In this class we can create a new game, see the score and is in charge of managing the different levels. And last but not least, *parking.java* this class represents the board.

**mainframe** this class is in charge of controlling the gui.

**level** is represented by a matrix of characters. The character '+' represents a wall, '*' a red car, '@' is the goal and the rest of letters are cars.

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

200320 - Julio Manso Sánchez-Tornero
200034 - Lucía Sánchez Navidad
200358 - Álvaro Dominguez Martín
200315 - Nihel Kella Bouziane