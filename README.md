# Space Commando

Kotlin implementation of an old BASIC game. To run the application, you can:

- Press the RUN button in the IDE in the [Main.kt](src/main/kotlin/com/github/ppartisan/spacecommando/Main.kt) file.
- Execute `./gradlew run` from the CLI.
- Build and run the JAR file, `./gradlew build && java -jar build/libs/SpaceCommando*.jar `

The last one gives the best results in my IDE, but they all work. 

## Distribution
- Run `./gradlew distZip`. 
- Share the `zip` archive generated under `./build/distributions/`.
- Unzip the archive, `cd` into the `bin` directory, and run either the script
or the BAT file.