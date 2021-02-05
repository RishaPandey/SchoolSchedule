package com.nda.academy.timetable;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.nda.academy.timetable.constants.Constants;
import com.nda.academy.timetable.service.ClassWiseTable;
import com.nda.academy.timetable.service.NewTimeTableTeachers;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;

public class School2021 extends JFrame {
	DefaultTableModel model;
	Map<String, String> newTeacherTable;
	JMenuBar menuBar;
	Constants constants = new Constants();
	public JTextField txtTableName;

	public static void main(String[] args) {
		System.setProperty("newTeacherTablePath", args[0]);
		System.setProperty("newClassTablePath", args[1]);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				School2021 frame = new School2021(); // Create SchhoDame
				frame.setVisible(true); // Set True for show SchhoDame
			}
		});
	}

	public School2021() {
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().setForeground(Color.BLACK);
		setBackground(new Color(100, 149, 237));
		getContentPane().setBackground(new Color(106, 90, 205));
		ClassWiseTable readcsv = new ClassWiseTable();
		NewTimeTableTeachers newTimeTableTeachers = new NewTimeTableTeachers();
		try {
			readcsv.createSectionTable();
			newTimeTableTeachers.generateNewTimeTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final int maxTeacherNeeded = newTimeTableTeachers.getMaxTeacherNeeded();
		newTeacherTable = constants.newteacherTableFileLocFileLoc;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 700, 550); // Set size and position
		setTitle("School Dame"); // Set Title
		getContentPane().setLayout(null);

		// Label Result
		final JLabel lblResult = new JLabel("NOTRE DAME ACADEMY", JLabel.CENTER); // Create JLabel
		lblResult.setBackground(new Color(0, 0, 0));
		lblResult.setForeground(new Color(230, 230, 250));
		lblResult.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 23));
		lblResult.setBounds(132, 11, 366, 30); // Set size and position JLabel
		getContentPane().add(lblResult);
		// Table
		JTable table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setRowHeight(26);
		table.setBorder(new CompoundBorder(new CompoundBorder(new CompoundBorder(), null), null));
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", new Color(230, 230, 250));

		// Create JTable
		getContentPane().add(table); // add JTable in SchhoDame

		// Filed
		txtTableName = new JTextField();
		txtTableName.setBackground(new Color(230, 230, 250));
		txtTableName.setHorizontalAlignment(SwingConstants.CENTER);
		txtTableName.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTableName.setEnabled(false);
		txtTableName.setEditable(false);
		txtTableName.setBounds(226, 106, 192, 30);
		txtTableName.setVisible(false);
		getContentPane().add(txtTableName);
		txtTableName.setColumns(10);

		// Table Model
		model = (DefaultTableModel) table.getModel();

		/*
		 * this.model.addColumn("--"); // Create Column and add to Jtable
		 * this.model.addColumn("Monday"); this.model.addColumn("Tuesday");
		 * this.model.addColumn("Wednesday"); this.model.addColumn("Thursday");
		 * this.model.addColumn("Friday"); this.model.addColumn("Saturday");
		 */
		setJMenuBar(createMenu(model));

		// ScrollPane
		JScrollPane scroll = new JScrollPane(table); // Create Scroll in Jtable
		scroll.setBounds(85, 147, 500, 270);
		getContentPane().add(scroll); // add Scroll in SchhoDame

		final JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		textPane.setBackground(new Color(230, 230, 250));
		textPane.setBounds(623, 455, 22, 20);
		textPane.setVisible(false);
		getContentPane().add(textPane);

		JButton btnNewButton = new JButton("Get more Teachers?");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(230, 230, 250));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText(Integer.toString(maxTeacherNeeded));
				textPane.setVisible(true);
			}
		});
		btnNewButton.setBounds(456, 452, 156, 23);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("TIME-TABLE 2020-21");
		lblNewLabel.setForeground(new Color(230, 230, 250));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(238, 48, 151, 14);
		getContentPane().add(lblNewLabel);
	}

	private JMenuBar createMenu(DefaultTableModel model) {
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(230, 230, 250));

		JMenu menu1 = new JMenu("Teacher Time-Table");
		menu1.setBackground(new Color(176, 196, 222));
		menu1.add(new DialogCreatingMenuItem("English", model, txtTableName));
		menu1.add(new DialogCreatingMenuItem("Hindi", model, txtTableName));
		menu1.add(new DialogCreatingMenuItem("Maths", model, txtTableName));
		menu1.add(new DialogCreatingMenuItem("Science", model, txtTableName));
		menu1.add(new DialogCreatingMenuItem("Kannada", model, txtTableName));
		JMenu menu2 = new JMenu("Class Time-Table");
		menu2.add(new DialogCreatingMenuItem("6th", model, txtTableName));
		menu2.add(new DialogCreatingMenuItem("7th", model, txtTableName));
		menu2.add(new DialogCreatingMenuItem("8th", model, txtTableName));
		menu2.add(new DialogCreatingMenuItem("9th", model, txtTableName));
		menu2.add(new DialogCreatingMenuItem("10th", model, txtTableName));
		JMenu menu3 = new JMenu("Existing Time-Table");
		menu3.setBackground(new Color(176, 196, 222));
		menu3.add(new DialogCreatingMenuItem("Original English", model, txtTableName));
		menu3.add(new DialogCreatingMenuItem("Original Hindi", model, txtTableName));
		menu3.add(new DialogCreatingMenuItem("Original Maths", model, txtTableName));
		menu3.add(new DialogCreatingMenuItem("Original Science", model, txtTableName));
		menu3.add(new DialogCreatingMenuItem("Original Kannada", model, txtTableName));
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		return menuBar;
	}
}

class DialogCreatingMenuItem extends JMenuItem implements ActionListener {

	String className;
	Constants constants = new Constants();
	Map<String, String> newTeacherTable = constants.newteacherTableFileLocFileLoc;
	Map<String, String> teacherTableFileLoc = constants.oldteacherTableFileLoc;
	Map<String, String> classTableFileLoc = constants.classTableFileLoc;
	DefaultTableModel model;
	JTextField tf;

	public DialogCreatingMenuItem(String text, DefaultTableModel model, JTextField j) throws HeadlessException {
		super(text);
		this.tf = j;
		this.model = model;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		if (this.model.getColumnCount() != 7) {
			this.model.addColumn("--"); // Create Column and add to Jtable
			this.model.addColumn("Monday");
			this.model.addColumn("Tuesday");
			this.model.addColumn("Wednesday");
			this.model.addColumn("Thursday");
			this.model.addColumn("Friday");
			this.model.addColumn("Saturday");
		}
		boolean b = false;
		tf.setVisible(true);
		tf.setText(event.getActionCommand());
		tf.enable();
		String path = newTeacherTable.get(event.getActionCommand());
		if (path == null) {
			path = teacherTableFileLoc.get(event.getActionCommand());
			if (path != null) {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				URL resource = classloader.getResource(path);
				try {
					path = Paths.get(resource.toURI()).toFile().toString();
					b = true;
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			} else {
				path = classTableFileLoc.get(event.getActionCommand());
			}
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			int row = 0;
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (count > 1 || (b && count > 0)) {
					if (b) {
						while (line.contains(",,")) {
							line = line.replace(",,", ", ,");
						}
						if (line.charAt(line.length() - 1) == ',') {
							line = line + " ";
						}
						if (line.charAt(0) == ',') {
							line = " " + line;
						}
					}
					String[] arr = line.split(",");
					if (arr.length == 7) {// split ',' (CSV file)
						if (model.getRowCount() <= 9) {
							model.addRow(new Object[0]);
						}
						model.setValueAt(arr[0], row, 0);
						model.setValueAt(arr[1], row, 1);
						model.setValueAt(arr[2], row, 2);
						model.setValueAt(arr[3], row, 3);
						model.setValueAt(arr[4], row, 4);
						model.setValueAt(arr[5], row, 5);
						model.setValueAt(arr[6], row, 6);
						row++;
					}
				}
				count++;
			}
			if (b) {
				model.addRow(new Object[0]);
			}
			br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}