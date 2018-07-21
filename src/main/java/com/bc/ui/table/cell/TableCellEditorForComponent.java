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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 7, 2017 5:20:08 PM
 */
public class TableCellEditorForComponent extends AbstractCellEditor implements TableCellEditor {

    private static final Logger LOG = Logger.getLogger(TableCellEditorForComponent.class.getName());

    private final TableCellComponentFormat cellComponentFormat;

    private final Component component;
    
    public TableCellEditorForComponent(
            Component component, TableCellComponentModel tableCellComponentModel, 
            TableCellUIState tableCellUIState, TableCellSize tableCellSize) {
        this(component, new TableCellComponentFormatImpl(
                tableCellComponentModel, tableCellUIState, tableCellSize
        ));
    }

    public TableCellEditorForComponent(
            Component component, TableCellComponentFormat fmt) {
        this.component = Objects.requireNonNull(component);
        this.cellComponentFormat = Objects.requireNonNull(fmt);
        LOG.fine(() -> "Editor component: " + component);
    }

    @Override
    public Object getCellEditorValue() {
        
        final Object value = this.cellComponentFormat.getEditedValue(component);
        
        if(LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "[{0}:{1}]. Raw type: {2}, value: {3}, component type: {4}", 
                    new Object[]{this.cellComponentFormat.getCurrentRow(), this.cellComponentFormat.getCurrentColumn(),
                        value == null ? null : value.getClass().getName(), value, component==null? null: component.getClass().getName()});
        }
        
        return value;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "[{0}:{1}]. Raw type: {2}, value: {3}", 
                    new Object[]{row, column, 
                        value == null ? null : value.getClass().getName(), value});
        }
        
        this.cellComponentFormat.format(table, component, value, isSelected, isSelected, row, column);
      
        if(LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "[{0}:{1}]. Raw type: {2}, value: {3}, component type: {4}", 
                    new Object[]{this.cellComponentFormat.getCurrentRow(), this.cellComponentFormat.getCurrentColumn(),
                        value == null ? null : value.getClass().getName(), value, component==null? null: component.getClass().getName()});
        }

        return component;
    }

    public Component getComponent() {
        return component;
    }

    public TableCellComponentFormat getCellComponentFormat() {
        return cellComponentFormat;
    }
}
