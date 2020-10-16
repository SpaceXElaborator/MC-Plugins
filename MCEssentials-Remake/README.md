# MCEssentials #
This is the largest of all the files. This plugin acts as the manager for everything. From loading databases, creating commands on the fly, registering listeners,
and creating quick access to inventory creation. 

## Usage ##
### Commands ###
#### CommandMeta ####
Acts as a quick interface to add additional information to your main commands.
* description - What the command will show when doing /help
* alias - Can use every listed alias instead of just the command name
* usage - Will show this if you type the command arguments wrong
#### CraftCommand ####
##### usage #####
~~~~
public TestCommand extends CraftCommand {
  public TestCommand() {
    super('test');
  }
}
~~~~
This will create the command '/test'
##### Adding functionality #####
~~~~
public void handleCommand(Player p, String[] args) { // For Players
  // Code here
} 
public void handleCommand(ConsoleCommandSender css, String[] args) { // For Console
  // Code here
}
public void handleCommand(BlockCommandSender bcs, String[] args) { // For Commandblocks
  // Code here
}
~~~~
These functions will take control of what will happen when the command is typed in chat
##### Adding sub commands #####
~~~~
public SecondTestCommand extends CraftCommand {
  public SecondTestCommand() {
    super('hello');
  }
}

public TestCommand() {
  super('test');
  addSubCommand(new SecondTestCommand());
}
~~~~
This will add SecondTestCommand as a subcommand. To access the second command -> '/test hello'
### Database ###
#### DatabaseManager ####
This file takes arguments to setup a connection to a MySQL database.
##### Getting Database #####
~~~~
DatabaseManager dbManager = new DatabaseManager("root", "", "127.0.0.1", "MeleeCraft");
MCDatabase coinsDB = dbManager.getDatabase("coins");
~~~~
##### Adding Database #####
~~~~
dbManager.registerDatabase(new MCDatabase())
~~~~
Database must be registered for them to work
#### MCDatabase ####
To create a new database it requires two arguments. The table name, and the table creation strings.
##### Example Databaes #####
~~~~
public MeleeCoinDB() {
  super("MeleeCoin", "`uuid` CHAR(36) NOT NULL:`coins` INT NOT NULL");
}
~~~~
This will create the MeleeCoin table and give it the string for the creation.
#### Functions within MCDatabase ####
##### updateSingle(String selector, String query, Object setter, Object matcher) #####
Will select * from the database where the query equals the matcher and then set the selector.
~~~~
updateSingle("coins", "uuid", 4, p.getUniqueId().toString());
~~~~
This will update "coins" to 4 where "uuid" = the players UUID string value
##### getSingle(String selector, String query, Object matcher) #####
Returns the selector as an Object where query = matcher
~~~~
Object j = getSingle("coins", "uuid", p.getUniqueId().toString());
~~~~
Make sure to do proper checking of the Object for TypeCasting
##### getAll(String query, Object matcher, Integer valuesInDB) #####
Will return a List<Object> where the query = matcher. Make sure to put in the full values in the DB.
~~~~
List<Object> objs = getAll("uuid", p.getUniqueId().toString(), 4);
~~~~
##### insert(Object... objs) #####
Can be used to add objects to the MySQL database. (This is an INSERT function not and UPDATE)
~~~~
insert(p.getUniqueID().toString(), 0);
~~~~
Creates the UUID and gives the player 0 coins.
### Inventory ###
#### InventoryButton ####
Works as a clickable button inside of an inventory.
##### Creating a button #####
~~~~
ItemStack item = new ItemStack(Material.STICK);
InventoryButton ib = new InventoryButton(item) {
  @Override
  public void onPlayerClick(Player p, ClickAction a) {
    // Function of button here
    // Can use ClickAction to see if they left-clicked or right-clicked the item
  }
}
~~~~
This will create an inventory button with a Stick as the display item.
#### InventoryUI ####
  This class handles how an inventory will be displayed, controlled, and who can open/close it.
  ##### Creation #####
  ~~~~
  public class TestInv extends InventoryUI {
    public TestInv() {
      super(27, "This is a test inv");
    }
  }
  ~~~~
  This will create an inventory with title "This is a test inv" with 27 slots.
  ##### Adding buttons #####
  ~~~~
  addButton(InventoryButton); // Will add the button to the next available slot (or -999 if there is no slot for it)
  addButton(InventoryButton, 0); // Will add the button to the given slot (will replace if it has too)
  updateInventory(); // Always call at the end of the function to make sure the buttons were added
  ~~~~
  ##### Remove button #####
  ~~~~
  removeButton(InventoryButton); // Will find and remove the button
  clearSlot(0); // Will remove the button at slot 0
  updateInventory(); // Always call at the end of the function to make sure the buttons were updated/removed
  ~~~~
  ##### Open inventory for a player #####
  ~~~~
  TestInv inv = new TestInv();
  inv.open(Player);
  ~~~~
  ##### Close inventory for a player #####
  ~~~~
  inv.close(Player);
  ~~~~
  It is recommended to close like this instead of p.closeInventory(); As using the close() function will make sure to de-register any handlers and work on the garbage collection of the inventory
  #### PageableInventoryManager ####
  This is a class that will allow for the creation of multiple inventories strung together using next and back buttons to create a better menu. It will dynamically create the pages based on the amount of InventoryButtons given during its creation and size.
  ##### Usage #####
  ~~~~
  PageableInventoryManager pim = new PageableInventoryManager(18, "Super Awesome Pages", List<InventoryButton>);
  pim.open(Player)
  ~~~~
  #### Minigames ####
  **IN PROGRESS**
  The goal of this is to be able to create a way to dynamically create as many minigame instances as is needed for the minigame plugins using Generics. The problem currently stands with the world creation and arena handling. Currently, this can be used to create kits, a kit selection screen, and arena with an arena handler. More will be added to this when I am able to develop more.
  
