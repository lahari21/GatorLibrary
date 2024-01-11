# GatorLibrary: Library Management System
## Introduction
GatorLibrary is a fictional library for which I created a software system to efficiently manage its
books, patrons, and borrowing operations. The system utilizes Red-Black tree data structure to
ensure efficient management of the books. I have used a priority Queue mechanism using Binary
Min-Heaps as data Structures for managing book reservations in case a book is not currently
available to be borrowed. Each book will have its own min-heap to keep track of book reservations
made by the patrons.

## Compilation and Running

1. Run the below command to compile the project.
> `javac gatorLibrary.java`
2. Or you can compile the program using makefile.
> `make`
3. Run the below command to run the project.
> `java gatorLibrary`
4. Run the below command to give input to the project in a text file.
> `java gatorLibrary input.txt`
5. The output will get stored in another text file named <input>_output_file.txt

### Input Example:
> Input.txt contains input in the below format.
 ![image](https://github.com/lahari21/GatorLibrary/assets/62760117/e37ba52f-7d70-42f4-888a-d681a3f4b947) width=50% height = 50% >

 A sample input.txt has also been uploaded.
