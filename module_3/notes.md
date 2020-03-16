Video part 2:
- one problem with recursion is the redundancy involved. In fibonacci, lots of duplicate calculations means we have redundant tree paths to calculate. Fib(0) is done a lot.


Consider memoizing every recursivep roblem. But you can still do recursion without memoization (saving previously calculated values to be used later)


How are stacks used?
Every instance of a recursive call can be saved on the stack.




__Greedy algos:__

A greedy algorithm is an algorithm that, when presented with a list of options, chooses the option that is optimal at that point in time. The choice of option does not consider additional subsequent options, and may or may not lead to an optimal solution. A greedy algorithm makes choices based on limited information at a specific point in the time. The result of many greedy algorithms is therefore not optimal.



__Max array problem:__
- if we sort and pick the last, usually it's nlogn
- if we pairwise compare, it's n-1 comparisons so O(n) is the cost
- What about recursion? Divide array in half. then divide again. So if array is 5,3,7,4,9,6. You can divide it into 5,3,7 on the left side. That divides into 5 and 3,7. The right side of that is 3 and 7 and we compare, 7 is the winner. 5 is the winner on the left, 7 is the winner on the right. We compare again, 7 is the winner vs 5. That's the left side. So lets compare 4,9,6. eventually recursively the winner is 9. then 7 is compared with 9, and 9 is the biggest number. Unlike binary search, we have to investigate both "sides" whereas we don't need to do that in binary search so there's more work going on here.

Cost of dividing is constant time from one level to the next.... just have to calculate the midpoint and split the group. Passing up the winners from each group is also a constant time cost. More work than binary search, but hard to tell by how much. It's more work than binary search... which is nlogn so that's the lower bound.


__Why recursive?__
- they are simple, elegant, and brief and intuitive. Best use of programmer effort. Possible proof of correctness available... you can prove something is correct and used for tests. In that process, the test using recurive could show a working solution. 
- Sometimes the language or environment doesn't support recursive. Still considered, because basis of correctness, learn more about the problem, source of iterative solution.
- learn more about the problem. eg. rapid prototyping. You can use a recursive solution since it's simple to learn why you need to know.


Java and C++ support recursion with stacks... they simulate recursion.
