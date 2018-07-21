/*
 * Copyright 2017 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.ui.table.cell;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 22, 2017 7:47:04 PM
 */
public class TableCellSizeManagerImpl implements TableCellSizeManager {
    
    private transient static final Logger logger = Logger.getLogger(TableCellSizeManagerImpl.class.getName());

    private final ColumnWidths columnWidths;
    
    public TableCellSizeManagerImpl(ColumnWidths columnWidths) { 
        this.columnWidths = Objects.requireNonNull(columnWidths);
    }

    @Override
    public void update(JTable table, int firstRow, int lastRow) {
        
        this.updateColumnWidths(table);
        
        this.updateRowHeights(table, firstRow, lastRow);
    }
    
    /*
     * http://stackoverflow.com/questions/21723025/how-to-set-the-rowheight-dynamically-in-a-jtable
     * Auto adjust the height of rows in a JTable.
     * The only way to know the row height for sure is to render each cell
     * to determine the rendered height. After your table is populated with
     * data you may call this method.
     */
    @Override
    public void updateRowHeights(JTable table, final int first, final int last) {
        if(javax.swing.SwingUtilities.isEventDispatchThread()) {
            this.doUpdateRowHeights(table, first, last);
        }else{
            java.awt.EventQueue.invokeLater(() -> {
                this.doUpdateRowHeights(table, first, last);
            });
        }
    }

    private void doUpdateRowHeights(JTable table, final int first, final int last) {
        
        int totalHeight = 0;

//        final Font font = table.getFont();
//        final float lineHeight = table.getFontMetrics(font).getHeight();
//        if(logger.isLoggable(Level.FINER)) {
//            logger.log(Level.FINER, "font-size: {0}, line-height: {1}", 
//                    new Object[]{font.getSize2D(), lineHeight});
//        }
        
        for (int row = first; row < last; row++) {
            
            int rowHeight = 0;
            
            for (int column = 0; column < table.getColumnCount(); column++) {

                final Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                
//                rowHeight = Math.max(rowHeight, Math.max(comp.getSize().height, comp.getPreferredSize().height));
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
//System.out.println("\t\tRow height: " + rowHeight + " " + this.getClass().getName());                    

//            final float lines = rowHeight / lineHeight;
//            rowHeight = Math.round(rowHeight + (lineHeight * (lines / 2.0f)));
            rowHeight = rowHeight + rowHeight / 2; 

            if(rowHeight != table.getRowHeight(row)) {
                
//System.out.println("Row: "+row+", row height: "+rowHeight+" "+this.getClass().getName());
                table.setRowHeight(row, rowHeight);
            }
            
            totalHeight += rowHeight;
        }
        
        totalHeight += table.getTableHeader().getHeight();        
        totalHeight += table.getIntercellSpacing().height * table.getRowCount();

        table.setSize(this.rescaleHeight(table.getSize(), totalHeight));
        table.setPreferredSize(this.rescaleHeight(table.getPreferredSize(), totalHeight));
    }
    
    public Dimension rescaleHeight(Dimension dim, int height) {
        return new Dimension(dim.width, height);
    }
    
    @Override
    public void updateColumnWidths(JTable table) {
        if(javax.swing.SwingUtilities.isEventDispatchThread()) {
            this.doUpdateColumnWidths(table);
        }else{
            java.awt.EventQueue.invokeLater(() -> {
                this.doUpdateColumnWidths(table);
            });
        }
    }
    
    private void doUpdateColumnWidths(JTable table) {
//System.out.println("Table width: "+table.getPreferredSize().width);
        final TableColumnModel tableColumnModel = table.getColumnModel();
        
        final float [] preferredWidths = columnWidths.getPreferred(table);
        
        for(int col=0; col<table.getColumnCount(); col++) {
            
            tableColumnModel.getColumn(col).setPreferredWidth((int)preferredWidths[col]);
        }
    }
}
