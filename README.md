# sourcecraft

an easy to use Minecraft to Source Engine Converter. It converts a given piece of Minecraft World into a map in .vmf-format that can be opened by Hammer Editor. It still needs to be compiled by Hammer Editor (/the Source-Engine compile tools). Aim of this project though is that no further steps or Hammer-Editor editing skills are needed. That is sourcecraft createas a (after compiling) a runnable and playable map with spawn-points and so on.

### what is needed
- Minecraft world of version 1.13 through 1.15 (tested with 1.15) either created in singleplayer or by a Minecraft server.
- Java Runtime Enviroment 1.8 or newer
- Recommended: Steam with installed target Source-Engine game and installed 'Source SDK'. The latter includes the Hammer Editor and the compile tools. You should have launched your target game and 'Source SDK' for your target game at least once. Then sourcecraft suggets to create the output-map at the correct path for the Hammer Editor. It can also copy the required textures to the correct path.

### textures
Sourcecraft creates the map with texture names equal to that used in minecraft. Suitable minecraft-like textures are not included here, but can be found online. If you put them into the "texture/<my-textures>" folder, sourcecraft makes sure that a copy is at the correct directory needed for the Hammer Editor (unlesss you wish otherwise).

### hammer editor
You can launch Hammer Editor directly from sourcecraft (alternatively you may launch it via Steam). Open the coverted .vmf-file via 'File->Open'. You may further edit your map here. By pressing F9 you open the compile dialog. For a first test, you should select for "Run VIS" the option "Fast". It is also better to check not to launch the game after compiling. Instead launch your target game manually with launch option "-console". Ingame you can open your map with the console command "map my-map-name".


### manual setup:
```
git clone git@github.com:Garten/sourcecraft.git
cd sourcecraft
mvn install
cd target
java -jar sourcecraft-x.y.jar
```
