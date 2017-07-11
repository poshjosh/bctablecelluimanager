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
import java.awt.ItemSelectable;
import java.util.Objects;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 5, 2017 9:50:56 PM
 */
public class TableCellComponentModelImpl implements TableCellComponentModel {

    private final TableCellDisplayFormat cellDisplayFormat;
    
    public TableCellComponentModelImpl() { 
        this(TableCellDisplayFormat.NO_OP);
    }
    
    public TableCellComponentModelImpl(TableCellDisplayFormat cellDisplayFormat) { 
        this.cellDisplayFormat = cellDisplayFormat;
    }
    
    @Override
    public Object getValue(JTable table, Component component, int row, int column) {
        
        Objects.requireNonNull(component);
        
        final Object value = this.getValue(component);
        
        final Object output = this.cellDisplayFormat.fromDisplayValue(table, component, value, row, column);
                
        return output;        
    }
    
    public Object getValue(Component component) {
        
        Objects.requireNonNull(component);
        
        final Object value;
        if(component instanceof JTextComponent) {
            value = ((JTextComponent)component).getText();
        }else if(component instanceof JLabel) {
            value = ((JLabel)component).getText();
        }else if(component instanceof AbstractButton) {
            value = ((AbstractButton)component).isSelected();
        }else if(component instanceof ItemSelectable) {
            final Object [] selected = ((ItemSelectable)component).getSelectedObjects();
            value = selected == null || selected.length == 0 ? null : selected[0];
        }else if(component instanceof JList) {
            final Object selected = ((JList)component).getSelectedValue();
            value = selected == null ? null : selected;
        }else{
            throw new UnsupportedOperationException("Unsupported UI component type: "+component.getClass().getName());
        }
        
        return value;        
    }
    
    @Override
    public void setValue(JTable table, Component component, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Objects.requireNonNull(component);

        value = this.cellDisplayFormat.toDisplayValue(table, component, value, isSelected, hasFocus, row, column);
        
        this.setValue(component, value);
    }

    public void setValue(Component component, Object value) {
        
        Objects.requireNonNull(component);
        
        if(component instanceof JTextComponent) {
            ((JTextComponent)component).setText(value==null?null:String.valueOf(value));
        }else if(component instanceof JLabel) {
            ((JLabel)component).setText(value==null?null:String.valueOf(value));
        }else if(component instanceof AbstractButton) {
            ((AbstractButton)component).setSelected(Boolean.valueOf(String.valueOf(value)));
        }else if(component instanceof JComboBox) {
            ((JComboBox)component).setSelectedItem(value);
        }else if(component instanceof JList) {
            ((JList)component).setSelectedValue(value, true);
        }else{
            throw new UnsupportedOperationException("Unsupported UI component type: "+component.getClass().getName());
        }
    }
}
