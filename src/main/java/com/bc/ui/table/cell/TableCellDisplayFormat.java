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
import javax.swing.JTable;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2017 12:07:36 AM
 * @param <T> The type of the display value
 */
public interface TableCellDisplayFormat<T> {
    
    TableCellDisplayFormat NO_OP = new TableCellDisplayFormat() {
        @Override
        public Object fromDisplayValue(JTable table, Component component, 
                Object displayValue, int row, int column) {
            return displayValue;
        }
        @Override
        public Object fromDisplayValue(Class columnClass, Object displayValue, int row, int column) {
            return displayValue;
        }
        @Override
        public Object toDisplayValue(JTable table, Component component, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            return value;
        }
        @Override
        public Object toDisplayValue(Class columnClass, Object value, int row, int column) {
            return value;
        }
    };

    Object fromDisplayValue(JTable table, Component component, T displayValue, int row, int column);
    
    Object fromDisplayValue(Class columnClass, T displayValue, int row, int column);
    
    T toDisplayValue(JTable table, Component component, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column);

    T toDisplayValue(Class columnClass, Object value, int row, int column);
}
