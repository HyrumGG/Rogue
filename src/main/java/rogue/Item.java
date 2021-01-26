package rogue;

import java.awt.Point;
import java.io.Serializable;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable {
    private int id;
    private String name = "";
    private String type = "";
    private String description = "";
    private Point location;
    private Character displayChar = 0;
    private Point xyLocation;
    private Room currentRoom;

    /**
     * Creates an item with blank fields.
     */
    public Item() {
        this(1, "", "", new Point(1, 1));
    }

    /**
     * Creates an item with specified id, name, type, and location.
     * @param newId The item's ID.
     * @param newName The item's name.
     * @param newType The item's type (ie. consumable).
     * @param newXyLocation The item's location given in Point object.
     */
    public Item(int newId, String newName, String newType, Point newXyLocation) {
        setId(newId);
        setName(newName);
        setType(newType);
        setXyLocation(newXyLocation);
    }

    /**
     * Gets the item's ID.
     * @return An integer representing the item's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the item's ID.
     * @param newId An integer representing the item's id.
     */
    public void setId(int newId) {
        id = newId;
    }

    /**
     * Gets the item's name.
     * @return A string representing the item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the item's name.
     * @param newName A string representing the item's name.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Gets the item's type (ie. consumable).
     * @return An integer representing the item's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the item's type (ie. consumable).
     * @param newType A string representing the item's type.
     */
    public void setType(String newType) {
        type = newType;
    }

    /**
     * Gets the item's character that represents it.
     * @return A character representing the Item.
     */
    public Character getDisplayCharacter() {
        return displayChar;
    }

    /**
     * Sets the item's character that represents it.
     * @param newDisplayCharacter A character representing the Item.
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        displayChar = newDisplayCharacter;
    }

    /**
     * Gets the item's description.
     * @return A string representing the item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the item's description.
     * @param newDescription A string representing the item's description.
     */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /**
     * Gets the item's x,y location.
     * @return A Point representing the item's location.
     */
    public Point getXyLocation() {
        return xyLocation;
    }

    /**
     * Sets the item's x,y location.
     * @param newXyLocation A Point representing the item's location.
     */
    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;
    }

    /**
     * Gets the item's current room.
     * @return A Room object representing the room the item is in.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the item's current room.
     * @param newCurrentRoom A Room object representing the room the item is in.
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        currentRoom = newCurrentRoom;
    }
}
