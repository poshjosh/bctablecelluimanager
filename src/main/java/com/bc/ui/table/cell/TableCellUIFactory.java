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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 18, 2017 1:01:12 PM
 */
public interface TableCellUIFactory {
    
    TableCellComponentFormat getTableCellComponentFormat();
    
    TableCellSizeManager getTableCellSizeManager();
    
    Component getRendererComponent(int columnIndex);
    
    Component getHeaderRendererComponent(int columnIndex);
    
    Component getEditorComponent(int columnIndex);
    
    TableCellComponentModel getRendererComponentModel(int columnIndex);
    
    TableCellComponentModel getHeaderRendererComponentModel(int columnIndex);
    
    TableCellComponentModel getEditorComponentModel(int columnIndex);

    TableCellRenderer getRenderer(int columnIndex);
    
    TableCellRenderer getHeaderRenderer(int columnIndex);
    
    TableCellEditor getEditor(int columnIndex);
}
