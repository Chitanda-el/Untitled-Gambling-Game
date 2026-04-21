# Untitled Gambling Game

Untitled Gambling Game is a roguelike gambling game in the spirit of Clover Pit and Balatro. It takes the form of a 3x3 slot machine where the player can purchase items to affect the outcome.

## Compilation/Running

Untitled Gambling Game requires JDK 25 and the latest version of [Maven](https://maven.apache.org/).

To compile the program, a batch file has been provided, but if you wish to compile manually, you may perform the following command:

```bash
mvn clean package
```
And the .jar file will be in the `target` folder.

To run the game properly, the jar file needs to be one directory above the Assets directory. This can be accomplished in Windows with the following: 

```bash
cd target
move UGG-1.0-SNAPSHOT.jar ..
```

To run the game, perform the following command:

```bash
java -jar UGG-1.0-SNAPSHOT.JAR
```

And have fun!

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
