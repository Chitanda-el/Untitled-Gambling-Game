# Untitled Gambling Game

Untitled Gambling Game is a roguelike gambling game in the spirit of Clover Pit and Balatro. It takes the form of a 3x3 slot machine where the player can purchase items to affect the outcome.

## Compilation/Running

Untitled Gambling Game requires JDK 25 and the latest version of [Maven](https://maven.apache.org/).

To compile the program, a PowerShell file has been provided. To run it, perform the following commands:
```powershell
Set-ExecutionPolicy -Scope CurrentUser Unrestricted
.\compile.ps1
```

If you wish to compile manually, you may perform the following command:

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

## Play

To start a game, press the `START/CONTINUE` button.

If you wish to delete the current save file, press the `ABANDON SAVE FILE` button.

If you wish to set a deterministic outcome for the random number generation, press the `INPUT CUSTOM SEED` button.

The default bet is $10. Once you are in the game, the `-5%` and `+5%` buttons change your bet by the stated amount.

The `ALL IN` button sets your bet to your current amount of money.

The `Set` button allows you to set your bet to a custom amount of at least $1. 

The `OPEN SHOP` button allows you to purchase items that unlock certain patterns in the slot machine, scaling in price with how many you have bought. At any point, you may re-roll the items available in the shop for $50.

You lose when you reach $0.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
