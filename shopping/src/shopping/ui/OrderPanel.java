package shopping.ui;

import shopping.model.OrderList;
import shopping.ui.compoent.CsvFileChoose;
import shopping.ui.util.BodyRender;
import shopping.ui.util.HeaderRender;
import shopping.ui.util.UiSettings;
import shopping.util.FileData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class OrderPanel extends JPanel {

    static OrderList orders = new OrderList();
    static JTable orderTable;

    private OrderPanel self = this;

    public OrderPanel() {
        InitUI();
        InitData();
    }

    public void InitUI() {
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
    }

    public void InitData() {
        final JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel emptyLabel = new JLabel(UiSettings.EMPTY_ORDER);
        emptyPanel.add(emptyLabel);


        final DefaultTableModel model = new DefaultTableModel() { // 表格数据不可改

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(model);
        orders.setTable(orderTable);
        orderTable.setRowHeight(30);

        JScrollPane ProductListScrollPane = new JScrollPane(orderTable);
        ProductListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        ProductListScrollPane.setPreferredSize(new Dimension(380, 450));
        ProductListScrollPane.getViewport().setBackground(Color.white);

        final JPanel orderPanel = new JPanel(new BorderLayout());

        JTableHeader header = orderTable.getTableHeader();
        header.setFont(UiSettings.FONT);
        header.setBackground(UiSettings.SIDE_COLOR);
        header.setForeground(Color.white);
        TableColumnModel columnModel = header.getColumnModel();
        orderTable.setSelectionBackground(UiSettings.SIDE_COLOR);
        orderTable.setShowVerticalLines(false);
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            columnModel.getColumn(i).setHeaderRenderer(new HeaderRender());
            columnModel.getColumn(i).setCellRenderer(new BodyRender());
        }

        orderPanel.add(ProductListScrollPane, BorderLayout.CENTER);

        JPanel buttonBox = new JPanel(new BorderLayout());
        // 添加导出的按钮
        JButton exportButton = new JButton("导出订单");

        JButton importButton = new JButton("导入订单");
        importButton.setPreferredSize(new Dimension((UiSettings.WIDTH - 48) / 2, 25));
        exportButton.setPreferredSize(new Dimension((UiSettings.WIDTH - 48) / 2, 25));
        buttonBox.add(importButton, BorderLayout.WEST);
        buttonBox.add(exportButton, BorderLayout.EAST);

        orderPanel.add(buttonBox, BorderLayout.SOUTH);

        this.add(emptyPanel);

        final JPanel container = this;

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.INSERT) {
                if (model.getRowCount() > 0) {
                    container.removeAll();
                    container.add(orderPanel);
                    container.updateUI();
                } else {
                    container.removeAll();
                    container.add(emptyPanel);
                    container.updateUI();
                }
            }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new FileData('.' + File.separator + "data" + File.separator + "order-"+new SimpleDateFormat("yyyy-MM-dd-HH-dd-ss").format(new Date()) +".csv").Export(orders.toVector(), orders.columnNames)) {
                    JOptionPane.showConfirmDialog(null, "导出成功", "导出订单", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, UiSettings.IMAGE_ICON);
                } else {
                    JOptionPane.showConfirmDialog(null, "导出失败", "导出订单", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, UiSettings.IMAGE_ICON);
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CsvFileChoose choose = new CsvFileChoose("."+ File.separator + "data" ,self,false);
                if (choose.result==JFileChooser.APPROVE_OPTION){
                    File file = choose.getSelectedFile();
                    Vector<Vector<Object>> data = new FileData(file.getPath()).Import();
                    data.remove(0);
                    orders.clone(data);
                }
            }
        });



    }
}
