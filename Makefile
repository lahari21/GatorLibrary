JCC = javac

TASKS = \
GatorLibrary.java\
MinHeap.java\
Node.java\
RedBlackTree.java\
Data.java\
Patron.java\


compile:
	javac GatorLibrary.java

# #compile all the classes
# Node.class: Node.java
# 	$(JCC) Node.java

# MinHeap.class: MinHeap.java
# 	$(JCC) MinHeap.java

# RedBlackTree.class: RedBlackTree.java
# 	$(JCC) RedBlackTree.java

# gatorTaxi.class: gatorTaxi.java
# 	$(JCC) gatorTaxi.java

clean:
	rm -f *.class
