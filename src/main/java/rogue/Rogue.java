package rogue;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.io.Serializable;

/**
 * The rogue class; represents the game manager.
 */
public class Rogue implements Serializable {
    public static final char UP = 's';
    public static final char DOWN = 'x';
    public static final char LEFT = 'z';
    public static final char RIGHT = 'c';
    private String nextDisplay = "";
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Door> doors = new ArrayList<Door>();
    private transient RogueParser gameParser;
    private static HashMap<String, Character> symbols;
    private static ArrayList<Map<String, String>> globalItems;
    private Player thePlayer;
    private Room startRoom;
    private ArrayList<Item> myInventory;

    /**
     * Default constructor.
     */
    public Rogue() {
        setPlayer(new Player("Hyrum"));
        myInventory = new ArrayList<Item>();
    }

    /**
     * Constructor that sets parser for game setup.
     * @param theDungeonInfo A RogueParser object used to fetch/parse json into objects
     */
    public Rogue(RogueParser theDungeonInfo) {
        gameParser = theDungeonInfo;
        globalItems = gameParser.getGlobalItems();
        setSymbols();
        Map roomInfo;
        while ((roomInfo = gameParser.nextRoom()) != null) {
            addRoom(roomInfo);
        }
        setPlayer(new Player("Hyrum"));
        Map itemInfo;
        while ((itemInfo = gameParser.nextItem()) != null) {
            addItem(itemInfo);
        }
        Map doorInfo;
        while ((doorInfo = gameParser.nextDoor()) != null) {
            addDoor(doorInfo);
        }
        myInventory = new ArrayList<Item>();
        nextDisplay = getPlayer().getCurrentRoom().displayRoom();
    }

    /**
     * Calculates whether an item id exists in the playable items list.
     * @param itemID the id of the item to search for in the global items.
     * @return boolean representing whether the item is playable in the dungeon.
     */
    public static boolean isItemPlayable(int itemID) {
        for (Map<String, String> item : globalItems) {
            if (Integer.valueOf(item.get("id")) == itemID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates whether the symbols list does not exist.
     * @return boolean representing if the symbols list exists.
     */
    public static boolean symbolsExists() {
        if (symbols == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns value relating to key in static symbols array map.
     * @param key the key to find in the symbols.
     * @return Character representing the symbol corresponding to the key.
     */
    public static Character getSymbol(String key) {
        return symbols.get(key);
    }

    /**
     * Sets the player/character of the game.
     * @param newPlayer A Player object representing the playable character.
     */
    public void setPlayer(Player newPlayer) {
        for (Room myRoom : rooms) {
            myRoom.setPlayer(newPlayer);
        }
        if (newPlayer != null) {
            if (newPlayer.getCurrentRoom() == null) {
                if (startRoom != null) {
                    newPlayer.setCurrentRoom(startRoom);
                    startRoom.setPlayer(newPlayer);
                }
            }
            if (newPlayer.getXyLocation() == null) {
                newPlayer.setXyLocation(new Point(1, 1));
            }
            if (gameParser != null && newPlayer.getDisplayCharacter() == 0) {
                newPlayer.setDisplayCharacter(gameParser.getSymbol("PLAYER"));
            }
        }
        thePlayer = newPlayer;
    }

    /**
     * Sets the symbols map to contain all the usable symbols to represent things in the game.
     */
    public void setSymbols() {
        symbols = gameParser.getSymbolMap();
    }

    /**
     * Gets the player/character of the game.
     * @return A Player object representing the playable character.
     */
    public Player getPlayer() {
        return thePlayer;
    }

    /**
     * Gets the list of rooms in the game.
     * @return An arraylist representing the list of rooms in the game.
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the list of creatable items in the game.
     * @return An arraylist representing the list of available items in the game.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Gets the display string of the rooms.
     * @return A string representing the display string of the game.
     */
    public String getNextDisplay() {
        return nextDisplay;
    }

    /**
     * Sets the display string of the rooms to update.
     */
    public void setNextDisplay() {
        nextDisplay = getPlayer().getCurrentRoom().displayRoom();
    }

        /**
     * Adds a given item object to the players inventory (Item ArrayList).
     * @param toAdd An item object representing the item to be added to the player's inventory
     */
    public void addToInventory(Item toAdd) {
        myInventory.add(toAdd);
    }

    /**
     * Removes a given item object from the players inventory (Item ArrayList).
     * @param toRemove An item object representing the item to be removed from the player's inventory
     */
    public void removeFromInventory(Item toRemove) {
        myInventory.remove(toRemove);
    }

    /**
     * Gets the game inventory and returns a string of item names in the inventory.
     * @return String array representing the item names contained in the game inventory
     */
    public String[] getInventoryString() {
        String[] returnArr = new String[myInventory.size()];
        for (int i = 0; i < myInventory.size(); i++) {
            returnArr[i] = "";
            if (myInventory.get(i) instanceof Wearable && ((Wearable) myInventory.get(i)).isWorn()) {
                returnArr[i] += "(W) ";
            }
            returnArr[i] += myInventory.get(i).getName();
        }
        return returnArr;
    }

    /**
     * Calculates the next display of moving the player, and returns a message for the player.
     * @param input A character representing the input key entered by the user.
     * @return A string representing a message for the player.
     * @throws InvalidMoveException Exception that occurs when an invalid movement key was entered.
     */
    public String makeMove(char input) throws InvalidMoveException {
        Point playerLoc = getPlayer().getXyLocation();
        int oldX = (int) playerLoc.getX();
        int oldY = (int) playerLoc.getY();
        Room playerRoom = getPlayer().getCurrentRoom();
        movePlayer(input, playerLoc, oldX, oldY);
        moveToDoor(playerRoom);
        String msg = pickupItem(playerRoom, playerLoc);
        if ((oldX == playerLoc.getX() && oldY == playerLoc.getY()) || playerRoom.isWall(playerLoc)) {
            playerLoc.setLocation(oldX, oldY);
            throw new InvalidMoveException();
        }
        nextDisplay = getPlayer().getCurrentRoom().displayRoom();
        return msg.equals("") ? "That's a lovely move: " +  Character.toString(input) : msg;
    }

    private void moveToDoor(Room playerRoom) {
        for (Door findDoor : doors) {
            String doorDir = findDoor.getDir();
            int doorPos = findDoor.getPosition();
            if (findDoor.getDoorRoom() == playerRoom && playerRoom.getDoor(doorDir) == doorPos) {
                Point doorLoc = getDoorLocation(doorDir, doorPos, playerRoom);
                if (doorLoc != null && doorLoc.equals(getPlayer().getXyLocation())) {
                    Room moveTo = findDoor.getOtherRoom(playerRoom);
                    String moveDirection = getOppositeDirection(doorDir);
                    Point newLoc = getMoveLocation(moveDirection, moveTo.getDoor(moveDirection), moveTo);
                    moveTo.setPlayer(getPlayer());
                    getPlayer().setCurrentRoom(moveTo);
                    getPlayer().setXyLocation(newLoc);
                    break;
                }
            }
        }
    }

    private String pickupItem(Room playerRoom, Point playerLoc) {
        Item possibleItem = playerRoom.fetchItem(playerLoc);
        if (possibleItem != null) {
            playerRoom.removeItem(possibleItem);
            addToInventory(possibleItem);
            return getPlayer().getName() + " picked up " + possibleItem.getName();
        }
        return "";
    }

    private void movePlayer(char input, Point playerLoc, int oldX, int oldY) throws InvalidMoveException {
        if (input == Rogue.UP) {
            playerLoc.setLocation(oldX, oldY - 1);
        } else if (input == Rogue.DOWN) {
            playerLoc.setLocation(oldX, oldY + 1);
        } else if (input == Rogue.LEFT) {
            playerLoc.setLocation(oldX - 1, oldY);
        } else if (input == Rogue.RIGHT) {
            playerLoc.setLocation(oldX + 1, oldY);
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Creates a room object and adds it to ArrayList of rooms.
     * @param toAdd A map containing parsed json keys and the respective values.
     */
    public void addRoom(Map<String, String> toAdd) {
        Room curRoom = new Room();
        if ("true".equals(toAdd.get("start"))) {
            startRoom = curRoom;
            curRoom.setPlayer(getPlayer());
            if (getPlayer() != null) {
                getPlayer().setCurrentRoom(curRoom);
            }
        }
        setRoomKeys(toAdd, curRoom);
        rooms.add(curRoom);
    }

    private void setRoomKeys(Map<String, String> toAdd, Room curRoom) {
        curRoom.setId(Integer.valueOf(toAdd.get("id")));
        curRoom.setHeight(Integer.valueOf(toAdd.get("height")));
        curRoom.setWidth(Integer.valueOf(toAdd.get("width")));
        if (toAdd.containsKey("N")) {
            curRoom.setDoor("N", Integer.valueOf(toAdd.get("N")));
        }
        if (toAdd.containsKey("E")) {
            curRoom.setDoor("E", Integer.valueOf(toAdd.get("E")));
        }
        if (toAdd.containsKey("S")) {
            curRoom.setDoor("S", Integer.valueOf(toAdd.get("S")));
        }
        if (toAdd.containsKey("W")) {
            curRoom.setDoor("W", Integer.valueOf(toAdd.get("W")));
        }
    }

    /**
     * Creates an item object adding it to a room.
     * @param toAdd A map containing parsed json keys and the respective values.
     */
    public void addItem(Map<String, String> toAdd) {
        Item curItem = setNewItemType(toAdd);
        addItemInformation(toAdd, curItem);
        items.add(curItem);
        for (Room itemRoom : rooms) {
            addItemToRoom(toAdd, itemRoom, curItem);
            try {
                itemRoom.verifyRoom();
            } catch (NotEnoughDoorsException e) {
                addDoorToEmptyRoom(itemRoom);
            }
        }
    }

    private void addDoorToEmptyRoom(Room itemRoom) {
        boolean isConnection = false;
        for (Room connectingRoom : rooms) {
            if (connectingRoom != itemRoom) {
                String conDir = getEmptyDoorDirection(connectingRoom);
                if (!conDir.equals("-1")) {
                    String otherRoomDir = getOppositeDirection(conDir);
                    addDoor(createDoorMap(conDir, connectingRoom, itemRoom));
                    addDoor(createDoorMap(otherRoomDir, itemRoom, connectingRoom));
                    isConnection = true;
                    return;
                }
            }
        }
        if (!isConnection) {
            System.out.println("No doors in room, cannot find connection\n");
            System.exit(0);
        }
    }

    private void addItemToRoom(Map<String, String> toAdd, Room itemRoom, Item curItem) {
        if (toAdd.get("room") != null && itemRoom.getId() == Integer.valueOf(toAdd.get("room"))) {
            boolean validPosition = false;
            while (!validPosition) {
                try {
                    itemRoom.addItem(curItem);
                    validPosition = true;
                } catch (ImpossiblePositionException e) {
                    fixItemLocation(curItem.getXyLocation(), itemRoom);
                } catch (NoSuchItemException e) {
                    itemRoom.removeItem(curItem);
                    validPosition = true;
                }
            }
        }
    }

    private Item setNewItemType(Map<String, String> toAdd) {
        if (toAdd.get("type").equals("Food")) {
            return new Food();
        } else if (toAdd.get("type").equals("SmallFood")) {
            return new SmallFood();
        } else if (toAdd.get("type").equals("Clothing")) {
            return new Clothing();
        } else if (toAdd.get("type").equals("Potion")) {
            return new Potion();
        } else if (toAdd.get("type").equals("Ring")) {
            return new Ring();
        }
        return new Item();
    }

    /**
     * Takes selected item in inventory and either eats, wears, tosses the item, or computes an error.
     * @param index integer presenting the position in the inventory the selected item is
     * @param operation a string representing what type of operation is happening with the item (wear, eat, toss)
     * @return String representing user output when using the selected item
     */
    public String itemOptions(int index, String operation) {
        if (index != -1) {
            if (operation.equals("Wear Item") && myInventory.get(index) instanceof Wearable) {
                Wearable item = (Wearable) myInventory.get(index);
                return item.wear();
            } else if (operation.equals("Eat Item") && myInventory.get(index) instanceof Edible) {
                Edible item = (Edible) myInventory.get(index);
                removeFromInventory(myInventory.get(index));
                return item.eat();
            } else if (operation.equals("Toss Item") && myInventory.get(index) instanceof Tossable) {
                Tossable item = (Tossable) myInventory.get(index);
                tossItem(myInventory.get(index));
                return item.toss();
            }
        }
        return "that item can't be used in that fashion";
    }

    private void tossItem(Item myItem) {
        removeFromInventory(myItem);
        int x = (int) getPlayer().getXyLocation().getX();
        int y = (int) getPlayer().getXyLocation().getY();
        myItem.getXyLocation().setLocation(x, y);
        boolean validPosition = false;
        while (!validPosition) {
            try {
                myItem.setCurrentRoom(getPlayer().getCurrentRoom());
                getPlayer().getCurrentRoom().addItem(myItem);
                validPosition = true;
            } catch (ImpossiblePositionException e) {
                fixItemLocation(myItem.getXyLocation(), getPlayer().getCurrentRoom());
            } catch (NoSuchItemException e) {
                getPlayer().getCurrentRoom().removeItem(myItem);
                validPosition = true;
            }
        }
    }

    private void fixItemLocation(Point itemLoc, Room itemRoom) {
        int x = (int) itemLoc.getX();
        int y = (int) itemLoc.getY();
        if (y == 0 || y >= itemRoom.getHeight() - 1) {
            y = 1;
        } else {
            x = x + 1;
            if (x >= itemRoom.getWidth()) {
                x = 1;
                y = (y + 1) % itemRoom.getHeight();
                if (y == 0) {
                    y = 1;
                }
            }
        }
        itemLoc.setLocation(x, y);
    }

    private void addItemInformation(Map<String, String> toAdd, Item curItem) {
        curItem.setId(Integer.valueOf(toAdd.get("id")));
        if (toAdd.containsKey("name") && toAdd.containsKey("type")) {
            curItem.setName(toAdd.get("name"));
            curItem.setType(toAdd.get("type"));
            String type = toAdd.get("type");
            if (type != null && gameParser.getSymbol(type.toUpperCase()) != null) {
                curItem.setDisplayCharacter(gameParser.getSymbol(toAdd.get("type").toUpperCase()));
            }
        }
        if (toAdd.containsKey("description")) {
            curItem.setDescription(toAdd.get("description"));
        }
        if (toAdd.containsKey("x") && toAdd.containsKey("y")) {
            curItem.setXyLocation(new Point(Integer.valueOf(toAdd.get("x")), Integer.valueOf(toAdd.get("y"))));
        }
    }

    private void addDoor(Map<String, String> toAdd) {
        Door curDoor = new Door();
        for (Room curRoom : rooms) {
            if (curRoom.getId() == Integer.valueOf(toAdd.get("room"))) {
                curDoor.connectRoom(curRoom);
                curDoor.setDoorRoom(curRoom);
                curDoor.setDir(toAdd.get("dir"));
                curDoor.setPosition(Integer.valueOf(toAdd.get("wall_pos")));
                curRoom.setDoor(toAdd.get("dir"), Integer.valueOf(toAdd.get("wall_pos")));
            } else if (curRoom.getId() == Integer.valueOf(toAdd.get("con_room"))) {
                curDoor.connectRoom(curRoom);
            }
        }
        doors.add(curDoor);
    }

    private Map<String, String> createDoorMap(String dir, Room curRoom, Room conRoom) {
        Map<String, String> doorMap = new HashMap<String, String>();
        doorMap.put("room", String.valueOf(curRoom.getId()));
        doorMap.put("dir", dir);
        doorMap.put("wall_pos", "1");
        doorMap.put("con_room", String.valueOf(conRoom.getId()));
        return doorMap;
    }

    private String getOppositeDirection(String dir) {
        if (dir.equals("N")) {
            return "S";
        } else if (dir.equals("E")) {
            return "W";
        } else if (dir.equals("S")) {
            return "N";
        } else if (dir.equals("W")) {
            return "E";
        }
        return "";
    }

    private Point getMoveLocation(String dir, int position, Room myRoom) {
        if (dir.equals("N")) {
            return new Point(position, 1);
        } else if (dir.equals("S")) {
            return new Point(position, myRoom.getHeight() - 2);
        } else if (dir.equals("E")) {
            return new Point(myRoom.getWidth() - 2, position);
        } else if (dir.equals("W")) {
            return new Point(1, position);
        }
        return null;
    }

    private Point getDoorLocation(String dir, int position, Room myRoom) {
        if (dir.equals("N")) {
            return new Point(position, 0);
        } else if (dir.equals("S")) {
            return new Point(position, myRoom.getHeight() - 1);
        } else if (dir.equals("E")) {
            return new Point(myRoom.getWidth() - 1, position);
        } else if (dir.equals("W")) {
            return new Point(0, position);
        }
        return null;
    }

    private String getEmptyDoorDirection(Room myRoom) {
        if (myRoom.getDoor("N") == -1) {
            return "N";
        } else if (myRoom.getDoor("E") == -1) {
            return "E";
        } else if (myRoom.getDoor("S") == -1) {
            return "S";
        } else if (myRoom.getDoor("W") == -1) {
            return "W";
        }
        return "-1";
    }

    /**
     * Displays all the rooms in game with respective player, doors,
     * and items at specified locations.
     * @return A string representing the display of all the rooms in the game.
     */
    public String displayAll() {
        //creates a string that displays all the rooms in the dungeon
        String returnVal = "";
        for (Room curRoom : getRooms()) {
            returnVal += curRoom.displayRoom();
        }
        return returnVal;
    }
}
