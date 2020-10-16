# MCQuests #
A quest handling plugin. This plugin will be able to create quests given a file or create quest sets.
## Quest ##
A single quest is an instance of MCQuest that will require a type and completion values.
### Quest Types ###
* KILL - Any entity (besides player)
* CRAFT - Craft certain ItemStack
* PLAYER_KILL - Any player
* TRAVEL - Moved to a certain location
* TAME - How much animals you tamed
* BREED - How much animals you breed
* MINE - How many blocks you broke of certain types
* COLLECT - How many items you picked up (Can currently be exploited by dropping/picking up.
## Quest Set ##
A list of quests that will display the next quest only after completion of the previous quest. The first quest is always unlocked, by then completing that quest, the next quest in the set will open up. The inventory will dynamically show locked/completing/working on quests and if the QuestSet Quest amount is below 9, it will create empty slots to show case how many quests are actually in the set.

## TODO ##
* Work on creating more quest types
* Work on making the quests based on Generics to get rid of a lot of redudant code
* Work on turning the quests into JSON files. To better handle information (And because it looks like SHIT in the file directory)
