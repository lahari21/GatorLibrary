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
Insert(5,50,120)
Insert(4,30,60)
Insert(7,40,90)
Insert(3,20,40)
Insert(1,10,20)
Print(2)
Insert(6,35,70)
Insert(8,45,100)
Print(3)
Print(1,6)
UpdateTrip(6,75)
Insert(10,60,150)
GetNextRide()
CancelRide(5)
UpdateTrip(3,22)
Insert(9,55,110)
GetNextRide()

> A sample input.txt has also been uploaded.
