Simple Hashtable Implementation

This hashtable implementation was created as a practice for a CS101 course taken at the University of Applied Science Trier.

The implementation includes:
- interfaces to be found in the package interfaces
- implementation classes to be found in the package implementation
- four different collision avoidance strategies to be found in the package implementation.collisionAvoidance
- code for different testcases to be found in the package testcode

Every HashTable object has one private parameter calles caStrategy which holds an instance of ICollisionAvoidance. That is your collision avoidance strategy. Pass different implementations that implement the ICollisionAvoidance interface as an constructor argument of HashTable to change the strategy.

HashTable resizes automatically when cetrain thresholds are violated. Thresholds are defined by the HashTable's final attributes MAX_LOAD_FACTOR and MIN_LOAD_FACTOR.

TestcodeRandom class creates elements with random keys and random value and inserts them into the hashtable. It takes three command line parameters:
args[0] number of elements to be inserted into the HashTable
args[1] upper limit for the randomizer
args[3] selected collision avoidance strategy [0-3]

TODOs:
- Implement HashTableResizeException, that handles errors when the hashtable is being resized
- Implement an algorithm that resizes the hashtable to next prime

Ideas:
- Implement a GUI
- Implement the options to compare different collision avoidance strategies
- Build an Android app for visualisation and practice purposes

Feel free to implement the ideas and todos above or add your own two cents. 
Fork it, play with it, modify it and please report bugs! ;-)

Christian H.
December 2013