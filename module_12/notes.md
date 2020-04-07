#### B-Trees

different from binary tree

- Introduction to B-trees
In a binary tree, each node has one key (the data) and up to two children. A B-tree with order K is a tree where nodes
 can have
 up to K-1 keys and up to K children. The order is the maximum number of children a node can have. Ex: In a B-tree with order 4, a nodes can have 1, 2, or 3 keys, and up to 4 children. B-trees have the following properties:

- A single node in a B-tree can contain multiple keys.
All keys in a B-tree must be distinct.
All leaf nodes must be at the same level.
An internal node with N keys must have N+1 children.
Keys in a node are stored in sorted order from smallest to largest.
Each key in a B-tree internal node has one left subtree and one right subtree. All left subtree keys are < that key, and all right subtree keys are > that key.

- An order 3 B-tree can have up to 2 keys per node. This root node contains the keys 10 and 20, which are ordered from smallest to largest.

- An internal node with 2 keys must have three children. The node with keys 10 and 20 has three children nodes, with
 keys 5, 15, and 25.
![b-tree](b-tree.png)



This is a valid tree:

![](valid_order_tree.png)


#### 2-3-4 tree
Remember that  A B-tree with order K is a tree where nodes can have up to K-1 keys and up to K children. 

![2-3-4](2-3-4_tree.png)