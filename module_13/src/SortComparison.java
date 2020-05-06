import java.io.*;
import java.util.Random;

/**
 * This class is the runner class that calls quicksort and heapsort on the input data
 */
public class SortComparison {
    private static String inputFolderName;
    private static String outputFolderName;

    /**
     * main runner method. It reads in the unsorted input files, sorts them through the 4 Quicksort variants
     * and 1 heapsort algorithm, writs the sorted data to output files with their respective names, and outputs a timetable
     * of runtimes with their respective sorts.
     * @param args no args in this method
     * @throws IOException if there is an error reading/writing to files
     * @throws Quicksort.InvalidPivot thrown if an invalid pivot choice is provided to Quicksort
     * @throws Quicksort.InvalidPartitionStoppingChoice thrown if an invalid stopping choice is provided to Quicksort
     */
    public static void main(String args[]) throws IOException, Quicksort.InvalidPivot, Quicksort.InvalidPartitionStoppingChoice {
        checkArgs(args);
        inputFolderName = args[0] + "/";
        outputFolderName = args[1] + "/";

        // this generates the input and output folders
        generateDirectory(args[0]);
        generateDirectory(args[1]);
        // this generates the input files that need to be sorted
        generateInputFilesSinceTheLabInputFilesAreWrong();

        // this gets the input folder and files of the raw unsorted input files that we just created
        File folder = new File(inputFolderName);
        File[] listOfDataFiles = folder.listFiles();
        // this sorts the input files so that the output files and time tables are created in order
        fileSort(listOfDataFiles);

        File timeTableOutputFile = new File(outputFolderName + "timeTables.txt");
        PrintWriter timeTableOutputWriter = new PrintWriter(new FileOutputStream(timeTableOutputFile));

        for (File file : listOfDataFiles) {
            if (file.isFile()) {
                String filePath = file.getPath();
                String fileNameOnly = file.getName();
                output("Reading in data from: " + fileNameOnly);
                FileInputStream fileInputStreamData = new FileInputStream(filePath);
                DataInputStream input = new DataInputStream(fileInputStreamData);

                // get the length of the file inferred by the name. Yes, this is dependent on the name of the file having the correct length
                // need to do this first before reading in the file because we need to create an array of a specific size.
                int fileLength = java.lang.Integer.parseInt(fileNameOnly.replaceAll("\\D+",""));
                int[] dataInput = new int[fileLength];

//              read in the raw input data and store it in an array
                for (int index = 0; index < fileLength; index++) {
                    dataInput[index] = input.readInt();
                }

                // this is quick sort using the first element as the pivot and runs insertion sort when the partition is size 1 or 2
                Quicksort firstStandardQuickSorter = new Quicksort("first", "standard");
                Pair<Integer, int[]> firstStandardQuickSorterPair = runAndGetAverageTime(firstStandardQuickSorter, dataInput);
                String firstStandardQuickSortDelimiter = "===========QuickSort Using First Item Pivot and Smallest Partition Size=============";

                // this is quick sort using the first element as the pivot and runs insertion sort when the partition is size 100
                Quicksort firstLargeQuickSorter = new Quicksort("first", "large");
                Pair<Integer, int[]> firstLargeQuickSorterPair = runAndGetAverageTime(firstLargeQuickSorter, dataInput);
                String firstLargeQuickSortDelimiter = "===========QuickSort Using First Item and 100 Partition Size=============";

                // this is quick sort using the first element as the pivot and runs insertion sort when the partition is size 50
                Quicksort firstMediumQuickSorter = new Quicksort("first", "medium");
                Pair<Integer, int[]> firstMediumQuickSorterPair = runAndGetAverageTime(firstMediumQuickSorter, dataInput);
                String firstMediumQuickSortDelimiter = "===========QuickSort Using First Item and 50 Partition Size=============";

                // this is quick sort using the median element as the pivot and runs insertion sort when the partition is size 1 or 2
                Quicksort medianStandardQuickSorter = new Quicksort("median", "standard");
                Pair<Integer, int[]> medianStandardQuickSorterPair = runAndGetAverageTime(medianStandardQuickSorter, dataInput);
                String medianStandardQuickSortDelimiter = "===========QuickSort Using Median Item and Smallest Partition Size=============";

                // this is heap sort
                MinHeapsort heapSorter = new MinHeapsort(fileLength);
                Pair<Integer, int[]> heapSorterPair = runAndGetAverageTime(heapSorter, dataInput);
                String heapSortDelimiter = "===========HeapSort=============";

                output("Ran the " + fileNameOnly + " through the sorting algos. Now writing to output files...");

                // get rid of the .dat part of the file so that we can convert to a .txt file
                String outputFileName = fileNameOnly.split("\\.")[0];
                File outputFile = new File(outputFolderName + outputFileName + "_sorted.txt");
                PrintWriter outputWriter = new PrintWriter(new FileOutputStream(outputFile));
                // write the sorted data and runtimes to the output folder
                writeToTxtOutput(outputWriter, firstStandardQuickSorterPair.second, firstStandardQuickSortDelimiter, firstStandardQuickSorterPair.first);
                writeToTxtOutput(outputWriter, firstMediumQuickSorterPair.second, firstMediumQuickSortDelimiter, firstMediumQuickSorterPair.first);
                writeToTxtOutput(outputWriter, firstLargeQuickSorterPair.second, firstLargeQuickSortDelimiter, firstLargeQuickSorterPair.first);
                writeToTxtOutput(outputWriter, medianStandardQuickSorterPair.second, medianStandardQuickSortDelimiter, medianStandardQuickSorterPair.first);
                writeToTxtOutput(outputWriter, heapSorterPair.second, heapSortDelimiter, heapSorterPair.first);

                // write file times to the timeTables.txt file
                timeTableOutputWriter.println("===========TIME ELAPSED PER SORT FOR FILE: " + fileNameOnly + "=============");
                writeTimeElapsedToFile(
                        timeTableOutputWriter,
                        "Quicksort using first element as pivot, and partition size of 1 or 2",
                        firstStandardQuickSorterPair.first
                );
                writeTimeElapsedToFile(
                        timeTableOutputWriter,
                        "Quicksort using first element as pivot, and partition size of 50",
                        firstMediumQuickSorterPair.first
                );
                writeTimeElapsedToFile(
                        timeTableOutputWriter,
                        "Quicksort using first element as pivot, and partition size of 100",
                        firstLargeQuickSorterPair.first
                );
                writeTimeElapsedToFile(
                        timeTableOutputWriter,
                        "Quicksort using median element as pivot, and partition size of 1 or 2",
                        medianStandardQuickSorterPair.first
                );
                writeTimeElapsedToFile(
                        timeTableOutputWriter,
                        "Heapsort",
                        heapSorterPair.first
                );

                // close input and output files
                outputWriter.close();
                input.close();
            }
        }
        // close the time table file
        timeTableOutputWriter.close();
        // if all the output files aren't sorted, throw an error because we made a mistake.
        try {
            checkIfEverythingIsSorted();
        } catch (FileNotSorted e) {
            output(e.getMessage());
        }

    }

    /**
     * this is an overloaded method that writes the message to standard out.
     * @param message A message to write to standard out. Used mainly for debugging and runner info
     */
    private static void output(String message) {
        System.out.println(message);
    }

    /**
     * A utility function to print to output array of size n. Used mainly for testing.
     * @param arr the array to output
     */
    private static void printArray(int arr[]) {
        for (int integer : arr)
            System.out.println(integer);
    }

    /**
     * this is used to generate the input files
     * @throws IOException thrown if there is a problem reading/writing to files.
     */
    private static void generateInputFilesSinceTheLabInputFilesAreWrong() throws IOException, Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        int[] INPUT_SIZES = new int[] {50, 500, 1000, 2000, 5000};
        for (int size : INPUT_SIZES) {
            generateRandomFiles(size);
            generateOrderedFiles(size);
            generateReverseOrderedFiles(size);
        }
    }

    /**
     * this method is used to generate the ascending input files. It calls generateRandomData and then sorts that data
     * @param size the size of the ascending data to generate
     */
    private static void generateOrderedFiles(int size) throws IOException, Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        int[] sorted = sortAsc(generateRandomData(size));

        String fileName = "asc" + String.valueOf(size);
        writeToDataOutput(fileName + ".dat", inputFolderName, sorted);
    }

    /**
     * this method is used to generate the reversed input files. It calls generateRandomData and then sorts that data in reverse order
     * @param size the size of the reversed data to generate
     */
    private static void generateReverseOrderedFiles(int size) throws IOException, Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        int[] sorted = sortDesc(generateRandomData(size));

        String fileName = "reversed" + String.valueOf(size);
        writeToDataOutput(fileName + ".dat", inputFolderName, sorted);
    }

    /**
     * used to sort data to create the ascending input files
     * @param data the data to sort
     * @return the sorted data to write to the ascending input files
     */
    private static int[] sortAsc(int[] data) throws Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        // use my own quicksort in generating the files, lol
        Quicksort ob = new Quicksort( "first", "standard");
        return ob.sort(data);
    }

    /**
     * used to sort data to create the reversed input files
     * @param data the data to sort
     * @return the sorted data to write to the reversed input files
     */
    private static int[] sortDesc(int[] data) throws Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        Quicksort ob = new Quicksort( "first", "standard");
        int[] sorted = ob.sort(data);

        // reverse the data. works for even and odd amounts of data
        int last = sorted.length - 1;
        int tmp;
        for (int i = 0; i < last; i++) {
            tmp = sorted[i];
            sorted[i] = sorted[last];
            sorted[last] = tmp;
            last--;
        }

        return sorted;
    }

    private static void generateRandomFiles(int size) throws IOException {
        int[] data = generateRandomData(size);

        String fileName = "rand" + String.valueOf(size);
        writeToDataOutput(fileName + ".dat", inputFolderName, data);
    }

    /**
     * used to create the random input files. Also used to create the ascending and descending input files. No duplicates
     * are in the files because this method also generates a new number if the input data already has the randomly generated
     * number.
     * @param size the size of the data input to created
     * @return the sorted data to write to the random input files
     */
    private static int[] generateRandomData(int size) {
        Random randomGenerator  = new Random();
        int[] data = new int[size];
        int number;

        for(int counter = 0; counter <= size - 1; counter++) {
            // generates random numbers up to 20000
            number = randomGenerator.nextInt(20000);

            // this ensures that each number generated is not a duplicate. If a duplicate, generate again.
            while (contains(data, number)) {
                number = randomGenerator.nextInt(20000);
            }
            data[counter] = number;
        }

        return data;
    }

    private static void generateDirectory(String folderName) {
        File file = new File(folderName);
        file.mkdir();
    }

    private static void writeToDataOutput(String fileName, String outputFolderPath, int[] data) throws IOException {
        File file = new File(outputFolderPath + fileName);
        DataOutputStream output = new DataOutputStream(new FileOutputStream(file));

        for (int datum : data) {
            output.writeInt(datum);
        }

        output.close();
    }

    private static void writeToTxtOutput(PrintWriter output, int[] data, String delimiter, long timeElapsed) {
        output.println(delimiter);
        for (int datum : data) {
            output.println(datum);
        }
        output.println("TIME ELAPSED IN NANOSECONDS: " + timeElapsed);
        output.println();
    }

    /**
     * used to write the time elapsed to the timeTable file
     * @param timeTableOutputWriter printWriter for writing to file
     * @param description a description for the output
     * @param elapsedTime the elapsed time to write to file
     */
    private static void writeTimeElapsedToFile(
            PrintWriter timeTableOutputWriter,
            String description,
            long elapsedTime
    ) {
        timeTableOutputWriter.println(description + ", TIME TO SORT IN NANOSECONDS: " + elapsedTime);
        timeTableOutputWriter.println();
    }

    private static boolean contains(int[] arr, int newNumber) {
        for (int datum : arr) {
            if (datum == newNumber) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method acts as a test that reads in the output files and ensure that the numbers are all sorted. This iterates
     * through all of the files and checks that they are sorted.
     */
    private static void checkIfEverythingIsSorted() throws IOException, FileNotSorted {
        output("Checking output files are all sorted...");
        File dir = new File(outputFolderName);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                String filePath = dataFile.getPath();
                String fileName = filePath.split("/")[1];
                if (fileName.equals("timeTables.txt")) {
                    continue;
                }
                String line;
                int fileLength = java.lang.Integer.parseInt(fileName.replaceAll("\\D+",""));
                int[] data = new int[fileLength];
                BufferedReader dataReader = new BufferedReader(new FileReader(filePath));
                int i = 0;
                while ((line = dataReader.readLine()) != null) {
                    try {
                        data[i] = java.lang.Integer.parseInt(line);
                        i++;
                    } catch (NumberFormatException e) {
                        // clear out the data array when you hit the delimiter for the next sort which is heapSort
                        for (int j = 0; j < fileLength; j++) {
                            data[j] = 0;
                        }
                        // reset the index so you can start writing from the beginning of the data array.
                        i = 0;
                    }
                }
                if (!isSorted(data) && !isSortedReversed(data)) {
                    throw new FileNotSorted("You made a mistake somewhere you idiot: " + fileName + " is not sorted");
                }
                output("This file was sorted: " + fileName);
                dataReader.close();
            }
        }
    }

    /**
     * used during the test of the output files. If a file is not sorted, an error is thrown.
     * @param data the data from the output file
     * @return a boolean that says if the output file is sorted or not
     */
    private static boolean isSorted(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] > data[i + 1])
                return false;
        }
        return true;
    }

    private static boolean isSortedReversed(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] < data[i + 1])
                return false;
        }
        return true;
    }


    /**
     * This method returns a pair of values, the average run time of the algorithm, and the sorted data
     * @param sorter takes in a generic sorter -- could be any of the 4 quick sorts or the heapsort. It can take in anything
     *               that implements Sorter, the interface
     * @param dataInput the unsorted data
     * @return a Pair that consists of the average runtime and the sorted data.
     */
    private static Pair<Integer, int[]> runAndGetAverageTime(Sorter sorter, int[] dataInput) {
        long totalTime = 0;
        int totalRuns = 4;

        for (int i = 0; i <= totalRuns; i++) {
            long startTime = System.nanoTime();
            sorter.sort(dataInput);
            long endTime = System.nanoTime();
            long runTime = endTime - startTime;

            totalTime += runTime;
        }
        int[] sorted = sorter.sort(dataInput);
        long avgTime = totalTime / totalRuns-1;

        return new Pair(avgTime, sorted);
    }

    /**
     * used to sort the input files so that ultimately the timeTable information isn't written in completely random order.
     * @param files sorted files, somewhat sorted.
     */
    private static void fileSort(File[] files) {
        File temp;
        for (int i = 0; i < files.length; i++) {
            for (int j = i + 1; j < files.length; j++) {
                if (files[i].getName().compareTo(files[j].getName()) > 0) {
                    temp = files[i];
                    files[i] = files[j];
                    files[j] = temp;
                }
            }
        }
    }

    private static void checkArgs(String[] args) {
        if(args.length != 2) {
            System.out.println("Proper Usage is: java SortComparison inputData outputData");
            System.exit(0);
        }
    }

    /**
     * thrown during the test of the output files if the output file isn't sorted.
     */
    public static class FileNotSorted extends Exception {
        public FileNotSorted(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * a generic class that can return an integer and an array. Will be used to return an average runtime and a sorted array.
     * @param <Integer> the average runtime
     * @param <Array> sorted array
     */
    public static class Pair<Integer, Array> {
        /**
         * The first element of this <code>Pair</code>
         */
        private long first;

        /**
         * The second element of this <code>Pair</code>
         */
        private Array second;

        /**
         * Constructs a new <code>Pair</code> with the given values.
         *
         * @param first  the first element
         * @param second the second element
         */
        public Pair(long first, Array second) {
            this.first = first;
            this.second = second;
        }
    }
}
