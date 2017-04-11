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

import java.awt.Dimension;
import java.text.DateFormat;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 3:18:17 PM
 */
public class TableCellUIUpdaterImpl implements TableCellUIUpdater{

    private int minCellHeight;
    private int maxCellHeight;
    private DateFormat dateFormat;
    private Dimension preferredSize;
    private ColumnWidths columnWidths;
    private JTable table;
    private TableModel tableModel;

    private TableCellUIState cellUIState;
    
    private TableCellSize cellSize;
    
    private TableCellDisplayValue cellDisplayValue;

    private TableCellUIFactory cellUIFactory;
    
    private TableCellSizeManager cellSizeManager;
    
    public TableCellUIUpdaterImpl() {
        this.initDefaults();
    }
    
    private void initDefaults() {

        if(this.table == null) {
            this.table = new JTable();
        }
        
        if(this.cellUIFactory == null) {
            
            if(this.cellUIState == null) {
                this.cellUIState = new TableCellUIStateImpl();
            }

            if(this.cellSize == null) {
                
                this.maxCellHeight = 15;
                this.maxCellHeight = 150;

                this.cellSize = new TableCellSizeImpl(minCellHeight, maxCellHeight);
            }
            
            if(this.cellDisplayValue == null) {
                this.cellDisplayValue = new TableCellDisplayValueImpl(dateFormat);
            }
            
            this.cellUIFactory = new TableCellUIFactoryImpl(cellUIState, cellSize, cellDisplayValue);
        }
        
        if(this.cellSizeManager == null) {
            this.cellSizeManager = new TableCellSizeManagerImpl();
        }
    }

    @Override
    public JTable update() {
        
        if(preferredSize != null) {
            table.setPreferredSize(preferredSize);
        }
        
        if(tableModel != null) {
            table.setModel(tableModel);
        }else{
            tableModel = table.getModel();
        }
        
        if(this.columnWidths == null) {
            this.columnWidths = new ColumnWidthsImpl(tableModel);
        }
        
        for(int col=0; col<table.getColumnCount(); col++) {
            
            final Class colClass = table.getColumnClass(col);
            
            final TableCellRenderer cellRenderer = cellUIFactory.getRenderer(col);
            
            table.setDefaultRenderer(colClass, cellRenderer);
            
            final TableCellEditor cellEditor = cellUIFactory.getEditor(col);
            
            table.setDefaultEditor(colClass, cellEditor);
            
//System.out.println("Col: "+col+", class: "+colClass+", editor type: "+cellEditor.getClass().getName());
        }
        
        this.cellSizeManager.updateCellSizes(table, columnWidths, table.getPreferredSize().getWidth());
        
        final UpdateRowHeightsTableAndColumnModelListener listener = 
                new UpdateRowHeightsTableAndColumnModelListener(table, this.cellSizeManager);
        
        tableModel.addTableModelListener(listener);
        
        table.getColumnModel().addColumnModelListener(listener);
        
        return table;
    }
    
    @Override
    public TableCellUIUpdater cellState(TableCellUIState cellUIState) {
        this.cellUIState = cellUIState;
        return this;
    }

    @Override
    public TableCellUIUpdater cellSize(TableCellSize cellSize) {
        this.cellSize = cellSize;
        return this;
    }

    @Override
    public TableCellUIUpdater cellDisplayValue(TableCellDisplayValue cellDisplayValue) {
        this.cellDisplayValue = cellDisplayValue;
        return this;
    }

    @Override
    public TableCellUIUpdater dateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    @Override
    public TableCellUIUpdater cellUIFactory(TableCellUIFactory cellUIFactory) {
        this.cellUIFactory = cellUIFactory;
        return this;
    }

    @Override
    public TableCellUIUpdater cellSizeManager(TableCellSizeManager cellSizeManager) {
        this.cellSizeManager = cellSizeManager;
        return this;
    }

    @Override
    public TableCellUIUpdater columnWidths(ColumnWidths columnWidths) {
        this.columnWidths = columnWidths;
        return this;
    }

    @Override
    public TableCellUIUpdater minCellHeight(int i) {
        this.minCellHeight = i;
        return this;
    }

    @Override
    public TableCellUIUpdater maxCellHeight(int i) {
        this.maxCellHeight = i;
        return this;
    }

    @Override
    public TableCellUIUpdater preferredSize(Dimension dim) {
        this.preferredSize = dim;
        return this;
    }

    @Override
    public TableCellUIUpdater table(JTable table) {
        this.table = table;
        return this;
    }

    @Override
    public TableCellUIUpdater tableModel(TableModel tableModel) {
        this.tableModel = tableModel;
        return this;
    }

    public int getMinCellHeight() {
        return minCellHeight;
    }

    public int getMaxCellHeight() {
        return maxCellHeight;
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public ColumnWidths getColumnWidths() {
        return columnWidths;
    }

    public JTable getTable() {
        return table;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public TableCellUIState getCellUIState() {
        return cellUIState;
    }

    public TableCellSize getCellSize() {
        return cellSize;
    }

    public TableCellUIFactory getCellUIFactory() {
        return cellUIFactory;
    }

    public TableCellSizeManager getCellSizeManager() {
        return cellSizeManager;
    }
}
