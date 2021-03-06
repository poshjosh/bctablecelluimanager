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

import javax.swing.JTable;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 22, 2017 7:45:21 PM
 */
public interface TableCellSizeManager {

    void update(JTable table, int firstRow, int lastRow);
    
    /*
     * http://stackoverflow.com/questions/21723025/how-to-set-the-rowheight-dynamically-in-a-jtable
     * Auto adjust the height of rows in a JTable.
     * The only way to know the row height for sure is to render each cell
     * to determine the rendered height. After your table is populated with
     * data you may call this method.
     */
    void updateRowHeights(JTable table, int firstRow, int lastRow);

    void updateColumnWidths(JTable table);
}
