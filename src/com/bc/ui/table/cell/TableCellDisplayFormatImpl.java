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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JTable;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2017 12:25:35 AM
 */
public class TableCellDisplayFormatImpl implements TableCellDisplayFormat {

    private final DateFormat dateFormat;
    
    public TableCellDisplayFormatImpl() {
        this(null);
    }
    
    public TableCellDisplayFormatImpl(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    @Override
    public Object fromDisplayValue(JTable table, Component component, Object displayValue, int row, int column) {
        
        final Class columnClass = this.getColumnClassWithHeaderRowAtMinusOne(table, row, column);
        
        return this.fromDisplayValue(columnClass, displayValue, row, column);
    }

    @Override
    public Object fromDisplayValue(Class columnClass, Object displayValue, int row, int column) {
        Object output = displayValue;
        final String sval = displayValue == null ? null : displayValue.toString();
        if(displayValue == null || sval == null || sval.isEmpty()) {
            output = null;
        }else if(Date.class.isAssignableFrom(columnClass)) {
            if(this.dateFormat != null && !(displayValue instanceof Date)) {
                try{
                    output = this.dateFormat.parse(sval);
                }catch(ParseException e) {
                    throw new RuntimeException();
                }
            }
        }else if(columnClass == Boolean.class || columnClass == boolean.class) {
            if(!(displayValue instanceof Boolean)) {
                output = Boolean.valueOf(sval);
            }
        }else if(columnClass == Long.class || columnClass == long.class) {
            if(!(displayValue instanceof Long)) {
                output = Long.valueOf(sval);
            }
        }else if(columnClass == Integer.class || columnClass == int.class) {
            if(!(displayValue instanceof Integer)) {
                output = Integer.valueOf(sval);
            }
        }else if(columnClass == Short.class || columnClass == short.class) {
            if(!(displayValue instanceof Short)) {
                output = Short.valueOf(sval);
            }
        }else if(columnClass == Double.class || columnClass == double.class) {
            if(!(displayValue instanceof Double)) {
                output = Double.valueOf(sval);
            }
        }else if(columnClass == Float.class || columnClass == float.class) {
            if(!(displayValue instanceof Float)) {
                output = Float.valueOf(sval);
            }
        }else if(columnClass == BigDecimal.class) {
            if(!(displayValue instanceof BigDecimal)) {
                output = BigDecimal.valueOf(Double.parseDouble(sval));
            }
        }else{
            output = displayValue;
        }
        return output;
    }

    @Override
    public Object toDisplayValue(JTable table, Component component, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        final Class columnClass = this.getColumnClassWithHeaderRowAtMinusOne(table, row, column);
        
        return this.toDisplayValue(columnClass, value, row, column);
    }
    
    public Class getColumnClassWithHeaderRowAtMinusOne(JTable table, int row, int column) {
        return row == -1 ? String.class : table.getColumnClass(column);
    }

    @Override
    public Object toDisplayValue(Class columnClass, Object value, int row, int column) {
        final Object output;
        if(Date.class.isAssignableFrom(columnClass)) {
            if(this.dateFormat != null && value != null) {
                output = new Date(((Date)value).getTime()) {
                    @Override
                    public String toString() {
                        return dateFormat.format(this);
                    }
                };
            }else{
                output = value;
            }
        }else{
            output = value;
        }
        return output;
    }
}
