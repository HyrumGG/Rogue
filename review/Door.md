
## Class Door

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Door()|initializes member variables|connectedRooms|None|None|3
|public String getDir()|gets the direction of the door|dir|None|None|3
|public void setDir(Strin newDir)|sets the direction of the door|dir|None|None|3
|public int getPosition()|gets the position on the wall the door is located|position|None|None|3
|public void setPosition()|sets the position on the wall the door is located|position|None|None|3
|public void setDoorRoom(Room newDoorRoom)|sets the room the door is located in|doorRoom|None|Room|3
|public Room getDoorRoom()|gets the room the door is located in|doorRoom|None|Room|3
|public void connectRoom(Room r)|adds a room to be attached to the door|connectedRooms|None|Room|5
|public ArrayList<Room> getConnectedRooms()|gets the arraylist representing the rooms connected by the door|connectedRooms|None|Room|3
|public Room getOtherRoom(Room currentRoom)|gets the other connected room to the door|connectedRooms|None|Room|9
|public boolean containsRoom(int roomID)|finds out whether a room is one of the connected rooms|connectedRooms|curRoom.getId()|Room|8
|public boolean containsRoom(Room myRoom)|finds out whether a room is one of the connected rooms|connectedRooms|None|Rooms|8
