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
import java.awt.Font;
import javax.swing.JTable;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 7, 2017 5:10:13 PM
 */
public class TableCellUIStateImpl implements TableCellUIState {

    @Override
    public void updateState(JTable table, Component cellUI, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        final Font font = table.getFont();
        
        cellUI.setFont(font); 

        if (isSelected) {
            cellUI.setForeground(table.getSelectionForeground());
            cellUI.setBackground(table.getSelectionBackground());
        } else {
            cellUI.setForeground(table.getForeground());
            cellUI.setBackground(table.getBackground());
        }
    }
}
