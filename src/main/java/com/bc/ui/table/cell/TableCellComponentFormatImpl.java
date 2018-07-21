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
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 * @author Chinomso Bassey Ikwuagwu on Jun 20, 2017 2:12:19 AM
 */
public class TableCellComponentFormatImpl implements TableCellComponentFormat {

    private static final Logger logger = Logger.getLogger(TableCellComponentFormatImpl.class.getName());

    private JTable currentTable;
    
    private int currentRow;
    
    private int currentColumn;
    
    private final TableCellComponentModel tableCellComponentModel; 
    private final TableCellUIState tableCellUIState;
    private final TableCellSize tableCellSize;

    public TableCellComponentFormatImpl(TableCellComponentModel tableCellComponentModel, 
            TableCellUIState tableCellUIState, TableCellSize tableCellSize) {
        this.tableCellComponentModel = tableCellComponentModel;
        this.tableCellUIState = tableCellUIState;
        this.tableCellSize = tableCellSize;
    }

    @Override
    public void format(JTable table, Component component, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        this.currentTable = table;
        this.currentRow = row;
        this.currentColumn = column;
        
        this.updateState(table, component, value, isSelected, hasFocus, row, column);
        
        final Dimension dim = this.getPreferedSize(table, value, isSelected, hasFocus, row, column);

        component.setPreferredSize(dim);
        
        this.setValue(table, component, value, isSelected, hasFocus, row, column);
    }

    @Override
    public Object getEditedValue(Component component) {
        return tableCellComponentModel.getValue(currentTable, component, currentRow, currentColumn);
    }

    @Override
    public int getCurrentRow() {
        return currentRow;
    }

    @Override
    public int getCurrentColumn() {
        return currentColumn;
    }

    @Override
    public Object getValue(JTable table, Component component, int row, int column) {
        return tableCellComponentModel.getValue(table, component, row, column);
    }

    @Override
    public void setValue(JTable table, Component component, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        tableCellComponentModel.setValue(table, component, value, isSelected, hasFocus, row, column);
    }

    @Override
    public void updateState(JTable table, Component cellUI, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        tableCellUIState.updateState(table, cellUI, value, isSelected, hasFocus, row, column);
    }

    @Override
    public int computeCellHeight(JTable table, Object value, int row, int column, int cellWidth, int minRowHeight, int maxRowHeight) {
        return tableCellSize.computeCellHeight(table, value, row, column, cellWidth, minRowHeight, maxRowHeight);
    }

    @Override
    public Dimension getPreferedSize(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return tableCellSize.getPreferedSize(table, value, isSelected, hasFocus, row, column);
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
