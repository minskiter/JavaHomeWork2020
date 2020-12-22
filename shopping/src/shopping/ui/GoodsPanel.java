package shopping.ui;

import shopping.model.OrderList;
import shopping.model.ProductList;
import shopping.ui.util.BodyRender;
import shopping.ui.util.HeaderRender;
import shopping.ui.util.UiSettings;
import shopping.model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Pattern;

public class GoodsPanel extends JPanel {

    public JTable goodsTable;
    public ProductList mall = new ProductList();

    public GoodsPanel() {
        InitUI();
        InitData();
    }

    public void InitUI() {
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
    }

    public void InitData() {
        // 初始化商城表格
        final DefaultTableModel model = new DefaultTableModel() { // 表格数据不可改
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        goodsTable = new JTable(model);
        goodsTable.setShowVerticalLines(false);
        goodsTable.setRowHeight(30);

        mall.setTable(goodsTable); // 绑定表格
        mall.clone(MainUI.products);

        JScrollPane ProductListScrollPane = new JScrollPane(goodsTable);
        ProductListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        ProductListScrollPane.setPreferredSize(new Dimension(380, 450));
        ProductListScrollPane.getViewport().setBackground(Color.white);

        // 设置表头样式
        JTableHeader header = goodsTable.getTableHeader();
        header.setFont(UiSettings.FONT);
        header.setBackground(UiSettings.SIDE_COLOR);
        header.setForeground(Color.white);
        TableColumnModel columnModel = header.getColumnModel();
        goodsTable.setSelectionBackground(UiSettings.SIDE_COLOR);
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            columnModel.getColumn(i).setHeaderRenderer(new HeaderRender());
            columnModel.getColumn(i).setCellRenderer(new BodyRender());
        }

        this.add(ProductListScrollPane, BorderLayout.WEST);


        // 初始化购买表单
        JPanel BuyForm = new JPanel(true);
        BuyForm.setBackground(new Color(0xf1f2f6));
        BuyForm.setLayout(new FlowLayout(0, 0, 0));
        BuyForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        //标题
        JLabel title = new JLabel("商品列表");
        title.setPreferredSize(new Dimension(380, 50));
        title.setFont(UiSettings.FONT);
        title.setBorder(new EmptyBorder(10, 0, 0, 0));

        Box searchBox = Box.createHorizontalBox();
        JLabel searchLabel = new JLabel("搜索商品: ");
        searchBox.setPreferredSize(new Dimension(295, 35));
        searchBox.setBorder(new EmptyBorder(0, 0, 10, 0));

        //搜索框
        final JTextField searchField = new JTextField(15);
        searchBox.add(searchLabel);
        searchBox.add(searchField);

        BuyForm.add(title);
        BuyForm.add(searchBox);

        Box BuyButtonBox = Box.createHorizontalBox();
        JButton buyButton = new JButton("加入购物车");
//        buyButton.setPreferredSize(new Dimension(380, 35));
//        BuyButtonBox.setPreferredSize(new Dimension(300,35));
        BuyButtonBox.add(buyButton);
        BuyForm.add(BuyButtonBox);


        // 添加商品的按钮
        Box addForm = Box.createVerticalBox();
        JLabel goodName = new JLabel("商品名称");
        JLabel goodPrice = new JLabel("商品价格");
        final JTextField goodNameText = new JTextField(15);
        final JTextField goodPriceText = new JTextField(15);
        Box goodNameBox = Box.createHorizontalBox();
        goodNameBox.add(goodName);
        goodNameBox.add(Box.createHorizontalStrut(10));
        goodNameBox.add(goodNameText);
        goodNameBox.add(Box.createHorizontalStrut(100));
        Box goodPriceBox = Box.createHorizontalBox();
        goodPriceBox.add(goodPrice);
        goodPriceBox.add(Box.createHorizontalStrut(10));
        goodPriceBox.add(goodPriceText);
        goodPriceBox.add(Box.createHorizontalStrut(100));
        JButton addButton = new JButton("添加商品");

        Box goodAddBox = Box.createHorizontalBox();
        goodAddBox.add(addButton);
        goodAddBox.add(Box.createHorizontalStrut(300));
        addForm.add(goodNameBox);
        addForm.add(Box.createVerticalStrut(10));
        addForm.add(goodPriceBox);
        addForm.add(Box.createVerticalStrut(10));
        addForm.add(goodAddBox);
        addForm.setBorder(new EmptyBorder(30,0,0,0));

        BuyForm.add(addForm);
        this.add(BuyForm, BorderLayout.CENTER);

        // 添加响应事件
        final Document searchDoc = searchField.getDocument();
        searchDoc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String query = searchField.getText();
                mall.clone(MainUI.products.filter(query));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String query = searchField.getText();
                mall.clone(MainUI.products.filter(query));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        // 添加到购物车
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 如果没有选择，则直接退出
                if (goodsTable.getSelectedRowCount() <= 0) {
                    return;
                }
                String InputValue = (String) JOptionPane.showInputDialog(null, "请输入商品的数量", "商品数量", JOptionPane.INFORMATION_MESSAGE, UiSettings.IMAGE_ICON, null, null);
                if (InputValue == null || InputValue.isEmpty()) {
                    return;
                }
                int Input = 0;
                try {
                    Input = Integer.parseInt(InputValue);
                } catch (NumberFormatException ex) {
                    return;
                }

                if (Input > 0) {
                    // 添加到购物车
                    int[] selectRows = goodsTable.getSelectedRows();
                    for (int selectRow : selectRows) {
                        Product selectProduct = mall.get(selectRow);
                        OrderList cart = ShoppingPanel.shoppingCart;
                        cart.add(selectProduct, Input);
                    }
                    SidePanel.shoppingButton.doClick();
                }
                goodsTable.clearSelection();
            }
        });

        // 添加商品
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = goodNameText.getText();
                String price = goodPriceText.getText();
                Pattern pattern = Pattern.compile("^[0-9]+$");
                if (pattern.matcher(price).matches()) {
                    // 计算最后一个的编号
                    int priceInt = Integer.parseInt(price);
                    int id = Integer.parseInt(MainUI.products.get(MainUI.products.size() - 1).getId());
                    Product pro = new Product(String.valueOf(id + 1), name, priceInt, 0);
                    MainUI.products.add(pro);
                    String query = searchField.getText();
                    mall.clone(MainUI.products.filter(query));
                } else {
                    goodPriceText.setText("");
                    JOptionPane.showConfirmDialog(null, "商品价格必须是数字", "价格错误", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, UiSettings.IMAGE_ICON);
                }
            }
        });
    }

}
