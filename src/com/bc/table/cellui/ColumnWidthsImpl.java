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

import java.util.Date;
import java.util.Objects;
import java.util.function.BiFunction;
import javax.swing.table.TableModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 3, 2017 10:33:14 AM
 */
public class ColumnWidthsImpl implements ColumnWidths {
    
    private final TableModel tableModel;
    
    private final BiFunction<Class, Class, Boolean> subClassTest;

    public ColumnWidthsImpl(TableModel tableModel) {
        this.tableModel = Objects.requireNonNull(tableModel);
        this.subClassTest = new TestSubClass();
    }
    
    @Override
    public int getColumnPreferredWidthInChars(int columnIndex) {
        
        final Class aClass = tableModel.getColumnClass(columnIndex);

        final int widthInChars;
        if(aClass == Long.class || aClass == Integer.class || aClass == Short.class) {
            widthInChars = 4;
        }else if(this.subClassTest.apply(aClass, Date.class)) {
            widthInChars = 14;
        }else{
            final String colName = tableModel.getColumnName(columnIndex);
            if("Remarks".equalsIgnoreCase(colName)) {
                widthInChars = 11;
            }else{
                widthInChars = 30;
            }
        }

        return widthInChars;
    }
}
