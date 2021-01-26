
## Class RogueParser

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public RogueParser(String filename)|takes filename and sets up object|None|parseLocations(filename)<br>parse()|None|8
|public RogueParser(String locationsFile, String jsonFile)|takes locations filename and json filename and sets up object|None|parseLocations(locationsFile, jsonFile)|None|8
|public Map nextRoom()|return next room|roomIterator|None|None|7
|public Map nextItem()|returns next item present in a room|itemIterator|None|None|7
|public ArrayList<Map<String, String>> getGlobalItems()|gets the list of globally usable items in the game|items|None|None|3
|public Map nextDoor()|returns the next door in all the rooms|doorIterator|None|None|7
|public Character getSymbol(String symbolName)|get character of symbol|symbols|None|None|6
|public HashMap<String, Character> getSymbolMap()|get map containing all the representable symbols|symbols|None|None|3
|public int getNumOfItems()|get the number of items|numOfItems|None|None|3
|public int getNumOfRooms()|get the number of rooms|numOfRooms|None|None|3
|private void parseLocations(String filename)|read file containing the file location and open room, symbols files|configJSON, roomsJSON, symbolsJSON|new JSONParser()<br>new FileReader()|None|10
|private void parseLocations(String filename, String roomsFilename)|read file containing the file location and open the given room file and symbols file|configJSON, roomsJSON, symbolsJSON|new JSONParser()<br>new FileReader()|None|10
|private void parse()|parses the room, item, symbol data, initializing the iterators for each|roomsJSON, symbolsJSON, roomIterator, itemIterator, doorIterator|extractRoomInfo(roomsJSON)<br>extractItemInfo(roomsJSON)<br>extractSymbolInfo(symbolsJSON), ArrayList.iterator()|None|12
|private void extractSymbolInfo(JSONObject jsonSymbols)|get symbol information|symbols|None|None|7
|private extractRoomInfo(JSONObject jsonRooms)|get room information|numOfRooms, rooms|singleRoom(roomsJSONArray.get(i))|None|7
|private Map<String, String> singleRoom(JSONObject roomJSON)|get a rooms information|itemLocations|new HashMap()<br>setRoomJSON(room, roomJSON)<br>setDoorJSON(room, roomJSON)<br>itemPosition()|None|11
|private void setRoomJSON(HashMap<String, String> room, JSONObject roomsJSON)|sets the json information for the room in the map|None|None|None|10
|private void setDoorJSON(HashMap<String, String> room, JSONObject roomJSON)|set the json information for the door in the map|doorLocations|new HashMap()|None|15
|private Map<String, String> itemPosition(JSONObject lootJSON, String roomID)|creates a map for information of an item in a room|None|new HashMap()|None|8
|private extractItemInfo(JSONObject jsonRooms)|get the item information from the item key|numOfItems, items|singleItem(itemsJSONArray.get(i))|None|7
|private Map<String, String> singleItem(JSONObject itemsJSON)|get a single item from its JSON object|itemLocations|new HashMap()|None|18
