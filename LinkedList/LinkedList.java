
package LinkedList;

public class LinkedList {
     public BookInventory head;
     
     public LinkedList(){
        head = null;
     }
     
     public void addItem(String image,int isbn,String booktype,String categ, String title, String author,int quantity,int borrowedquantity, double price){
        BookInventory newItem = new BookInventory(image,isbn,booktype,categ,title,author,quantity,borrowedquantity,price);
        
            newItem.next = head; //the new Item will be addded on top
            head = newItem;
            
    }
     
     public void printItem(){
         BookInventory current = head;
         while(current!=null){
             System.out.println(current.imagebook +" "+current.isbnbook+" "+current.booktype+" "+current.categtype
             +" "+ current.titlebook +" "+ current.bookauthor+" "+current.quantitybook+" "+current.pricebook);
             current = current.next;
         }
     }
     
     public void deleteItemAt(int index){
         if (index == 0) {
             head = head.next;
             return;
         }
         BookInventory current = head;
         for(int i = 0; i<index-1; i++){ // Traversing 
             if(current == null || current.next == null){
                 System.out.println("Index out of bounds!");
                 return;
             }
             current = current.next;
         }
         current.next = current.next.next; //remove the node
     }
     
     public void updateItem(int index,String newImage,int newisbn,String newBooktype,String newCateg, String newTitle, String newAuthor,int newquantity,int newborrowedquantity,double newPrice) {
        BookInventory current = head;
        int search = 0;
        
        while (current != null && search < index) {
                current = current.next;
                search++;
            }
        
        if (current != null) {
            if(newImage != null){
                 current.imagebook = newImage;
            }
                current.quantitybook = newquantity;
                current.borrowedquantity = newborrowedquantity;
                current.isbnbook = newisbn;
                current.booktype= newBooktype;
                current.categtype = newCateg;
                current.titlebook = newTitle;
                current.bookauthor = newAuthor;
                current.pricebook = newPrice;
                System.out.println("Item updated successfully!");
                return;
            }
        else{
            System.out.println("Index out of bounds. Update failed."); 
        }
    }
     
     public BookInventory getItemAt(int index) {
        BookInventory current = head;
        int currentIndex = 0;

        // Traverse the linked list to find the item at the specified index
        while (current != null) {
            if (currentIndex == index) {
                return current; // Return the item at the specified index
            }
            current = current.next;
            currentIndex++;
        }

        // Return null if the index is out of bounds
        return null;
    }
}
