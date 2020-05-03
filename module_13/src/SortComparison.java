import java.io.*;
import java.util.Random;

public class SortComparison {
    private static String inputFolderPath = "inputData/";
    private static String outputFolderName = "outputData/";
    private static int fileLength = 50;

    public static void main(String args[]) throws IOException, Quicksort.InvalidPivot, Quicksort.InvalidPartitionStoppingChoice {
        generateDirectory("inputData");
        generateInputFilesSinceTheLabInputFilesAreWrong();;
        File folder = new File(inputFolderPath);
        File[] listOfDataFiles = folder.listFiles();

        File folderOutput = new File(outputFolderName);

        for (File file : listOfDataFiles) {
            if (file.isFile()) {
                String filePath = file.getPath();
                String fileNameOnly = file.getName();
                output("Now reading in data from " + fileNameOnly);
                FileInputStream fileInputStreamData = new FileInputStream(filePath);
                DataInputStream input = new DataInputStream(fileInputStreamData);

//                int fileLength = Integer.parseInt(fileNameLast .replaceAll("\\D+",""));

                int[] dataInput = new int[fileLength];

//              read in the raw input data
                for (int index = 0; index < fileLength; index++) {
                    int data = input.readInt();
                    dataInput[index] = data;
                }

                Quicksort quickSorter = new Quicksort( "first", "standard");
                int[] quickSorted = quickSorter.sort(dataInput,0, fileLength-1);
                String quickSortDelimiter = "===========QuickSort=============";

                MinHeapsort heapSorter = new MinHeapsort(fileLength);
                int[] heapSorted = heapSorter.sort(dataInput);
                String heapSortDelimiter = "===========HeapSort=============";

                System.out.println("writing to output files...");

                if (fileLength == 50) {
                    String outputFileName = fileNameOnly.split("\\.")[0];
                    generateDirectory("outputData");
                    File outputFile = new File(outputFolderName + outputFileName + "_sorted.txt");
                    PrintWriter outputWriter = new PrintWriter(new FileOutputStream(outputFile));
                    writeToTxtOutput(outputWriter, quickSorted, quickSortDelimiter);
                    writeToTxtOutput(outputWriter, heapSorted, heapSortDelimiter);
                    outputWriter.close();
                }

                input.close();
            }
        }
        // if all the output files aren't sorted, throw an error
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
        printArray(sorted);
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

    private static void writeToTxtOutput(PrintWriter output, int[] data, String delimiter) {
        output.println(delimiter);
        for (int datum : data) {
            output.println(datum);
        }
        output.println();
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
        output("Checking output files...");
        File dir = new File(outputFolderName);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                String filePath = dataFile.getPath();
                String fileName = filePath.split("/")[1];
                String line;
//                int fileLength = Integer.parseInt(fileName.replaceAll("\\D+",""));
                int[] data = new int[fileLength];
                BufferedReader dataReader = new BufferedReader(new FileReader(filePath));
                int i = 0;
                while ((line = dataReader.readLine()) != null) {
                    try {
                        data[i] = Integer.parseInt(line);
                        i++;
                    } catch (NumberFormatException e) {
                        System.out.println("skpping non int in the sorted file");
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
                System.out.println("this file was sorted; " + fileName);
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

    public static class FileNotSorted extends Exception {
        public FileNotSorted(String errorMessage) {
            super(errorMessage);
        }
    }

}
