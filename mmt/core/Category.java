package mmt.core;

public abstract class Category implements java.io.Serializable{

	private double _discount;
	private String _type;


	protected Category ( double discount, String type) {
		_discount = discount;
		_type = type;
	}

	protected String getCategory(){
		return _type;
	}


	protected double getDiscount(){
		return _discount;
	}




}