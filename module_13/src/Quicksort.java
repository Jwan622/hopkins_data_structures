/**
 * Quicksort algorithm. Using OOP, this quicksort can sort data in the following ways:
 * - use the element at the first index as the pivot and partitioning until there is a partition of size 1 or 2 and then using insertion sort.
 * - use the element at the first index as the pivot and partitioning until there is a partition of size 50 and then using insertion sort.
 * - use the element at the first index as the pivot and partitioning until there is a partition of size 100 and then using insertion sort.
 * - use the element at the median index as the pivot and partitioning until there is a partition of size 1 or 2 then using insertion sort.
 */
public class Quicksort implements Sorter {
    private String pivotMethod;
    private int[] partitionStoppingSizes;
    private final String[] PIVOT_CHOICES = {"first", "median"};
    private final int[] PARTITION_STOPPING_SIZES = {1,2,50,100};
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
            this.partitionStoppingSizes = new int[] { PARTITION_STOPPING_SIZES[0], PARTITION_STOPPING_SIZES[1] };
        } else if (partitionStoppingChoice.equals(PARTITION_STOPPING_CHOICES[1])) {
            this.partitionStoppingSizes = new int[] { PARTITION_STOPPING_SIZES[1] };
        } else {
            this.partitionStoppingSizes = new int[] { PARTITION_STOPPING_SIZES[2] } ;
        }
    }

    /**
     * the public api method for sorting. It first copies the incoming data so it does not mutate the data which might be
     * used in another sort. Then, it determines what pivot to use based on values that were passed to this sort and the constructor.
     * It then calls partition.
     * @param arr the data to sort
     * @return the sorted array
     */
    public int[] sort(int[] arr) {
        int size = arr.length;
        // check for empty or null array. do nothing in this case
        int[] dataCopy = new int[size];

        for (int i = 0; i <= size - 1; i++) {
            dataCopy[i] = arr[i];
        }

        int pivotIndex = determine_pivot(0, size - 1);
        // if the pivot is not the first item, then do an initial swap before the partitioning.
        if (pivotIndex != 0) {
            swap(dataCopy, pivotIndex, 0);
        }

        partition(dataCopy, 0, size - 1);

        return dataCopy;
    }

    /**
     * method to partition the array by setting the pivot as the element in the lowest index and swapping elements between
     * high and low indicies until the indicies collide. Then, right and left partitions are created to the left and right
     * of the collided indicies and this method is called again recursively.
     * @param arr the array to sort
     * @param lowIndex lowest index of the partition
     * @param highIndex highest index of the partition
     */
    private void partition(int[] arr, int lowIndex, int highIndex) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int partitionLength = highIndex - lowIndex + 1;

        // Use insertion sort once the partition hits a certain size. By default, this should be 2.
        if (contains(this.partitionStoppingSizes, partitionLength)) {
            insertionSort(arr, lowIndex, highIndex);
            return;
        }

        // if the indicies are equal or if crossed (shouldn't happen), do nothing.
        if (lowIndex >= highIndex) {
            return;
        }

        // the pivot is always the first item in the partition in the recursive calls
        int pivot = arr[lowIndex];

        int lowIndexCopy = lowIndex;
        int highIndexCopy = highIndex;

        // partitioning algorithm section of the sort.
        while (highIndexCopy != lowIndexCopy) {
            // this finds a value in the array that is not greater than the pivot starting from the right
            while (arr[highIndexCopy] > pivot && highIndexCopy != lowIndexCopy) {
                highIndexCopy--;
            }

            replace(arr, highIndexCopy, lowIndexCopy);

            // this finds a value in the array that is not less than the pivot starting from the left
            while (arr[lowIndexCopy] < pivot && highIndexCopy != lowIndexCopy) {
                lowIndexCopy++;
            }

            replace(arr, lowIndexCopy, highIndexCopy);

            // copy pivot to where highIndexCopy and lowIndexCopy collided as per the video in the Johns Hopkins video lectures!
            if (lowIndexCopy == highIndexCopy) {
                arr[lowIndexCopy] = pivot;
            }
        }

        // Do same operation as above recursively to sort two sub arrays. do not do anything if the lowIndex and highIndex would be the same.
        if (lowIndex < highIndexCopy - 1){
            partition(arr, lowIndex, highIndexCopy - 1);
        }
        if (highIndex > lowIndexCopy + 1){
            partition(arr, lowIndexCopy + 1, highIndex);
        }
    }

    /**
     * used to insert the element at the insertFrom to the indexTo. Used by the partition method to do the replacing of
     * elements with other elements at the respective down and up pointers.
     * @param array the array to replace elements in
     * @param insertFrom the index where an element is moved from
     * @param insertTo the index where the element at indexFrom is moving into
     */
    private void replace(int array[], int insertFrom, int insertTo) {
        array[insertTo] = array[insertFrom];;
    }

    /**
     * used when the pivot index isn't the first item and we need to do an initial swap before the partitoning algorithm
     */
    private void swap(int array[], int insertFrom, int insertTo) {
        int temp = array[insertFrom];
        array[insertFrom] = array[insertTo];
        array[insertTo] = temp;
    }

    /**
     * used to determine the pivot to use. If the median pivot is chosen, it is moved into the first index of the array and
     * the normal quicksort is run. ex: if the lowest index is 5 and the highest is 10, the median index will be 7 due to
     * integer division
     * @param low the lowest index of the partition
     * @param high the highest index of the partition.
     * @return the pivot index to use
     */
    private int determine_pivot(int low, int high) {
        if (this.pivotMethod.equals(PIVOT_CHOICES[0])) {
            return low;
        } else {
            // return median
            return (low + high) / 2;
        }
    }

    /**
     * insertion sort in place
     * @param arr the array to sort in place
     */
    private static void insertionSort(int[] arr, int lowIndex, int highIndex) {
        int temp;
        // we assume element at index 0 is sorted already so we start at index 1. We will iterate up to the high index
        for (int unsortedIndex = lowIndex + 1; unsortedIndex <= highIndex; unsortedIndex++) {
            // this iterates backwards through the sorted section
            for(int sortedIndex = unsortedIndex; sortedIndex > lowIndex; sortedIndex--){
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
     * used to check if the partition size is reached to begin insertion sort.
     * @param arr the array of partition stopping sizes for this version of quicksort
     * @param number the size of the partition
     * @return a boolean whether the array contains the number.
     */
    private static boolean contains(int arr[], int number) {
        boolean test = false;
        for (int element : arr) {
            if (element == number) {
                test = true;
                break;
            }
        }
        return test;
    }

    /**
     * custom exception to throw when pivot choice is not a valid choice
     */
    public class InvalidPivot extends Exception {
        public InvalidPivot(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * custom exception to throw when the stopping size to begin insertion sort is invalid
     */
    public class InvalidPartitionStoppingChoice extends Exception {
        public InvalidPartitionStoppingChoice(String errorMessage) {
            super(errorMessage);
        }
    }
}
