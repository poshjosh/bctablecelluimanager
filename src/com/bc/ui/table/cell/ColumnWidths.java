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

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 3, 2017 10:31:26 AM
 */
public interface ColumnWidths {
    
    default float [] getPreferred(JTable table) {
        final Font font = table.getFont();
        final Rectangle2D rec = table.getFontMetrics(font).getStringBounds("h", table.getGraphics());
        final float averageCharWidth = (float)rec.getWidth();
//System.out.println("- - - - - - - Font size: "+font.getSize2D()+ ", average char width "+maxCharWidth+" "+this.getClass().getName());   
        final int [] preferredChars = getPreferredChars(table.getModel(), table.getWidth(), averageCharWidth);
//System.out.println("- - - - - - - Preferred width chars: "+java.util.Arrays.toString(preferredChars));        
        final float [] preferred = new float[preferredChars.length];
//        float preferredTotal = 0;
        for(int i=0; i<preferredChars.length; i++) {
            preferred[i] = averageCharWidth * preferredChars[i];
//            preferredTotal += preferred[i];
        }
//System.out.println("- - - - - - - Preferred widths "+java.util.Arrays.toString(preferred)+" "+this.getClass().getName());   
//System.out.println("Preferred total: "+(preferredTotal)+", table width: "+table.getWidth()+" "+this.getClass().getName());        

        return preferred;
    }
    
    int [] getPreferredChars(TableModel tableModel, float tableWidth, float charWidth);
    
    int getMinChars(TableModel tableModel, int columnIndex);    
    
    int getMaxChars(TableModel tableModel, int columnIndex);    
}
