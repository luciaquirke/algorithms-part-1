# Assignments for Princeton's Algorithms, Part I

## Percolation

Modelling connectivity in systems composed of permeable and non-permeable parts, e.g. conductance in a system of metallic and non-metallic particles, or water flowing through empty and full sites.
The model uses the weighted quick union algorithm.

A difficult bug, backwash, can occur when visualising the percolation system. Areas connected to the system entrance are coloured blue. Because all the exit areas are connected, when a
blue area is connected to the system exit all areas connected to the exit turn blue, even if they're not directly connected to the entrance. You can fix this by 
adding a separate weighted quick union data structure solely for visualisation, without connected exit areas.

## Deques and Randomised Queues

Implementing a resizing array, linked list, deque and randomised queue using fundamental data types. 

## Collinear Points

Recognising line patterns in a set of points. Introduces the Comparable interface.

## 8 Puzzle

Solve an sliding tile game using the A* algorithm with manhattan distances. Uses cached data to improve performance.

## Kd-Trees

Implements a 2d-tree to support efficient range search (finding all the coordinates contained in a given area) and nearest neighbour search.
Includes a brute force implementation using a red-black BST.
