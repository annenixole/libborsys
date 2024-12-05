
package LinkedList;

public class BorrowedLinkedList {
    public BorrowedBookNode head;
    
    public BorrowedLinkedList(){
        head = null;
    }
    
    public void borrowBook(String callnumber,String isbn,int quantityBorrowed, String borrowerNum, String borrowedDate,String expectReturn,String borrowerDetails, String title,String author){
        BorrowedBookNode newBook = new BorrowedBookNode(callnumber, isbn, quantityBorrowed, borrowerNum, borrowedDate,expectReturn, borrowerDetails, title, author);
        
        //Adding borrowedbook to the last row
        if(head == null){
            head = newBook;
        }  else{
            BorrowedBookNode current = head;
            while(current.next != null){
                current = current.next;
            }
            current.next = newBook;
        }
    }
    
    public void printBook(){
      BorrowedBookNode current = head;
      while(current != null){
          System.out.println(current.callNumber + " " + current.isbn + " " + current.quantityBorrowed + " " + current.borrowerNum
                  + " " + current.borrowedDate + " " + current.expectReturn + " " + current.borrowerDetails + " " + current.title + " " + current.author);
          current = current.next;
      }
    }
    
    public BorrowedBookNode getItemAt(int index){
        BorrowedBookNode current = head;
        int currentIndex = 0;
        
        while(current!= null){
            if(currentIndex == index){
                return current;
            }
            current = current.next;
            currentIndex++;
        }
        return null;
    }
}
