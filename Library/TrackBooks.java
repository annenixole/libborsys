package Library;
import LinkedList.BorrowedLinkedList;
import LinkedList.BorrowedBookNode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TrackBooks extends JPanel {
    
    BorrowedLinkedList BorrowedList = new BorrowedLinkedList();

    private JPanel cardPanel;
    private JPanel borrowedP;
    private JPanel returnedP;
    private JPanel renewedP;
    private JTable borrowedTable;
    public DefaultTableModel borrowedModel;
    private JTable returnTable;
    private DefaultTableModel returnModel;
    
    public DefaultTableModel booksModel;
    private JTable booksTable;
    public int selectedRow;

    public TrackBooks() {
        setLayout(null); //for manual positioning
        setBackground(Color.WHITE);

        // Create buttons
        JButton borrowedBtn = new JButton("Borrowed");
        JButton returnedBookBtn = new JButton("Returned");
        JButton renewBookBtn = new JButton("Renewed");

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // Set null layout for button panel
        buttonPanel.setBounds(50, 100, 980, 40); // Position and size
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel);

        // Position and add buttons to the button panel
        borrowedBtn.setBounds(0, 10, 120, 30); // x, y, width, height
        returnedBookBtn.setBounds(120, 10, 120, 30);
        renewBookBtn.setBounds(240, 10, 120, 30);

        buttonPanel.add(borrowedBtn);
        buttonPanel.add(returnedBookBtn);
        buttonPanel.add(renewBookBtn);

        // Tables Panel for switching panels
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());
        cardPanel.setBounds(50, 140, 980, 500); // Position and size
        add(cardPanel);
        
        borrowedP = createBorrowedPanel();
        returnedP = createReturnedPanel();
        renewedP = createRenewedPanel();
        
        cardPanel.add(borrowedP, "Borrowed");
        cardPanel.add(returnedP, "Returned");
        cardPanel.add(renewedP, "Renewed");
        
        borrowedBtn.addActionListener(e -> switchCard("Borrowed"));
        returnedBookBtn.addActionListener(e -> switchCard("Returned"));
        renewBookBtn.addActionListener(e -> switchCard("Renewed"));
        
        booksModel = new DefaultTableModel(new String[]{"Call Number", "ISBN", "Title", "Author"}, 0);
        booksTable = new JTable(booksModel);
    }

    private JPanel createBorrowedPanel() {
        JPanel borrowedPanel = new JPanel();
        borrowedPanel.setLayout(null); 
        borrowedPanel.setBackground(Color.LIGHT_GRAY);

        String[] headerTrack = {"Borrower no.","Quantity of book", "Borrowed Date", "Expected Return Date","Borrower"};
        borrowedModel = new DefaultTableModel(headerTrack, 0);
        borrowedTable = new JTable(borrowedModel);
        JScrollPane borrowedScroll = new JScrollPane(borrowedTable);
        
        borrowedScroll.setBounds(10, 10, 960, 480);
        borrowedPanel.add(borrowedScroll);
        
        borrowedTable.getColumnModel().removeColumn(borrowedTable.getColumnModel().getColumn(4));

        borrowedTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = borrowedTable.getSelectedRow();
                if (selectedRow != -1) {
                    borrowerDetails();
                }
            }
        });

        return borrowedPanel;
    }

    private JPanel createReturnedPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);

        String[] returnTrack = {"Borrower no.", "Quantity of book", "Borrowed Date", "Return Date"};
        returnModel = new DefaultTableModel(returnTrack, 0);
        returnTable = new JTable(returnModel);
        JScrollPane returnScroll = new JScrollPane(returnTable);

        returnScroll.setBounds(10, 10, 960, 480); 
        panel.add(returnScroll);

        return panel;
    }

    private JPanel createRenewedPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null); 
        panel.setBackground(Color.blue);

        return panel;
    }

    private void switchCard(String cardName) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, cardName);
    }
    
    public void borrowerDetails(){
        JFrame detailsfrm = new JFrame();
        detailsfrm.setLayout(null);
        detailsfrm.setResizable(false);
        detailsfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsfrm.setSize(500, 500);
        detailsfrm.setVisible(true);
        detailsfrm.setLocationRelativeTo(null);
        
        JPanel bgpanel = new JPanel();
        bgpanel.setLayout(null);
        bgpanel.setBounds(0, 0, 500, 500);
        bgpanel.setBackground(Color.white);
        detailsfrm.add(bgpanel);
        
        JPanel borrowerDitsP = new JPanel();
        borrowerDitsP.setLayout(null);
        borrowerDitsP.setBackground(Color.WHITE);
        borrowerDitsP.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        borrowerDitsP.setBounds(20, 20, 450, 110);
        bgpanel.add(borrowerDitsP);
        
        JLabel borrowerLabel = new JLabel("Borrower Details");
        borrowerLabel.setBounds(10, 5, 200, 20);
        borrowerDitsP.add(borrowerLabel);
        
        int selectedRow = borrowedTable.getSelectedRow();
        String borrower = borrowedModel.getValueAt(selectedRow, 4).toString();
        String formattedText = borrower.replace("Student No.:", "Student No.:")
                .replace("Full Name:", "Full Name:")
                .replace("Program:", "Program:");

        //JTextArea for multi-line text
        JTextArea borrowerDits = new JTextArea(formattedText);
        borrowerDits.setBounds(10, 40, 400, 50);
        borrowerDits.setEditable(false);
        borrowerDitsP.add(borrowerDits);
        
        // Create a filtered model for books specific to the borrower
        DefaultTableModel filteredBooksModel = new DefaultTableModel(
                new Object[]{"Call Number", "ISBN", "Title", "Author"},
                0
        );
        
        String borrowerNum = borrowedModel.getValueAt(selectedRow, 0).toString();

        // Filter the booksModel for the selected borrower
        for (int i = 0; i < booksModel.getRowCount(); i++) {
            String modelBorrowerNum = booksModel.getValueAt(i, 0).toString();
            if (modelBorrowerNum.equals(borrowerNum)) {
                Object[] row = new Object[booksModel.getColumnCount() - 1]; // Exclude BorrowerNum column
                for (int j = 1; j < booksModel.getColumnCount(); j++) {
                    row[j - 1] = booksModel.getValueAt(i, j);
                }
                filteredBooksModel.addRow(row);
            }
        }

        JTable filteredBooksTable = new JTable(filteredBooksModel);
        JScrollPane booksScroll = new JScrollPane(filteredBooksTable);
        booksScroll.setBounds(20, 200, 450, 200);
        bgpanel.add(booksScroll);
        
    }
}
