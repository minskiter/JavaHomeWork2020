package shopping.ui.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HeaderRender extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable t, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(UiSettings.SIDE_COLOR);
        setPreferredSize(new Dimension(120, 38));
        setFont(UiSettings.FONT);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setForeground(Color.white);
        setHorizontalAlignment(JLabel.CENTER);
        return super.getTableCellRendererComponent(t, value, isSelected,
                hasFocus, row, column);
    }
}
