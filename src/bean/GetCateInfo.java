package bean;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GetCateInfo extends JPanel {
	private JTextField kind; // ���� name
	private JTextField number;// ��� number

	public GetCateInfo() {
		JLabel booknumber = new JLabel("���");
		setKind(new JTextField(10));// ���� name
		JLabel bookkind = new JLabel("����");// stuname
		setNumber(new JTextField(10));// ��� number
		this.setLayout(new FlowLayout());
		this.add(booknumber);//��ӱ�ű�ǩ
		this.add(getNumber());//��ӱ���ı���
		this.add(bookkind);//��������ǩ
		this.add(getKind());//��������ı���
	}

	public String Getnumber() {
		String number1 = getNumber().getText();
		return number1;//���ػ�ȡ���ı��������

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
