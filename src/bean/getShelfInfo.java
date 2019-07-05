package bean;
import java.awt.FlowLayout;
import javax.swing.*;

public class getShelfInfo extends JPanel{
	private JTextField number; // 书架号
	private JTextField type;// 书架类型
    private JTextField position;//书架位置
	public getShelfInfo() {
		JLabel shelfNumber = new JLabel("书架号");
		number = new JTextField(10);//
		JLabel shelfType = new JLabel("书架类型");//
		type = new JTextField(10);//
		JLabel shelfPosition = new JLabel("书架位置");
		position = new JTextField(10);
		
		this.setLayout(new FlowLayout());
		this.add(shelfNumber);//添加书架号标签
		this.add(number);//添加编号文本框
		this.add(shelfType);//添加种类标签
		this.add(type);//添加种类文本框
		this.add(shelfPosition);
		this.add(position);
	}

	public String Getnumber() {
		String number1 = number.getText();
		return number1;//返回获取的文本框的内容

	}

	public String Gettype() {
		String type1 = type.getText();
		return type1;
	}

	public String Getposition() {
		String position1 = position.getText();
		return position1;
	}
}
	