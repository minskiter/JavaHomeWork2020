package shopping.model;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.Vector;


public class Order extends Product{

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	private int recordId;

	public int getTotal() {
		return total;
	}

	private int total;

	public Order(int recordId,String id, String name, int price, int num) {
		super(id, name, price, num);
		this.recordId=recordId;
		if (num>0)
			this.total = price*num;
		else
			this.total = 0;
	}

	public Order(Vector<Object> obj){
		this(Integer.parseInt(obj.get(0).toString()),obj.get(1).toString(),obj.get(2).toString(),Integer.parseInt(obj.get(3).toString()),Integer.parseInt(obj.get(4).toString()));
	}

	@Override
	public void setPrice(int price){
		super.setPrice(price);
		this.total = price*getNum();
	}

	@Override
	public Vector<Object> toVector() {
		Vector<Object> obj =  super.toVector();
		obj.add(0,recordId);
		obj.add(total);
		return obj;
	}
}

