package gui;



import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import bean.GetCateInfo;
import util.GetConnection;



public class BookCateManage extends JFrame implements ActionListener {
	int row;// 获取数据表行数
	JTabbedPane tab = new JTabbedPane();// 选项卡组件
	JPanel mainpanel = new JPanel();// 面板
	JScrollPane viewlistscroll;// 显示所有数据的滚动面板
	JScrollPane viewscroll;// 显示单条数据的滚动面板
	JPanel updatepanel = new JPanel();// 更新面板
	GetCateInfo bookinfo1 = new GetCateInfo();// 实例化获取number和kind的类
	GetCateInfo bookinfo2 = new GetCateInfo();
	JButton dataButton = new JButton("删除");// 删除按钮
	Object data[][], data1[][], data2[][];// 定义三个数组对象
	Object colname[] = { "编号", "类型" };// 包含两个变量的表头
	JTable booktable, qureytable, qureylist;// 定义表格
	JButton addButton = new JButton("添加");// 定义添加按钮
	JButton modifybutton = new JButton("修改");// 修改按钮
	JButton updatebutton = new JButton("更新");// 更新按钮
	JButton querybutton = new JButton("查询");// 查询按钮
	String sno;// 定义字符串用于储存文本框中输入的内容
	JTextField snotext = new JTextField(10);// 用于输入删除信息的文本框

	public BookCateManage() {
		super("图书管图书类别界面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 定义可关闭
		setResizable(true);// 可更改大小
		viewData();//
		addData();
		deleteData();
		modifyData();
		add(tab);// 添加选项卡组件
		setSize(500,500);
		setVisible(true);
	}

	//判断编号是否存在
	public boolean queryExist(String id) {
		boolean b = false;
		try {
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookcate where 编号=" + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				b = true;
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return b;
	}

	// ------------添加数据-------------------//
	public void addData() {
		JButton adddata_clear = new JButton("清除");// 定义按钮
		mainpanel.add(bookinfo1);// 将bookinfo1中的组件加到mainpanel面板上
		addButton.addActionListener(this);// 自我监听
		bookinfo1.add(addButton);// 添加添加按钮到文本框前
		bookinfo1.add(adddata_clear);// 添加删除按钮到文本框前
		adddata_clear.addActionListener(new ActionListener() {// 点击清除按钮，清除文本框中的内容
			public void actionPerformed(ActionEvent d) {
				bookinfo1.getNumber().setText("");
				bookinfo1.getKind().setText("");
			}
		});
		tab.add("添加数据", bookinfo1);// 将上述组件加到添加数据的组件下
	}

	// ---------------修改数据------------------//
	public void modifyData() {
		JButton update_clear = new JButton("清除");
		mainpanel.add(bookinfo2);// 将BookInfo中的组件加到mainpanel中
		bookinfo2.add(modifybutton);// 添加更改按钮到bookinfo2的文本框下
		bookinfo2.add(querybutton);// 添加查询按钮
		querybutton.addActionListener(new ActionListener() {// 事件监听
			public void actionPerformed(ActionEvent d) {// 事件回应
				String no =bookinfo2.getNumber().getText();
				if (bookinfo2.getNumber().getText() != null &&queryExist(no)&& d.getSource() == modifybutton)// 文本框不空且按钮被点击
					bookinfo2.getNumber().setEditable(false);// 将输入的文本框变为不可编辑
				bookinfo2.getKind().setEditable(true);// 将类型文本框变为可用,用来修改类型
				sno = bookinfo2.getNumber().getText();
				if(queryExist(no)==false)
					JOptionPane.showMessageDialog(null, "图书编号不存在");
				if (bookinfo2.getNumber().getText().isEmpty())// 文本框是否为空的判断
					JOptionPane.showMessageDialog(null, "图书编号number不能为空");
				else
					try {
						ResultSet rs;
						Connection conn = new GetConnection().getConn();// 实例一个连接数据库的conn对象
						Statement stmt = conn.createStatement();
						String sql = "select * from bookcate where 编号=" + sno;// 选择与sno相同的信息
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							bookinfo2.getKind().setText(rs.getString(2));// 将种类的文本框的内容显示为数据库符合编号的值
						}
						rs.close();
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		bookinfo2.add(modifybutton);// 添加更改按钮
		modifybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent d) {
				if (bookinfo2.getNumber().getText().isEmpty())
					JOptionPane.showMessageDialog(null, "图书编号number不能为空");
				else
					try {
						bookinfo2.getKind().setEditable(false);
						String no = bookinfo2.getNumber().getText();// 将number文本框的内容存到no中
						String kind = bookinfo2.getKind().getText();
						Connection conn = new GetConnection().getConn();
						Statement stmt = conn.createStatement();
						String sql = "update bookcate set 类型='" + kind + "'where 编号='" + no + "'";
						String sql1 = "update bookcate set 编号='" + no + "'where 类型='" + kind + "'";
						stmt.executeUpdate(sql);
						stmt.executeUpdate(sql1);
						JOptionPane.showMessageDialog(null, "修改成功");
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						BookCateManage add1 = new BookCateManage();
						add1.setLocationRelativeTo(null);// 居中
						add1.setVisible(true);// 可见
						add1.setSize(500, 500);//数据更改后跳出窗体大小
						setVisible(false);// 更改后该窗体不可见
						bookinfo2.getNumber().setEditable(true);
					}
			}

		});
		/* 点击清除按钮后设为初始状态...可编辑...不可编辑 */
		bookinfo2.add(update_clear);
		update_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent d) {
				bookinfo2.getNumber().setEditable(true);
				bookinfo2.getKind().setEditable(false);
				bookinfo2.getNumber().setText("");
				bookinfo2.getKind().setText("");

			}

		});
		bookinfo2.getKind().setEditable(false);
		tab.add("修改数据", bookinfo2);// 添加选项卡
	}
	// --------------------删除数据----------------------------//
	public void deleteData() {
		JLabel snolabel = new JLabel("编号");// 编号标签
		JButton delete_query = new JButton("查询");// 查询按钮
		JButton delete_clear = new JButton("清除");// 清除按钮
		mainpanel.add(snolabel);// 添加编号标签
		mainpanel.add(snotext);//
		mainpanel.add(dataButton);// 编号文本框
		mainpanel.add(delete_clear);// 清除按钮
		mainpanel.add(delete_query);// 删除前的查询按钮
		data2 = new Object[1][2];// 创建显示数据
		qureylist = new JTable(data2, colname);// 实例表
		JScrollPane jsp = new JScrollPane(qureylist);// 将表的面板设置为可滚动型
		mainpanel.add(jsp);
		qureylist.setVisible(true);// 将表线条设为可见
		qureylist.setFillsViewportHeight(true);// 将表设置为高度可以填充封闭视口
		////
		delete_query.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent d) {
				sno = snotext.getText();// 获取文本框内容
				if (snotext.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "编号不能为空");
				else
					try {
						ResultSet rs1;
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sql1 = "select * from bookcate where 编号=" + sno;//
						rs1 = stmt1.executeQuery(sql1);
						int i = 0;
						if (rs1.next()) {
							data2[i][0] = rs1.getString(1);// 获取符合要求的第一列
							data2[i][1] = rs1.getString(2);//获取符合要求的第二列
							qureylist.setVisible(true);
						} else
							JOptionPane.showMessageDialog(null, "无记录");
						qureylist.setVisible(false);
						qureylist.setVisible(true);
						rs1.close();
						conn.close();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

			}

		});
		dataButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent d) {
				sno = snotext.getText();
				if (snotext.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "编号不能为空");
				else
					try {
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sql1 = "delete from bookcate where 编号='" + sno + "'";
						String sqlNo = "delete from bookinfomation where 编号='" + sno + "'";
						stmt1.executeUpdate(sql1);
						stmt1.executeUpdate(sqlNo);
						conn.close();
						JOptionPane.showMessageDialog(null, "删除成功");
					} catch (Exception e) {
						e.printStackTrace();
					}

			}
		});
		delete_clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent d) {
				snotext.setText("");//将删除界面的文本框设置为空
			}
		});
		tab.add("删除数据", mainpanel);//添加选项卡
	}

	// --显示数据
	public void viewData() {
		try {
			JPanel content = new JPanel();// content面板
			JButton clear = new JButton("清除");
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookcate";
			rs = stmt.executeQuery(sql);
			rs.last();//移动到表的最后一行
			row = rs.getRow();// 返回当前行数
			data = new Object[row][2];//形成对象
			booktable = new JTable(data, colname);//绘制成表
			/*美化表*/
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			booktable.setDefaultRenderer(Object.class, r);
			content.setPreferredSize(new Dimension(50, 400));
			content.add(clear);
			content.add(updatebutton);
			updatebutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {
					BookCateManage add1 = new BookCateManage();
					add1.setLocationRelativeTo(null);
					add1.setVisible(true);
					add1.setSize(476, 476);// 设置刷新后窗体大小
					setVisible(false);
				}
			});
			content.add(new JScrollPane(booktable));
			viewlistscroll = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// 获取action后清除所有数据
					int i;
					for (i = 0; i < booktable.getRowCount(); i++) { // getrowcount,获取表的行数
						data[i][0] = "";
						data[i][1] = "";
					}

				}

			});
			int i = 0;// 将结果集中的数据存到data中显示
			while (rs.next()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getString(2);
				i++;
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tab.add("浏览数据", viewlistscroll);
	}

	/* addButton的事件响应 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			Statement stmt = null;
			try {
				String no = bookinfo1.Getnumber();
				String kind1 = bookinfo1.Getkind();
				if (queryExist(no)) {
					JOptionPane.showMessageDialog(null, "编号已存在,请另外选取编号");
				} else {
					Connection conn = new GetConnection().getConn();
					stmt = conn.createStatement();
					String sql = "insert into bookcate(编号,类型)" + "values(" + "'" + no + "'," + "'" + kind1 + "')";
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "添加成功");
				}
			} catch (Exception f) {
				f.printStackTrace();
				JOptionPane.showMessageDialog(null, "添加失败,请重新输入");
			}
		}

	}

}