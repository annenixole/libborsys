
package Library;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class SearchAndSort {
    
    public void sortByTitle(DefaultTableModel tableModel) {
        quickSort(tableModel, 4); 
    }

    public void sortByAuthor(DefaultTableModel tableModel) {
        quickSort(tableModel, 5); 
    }
    
    //method overloading
    private void quickSort(DefaultTableModel tableModel, int columnIndex) {
        int rowCount = tableModel.getRowCount();
        if (rowCount < 2) {
            return; // No need to sort if there's 0 or 1 row
        }
        Object[][] tableData = new Object[rowCount][tableModel.getColumnCount()];

        // Copy data from table model to array for sorting
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                tableData[i][j] = tableModel.getValueAt(i, j);
            }
        }

        quickSort(tableData, 0, rowCount - 1, columnIndex);

        // Update the table with sorted data
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                tableModel.setValueAt(tableData[i][j], i, j);
            }
        }
    }

    private void quickSort(Object[][] data, int low, int high, int columnIndex) {
        if (low < high) {
            int pivotIndex = partition(data, low, high, columnIndex);
            quickSort(data, low, pivotIndex - 1, columnIndex);
            quickSort(data, pivotIndex + 1, high, columnIndex);
        }
    }

    private int partition(Object[][] data, int low, int high, int columnIndex) {
        String pivot = (String) data[high][columnIndex]; // Use the last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            String currentValue = (String) data[j][columnIndex];
            if (currentValue.compareToIgnoreCase(pivot) <= 0) {
                i++;
                // Swap data[i] and data[j]
                Object[] temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
        // Swap data[i + 1] and pivot (data[high])
        Object[] temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;

        return i + 1;
    }

    // Method to get rows from the table model as an ArrayList of Object arrays
    private ArrayList<Object[]> getRowsFromTableModel(DefaultTableModel tableModel) {
        ArrayList<Object[]> rows = new ArrayList<>();
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            Object[] row = new Object[columnCount];
            for (int j = 0; j < columnCount; j++) {
                row[j] = tableModel.getValueAt(i, j);
            }
            rows.add(row);
        }
        return rows;
    }

    
    
    // Method to update the table model with the filtered result rows
    private void updateTableModel(DefaultTableModel tableModel, ArrayList<Object[]> resultRows) {
        // Clear the existing rows in the table
        tableModel.setRowCount(0);

        // Add the filtered rows to the table
        for (int i = 0; i < resultRows.size(); i++) {
            Object[] addrow = resultRows.get(i); // Get the row at index i
            tableModel.addRow(addrow); // Add the row to the table model
        }
    }
    
    // Linear search based on title or author
    public void linearSearch(DefaultTableModel tableModel, String query) {
        ArrayList<Object[]> rows = getRowsFromTableModel(tableModel);
        ArrayList<Object[]> resultRows = new ArrayList<>();

        // Perform linear search on the title and author columns
        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i); // Get the row at index i
            String title = (String) row[4]; 
            String author = (String) row[5];

            // Check if either the title or author contains the search term
            if (title.toLowerCase().contains(query.toLowerCase()) || author.toLowerCase().contains(query.toLowerCase())) {
                resultRows.add(row);
            }
        }

        // Update the table with only the matching rows
        updateTableModel(tableModel, resultRows); // to call 
    }
}
