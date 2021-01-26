package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class WindowUI extends JFrame implements Serializable {

    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 800;
    private final int invWidth = 200;
    private final int vgap = 20;
    private final int containerGap = 5;
    private final int gridGap = 350;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private final int gridCols = 2;
    private final char startCol = 1;
    private final char roomRow = 1;
    private Container contentPane;
    private JLabel playerLabel;
    private JList inventoryList;
    private JLabel messageBox;
    private Rogue theGame;
    private RogueParser parser;
    private JButton itemSelect;

    /**
     * Default constructor.
     */
    public WindowUI() {
        super("my awesome game");
        contentPane = getContentPane();
        setWindowDefaults(contentPane);
        setUpMenuBar();
        setUpPanels();
        setJList();
        pack();
        start();
    }

    private void setWindowDefaults(Container pane) {
        setTitle("Rogue");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane.setLayout(new BorderLayout(containerGap, containerGap));
    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel(new FlowLayout());
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.CENTER);
    }

    private void setJList() {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JLabel inventoryTitle = new JLabel("Inventory:", SwingConstants.CENTER);
        inventoryTitle.setPreferredSize(new Dimension(invWidth, vgap));
        inventoryPanel.add(inventoryTitle, BorderLayout.NORTH);
        inventoryList = new JList();
        inventoryPanel.add(inventoryList, BorderLayout.CENTER);
        itemSelect = new JButton();
        itemSelect.addActionListener(ev -> selectItem());
        itemSelect.setVisible(false);
        inventoryPanel.add(itemSelect, BorderLayout.SOUTH);
        contentPane.add(inventoryPanel, BorderLayout.EAST);
    }

    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem changeNameLabel = new JMenuItem("Change Player Name");
        changeNameLabel.addActionListener(ev -> changeName());
        fileMenu.add(changeNameLabel);
        JMenuItem newJSONFile = new JMenuItem("Load new JSON File");
        newJSONFile.addActionListener(ev -> loadNewJSON());
        fileMenu.add(newJSONFile);
        JMenuItem save = new JMenuItem("Save Game");
        save.addActionListener(ev -> save());
        fileMenu.add(save);
        JMenuItem load = new JMenuItem("Load Game");
        load.addActionListener(ev -> loadFileChooser());
        fileMenu.add(load);
    }

    private void setUpPanels() {
        JPanel labelPanel = new JPanel(new GridLayout());
        labelPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setUpLabelPanel(labelPanel);
        setTerminal();
    }

    private void setUpLabelPanel(JPanel thePanel) {
        messageBox = new JLabel();
        thePanel.add(messageBox, BorderLayout.WEST);
        playerLabel = new JLabel("", SwingConstants.RIGHT);
        thePanel.add(playerLabel, BorderLayout.EAST);
        contentPane.add(thePanel, BorderLayout.SOUTH);
    }

    private void setPlayerLabel(String name) {
        playerLabel.setText("Player Name: " + name);
    }

    private void saveAfterFileChoose(JFileChooser saveChooser) {
        try {
            String filename = saveChooser.getSelectedFile().getAbsolutePath().toString();
            if (filename.endsWith(".json")) {
                throw new Exception();
            }
            FileOutputStream outputStream = new FileOutputStream(filename);
            ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);
            outputDest.writeObject(getGame());
            outputDest.close();
            outputStream.close();
        } catch (Exception e) {
            int n = JOptionPane.showConfirmDialog(null, "Error Loading file. would you like to select another",
                null, JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                save();
            }
        }
    }

    /**
     * Saves the game state to a binary file.
     */
    public void save() {
        JFileChooser saveChooser = new JFileChooser(".");
        int result = saveChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            saveAfterFileChoose(saveChooser);
        }
    }

    private void selectItem() {
        String use = getGame().itemOptions(inventoryList.getSelectedIndex(), itemSelect.getText());
        getGame().setNextDisplay();
        draw(use, getGame().getNextDisplay());
        updateInventory();
    }

    private void loadFileChooser() {
        JFileChooser loadChooser = new JFileChooser(".");
        int result = loadChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            load(loadChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Reads a binary file containing information on the game state and loads the state.
     * @param filename A string representing the binary file to be opened
     */
    public void load(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)); ) {
            setGame((Rogue) in.readObject());
            draw("", getGame().getNextDisplay());
            setPlayerLabel(getGame().getPlayer().getName());
            updateInventory();
        } catch (Exception e) {
            int n = JOptionPane.showConfirmDialog(null, "Error Loading file. would you like to select another",
                null, JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                loadFileChooser();
            }
        }
    }

    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeName() {
        if (getGame() != null && getGame().getPlayer() != null) {
            String name = JOptionPane.showInputDialog("Please Enter a Player name:");
            if (name != null && !name.isEmpty()) {
                getGame().getPlayer().setName(name);
                setPlayerLabel(name);
            }
        }
    }

    private void loadNewJSON() {
        if (parser == null && getGame() == null) {
            try {
                parser = new RogueParser("fileLocations.json");
                setGame(new Rogue(parser));
                setPlayerLabel(getGame().getPlayer().getName());
                updateInventory();
            } catch (Exception e) {
                int n = JOptionPane.showConfirmDialog(null, "Error Loading file. would you like to select another",
                    null, JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    fileChooserNewJSON();
                }
            }
        } else {
            fileChooserNewJSON();
        }
    }

    private void fileChooserNewJSON() {
        JFileChooser loadChooser = new JFileChooser(".");
        int result = loadChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                parser = new RogueParser("fileLocations.json", loadChooser.getSelectedFile().getAbsolutePath());
                setGame(new Rogue(parser));
                setPlayerLabel(getGame().getPlayer().getName());
                draw("Welcome to my Rogue game", getGame().getNextDisplay());
                updateInventory();
            } catch (Exception e) {
                int n = JOptionPane.showConfirmDialog(null, "Error Loading file. would you like to select another",
                    null, JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    fileChooserNewJSON();
                }
            }
        }
    }

    /**
     * Updates the rogue game and GUI based on user input.
     * @param userInput A character representing the character inputted by the user
     * @throws InvalidMoveException Exception representing when invalid input is given
     */
    public void updateGame(char userInput) throws InvalidMoveException {
        if (userInput == 'e') {
            sendMessage("Please select an item to eat from your inventory with your mouse");
            itemSelect.setText("Eat Item");
            itemSelect.setVisible(true);
        } else if (userInput == 'w') {
            sendMessage("Please select an item to wear from your inventory with your mouse");
            itemSelect.setText("Wear Item");
            itemSelect.setVisible(true);
        } else if (userInput == 't') {
            sendMessage("Please select an item to toss from your inventory with your mouse");
            itemSelect.setText("Toss Item");
            itemSelect.setVisible(true);
        } else {
            itemSelect.setVisible(false);
            draw(getGame().makeMove(userInput), getGame().getNextDisplay());
        }
    }

    /**
     * Updates the inventory GUI with the game's inventory list.
     */
    public void updateInventory() {
        if (getGame() != null) {
            inventoryList.setListData(getGame().getInventoryString());
        }
    }

    /**
     * Prints a string to the screen starting at the indicated column and row.
     * @param toDisplay the string to be printed.
     * @param column the column in which to start the display.
     * @param row the row in which to start the display.
     */
    public void putString(String toDisplay, int column, int row) {
        Terminal t = screen.getTerminal();
        try {
            t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                if (ch == '\n') {
                    t.setCursorPosition(column, ++row);
                    continue;
                }
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Error printing to screen...");
        }
    }

    /**
     * Sets the message to be displayed at the top of the screen for the user.
     * @param msg the message to be displayed
     **/
    public void setMessage(String msg) {
        messageBox.setText(msg);
    }

    /**
     * Changes the message at the top of the screen for the user.
     * @param msg the message to be displayed
     **/
    public void sendMessage(String msg) {
        try {
            setMessage(msg);
            screen.refresh();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Error printing to screen...");
        }
    }

    /**
     * Redraws the whole screen including the room and the message.
     * @param message the message to be displayed at the top of the room
     * @param room the room map to be drawn
     **/
    public void draw(String message, String room) {
        try {
            screen.getTerminal().clearScreen();
            setMessage(message);
            putString(room, startCol, roomRow);
            screen.refresh();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Error printing to screen...");
        }
    }

    /**
     * Obtains input from the user and returns it as a char.
     * Converts arrow keys to the equivalent movement keys in rogue.
     * @return the ascii value of the key pressed by the user.
     */
    public char getInput() {
        KeyStroke keyStroke = null;
        try {
            while (keyStroke == null) {
                keyStroke = screen.pollInput();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Error reading input...");
        }
        if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            return Rogue.DOWN;
        } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            return Rogue.UP;
        } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            return Rogue.LEFT;
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            return Rogue.RIGHT;
        }
        return keyStroke.getCharacter();
    }

    /**
     * Gets the rogue (game master) object and returns it.
     * @return Rogue object representing the game manager
     */
    public Rogue getGame() {
        return theGame;
    }

    /**
     * Sets the rogue (game master) object.
     * @param newGame Rogue object representing the game manager
     */
    public void setGame(Rogue newGame) {
        theGame = newGame;
    }

    /**
     * The controller method for making the game logic work.
     * @param args command line parameters
     */
    public static void main(String[] args) {
        WindowUI theGameUI = new WindowUI();
        theGameUI.loadNewJSON();
        char userInput = 'w';
        String message = "Welcome to my Rogue game";
        theGameUI.draw(message, theGameUI.getGame().getNextDisplay());
        theGameUI.setVisible(true);
        while ((userInput = theGameUI.getInput()) != 'q') {
            try {
                theGameUI.updateGame(userInput);
                theGameUI.updateInventory();
            } catch (InvalidMoveException badMove) {
                message = "I didn't understand what you meant, please enter a command";
                theGameUI.sendMessage(message);
            }
        }
        theGameUI.sendMessage("Game exiting, please close the window");
    }
}
