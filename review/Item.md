
## Class Item

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Item()|creates an item with default fields|None|Item(1, "", "", new Point(1, 1))|None|3
|public Item(int newId, String newName, String newType, Point newXyLocation)|creates item with specified fields|None|setId(newId)<br>setName(newName)<br>setType(newType)<br>setXyLocation(newXyLocation)|None|6
|public int getId()|gets the items id|id|None|None|3
|public void setId(int newId)|sets the items id|id|None|None|3
|public String getName()|gets the items name|name|None|None|3
|public void setName(String newName)|sets the items name|name|None|None|3
|public String getType()|gets the items type|type|None|None|3
|public void setType(String newType)|sets the items type|type|None|None|3
|public Character getDisplayCharacter()|gets the items character that represents it|displayChar|None|None|3
|public void setDisplayCharacter(Character newDisplayCharacter)|set the items character that represents it|displayChar|None|None|3
|public String getDescription()|gets the items description|description|None|None|3
|public void setDescription(String newDescription)|sets the items description|description|None|None|3
|public Point getXyLocation()|gets the items x,y location|xyLocation|None|None|3
|public void setXyLocation(Point newXyLocation)|sets the items x,y location|xyLocation|None|None|3
|public Room getCurrentRoom()|gets the items current room|currentRoom|None|Room|3
|public void setCurrentRoom(Room newCurrentRoom)|sets the items current room|currentRoom|None|Room|3
