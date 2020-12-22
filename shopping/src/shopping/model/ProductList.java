package shopping.model;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * 产品列表
 */
public class ProductList extends ArrayList<Product> {

    public ProductList() {
        super();
    }

    public ProductList(Vector<Vector<Object>> vectors) {
        super();
        for (Vector<Object> vector : vectors) {
            Product pro = new Product(vector);
            add(pro);
        }
    }

    TableModelListener listener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            // 响应表单被用户所修改
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS) {
                // 一般用户一次只会修改一个地方
                final DefaultTableModel model = (DefaultTableModel) table.getModel();
                ProductList.super.set(e.getFirstRow(), new Product((Vector<Object>) (model.getDataVector().get(e.getFirstRow()))));
            }
        }
    };

    public void setTable(JTable table) {
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(this.toVector(), columnNames);
        table.setModel(model);
        // 设置Table改变时触发列表改变
        model.addTableModelListener(listener);
        this.table = table;
    }

    /**
     * 深拷贝对象
     * @param copyObject 拷贝对象
     */
    public void clone(ProductList copyObject) {
        this.clear();
        for (Product obj:copyObject){
            this.add(new Product(obj.toVector()));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeTableModelListener(listener);// 取消注册的监听器
        }
    }

    @Override
    public Product get(int index) {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            return new Product((Vector<Object>) model.getDataVector().get(index));
        }
        return super.get(index);
    }

    public Vector<Vector<Object>> toVector() {
        Vector<Vector<Object>> objs = new Vector<Vector<Object>>();
        for (Product product : this) {
            objs.add(product.toVector());
        }
        return objs;
    }

    // 绑定的表单
    JTable table;

    public ArrayList<Method> updateFuncList = new ArrayList<Method>();

    Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"编号", "名称", "单价"}));

    /**
     * 搜索过滤商品
     *
     * @param query 商品编号或者商品的名称
     * @return 商品列表
     */
    public ProductList filter(String query) {
        if (query==null) return null;
//        this.query = query; TODO: 可能是并发修改导致出错？
        ProductList filterData = new ProductList();
        for (Product a : this) {
            if (a.getName().indexOf(query) != -1) {
                filterData.add(a);
            }
        }
        for (Product a : this) {
            if (a.getId().indexOf(query) != -1 && !filterData.contains(a)) {
                filterData.add(a);
            }
        }
        return filterData;
    }

    private void updateFunc(String event) {
        // 执行自定义更新函数
        for (Method func : updateFuncList) {
            try {
                func.invoke(null, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean add(Product product) {
        // 更新表单
        boolean result = super.add(product);
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(product.toVector());
            table.updateUI();
        }
        updateFunc(Events.Insert);
        return result;
    }

    @Override
    public Product remove(int index) {
        Product result = super.remove(index);
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(index);
            table.updateUI();
        }
        updateFunc(Events.Delete);
        return result;
    }

    @Override
    public boolean remove(Object o) {
        int index = this.indexOf(o);
        if (index == -1) return false;
        return this.remove(index) == o;
    }

    @Override
    public Product set(int index, Product element) {
        Product result = super.set(index, element);
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Vector<Object> objects = element.toVector();
            model.removeRow(index);
            model.insertRow(index, objects);
            table.updateUI();
        }
        updateFunc(Events.Change);
        return result;
    }

    @Override
    public void clear() {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            table.updateUI();
        }
        updateFunc(Events.Delete);
        super.clear();
    }
}
