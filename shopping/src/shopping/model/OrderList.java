package shopping.model;

import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class OrderList extends ArrayList<Order> {

    JTable table;

    OrderList self = this;

    private TableModelListener listener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            // 响应表单被用户所修改
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS) {
                // 一般用户一次只会修改一个地方
                final DefaultTableModel model = (DefaultTableModel) table.getModel();
                self.set(e.getFirstRow(), new Order((Vector<Object>) model.getDataVector().get(e.getFirstRow())));
            }
        }
    };

    public void setShowTotal(boolean showTotal) {
        if (!showTotal && this.showTotal) {
            this.showTotal = showTotal;
            if (table != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(this.size());
            }
        } else if (showTotal && !this.showTotal) {
            this.showTotal = showTotal;
            if (table != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Vector<Object>(Arrays.asList(new String[]{null, null, null, null, "总计", String.valueOf(total)})));
            }
        }
    }

    private boolean showTotal = false;

    private int total = 0;

    public Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"记录号", "编号", "名称", "单价", "数量", "总价"}));

    public void setTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(this.toVector(), columnNames);
        model.addTableModelListener(listener);
        updateTotalView();
        this.table = table;
    }

    private void updateTotalView() {
        if (showTotal) {
            setShowTotal(false);
            setShowTotal(true);
        }
    }

    public void clone(Vector<Vector<Object>> orders) {
        this.clear();
        total = 0;
        for (Vector<Object> orderObj : orders
        ) {
            Order order = new Order(orderObj);
            total += order.getTotal();
            this.add(order);
        }
    }

    public void clone(OrderList orders) {
        this.clear();
        total = 0;
        for (Order order : orders
        ) {
            total += order.getTotal();
            this.add(order);
        }
    }

    public Vector<Vector<Object>> toVector() {
        Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
        for (Order order : this) {
            vector.add(order.toVector());
        }
        return vector;
    }

    @Override
    protected void finalize() throws Throwable {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeTableModelListener(listener);
        }
        super.finalize();
    }

    @Override
    public Order set(int index, Order element) {
        total -= this.get(index).getTotal();
        total += element.getTotal();
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(index);
            model.insertRow(index, element.toVector());
            updateTotalView();
            table.updateUI();
        }
        return super.set(index, element);
    }

    @Override
    public boolean add(Order order) {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(order.toVector());
            total += order.getTotal();
            updateTotalView();
            table.updateUI();
        }
        return super.add(order);
    }

    /**
     * 添加物品
     *
     * @param product 物品
     * @param num     数量
     * @return 是否添加成功
     */
    public boolean add(Product product, int num) {
        int lastOrderId = 1;
        if (this.size() > 0)
            lastOrderId = this.get(this.size() - 1).getRecordId() + 1;
        int index = indexOf(product);
        if (index != -1) {
            lastOrderId = this.get(index).getRecordId();
            this.remove(index); // 删除过去的
        }
        Order order = new Order(lastOrderId, product.getId(), product.getName(), product.getPrice(), num);
        return this.add(order);
    }

    @Override
    public int indexOf(Object o) {
        Product order = (Product) o;// 解包,由于Product是Order的基类，所以内存同样可以使用
        int index = 0;
        for (Order e : this) {
            if (e.getId() == order.getId()) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public Order search(int recordId) {
        for (Order item : this) {
            if (item.getRecordId() == recordId) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Order remove(int index) {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(index);
            table.updateUI();
        }
        return super.remove(index);
    }

    @Override
    public void clear() {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (showTotal) {
                model.setRowCount(1);
            }else{
                model.setRowCount(0);
            }
            table.updateUI();
        }
        super.clear();
    }
}
