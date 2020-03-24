public class TowersOfHanoiRecursive {
    private StringBuilder steps;

    TowersOfHanoiRecursive() {
        this.steps = new StringBuilder().append("");
    }

    public StringBuilder run(int numberOfDisks, char sourceName, char destName, char spareName) {
        StringBuilder recursiveSteps = build(numberOfDisks, sourceName, destName, spareName);
        // removes the last newline of the StringBuilder
        recursiveSteps.setLength(recursiveSteps.length() - 1);
        return recursiveSteps;
    }

    private StringBuilder build(int numberOfDisks, char sourceName, char destName, char spareName) {
        if (numberOfDisks == 1) {
            log(numberOfDisks, sourceName, destName);
            return steps;
        }

        // This line makes sense since we now have to move the disks from the source to the spare while using the destination rod as the spare.
        // The spare becomes the dest_rod.
        // The dest_rod becomes the spare_rod.
        // The source_rod is the same.
        build(numberOfDisks-1, sourceName, spareName, destName);

        // once we're done with the above, we move the remaining disk (the largest) from the source to the destination.
        log(numberOfDisks, sourceName, destName);

        // this last line makes sense since we now have to move the disks from the spare to the destination.
        // The spare becomes the source_rod.
        // The source_rod becomes the spare.
        // The destination rod is the same.
        build(numberOfDisks-1, spareName, destName, sourceName);

        return steps;
    }



    private void log(int numberOfDisks, char source_rod, char dest_rod) {
        String separator = "\n";
        steps.append("Move disk " + numberOfDisks + " from tower " +  source_rod + " to tower " + dest_rod).append(separator);
    }

}
