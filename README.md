# Dependencies
- Java 8
- Log file

# Build
```
cd <project-path>
./gradlew build
```
this will create a jar file *build/libs/fieldaware-1.0.jar*

# Execute
The first parameter must be the path of the log file that we want to analise:
```
java -jar build/libs/fieldaware-1.0.jar <path of the log file>
```

# Tests
Execute the unit tests using the command:
```
./gradlew test
```