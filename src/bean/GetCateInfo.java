package bean;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GetCateInfo extends JPanel {
	private JTextField kind; // 种类 name
	private JTextField number;// 编号 number

	public GetCateInfo() {
		JLabel booknumber = new JLabel("编号");
		setKind(new JTextField(10));// 种类 name
		JLabel bookkind = new JLabel("类型");// stuname
		setNumber(new JTextField(10));// 编号 number
		this.setLayout(new FlowLayout());
		this.add(booknumber);//添加编号标签
		this.add(getNumber());//添加编号文本框
		this.add(bookkind);//添加种类标签
		this.add(getKind());//添加种类文本框
	}

	public String Getnumber() {
		String number1 = getNumber().getText();
		return number1;//返回获取的文本框的内容

	}

	public String Getkind() {
		String number2 = getKind().getText();
		return number2;
	}

	public JTextField getNumber() {
		return number;
	}

	public void setNumber(JTextField number) {
		this.number = number;
	}

	public JTextField getKind() {
		return kind;
	}

	public void setKind(JTextField kind) {
		this.kind = kind;
	}
}
