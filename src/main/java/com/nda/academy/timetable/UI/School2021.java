package com.nda.academy.timetable.UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.nda.academy.timetable.constants.Constants;
import com.nda.academy.timetable.service.ClassWiseTable;
import com.nda.academy.timetable.service.NewTimeTableTeachers;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class School2021 extends JFrame {
    DefaultTableModel model;
	Map<String, String> newTeacherTable;
	JMenuBar menuBar;
	Constants constants = new Constants();
	public JTextField txtTableName;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				School2021 frame = new School2021();						//  Create SchhoDame
				frame.setVisible(true);						//  Set True for show SchhoDame 
			}
		});
	}
	
	public School2021() {
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
		setBounds(0,0,700,550);										// Set size and position  SchhoDame
		setTitle("School Dame");										// Set Title
		getContentPane().setLayout(null);
		
        // Label Result
		final JLabel lblResult = new JLabel("NOTRE DAME ACADEMY", JLabel.CENTER);		// Create JLabel
		lblResult.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblResult.setBounds(448, 38, 200, 30);												// Set size and position JLabel
		getContentPane().add(lblResult);
		// Table
		JTable table = new JTable();
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(135, 206, 235));
		
		// Create JTable
		getContentPane().add(table);				// add JTable in SchhoDame
		
		
		//Filed
		txtTableName = new JTextField();
		txtTableName.setBackground(new Color(255, 255, 255));
		txtTableName.setHorizontalAlignment(SwingConstants.CENTER);
		txtTableName.setText("TABLE NAME");
		txtTableName.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTableName.setEnabled(false);
		txtTableName.setEditable(false);
		txtTableName.setBounds(226, 106, 192, 30);
		getContentPane().add(txtTableName);
		txtTableName.setColumns(10);
		
		// Table Model
	    model = (DefaultTableModel)table.getModel();
	    
		
	    this.model.addColumn("--");              	 // Create Column and add to Jtable
		this.model.addColumn("Monday");					
		this.model.addColumn("Tuesday");	
		this.model.addColumn("Wednesday");					
		this.model.addColumn("Thursday");	
		this.model.addColumn("Friday");					
		this.model.addColumn("Saturday");
	    setJMenuBar(createMenu(model));

		//ScrollPane
		JScrollPane scroll = new JScrollPane(table);		// Create Scroll in Jtable
		scroll.setBounds(85, 147, 500, 185);
		getContentPane().add(scroll);						// add Scroll in SchhoDame
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(432, 408, 95, 20);
		getContentPane().add(textPane);
		
		JButton btnNewButton = new JButton("Click to find Extra Teachers Needed");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText(Integer.toString(maxTeacherNeeded));
			}
		});
		btnNewButton.setBounds(113, 408, 237, 23);
		getContentPane().add(btnNewButton);
		}
	
		
	 private JMenuBar createMenu(DefaultTableModel model) {
         menuBar = new JMenuBar();
        
        JMenu menu1 = new JMenu("New Subject");
        menu1.add(new DialogCreatingMenuItem("English",model, txtTableName));
        menu1.add(new DialogCreatingMenuItem("Hindi",model, txtTableName));
        menu1.add(new DialogCreatingMenuItem("Maths",model, txtTableName));
        menu1.add(new DialogCreatingMenuItem("Science",model, txtTableName));
        menu1.add(new DialogCreatingMenuItem("Kannada",model, txtTableName));
        JMenu menu2 = new JMenu("Class Table");
        menu2.add(new DialogCreatingMenuItem("6th", model, txtTableName));
        menu2.add(new DialogCreatingMenuItem("7th", model, txtTableName));
        menu2.add(new DialogCreatingMenuItem("8th", model, txtTableName));
        menu2.add(new DialogCreatingMenuItem("9th", model, txtTableName));
        menu2.add(new DialogCreatingMenuItem("10th", model, txtTableName));
        JMenu menu3 = new JMenu("Existing Subject");
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
        JTextField  tf;

        public DialogCreatingMenuItem(String text, DefaultTableModel model, JTextField j) throws HeadlessException {
            super(text);
            //j.setText(text);
            this.tf=j;
            this.model=model;
            addActionListener(this);
        }
        
    	public void actionPerformed(ActionEvent event) {
    		tf.setText( event.getActionCommand());
    		tf.enable();
    		String path = newTeacherTable.get(event.getActionCommand());
    		if(path == null) {
    			path = teacherTableFileLoc.get(event.getActionCommand());
    		}
    		if(path == null) {
    			path = classTableFileLoc.get(event.getActionCommand());
    		}
    		
    				try {
    					BufferedReader br = new BufferedReader(new FileReader(path));	
    					String line;													
    					int row = 0 ;													
    					while ((line = br.readLine()) != null ) {	
    						String[] arr = line.split(",");
    						if(arr.length == 7){// split ','  (CSV file)
    					    if(model.getRowCount()<=9) {
    						model.addRow(new Object[0]);
    					    }
    						model.setValueAt(arr[0], row,0);							
    						model.setValueAt(arr[1], row,1);							
    						model.setValueAt(arr[2], row,2);							
    						model.setValueAt(arr[3], row,3);
    						model.setValueAt(arr[4], row,4);
    						model.setValueAt(arr[5], row,5);
    						model.setValueAt(arr[6], row,6);					
    						row++;
    						}
    					}
    					br.close();										
    				} catch (IOException ex) {
    					ex.printStackTrace();
    				}
    	
    	}
}