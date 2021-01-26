package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RogueParser {

    private ArrayList<Map<String, String>> rooms = new ArrayList<>();
    private ArrayList<Map<String, String>> items = new ArrayList<>();
    private ArrayList<Map<String, String>> doorLocations = new ArrayList<>();
    private ArrayList<Map<String, String>> itemLocations = new ArrayList<>();
    private HashMap<String, Character> symbols = new HashMap<>();

    private Iterator<Map<String, String>> doorIterator;
    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> itemIterator;
    private JSONObject configJSON;
    private JSONObject roomsJSON;
    private JSONObject symbolsJSON;

    private int numOfRooms = -1;
    private int numOfItems = -1;

    /**
     * Default constructor.
     */
    public RogueParser() {

    }

    /**
     * Constructor that takes filename and sets up the object.
     * @param filename  (String) name of file that contains file location for rooms and symbols
     * @throws Exception when an error occurs setting up the objects throw exception to main manager to handle
     */
    public RogueParser(String filename) throws Exception {
        try {
            parseLocations(filename);
            parse();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /**
     * Constructor that takes filename and sets up the object.
     * @param locationsFile  (String) name of file that contains file location for rooms and symbols
     * @param jsonFile (String) name of file containing the rooms json
     * @throws Exception when an error occurs setting up the objects throw exception to main manager to handle
     */
    public RogueParser(String locationsFile, String jsonFile) throws Exception {
        try {
            parseLocations(locationsFile, jsonFile);
            parse();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /**
     * Return the next room.
     * @return (Map) Information about a room.
     */
    public Map nextRoom() {
        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the next item present in a room.
     * @return (Map) Information about an item
     */
    public Map nextItem() {
        if (itemIterator.hasNext()) {
            return itemIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the list of global items usable within the dungeon.
     * @return Arraylist of maps containing information for each global item.
     */
    public ArrayList<Map<String, String>> getGlobalItems() {
        return items;
    }

    /**
     * Returns the next door in all the rooms.
     * @return (Map) Information about a door.
     */
    public Map nextDoor() {
        if (doorIterator.hasNext()) {
            return doorIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Get the character for a symbol.
     * @param symbolName (String) Symbol Name
     * @return (Character) Display character for the symbol
     */
    public Character getSymbol(String symbolName) {

        if (symbols.containsKey(symbolName)) {
            return symbols.get(symbolName);
        }

        // Does not contain the key
        return null;
    }

    /**
     * Get the map containing all the representable symbols.
     * @return a HashMap containing the symbols to use in the game.
     */
    public HashMap<String, Character> getSymbolMap() {
        return symbols;
    }

    /**
     * Get the number of items.
     * @return (int) Number of items
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     * Get the number of rooms.
     * @return (int) Number of rooms
     */
    public int getNumOfRooms() {
        return numOfRooms;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     * @throws Exception when an error occurs setting up the objects throw exception to main manager to handle
     */
    private void parseLocations(String filename) throws Exception {
        JSONParser parser = new JSONParser();
        try {
            configJSON = (JSONObject) parser.parse(new FileReader(filename));
            roomsJSON = (JSONObject) parser.parse(new FileReader((String) configJSON.get("Rooms")));
            symbolsJSON = (JSONObject) parser.parse(new FileReader((String) configJSON.get("Symbols")));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     * @param roomsFilename (String) name of rooms json file to open
     * @throws Exception when an error occurs setting up the objects throw exception to main manager to handle
     */
    private void parseLocations(String filename, String roomsFilename) throws Exception {
        JSONParser parser = new JSONParser();
        try {
            configJSON = (JSONObject) parser.parse(new FileReader(filename));
            roomsJSON = (JSONObject) parser.parse(new FileReader((String) roomsFilename));
            symbolsJSON = (JSONObject) parser.parse(new FileReader((String) configJSON.get("Symbols")));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private void parse() throws Exception {
        try {
            extractRoomInfo(roomsJSON);
            extractItemInfo(roomsJSON);
            extractSymbolInfo(symbolsJSON);
            roomIterator = rooms.iterator();
            itemIterator = items.iterator();
            doorIterator = doorLocations.iterator();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /**
     * Get the symbol information.
     * @param jsonSymbols (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject jsonSymbols) {
        JSONArray symbolsJSONArray = (JSONArray) jsonSymbols.get("symbols");

        // Make an array list of room information as maps
        for (int i = 0; i < symbolsJSONArray.size(); i++) {
            JSONObject symbolObj = (JSONObject) symbolsJSONArray.get(i);
            symbols.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")).charAt(0));
        }
    }

    /**
     * Get the room information.
     * @param jsonRooms (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject jsonRooms) {
        JSONArray roomsJSONArray = (JSONArray) jsonRooms.get("room");

        // Make an array list of room information as maps
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            rooms.add(singleRoom((JSONObject) roomsJSONArray.get(i)));
            numOfRooms += 1;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {
        HashMap<String, String> room = new HashMap<>();
        setRoomJSON(room, roomJSON);
        setDoorJSON(room, roomJSON);

        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        // Loop through each item and update the hashmap
        for (int j = 0; j < lootArray.size(); j++) {
            itemLocations.add(itemPosition((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }
        return room;
    }

    private void setRoomJSON(HashMap<String, String> room, JSONObject roomJSON) {
        room.put("id", roomJSON.get("id").toString());
        room.put("start", roomJSON.get("start").toString());
        room.put("height", roomJSON.get("height").toString());
        room.put("width", roomJSON.get("width").toString());
        room.put("E", "-1");
        room.put("N", "-1");
        room.put("S", "-1");
        room.put("W", "-1");
    }

    private void setDoorJSON(HashMap<String, String> room, JSONObject roomJSON) {
        // Update the map with any doors in the room
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int j = 0; j < doorArray.size(); j++) {
            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = String.valueOf(doorObj.get("dir"));
            room.replace(dir, doorObj.get("wall_pos").toString());

            HashMap<String, String> door = new HashMap<>();
            door.put("room", room.get("id"));
            door.put("dir", dir);
            door.put("wall_pos", doorObj.get("wall_pos").toString());
            door.put("con_room", String.valueOf(doorObj.get("con_room")));
            doorLocations.add(door);
        }
    }

    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map<String, String>) Contains information about the item, where it is and what room
     */
    private Map<String, String>  itemPosition(JSONObject lootJSON, String roomID) {
        HashMap<String, String> loot = new HashMap<>();
        loot.put("room", roomID);
        loot.put("id", lootJSON.get("id").toString());
        loot.put("x", lootJSON.get("x").toString());
        loot.put("y", lootJSON.get("y").toString());

        return loot;
    }

    /**
     * Get the Item information from the Item key.
     * @param jsonRooms (JSONObject) The entire JSON file that contains keys for room and items
     */
    private void extractItemInfo(JSONObject jsonRooms) {
        JSONArray itemsJSONArray = (JSONArray) jsonRooms.get("items");

        for (int i = 0; i < itemsJSONArray.size(); i++) {
            items.add(singleItem((JSONObject) itemsJSONArray.get(i)));
            numOfItems += 1;
        }
    }

    /**
     * Get a single item from its JSON object.
     * @param itemsJSON (JSONObject) JSON version of an item
     * @return (Map<String, String>) Contains information about a single item
     */
    private Map<String, String>  singleItem(JSONObject itemsJSON) {
        HashMap<String, String> item = new HashMap<>();
        item.put("id", itemsJSON.get("id").toString());
        item.put("name", itemsJSON.get("name").toString());
        item.put("type", itemsJSON.get("type").toString());
        if (itemsJSON.containsKey("description")) {
            item.put("description", itemsJSON.get("description").toString());
        }

        for (Map<String, String> itemLocation : itemLocations) {
            if (itemLocation.get("id").toString().equals(item.get("id").toString())) {
                item.put("room", itemLocation.get("room"));
                item.put("x", itemLocation.get("x"));
                item.put("y", itemLocation.get("y"));
                break;
            }
        }
        return item;
    }
}
