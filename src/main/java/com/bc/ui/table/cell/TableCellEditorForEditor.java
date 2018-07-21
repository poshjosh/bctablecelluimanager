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
import java.util.EventObject;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * @author Chinomso Bassey Ikwuagwu on Jun 20, 2017 3:03:48 AM
 */
public class TableCellEditorForEditor implements TableCellEditor {

    private static final Logger logger = Logger.getLogger(TableCellEditorForEditor.class.getName());

    private final TableCellEditor tableCellEditor;
    private final TableCellComponentFormat cellComponentFormat;

    public TableCellEditorForEditor(
            TableCellEditor tableCellEditor, TableCellComponentModel tableCellComponentModel, 
            TableCellUIState tableCellUIState, TableCellSize tableCellSize) {
        this(tableCellEditor, new TableCellComponentFormatImpl(
                tableCellComponentModel, tableCellUIState, tableCellSize
        ));
    }

    public TableCellEditorForEditor(
            TableCellEditor tableCellEditor, TableCellComponentFormat fmt) {
        this.tableCellEditor = Objects.requireNonNull(tableCellEditor);
        this.cellComponentFormat = Objects.requireNonNull(fmt);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        if(logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "[{0}:{1}]. Raw type: {2}, value: {3}", 
                    new Object[]{row, column,
                        value == null ? null : value.getClass().getName(), value});
        }
        
        final Component component = this.tableCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
        
        if(component != null) {
            this.cellComponentFormat.format(table, component, value, isSelected, isSelected, row, column);
        }

        return component;
    }

    @Override
    public Object getCellEditorValue() {
        return tableCellEditor.getCellEditorValue();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return tableCellEditor.isCellEditable(anEvent);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return tableCellEditor.shouldSelectCell(anEvent);
    }

    @Override
    public boolean stopCellEditing() {
        return tableCellEditor.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
        tableCellEditor.cancelCellEditing();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        tableCellEditor.addCellEditorListener(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        tableCellEditor.removeCellEditorListener(l);
    }

    public TableCellEditor getTableCellEditor() {
        return tableCellEditor;
    }

    public TableCellComponentFormat getCellComponentFormat() {
        return cellComponentFormat;
    }
}
