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

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 4, 2017 2:52:49 PM
 */
public class TableCellSizeImpl implements TableCellSize {

    private final int minHeight;
    
    private final int maxHeight;
    
    private TableCellDisplayFormat cellDisplayFormat;
    
    private int largestCellHeightInCurrentRow;
    
    private int previousRow;
    
    private JTable previousTable;
    
    private FontMetrics fontMetrics;
    
    public TableCellSizeImpl(int minHeight, int maxHeight) {
        this(TableCellDisplayFormat.NO_OP, minHeight, maxHeight);
    }
    
    public TableCellSizeImpl(TableCellDisplayFormat cellDisplayFormat, int minHeight, int maxHeight) {
        this.cellDisplayFormat = Objects.requireNonNull(cellDisplayFormat);
        this.minHeight= minHeight;
        this.maxHeight = maxHeight;
    }
    
    @Override
    public Dimension getPreferedSize(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        if(row != previousRow) {
            largestCellHeightInCurrentRow = minHeight;
        }
        
        final TableColumn tableColumn = table.getColumnModel().getColumn(column);
        final int columnWidth = tableColumn.getPreferredWidth();
//System.out.println("Column: "+column+", width: "+tableColumn.getWidth()+", preferred width: "+tableColumn.getPreferredWidth()+" "+this.getClass().getName());                

        final int cellHeight = this.computeCellHeight(
                table, value, row, column, columnWidth, minHeight, maxHeight);
// Don't call this here        
//        if(cellHeight > largestDimension.height) {
//            table.setRowHeight(row, cellHeight);
//        }

        largestCellHeightInCurrentRow = Math.max(largestCellHeightInCurrentRow, cellHeight);
        
//System.out.println("================Row: "+row+", computed: " + cellHeight + ", largest height" + largestCellHeightInCurrentRow+" "+this.getClass().getName());

        previousRow = row;
        
        final Dimension dim = new Dimension(columnWidth, largestCellHeightInCurrentRow);
//System.out.println("["+row+':'+column+"] "+dim+" "+this.getClass().getName());        
        return dim;
    }
    
    @Override
    public int computeCellHeight(JTable table, Object value, int row, int column, int cellWidth, int minRowHeight, int maxRowHeight) {
        
        if(!table.equals(previousTable)) {
            fontMetrics = table.getFontMetrics(table.getFont()); 
        }

        final int cellHeight;
        if(value == null) {
            
            cellHeight = fontMetrics.getHeight();
            
        }else{
            
            
            final Class columnClass = table.getColumnClass(column);
            final String sval = String.valueOf(
                    this.cellDisplayFormat.toDisplayValue(columnClass, value, row, column)
            );
            
            final int linesInCell = this.getLinesInCell(sval, cellWidth);
            
            final float cHeight = linesInCell * fontMetrics.getHeight();

//System.out.println("@["+row+':'+column+"] text len: "+sval.length()+", cell width: "+cellWidth+
//        ", lines in cell: "+linesInCell+", cell height: "+cHeight+" "+this.getClass().getName());            
            if(cHeight < minRowHeight) {
                cellHeight = minRowHeight;
            }else if(cHeight > maxRowHeight) {
                cellHeight = maxRowHeight;
            }else{
                cellHeight = Math.round(cHeight); 
            }
        }

        return cellHeight;
    }
    
    public int getLinesInCell(String text, int cellWidth) {
        final int textWidth = this.fontMetrics.stringWidth(text);
        return this.getLinesInCell(textWidth, cellWidth);
    }

    public int getLinesInCell(int textWidth, int cellWidth) {
        final int div = textWidth / cellWidth;
        final int rem = textWidth % cellWidth;
        return rem > 0 ? div + 1 : div;
    }
}

