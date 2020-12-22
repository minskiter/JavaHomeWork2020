package shopping.ui;

import shopping.ui.compoent.leavesButton;
import shopping.ui.util.UiSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel {

    static public leavesButton listButton;
    static public leavesButton shoppingButton;
    static public leavesButton shopCardButton;

    public SidePanel() {
        InitUI();
        AddButtons();
        AddListeners();
    }

    public void InitUI() {
        Dimension preferredSize = new Dimension(48, UiSettings.HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiSettings.SIDE_COLOR);
        this.setLayout(new GridLayout(1, 1));
    }

    public void AddButtons() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(UiSettings.SIDE_COLOR);
        leftPanel.setLayout(new FlowLayout(-2, -2, -4));
        // 列表按钮
        listButton = new leavesButton(UiSettings.LIST_ICON, UiSettings.LIST_ICON_DISABLE, UiSettings.LIST_ICON_DISABLE, "商品列表");
        leftPanel.add(listButton);
        // 购物车按钮
        shoppingButton = new leavesButton(UiSettings.SHOPPING_ICON, UiSettings.SHOPPING_ICON, UiSettings.SHOPPING_ICON, "购物车");
        leftPanel.add(shoppingButton);
        // 订单按钮
        shopCardButton = new leavesButton(UiSettings.ORDER_ICON, UiSettings.ORDER_ICON, UiSettings.ORDER_ICON, "订单");
        leftPanel.add(shopCardButton);
        this.add(leftPanel);
    }

    public void AddListeners() {
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listButton.setIcon(UiSettings.LIST_ICON_DISABLE);
                MainUI.Content.removeAll();
                MainUI.Content.add(MainUI.ListContent, BorderLayout.CENTER);
                MainUI.Content.updateUI();
            }
        });
        shoppingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listButton.setIcon(UiSettings.LIST_ICON);
                MainUI.Content.removeAll();
                MainUI.Content.add(MainUI.ShoppingContent,BorderLayout.CENTER);
                MainUI.Content.updateUI();
            }
        });
        shopCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listButton.setIcon(UiSettings.LIST_ICON);
                MainUI.Content.removeAll();
                MainUI.Content.add(MainUI.OrderContent,BorderLayout.CENTER);
                MainUI.Content.updateUI();
            }
        });
    }
}
