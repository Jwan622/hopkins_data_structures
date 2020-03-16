## Sorting
Sorting is the process of converting a list of elements into ascending (or descending) order. For example, given a list of numbers (17, 3, 44, 6, 9), the list after sorting is (3, 6, 9, 17, 44). You may have carried out sorting when arranging papers in alphabetical order, or arranging envelopes to have ascending zip codes (as required for bulk mailings).

The challenge of sorting is that a program can't "see" the entire list to know where to move an element. Instead, a program is limited to simpler steps, typically observing or swapping just two elements at a time. So sorting just by swapping values is an important part of sorting algorithms.

## Bubble sort
Bubble sort is a sorting algorithm that iterates through a list, comparing and swapping adjacent elements if the second element is less than the first element. Bubble sort uses nested loops. Given a list with N elements, the outer i-loop iterates N times. Each iteration moves the  largest element into sorted position. The inner j-loop iterates through all adjacent pairs, comparing and swapping adjacent elements as needed, except for the last i pairs that are already in the correct position,.

Because of the nested loops, bubble sort has a runtime of O(). Bubble sort is often considered impractical for real-world use because many faster sorting algorithms exist.

## Quick sort

Quicksort
Quicksort is a sorting algorithm that repeatedly partitions the input into low and high parts (each part unsorted), and then recursively sorts each of those parts. To partition the input, quicksort chooses a pivot to divide the data into low and high parts. The pivot can be any value within the array being sorted, commonly the value of the middle array element. Ex: For the list (4, 34, 10, 25, 1), the middle element is located at index 2 (the middle of indices 0..4) and has a value of 10.

To partition the input, the quicksort algorithm divides the array into two parts, referred to as the low partition and the high partition. All values in the low partition are less than or equal to the pivot value. All values in the high partition are greater than or equal to the pivot value. The values in each partition are not necessarily sorted. Ex: Partitioning (4, 34, 10, 25, 1) with a pivot value of 10 results in a low partition of (4, 10, 1) and a high partition of (34, 25). Values equal to the pivot may appear in either or both of the partitions.

The partitioning part:
Partitioning algorithm
The partitioning algorithm uses two index variables l and h (low and high), initialized to the left and right sides of the current elements being sorted. As long as the value at index l is less than the pivot value, the algorithm increments l, because the element should remain in the low partition. Likewise, as long as the value at index h is greater than the pivot value, the algorithm decrements h, because the element should remain in the high partition. Then, if l >= h, all elements have been partitioned, and the partitioning algorithm returns h, which is the index of the last element in the low partition. Otherwise, the elements at indices l and h are swapped to move those elements to the correct partitions. The algorithm then increments l, decrements h, and repeats.


```
int partition(numbers, i, k) {   
   /* Initialize variables */
   
   /* Pick middle value as pivot */
   midpoint = i + (k - i) / 2
   pivot = numbers[midpoint]

   l = i
   h = k
   
   while (!done) {
      /* Increment l while numbers[l] < pivot */
      while (numbers[l] < pivot) {
         ++l
      }
      
      /* Decrement h while pivot < numbers[h] */
      while (pivot < numbers[h]) {
         --h
      }
/* If there are zero or one items remaining,
         all numbers are partitioned. Return h */
      if (l >= h) {
         done = true
      }
      else {
         /* Swap numbers[l] and numbers[h],
            update l and h */
         temp = numbers[l]
         numbers[l] = numbers[h]
         numbers[h] = temp
         
         ++l
         --h
      }
   }
   return h
}
```

