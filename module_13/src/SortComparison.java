import java.io.*;


public class SortComparison {
    public static void main(String args[]) throws IOException, Quicksort.InvalidPivot {
        String fileName = "lab_4_input_files/asc50.dat";
        FileInputStream fileInputStreamData = new FileInputStream(fileName);
        DataInputStream input = new DataInputStream(fileInputStreamData);

        // get length of the file based on the name. This assumes the fileName contains the true length of the file.
        String[] fileNameSplit = fileName.split("/");
        String fileNameLast = fileNameSplit[fileNameSplit.length - 1];
        int fileLength = Integer.parseInt(fileNameLast .replaceAll("\\D+",""));

        int[] dataInput = new int[fileLength];

        for (int index = 0; index < fileLength; index++) {
            dataInput[index] = input.readInt();
        }

        Quicksort ob = new Quicksort( "first");
        ob.sort(dataInput,0, fileLength-1);

        System.out.println("sorted array is ...");
        printArray(dataInput);

//        input.close();
    }

    /**
     * this is an overloaded method that writes the message to standard out.
     * @param message A message to output to standard out
     */
    private static void output(int message) {
        System.out.println(message);
    }

    /**
     * this is an overloaded method that writes the message to standard out and the writer
     * @param message A message to output to standard out
     */
    private static void output(int message, PrintWriter writer) {
        System.out.println(message);
        writer.println(message);
    }

    /* A utility function to print array of size n */
    private static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.println(arr[i] + " ");
        System.out.println();
    }
}
