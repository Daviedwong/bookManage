package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import bean.GetBooksInfo;
import util.GetConnection;

public class BooksManage extends JFrame implements ActionListener {
	int row;
	JTabbedPane bookTab = new JTabbedPane();
	JPanel bookMainpanel = new JPanel();
	JScrollPane viewlistscroll, viewlistscrollAll, viewlistscrollNest, viewListscrollAssume;// 显示所有数据的滚动面板??
	JScrollPane viewscroll, viewscrollAll, viewscrollNest, viewscrollAssume;// 显示单条数据的滚动面板??
	JPanel updatepanel = new JPanel();// 更新面板
	GetBooksInfo booksinfo1 = new GetBooksInfo();// 实例化获取书籍信息的类
	GetBooksInfo booksinfo2 = new GetBooksInfo();
	JPanel querypanel = new JPanel();
	JButton dataButton = new JButton("删除");// 删除按钮
	JTextField queryTextFile = new JTextField(10);
	Object data[][], data1[][], data2[][], data3[][], data4[][], data5[][];// 定义三个数组对象??
	Object bookColname[] = { "书籍号", "书籍名", "价格", "馆藏册数", "编号" };// 包含五个变量的表头
	Object bookColnameAll[] = { "书籍号", "书籍名", "价格", "馆藏册数", "编号", "类型" };// ？
	Object bookColnameNest[] = { "书籍号", "书籍名", "编号" };
	JTable booktable, qureytable, qureylist, list, listNest, listAssume;// 定义表格??
	JButton addButton = new JButton("添加");// 定义添加按钮
	JButton modifybutton = new JButton("修改");// 修改按钮
	JButton updateBookButton = new JButton("更新");// 更新按钮
	JButton querybutton = new JButton("查询");// 查询按钮
	String sno;// 定义字符串用于储存文本框中输入的内容
	JTextField snotext = new JTextField(10);// 用于输入删除信息的文本框

	public BooksManage() {
		super("图书管图书信息界面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 定义可关闭
		setResizable(true);// 可更改大小
		addBook();//
		viewBook();
		viewBookByLinkSearch();// 连接查询
		viewBookByNestSearch();// 嵌套查询
		viewBookByAssumSearch();// 集合查询
		deleteBook();
		add(bookTab);// 添加选项卡组件
		setVisible(true);
	}

//---------------添加数据------------------------//
	public void addBook() {
		JButton adddata_clear = new JButton("清除");// 定义按钮
		bookMainpanel.add(booksinfo1);// 将bookinfo1中的组件加到bookMainpanel面板上
		addButton.addActionListener(this);// 自我监听
		addButton.setBackground(Color.decode("#F5F5DC"));
		adddata_clear.setBackground(Color.decode("#F5F5DC"));
		booksinfo1.add(addButton);// 添加添加按钮到文本框前
		booksinfo1.add(adddata_clear);// 添加删除按钮到文本框前
		adddata_clear.addActionListener(new ActionListener() {// 点击清除按钮，清除文本框中的内容
			public void actionPerformed(ActionEvent d) {
				booksinfo1.no.setText("");
				booksinfo1.name.setText("");
				booksinfo1.price.setText("");
				booksinfo1.amount.setText("");
				booksinfo1.cate.setText("");
			}
		});
		bookTab.add("添加数据", booksinfo1);// 将上述组件加到添加数据的组件下
	}

// --------------------删除数据----------------------------
	public void deleteBook() {
		JLabel snolabel = new JLabel("书籍号");// 书籍号标签
		JButton delete_query = new JButton("查询");// 查询按钮
		JButton delete_clear = new JButton("清除");// 清除按钮
		delete_query.setBackground(Color.decode("#F5F5DC"));
		delete_clear.setBackground(Color.decode("#F5F5DC"));
		dataButton.setBackground(Color.decode("#F5F5DC"));
		bookMainpanel.setBackground(Color.decode("#F4F4F4"));
		bookMainpanel.add(snolabel);// 添加编号标签
		bookMainpanel.add(snotext);//
		bookMainpanel.add(dataButton);// 编号文本框
		bookMainpanel.add(delete_clear);// 清除按钮
		bookMainpanel.add(delete_query);// 删除前的查询按钮
		data2 = new Object[1][5];// 创建显示数据
		qureylist = new JTable(data2, bookColname);// 实例表
		JScrollPane jsp = new JScrollPane(qureylist);// 将表的面板设置为可滚动型
		bookMainpanel.add(jsp);
		qureylist.setVisible(true);// 将表线条设为可见
		qureylist.setBackground(Color.decode("#F4F4F4"));
		qureylist.setFillsViewportHeight(true);// 将表设置为高度可以填充封闭视口
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
						String sqlNo = "select * from bookinfomation where 书籍号=" + sno;//
						rs1 = stmt1.executeQuery(sqlNo);
						int i = 0;
						if (rs1.next()) {
							data2[i][0] = rs1.getString(1);// 获取符合要求的第一列
							data2[i][1] = rs1.getString(2);// 获取符合要求的第二列
							data2[i][2] = rs1.getString(3);// 获取...3
							data2[i][3] = rs1.getString(4);// ......4
							data2[i][4] = rs1.getString(5);// .......5
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
					JOptionPane.showMessageDialog(null, "书籍号不能为空");
				else
					try {
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sqlNo = "delete from bookinfomation where 书籍号='" + sno + "'";
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
				snotext.setText("");// 将删除界面的文本框设置为空
			}
		});
		bookTab.add("删除数据", bookMainpanel);// 添加选项卡
	}

// -----------单表显示数据(用表格)-------------
	public void viewBook() {
		try {
			JPanel content = new JPanel();// content面板
			JButton clear = new JButton("查看类别");
			clear.setBackground(Color.decode("#F5F5DC"));
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookinfomation";
			rs = stmt.executeQuery(sql);
			rs.last();// 移动到表的最后一行
			row = rs.getRow();// 返回当前行数
			data = new Object[row][5];// 形成row行5列的表格对象
			booktable = new JTable(data, bookColname);// 绘制成表
			/* 美化表 */
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			booktable.setDefaultRenderer(Object.class, r);
			content.setPreferredSize(new Dimension(50, 400));
			content.add(clear);
			updateBookButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {
					BooksManage newManage = new BooksManage();
					newManage.setLocationRelativeTo(null);
					newManage.setVisible(true);
					newManage.setSize(476, 476);// 设置刷新后窗体大小
					setVisible(false);
				}
			});
			content.add(new JScrollPane(booktable));
			viewlistscroll = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// 获取action后清除所有数据
					BookCateManage cate = new BookCateManage();
				}

			});
			int i = 0;// 将结果集中的数据存到data中显示
			while (rs.next()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getString(2);
				data[i][2] = rs.getString(3);
				data[i][3] = rs.getString(4);
				data[i][4] = rs.getString(5);
				i++;
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bookTab.add("浏览数据", viewlistscroll);
	}

//------------------连接查询书籍和类别-------------------
	public void viewBookByLinkSearch() {
		try {
			JPanel content = new JPanel();// content面板
			JButton clear = new JButton("清除");
			clear.setBackground(Color.decode("#F5F5DC"));
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select 书籍号,书籍名,价格,馆藏册数,bookcate.编号,类型 from bookinfomation,bookcate where bookinfomation.编号 = bookcate.编号";
			rs = stmt.executeQuery(sql);
			rs.last();// 移动到表的最后一行
			row = rs.getRow();// 返回当前行数
			data3 = new Object[row][6];// 形成row行6列的表格对象
			list = new JTable(data3, bookColnameAll);// 绘制成表
			/* 美化表 */
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			list.setDefaultRenderer(Object.class, r);
			content.setPreferredSize(new Dimension(50, 400));
			content.add(clear);
			content.add(updateBookButton);
			updateBookButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {
					BooksManage newManage = new BooksManage();
					newManage.setLocationRelativeTo(null);
					newManage.setVisible(true);
					newManage.setSize(476, 476);// 设置刷新后窗体大小
					setVisible(false);
				}
			});
			content.add(new JScrollPane(list));
			viewlistscrollAll = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// 获取action后清除所有数据
					int i;
					for (i = 0; i < list.getRowCount(); i++) { // getrowcount,获取表的行数
						data3[i][0] = "";
						data3[i][1] = "";
						data3[i][2] = "";
						data3[i][3] = "";
						data3[i][4] = "";
						data3[i][5] = "";
					}

				}

			});
			int i = 0;// 将结果集中的数据存到data3中显示
			while (rs.next()) {
				data3[i][0] = rs.getString(1);
				data3[i][1] = rs.getString(2);
				data3[i][2] = rs.getString(3);
				data3[i][3] = rs.getString(4);
				data3[i][4] = rs.getString(5);
				data3[i][5] = rs.getString(6);
				i++;
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bookTab.add("浏览图书及类别数据(连接)", viewlistscrollAll);
	}

//-----------------嵌套查询
public void viewBookByNestSearch() {
		JPanel content = new JPanel();// content面板
		JButton search = new JButton("查询");
		JButton updateButton =new JButton("更新");
		JTextField snotext =new JTextField(10);
		search.setBackground(Color.decode("#F5F5DC"));
		updateButton.setBackground(Color.decode("#F5F5DC"));
		data4=new Object[row][3];
		listNest=new JTable(data4,bookColnameNest);
		content.add(snotext);
		content.add(search);
		content.add(updateButton);
		content.add(new JScrollPane(listNest));
		viewlistscrollNest = new JScrollPane(content);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent d) {// 获取action后清除所有数据
				sno=snotext.getText();
				if (snotext.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "类型不能为空");
				else if(queryExist(sno)==false) {
					JOptionPane.showMessageDialog(null, "类型不存在,请另外选取编号");
				}
				else
					try {
						ResultSet rs1;
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sql1="select 书籍号,书籍名,编号 from bookinfomation where 编号 IN (select 编号 from bookcate where 类型 ='"+sno+"')";//
						rs1 = stmt1.executeQuery(sql1);
						rs1.beforeFirst();
						int i = 0;// 将结果集中的数据存到data4中显示
						while (rs1.next()) {
							data4[i][0] = rs1.getString(1);
							data4[i][1] = rs1.getString(2);
							data4[i][2] = rs1.getString(3);
							i++;
						}
						rs1.close();
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
	bookTab.add("查询同一类图书(嵌套)", viewlistscrollNest);
}

//集合查询
	public void viewBookByAssumSearch() {
		try {
			JPanel content = new JPanel();// content面板
			JButton clear = new JButton("清除");
			clear.setBackground(Color.decode("#F5F5DC"));
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookinfomation where 编号 ='1' union select * from bookinfomation where 编号='2'";
			rs = stmt.executeQuery(sql);
			rs.last();// 移动到表的最后一行
			row = rs.getRow();// 返回当前行数
			data5 = new Object[row][5];// 形成row行5列的表格对象
			listAssume = new JTable(data5, bookColname);// 绘制成表
			/* 美化表 */
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			booktable.setDefaultRenderer(Object.class, r);
			content.setPreferredSize(new Dimension(50, 400));
			updateBookButton.setBackground(Color.decode("#F5F5DC"));
			content.add(updateBookButton);
			content.add(clear);
			updateBookButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {
					BooksManage newManage = new BooksManage();
					newManage.setLocationRelativeTo(null);
					newManage.setVisible(true);
					newManage.setSize(476, 476);// 设置刷新后窗体大小
					setVisible(false);
				}
			});
			content.add(new JScrollPane(listAssume));
			viewListscrollAssume = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// 获取action后清除所有数据
					int i;
					for (i = 0; i < listAssume.getRowCount(); i++) { // getrowcount,获取表的行数
						data5[i][0] = "";
						data5[i][1] = "";
						data5[i][2] = "";
						data5[i][3] = "";
						data5[i][4] = "";
					}

				}

			});
			int i = 0;// 将结果集中的数据存到data中显示
			while (rs.next()) {
				data5[i][0] = rs.getString(1);
				data5[i][1] = rs.getString(2);
				data5[i][2] = rs.getString(3);
				data5[i][3] = rs.getString(4);
				data5[i][4] = rs.getString(5);
				i++;
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bookTab.add("浏览数据集合查询", viewListscrollAssume);
	}

//-----------------------查询是否存在------------------------
	public boolean queryExist(String no) {
		boolean b = false;
		try {
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookinfomation where 书籍号=" + no;
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

	/*----------addButton的事件响应------------------------------*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			Statement stmt = null;
			try {
				String no = booksinfo1.Getno();
				String cate = booksinfo1.Getcate();
				String name = booksinfo1.Getname();
				String price = booksinfo1.Getprice();
				String amount = booksinfo1.Getamount();
				if (queryExist(no)) {
					JOptionPane.showMessageDialog(null, "编号已存在,请另外选取编号");
				} else {
					Connection conn = new GetConnection().getConn();
					stmt = conn.createStatement();
//---------------------------------------------------------------------------------//
					String sql = "insert into bookinfomation(书籍号,书籍名,价格,馆藏册数,编号)" + "values(" + "'" + no + "'," + "'"
							+ name + "'," + "'" + price + "'," + "'" + amount + "'," + "'" + cate + "')";
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
