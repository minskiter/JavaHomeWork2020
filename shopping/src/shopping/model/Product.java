package shopping.model;

import java.util.Vector;

/**
 * 商品类
 */
public class Product {
    private String id; // 商品的编号
    private String name; // 名称
    private int price; // 价格
    private int num; // 商品数量

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Product(String id, String name, int price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public Product(String id, String name, int price) {
        this(id, name, price,-1);
    }

    public Product(Vector<Object> obj){
//        this(obj.get(0).toString(),obj.get(1).toString(),Integer.parseInt(new StringBuffer().delete(obj.get(2).toString().length()-2,obj.get(2).toString().length()-2).toString()),Integer.parseInt(obj.get(3).toString()));
        this(obj.get(0).toString(),obj.get(1).toString(),Integer.parseInt(obj.get(2).toString()));
    }

    public Vector<Object> toVector(){
        // TODO: 这里先简单处理,价钱用分代替
        Vector<Object> obj = new Vector<Object>();
//        String priceS = String.format("%03",price);
//        obj.add(id);obj.add(name);obj.add(new StringBuffer(priceS).insert(priceS.length()-2,".").toString());obj.add(num);
        obj.add(id);obj.add(name);obj.add(price);obj.add(num);
        return obj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
