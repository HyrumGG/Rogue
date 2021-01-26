
## Class Room

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Room()|Initialize member variables with defaults values|None|Room(0, 0, 0)|None|3
|public Room(int newWidth, int newHeight, int newId)|Initialize member variables with given fields for object|None|setWidth(newWidth)<br>setHeight(newHeight)<br>setId(newId)|None|5
|public boolean addItem(Item toAdd) |Adds a given item object to rooms item list|roomItems|setCurrentRoom(this)<br>validItem(toAdd)<br>Rogue.isItemPlayable(toAdd.getId())|Item, Rogue|13
|public int getWidth() |Gets the width of the room|width|None|None|3
|public void setWidth(int newWidth) |Sets the width of the room|width|None|None|3
|public int getHeight() |Gets the height of the room|height|None|None|3
|public void setHeight(int newHeight) |Sets the height of the room|height|None|None|3
|public int getId() |Gets the id tag of the room|id|None|None|3
|public void setId(int newId) |Sets the id tag of the room|id|None|None|3
|public ArrayList<Item> getRoomItems() |Returns the list of items inside the room|roomItems|None|Item list returned|3
|public void setRoomItems(ArrayList<Item> newRoomItems) |Sets the list of items inside the room|roomItems|None|Item list set|3
|public Player getPlayer() |Gets the player of the game in the room object|thePlayer|None|Player returned|3
|public void setPlayer(Player newPlayer) |Sets the player of the game in the room object|thePlayer|None|Player set|3
|public int getDoor(String direction) |gets a door in the room's position on the given wall direction|doors|None|None|6
|public void setDoor(String direction, int location) |Sets a door in the room's position at the given wall direction|doors|None|None|3
|public boolean isPlayerInRoom() |find out whether the player of the game is in the current room object|None|getPlayer()<br>getPlayer().getCurrentRoom()|Player, Room|8
|public boolean verifyRoom() |makes sure the room is complete and valid by checking for conflicts of objects in the room|roomItems|validItem(curItem)<br>validPlayer()<br>getDoor()|Item|16
|public boolean isWall(Point location) |figures out if the location given corresponds to a wall|None|getHeight()<br>getWidth()<br>isDoor(x, y)|None|8
|public void removeItem(Item toRemove) |Removes a given item object from the rooms item list|None|getRoomItems()|Item|6
|public Item fetchItem(Point location)|Gets an item from the rooms list at a given location|None|getRoomItems()<br>myItem.getXyLocation()|Item|13
|private boolean validItem(Item myItem) |checks if given item's location does not conflict with other objects in the room|None|getPlayer().getXyLocation()<br>isPlayer(x, y)<br>isDoor(y, x)<br>displayWall(y, x)<br>inBounds(x, y)<br>isItem(x, y)|Player, Item|8
|private boolean validPlayer() |checks if the players location does not conflict with other objects in the room|None|isPlayerInRoom()<br>isDoor(y, x)<br>displayWall(y, x)<br>displayItem(x, y)<br>inBounds(x, y)<br>getPlayer().getXyLocation()|Player|8
|private boolean isItem(int x, int y, Item check)|Finds out if the given x,y coordinates correspond to an item in the room list|None|getRoomItems()<br>myItem.getXyLocation()|Item|14
|private boolean inBounds(int x, int y)|Finds out whether the given x,y coordinates are in the boundaries of the room object|None|getWidth()<br>getHeight()|None|6
|private boolean isPlayer(int x, int y)|Finds out whether the given x,y coordinates map to the player in the room|None|isPlayerInRoom()<br>getPlayer.getXyLocation()|Player|9
|private boolean isDoor(int row, int col)|Checks if the given x,y coordinates correspond to one of the room's doors|None|getDoor("N")<br>getHeight()<br>getWidth()|None|12
|private Character displayWall(int row, int col)|Finds out whether the given coordinates are on the room's wall and returns the symbol of the wall if true|None|Rogue.symbolsExists()<br>getHeight()<br>getWidth()<br>Rogue.getSymbol("NS_WALL")|Rogue|10
|private Character displayItem(int x, int y)|Finds out whether the given coordinates map to an item and returns the items character|None|getRoomItems()<br>myItem.getXyLocation()<br>myItem.getDisplayCharacter()|Item|13
|public string displayRoom()|produces a string of ascii characters representing the room and its objects located within it|None|Rogue.symbolsExists()<br>getHeight()<br>displayLine(row)|Rogue|13
|private String displayLine(int row)|produces a string of ascii characters representing the rooms given row/y coordinate containing the rooms objects|None|getWidth()<br>displayChar(row, col)|None|7
|private Character displayChar(int row, int col)|produces a ascii character representing the object corresponding to the given row and column within the room|None|isPlayer(col, row)<br>getPlayer().getDisplayCharacter()<br>isDoor(row, col)<br>Rogue.getSymbol("DOOR")<br>displayItem(col, row)<br>displayWall(row, col)|Rogue, Player|13
