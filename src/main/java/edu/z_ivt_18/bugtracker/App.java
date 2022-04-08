package edu.z_ivt_18.bugtracker;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App {

	private JFrame frame;
	private JTextField txtId;
	private JTextField txtCode;
	private JTextField txtDescr;
	private JTextField txtAuthorId;
	private JTextField txtErrorDate;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ShowData();
			}
		});
		frame.setBounds(100, 100, 581, 567);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(140, 51, 87, 13);
		frame.getContentPane().add(lblId);
		
		JLabel lblCode = new JLabel("\u041A\u043E\u0434");
		lblCode.setBounds(140, 74, 87, 13);
		frame.getContentPane().add(lblCode);
		
		JLabel lblDescr = new JLabel("\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435");
		lblDescr.setBounds(140, 97, 87, 13);
		frame.getContentPane().add(lblDescr);
		
		JLabel lblAuthorId = new JLabel("ID \u0430\u0432\u0442\u043E\u0440\u0430");
		lblAuthorId.setBounds(140, 120, 87, 13);
		frame.getContentPane().add(lblAuthorId);
		
		JLabel lblErrorDate = new JLabel("\u0414\u0430\u0442\u0430 \u043E\u0448\u0438\u0431\u043A\u0438");
		lblErrorDate.setBounds(140, 143, 87, 13);
		frame.getContentPane().add(lblErrorDate);
		
		txtId = new JTextField();
		txtId.setBounds(237, 48, 194, 19);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtCode = new JTextField();
		txtCode.setBounds(237, 71, 194, 19);
		frame.getContentPane().add(txtCode);
		txtCode.setColumns(10);
		
		txtDescr = new JTextField();
		txtDescr.setBounds(237, 94, 194, 19);
		frame.getContentPane().add(txtDescr);
		txtDescr.setColumns(10);
		
		txtAuthorId = new JTextField();
		txtAuthorId.setBounds(237, 117, 194, 19);
		frame.getContentPane().add(txtAuthorId);
		txtAuthorId.setColumns(10);
		
		txtErrorDate = new JTextField();
		txtErrorDate.setBounds(237, 140, 194, 19);
		frame.getContentPane().add(txtErrorDate);
		txtErrorDate.setColumns(10);
		
		JButton btnSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveToDatabase();
			}
		});
		btnSave.setBounds(345, 167, 110, 21);
		frame.getContentPane().add(btnSave);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 214, 498, 290);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				SetTextField(id);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnUpdate = new JButton("\u041E\u0431\u043D\u043E\u0432\u0438\u0442\u044C");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					Update(txtId.getText());
				}
			}
		});
		btnUpdate.setBounds(117, 167, 102, 21);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0)
					Delete(txtId.getText());
			}
		});
		btnDelete.setBounds(229, 167, 102, 21);
		frame.getContentPane().add(btnDelete);
	}
	
	static Connection con() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/errors";
			Class.forName(driver);
			return DriverManager.getConnection(url,"root", "1234");
		} catch (Exception e) {
			System.out.println("Connection failed! " + e);
		}
		
		return null;
	}
	
	private void SaveToDatabase() {
		Connection con = con();
		try {
			String query = "INSERT INTO error values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtCode.getText());
			ps.setString(3, txtDescr.getText());
			ps.setString(4, txtAuthorId.getText());
			ps.setString(5, txtErrorDate.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Saved!");
			ShowData();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
	}
	
	private void ShowData() {
		Connection con = con();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Code");
		model.addColumn("Description");
		model.addColumn("Author ID");
		model.addColumn("Error date");
		try {
			String query = "select * from error";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("code"),
						rs.getString("descr"),
						rs.getString("authorid"),
						rs.getString("errordate")
				});
			}
			
			rs.close();
			st.close();
			con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(45);
			table.getColumnModel().getColumn(1).setPreferredWidth(45);
			table.getColumnModel().getColumn(2).setPreferredWidth(210);
			table.getColumnModel().getColumn(3).setPreferredWidth(75);
			table.getColumnModel().getColumn(4).setPreferredWidth(75);
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
	}
	
	private void SetTextField(String id) {
		Connection con = con();
		try {
			String query = "select * from error where id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtId.setText(rs.getString("id"));
				txtCode.setText(rs.getString("code"));
				txtDescr.setText(rs.getString("descr"));
				txtAuthorId.setText(rs.getString("authorid"));
				txtErrorDate.setText(rs.getString("errordate"));
			}
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}		
	}
	
	private void Update(String id) {
		Connection con = con();
		try {
			String query = "update error set code = ?, descr = ?, "
					+ "authorid = ?, errordate = ? where id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, txtCode.getText());
			ps.setString(2, txtDescr.getText());
			ps.setString(3, txtAuthorId.getText());
			ps.setString(4, txtErrorDate.getText());
			ps.setString(5, id);
			ps.execute();
			
			ps.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Успешно обновлено!");
			ShowData();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
	}
	
	private void Delete(String id) {
		Connection con = con();
		try {
			String query = "delete from error where id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ps.execute();
			
			ps.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Deleted");
			ShowData();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
	}
}