package rogue;

import java.util.ArrayList;
import java.io.Serializable;

public class Door implements Serializable {
    private ArrayList<Room> connectedRooms;
    private String dir = "";
    private int position;
    private Room doorRoom;

    /**
     * Default constructor.
     */
    public Door() {
        connectedRooms = new ArrayList<Room>();
    }

    /**
     * Gets the direction of the door and returns it.
     * @return A string representing the direction (NESW) of the door object.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Sets the direction of the door and returns it.
     * @param newDir A string representing the direction (NESW) of the door object.
     */
    public void setDir(String newDir) {
        dir = newDir;
    }

    /**
     * Gets the position on the wall the door is located.
     * @return An integer representing the position on the wall the door is located.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position on the wall the door is located.
     * @param newPosition An integer representing the position on the wall the door is located.
     */
    public void setPosition(int newPosition) {
        position = newPosition;
    }

    /**
     * Sets the room that the door is located in.
     * @param newDoorRoom A Room object representing the room the door is in
     */
    public void setDoorRoom(Room newDoorRoom) {
        doorRoom = newDoorRoom;
    }

    /**
     * Gets the room that the door is located in.
     * @return A Room object representing the room the door is in
     */
    public Room getDoorRoom() {
        return doorRoom;
    }

    /**
     * Adds a room to be attached/connected to the door.
     * @param r Room object to be attached/connected to the door.
     */
    public void connectRoom(Room r) {
        if (connectedRooms.size() != 2) {
            connectedRooms.add(r);
        }
    }

    /**
     * Gets an ArrayList containing the two rooms connected by the door.
     * @return ArrayList representing the rooms attached/connected to the door.
     */
    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    /**
     * Gets the other connected room to the door, by sending one of the rooms connected to the door.
     * @param currentRoom Room object representing one of the rooms connected to the door.
     * @return Room object representing the room opposite to the room object given.
     */
    public Room getOtherRoom(Room currentRoom) {
        if (connectedRooms.get(0) == currentRoom) {
           return connectedRooms.get(1);
        } else if (connectedRooms.get(1) == currentRoom) {
            return connectedRooms.get(0);
        } else {
            return null;
        }
    }

    /**
     * Calculates whether a room is in the list of connected rooms for the door.
     * @param roomID Integer representing the id of the room to find.
     * @return boolean representing whether the room id given is connected to the door.
     */
    public boolean containsRoom(int roomID) {
        for (Room curRoom : connectedRooms) {
            if (curRoom.getId() == roomID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates whether a room is in the list of connected rooms for the door.
     * @param myRoom Room object the room to find in the connections of the door.
     * @return boolean representing whether the room id given is connected to the door.
     */
    public boolean containsRoom(Room myRoom) {
        for (Room curRoom : connectedRooms) {
            if (curRoom == myRoom) {
                return true;
            }
        }
        return false;
    }
}
