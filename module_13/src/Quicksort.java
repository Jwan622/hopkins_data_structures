public class Quicksort {
    private String pivotMethod;
    private int partitionStoppingSize;
    private final String[] PIVOT_CHOICES = {"first", "median"};
    private final int[] PARTITION_STOPPING_SIZES = {2,50,100};
    private final String[] PARTITION_STOPPING_CHOICES = {"standard", "medium", "large"};

    public Quicksort(String pivotMethod, String partitionStoppingChoice) throws InvalidPivot, InvalidPartitionStoppingChoice {
        boolean validPivot = false;
        boolean validPartitionStoppingChoice = false;

        // check to see if the pivot choice was valid
        for(String choice: PIVOT_CHOICES) {
            if(choice.equals(pivotMethod)) {
                validPivot = true;
            }
        }
        if (!validPivot) {
            throw new InvalidPivot("pivot choice is not valid, try again");
        }

        // check to see if the partition stopping choice was valid
        for(String choice: PARTITION_STOPPING_CHOICES) {
            if(choice.equals(partitionStoppingChoice)) {
                validPartitionStoppingChoice = true;
            }
        }
        if (!validPartitionStoppingChoice) {
            throw new InvalidPartitionStoppingChoice("partition stopping choice is not valid, try again");
        }

        // used to determine what kind of pivot to use
        this.pivotMethod = pivotMethod;
        // used to determine when to stop smaller partitions
        if (partitionStoppingChoice.equals(PARTITION_STOPPING_CHOICES[0])) {
            this.partitionStoppingSize = PARTITION_STOPPING_SIZES[0];
        } else if (partitionStoppingChoice.equals(PARTITION_STOPPING_CHOICES[1])) {
            this.partitionStoppingSize = PARTITION_STOPPING_SIZES[1];
        } else {
            this.partitionStoppingSize = PARTITION_STOPPING_SIZES[2];
        }
    }

    public int[] sort(int[] arr, int lowIndex, int highIndex) {
//        System.out.println("low index: " + lowIndex);
//        System.out.println("high index: " + highIndex);
        // check for empty or null array. do nothing in this case
        int[] dataCopy = new int[highIndex + 1];

        for (int i = 0; i <= highIndex; i++) {
            dataCopy[i] = arr[i];
        }

        partition(dataCopy, lowIndex, highIndex);

        return dataCopy;
    }

    private void partition(int[] arr, int lowIndex, int highIndex) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // Use insertion sort once the partition hits a certain size. By default, this should be 2.
        if(arr.length == this.partitionStoppingSize) {
            insertionSort(arr);
            return;
        }

        // if the indicies are equal or if crossed (shouldn't happen), do nothing.
        if (lowIndex >= highIndex) {
            return;
        }

        // Get the pivot element from the pivotIndex of the list
        int pivotIndex = determine_pivot(arr, lowIndex, highIndex);
//        System.out.println("pviot index: " + pivotIndex);

        int pivot = arr[pivotIndex];
//        System.out.println("pivot: " + pivot);
        int lowIndexCopy = lowIndex;
        int highIndexCopy = highIndex;

        // partitioning algorithm section of the sort.
        while (highIndexCopy != lowIndexCopy) {
            // this finds a value in the array that is not greater than the pivot starting from the right
            while (arr[highIndexCopy] > pivot && highIndexCopy != lowIndexCopy) {
//                System.out.println("high index copy: " + arr[highIndexCopy]);
                highIndexCopy--;
            }

            replace(arr, highIndexCopy, lowIndexCopy);
//            System.out.println("array after the high swpas");
//            printArray(arr);

            // this finds a value in the array that is not less than the pivot starting from the left
            while (arr[lowIndexCopy] < pivot && highIndexCopy != lowIndexCopy) {
//                System.out.println("low index copy: " + lowIndexCopy);
                lowIndexCopy++;
            }

            replace(arr, lowIndexCopy, highIndexCopy);
//            System.out.println("array after the low swpas");
//            printArray(arr);

            // copy pivot to where highIndexCopy and lowIndexCopy collided as per the video in the Johns Hopkins video lectures!
            if (lowIndexCopy == highIndexCopy) {
//                System.out.println("they equal each other for pivot!");
                arr[lowIndexCopy] = pivot;
            }
        }

//        System.out.println("printing array after setting pivot");
//        printArray(arr);

        //Do same operation as above recursively to sort two sub arrays. do not do anything if the lowIndex and highIndex would be the same.
        if (lowIndex < highIndexCopy - 1){
//            System.out.println("sorting low index to highindexcopy - 1");
            partition(arr, lowIndex, highIndexCopy - 1);
        }
        if (highIndex > lowIndexCopy + 1){
//            System.out.println("sorting lowindexCopy + 1 index to highindex");
            partition(arr, lowIndexCopy + 1, highIndex);
        }
    }

    public void replace(int array[], int insertFrom, int insertTo) {
        array[insertTo] = array[insertFrom];;
    }

    private int determine_pivot(int[] arr, int low, int high) {
        if (this.pivotMethod.equals(PIVOT_CHOICES[0])) {
            return low;
        } else if (this.pivotMethod.equals(PIVOT_CHOICES[1])) {
            // store the low index, high index, middle index numbers in an array
            int[] numbers = new int[] { arr[low], arr[high], arr[(low + high)/2] };
            // sort the low index, high index, middle index numbers
            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j < 3; j++) {
                    if (numbers[i] > numbers[j]) {
                        int temp = numbers[i];
                        numbers[i] = numbers[j];
                        numbers[j] = temp;
                    }
                }
            }

            // return median
            return numbers[1];
        }

        return low;
    }

    /**
     * insertion sort in place
     * @param arr the array to sort in place
     */
    private static void insertionSort(int[] arr) {
        int temp;
        // we assume element at index 0 is sorted already so we start at index 1
        for (int unsortedIndex = 1; unsortedIndex < arr.length; unsortedIndex++) {
            // this iterates backwards through the sorted section
            for(int sortedIndex = unsortedIndex; sortedIndex > 0; sortedIndex--){
                // check if element at sortedIndex is less than the element to the left of it in the sorted array
                if(arr[sortedIndex] < arr[sortedIndex-1]){
                    // move the smaller element at sortedIndex to the left and move larger element at sortedIndex - 1 to the right
                    temp = arr[sortedIndex];
                    arr[sortedIndex] = arr[sortedIndex-1];
                    arr[sortedIndex-1] = temp;
                }
            }
        }
    }

    /* A utility function to print array of size n */
    private static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.println(arr[i] + " ");
        System.out.println();
    }

    /**
     * custom exception to throw when pivot choice is not a valid choice
     */
    public class InvalidPivot extends Exception {
        public InvalidPivot(String errorMessage) {
            super(errorMessage);
        }
    }

    public class InvalidPartitionStoppingChoice extends Exception {
        public InvalidPartitionStoppingChoice(String errorMessage) {
            super(errorMessage);
        }
    }
}
