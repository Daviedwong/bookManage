package bean;
import java.awt.FlowLayout;
import javax.swing.*;

public class getShelfInfo extends JPanel{
	private JTextField number; // ��ܺ�
	private JTextField type;// �������
    private JTextField position;//���λ��
	public getShelfInfo() {
		JLabel shelfNumber = new JLabel("��ܺ�");
		number = new JTextField(10);//
		JLabel shelfType = new JLabel("�������");//
		type = new JTextField(10);//
		JLabel shelfPosition = new JLabel("���λ��");
		position = new JTextField(10);
		
		this.setLayout(new FlowLayout());
		this.add(shelfNumber);//�����ܺű�ǩ
		this.add(number);//��ӱ���ı���
		this.add(shelfType);//��������ǩ
		this.add(type);//��������ı���
		this.add(shelfPosition);
		this.add(position);
	}

	public String Getnumber() {
		String number1 = number.getText();
		return number1;//���ػ�ȡ���ı��������

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
	