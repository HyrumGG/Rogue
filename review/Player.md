
## Class Player

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Player()|creates a player object with default fields|None|Player("Bilbo", new Point(1, 1))|None|3
|public Player(String newName)|creates a player object with specified name and default location|None|Player(newName, new Point(1, 1))|None|3
|public Player(String newName, Point newXyLocation)|creates player object with specified name and location|None|setName(newName)<br>setXyLocation(newXyLocation)|None|5
|public String getName()|gets the players name|name|None|None|3
|public void setName(String newName)|sets the players name|name|None|None|3
|public Point getXyLocation()|gets the players location|xyLocation|None|None|3
|public void setXyLocation(Point newXyLocation)|sets the players location|xyLocation|None|None|3
|public Room getCurrentRoom()|gets the players current room|curRoom|None|Room|3
|public void setCurrentRoom(Room newRoom)|sets the players current room|curRoom|None|Room|3
|public Character getDisplayCharacter(Character newDisplayCharacter)|gets the players character that represents it|displayChar|None|None|3
|public void setDisplayCharacter(Character newDisplayCharacter)|sets the players character that represents it|displayChar|None|None|3
