# cavazos-commander

General Cavazos Commander App — CIS-055 Data Structures assignment. Issues random military commands (from `commands.json`) with undo/redo built on two stacks (LIFO).

Commands: `i` issue random, `l` list all 26, `u` undo, `r` redo, `q` quit.

## Run

```
mvn compile org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=com.joelperez.cavazos.App
```

Or open the folder in VS Code and Run `src/main/java/com/joelperez/cavazos/App.java`. Run from the project root so `src/main/resources/commands.json` resolves.

## Test

```
mvn test
```

## Requirements

- JDK 25 (JAVA_HOME set), Apache Maven 3.9+ (pulls json-simple 1.1.1)
