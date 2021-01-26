package rogue;

import java.util.ArrayList;
import java.awt.Point;
import java.util.HashMap;
import java.io.Serializable;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room implements Serializable {
    private int width;
    private int height;
    private int id;
    private ArrayList<Item> roomItems;
    private HashMap<String, Integer> doors = new HashMap<String, Integer>();
    private Player thePlayer;

    /**
     * Creates a room with blank fields.
     */
    public Room() {
        this(0, 0, 0);
    }

    /**
     * Creates a room with specified width, height, id, and Rogue.symbols fields.
     * @param newWidth An integer representing the width of the room.
     * @param newHeight An integer representing the height of the room.
     * @param newId An integer representing the id of the room.
     */
    public Room(int newWidth, int newHeight, int newId) {
        setWidth(newWidth);
        setHeight(newHeight);
        setId(newId);
    }

    /**
     * Adds an item to room item list.
     * @param toAdd Item object to be added into the rooms item list.
     * @throws ImpossiblePositionException Exception that occurs when an item position is invalid/overlaps.
     * @throws NoSuchItemException Exception that occurs when an item id does not exist in the games item list.
     */
    public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException {
        toAdd.setCurrentRoom(this);
        if (roomItems == null) {
            roomItems = new ArrayList<Item>();
        }
        if (!validItem(toAdd)) {
            throw new ImpossiblePositionException();
        }
        roomItems.add(toAdd);
        if (!Rogue.isItemPlayable(toAdd.getId())) {
            throw new NoSuchItemException();
        }
    }

    /**
     * Gets the width of the room.
     * @return An integer representing the width of the room.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the room.
     * @param newWidth An integer representing the width of the room.
     */
    public void setWidth(int newWidth) {
        width = newWidth;
    }

    /**
     * Gets the height of the room.
     * @return An integer representing the height of the room.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the room.
     * @param newHeight An integer representing the height of the room.
     */
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    /**
     * Gets the ID of the room.
     * @return An integer representing the id of the room.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the room.
     * @param newId An integer representing the id of the room.
     */
    public void setId(int newId) {
        id = newId;
    }

    /**
     * Gets list of items in the room.
     * @return An arraylist representing the items located within the room.
     */
    public ArrayList<Item> getRoomItems() {
        return roomItems;
    }

    /**
     * Sets list of items in the room.
     * @param newRoomItems An arraylist representing the items located within the room.
     */
    public void setRoomItems(ArrayList<Item> newRoomItems) {
        roomItems = newRoomItems;
    }

    /**
     * Gets the player/character of the game.
     * @return A Player object representing the playable character.
     */
    public Player getPlayer() {
        return thePlayer;
    }

    /**
     * Sets the player/character of the game.
     * @param newPlayer A Player object representing the playable character.
     */
    public void setPlayer(Player newPlayer) {
        thePlayer = newPlayer;
    }

    /**
     * Gets a doors position on a given direction's wall.
     * direction is one of NSEW; location is a number between 0 and the length of the wall.
     * @param direction A string representing the direction of the wall to check for a door.
     * @return An integer representing the location of the door on the wall.
     */
    public int getDoor(String direction) {
        if (doors.containsKey(direction)) {
            return doors.get(direction);
        }
        return -1;
    }

    /**
     * Sets a doors position on a given direction's wall.
     * direction is one of NSEW; location is a number between 0 and the length of the wall.
     * @param direction A string representing the direction of the wall to check for a door.
     * @param location An integer representing the location of the door on the wall.
     */
    public void setDoor(String direction, int location) {
        doors.put(direction, location);
    }

    /**
     * Calculates whether the player is inside the room.
     * @return A boolean representing whether the player is inside the room or not.
     */
    public boolean isPlayerInRoom() {
        if (getPlayer() != null) {
            if (getPlayer().getCurrentRoom() == this) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ensures the room is complete and valid, containing no overlaps or bound errors.
     * @return boolean representing whether the room is complete
     * @throws NotEnoughDoorsException Exception that occurs when a room does not have any doors.
     */
    public boolean verifyRoom() throws NotEnoughDoorsException {
        if (roomItems != null) {
            for (Item curItem : roomItems) {
                if (!validItem(curItem)) {
                    return false;
                }
            }
        }
        if (!validPlayer()) {
            return false;
        }
        if (getDoor("N") == -1 && getDoor("E") == -1 && getDoor("S") == -1 && getDoor("W") == -1) {
            throw new NotEnoughDoorsException();
        }
        return true;
    }

    /**
     * Calculates whether the location given by the point parameter passed corresponds to a wall.
     * @param location A point representing the xy location to be checked.
     * @return a boolean reprenting whether the location given maps to a wall.
     */
    public boolean isWall(Point location) {
        int x = (int) location.getX();
        int y = (int) location.getY();
        if ((y == 0 || y == getHeight() - 1 || x == 0 || x == getWidth() - 1) && !isDoor(y, x)) {
            return true;
        }
        return false;
    }

    /**
     * Removes given item object from room item list if it exists in the room.
     * @param toRemove Item object representing the item to find and remove from the room.
     */
    public void removeItem(Item toRemove) {
        if (getRoomItems() == null || toRemove == null) {
            return;
        }
        getRoomItems().remove(toRemove);
    }

    /**
     * Calculates if the given location corresponds to an item and if true returns the item.
     * @param location A point representing the xy location to be checked.
     * @return a Item object or null value based on whether the location maps to an item.
     */
    public Item fetchItem(Point location) {
        if (getRoomItems() == null) {
            return null;
        }
        for (Item myItem : getRoomItems()) {
            int x = (int) myItem.getXyLocation().getX();
            int y = (int) myItem.getXyLocation().getY();
            if (location.getX() == x && location.getY() == y) {
                return myItem;
            }
        }
        return null;
    }

    private boolean validPlayer() {
        if (isPlayerInRoom()) {
            Point location = getPlayer().getXyLocation();
            int x = (int) location.getX();
            int y = (int) location.getY();
            if (isDoor(y, x) || displayWall(y, x) != 0 || displayItem(x, y) != 0 || !inBounds(x, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean validItem(Item myItem) {
        int x = (int) myItem.getXyLocation().getX();
        int y = (int) myItem.getXyLocation().getY();
        if (isPlayer(x, y) || isDoor(y, x) || displayWall(y, x) != 0 || !inBounds(x, y) || isItem(x, y, myItem)) {
            return false;
        }
        return true;
    }

    private boolean isItem(int x, int y, Item check) {
        if (getRoomItems() == null) {
            return false;
        }
        for (Item myItem : getRoomItems()) {
            if (myItem != check) {
                Point location = myItem.getXyLocation();
                if (location.getX() == x && location.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inBounds(int x, int y) {
        if (x >= getWidth() || x < 0 || y >= getHeight() || y < 0) {
            return false;
        }
        return true;
    }

    private boolean isPlayer(int x, int y) {
        if (isPlayerInRoom()) {
            Point location = getPlayer().getXyLocation();
            if (location != null && location.getX() == x && location.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoor(int row, int col) {
        if (row == 0) {
            return col == getDoor("N");
        } else if (row == getHeight() - 1) {
            return col == getDoor("S");
        } else if (col == 0) {
            return row == getDoor("W");
        } else if (col == getWidth() - 1) {
            return row == getDoor("E");
        }
        return false;
    }

    private Character displayWall(int row, int col) {
        if (Rogue.symbolsExists()) {
            if ((row == 0 || row == getHeight() - 1) && Rogue.getSymbol("NS_WALL") != null) {
                return Rogue.getSymbol("NS_WALL");
            } else if ((col == 0 || col == getWidth() - 1) && Rogue.getSymbol("EW_WALL") != null) {
                return Rogue.getSymbol("EW_WALL");
            }
        }
        return 0;
    }

    private Character displayItem(int x, int y) {
        if (getRoomItems() == null) {
            return 0;
        }
        for (Item myItem : getRoomItems()) {
            Point location = myItem.getXyLocation();
            if (myItem.getDisplayCharacter() != null && location.getX() == x && location.getY() == y) {
                return myItem.getDisplayCharacter();
            }
        }
        return 0;
    }

    /**
     * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
     * @return A String representation of how the room looks.
     */
    public String displayRoom() {
        if (Rogue.symbolsExists()) {
            String roomString = "";
            for (int row = 0; row < getHeight(); row++) {
                roomString += displayLine(row);
                if (row + 1 < getHeight()) {
                    roomString += '\n';
                }
            }
            return roomString;
        }
        return "Room symbols not loaded";
    }

    private String displayLine(int row) {
        String line = "";
        for (int col = 0; col < getWidth(); col++) {
            line += displayChar(row, col);
        }
        return line;
    }

    private Character displayChar(int row, int col) {
        if (isPlayer(col, row)) {
            return getPlayer().getDisplayCharacter();
        } else if (isDoor(row, col)) {
            return Rogue.getSymbol("DOOR");
        } else if (displayItem(col, row) != 0) {
            return displayItem(col, row);
        } else if (displayWall(row, col) != 0) {
            return displayWall(row, col);
        }
        return Rogue.getSymbol("FLOOR");
    }
}
