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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.function.BiFunction;
import javax.swing.JTable;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2017 12:25:35 AM
 */
public class TableCellDisplayValueImpl implements TableCellDisplayValue {

    private final DateFormat dateFormat;
    
    private final BiFunction<Class, Class, Boolean> subClassTest;

    public TableCellDisplayValueImpl(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        this.subClassTest = new TestSubClass();
    }
    
    @Override
    public Object fromDisplayValue(JTable table, Component component, Object displayValue, int row, int column) {
        Object output = displayValue;
        final Class columnClass = table.getColumnClass(column);
        if(displayValue == null) {
            output = null;
        }else if(this.subClassTest.apply(columnClass, Date.class)) {
            if(this.dateFormat != null && !(displayValue instanceof Date)) {
                try{
                    output = this.dateFormat.parse(displayValue.toString());
                }catch(ParseException e) {
                    throw new RuntimeException();
                }
            }
        }else if(columnClass == Boolean.class || columnClass == boolean.class) {
            if(!(displayValue instanceof Boolean)) {
                output = Boolean.valueOf(displayValue.toString());
            }
        }else if(columnClass == Long.class || columnClass == long.class) {
            if(!(displayValue instanceof Long)) {
                output = Long.valueOf(displayValue.toString());
            }
        }else if(columnClass == Integer.class || columnClass == int.class) {
            if(!(displayValue instanceof Integer)) {
                output = Integer.valueOf(displayValue.toString());
            }
        }else if(columnClass == Short.class || columnClass == short.class) {
            if(!(displayValue instanceof Short)) {
                output = Short.valueOf(displayValue.toString());
            }
        }else if(columnClass == Double.class || columnClass == double.class) {
            if(!(displayValue instanceof Double)) {
                output = Double.valueOf(displayValue.toString());
            }
        }else if(columnClass == Float.class || columnClass == float.class) {
            if(!(displayValue instanceof Float)) {
                output = Float.valueOf(displayValue.toString());
            }
        }else if(columnClass == BigDecimal.class) {
            if(!(displayValue instanceof BigDecimal)) {
                output = BigDecimal.valueOf(Double.parseDouble(displayValue.toString()));
            }
        }else{
            output = displayValue;
        }
        return output;
    }

    @Override
    public Object toDisplayValue(JTable table, Component component, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        Object output = value;
        final Class columnClass = table.getColumnClass(column);
        if(columnClass == Date.class) {
            if(this.dateFormat != null && (value instanceof Date)) {
                output = new Date(((Date)value).getTime()) {
                    @Override
                    public String toString() {
                        return dateFormat.format(this);
                    }
                };
            }
        }else{
            output = value;
        }
        return output;
    }
}
