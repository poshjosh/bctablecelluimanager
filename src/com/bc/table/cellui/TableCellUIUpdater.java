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
import javax.swing.table.TableModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 3:11:40 PM
 */
public interface TableCellUIUpdater {
    
    TableCellUIUpdater dateFormat(DateFormat dateFormat);
    
    TableCellUIUpdater cellState(TableCellUIState cellUIState);
    
    TableCellUIUpdater cellSize(TableCellSize cellSize);
    
    TableCellUIUpdater cellDisplayValue(TableCellDisplayValue cellDisplayValue);
    
    TableCellUIUpdater cellUIFactory(TableCellUIFactory cellUIFactory);

    TableCellUIUpdater cellSizeManager(TableCellSizeManager cellSizeManager);
    
    TableCellUIUpdater columnWidths(ColumnWidths columnWidths);
    
    TableCellUIUpdater minCellHeight(int i);
    
    TableCellUIUpdater maxCellHeight(int i);
    
    TableCellUIUpdater preferredSize(Dimension dim);
    
    TableCellUIUpdater table(JTable table);
    
    TableCellUIUpdater tableModel(TableModel tableModel);
    
    JTable update();
}
