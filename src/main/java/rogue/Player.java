package rogue;

import java.awt.Point;
import java.io.Serializable;

/**
 * The player character.
 */
public class Player implements Serializable {
    private String name = "";
    private Room curRoom;
    private Point xyLocation;
    private Character displayChar = 0;

    /**
     * Creates a player with default fields.
     */
    public Player() {
        this("Bilbo", new Point(1, 1));
    }

    /**
     * Creates a player with specified name and default location.
     * @param newName The players name.
     */
    public Player(String newName) {
        this(newName, new Point(1, 1));
    }

    /**
     * Creates a player with specified name and location.
     * @param newName The player's name.
     * @param newXyLocation The player's location.
     */
    public Player(String newName, Point newXyLocation) {
      setName(newName);
      setXyLocation(newXyLocation);
    }

    /**
     * Gets the player's name.
     * @return A string representing the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * @param newName A string representing the player's name.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Gets the player's location.
     * @return A Point representing the player's x,y location.
     */
    public Point getXyLocation() {
        return xyLocation;
    }

    /**
     * Sets the player's location.
     * @param newXyLocation A Point representing the player's x,y location.
     */
    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;
    }

    /**
     * Gets the player's current room.
     * @return A Room object representing the room the player is inside.
     */
    public Room getCurrentRoom() {
        return curRoom;
    }

    /**
     * Sets the player's current room.
     * @param newRoom A Room object representing the room the player is inside.
     */
    public void setCurrentRoom(Room newRoom) {
        curRoom = newRoom;
    }

    /**
     * Gets the player's character that represents it.
     * @return A character representing the player.
     */
    public Character getDisplayCharacter() {
        return displayChar;
    }

    /**
     * Sets the player's character that represents it.
     * @param newDisplayCharacter A character representing the player.
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        displayChar = newDisplayCharacter;
    }
}
