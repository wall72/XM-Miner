package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;


    public class CheckBoxHeader extends JCheckBox 
        implements TableCellRenderer { 

        public CheckBoxHeader(/*ItemListener itemListener*/) { 
//            addItemListener(itemListener); 
        } 

        //implements TableCellRenderer 
    		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
            JTableHeader header = table.getTableHeader(); 
//            header.addMouseListener(this); 
//            return this; 
        		return (Component)value;
    		}
    } 
