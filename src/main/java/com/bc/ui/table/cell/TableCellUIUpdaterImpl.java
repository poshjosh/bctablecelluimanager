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
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 3:18:17 PM
 */
public class TableCellUIUpdaterImpl implements TableCellUIUpdater{

    private transient static final Logger LOG = Logger.getLogger(TableCellUIUpdaterImpl.class.getName());

    private int minCellHeight = 0;
    private int maxCellHeight = Integer.MAX_VALUE;
    private DateFormat dateFormat;
    private Dimension preferredSize;
    private ColumnWidths columnWidths;

    private TableCellUIState cellUIState;
    
    private TableCellSize cellSize;
    
    private TableCellDisplayFormat cellDisplayFormat;

    private TableCellUIFactory cellUIFactory;
    
    private TableCellSizeManager cellSizeManager;
    
    public TableCellUIUpdaterImpl() { }
    
    @Override
    public void update(JTable table) {
        if(javax.swing.SwingUtilities.isEventDispatchThread()) {
            this.doUpdate(table);
        }else{
            java.awt.EventQueue.invokeLater(() -> {
                this.doUpdate(table);
            });
        }
    }

    private void doUpdate(JTable table) {

        Objects.requireNonNull(table);
        
        if(preferredSize != null) {
            table.setPreferredSize(preferredSize);
        }
        
        if(this.cellSizeManager == null) {
            
            if(this.columnWidths == null) {
                this.columnWidths = new ColumnWidthsImpl();
            }
            
            this.cellSizeManager = new TableCellSizeManagerImpl(columnWidths);
        }

        if(this.cellUIFactory == null) {
            
            LOG.fine(() -> "Using default " + TableCellUIFactory.class.getName());
            
            if(this.cellUIState == null) {
                this.cellUIState = new TableCellUIStateImpl();
            }

            if(this.cellDisplayFormat == null) {
                this.cellDisplayFormat = new TableCellDisplayFormatImpl(dateFormat);
            }
            
            if(this.cellSize == null) {
                this.cellSize = new TableCellSizeImpl(cellDisplayFormat, minCellHeight, maxCellHeight);
            }
            
            this.cellUIFactory = new TableCellUIFactoryImpl(
                    cellUIState, cellSize, cellDisplayFormat, cellSizeManager);
        }
        
        table.getTableHeader().setDefaultRenderer(new TableCellRendererForHeaderRenderer(
                table.getTableHeader().getDefaultRenderer(),
                cellUIFactory.getTableCellComponentFormat()
        ));
        
        final Map<Class, TableCellRenderer> renderers = new HashMap();
        final Map<Class, TableCellEditor> editors = new HashMap();
        
//        final TableCellComponentFormat shareAcrossCells = cellUIFactory.getTableCellComponentFormat();
        
        for(int col=0; col<table.getColumnCount(); col++) {
            final int column = col;
            final Class colClass = table.getColumnClass(col);
            
            if(renderers.get(colClass) == null) {
                LOG.finer(() -> "Column: " + column + ", renderer component: " + cellUIFactory.getRendererComponent(column));
//                final TableCellRenderer renderer = new TableCellRendererForRenderer(
//                    table.getDefaultRenderer(colClass), shareAcrossCells
//                );
//                final TableCellRenderer renderer = new TableCellRendererForComponent(
//                    cellUIFactory.getRendererComponent(col), shareAcrossCells
//                );
                final TableCellRenderer renderer = cellUIFactory.getRenderer(col);
                renderers.put(colClass, renderer);
                table.setDefaultRenderer(colClass, renderer);
            }
            
            if(editors.get(colClass) == null) {
                LOG.finer(() -> "Column: " + column + ", editor component: " + cellUIFactory.getEditorComponent(column));
//                final TableCellEditor editor = new TableCellEditorForEditor(
//                    table.getDefaultEditor(colClass), shareAcrossCells
//                );
//                final TableCellEditor editor = new TableCellEditorForComponent(
//                    cellUIFactory.getEditorComponent(col), shareAcrossCells
//                );
                final TableCellEditor editor = cellUIFactory.getEditor(col);
                editors.put(colClass, editor);
                table.setDefaultEditor(colClass, editor);
            }
            
//System.out.println("Col: "+col+", class: "+colClass+
///        ", editor type: "+cellEditor.getClass().getName()+
//        ", renderer type: "+cellRenderer.getClass().getName()+"\t@"+this.getClass());
        }
        
        this.cellSizeManager.update(table, 0, table.getRowCount());
        
        final UpdateRowHeightsTableAndColumnModelListener listener = 
                new UpdateRowHeightsTableAndColumnModelListener(table, this.cellSizeManager);
        
        table.getModel().addTableModelListener(listener);
        
        table.getColumnModel().addColumnModelListener(listener);
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
    public TableCellUIUpdater cellDisplayValue(TableCellDisplayFormat cellDisplayValue) {
        this.cellDisplayFormat = cellDisplayValue;
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
