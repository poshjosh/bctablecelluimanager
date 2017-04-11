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
import java.text.DateFormat;
import java.util.Objects;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 1:35:45 PM
 */
public class TableCellUIFactoryImpl implements TableCellUIFactory{

    private final TableCellUIState tableCellUIState;
    
    private final TableCellSize tableCellSize;
    
    private final TableCellDisplayValue tableCellDisplayValue;
    
    public TableCellUIFactoryImpl(DateFormat dateFormat, int minCellHeight, int maxCellHeight) {
        this(new TableCellUIStateImpl(), 
                new TableCellSizeImpl(minCellHeight, maxCellHeight),
                new TableCellDisplayValueImpl(dateFormat)); 
    }
    
    public TableCellUIFactoryImpl(
            TableCellUIState tableCellUIState, 
            TableCellSize tableCellSize,
            TableCellDisplayValue tableCellDisplayValue) {
        this.tableCellUIState = Objects.requireNonNull(tableCellUIState);
        this.tableCellSize = Objects.requireNonNull(tableCellSize);
        this.tableCellDisplayValue = Objects.requireNonNull(tableCellDisplayValue);
    }

    @Override
    public Component getRendererComponent(int columnIndex) {
        return this.getComponent(columnIndex);
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
    public TableCellComponentModel getEditorComponentModel(int columnIndex) {
        return this.getComponentModel(columnIndex);
    }
    
    public TableCellComponentModel getComponentModel(int columnIndex) {
        return new TableCellComponentModelImpl(this.tableCellDisplayValue);
    }
    
    @Override
    public TableCellRenderer getRenderer(int columnIndex) {
        return new TableCellRendererImpl(
                this.getRendererComponent(columnIndex),
                this.getRendererComponentModel(columnIndex),
                this.tableCellUIState, 
                this.tableCellSize
        );
    }

    @Override
    public TableCellEditor getEditor(int columnIndex) {
        return new TableCellEditorImpl(
                this.getEditorComponent(columnIndex),
                this.getEditorComponentModel(columnIndex),
                this.tableCellUIState, 
                this.tableCellSize
        );
    }

    public TableCellUIState getTableCellUIState() {
        return tableCellUIState;
    }

    public TableCellSize getTableCellSize() {
        return tableCellSize;
    }

    public TableCellDisplayValue getTableCellDisplayValue() {
        return tableCellDisplayValue;
    }
}
