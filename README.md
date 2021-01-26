Name: Hyrum Nantais

Student Number: 1105303

Email: hnantais@uoguelph.ca

Note: The work done for this assignment was completed on my behalf, without copying or hiring anyone's work or advice on completing the program.

# Compilation Requirements
In order to compile the program, one must be using gradle 6.0 or newer
To run the program, you must also be using OpenJDK 11 or newer

# Compilation
To compile the program type the following command into a terminal/command line

gradle build

# To run the program
To run the program type the following command into a terminal/command line

java -jar build/libs/A3.jar

Please ensure that the program is run in the root directory of the project following MAVEN directory style. Also ensure there exists, a fileLocations.json file
containing a valid json file that contains symbols for the game, as well as a json file for the rooms to be setup. (in this directory)

# How to navigate the program
In order to move the player around the dungeon you can use either the keys:

W/Up-Arrow = up

A/Left-Arrow = left

S/Down-Arrow = down

D/Right-Arrow = right

To use items press one of the following keys and proceed to click on an item from the inventory with the mouse, selecting it, then pressing the corresponding button that appeared.

E = eat

W = wear

T = Toss

To exit the game press the Q key, then close the window, or just close the window.

# File Menu
The user is able to do several things from the File menu on the game window. Click the File button to make a dropdown appear. The following options are present

Change Player Name - Allows the user to change the name of the player

Load new JSON file - Prompts the user to pick a new adventure (json) file, if an error occurs, the user will be prompted to pick a new file.

Save Game - prompts the user to pick a file/choose a file and directory to save the game state in (cannot be .json)

Load Game - prompts the user to choose a file to load the game state from

upon error of saving or loading a file for the game state, the user will be prompted to reselect a file.