package shopping.ui;

import shopping.model.Order;
import shopping.model.OrderList;
import shopping.ui.util.BodyRender;
import shopping.ui.util.HeaderRender;
import shopping.ui.util.UiSettings;

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

public class ShoppingPanel extends JPanel {
    public static OrderList shoppingCart = new OrderList();
    public static JTable shoppingTable;

    public ShoppingPanel() {
        InitUI();
        InitData();
    }

    public void InitUI() {
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
    }

    public void InitData() {
        final JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel emptyLabel = new JLabel(UiSettings.EMPTY_SHOPPING);
        emptyPanel.add(emptyLabel);

        final DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                if (column == 4) {
                    return true;//返回true则表明单元格可编辑
                } else return false;
            }
        };
        shoppingTable = new JTable(model);
        shoppingCart.setTable(shoppingTable);

        JScrollPane ProductListScrollPane = new JScrollPane(shoppingTable);
        ProductListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        ProductListScrollPane.setPreferredSize(new Dimension(580, 450));
        ProductListScrollPane.getViewport().setBackground(Color.white);

        final JPanel shoppingPanel = new JPanel(new BorderLayout());

        JTableHeader header = shoppingTable.getTableHeader();
        header.setFont(UiSettings.FONT);
        header.setBackground(UiSettings.SIDE_COLOR);
        header.setForeground(Color.white);
        TableColumnModel columnModel = header.getColumnModel();
        shoppingTable.setSelectionBackground(UiSettings.SIDE_COLOR);
        shoppingTable.setShowVerticalLines(false);
        shoppingTable.setRowHeight(30);
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            columnModel.getColumn(i).setHeaderRenderer(new HeaderRender());
            columnModel.getColumn(i).setCellRenderer(new BodyRender());
        }
        shoppingPanel.add(ProductListScrollPane, BorderLayout.WEST);

        // 加入删除单项，删除全部，结算等所有的款项
        JLabel title = new JLabel("购物车");
        title.setFont(UiSettings.FONT);
        title.setPreferredSize(new Dimension(100, 36));
        JPanel rightContent = new JPanel(new FlowLayout(0, 0, 0));
        rightContent.setBorder(new EmptyBorder(30, 10, 10, 10));
        Box buttonBox = Box.createVerticalBox();
        JButton deleteButton = new JButton("删除商品");

        JButton deleteAllButton = new JButton("清空商品");

        JButton settlementButton = new JButton("结算");

        JButton showFirstGoods = new JButton("第一个商品");

        JButton showLastGoods = new JButton("最后一个商品");
        buttonBox.add(deleteButton);
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(deleteAllButton);
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(settlementButton);
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(showFirstGoods);
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(showLastGoods);

        final JTextField searchGoods = new JTextField(1);
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(searchGoods);
        buttonBox.add(Box.createVerticalStrut(10));
        JButton searchButton = new JButton("搜索商品记录");
        buttonBox.add(searchButton);


        rightContent.add(title);
        rightContent.add(buttonBox);
        rightContent.setBackground(new Color(0xf1f2f6));
        shoppingPanel.add(rightContent, BorderLayout.CENTER);

        this.add(emptyPanel);
        final JPanel container = this;

        final OrderDialog orderDialog = new OrderDialog();

        // 当表单发生变化时
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 如果表单改变，则变化
                if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE) {
                    if (model.getRowCount() == 0) {
                        container.removeAll();
                        container.add(emptyPanel);
                        orderDialog.setVisible(false);
                        container.updateUI();
                    } else {
                        container.removeAll();
                        container.add(shoppingPanel);
                        container.updateUI();
                    }
                    shoppingTable.clearSelection();
                };

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selecteds = shoppingTable.getSelectedRows();
                // 倒序删除，因为序列是正序的
                for (int selected = selecteds.length - 1; selected >= 0; --selected) {
                    shoppingCart.remove(selecteds[selected]);
                }
                shoppingTable.clearSelection();
            }
        });



        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingTable.clearSelection();
                shoppingCart.clear();
            }
        });

        settlementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderPanel.orders.clone(shoppingCart); // 复制订单
                OrderPanel.orders.setShowTotal(true);
                shoppingCart.clear(); // 清空订单
                shoppingTable.clearSelection();
                SidePanel.shopCardButton.doClick();
            }
        });



        showFirstGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderDialog.setOrder(shoppingCart.get(0));
                orderDialog.setVisible(true);
            }
        });

        showLastGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderDialog.setOrder(shoppingCart.get(shoppingCart.size()-1));
                orderDialog.setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recordId = searchGoods.getText();
                if (!recordId.isEmpty()){
                    try{
                        int record = Integer.parseInt(recordId);
                        Order order = shoppingCart.search(record);
                        if (order!=null){
                            orderDialog.setOrder(order);
                            orderDialog.setVisible(true);
                        }else{
                            orderDialog.setVisible(false);
                        }
                    }catch (NumberFormatException numberFormatException){
                        numberFormatException.printStackTrace();
                        orderDialog.setVisible(false);
                    }
                }
            }
        });


    }
}
