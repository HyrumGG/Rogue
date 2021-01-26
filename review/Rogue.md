
## Class Rogue

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Rogue()|Initialize player and inventory variables with defaults value|myInventory|getPlayer()<br>setPlayer(new Player("Hyrum"))|Player|6
|public Rogue(RogueParser theDungeonInfo)|constructor that sets the parser, using it for game setup|gameParser, globalItems, myInventory, nextDisplay|gameParser.getGlobalItems()<br>setSymbols()<br>gameParser.nextRoom()<br>addRoom(roomInfo)<br>getPlayer()<br>setPlayer(new Player("Hyrum"))<br>gameParser.nextItem()<br>addItem(itemInfo)<br>gameParser.nextDoor()<br>addDoor(doorInfo)<br>getPlayer().getCurrentRoom().displayRoom()|RogueParser, Player, Item|20
|public static boolean isItemPlayable(int itemID)|Finds out if an item id exists in the games playable items list|globalItems|None|None|8
|public static boolean symbolsExists()|returns a boolean whether the games symbol list exists or not|symbols|None|None|6
|public static Character getSymbol(String key)|returns value matching given key in symbols map|symbols|None|None|3
|public void setPlayer(Player newPlayer)|sets the player of the game|thePlayer, startRoom, gameParser, rooms|myRoom.setPlayer(newPlayer)<br>newPlayer.getCurrentRoom()<br>newPlayer.setCurrentRoom()<br>startRoom.setPlayer(newPlayer)<br>newPlayer.getXyLocation()<br>newPlayer.setXyLocation(new Point(1, 1))<br>newPlayer.getDisplayCharacter()<br>newPlayer.setDisplayCharacter()<br>gameParser.getSymbol("PLAYER")|Room, Player, RogueParser|20
|public void setSymbols()|Sets the symbols map to contain all the game's usable symbols|symbols, gameParser|gameParser.getSymbolMap()|RogueParser|3
|public Player getPlayer()|gets the player object of the game|thePlayer|None|Player returned|3
|public ArrayList<Room> getRooms()|gets the list of room objects in the game|rooms|None|Room list returned|3
|public ArrayList<Item> getItems()|gets the list of item objects in the game|items|None|Item list returned|3
|public String getNextDisplay()|gets the current display of the active room|nextDisplay|None|None|3
|public void setNextDisplay()|updates the display string with the active room object|nextDisplay|getPlayer().getCurrentRoom().displayRoom()|Player, Room|3
|public void addToInventory(Item toAdd)|adds a given item object to the games inventory list|myInventory|myInventory.add(toAdd)|Item|3
|public void removeFromInventory(Item toRemove)|removes a given item object from the games inventory|myInevntory|myInventory.remove(toRemove)|Item|3
|public string[] getInventoryString()|gets the games inventory and converts its items to a string array|myInventory|myInventory.get(i).isWorn()<br>myInventory.get(i).getName()|Item|11
|public String makeMove(char input)|calculates the next display after moving the player, returning a message for the user|nextDisplay|getPlayer()<br>getPlayer().getXyLocation()<br>getPlayer().getCurrentRoom()<br>room.displayRoom()<br>movePlayer(input, playerLoc, oldX, oldY)<br>moveToDoor(playerRoom)<br>pickupItem(playerRoom, playerLoc)<br>playerRoom.isWall(playerLoc)<br>|Player, Room, Item|15
|private void moveToDoor(Room playerRoom)|moves the player to a new room if the player walked through a door|None|findDoor.getDir()<br>findDoor.getPosition()<br>findDoor.getDoorRoom()<br>playerRoom.getDoor(doorDir)<br>getDoorLocation(doorDir, doorPos, playerRoom)<br>getPlayer().getXyLocation()<br>findDoor.getOtherRoom(playerRoom)<br>getOppositeDirection(doorDir)<br>getMoveLocation(moveDirection, moveTo.getDoor(moveDirection), moveTo)<br>moveTo.setPlayer(getPlayer())<br>getPlayer().setCurrentRoom(moveTo)<br>getPlayer().setXyLocation(newLoc)|Door, Player, Room|18
|private String pickupItem(Room playerRoom, Point playerLoc)|picks up an item at the given location in the specified room, adding it to the game inventory|None|playerRoom.fetchItem(playerLoc)<br>playerRoom.removeItem(possibleItem)<br>addToInventory(possibleItem)<br>getPlayer().getName()<br>possibleItem.getName()|Item, Room, Player|9
|private void movePlayer(char input, Point playerLoc, int oldX, int oldY)|Moves the player 1 square based on the given character input|None|None|None|13
|public void addRoom(Map<String, String> toAdd)|creates a room object and adds it to games list of rooms|rooms, startRoom|new Room()<br>curRoom.setPlayer(getPlayer())<br>getPlayer().setCurrentRoom(curRoom)<br>setRoomKeys(toAdd, curRoom)|Room, Player|12
|private void setRoomKeys(Map<String, String> toAdd, Room curRoom)|initializes the given room object with the values given in the map|None|curRoom.setId()<br>curRoom.setHeight()<br>curRoom.setWidth()<br>curRoom.setDoor()|Room|17
|public void addItem(Map<String, String> toAdd)|creates an item object adding it to a room and the games item list|items, rooms|setNewItemType(toAdd)<br>addItemToRoom(toAdd, itemRoom, curItem)<br>itemRoom.verifyRoom()<br>addDoorToEmptyRoom(itemRoom)|Room, Item|13
|private void addDoorToEmptyRoom(Room itemRoom)|adds given item to given room|None|itemRoom.getId()<br>itemRoom.addItem(curItem)<br>curItem.getXyLocation()<br>fixItemLocation(location, itemRoom)<br>itemRoom.removeItem(curItem)|Item, Room|16
|private Item setItemType(Map<String, String> toAdd)|Finds out the type of item being added, and returns an item of the proper type|None|None|Item|14
|public String itemOptions(int index, String operation)|takes selected item in inventory and eats, wears, or tosses the item|myInventory|item.wear()<br>item.eat()<br>item.toss()|Item|17
|private void tossItem(Item myItem)|removes an item from inventory and places it in current room|None|removeFromInventory(myItem)<br>getPlayer().getXyLocation()<br>getPlayer().getCurrentRoom()<br>room.addItem()<br>item.getXyLocation()<br>fixItemLocation(location, getPlayer.getCurrentRoom())<br>getPlayer().getCurrentRoom().removeItem(myItem)<br>item.setCurrentRoom(room)|Item, Room, Player|18
|private void fixItemLocation(Point itemLoc, Room itemRoom)|Moves an item to be within bounds of the given room|None|itemRoom.getHeight()<br>itemRoom.getWidth()|Room|17
|private void addItemInformation(Map<String, String> toAdd, Item curItem)|initialize given item's fields|gameParser|curItem.setId()<br>curItem.setName()<br>curItem.setType()<br>gameParser.getSymbol()<br>curItem.setDisplayCharacter()<br>curItem.setDescription()<br>curItem.setXyLocation()|Item, RogueParser|17
|private void addDoor(Map<String, String> toAdd)|adds a door object to the games doors list, and adds it to the proper room|doors, rooms|new Door()<br>curRoom.getId()<br>curDoor.connectRoom(curRoom)<br>curDoor.setDoorRoom(curRoom)<br>curDoor.setDir()<br>curDoor.setPosition()<br>curRoom.setDoor()|Door, Room|15
|private Map<String, String> createDoorMap(String dir, Room curRoom, Room conRoom)|creates a map containing json information corresponding to doors|None|curRoom.getId()|Room|8
|private String getOppositeDirection(String dir)|returns the opposite direction given|None|None|None|12
|private Point getMoveLocation(String dir, int position, Room myRoom)|Gets a point for player to spawn on when walking through door taking a direction and position on wall|None|myRoom.getHeight()<br>myRoom.getWidth()|Room|12
|private Point getDoorLocation(String dir, int position, Room myRoom)|Gets a point location by taking a direction and position on wall|None|myRoom.getHeight()<br>myRoom.getWidth()|Room|12
|private String getEmptyDoorDirection(Room myRoom)|Gets first wall direction on a given room that has no doors|None|myRoom.getDoor("N")|Room|12
|public String displayAll()|displays all the rooms in the game|None|getRooms()<br>curRoom.displayRoom()|Room|8
