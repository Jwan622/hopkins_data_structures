## Instructions

Runs on:
- Java 12
- IntelliJ IDEA


##Steps:
- first unzip the file: `unzip JeffreyWanModule8Lab2`
- to run the application, run these commands inside the `src` folder:

```
javac javac TowersOfHanoiRunner.java
```

and then
`java TowersOfHanoiRunner 17 towers_of_hanoi_output.txt timetable.txt`

where n = 17 and is the numberOfDisks. This will output the Hanoi steps starting from 1 disk up to n disks to the
 `towers_of_hanoi_output.txt` file and the `timetable.txt`  will store the runtimes of each algo in nanoseconds (1
  nanosecond - 1 billionth of a second) 

To generate javadocs, run this outside of the src folder:

`javadoc -d doc src/*.java` and view them using `open doc/index.html`