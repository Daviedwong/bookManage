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
	int row;// ��ȡ���ݱ�����
	JTabbedPane tab = new JTabbedPane();// ѡ����
	JPanel mainpanel = new JPanel();// ���
	JScrollPane viewlistscroll;// ��ʾ�������ݵĹ������
	JScrollPane viewscroll;// ��ʾ�������ݵĹ������
	JPanel updatepanel = new JPanel();// �������
	GetCateInfo bookinfo1 = new GetCateInfo();// ʵ������ȡnumber��kind����
	GetCateInfo bookinfo2 = new GetCateInfo();
	JButton dataButton = new JButton("ɾ��");// ɾ����ť
	Object data[][], data1[][], data2[][];// ���������������
	Object colname[] = { "���", "����" };// �������������ı�ͷ
	JTable booktable, qureytable, qureylist;// ������
	JButton addButton = new JButton("���");// ������Ӱ�ť
	JButton modifybutton = new JButton("�޸�");// �޸İ�ť
	JButton updatebutton = new JButton("����");// ���°�ť
	JButton querybutton = new JButton("��ѯ");// ��ѯ��ť
	String sno;// �����ַ������ڴ����ı��������������
	JTextField snotext = new JTextField(10);// ��������ɾ����Ϣ���ı���

	public BookCateManage() {
		super("ͼ���ͼ��������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ����ɹر�
		setResizable(true);// �ɸ��Ĵ�С
		viewData();//
		addData();
		deleteData();
		modifyData();
		add(tab);// ���ѡ����
		setSize(500,500);
		setVisible(true);
	}

	//�жϱ���Ƿ����
	public boolean queryExist(String id) {
		boolean b = false;
		try {
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookcate where ���=" + id;
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

	// ------------�������-------------------//
	public void addData() {
		JButton adddata_clear = new JButton("���");// ���尴ť
		mainpanel.add(bookinfo1);// ��bookinfo1�е�����ӵ�mainpanel�����
		addButton.addActionListener(this);// ���Ҽ���
		bookinfo1.add(addButton);// �����Ӱ�ť���ı���ǰ
		bookinfo1.add(adddata_clear);// ���ɾ����ť���ı���ǰ
		adddata_clear.addActionListener(new ActionListener() {// ��������ť������ı����е�����
			public void actionPerformed(ActionEvent d) {
				bookinfo1.getNumber().setText("");
				bookinfo1.getKind().setText("");
			}
		});
		tab.add("�������", bookinfo1);// ����������ӵ�������ݵ������
	}

	// ---------------�޸�����------------------//
	public void modifyData() {
		JButton update_clear = new JButton("���");
		mainpanel.add(bookinfo2);// ��BookInfo�е�����ӵ�mainpanel��
		bookinfo2.add(modifybutton);// ��Ӹ��İ�ť��bookinfo2���ı�����
		bookinfo2.add(querybutton);// ��Ӳ�ѯ��ť
		querybutton.addActionListener(new ActionListener() {// �¼�����
			public void actionPerformed(ActionEvent d) {// �¼���Ӧ
				String no =bookinfo2.getNumber().getText();
				if (bookinfo2.getNumber().getText() != null &&queryExist(no)&& d.getSource() == modifybutton)// �ı��򲻿��Ұ�ť�����
					bookinfo2.getNumber().setEditable(false);// ��������ı����Ϊ���ɱ༭
				bookinfo2.getKind().setEditable(true);// �������ı����Ϊ����,�����޸�����
				sno = bookinfo2.getNumber().getText();
				if(queryExist(no)==false)
					JOptionPane.showMessageDialog(null, "ͼ���Ų�����");
				if (bookinfo2.getNumber().getText().isEmpty())// �ı����Ƿ�Ϊ�յ��ж�
					JOptionPane.showMessageDialog(null, "ͼ����number����Ϊ��");
				else
					try {
						ResultSet rs;
						Connection conn = new GetConnection().getConn();// ʵ��һ���������ݿ��conn����
						Statement stmt = conn.createStatement();
						String sql = "select * from bookcate where ���=" + sno;// ѡ����sno��ͬ����Ϣ
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							bookinfo2.getKind().setText(rs.getString(2));// ��������ı����������ʾΪ���ݿ���ϱ�ŵ�ֵ
						}
						rs.close();
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		bookinfo2.add(modifybutton);// ��Ӹ��İ�ť
		modifybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent d) {
				if (bookinfo2.getNumber().getText().isEmpty())
					JOptionPane.showMessageDialog(null, "ͼ����number����Ϊ��");
				else
					try {
						bookinfo2.getKind().setEditable(false);
						String no = bookinfo2.getNumber().getText();// ��number�ı�������ݴ浽no��
						String kind = bookinfo2.getKind().getText();
						Connection conn = new GetConnection().getConn();
						Statement stmt = conn.createStatement();
						String sql = "update bookcate set ����='" + kind + "'where ���='" + no + "'";
						String sql1 = "update bookcate set ���='" + no + "'where ����='" + kind + "'";
						stmt.executeUpdate(sql);
						stmt.executeUpdate(sql1);
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						BookCateManage add1 = new BookCateManage();
						add1.setLocationRelativeTo(null);// ����
						add1.setVisible(true);// �ɼ�
						add1.setSize(500, 500);//���ݸ��ĺ����������С
						setVisible(false);// ���ĺ�ô��岻�ɼ�
						bookinfo2.getNumber().setEditable(true);
					}
			}

		});
		/* ��������ť����Ϊ��ʼ״̬...�ɱ༭...���ɱ༭ */
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
		tab.add("�޸�����", bookinfo2);// ���ѡ�
	}
	// --------------------ɾ������----------------------------//
	public void deleteData() {
		JLabel snolabel = new JLabel("���");// ��ű�ǩ
		JButton delete_query = new JButton("��ѯ");// ��ѯ��ť
		JButton delete_clear = new JButton("���");// �����ť
		mainpanel.add(snolabel);// ��ӱ�ű�ǩ
		mainpanel.add(snotext);//
		mainpanel.add(dataButton);// ����ı���
		mainpanel.add(delete_clear);// �����ť
		mainpanel.add(delete_query);// ɾ��ǰ�Ĳ�ѯ��ť
		data2 = new Object[1][2];// ������ʾ����
		qureylist = new JTable(data2, colname);// ʵ����
		JScrollPane jsp = new JScrollPane(qureylist);// ������������Ϊ�ɹ�����
		mainpanel.add(jsp);
		qureylist.setVisible(true);// ����������Ϊ�ɼ�
		qureylist.setFillsViewportHeight(true);// ��������Ϊ�߶ȿ���������ӿ�
		////
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
						String sql1 = "select * from bookcate where ���=" + sno;//
						rs1 = stmt1.executeQuery(sql1);
						int i = 0;
						if (rs1.next()) {
							data2[i][0] = rs1.getString(1);// ��ȡ����Ҫ��ĵ�һ��
							data2[i][1] = rs1.getString(2);//��ȡ����Ҫ��ĵڶ���
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
					JOptionPane.showMessageDialog(null, "��Ų���Ϊ��");
				else
					try {
						Connection conn = new GetConnection().getConn();
						Statement stmt1 = conn.createStatement();
						String sql1 = "delete from bookcate where ���='" + sno + "'";
						String sqlNo = "delete from bookinfomation where ���='" + sno + "'";
						stmt1.executeUpdate(sql1);
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
				snotext.setText("");//��ɾ��������ı�������Ϊ��
			}
		});
		tab.add("ɾ������", mainpanel);//���ѡ�
	}

	// --��ʾ����
	public void viewData() {
		try {
			JPanel content = new JPanel();// content���
			JButton clear = new JButton("���");
			ResultSet rs;
			Connection conn = new GetConnection().getConn();
			Statement stmt = conn.createStatement();
			String sql = "select * from bookcate";
			rs = stmt.executeQuery(sql);
			rs.last();//�ƶ���������һ��
			row = rs.getRow();// ���ص�ǰ����
			data = new Object[row][2];//�γɶ���
			booktable = new JTable(data, colname);//���Ƴɱ�
			/*������*/
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
					add1.setSize(476, 476);// ����ˢ�º����С
					setVisible(false);
				}
			});
			content.add(new JScrollPane(booktable));
			viewlistscroll = new JScrollPane(content);
			rs.beforeFirst();
			clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent d) {// ��ȡaction�������������
					int i;
					for (i = 0; i < booktable.getRowCount(); i++) { // getrowcount,��ȡ�������
						data[i][0] = "";
						data[i][1] = "";
					}

				}

			});
			int i = 0;// ��������е����ݴ浽data����ʾ
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
		tab.add("�������", viewlistscroll);
	}

	/* addButton���¼���Ӧ */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			Statement stmt = null;
			try {
				String no = bookinfo1.Getnumber();
				String kind1 = bookinfo1.Getkind();
				if (queryExist(no)) {
					JOptionPane.showMessageDialog(null, "����Ѵ���,������ѡȡ���");
				} else {
					Connection conn = new GetConnection().getConn();
					stmt = conn.createStatement();
					String sql = "insert into bookcate(���,����)" + "values(" + "'" + no + "'," + "'" + kind1 + "')";
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