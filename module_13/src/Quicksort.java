public class Quicksort {
    private String pivotMethod;
    private final String[] PIVOT_CHOICES = {"first", "median"};

    public Quicksort(String pivotMethod) throws InvalidPivot {
        boolean validPivot = false;
        for(String choice: PIVOT_CHOICES) {
            if(choice.equals(pivotMethod)) {
                validPivot = true;
            }
        }
        if (!validPivot) {
            throw new InvalidPivot("pivot choice is not valid, try again");
        }
        // used to determine what kind of pivot to use
        this.pivotMethod = pivotMethod;
    }

    public void sort(int[] arr, int lowIndex, int highIndex) {
        System.out.println("low index: " + lowIndex);
        System.out.println("high index: " + highIndex);
        // check for empty or null array
        if (arr == null || arr.length == 0) {
            return;
        }

        if (lowIndex >= highIndex) {
            return;
        }

        // Get the pivot element from the pivotIndex of the list
        int pivotIndex = determine_pivot(lowIndex, highIndex);
        System.out.println("pviot index: " + pivotIndex);

        int pivot = arr[pivotIndex];
        System.out.println("pivot: " + pivot);
        int lowIndexCopy = lowIndex;
        int highIndexCopy = highIndex;

        // In each iteration, we will identify a number from left side which is greater then the pivot value, and also
        // we will identify a number from right side which is less then the pivot value. Once the search is done, then
        // we exchange both numbers.

        while (highIndexCopy != lowIndexCopy) {
            // this finds a value in the array that is not greater than the pivot starting from the right
            while (arr[highIndexCopy] > pivot && highIndexCopy != lowIndexCopy) {
                System.out.println("high index copy: " + arr[highIndexCopy]);
                highIndexCopy--;
            }

            insert(arr, highIndexCopy, lowIndexCopy);
            System.out.println("array after the high swpas");
            printArray(arr);

            // this finds a value in the array that is not less than the pivot starting from the left
            while (arr[lowIndexCopy] < pivot && highIndexCopy != lowIndexCopy) {
                System.out.println("low index copy: " + lowIndexCopy);
                lowIndexCopy++;
            }

            insert(arr, lowIndexCopy, highIndexCopy);
            System.out.println("array after the low swpas");
            printArray(arr);

            // copy pivot to where highIndexCopy and lowIndexCopy collided as per the video in the Johns Hopkins video lectures!
            if (lowIndexCopy == highIndexCopy) {
                System.out.println("they equal each other for pivot!");
                arr[lowIndexCopy] = pivot;
            }
        }

        System.out.println("printing array after setting pivot");
        printArray(arr);

        //Do same operation as above recursively to sort two sub arrays. do not do anything if the lowIndex and highIndex would be the same.
        if (lowIndex < highIndexCopy - 1){
            System.out.println("sorting low index to highindexcopy - 1");
            sort(arr, lowIndex, highIndexCopy - 1);
        }
        if (highIndex > lowIndexCopy + 1){
            System.out.println("sorting lowindexCopy + 1 index to highindex");
            sort(arr, lowIndexCopy + 1, highIndex);
        }
    }

    public void insert(int array[], int insertFrom, int insertTo) {
        array[insertTo] = array[insertFrom];;
    }

    private int determine_pivot(int low, int high) {
        if (this.pivotMethod.equals(PIVOT_CHOICES[0])) {
            return low;
        }

        return low;
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
}
