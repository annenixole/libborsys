
package Library;
import LinkedList.LinkedList;
import LinkedList.BookInventory;
import LinkedList.BorrowedLinkedList;
import LinkedList.BorrowedBookNode;
import StudentIDScanner.QRScanner;

import static Library.Dashboard_Inventory.getCurrentDate;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Dashboard_Inventory extends JFrame implements ActionListener, ItemListener {
    private TrackBooks trackBooks;
    
    LinkedList List = new LinkedList();
    BorrowedLinkedList BorrowedList = new BorrowedLinkedList();
    
    int bookborrownum;
    int borrowerCount = 1;
    
    SearchAndSort s = new SearchAndSort();

    String imgpath;
    private static final int borrowbooklimit = 2;
    public JTextArea scannedValue = new JTextArea();
    
    JLabel imageholder = new JLabel();
    JLabel bookimageholder = new JLabel();
    JLabel username = new JLabel("Reinius Dela Cruz");

    JPanel contentPanel = new JPanel(new CardLayout());
    JPanel header = new JPanel();
    JPanel header2 = new JPanel();
    JPanel menubar = new JPanel();
    JPanel bgPanel2 = new JPanel();
    JPanel bgPanel3 = new JPanel();
    JPanel inventoryP = new JPanel();

    JButton addmaterials = new JButton("Add Materials");
    JButton updatematerials = new JButton("Update Materials");
    JButton removematerials = new JButton("Remove Materials");
    
    
    JButton inventorybtn = new JButton("Materials Inventory");
    JButton borrowlistbtn = new JButton("View List");
    JButton borrowbookbtn = new JButton("Borrow book ( " + bookborrownum + " )");
    JButton returnbookbtn = new JButton("Return books");
    JButton renewbtn = new JButton("Re-new books");
    JButton trackbtn = new JButton("Track books");
    JButton reportbtn = new JButton("Report");
    JButton historybtn = new JButton("Transaction History");
    JButton logoutbtn = new JButton("LOG OUT");
    JButton refreshData;
    
    //add materials field and buttons
    JFrame addMaterials = new JFrame();
    JPanel panel = new JPanel();
    JTextField titleField = new JTextField();
    JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    JLabel imageborder = new JLabel();
    JComboBox<String> categ = new JComboBox<>();
    JComboBox<String> booktype = new JComboBox<>(new String[]{"Academic Books", "Non-Academic Books"});
    JTextField authorField = new JTextField();
    JTextField isbnField = new JTextField();
    JTextField priceField = new JTextField();
    JButton addButton = new JButton("Done");

    JFileChooser file = new JFileChooser();
    
    //table variables
    DefaultTableModel Inventorytable;
    JTable booktable;
    int row;
    
    //for adding the book into borrowedlist
    ArrayList<Object[]> borrowedBooksList = new ArrayList<>();
    String[] headertable = new String[]{"Book Image", "ISBN", "Book", "Category", "Title", "Author","Quantity","Borrowed Quantity","Price"};
    //book variables
    String imagebook, titlebook, bookauthor, bookType, categType, borrowerDetails;
    int isbnbook,quantitybook;
    double pricebook;
    int borrowedquantity;
    
    //Inventory fields
    JLabel borrow;
    JLabel noBooks;
    JLabel current;
    int totalBooks;
    int totalBorrowedBooks;
    int CurrentNumBooks;
    
    String callNumber;
    String borrowerNum;
    int quantity;
    String borrowedDate;
    String Borrower;
    String returnDate;  
    
    ArrayList<Object[]> borrowedBooksRecords = new ArrayList<>();
   

    public Dashboard_Inventory() {
        
        trackBooks = new TrackBooks();
        contentPanel.setLayout(new CardLayout());
        contentPanel.add(trackBooks, "TrackBooks");
        
        Inventorytable = new DefaultTableModel(headertable, 0);
        booktable = new JTable(Inventorytable);
        JScrollPane booktable_scroll = new JScrollPane(booktable);
        
        List.addItem("null", 20233, "Academic Books", "Cells and So", "Biology", "Mendeleev", 2,0, 200);
        List.addItem("null", 20234, "Academic Books", "Anformation System", "Haha", "Reinius", 2,0, 100);
        List.addItem("null", 20293, "Academic Books", "Znformation Technology", "Intro to Computing", "Aendeleev", 2,0, 200);
        displayData();
                //Displaying the book visual image
        booktable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = booktable.getSelectedRow();
                String imagepath = Inventorytable.getValueAt(row, 0).toString();

                if (!imagepath.equals("null") && !imagepath.isEmpty()) {
                    ImageIcon myimage = new ImageIcon(imagepath);
                    Image image = myimage.getImage();
                    Image image2 = image.getScaledInstance(bookimageholder.getWidth(), bookimageholder.getHeight(), Image.SCALE_SMOOTH);
                    myimage = new ImageIcon(image2);
                    bookimageholder.setIcon(myimage);
                } else {
                    bookimageholder.setIcon(null);
                }
            }
        });
        
        this.setLayout(null);
        this.setTitle("Dashboard");
        this.setSize(1366, 768);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel editProfileP = new JPanel();
        editProfileP.setLayout(null);
        editProfileP.setBackground(new Color(255, 255, 240));
        contentPanel.add(editProfileP, "EditProfile");

        //PARA SA PAGBUKAS IDK
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(null);
        welcomePanel.setBackground(new Color(250, 250, 250));

        JLabel welcomeTitle = new JLabel("Welcome to the Library System!", JLabel.CENTER);
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
        welcomeTitle.setForeground(new Color(54, 69, 79));
        welcomeTitle.setBounds(150, 100, 800, 100);
        welcomePanel.add(welcomeTitle);

        JLabel mema = new JLabel("Manage books, track borrowings, and much more with ease!", JLabel.CENTER);
        mema.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        mema.setForeground(new Color(54, 69, 79));
        mema.setBounds(330, 200, 450, 30);
        welcomePanel.add(mema);

        contentPanel.add(welcomePanel, "Welcome");
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, "Welcome");

        // 4TH PANEL      
        JPanel bgpanel4 = new JPanel();
        bgpanel4.setBounds(20, 20, 750, 580);
        bgpanel4.setBackground(new Color(230, 230, 220));
        bgpanel4.setLayout(null);
        inventoryP.add(bgpanel4);
        bgpanel4.add(booktable_scroll);
        booktable_scroll.setBounds(0, 120, 750, 460);
        
        setButton(inventorybtn, 300);
        setButton(returnbookbtn, 340);
        setButton(renewbtn, 380);
        setButton(trackbtn, 420);
        setButton(reportbtn, 460);
        setButton(historybtn, 500);
        setButton(logoutbtn, 540);
        
        // INV BUTTONS 
        addmaterials.setBounds(20, 610, 220, 30);
        layoutngBtn(addmaterials);
        inventoryP.add(addmaterials);
        addmaterials.addActionListener(this);

        updatematerials.setBounds(290, 610, 220, 30);
        layoutngBtn(updatematerials);
        inventoryP.add(updatematerials);
        updatematerials.addActionListener(this);

        removematerials.setBounds(550, 610, 220, 30);
        layoutngBtn(removematerials);
        inventoryP.add(removematerials);
        removematerials.addActionListener(this);
        
        borrowbookbtn.setBounds(790, 520, 250, 30);
        layoutngBtn(borrowbookbtn);
        inventoryP.add(borrowbookbtn);
        borrowbookbtn.addActionListener(this);
        
        borrowlistbtn.setBounds(790, 570, 250, 30);
        layoutngBtn(borrowlistbtn);
        inventoryP.add(borrowlistbtn);
        borrowlistbtn.addActionListener(this);

        // SA BABA 
        noBooks = new JLabel(" Total No of Books: " + totalBooks);
        noBooks.setFont(new Font("Arial", Font.PLAIN, 14));
        noBooks.setBounds(60, 65, 195, 30);
        noBooks.setForeground(new Color(34, 139, 34));
        noBooks.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        noBooks.setOpaque(true);
        noBooks.setBackground(Color.WHITE);
        bgpanel4.add(noBooks);

        borrow = new JLabel(" Total Borrowed Books: " + totalBorrowedBooks);
        borrow.setFont(new Font("Arial", Font.PLAIN, 14));
        borrow.setBounds(265, 65, 195, 30);
        borrow.setForeground(new Color(34, 139, 34));
        borrow.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        borrow.setOpaque(true);
        borrow.setBackground(Color.WHITE);
        bgpanel4.add(borrow);

        current = new JLabel(" Current No. of Books: " + totalBooks);
        current.setFont(new Font("Arial", Font.PLAIN, 14));
        current.setBounds(470, 65, 210, 30);
        current.setForeground(new Color(34, 139, 34));
        current.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        current.setOpaque(true);
        current.setBackground(Color.WHITE);
        bgpanel4.add(current);

        // EFFECT KUNO
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JButton sourceButton = (JButton) evt.getSource();
                sourceButton.setBackground(new Color(230, 255, 230)); // Light green on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JButton sourceButton = (JButton) evt.getSource();
                sourceButton.setBackground(Color.WHITE); // Revert to white background
            }
        };
        
        // SEARCH FIELD 
        JTextField searchField = new JTextField();
        searchField.setBounds(60, 20, 400, 30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        searchField.setOpaque(true);
        bgpanel4.add(searchField);
        
        //Refresh Button
        ImageIcon refreshIcon = new ImageIcon("C:\\Users\\Annenixole\\Downloads\\Library System Images\\refresh.png");
        Image getIcon = refreshIcon.getImage();
        Image setIcon = getIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
        refreshData = new JButton();
        refreshData.setIcon(new ImageIcon(setIcon));
        refreshData.setBounds(20, 20, 30, 30);
        refreshData.setBackground(Color.WHITE);
        refreshData.setForeground(new Color(76, 175, 80)); // Green text color
        refreshData.setFont(new Font("Segoi UI", Font.BOLD, 14));
        refreshData.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        refreshData.setBorderPainted(true);
        refreshData.setFocusPainted(false);
        refreshData.setOpaque(true);
        bgpanel4.add(refreshData);
        refreshData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                displayData();
            }
            
        });

        // SEARCH SAKA SORT BTN
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(470, 20, 100, 30);
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(new Color(76, 175, 80)); // Green text color
        searchButton.setFont(new Font("Segoi UI", Font.BOLD, 14));
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        searchButton.setBorderPainted(true);
        searchButton.setFocusPainted(false);
        searchButton.setOpaque(true);
        bgpanel4.add(searchButton);

        searchButton.addMouseListener(hoverEffect);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                s.linearSearch(Inventorytable, query);
            }

        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String query = searchField.getText();
                    s.linearSearch(Inventorytable, query);
                }
            }
        });

        JComboBox<String> sortComboBox;
        String[] sortOptions = {"Sort by Title", "Sort by Author"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setBounds(580, 20, 100, 30);
        sortComboBox.setBackground(Color.WHITE);
        sortComboBox.setForeground(new Color(76, 175, 80));
        sortComboBox.setFont(new Font("Segoi UI", Font.BOLD, 12));
        sortComboBox.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        sortComboBox.setOpaque(true);
        bgpanel4.add(sortComboBox);

        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortComboBox.getSelectedItem();
                if ("Sort by Title".equals(selectedOption)) {
                    s.sortByTitle(Inventorytable); // Sort by Title
                } else if ("Sort by Author".equals(selectedOption)) {
                    s.sortByAuthor(Inventorytable); // Sort by Author
                }
            }
        });
        
        imageholder.setBounds(70, 100, 130, 130);
        imageholder.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.add(imageholder);

        username.setBounds(60, 250, 200, 25);
        username.setFont(new Font("Arial", Font.BOLD, 17));
        username.setForeground(Color.WHITE);
        this.add(username);

        header.setBackground(new Color(46, 125, 50));
        header.setBounds(0, 0, 1366, 50);
        this.add(header);

        header2.setBackground(new Color(255, 235, 59));
        header2.setBounds(0, 50, 1366, 10);
        this.add(header2);

        menubar.setBackground(new Color(46, 125, 50));
        menubar.setBounds(0, 60, 270, 700);
        menubar.setLayout(null);
        this.add(menubar);

        contentPanel.setBounds(270, 60, 1080, 670);
        contentPanel.setBackground(new Color(250, 250, 240));
        this.add(contentPanel);

        contentPanel.add(inventoryP, "Inventory");
        inventoryP.setLayout(null);

        bgPanel2.setLayout(null);
        bgPanel2.setBounds(790, 20, 250, 220);
        bgPanel2.setBackground(new Color(245, 245, 245));
        bgPanel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        bgPanel2.add(bookimageholder);
        bookimageholder.setBounds(5, 5, 240, 210);
        bookimageholder.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        inventoryP.add(bgPanel2);

        bgPanel3.setLayout(null);
        bgPanel3.setBackground(new Color(245, 245, 245));
        bgPanel3.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        Font labelFont = new Font("Segoe Ui", Font.BOLD, 14);

        // RETURN BOOKS NA MENU
        JPanel returnBooksPnl = new JPanel();
        returnBooksPnl.setLayout(null);
        returnBooksPnl.setBackground(new Color(250, 250, 240));
        contentPanel.add(returnBooksPnl, "ReturnBooks");

        JPanel returnPanel1 = new JPanel();
        returnPanel1.setBounds(20, 20, 750, 660);
        returnPanel1.setBackground(new Color(230, 230, 220));
        returnPanel1.setLayout(null);
        returnBooksPnl.add(returnPanel1);

        JPanel returnPanel2 = new JPanel();
        returnPanel2.setLayout(null);
        returnPanel2.setBounds(790, 20, 250, 230);
        returnPanel2.setBackground(new Color(230, 230, 230));
        returnBooksPnl.add(returnPanel2);

        JPanel returnPanel3 = new JPanel();
        returnPanel3.setLayout(null);
        returnPanel3.setBounds(790, 280, 250, 240);
        returnPanel3.setBackground(new Color(230, 230, 230));
        returnBooksPnl.add(returnPanel3);

        JButton code = new JButton("Code");
        code.setBounds(50, 30, 180, 30);
        code.setBackground(Color.WHITE);
        code.setForeground(new Color(76, 175, 80));
        code.setFont(new Font("Segoi UI", Font.BOLD, 14));
        code.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        code.setBorderPainted(true);
        code.setFocusPainted(false);
        code.setOpaque(true);
        returnPanel1.add(code);

        JButton enter = new JButton("Enter");
        enter.setBounds(250, 30, 120, 30);
        enter.setBackground(Color.WHITE);
        enter.setForeground(new Color(76, 175, 80));
        enter.setFont(new Font("Segoi UI", Font.BOLD, 14));
        enter.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        enter.setBorderPainted(true);
        enter.setFocusPainted(false);
        enter.setOpaque(true);
        returnPanel1.add(enter);

        JPanel returnPanel4 = new JPanel();
        returnPanel4.setLayout(null);
        returnPanel4.setBounds(50, 70, 650, 555);
        returnPanel4.setBackground(new Color(250, 250, 240));
        returnPanel1.add(returnPanel4);

        JLabel date = new JLabel("Date Returned:");
        date.setBounds(70, 22, 120, 20);
        date.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        date.setForeground(new Color(54, 69, 79));
        returnPanel4.add(date);

        JTextField dateField = new JTextField();
        dateField.setBounds(200, 20, 170, 25);
        dateField.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        dateField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        returnPanel4.add(dateField);

        // DAMAGES NA CHECKBOX 
        JPanel damagesPnl = new JPanel();
        damagesPnl.setBounds(70, 70, 505, 310);
        damagesPnl.setBackground(new Color(250, 250, 240));
        damagesPnl.setLayout(null);
        damagesPnl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel damagelbl = new JLabel(" Damages:");
        damagelbl.setBounds(10, 10, 150, 22);
        damagelbl.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        damagelbl.setForeground(new Color(54, 69, 79));
        damagesPnl.add(damagelbl);

        JCheckBox tornPages = new JCheckBox("Torn Pages");
        tornPages.setBounds(10, 40, 200, 25);
        tornPages.setBackground(new Color(250, 250, 240));
        tornPages.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(tornPages);

        JCheckBox waterDamage = new JCheckBox("Water Damage");
        waterDamage.setBounds(10, 70, 200, 25);
        waterDamage.setBackground(new Color(250, 250, 240));
        waterDamage.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(waterDamage);

        JCheckBox missingyou = new JCheckBox("Missing Pages");
        missingyou.setBounds(10, 100, 200, 25);
        missingyou.setBackground(new Color(250, 250, 240));
        missingyou.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(missingyou);

        JCheckBox writing = new JCheckBox("Writing or Markings on Pages");
        writing.setBounds(10, 130, 250, 25);
        writing.setBackground(new Color(250, 250, 240));
        writing.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(writing);

        JCheckBox binding = new JCheckBox("Loose Binding");
        binding.setBounds(10, 160, 200, 25);
        binding.setBackground(new Color(250, 250, 240));
        binding.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(binding);

        JCheckBox cover = new JCheckBox("Missing Cover");
        cover.setBounds(10, 190, 200, 25);
        cover.setBackground(new Color(250, 250, 240));
        cover.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(cover);

        JCheckBox foodStains = new JCheckBox("Food or Drink Stains");
        foodStains.setBounds(10, 220, 200, 25);
        foodStains.setBackground(new Color(250, 250, 240));
        foodStains.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        damagesPnl.add(foodStains);

        JLabel explainmo = new JLabel("Explain Further (if any):");
        explainmo.setBounds(15, 255, 200, 20);
        explainmo.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        explainmo.setForeground(new Color(54, 69, 79));
        damagesPnl.add(explainmo);

        JTextField explanationField = new JTextField();
        explanationField.setBounds(180, 255, 220, 23);
        explanationField.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        explanationField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        damagesPnl.add(explanationField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(410, 255, 80, 23);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(new Color(76, 175, 80));
        saveBtn.setFont(new Font("Segoi UI", Font.BOLD, 12));
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        saveBtn.setBorderPainted(true);
        saveBtn.setFocusPainted(false);
        saveBtn.setOpaque(true);
        damagesPnl.add(saveBtn);

        JLabel fee = new JLabel("Late Fee:");
        fee.setBounds(75, 400, 100, 20);
        fee.setFont(new Font("Segoi UI", Font.PLAIN, 15));
        fee.setForeground(new Color(54, 69, 79));
        returnPanel4.add(fee);

        JLabel fee2 = new JLabel("Damage Fee:");
        fee2.setBounds(75, 430, 100, 20);
        fee2.setFont(new Font("Segoi UI", Font.PLAIN, 15));
        fee2.setForeground(new Color(54, 69, 79));
        returnPanel4.add(fee2);

        JLabel total = new JLabel("Total Fee:");
        total.setBounds(460, 480, 100, 20);
        total.setFont(new Font("Segoi UI", Font.PLAIN, 15));
        total.setForeground(new Color(54, 69, 79));
        returnPanel4.add(total);

        returnPanel4.add(damagesPnl);

        // SA INVENTORY PANEL 3 NA LABELS 
        JPanel renewPnl = new JPanel();
        renewPnl.setBackground(new Color(250, 250, 240));
        renewPnl.setLayout(null);
        contentPanel.add(renewPnl, "RenewBooks");

        JPanel reportPnl = new JPanel();
        reportPnl.setBackground(new Color(250, 250, 240));
        reportPnl.setLayout(null);
        contentPanel.add(reportPnl, "Report");
        
        ////////////////////////////////////////////////////////////Displaying the text of book details
        JLabel titleLbl = new JLabel("Title:");
        titleLbl.setBounds(10, 10, 200, 15);
        titleLbl.setFont(labelFont);
        bgPanel3.add(titleLbl);
        
        JLabel titleVal = new JLabel();
        titleVal.setBounds(50, 10, 300, 15);
        titleVal.setFont(labelFont);
        bgPanel3.add(titleVal);

        JLabel authorLbl = new JLabel("Author:");
        authorLbl.setBounds(10, 38, 200, 15);
        authorLbl.setFont(labelFont);
        bgPanel3.add(authorLbl);
        
        JLabel authorVal = new JLabel();
        authorVal.setBounds(70, 38, 300, 15);
        authorVal.setFont(labelFont);
        bgPanel3.add(authorVal);

        JLabel categoryLbl = new JLabel("Category:");
        categoryLbl.setBounds(10, 63, 200, 15);
        categoryLbl.setFont(labelFont);
        bgPanel3.add(categoryLbl);
        
        JLabel categVal = new JLabel();
        categVal.setBounds(80, 63, 200, 15);
        categVal.setFont(labelFont);
        bgPanel3.add(categVal);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(10, 88, 200, 15);
        priceLbl.setFont(labelFont);
        bgPanel3.add(priceLbl);
        
        JLabel priceVal = new JLabel();
        priceVal.setBounds(60, 88, 200, 15);
        priceVal.setFont(labelFont);
        bgPanel3.add(priceVal);

        JLabel numberLbl = new JLabel("Number of Books:");
        numberLbl.setBounds(10, 113, 200, 15);
        numberLbl.setFont(labelFont);
        bgPanel3.add(numberLbl);
        
        JLabel numberVal = new JLabel();
        numberVal.setBounds(140, 113, 200, 15);
        numberVal.setFont(labelFont);
        bgPanel3.add(numberVal);

        JLabel borrowedLbl = new JLabel("Borrowed:");
        borrowedLbl.setBounds(10, 138, 200, 15);
        borrowedLbl.setFont(labelFont);
        bgPanel3.add(borrowedLbl);
        
        JLabel borrowedVal = new JLabel();
        borrowedVal.setBounds(90, 138, 200, 15);
        borrowedVal.setFont(labelFont);
        bgPanel3.add(borrowedVal);

        JLabel remainingLbl = new JLabel("Remaining Stock:");
        remainingLbl.setBounds(10, 163, 200, 17);
        remainingLbl.setFont(labelFont);
        bgPanel3.add(remainingLbl);
        
        JLabel remainingVal = new JLabel();
        remainingVal.setBounds(135, 163, 200, 17);
        remainingVal.setFont(labelFont);
        bgPanel3.add(remainingVal);

        bgPanel3.setBounds(790, 280, 250, 200);
        inventoryP.add(bgPanel3);
        
        booktable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = booktable.getSelectedRow();

                if (selectedRow != -1) {
                    String title = Inventorytable.getValueAt(selectedRow, 4).toString();
                    titleVal.setText(title);
                    String author = Inventorytable.getValueAt(selectedRow, 5).toString();
                    authorVal.setText(author);
                    String category = Inventorytable.getValueAt(selectedRow, 3).toString();
                    categVal.setText(category);
                    String price = Inventorytable.getValueAt(selectedRow, 8).toString();
                    priceVal.setText(price);
                    int remainingBook = Integer.parseInt(Inventorytable.getValueAt(selectedRow, 6).toString());
                    remainingVal.setText(String.valueOf(remainingBook));
                    int borrowedBook = Integer.parseInt(Inventorytable.getValueAt(selectedRow, 7).toString());
                    borrowedVal.setText(String.valueOf(borrowedBook));
                    int originalQuantity = remainingBook + borrowedBook;
                    numberVal.setText(String.valueOf(originalQuantity));
                }
            }
        });
    }

    public void setButton(JButton button, int y) {
        button.setBounds(0, y, 270, 40);
        button.setBackground(new Color(56, 142, 60));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener(this);

        // HOVER EFFECT SA MENU BAR
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(76, 175, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!button.getBackground().equals(new Color(255, 193, 7))) { // Check if not active
                    button.setBackground(new Color(56, 142, 60));
                }
            }
        });

        menubar.add(button);

        imageholder.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e
            ) {
                Profile();
            }
        }
        );

        username.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e
            ) {
                Profile();
            }
        }
        );
    }

    private void Profile() {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, "EditProfile");
        highlightActiveButton(null);
    }

    private void highlightActiveButton(JButton activeButton) {
        JButton[] buttons = {inventorybtn, returnbookbtn, renewbtn, trackbtn, reportbtn, historybtn, logoutbtn};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(56, 142, 60));
        }

        activeButton.setBackground(new Color(255, 193, 7)); // Yellow
    }

    public void layoutngBtn(JButton button) {
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
    }

    public static void main(String[] args) {
        Dashboard_Inventory d = new Dashboard_Inventory();
        d.setVisible(true);
    }
    
    //METHODSS
    
    public int TotalBooks() {
        totalBooks = 0; // Reset totalBooks to zero
        for (int i = 0; i < Inventorytable.getRowCount(); i++) {
            int quantity = (int) Inventorytable.getValueAt(i, 6); // Assuming column 6 is the quantity
            totalBooks += quantity;
        }
        return totalBooks;
    }
    
    public int TotalBorrowedBooks() {
        totalBorrowedBooks = 0; // Initialize the total borrowed books count
        for (int i = 0; i < Inventorytable.getRowCount(); i++) {
            int borrowedQuantity = (int) Inventorytable.getValueAt(i, 7); // Assuming column 7 is the borrowed quantity
            totalBorrowedBooks += borrowedQuantity; // Sum the borrowed quantities
        }
        return totalBorrowedBooks; // Return the total borrowed books count
    }
    
    public int CurrentNumBooks(){
        CurrentNumBooks = 0;
        for(int i = 0; i < Inventorytable.getRowCount(); i++){
            int CurrentTotal = (int) Inventorytable.getValueAt(i, 6);
            CurrentNumBooks += CurrentTotal;
        }
        return CurrentNumBooks;
    }

    public void borrowbtn() {
        row = booktable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select a book to borrow", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int quantityAvailable = (int) Inventorytable.getValueAt(row, 6); // Quantity available
        if (quantityAvailable <= 0) {
            JOptionPane.showMessageDialog(rootPane, "No available copies to borrow.", "Borrow Limit Reached", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (borrowedBooksList.size() >= borrowbooklimit) {
            JOptionPane.showMessageDialog(rootPane,
                    "Borrowed book limit exceeded! You can only borrow up to " + borrowbooklimit + " books.",
                    "Limit Reached",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Add book to borrowedBooksList
        Object[] borrowedBook = new Object[9];
        for (int i = 0; i < 9; i++) {
            borrowedBook[i] = Inventorytable.getValueAt(row, i);
        }
        borrowedBooksList.add(borrowedBook);
        

        // Update Inventorytable for the borrowed book
        int currentQuantity = (int) Inventorytable.getValueAt(row, 6);
        Inventorytable.setValueAt(currentQuantity - 1, row, 6);

        bookborrownum++;
        borrowbookbtn.setText("Borrow book (" + bookborrownum + ")");
    }

    
    public void borrowlistbtn(){
        JFrame borrowListfrm = new JFrame();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // Using null layout for absolute positioning
        borrowListfrm.setUndecorated(true);
        mainPanel.setBackground(Color.white);

        // Title label
        JLabel title = new JLabel("Borrow Details");
        title.setBounds(50, 30, 300, 30);
        title.setFont(new Font("Segoi UI", Font.BOLD, 18));
        mainPanel.add(title);

        // Number of books label
        JLabel books = new JLabel("Number of Books:" + bookborrownum);
        books.setBounds(50, 90, 200, 20);
        books.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        mainPanel.add(books);

        // Date period label
        JLabel period = new JLabel("Date:");
        period.setBounds(380, 127, 200, 20);
        period.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        mainPanel.add(period);

        // Date borrowed text field
        JTextField dateborrowed = new JTextField(getCurrentDate(0));
        dateborrowed.setBounds(380, 158, 250, 28);
        dateborrowed.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        mainPanel.add(dateborrowed);

        // Expected return date label
        JLabel expectreturn = new JLabel("*Return Date Expected " + "(" + getCurrentDate(1) + ")");
        expectreturn.setBounds(380, 188, 300, 20);
        expectreturn.setFont(new Font("Arial", Font.PLAIN, 10));
        mainPanel.add(expectreturn);

        // Table for borrow list
        DefaultTableModel borrowListModel = new DefaultTableModel(headertable, 0);
        JTable borrowListTable = new JTable(borrowListModel);
        JScrollPane BorrowscrollPane = new JScrollPane(borrowListTable);
        BorrowscrollPane.setBounds(0, 0, 300, 400);
        
        //hiding the column of table in borrowing list table
        borrowListTable.getColumnModel().removeColumn(borrowListTable.getColumnModel().getColumn(0));
        borrowListTable.getColumnModel().removeColumn(borrowListTable.getColumnModel().getColumn(7));
        borrowListTable.getColumnModel().removeColumn(borrowListTable.getColumnModel().getColumn(6));
        borrowListTable.getColumnModel().removeColumn(borrowListTable.getColumnModel().getColumn(5));

        // Panel for holding the table
        JPanel tableholder = new JPanel();
        tableholder.setBounds(50, 130, 300, 400);
        tableholder.setBackground(Color.WHITE);
        tableholder.setLayout(null);
        tableholder.add(BorrowscrollPane);
        mainPanel.add(tableholder);

        // Separator line
        JSeparator line = new JSeparator();
        line.setBounds(50, 75, 580, 2);
        line.setForeground(Color.GRAY);
        mainPanel.add(line);

        // Scan button
        JButton scanbtn = new JButton("Scan ID");
        scanbtn.setBounds(380, 225, 250, 30);
        scanbtn.setBackground(Color.WHITE);
        scanbtn.setForeground(new Color(76, 175, 80));
        scanbtn.setFont(new Font("Segoi UI", Font.BOLD, 14));
        scanbtn.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        scanbtn.setFocusPainted(false);
        mainPanel.add(scanbtn);

        // Confirm button
        JButton confirmbtn = new JButton("Confirm");
        confirmbtn.setBounds(380, 455, 250, 30);
        confirmbtn.setBackground(Color.WHITE);
        confirmbtn.setForeground(new Color(76, 175, 80));
        confirmbtn.setFont(new Font("Segoi UI", Font.BOLD, 14));
        confirmbtn.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        confirmbtn.setFocusPainted(false);
        mainPanel.add(confirmbtn);

        // Cancel button
        JButton cancelbtn = new JButton("Cancel");
        cancelbtn.setBounds(380, 490, 250, 30);
        cancelbtn.setBackground(Color.WHITE);
        cancelbtn.setForeground(new Color(76, 175, 80));
        cancelbtn.setFont(new Font("Segoi UI", Font.BOLD, 14));
        cancelbtn.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        cancelbtn.setFocusPainted(false);
        mainPanel.add(cancelbtn);

        // Remove to list button
        JButton removelistbtn = new JButton("Remove to list");
        removelistbtn.setBounds(50, 540, 300, 30);
        removelistbtn.setBackground(Color.WHITE);
        removelistbtn.setForeground(new Color(76, 175, 80));
        removelistbtn.setFont(new Font("Segoi UI", Font.BOLD, 14));
        removelistbtn.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 1));
        removelistbtn.setFocusPainted(false);
        mainPanel.add(removelistbtn);
        
        scannedValue.setBounds(380, 270, 250, 170);
        scannedValue.setBackground(Color.WHITE);
        scannedValue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scannedValue.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        scannedValue.setEditable(false); // Make it non-editable if it's just for displaying scanned data
        scannedValue.setLineWrap(true);
        scannedValue.setWrapStyleWord(true);
        mainPanel.add(scannedValue);

        // Set up JFrame
        borrowListfrm.setContentPane(mainPanel);
        borrowListfrm.setVisible(true);
        borrowListfrm.setSize(700, 700);
        borrowListfrm.setTitle("Borrow Book List");
        borrowListfrm.setLocationRelativeTo(null);
        borrowListfrm.setResizable(false);
        borrowListfrm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


            //scanner visibility
            scanbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    QRScanner IDscan = new QRScanner(Dashboard_Inventory.this);
                    IDscan.setVisible(true);
                }

            });
            
        confirmbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (borrowListModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Cannot Borrowed Book", "No Selection", JOptionPane.WARNING_MESSAGE);
                } else {
                    String[] callNumbers = new String[borrowListModel.getRowCount()];
                    boolean validInput = true;

                    // Collect Call Numbers for each book
                    for (int i = 0; i < borrowListModel.getRowCount(); i++) {
                        String title = borrowListModel.getValueAt(i, 4).toString();
                        callNumber = JOptionPane.showInputDialog(rootPane, "Call Number", title, JOptionPane.PLAIN_MESSAGE);

                        if (callNumber == null || callNumber.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(rootPane, "Call Number is required for all books.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            validInput = false;
                            break;
                        }
                        callNumbers[i] = callNumber;
                    }

                    if (!validInput) {
                        return; // Exit if input was invalid
                    }

                    // Capture borrower details
                    Borrower = scannedValue.getText();
                    String borrowerNum = generateNumber();
                    int quantity = borrowListModel.getRowCount();
                    String borrowedDate = dateborrowed.getText();
                    String returnDate = getCurrentDate(1);
                    
                    // Iterate through borrow list and update the Inventorytable & booksModel
                    for (int i = 0; i < borrowListModel.getRowCount(); i++) {
                        String isbn = borrowListModel.getValueAt(i, 1).toString();
                        String title = borrowListModel.getValueAt(i, 4).toString();
                        String author = borrowListModel.getValueAt(i, 5).toString();

                        // Update Inventorytable quantities
                        for (int row = 0; row < Inventorytable.getRowCount(); row++) {
                            String inventoryisbn = Inventorytable.getValueAt(row, 1).toString();
                            if (isbn.equals(inventoryisbn)) {
                                int currentBorrowed = (int) Inventorytable.getValueAt(row, 7);
                                Inventorytable.setValueAt(currentBorrowed + 1, row, 7);
                            }
                        }
                        
                        // Add book details to booksModel for the current borrower only
                        Object[] bookDetails = {borrowerNum, callNumbers[i], isbn, title, author};
                        trackBooks.booksModel.addRow(bookDetails);

                        // Borrowed list logic
                        BorrowedList.borrowBook(callNumbers[i], isbn, 1, borrowerNum, borrowedDate, returnDate, Borrower, title, author);
                    }

                    // Add transaction record to borrowedModel
                    Object[] transactionRecord = {borrowerNum, quantity, borrowedDate, returnDate, Borrower};
                    trackBooks.borrowedModel.addRow(transactionRecord);

                    JOptionPane.showMessageDialog(rootPane, "Successfully Borrowed! " + borrowedDate + " " + quantity);

                    // Clear borrow list and reset counters
                    borrowListModel.setRowCount(0);
                    borrowedBooksList.clear();
                    bookborrownum = 0;
                    borrowbookbtn.setText("Borrow book (0)");

                    int totalBorrowedBooks = TotalBorrowedBooks();
                    borrow.setText(" Total Borrowed Books: " + totalBorrowedBooks);

                    int totalCurrentBooks = CurrentNumBooks();
                    current.setText(" Current No. of Books: " + totalCurrentBooks);

                    borrowListfrm.dispose(); // Close the borrow list frame
                }
            }
        });


            //Removing some books to the borrow list
            removelistbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        cancelbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cancel = JOptionPane.showConfirmDialog(rootPane, "Cancel the book?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (cancel == JOptionPane.YES_OPTION) {
                    // Revert inventory changes for all books in borrowListModel
                    for (int i = 0; i < borrowListModel.getRowCount(); i++) {
                        String isbn = borrowListModel.getValueAt(i, 1).toString(); // Get the ISBN

                        // Revert inventory table
                        for (int row = 0; row < Inventorytable.getRowCount(); row++) {
                            String inventoryisbn = Inventorytable.getValueAt(row, 1).toString();
                            if (isbn.equals(inventoryisbn)) {
                                int currentQuantity = Integer.parseInt(Inventorytable.getValueAt(row, 6).toString());

                                Inventorytable.setValueAt(currentQuantity + 1, row, 6); // Restore quantity
                                break;
                            }
                        }
                    }
                    // Clear the borrow list model and internal list
                    borrowListModel.setRowCount(0);
                    borrowedBooksList.clear();
                    bookborrownum = 0; // Reset borrow count
                    borrowbookbtn.setText("Borrow book (0)");
                    borrowListfrm.dispose(); // Close the borrow list frame
                }
            }
        });

        
        // Borrowing book function
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Object[] borrowedBook = borrowedBooksList.get(i);
            borrowListModel.addRow(borrowedBook);
        }
        if (borrowedBooksList.isEmpty()) {
            showMessageDialog(borrowListfrm, "No books have been borrowed yet!", "Empty Borrow List", INFORMATION_MESSAGE);
        }
    }
    
    public String generateNumber() {
        return String.format("Stud%04d", borrowerCount++);
    }
    
    public static String getCurrentDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now().plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        return currentDate.format(formatter);
    } // to display the current date
    
    public void InventoryFormFrame() {
        addMaterials.setVisible(true);
        addMaterials.setSize(800, 600);
        addMaterials.setTitle("Add Materials");
        addMaterials.setLocationRelativeTo(null);
        addMaterials.setResizable(false);
        addMaterials.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        addMaterials.add(panel);

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(300, 50, 100, 25);
        titleLabel.setFont(labelFont);
        titleField.setBounds(400, 50, 250, 30);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(300, 90, 100, 25);
        authorLabel.setFont(labelFont);
        authorField.setBounds(400, 90, 250, 30);

        JLabel genreLabel = new JLabel("Literature:");
        genreLabel.setBounds(300, 130, 100, 25);
        genreLabel.setFont(labelFont);
        booktype.setBounds(400, 130, 250, 30);

        JLabel categLabel = new JLabel("Category:");
        categLabel.setBounds(300, 170, 100, 25);
        categLabel.setFont(labelFont);
        categ.setBounds(400, 170, 250, 30);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(300, 210, 100, 25);
        isbnLabel.setFont(labelFont);
        isbnField.setBounds(400, 210, 250, 30);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(300, 250, 100, 25);
        quantityLabel.setFont(labelFont);
        quantitySpinner.setBounds(400, 250, 250, 30);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(300, 290, 100, 25);
        priceLabel.setFont(labelFont);
        priceField.setBounds(400, 290, 250, 30);

        JButton addVisual = new JButton("Upload Visual");
        addVisual.setBounds(50, 260, 200, 40);
        addVisual.setFont(new Font("SansSerif", Font.BOLD, 14));
        addVisual.setBackground(new Color(46, 125, 50));
        addVisual.setForeground(Color.WHITE);

        addButton.setBackground(new Color(46, 125, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        addButton.setBounds(450, 350, 150, 30);
        panel.add(addButton);

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(genreLabel);
        panel.add(booktype);
        panel.add(isbnLabel);
        panel.add(isbnField);
        panel.add(quantityLabel);
        panel.add(quantitySpinner);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(categLabel);
        panel.add(categ);

        booktype.setSelectedItem(null);
        booktype.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (booktype.getSelectedItem().equals("Academic Books")) {
                        categ.removeAllItems();
                        categ.setSelectedItem(null);
                        categ.addItem("Business and Management");
                        categ.addItem("Computing Literature");
                        categ.addItem("Humanities");
                        categ.addItem("Engineering Literature");
                        categ.addItem("Social Science");
                        categ.addItem("Finance");

                    } else if (booktype.getSelectedItem().equals("Non-Academic Books")) {
                        categ.removeAllItems();
                        categ.setSelectedItem(null);
                        categ.addItem("Horror");
                        categ.addItem("Mystery");
                        categ.addItem("Thriller");
                        categ.addItem("Science Fiction");
                        categ.addItem("Historical Fiction");
                        categ.addItem("Graphical Novel");
                    }
                } catch (Exception a) {
                    System.out.println(a);
                }
            }

        });

        file = new JFileChooser();
        //Adding the book picture
        panel.add(addVisual);
        addVisual.setBounds(50, 250, 200, 25);
        panel.add(imageborder);
        imageborder.setBounds(50, 50, 200, 200);
        imageborder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        addVisual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file.setCurrentDirectory(new File("C:\\Users\\Annenixole\\Downloads"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "png", "jpeg");
                file.setFileFilter(filter);
                int result = file.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = file.getSelectedFile();
                    imgpath = selectedFile.getAbsolutePath();

                    ImageIcon myimage = new ImageIcon(imgpath);
                    Image image = myimage.getImage();
                    Image image2 = image.getScaledInstance(imageborder.getWidth(), imageborder.getHeight(), Image.SCALE_SMOOTH);
                    myimage = new ImageIcon(image2);
                    imageborder.setIcon(myimage);
                } else {
                    imgpath = null; // Clear the stored image path
                    imageborder.setIcon(null);

                }
            }
        });
    }

    public void addMaterialsFrame() {
        InventoryFormFrame();
        clearActionListeners(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                imagebook = imgpath;
                isbnbook = Integer.parseInt(isbnField.getText());
                bookType = booktype.getSelectedItem().toString();
                categType = categ.getSelectedItem().toString();
                titlebook = titleField.getText();
                bookauthor = authorField.getText();
                quantitybook = (int) quantitySpinner.getValue();
                pricebook = Double.parseDouble(priceField.getText());

                // Add to the LinkedList
                List.addItem(imagebook, isbnbook, bookType, categType, titlebook, bookauthor,quantitybook,borrowedquantity,pricebook);
                displayData();
                
                    // Update the total books label
                    noBooks.setText("Total Books: " + totalBooks);
                    
                clearField();
            }catch(NumberFormatException numberexcept){
              JOptionPane.showMessageDialog(rootPane,"Please enter valid numeric values for Book code, ISBN, and Price", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
          }
        });
    }
    
    private void clearActionListeners(JButton button) {
        ActionListener[] listeners = button.getActionListeners();
        int i = 0;
        while (i < listeners.length) {
            button.removeActionListener(listeners[i]);
            i++;
        }
    }
    
    public void deleteConfirm() {
        int selectedrow = booktable.getSelectedRow();
        if (selectedrow >= 0) {
            int conf = JOptionPane.showConfirmDialog(rootPane, "Are you sure want to delete?", "Confirm Deletion", YES_NO_OPTION);
            if (conf == 0) {
                int quantityToRemove = (int) Inventorytable.getValueAt(selectedrow, 6); 
                List.deleteItemAt(selectedrow);
                
                totalBooks -= quantityToRemove; // Subtract the quantity of the removed book
                noBooks.setText("Total No of Books: " + totalBooks);
                
                displayData();

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Click the row of the book you want to remove");
        }
    }
    
    public void clearField() {
        titleField.requestFocus();
        titleField.setText("");
        isbnField.setText("");
        authorField.setText("");
        priceField.setText("");
        booktype.setSelectedItem(null);
        categ.setSelectedItem(null);
        quantitySpinner.setValue(1);
        imageborder.setIcon(null);
    }
    
    public void displayData() {
        //para di maulit pagaadd
        Inventorytable.setRowCount(0);
        BookInventory current = List.head;

        while (current != null) {

            Inventorytable.addRow(new Object[]{
                new ImageIcon(current.imagebook),
                current.isbnbook,
                current.booktype,
                current.categtype,
                current.titlebook,
                current.bookauthor,
                current.quantitybook,
                current.borrowedquantity,
                current.pricebook
            });

            current = current.next;
        }
        // Update the total books label
        totalBooks = TotalBooks();
        
        System.out.println(imagebook + " " + isbnbook + " " + bookType + " " + categType + " " + titlebook + " " + bookauthor + " " +quantitybook +" "+ borrowedquantity+" "+pricebook);
    }
    public void UpdateMaterialsFrame() {
        int selectedrow = booktable.getSelectedRow();

        if (selectedrow < 0) {
            JOptionPane.showMessageDialog(rootPane, "Please select a row to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BookInventory current = List.head;
        int search = 0;
        while (current != null && search < selectedrow) {
            current = current.next;
            search++;
        }
        if (current != null) {
            InventoryFormFrame();

            titleField.setText(current.titlebook);
            isbnField.setText(String.valueOf(current.isbnbook));
            authorField.setText(current.bookauthor);
            priceField.setText(String.valueOf(current.pricebook));
            booktype.setSelectedItem(current.booktype);
            categ.setSelectedItem(current.categtype);
            quantitySpinner.setValue(current.quantitybook);
            if (imagebook != null) {
                imgpath = current.imagebook; // Use the current image path
                ImageIcon myimage = new ImageIcon(current.imagebook);
                Image image = myimage.getImage();
                Image scaledImage = image.getScaledInstance(imageborder.getWidth(), imageborder.getHeight(), Image.SCALE_SMOOTH);
                imageborder.setIcon(new ImageIcon(scaledImage));
            }
        }
        clearActionListeners(addButton);
        
        //adding book details
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String updatedImage = imgpath;
                int updatedIsbn = Integer.parseInt(isbnField.getText());
                String updatedbooktype = booktype.getSelectedItem().toString();
                String updatedcateg = categ.getSelectedItem().toString();
                String updatedTitle = titleField.getText();
                String updatedAuthor = authorField.getText();
                int updatedQuantity = (int) quantitySpinner.getValue();
                double updatedPrice = Double.parseDouble(priceField.getText());

                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "Confirm Update", YES_NO_OPTION);
                if (confirm == 0) {
                    // Update in the linked list
                    List.updateItem(selectedrow, updatedImage, updatedIsbn, updatedbooktype, updatedcateg, updatedTitle, updatedAuthor,updatedQuantity,borrowedquantity,updatedPrice);
                    displayData();

                    JOptionPane.showMessageDialog(rootPane, "Update successful!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
                    addMaterials.dispose();
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();

        if (e.getSource() == inventorybtn) {
            highlightActiveButton(inventorybtn);
            cardLayout.show(contentPanel, "Inventory");
        } else if (e.getSource() == returnbookbtn) {
            highlightActiveButton(returnbookbtn);
            cardLayout.show(contentPanel, "ReturnBooks");
        } else if (e.getSource() == renewbtn) {
            highlightActiveButton(renewbtn);
            cardLayout.show(contentPanel, "RenewBooks");
        } else if (e.getSource() == trackbtn) {
            highlightActiveButton(trackbtn);
            cardLayout.show(contentPanel, "TrackBooks");
        } else if (e.getSource() == reportbtn) {
            highlightActiveButton(reportbtn);
            cardLayout.show(contentPanel, "Report");
        } else if (e.getSource() == historybtn) {
            highlightActiveButton(historybtn);
            cardLayout.show(contentPanel, "TransactionHistory");
        } else if (e.getSource() == logoutbtn) {
            highlightActiveButton(logoutbtn);
            cardLayout.show(contentPanel, "Logout");
        } else if (e.getSource().equals(addmaterials)) {
            addMaterialsFrame();
        } else if (e.getSource().equals(updatematerials)) {
            UpdateMaterialsFrame();
        }else if (e.getSource().equals(removematerials)){
            deleteConfirm();
        }else if(e.getSource().equals(borrowbookbtn)){
            borrowbtn();
        }else if(e.getSource().equals(borrowlistbtn)){
            borrowlistbtn();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }
}
