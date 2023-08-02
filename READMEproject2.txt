Name: Duc Vu 
UR ID: 32248562
mail: dvu2@u.rochester.edu
Project2, class HuffmanSubmit.java

In this java file, I created a class called Node and 2 methods encode and decode to represent the Huffman Coding

From  line 12 to 49, it's a Node class with variables, constructors, and method Left, Right, and toString
	line 52 to 65, method assignHuffmanChar utilizes the Huffman tree to assign 1s and 0s to the characters
	line 68 to 72, this class allows Priority Queue to sort based on the frequency
	
	In the encode method (line 75 to 134), firstly the code created a hashmap to store all the characters and their frequency. Then, the for loop is to loop through the hashmap to take the character and frequency to print it in the frequency file. After that, the code added all elements in the hashmap to the priority queue, so that the elements are sorted. Line 108 to 113 is creating a HuffmanTree using that priority queue. Then after the assignHuffmanChar, we have the encoded version of each character, and the last part from line 120 to 132 is to print out them in the output file.

	In the decode, the code reads a frequency file which contains the frequencies of the characters in the input file. The frequencies are read as binary strings of length 8, which represent the ASCII values of the characters. Each frequency is then read as a string and converted to an integer. These frequencies will be used to build a Huffman tree for encoding and decoding. The code then uses a priority queue to build a Huffman tree from the character frequencies. Each node in the priority queue represents a character and its frequency. The priority queue is initialized with a custom comparator that compares the frequencies of the nodes.

Next, the code builds the Huffman tree by repeatedly removing the two smallest nodes from the priority queue, combining them into a single node, and adding the new node back to the queue. This process continues until there is only one node left in the queue, which becomes the root of the Huffman tree. After building the Huffman tree, the code starts the decoding process. It reads binary data from the input file and traverses the Huffman tree to find the corresponding characters. The decoding process continues until it has processed all of the characters in the input file. Finally, the decoded data is written to the output file, and the output stream is closed.