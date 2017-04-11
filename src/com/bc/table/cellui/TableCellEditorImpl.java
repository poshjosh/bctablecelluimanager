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

package com.bc.table.cellui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 7, 2017 5:20:08 PM
 */
public class TableCellEditorImpl extends AbstractCellEditor implements TableCellEditor {

    private static final Logger logger = Logger.getLogger(TableCellEditorImpl.class.getName());

    private JTable table;
    
    private int editingRow;
    
    private int editingColumn;
    
    private final Component component;
    private final TableCellComponentModel tableCellComponentModel; 
    private final TableCellUIState tableCellUIState;
    private final TableCellSize tableCellSize;

    public TableCellEditorImpl(
            Component component, TableCellComponentModel tableCellComponentModel, 
            TableCellUIState tableCellUIState, TableCellSize tableCellSize) {
        this.component = component;
        this.tableCellComponentModel = tableCellComponentModel;
        this.tableCellUIState = tableCellUIState;
        this.tableCellSize = tableCellSize;
    }

    @Override
    public Object getCellEditorValue() {
        return this.tableCellComponentModel.getValue(
                table, this.component, editingRow, editingColumn);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        this.table = table;
        this.editingRow = row;
        this.editingColumn = column;
        if(logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "[{0}:{1}]. Raw type: {2}, value: {3}", 
                    new Object[]{row, column,
                        value == null ? null : value.getClass().getName(),
                        value == null ? null : value});
        }
        
        tableCellUIState.update(table, component, value, isSelected, isSelected, row, column);
        
        final Dimension dim = this.tableCellSize.getPreferedSize(table, value, isSelected, isSelected, row, column);
        
        this.component.setPreferredSize(dim);
        
        this.tableCellComponentModel.setValue(table, component, value, isSelected, isSelected, row, column);
        
        return component;
    }

    public Component getComponent() {
        return component;
    }

    public TableCellComponentModel getTableCellComponentModel() {
        return tableCellComponentModel;
    }

    public TableCellUIState getTableCellUIState() {
        return tableCellUIState;
    }

    public TableCellSize getTableCellSize() {
        return tableCellSize;
    }
}
