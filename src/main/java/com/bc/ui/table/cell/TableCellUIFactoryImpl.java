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

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 1:35:45 PM
 */
public class TableCellUIFactoryImpl implements TableCellUIFactory{

    private final TableCellUIState tableCellUIState;
    
    private final TableCellSize tableCellSize;
    
    private final TableCellDisplayFormat tableCellDisplayFormat;
    
    private final TableCellSizeManager tableCellSizeManager;
    
    public TableCellUIFactoryImpl(int minCellHeight, int maxCellHeight, DateFormat dateFormat) {
        this(new TableCellUIStateImpl(), 
                new TableCellSizeImpl(new TableCellDisplayFormatImpl(dateFormat), minCellHeight, maxCellHeight),
                new TableCellDisplayFormatImpl(dateFormat),
                new TableCellSizeManagerImpl(new ColumnWidthsImpl())
        ); 
    }
    
    public TableCellUIFactoryImpl(
            TableCellUIState tableCellUIState, 
            TableCellSize tableCellSize,
            TableCellDisplayFormat tableCellDisplayFormat,
            TableCellSizeManager tableCellSizeManager
    ) {
        this.tableCellUIState = Objects.requireNonNull(tableCellUIState);
        this.tableCellSize = Objects.requireNonNull(tableCellSize);
        this.tableCellDisplayFormat = Objects.requireNonNull(tableCellDisplayFormat);
        this.tableCellSizeManager = Objects.requireNonNull(tableCellSizeManager);
    }

    @Override
    public TableCellComponentFormat getTableCellComponentFormat() {
        return new TableCellComponentFormatImpl(
                new TableCellComponentModelImpl(this.tableCellDisplayFormat),
                this.tableCellUIState,
                this.tableCellSize
        );
    }

    @Override
    public Component getRendererComponent(int columnIndex) {
        return this.getComponent(columnIndex);
    }

    @Override
    public Component getHeaderRendererComponent(int columnIndex) {
        final JLabel textField = new JLabel();
        textField.setBackground(Color.WHITE);
        return textField;
    }

    @Override
    public Component getEditorComponent(int columnIndex) {
        return this.getComponent(columnIndex);
    }
    
    public Component getComponent(int columnIndex) {
        return new TableCellTextArea();
    }
    
    @Override
    public TableCellComponentModel getRendererComponentModel(int columnIndex) {
        return this.getComponentModel(columnIndex);
    }

    @Override
    public TableCellComponentModel getHeaderRendererComponentModel(int columnIndex) {
        return this.getComponentModel(columnIndex);
    }
    
    @Override
    public TableCellComponentModel getEditorComponentModel(int columnIndex) {
        return this.getComponentModel(columnIndex);
    }
    
    public TableCellComponentModel getComponentModel(int columnIndex) {
        return new TableCellComponentModelImpl(this.tableCellDisplayFormat);
    }
    
    @Override
    public TableCellRenderer getRenderer(int columnIndex) {
        return new TableCellRendererForComponent(
                this.getRendererComponent(columnIndex),
                this.getRendererComponentModel(columnIndex),
                this.tableCellUIState, 
                this.tableCellSize
        );
    }

    @Override
    public TableCellRenderer getHeaderRenderer(int columnIndex) {
        return new TableCellRendererForComponent(
                this.getHeaderRendererComponent(columnIndex),
                this.getHeaderRendererComponentModel(columnIndex),
                this.tableCellUIState, 
                this.tableCellSize
        );
    }

    @Override
    public TableCellEditor getEditor(int columnIndex) {
        return new TableCellEditorForComponent(
                this.getEditorComponent(columnIndex),
                this.getEditorComponentModel(columnIndex),
                this.tableCellUIState, 
                this.tableCellSize
        );
    }

    @Override
    public TableCellSizeManager getTableCellSizeManager() {
        return tableCellSizeManager;
    }

    public TableCellUIState getTableCellUIState() {
        return tableCellUIState;
    }

    public TableCellSize getTableCellSize() {
        return tableCellSize;
    }

    public TableCellDisplayFormat getTableCellDisplayFormat() {
        return tableCellDisplayFormat;
    }
}
