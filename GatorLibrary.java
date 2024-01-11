import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GatorLibrary {
	private RedBlackTree tree;

	// default constructor
	GatorLibrary() {
		this.tree = new RedBlackTree();   // initailize the red-black tree
	}

	// print the details of Book
	public Node printBook(int bookId) {
		Node node = tree.search(bookId);
		if (node == null || node == tree.nillNode) {
			node = new Node(-1, "", "", "");
		}
		return node;   // return the book node of the target ID
	}

	// printing all Books in the given range of book Ids
	public ArrayList<Node> printBooks(int bookId1, int bookId2) {
		ArrayList<Node> array = tree.range(bookId1, bookId2);
		if (array.isEmpty()) {
			Node node = new Node(-1, "", "", "");
			array.add(node);
		}
		return array;    // return array of all the books in the range given.
	}

	// inserting the Book into the library
	public Boolean insertBook(int bookId, String bookName, String authorName, String availabilityStatus) {
		Node node = new Node(bookId, bookName, authorName, availabilityStatus);
		if (tree.search(node.data.bookId) == tree.nillNode) {
			tree.insert(node);
			return true;     // return true, when node is inserted into the tree
		} else {
			return false;   // return false if did not insert
		}
	}

	// Assign the book to the requested patron, if the book is available, else add the student to reservation heap
	private String borrowBook(int patronId, int bookId, int patronPriority) {
		Node node = tree.search(bookId);
		StringBuilder temp = new StringBuilder();
		Patron patron = new Patron(patronId, patronPriority, (int) Instant.now().getEpochSecond());  // create patron object
		String availabilityStatus = node.data.availabilityStatus;
		if (availabilityStatus.contains("Yes")) {     		// if the book status is avaialable, assign the book to requested patron
			node.data.availabilityStatus = "No";
			node.data.borrowedBy = patron;
			temp.append("Book " + node.data.bookId + " Borrowed by Patron " + patronId);

		} else { 								// If the book is not available, add the patron to reseravtion heap
			node.data.minHeap.insert(patron);
			temp.append("Book " + node.data.bookId + " Reserved by Patron " + patron.id);

		}
		return temp.toString();
	}

	// remove the book from patron assignnment and make its availability as Yes or assign it next person in reservation
	private String returnBook(int patronId, int bookId) {
		Node node = tree.search(bookId);
		StringBuilder temp = new StringBuilder();
		if (node.data.availabilityStatus.equals("No")) {
			temp.append("Book " + bookId + " Returned by Patron " + patronId);
			if (node.data.minHeap.isEmpty()) {            // change the book availability to "yes", if there are no reservations
				node.data.availabilityStatus = "Yes";
				node.data.borrowedBy = null;
			} else {     								// assign the book to first patron in the min-heap as per priority
				node.data.borrowedBy = node.data.minHeap.getMin();
				temp.append("\n" + "Book " + bookId + " Allotted to Patron " + node.data.borrowedBy.id);
				node.data.minHeap.removeMin();
			}
		}
		return temp.toString();

	}

	// count the number of color flips
	private int colorFlipcount() {
		return tree.flipCount;

	}

	// find the books with closest id to the target bookID
	private String findClosestBook(int targetId) {
		return tree.findClosestBook(targetId);

	}

	//Delete the book from library and cancel all reservations on that book 
	private String deleteBook(int bookId) {
		Node node = tree.search(bookId);
		StringBuilder temp = new StringBuilder();
		if (node != null) {
			temp.append("Book " + bookId + " is no longer available. ");
			if (!node.data.minHeap.isEmpty()) {
				List<Patron> list = node.data.minHeap.getAllElements();
				if (list.size() > 1) {   // cancel all the reservations made on the book
					String result = list.stream().map(patron -> String.valueOf(patron.id))
							.collect(Collectors.joining(", "));
					temp.append("Reservations made by Patrons " + result + " have been cancelled!");
				} else {
					temp.append("Reservation made by Patron " + list.get(0).id + " has been cancelled!");
				}
			}
			tree.delete(bookId);    		// delete the node with target bookid from tree
		}
		return temp.toString();
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Error: input file not specified.");
			return;
		}
		String inputFileName = args[0];
		GatorLibrary gatorLibrary = new GatorLibrary();
		try {
			File input_file = new File(inputFileName);
			Scanner sc = new Scanner(input_file);        // reading from the input files given in the command prompt. 
			String fileName = new File(inputFileName).getName().replaceFirst("[.][^.]+$", "");
			FileWriter output_file = new FileWriter(fileName+"_output_file.txt");  //creating a file to write all
																						// the outputs into one txt file
			
			while (sc.hasNextLine()) {
				String input = sc.nextLine().strip();
				String[] in = input.substring(input.indexOf("(") + 1, input.indexOf(")")).split(",");

				if (input.startsWith("InsertBook")) {     //call the insert method to insert the book into the library
					int bookId = Integer.parseInt(in[0].strip());
					String bookName = in[1].strip();
					String authorName = in[2].strip();
					String availabilityStatus = in[3].strip();

					Boolean node = gatorLibrary.insertBook(bookId, bookName, authorName, availabilityStatus);
					if (node == false) {
						String out = "Duplicate BookId";
						output_file.write(out + "\n\n");
					}
				} else if (input.startsWith("Print")) {  // call the print method to print the book details
					if (in.length == 1) {
						int bookId = Integer.parseInt(in[0].strip());
						Node node = gatorLibrary.printBook(bookId);
						if (node.data.bookId == -1) {
							output_file.write("Book " + bookId + " not found in the Library");
							output_file.write("\n\n");

						} else {
							output_file.write("" + node + "");
							output_file.write("\n\n");
						}
					} else {					// call the print method to print all the books between the given range of book Ids
						int bookNumber1 = Integer.parseInt(in[0].strip());
						int bookNumber2 = Integer.parseInt(in[1].strip());
						ArrayList<Node> arr = gatorLibrary.printBooks(bookNumber1, bookNumber2);

						for (int i = 0; i < arr.size(); i++) {
							output_file.write("" + arr.get(i) + "");
							output_file.write("\n\n");
						}
					}
				} else if (input.startsWith("Borrow")) { //call the borrow method to assign the book to patron
					int patronId = Integer.parseInt(in[0].strip());
					int bookId = Integer.parseInt(in[1].strip());
					int patronPriority = Integer.parseInt(in[2].strip());
					output_file.write(gatorLibrary.borrowBook(patronId, bookId, patronPriority));
					output_file.write("\n\n");

				} else if (input.startsWith("Return")) {  // calling the return method to return the book
					int patronId = Integer.parseInt(in[0].strip());
					int bookId = Integer.parseInt(in[1].strip());
					output_file.write(gatorLibrary.returnBook(patronId, bookId));
					output_file.write("\n\n");

				} else if (input.startsWith("FindClosestBook")) { //calling method to find the closest book
					int targetId = Integer.parseInt(in[0].strip());

					output_file.write(gatorLibrary.findClosestBook(targetId));
					output_file.write("\n\n");

				} else if (input.startsWith("Delete")) {  // calling method to delete book
					int bookId = Integer.parseInt(in[0].strip());
					output_file.write(gatorLibrary.deleteBook(bookId));
					output_file.write("\n\n");

				} else if (input.startsWith("ColorFlipCount")) {   //calling method to count the number of flips
					output_file.write("Color Flip Count: "+gatorLibrary.colorFlipcount());
					output_file.write("\n\n");

				} else if (input.startsWith("Quit")) {  //Exit the library software.
					output_file.write("Program Terminated!!");
					break;

				}
			}

			sc.close();
			output_file.close();  // Close the file
		} catch (IOException ioException) {
			ioException.getStackTrace();
		}
	}

}
