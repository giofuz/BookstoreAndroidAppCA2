package com.bookstore.app;

public class Computer {
	private int mId;
	private String make;
	private String model;
    private String os;
    private String quantity;
    private double price;
	private int studentId;

	public Computer(int id, String make, String model, String os, String quantity, double price, int studentId) {
		mId = id;
		this.make = make;
		this.model = model;
        this.os = os;
        this.quantity = quantity;
        this.price = price;
		this.studentId = studentId;
	}

    public Computer(String make, String model, String os, String quantity, double price, int studentId) {
        this(-1, make, model, os, quantity, price, studentId);
    }

	public int getId() { return mId; }
	public String getMake() { return make; }
	public String getModel() { return model; }
    public String getOs() { return os; }
    public String getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getStudentId() { return studentId; }

	public void setId(int id) { mId = id; }
	public void setMake(String make) { this.make = make; }
	public void setModel(String model) { this.model = model; }
    public void setOs(String os) { this.os = os; }
	public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
}