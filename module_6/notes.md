**Doulby Linked Lists**

## What is it?
A doubly-linked list is similar to a singly-linked list, but instead of using a single pointer to the next node in the list, each node has a pointer to the next and previous nodes. Such a list is called "doubly-linked" because each node has two pointers, or "links". A doubly-linked list is a type of positional list: A list where elements contain pointers to the next and/or previous elements in the list.


### How are inserts done:  
Insert in middle of list: If the list's head pointer is not null (list is not empty) and curNode does not point to the list's tail node, the algorithm updates the current, new, and successor nodes' next and previous pointers to achieve the ordering {curNode newNode sucNode}, which requires four pointer updates: point the new node's next pointer to sucNode, point the new node's previous pointer to curNode, point curNode's next pointer to the new node, and point sucNode's previous pointer to the new node.



**Hybrid implementation**
- the programmer has to manage the free space in the hybrid implementation. Has to manage the free space. If inserting into the list,
we need to pop off the free space and use that space to add an item. We have to pop free space off the free space which is something that the
computer does for the programmer normally.

### Digraph
> A directed graph, or digraph, consists of vertices connected by directed edges. A directed edge is a connection between a starting vertex and a terminating vertex. In a directed graph, a vertex Y is adjacent to a vertex X, if there is an edge from X to Y.
>
In a directed graph:

A path is a sequence of directed edges leading from a source (starting) vertex to a destination (ending) vertex.
A cycle is path that starts and ends at the same vertex. A directed graph is cyclic if the graph contains a cycle, and acyclic if the graph does not contain a cycle.


weighted graphs:
> The cycle length is the sum of the edge weights in a cycle. A negative edge weight cycle has a cycle length less than 0. A shortest path does not exist in a graph with a negative edge weight cycle,


**graphs**:
video 3
Just a pictorial way to represent information

a graph is G=(V,E), E for edges may be empty but V may not be empty.

- directed graph with arrows on the edges is also known as a digraph.

- if the edges have no arrowheads, it's an undirected graph. The direction is both ways.

- directed edges are on way streets. undirected are two ways. Graph can't have both, only one type of edge.

parallel edges are edges that go from one vertex to teh same vertex. Have to be in the same direction.


The way a graph is drawn is not important as long as teh physical characteristics are preserved.  

- acyclic graph has no cycles.
- an edge that connects to the own vertex are cycles of 1. If the edge starts from a vertex and ends at a vertex, it's a cycle of 1.


edge values are called weights. If no weights, it is assumed they are all 1.


degree is the numnber of incident edges. In an undirected graph, an edge is counter twice if the edge goes out and comes into the same vertex.

In a directed graph, you have a to distinguish bewteen out edges and in edges for degree. A vertex might have a degre of 3,2 meaning 3 in edges and 2 out edges.