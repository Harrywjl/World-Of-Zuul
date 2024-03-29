/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room startingRoom, itemRoom1, itemRoom2, monsterRoom1, monsterRoom2, monsterRoom3,
             monsterRoom4, monsterRoom5, fountain, trapRoom1, trapRoom2, bossRoom1,
             floor2;
      
        // create the rooms
        startingRoom = new Room("in the starting room");
        itemRoom1 = new Room("in an item room");
        monsterRoom1 = new Room("in a monster room");
        monsterRoom2 = new Room("in a monster room");
        trapRoom1 = new Room("in a trap!");
        fountain = new Room("in the fountain room\nYour health has been restored");
        monsterRoom3 = new Room("in a monster room");
        itemRoom2 = new Room("in an item room");
        monsterRoom4 = new Room("in a monster room");
        monsterRoom5 = new Room("in a monster room"); 
        trapRoom2 = new Room("in a trap!");
        bossRoom1 = new Room("in the boss room");
        floor2 = new Room("out of the dungeon! Congratulations!");
        
        // initialise room exits (north, east, south, west)
        startingRoom.setExits(null, monsterRoom2, monsterRoom1, null);
        monsterRoom1.setExits(startingRoom, null, trapRoom1, null);
        trapRoom1.setExits(monsterRoom1, null, monsterRoom3, null);

        monsterRoom3.setExits(trapRoom1, itemRoom1, null, null);
        itemRoom1.setExits(null, monsterRoom3, null, trapRoom2);
        monsterRoom2.setExits(null, itemRoom2, null, startingRoom);
        itemRoom2.setExits(null, null, fountain, monsterRoom2);
        fountain.setExits(itemRoom2, null, monsterRoom4, null);
        monsterRoom4.setExits(fountain, null, trapRoom2, null);
        trapRoom2.setExits(monsterRoom4, monsterRoom5, null, itemRoom1);
        monsterRoom5.setExits(null, bossRoom1, null, trapRoom2);
        bossRoom1.setExits(null, null, null, monsterRoom5);
        bossRoom1.setExit("upstairs", floor2);

        floor2.setExit("downstairs", bossRoom1);
        
        currentRoom = startingRoom;  // start game outside
    }

    public Player p1 = new Player();

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printLocationInfo(Player player) {
        System.out.println("You are " + currentRoom.getDescription());
        if (currentRoom.getDescription().equals("in an item room")) {
            p1.newWeapon();
        }
        if (currentRoom.getDescription().equals("out of the dungeon! Congratulations!")) {
            System.out.println("Thank you for playing.  Good bye.");
        } else {
            System.out.print("You can go: ");
            System.out.println(currentRoom.getExitString());
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo(p1);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo(p1);
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}