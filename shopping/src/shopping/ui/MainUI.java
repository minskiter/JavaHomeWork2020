package shopping.ui;

import shopping.ui.util.UiSettings;
import shopping.model.ProductList;
import shopping.util.FileData;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;

public class MainUI{
    public static JFrame frame;

    public static ProductList products;

    public static JPanel Content,ListContent,ShoppingContent,OrderContent;


    public MainUI() {
        // 设置默认的Window 10 默认样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initGobalFont(new Font("Microsoft Yahei light",Font.PLAIN,16));
        InitData();
        InitUI();
    }

    /**
     * 初始化UI界面
     */
    private void InitUI(){
        // 初始化大小
        frame = new JFrame();

        frame.setResizable(false);

        frame.setTitle(UiSettings.APP_NAME);

        frame.setFont(UiSettings.FONT);

        frame.pack();
        frame.setVisible(true);

        // 设置应用的长宽
        frame.setBounds(UiSettings.LEFT,UiSettings.TOP,UiSettings.WIDTH,UiSettings.HEIGHT);

        // 这是图标
        frame.setIconImage(UiSettings.ICON);

        frame.setBackground(UiSettings.MAIN_COLOR);

        // 加入主要的界面
        JPanel mainJPanel = new JPanel(true);

        mainJPanel.setBackground(Color.white);

        mainJPanel.setLayout(new BorderLayout());

        JPanel sideBar = new SidePanel();

        mainJPanel.add(sideBar,BorderLayout.WEST);

        Content = new JPanel(true);

        Content.setBackground(Color.white);

        Content.setLayout(new BorderLayout());

        mainJPanel.add(Content,BorderLayout.CENTER);

        ListContent = new GoodsPanel();

        ShoppingContent = new ShoppingPanel();

        OrderContent = new OrderPanel();

        // 默认加入商城页
        Content.add(ListContent);

        frame.add(mainJPanel);


    }

    void InitData(){
        // 从文件中导入数据
        products = new ProductList(new FileData("."+ File.separator+"data"+File.separator+"products.csv").Import());
    }

    public static void initGobalFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource) {
//                System.out.println(key);
                UIManager.put(key, fontResource);
            }
        }
    }
}
