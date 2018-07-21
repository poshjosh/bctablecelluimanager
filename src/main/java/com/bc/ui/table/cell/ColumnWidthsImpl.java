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

import java.util.Date;
import javax.swing.table.TableModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 3, 2017 10:33:14 AM
 */
public class ColumnWidthsImpl implements ColumnWidths {
    
    public ColumnWidthsImpl() { }
    
    @Override
    public int [] getPreferredChars(TableModel tableModel, float tableWidth, float charWidth) {
        final float tableWidthChars = tableWidth / charWidth;
        final int columnCount = tableModel.getColumnCount();
//System.out.println("Column count: "+columnCount+", table width in chars: "+tableWidthChars+" "+this.getClass().getName());        
        final float [] minChars = new float[columnCount];
        final float [] maxChars = new float[columnCount];
        float totalFromMin = 0;
        for(int column=0; column<columnCount; column++) {
            minChars[column] = getMinChars(tableModel, column);
            totalFromMin += minChars[column];
            maxChars[column] = getMaxChars(tableModel, column);
        }
        final float [] preferred;
        if(tableWidthChars <= totalFromMin) {
            preferred = minChars;
        }else{
            preferred = new float[columnCount];
            float remWidth = tableWidthChars - totalFromMin;
//System.out.println("Remaining width: "+remWidth+" "+this.getClass().getName());            
            for(int column=0; column<columnCount; column++) {
                float toAdd = remWidth * (minChars[column] / totalFromMin);
                float target = minChars[column] + toAdd;
//System.out.println("Column: "+column+", min: "+minChars[column] + ", to add: " + toAdd + ", target: "+target+" "+this.getClass().getName());                
                if(target <= maxChars[column]) {
                    preferred[column] = target;
                }else {
                    final float excess = target - maxChars[column];
                    float add = toAdd - excess;
                    remWidth += excess;
//System.out.println("Column: "+column+", max chars: "+maxChars[column]+", excess: "+excess+", add: "+add+" "+this.getClass().getName());                    
                    preferred[column] = minChars[column] + add;
                }
            }
        }
        
        final int [] output = new int[preferred.length];
        
        for(int i=0; i<preferred.length; i++) {
            output[i] = (int)preferred[i];
        }

        return output;
    }
    
    @Override
    public int getMinChars(TableModel tableModel, int columnIndex) {
        
        final Class aClass = tableModel.getColumnClass(columnIndex);

        final int widthInChars;
        if(aClass == Long.class || aClass == Integer.class || aClass == Short.class) {
            widthInChars = 3;
        }else if(Date.class.isAssignableFrom(aClass)) {
            widthInChars = 8;
        }else{
            final String colName = tableModel.getColumnName(columnIndex);
            if("Remarks".equalsIgnoreCase(colName)) {
                widthInChars = 8;
            }else{
                widthInChars = 16;
            }
        }

        return widthInChars;
    }
    
    @Override
    public int getMaxChars(TableModel tableModel, int columnIndex) {
        
        final Class aClass = tableModel.getColumnClass(columnIndex);

        final int widthInChars;
        if(aClass == Long.class || aClass == Integer.class || aClass == Short.class) {
            widthInChars = 4;
        }else if(Date.class.isAssignableFrom(aClass)) {
            widthInChars = Short.MAX_VALUE;
        }else{
            final String colName = tableModel.getColumnName(columnIndex);
            if("Remarks".equalsIgnoreCase(colName)) {
                widthInChars = 14;
            }else{
                widthInChars = Short.MAX_VALUE;
            }
        }

        return widthInChars;
    }
}
