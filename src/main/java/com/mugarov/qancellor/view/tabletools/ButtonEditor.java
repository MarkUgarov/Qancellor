/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.view.tabletools;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author mugarov
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final Button button;
    public static final int ID_ROW = 0;

    public ButtonEditor(ActionListener listener) {
        super();
        button = new Button();
        button.setOpaque(true);
        button.addActionListener(listener);
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String txt = (value == null) ? "" : value.toString();
        this.button.setText(txt);
        this.button.setID(table.getValueAt(row, ButtonEditor.ID_ROW).toString());
        return this.button;
    }

} 
