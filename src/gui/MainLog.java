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
		LogInframe.setTitle("图书馆信息管理系统登陆界面");
		LogInframe.setDefaultCloseOperation(3); // 发起close时默认执行操作
		LogInframe.setSize(520,399);
		LogInframe.setLocationRelativeTo(null); // 设置窗体显示在居中位置
		LogInframe.setResizable(true);
		LogInframe.setLayout(new FlowLayout()); // 设置为流式布局
		JLabel L1 = new JLabel("账号:");
		JTextField textAccount = new JTextField(20); // 账号输入框 括号内是输入框长度
		JLabel L2 = new JLabel("密码:");
		JPasswordField TextPassword = new JPasswordField(20);// PasswordField 的使用 密码输入框
		TextPassword.setEchoChar('*');
		
		JLabel Lpic = new JLabel();// 图片组件
		 Lpic.setIcon(new ImageIcon("C:\\Users\\56851\\Desktop\\javashixun\\5.jpg")); 
		 LogInframe.add(Lpic);
		 
		// 将你的电脑里面的照片添加到L这个组件上来
		// 将其他组件都添加到窗体上面来
		LogInframe.add(L1);
		LogInframe.add(textAccount);
		LogInframe.add(L2);
		LogInframe.add(TextPassword);
		JButton B1 = new JButton("登陆");
		B1.setBackground(Color.decode("#E6F1FC"));
		                                           //E6F1FC
		ButtonListener li1 = new ButtonListener(textAccount, TextPassword);
		B1.addActionListener(li1); // 给登录按钮添加监听
		LogInframe.add(B1);
		JButton B2 = new JButton("取消");
		B2.setBackground(Color.decode("#E6F1FC"));
		LogInframe.add(B2);
		LogInframe.setVisible(true); // 窗体设置为可见
	}

	///////////////////////////////////////////////////////////////
	// 定义一个类来实现接口
	public class ButtonListener implements java.awt.event.ActionListener { // 实现ActionListener 接口 implement
		public JTextField textAccount = new JTextField(); // 传参
		public JPasswordField TextPassword = new JPasswordField();

		// 一个画板对象
		public ButtonListener(JTextField textAccount, JPasswordField TextPassword) {// 重载 窗体上的账号框，密码框传到监听上来
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
				String str1 = textAccount.getText();// 获取用户account
				String zhanghao = textAccount.getText(); // getText 用于获取管理员输入框内的东西
				String mima = String.valueOf(TextPassword.getPassword()); // 获得管理员密码框内的东西
				// ----登陆----//
				if ((zhanghao.equals("123456")) && (mima.equals("123456"))) {
					// 类别界面
					BookCateManage cate= new BookCateManage();// 此处可以调用一个画板对象中的函数 弹出一个界面
					LogInframe.setVisible(false);
				}
				
			 else if (result.next()) {
					String str2 = result.getString("account");// 获取数据库账号
					String str3 = result.getString("password");// 获取数据库密码
					char[] ps = TextPassword.getPassword();
					String st3 = "";
					for (int i = 0; i < ps.length; i++)
						st3 += ps[i];
					if (str1.equals(str2) && (st3.equals(str3))) {
						// 图书界面
						BooksManage manage= new BooksManage();// 此处可以调用一个画板对象中的函数 弹出一个界面
						manage.setSize(1201,740);
						LogInframe.setVisible(false);
					} else {
						String info = "你输入的密码不正确，原因可能是：\n" + "1、忘记密码；\n" + "2、未开启小键盘；\n" + "3、大小写未区分。";
						JOptionPane.showMessageDialog(null, info, "系统信息", JOptionPane.INFORMATION_MESSAGE);

					}
				} else
					JOptionPane.showMessageDialog(null, "用户不存在！！！", "系统信息", JOptionPane.WARNING_MESSAGE);
					

				preparedStatement.close();
				conn.close();
			} catch (ClassNotFoundException e1) {
				System.out.println("未成功加载驱动");
				e1.printStackTrace();
			} catch (SQLException e1) {
				System.out.println("未成功打开数据库");
				e1.printStackTrace();
			}
		};
	}
}
