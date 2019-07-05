package bean;

import java.awt.*;

import javax.swing.*;

public class GetBooksInfo extends JPanel {
	public JTextField no;// number
	public JTextField name;// name
	public JTextField price;// 价格
	public JTextField amount;// 总数
	public JTextField cate;// 编号

	public GetBooksInfo() {
		JLabel bookNo = new JLabel("书籍号");
		no= new JTextField(10);

		JLabel bookName = new JLabel("书籍名");
		name= new JTextField(10);

		JLabel bookPrice = new JLabel("价格");
		price=new JTextField(10);

		JLabel bookAmount = new JLabel("馆藏册数");
		amount= new JTextField(10);

		JLabel bookCate = new JLabel("编号");
		cate= new JTextField(10);

		this.setLayout(new FlowLayout());
		this.add(bookNo);//决定表格显示
		this.add(no);
		this.add(bookName);
		this.add(name);
		this.add(bookPrice);
		this.add(price);
		this.add(bookAmount);
		this.add(amount);
		this.add(bookCate);
		this.add(cate);
	}

	public String Getno() {
		String no1 = no.getText();
		return no1;
	}

	public String Getname() {
		String name1 = name.getText();
		return name1;
	}

	public String Getprice() {
		String price1 = price.getText();
		return price1;
	}

	public String Getamount() {
		String amount1 = amount.getText();
		return amount1;
	}

	public String Getcate() {
		String cate1 = cate.getText();
		return cate1;
	}

	}
