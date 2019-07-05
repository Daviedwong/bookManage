package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainLog {
	JFrame LogInframe = new JFrame();
	JFrame LogInframe2 = new JFrame();
	public static void main(String[] args) {
		MainLog LogInframe = new MainLog();
		LogInframe.LogIn();
	}

	public void LogIn() {
		LogInframe.setTitle("ͼ�����Ϣ����ϵͳ��½����");
		LogInframe.setDefaultCloseOperation(3); // ����closeʱĬ��ִ�в���
		LogInframe.setSize(520,399);
		LogInframe.setLocationRelativeTo(null); // ���ô�����ʾ�ھ���λ��
		LogInframe.setResizable(true);
		LogInframe.setLayout(new FlowLayout()); // ����Ϊ��ʽ����
		JLabel L1 = new JLabel("�˺�:");
		JTextField textAccount = new JTextField(20); // �˺������ ������������򳤶�
		JLabel L2 = new JLabel("����:");
		JPasswordField TextPassword = new JPasswordField(20);// PasswordField ��ʹ�� ���������
		TextPassword.setEchoChar('*');
		
		JLabel Lpic = new JLabel();// ͼƬ���
		 Lpic.setIcon(new ImageIcon("C:\\Users\\56851\\Desktop\\javashixun\\5.jpg")); 
		 LogInframe.add(Lpic);
		 
		// ����ĵ����������Ƭ��ӵ�L����������
		// �������������ӵ�����������
		LogInframe.add(L1);
		LogInframe.add(textAccount);
		LogInframe.add(L2);
		LogInframe.add(TextPassword);
		JButton B1 = new JButton("��½");
		B1.setBackground(Color.decode("#E6F1FC"));
		                                           //E6F1FC
		ButtonListener li1 = new ButtonListener(textAccount, TextPassword);
		B1.addActionListener(li1); // ����¼��ť��Ӽ���
		LogInframe.add(B1);
		JButton B2 = new JButton("ȡ��");
		B2.setBackground(Color.decode("#E6F1FC"));
		LogInframe.add(B2);
		LogInframe.setVisible(true); // ��������Ϊ�ɼ�
	}

	///////////////////////////////////////////////////////////////
	// ����һ������ʵ�ֽӿ�
	public class ButtonListener implements java.awt.event.ActionListener { // ʵ��ActionListener �ӿ� implement
		public JTextField textAccount = new JTextField(); // ����
		public JPasswordField TextPassword = new JPasswordField();

		// һ���������
		public ButtonListener(JTextField textAccount, JPasswordField TextPassword) {// ���� �����ϵ��˺ſ�����򴫵���������
			this.textAccount = textAccount;
			this.TextPassword = TextPassword;
		}

		public void actionPerformed(ActionEvent e) {

			Connection conn;
			PreparedStatement preparedStatement;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
						"root", "mysql123456");
				String sql = "select * from account where account='" + textAccount.getText() + "'";
				preparedStatement = conn.prepareStatement(sql);
				ResultSet result = preparedStatement.executeQuery();
				String str1 = textAccount.getText();// ��ȡ�û�account
				String zhanghao = textAccount.getText(); // getText ���ڻ�ȡ����Ա������ڵĶ���
				String mima = String.valueOf(TextPassword.getPassword()); // ��ù���Ա������ڵĶ���
				// ----��½----//
				if ((zhanghao.equals("123456")) && (mima.equals("123456"))) {
					// ������
					BookCateManage cate= new BookCateManage();// �˴����Ե���һ����������еĺ��� ����һ������
					LogInframe.setVisible(false);
				}
				
			 else if (result.next()) {
					String str2 = result.getString("account");// ��ȡ���ݿ��˺�
					String str3 = result.getString("password");// ��ȡ���ݿ�����
					char[] ps = TextPassword.getPassword();
					String st3 = "";
					for (int i = 0; i < ps.length; i++)
						st3 += ps[i];
					if (str1.equals(str2) && (st3.equals(str3))) {
						// ͼ�����
						BooksManage manage= new BooksManage();// �˴����Ե���һ����������еĺ��� ����һ������
						manage.setSize(1201,740);
						LogInframe.setVisible(false);
					} else {
						String info = "����������벻��ȷ��ԭ������ǣ�\n" + "1���������룻\n" + "2��δ����С���̣�\n" + "3����Сдδ���֡�";
						JOptionPane.showMessageDialog(null, info, "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);

					}
				} else
					JOptionPane.showMessageDialog(null, "�û������ڣ�����", "ϵͳ��Ϣ", JOptionPane.WARNING_MESSAGE);
					

				preparedStatement.close();
				conn.close();
			} catch (ClassNotFoundException e1) {
				System.out.println("δ�ɹ���������");
				e1.printStackTrace();
			} catch (SQLException e1) {
				System.out.println("δ�ɹ������ݿ�");
				e1.printStackTrace();
			}
		};
	}
}
