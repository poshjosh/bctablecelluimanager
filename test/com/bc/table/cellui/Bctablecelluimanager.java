package com.bc.table.cellui;

import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 12:43:20 PM
 */
public class Bctablecelluimanager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        final Object [] columnNames = {"Ser", "Boolean", "Short Text", "Date", "Long Text", "Remarks"};
        final Object [][] data = {
            new Object[]{1, true, "", new Date(), "This is some very long text???.", "First Row"},
            new Object[]{2, Boolean.FALSE, "Some short text", new Date(), "This is some very long text added to show the effect of having the cell sizes automatically computed. This is some very long text added to show the effect of having the cell sizes automatically computed.", null},
            new Object[]{3, "Tru", "Short text", new Date(), "This is some very long text added to show the effect of having the cell sizes automatically computed.", "Third Row"},
            new Object[]{4, false, null, new Date(), "This is some very long text added to show the effect of having the cell sizes automatically computed. This is some very long text added to show the effect of having the cell sizes automatically computed. This is some very long text added to show the effect of having the cell sizes automatically computed.", null}
        };
        
        final TableModel tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch(columnIndex) {
                    case 0: return Integer.class;
                    case 1: return Boolean.class;
                    case 2: return String.class;
                    case 3: return Date.class;
                    case 4: return String.class;
                    case 5: return String.class;
                    default: throw new IllegalArgumentException("Unexpected column index: "+columnIndex);
                }
            }
        };
        
        final ColumnWidths columnWidths = new ColumnWidthsImpl(tableModel);
        
        final JTable table = new JTable(tableModel);
        
        final JScrollPane scrolls = new JScrollPane(table);
        
        final Dimension tablePreferredSize = new Dimension(700, 500);
        
        table.setPreferredScrollableViewportSize(tablePreferredSize);
        
        JFrame frame = new JFrame("Table Cell UI Manager Test");
        frame.setPreferredSize(tablePreferredSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrolls);
        
        frame.pack();
        
//        final BiFunction<Class, Class, Boolean> subClassTest = new TestSubClass();

        final String datePattern = "dd MMM yy";
        final SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyLocalizedPattern(datePattern);
        
        final TableCellUIFactory cellUIFactory = new TableCellUIFactoryImpl(dateFormat, 15, 150){
            @Override
            public Component getComponent(int columnIndex) {
                final Class columnType = tableModel.getColumnClass(columnIndex);
                if(columnType == Boolean.class) {
                    return new JCheckBox();
                }else {
                    return new TableCellTextArea();
                }
            }
        };
        
        final TableCellUIUpdater updater = new TableCellUIUpdaterImpl();
        
        updater.table(table)
                .columnWidths(columnWidths)
                .cellUIFactory(cellUIFactory)
                .update();

        frame.setVisible(true);
    }
}
