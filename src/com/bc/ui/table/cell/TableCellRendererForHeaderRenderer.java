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
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Chinomso Bassey Ikwuagwu on Jun 20, 2017 3:20:03 AM
 */
public class TableCellRendererForHeaderRenderer implements TableCellRenderer{

    private final TableCellRenderer tableCellRenderer;
    private final TableCellComponentModel tableCellComponentModel;

    public TableCellRendererForHeaderRenderer(
            TableCellRenderer tableCellRenderer, TableCellComponentModel tableCellComponentModel) {
        this.tableCellRenderer = Objects.requireNonNull(tableCellRenderer);
        this.tableCellComponentModel = Objects.requireNonNull(tableCellComponentModel);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        final Component component = tableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if(component != null) {
            this.tableCellComponentModel.setValue(table, component, value, isSelected, hasFocus, row, column);
        }
        
        return component;
    }

    public TableCellRenderer getTableCellRenderer() {
        return tableCellRenderer;
    }

    public TableCellComponentModel getTableCellComponentModel() {
        return tableCellComponentModel;
    }
}
