package bean;

import java.awt.*;

import javax.swing.*;

public class GetBooksInfo extends JPanel {
	public JTextField no;// number
	public JTextField name;// name
	public JTextField price;// �۸�
	public JTextField amount;// ����
	public JTextField cate;// ���

	public GetBooksInfo() {
		JLabel bookNo = new JLabel("�鼮��");
		no= new JTextField(10);

		JLabel bookName = new JLabel("�鼮��");
		name= new JTextField(10);

		JLabel bookPrice = new JLabel("�۸�");
		price=new JTextField(10);

		JLabel bookAmount = new JLabel("�ݲز���");
		amount= new JTextField(10);

		JLabel bookCate = new JLabel("���");
		cate= new JTextField(10);

		this.setLayout(new FlowLayout());
		this.add(bookNo);//���������ʾ
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
