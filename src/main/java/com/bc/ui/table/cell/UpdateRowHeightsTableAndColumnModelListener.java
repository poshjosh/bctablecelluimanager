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

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 21, 2017 1:55:31 PM
 */
public class UpdateRowHeightsTableAndColumnModelListener 
        implements TableModelListener, TableColumnModelListener {
    
    private transient static final Logger logger = Logger.getLogger(UpdateRowHeightsTableAndColumnModelListener.class.getName());

    /**
     * We only need to recalculate once; so track if we are already going to do it.
     */
    private boolean areCellSizesBeingCalculated = false;
    
    private final JTable table;
    
    private final TableCellSizeManager tableCellSizeManager;

    public UpdateRowHeightsTableAndColumnModelListener(
            JTable table, TableCellSizeManager tableCellSizeManager) {
        this.table = Objects.requireNonNull(table);
        this.tableCellSizeManager = Objects.requireNonNull(tableCellSizeManager);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        final int first;
        final int last;
        if (e == null || e.getFirstRow() == TableModelEvent.HEADER_ROW) {
            // assume everything changed
            first = 0;
            last = table.getModel().getRowCount();
        } else {
            first = e.getFirstRow();
            last = e.getLastRow() + 1;
        }
        // GUI-Changes should be done through the EventDispatchThread which ensures all pending events were processed
        // Also this way nobody will change the text of our RowHeightCellRenderer because a cell is to be rendered
        
        if(logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "Updating cell sizes for rows from {0} to {1}", 
                    new Object[]{first, last});
        }
        
        if(SwingUtilities.isEventDispatchThread()) {
            this.tableCellSizeManager.update(table, first, last);
        } else {
            SwingUtilities.invokeLater(() -> {
                this.tableCellSizeManager.update(table, first, last);
            });
        }
    }

    @Override
    public void columnAdded(TableColumnModelEvent e) { }
    @Override
    public void columnRemoved(TableColumnModelEvent e) { }
    @Override
    public void columnMoved(TableColumnModelEvent e) { }
    @Override
    public void columnMarginChanged(ChangeEvent e) { 
        if (!areCellSizesBeingCalculated) {// && table.getTableHeader().getResizingColumn() != null) {
            areCellSizesBeingCalculated = true;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // textTable.getTableHeader().getResizingColumn() is != null as long as the user still is holding the mouse down
                    // To avoid going over all data every few milliseconds wait for user to release
                    if (table.getTableHeader().getResizingColumn() != null) {
                        SwingUtilities.invokeLater(this);
                    } else {
                        tableChanged(null);
                        areCellSizesBeingCalculated = false;
                    }
                }
            });
        }    
    }
    @Override
    public void columnSelectionChanged(ListSelectionEvent e) { }
}
