import java.io.*;
import java.util.Random;

/**
 * This class is the runner class for reading in the unsorted input files, sorting them through the 4 Quicksort variants
 * and 1 heapsort algorithm, writing the sorted data to output files with their respective names, and outputting a timetable
 * of runtimes with their respective sorts.
 */
public class SortComparison {
    private static String inputFolderPath = "inputData/";
    private static String outputFolderName = "outputData/";

    public static void main(String args[]) throws IOException, Quicksort.InvalidPivot, Quicksort.InvalidPartitionStoppingChoice {
        // this generates the input files that need to be sorted
        generateDirectory("inputData");
        generateInputFilesSinceTheLabInputFilesAreWrong();

        // this gets the input folder and files of the raw unsorted input files that we just created
        File folder = new File(inputFolderPath);
        File[] listOfDataFiles = folder.listFiles();
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
                int fileLength = Integer.parseInt(fileNameOnly.replaceAll("\\D+",""));
                int[] dataInput = new int[fileLength];

//              read in the raw input data and store it in an array
                for (int index = 0; index < fileLength; index++) {
                    dataInput[index] = input.readInt();
                }

                Quicksort firstStandardQuickSorter = new Quicksort("first", "standard");
                long firstStandardStartTimeQuickSort = System.nanoTime();
                int[] firstStandardQuickSorted = firstStandardQuickSorter.sort(dataInput,0, fileLength - 1);
                long firstStandardEndTimeQuickSort = System.nanoTime();
                long firstStandardTimeElapsedQuickSort = firstStandardEndTimeQuickSort - firstStandardStartTimeQuickSort;
                String firstStandardQuickSortDelimiter = "===========QuickSort Using First Item Pivot and Smallest Partition Size=============";

                Quicksort firstLargeQuickSorter = new Quicksort("first", "large");
                long firstLargeStartTimeQuickSort = System.nanoTime();
                int[] firstLargeQuickSorted = firstLargeQuickSorter.sort(dataInput,0, fileLength - 1);
                long firstLargeEndTimeQuickSort = System.nanoTime();
                long firstLargeTimeElapsedQuickSort = firstLargeEndTimeQuickSort - firstLargeStartTimeQuickSort;
                String firstLargeQuickSortDelimiter = "===========QuickSort Using First Item and 100 Partition Size=============";

                Quicksort firstMediumQuickSorter = new Quicksort("first", "medium");
                long firstMediumStartTimeQuickSort = System.nanoTime();
                int[] firstMediumQuickSorted = firstMediumQuickSorter.sort(dataInput,0, fileLength - 1);
                long firstMediumEndTimeQuickSort = System.nanoTime();
                long firstMediumTimeElapsedQuickSort = firstMediumEndTimeQuickSort - firstMediumStartTimeQuickSort;
                String firstMediumQuickSortDelimiter = "===========QuickSort Using First Item and 50 Partition Size=============";

                Quicksort medianStandardQuickSorter = new Quicksort("median", "standard");
                long medianStandardStartTimeQuickSort = System.nanoTime();
                int[] medianStandardQuickSorted = medianStandardQuickSorter.sort(dataInput,0, fileLength - 1);
                long medianStandardEndTimeQuickSort = System.nanoTime();
                long medianStandardTimeElapsedQuickSort = medianStandardEndTimeQuickSort - medianStandardStartTimeQuickSort;
                String medianStandardQuickSortDelimiter = "===========QuickSort Using Median Item and Smallest Partition Size=============";

                MinHeapsort heapSorter = new MinHeapsort(fileLength);
                long startTimeHeapSort = System.nanoTime();
                int[] heapSorted = heapSorter.sort(dataInput);
                long endTimeHeapSort = System.nanoTime();
                long timeElapsedHeapSort = endTimeHeapSort - startTimeHeapSort;
                String heapSortDelimiter = "===========HeapSort=============";

                output("Ran the " + fileNameOnly + " through the sorting algos. Now writing to output files...");

                // get rid of the .dat part of the file so that we can convert to a .txt file
                String outputFileName = fileNameOnly.split("\\.")[0];
                generateDirectory("outputData");
                File outputFile = new File(outputFolderName + outputFileName + "_sorted.txt");
                PrintWriter outputWriter = new PrintWriter(new FileOutputStream(outputFile));
                writeToTxtOutput(outputWriter, firstStandardQuickSorted, firstStandardQuickSortDelimiter, firstStandardTimeElapsedQuickSort);
                writeToTxtOutput(outputWriter, firstLargeQuickSorted, firstLargeQuickSortDelimiter, firstLargeTimeElapsedQuickSort);
                writeToTxtOutput(outputWriter, firstMediumQuickSorted, firstMediumQuickSortDelimiter, firstMediumTimeElapsedQuickSort);
                writeToTxtOutput(outputWriter, medianStandardQuickSorted, medianStandardQuickSortDelimiter, medianStandardTimeElapsedQuickSort);
                writeToTxtOutput(outputWriter, heapSorted, heapSortDelimiter, timeElapsedHeapSort);

                // write file times
                timeTableOutputWriter.println("===========TIME ELAPSED PER SORT FOR FILE: " + fileNameOnly + "=============");
                writeTimeElapsedToFile(
                        fileNameOnly,
                        timeTableOutputWriter,
                        "Quicksort using first element as pivot, and partition size of 1 or 2",
                        firstStandardTimeElapsedQuickSort
                );
                writeTimeElapsedToFile(
                        fileNameOnly,
                        timeTableOutputWriter,
                        "Quicksort using first element as pivot, and partition size of 50",
                        firstMediumTimeElapsedQuickSort
                );
                writeTimeElapsedToFile(
                        fileNameOnly,
                        timeTableOutputWriter,
                        "Quicksort using first element as pivot, and partition size of 100",
                        firstLargeTimeElapsedQuickSort
                );
                writeTimeElapsedToFile(
                        fileNameOnly,
                        timeTableOutputWriter,
                        "Quicksort using median element as pivot, and partition size of 1 or 2",
                        medianStandardTimeElapsedQuickSort
                );
                writeTimeElapsedToFile(
                        fileNameOnly,
                        timeTableOutputWriter,
                        "Heapsort",
                        timeElapsedHeapSort
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
     * @param message A message to output to standard out
     */
    private static void output(String message) {
        System.out.println(message);
    }

    /**
     * this is an overloaded method that writes the data to standard out and to the writer
     * @param data A data array of sorted numbers
     */
    private static void output(int[] data, PrintWriter writer) {
        for (int datum : data) {
            System.out.println(datum);
            writer.println(datum);
        }
    }

    /* A utility function to print to output array of size n */
    private static void printArray(int arr[]) {
        for (int integer : arr)
            System.out.println(integer);
    }

    private static void generateInputFilesSinceTheLabInputFilesAreWrong() throws IOException, Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        int[] INPUT_SIZES = new int[] {50, 500, 1000, 2000, 5000};
        for (int size : INPUT_SIZES) {
            generateRandomFiles(size);
            generateOrderedFiles(size);
            generateReverseOrderedFiles(size);
        }
    }

    private static void generateOrderedFiles(int size) throws IOException, Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        int[] data = generateRandomData(size);
        int[] sorted = sortAsc(data, size);

        String fileName = "asc" + String.valueOf(size);
        writeToDataOutput(fileName + ".dat", inputFolderPath, sorted);
    }

    private static void generateReverseOrderedFiles(int size) throws IOException, Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        int[] data = generateRandomData(size);
        int[] sorted = sortDesc(data, size);

        String fileName = "reversed" + String.valueOf(size);
        writeToDataOutput(fileName + ".dat", inputFolderPath, sorted);
    }

    private static int[] sortAsc(int[] data, int size) throws Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        // use my own quicksort in generating the files, lol
        Quicksort ob = new Quicksort( "first", "standard");
        return ob.sort(data,0, size - 1);
    }

    private static int[] sortDesc(int[] data, int size) throws Quicksort.InvalidPartitionStoppingChoice, Quicksort.InvalidPivot {
        Quicksort ob = new Quicksort( "first", "standard");
        int[] sorted = ob.sort(data,0, size - 1);

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
        writeToDataOutput(fileName + ".dat", inputFolderPath, data);
    }

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

    private static void writeTimeElapsedToFile(
            String fileName,
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
                int fileLength = Integer.parseInt(fileName.replaceAll("\\D+",""));
                int[] data = new int[fileLength];
                BufferedReader dataReader = new BufferedReader(new FileReader(filePath));
                int i = 0;
                while ((line = dataReader.readLine()) != null) {
                    try {
                        data[i] = Integer.parseInt(line);
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

    public static class FileNotSorted extends Exception {
        public FileNotSorted(String errorMessage) {
            super(errorMessage);
        }
    }

}
