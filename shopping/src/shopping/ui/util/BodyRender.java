package shopping.ui.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BodyRender extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable t, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (row % 2 == 0) {
            setBackground(new Color(0xf1f2f6));
        } else {
            setBackground(Color.white);
        }
        setBorder(new EmptyBorder(0, 0, 0, 0));
        return super.getTableCellRendererComponent(t, value, isSelected,
                hasFocus, row, column);
    }
}
