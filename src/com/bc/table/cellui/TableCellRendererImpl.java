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
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 7, 2017 5:12:19 PM
 */
public class TableCellRendererImpl implements TableCellRenderer{

    private final Component component;
    private final TableCellComponentModel tableCellComponentModel; 
    private final TableCellUIState tableCellUIState;
    private final TableCellSize tableCellSize;

    public TableCellRendererImpl(
            Component component, TableCellComponentModel tableCellComponentModel, 
            TableCellUIState tableCellUIState, TableCellSize tableCellSize) {
        this.component = component;
        this.tableCellComponentModel = tableCellComponentModel;
        this.tableCellUIState = tableCellUIState;
        this.tableCellSize = tableCellSize;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        tableCellUIState.update(table, component, value, isSelected, hasFocus, row, column);
        
        final Dimension dim = this.tableCellSize.getPreferedSize(table, value, isSelected, hasFocus, row, column);
        
        this.component.setPreferredSize(dim);
        
        this.tableCellComponentModel.setValue(table, component, value, isSelected, hasFocus, row, column);
        
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
