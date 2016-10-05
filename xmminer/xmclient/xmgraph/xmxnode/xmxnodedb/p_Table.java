package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import javax.swing.table.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import javax.swing.*;

public class p_Table extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();
  TableModel det = new TableModel();

  public p_Table() {
    try{
      jbInit();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception{
    this.setLayout(borderLayout1);
    this.setOpaque(false);
    jTable1.setName("");
    jTable1.setOpaque(false);
    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    this.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTable1, null);
    jTable1.setModel(det);
  }

  public void setColumnSize(){
    TableColumn tCol = jTable1.getColumnModel().getColumn(0);
    tCol.setPreferredWidth(30);
    tCol = jTable1.getColumnModel().getColumn(1);
    tCol.setPreferredWidth(150);
  }

  public void setUpCheckColumn(TableColumn tColumn) {
    JCheckBox checkBox = new JCheckBox();
    tColumn.setCellEditor(new DefaultCellEditor(checkBox));
  }
}