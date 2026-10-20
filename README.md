# Java Duplicate Finder

A command-line tool that finds files with identical contents using SHA-256.

## Requirements

Java 17 or newer.

## Compile

```bash
javac -d out src/DuplicateFinder.java
```

## Run

```bash
java -cp out DuplicateFinder /path/to/folder
```

Windows example:

```bash
java -cp out DuplicateFinder "C:\Users\YourName\Documents"
```

The program prints each duplicate file and the matching original file. It does not delete or modify files.