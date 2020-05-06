## Instructions

Runs on:
- Java 12
- IntelliJ IDEA


##Steps:
- first unzip the file: `unzip JeffreyWanModule13Lab4`
- to run the application, run these commands inside the `src` folder:

```
javac SortComparison.java
```

and then run:
`java SortComparison inputData outputData`

This command will generate the input and output folders (called `inputData` and `outputData` respectively), the unsorted 
input files, and the sorted output files. It will also generate a file called `timeTables.txt` which will have the time 
of each run for each sort for each input file.

To generate javadocs, run this outside of the src folder:

`javadoc -d doc src/*.java` and view them using `open doc/index.html`