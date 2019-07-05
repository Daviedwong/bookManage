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
	JScrollPane viewlistscroll, viewlistscrollAll, viewlistscrollNest, viewListscrollAssume;// ��ʾ�������ݵĹ������??
	JScrollPane viewscroll, viewscrollAll, viewscrollNest, viewscrollAssume;// ��ʾ�������ݵĹ������??
	JPanel updatepanel = new JPanel();// �������
	GetBooksInfo booksinfo1 = new GetBooksInfo();// ʵ������ȡ�鼮��Ϣ����
	GetBooksInfo booksinfo2 = new GetBooksInfo();
	JPanel querypanel = new JPanel();
	JButton dataButton = new JButton("ɾ��");// ɾ����ť
	JTextField queryTextFile = new JTextField(10);
	Object data[][], data1[][], data2[][], data3[][], data4[][], data5[][];// ���������������??
	Object bookColname[] = { "�鼮��", "�鼮��", "�۸�", "�ݲز���", "���" };// ������������ı�ͷ
	Object bookColnameAll[] = { "�鼮��", "�鼮��", "�۸�", "�ݲز���", "���", "����" };// ��
	Object bookColnameNest[] = { "�鼮��", "�鼮��", "���" };
	JTable booktable, qureytable, qureylist, list, listNest, listAssume;// ������??
	JButton addButton = new JButton("���");// ������Ӱ�ť
	JButton modifybutton = new JButton("�޸�");// �޸İ�ť
	JButton updateBookButton = new JButton("����");// ���°�ť
	JButton querybutton = new JButton("��ѯ");// ��ѯ��ť
	String sno;// �����ַ������ڴ����ı��������������
	JTextField snotext = new JTextField(10);// ��������ɾ����Ϣ���ı���

	public BooksManage() {
		super("ͼ���ͼ����Ϣ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ����ɹر�
		setResizable(true);// �ɸ��Ĵ�С
		addBook();//
		viewBook();
		viewBookByLinkSearch();// ���Ӳ�ѯ
		viewBookByNestSearch();// Ƕ�ײ�ѯ
		viewBookByAssumSearch();// ���ϲ�ѯ
		deleteBook();
		add(bookTab);// ���ѡ����
		setVisible(true);
	}

//---------------�������------------------------//
	public void addBook() {
		JButton adddata_clear = new JButton("���");// ���尴ť
		bookMainpanel.add(booksinfo1);// ��bookinfo1�е�����ӵ�bookMainpanel�����
		addButton.addActionListener(this);// ���Ҽ���
		addButton.setBackground(Color.decode("#F5F5DC"));
		adddata_clear.setBackground(Color.decode("#F5F5DC"));
		booksinfo1.add(addButton);// �����Ӱ�ť���ı���ǰ
		booksinfo1.add(adddata_clear);// ���ɾ����ť���ı���ǰ
		adddata_clear.addActionListener(new ActionListener() {// ��������ť������ı����е�����
			public void actionPerformed(ActionEvent d) {
				booksinfo1.no.setText("");
				booksinfo1.name.setText("");
				booksinfo1.price.setText("");
				booksinfo1.amount.setText("");
				booksinfo1.cate.setText("");
			}
		});
		bookTab.add("�������", booksinfo1);// ����������ӵ�������ݵ������
	}

// --------------------ɾ������----------------------------
	public void deleteBook() {
		JLabel snolabel = new JLabel("�鼮��");// �鼮�ű�ǩ
		JButton delete_query = new JButton("��ѯ");// ��ѯ��ť
		JButton delete_clear = new JButton("���");// �����ť
		delete_query.setBackground(Color.decode("#F5F5DC"));
		delete_clear.setBackground(Color.decode("#F5F5DC"));
		dataButton.setBackground(Color.decode("#F5F5DC"));
		bookMainpanel.setBackground(Color.decode("#F4F4F4"));
		bookMainpanel.add(snolabel);// ��ӱ�ű�ǩ
		bookMainpanel.add(snotext);//
		bookMainpanel.add(dataButton);// ����ı���
		bookMainpanel.add(delete_clear);// �����ť
		bookMainpanel.add(delete_query);// ɾ��ǰ�Ĳ�ѯ��ť
		data2 = new Object[1][5];// ������ʾ����
		qureylist = new JTable(data2, bookColname);// ʵ����
		JScrollPane jsp = new JScrollPane(qureylist);// ������������Ϊ�ɹ�����
		bookMainpanel.add(jsp);
		qureylist.setVisible(true);// ����������Ϊ�ɼ�
		qureylist.setBackground(Color.decode("#F4F4F4"));
		qureylist.setFillsViewportHeight(true);// ��������Ϊ�߶ȿ���������ӿ�
		delete_query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent d) {
				sno = snotext.getText();// ��ȡ�ı�������
				if (snotext.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "��Ų���Ϊ��");
				else
					try {
						ResultSet rs1;
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sqlNo = "select * from bookinfomation where �鼮��=" + sno;//
						rs1 = stmt1.executeQuery(sqlNo);
						int i = 0;
						if (rs1.next()) {
							data2[i][0] = rs1.getString(1);// ��ȡ����Ҫ��ĵ�һ��
							data2[i][1] = rs1.getString(2);// ��ȡ����Ҫ��ĵڶ���
							data2[i][2] = rs1.getString(3);// ��ȡ...3
							data2[i][3] = rs1.getString(4);// ......4
							data2[i][4] = rs1.getString(5);// .......5
							qureylist.setVisible(true);
						} else
							JOptionPane.showMessageDialog(null, "�޼�¼");
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
					JOptionPane.showMessageDialog(null, "�鼮�Ų���Ϊ��");
				else
					try {
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sqlNo = "delete from bookinfomation where �鼮��='" + sno + "'";
						stmt1.executeUpdate(sqlNo);
						conn.close();
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					} catch (Exception e) {
						e.printStackTrace();
					}

			}
		});
		delete_clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent d) {
				snotext.setText("");// ��ɾ��������ı�������Ϊ��
			}
		});
		bookTab.add("ɾ������", bookMainpanel);// ���ѡ�
	}

// -----------������ʾ����(�ñ��)-------------
	public void viewBook() {
		try {
			JPanel content = new JPanel();// content���
			JButton clear = new JButton("�鿴���");
			clear.setBackground(Color.decode("#F5F5DC"));
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookinfomation";
			rs = stmt.executeQuery(sql);
			rs.last();// �ƶ���������һ��
			row = rs.getRow();// ���ص�ǰ����
			data = new Object[row][5];// �γ�row��5�еı�����
			booktable = new JTable(data, bookColname);// ���Ƴɱ�
			/* ������ */
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
					newManage.setSize(476, 476);// ����ˢ�º����С
					setVisible(false);
				}
			});
			content.add(new JScrollPane(booktable));
			viewlistscroll = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// ��ȡaction�������������
					BookCateManage cate = new BookCateManage();
				}

			});
			int i = 0;// ��������е����ݴ浽data����ʾ
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
		bookTab.add("�������", viewlistscroll);
	}

//------------------���Ӳ�ѯ�鼮�����-------------------
	public void viewBookByLinkSearch() {
		try {
			JPanel content = new JPanel();// content���
			JButton clear = new JButton("���");
			clear.setBackground(Color.decode("#F5F5DC"));
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select �鼮��,�鼮��,�۸�,�ݲز���,bookcate.���,���� from bookinfomation,bookcate where bookinfomation.��� = bookcate.���";
			rs = stmt.executeQuery(sql);
			rs.last();// �ƶ���������һ��
			row = rs.getRow();// ���ص�ǰ����
			data3 = new Object[row][6];// �γ�row��6�еı�����
			list = new JTable(data3, bookColnameAll);// ���Ƴɱ�
			/* ������ */
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
					newManage.setSize(476, 476);// ����ˢ�º����С
					setVisible(false);
				}
			});
			content.add(new JScrollPane(list));
			viewlistscrollAll = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// ��ȡaction�������������
					int i;
					for (i = 0; i < list.getRowCount(); i++) { // getrowcount,��ȡ�������
						data3[i][0] = "";
						data3[i][1] = "";
						data3[i][2] = "";
						data3[i][3] = "";
						data3[i][4] = "";
						data3[i][5] = "";
					}

				}

			});
			int i = 0;// ��������е����ݴ浽data3����ʾ
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
		bookTab.add("���ͼ�鼰�������(����)", viewlistscrollAll);
	}

//-----------------Ƕ�ײ�ѯ
public void viewBookByNestSearch() {
		JPanel content = new JPanel();// content���
		JButton search = new JButton("��ѯ");
		JButton updateButton =new JButton("����");
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
			public void actionPerformed(ActionEvent d) {// ��ȡaction�������������
				sno=snotext.getText();
				if (snotext.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "���Ͳ���Ϊ��");
				else if(queryExist(sno)==false) {
					JOptionPane.showMessageDialog(null, "���Ͳ�����,������ѡȡ���");
				}
				else
					try {
						ResultSet rs1;
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sql1="select �鼮��,�鼮��,��� from bookinfomation where ��� IN (select ��� from bookcate where ���� ='"+sno+"')";//
						rs1 = stmt1.executeQuery(sql1);
						rs1.beforeFirst();
						int i = 0;// ��������е����ݴ浽data4����ʾ
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
	bookTab.add("��ѯͬһ��ͼ��(Ƕ��)", viewlistscrollNest);
}

//���ϲ�ѯ
	public void viewBookByAssumSearch() {
		try {
			JPanel content = new JPanel();// content���
			JButton clear = new JButton("���");
			clear.setBackground(Color.decode("#F5F5DC"));
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookinfomation where ��� ='1' union select * from bookinfomation where ���='2'";
			rs = stmt.executeQuery(sql);
			rs.last();// �ƶ���������һ��
			row = rs.getRow();// ���ص�ǰ����
			data5 = new Object[row][5];// �γ�row��5�еı�����
			listAssume = new JTable(data5, bookColname);// ���Ƴɱ�
			/* ������ */
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
					newManage.setSize(476, 476);// ����ˢ�º����С
					setVisible(false);
				}
			});
			content.add(new JScrollPane(listAssume));
			viewListscrollAssume = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// ��ȡaction�������������
					int i;
					for (i = 0; i < listAssume.getRowCount(); i++) { // getrowcount,��ȡ�������
						data5[i][0] = "";
						data5[i][1] = "";
						data5[i][2] = "";
						data5[i][3] = "";
						data5[i][4] = "";
					}

				}

			});
			int i = 0;// ��������е����ݴ浽data����ʾ
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
		bookTab.add("������ݼ��ϲ�ѯ", viewListscrollAssume);
	}

//-----------------------��ѯ�Ƿ����------------------------
	public boolean queryExist(String no) {
		boolean b = false;
		try {
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookinfomation where �鼮��=" + no;
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

	/*----------addButton���¼���Ӧ------------------------------*/
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
					JOptionPane.showMessageDialog(null, "����Ѵ���,������ѡȡ���");
				} else {
					Connection conn = new GetConnection().getConn();
					stmt = conn.createStatement();
//---------------------------------------------------------------------------------//
					String sql = "insert into bookinfomation(�鼮��,�鼮��,�۸�,�ݲز���,���)" + "values(" + "'" + no + "'," + "'"
							+ name + "'," + "'" + price + "'," + "'" + amount + "'," + "'" + cate + "')";
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "��ӳɹ�");
				}
			} catch (Exception f) {
				f.printStackTrace();
				JOptionPane.showMessageDialog(null, "���ʧ��,����������");
			}
		}

	}

}
