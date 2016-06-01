/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.view.tabletools;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author mugarov
 */
public class ButtonRenderer implements TableCellRenderer {

    private final JButton button;
    private final String defaultTootltip = "Stop this thread!";

    public ButtonRenderer(){
        this.button = new JButton();
    }

    public void setTooltip(String tooltip){
        this.button.setToolTipText(tooltip);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // some NullPointerExceptions appeared when "clicking to fast"
        if(table != null){
            table.setShowGrid(true);
            table.setGridColor(Color.LIGHT_GRAY);
        }
        if(this.button != null && value != null){
            this.button.setText(value.toString());
        }
        return button;
    } 

}