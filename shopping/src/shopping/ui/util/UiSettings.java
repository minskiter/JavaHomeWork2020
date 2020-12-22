package shopping.ui.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 2020-12-14
 * Java HomeWork 2
 * @author minskiter
 */
public class  UiSettings {
    public final static String APP_NAME="零元购";

    public final static String VERSION = "v0.1";

    public final static String Author="Minskiter";

    public final static int LEFT = 500;

    public final static int TOP = 200;

    public final static int HEIGHT=500;

    public final static int WIDTH=800;

    public final static Image ICON = Toolkit.getDefaultToolkit().getImage("image/online-shopping.png");

    public final static Color MAIN_COLOR = new Color(0x00ffffff);

    public final static Color SIDE_COLOR = new Color(0x130f40);

    public final static ImageIcon LIST_ICON = new ImageIcon(new ImageIcon("image/food.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH));

    public final static ImageIcon LIST_ICON_DISABLE = new ImageIcon(new ImageIcon("image/food_1.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH));

    public final static ImageIcon SHOPPING_ICON = new ImageIcon(new ImageIcon("image/goods.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH));

    public final static ImageIcon ORDER_ICON = new ImageIcon(new ImageIcon("image/order.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH));

    public final static  ImageIcon IMAGE_ICON = new ImageIcon(new ImageIcon("image/online-shopping.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH));

    public final static ImageIcon EMPTY_SHOPPING = new ImageIcon("image/emptycar.png");

    public final static ImageIcon EMPTY_ORDER = new ImageIcon("image/emptyOrder.png");

    public final static Font FONT = new Font("Microsoft YaHei",Font.PLAIN,16);

}
