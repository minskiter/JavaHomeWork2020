package shopping.ui;

import shopping.model.Order;
import shopping.ui.util.BodyRender;
import shopping.ui.util.HeaderRender;
import shopping.ui.util.UiSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class OrderDialog extends JDialog {

    private JTable table;

    private int screenHeight,screenWidth;

    public OrderDialog(){
        this.setPreferredSize(new Dimension(200,219));
        this.setMinimumSize(new Dimension(200,219));
        this.setIconImage(UiSettings.ICON);
        this.setLayout(new BorderLayout());
        this.setTitle("商品信息");
        initCommponent();
        this.add(table,BorderLayout.CENTER);
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        screenWidth = kit.getScreenSize().width; //获取屏幕的宽
        screenHeight = kit.getScreenSize().height; //获取屏幕的高
    }

    private void initCommponent(){
        // 初始化组件
        // 使用表格来现实商品的信息
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setPreferredSize(new Dimension(200,200));
        model.setDataVector(new Vector<Vector<Object>>(), new Vector<Object>(Arrays.asList(new Object[]{"", ""})));
        JTableHeader header = table.getTableHeader();
        header.setFont(UiSettings.FONT);
        header.setBackground(UiSettings.SIDE_COLOR);
        header.setForeground(Color.white);
        TableColumnModel columnModel = header.getColumnModel();
        table.setSelectionBackground(UiSettings.SIDE_COLOR);
        table.setShowVerticalLines(false);
        table.setRowHeight(30);
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            columnModel.getColumn(i).setHeaderRenderer(new HeaderRender());
            columnModel.getColumn(i).setCellRenderer(new BodyRender());
        }

    }

    public void setOrder(Order order){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        model.addRow(new Vector<Object>(Arrays.asList(new Object[]{OrderPanel.orders.columnNames.get(0),order.getRecordId()})));
        model.addRow(new Vector<Object>(Arrays.asList(new Object[]{OrderPanel.orders.columnNames.get(1),order.getId()})));
        model.addRow(new Vector<Object>(Arrays.asList(new Object[]{OrderPanel.orders.columnNames.get(2),order.getName()})));
        model.addRow(new Vector<Object>(Arrays.asList(new Object[]{OrderPanel.orders.columnNames.get(3),order.getPrice()})));
        model.addRow(new Vector<Object>(Arrays.asList(new Object[]{OrderPanel.orders.columnNames.get(4),order.getNum()})));
        model.addRow(new Vector<Object>(Arrays.asList(new Object[]{OrderPanel.orders.columnNames.get(5),order.getTotal()})));
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b){
            int windowWidth = this.getWidth();
            int windowHeight = this.getHeight();
            this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
        }
    }
}
