package com.nda.academy.timetable.service;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nda.academy.timetable.constants.Constants;
import com.nda.academy.timetable.model.ClassInfo;
import com.nda.academy.timetable.model.TeacherTimeTable;

public class ClassWiseTable {
	boolean tableExists = false;
	Constants constants = new Constants();
	Map<String, String> classMapping = constants.classMapping;
	Map<String, String> teacherTable = constants.teacherTableFileLoc;
	Map<String, Integer> weekNumMapping = constants.weekNumMapping;
	Map<String, Integer> timeNumMapping = constants.timeNumMapping;
	Map<String, String> classTable = constants.classTableFileLoc;
	ArrayList<ClassInfo> class10 = new ArrayList<ClassInfo>();
	ArrayList<ClassInfo> class9 = new ArrayList<ClassInfo>();
	ArrayList<ClassInfo> class8 = new ArrayList<ClassInfo>();
	ArrayList<ClassInfo> class7 = new ArrayList<ClassInfo>();
	ArrayList<ClassInfo> class6 = new ArrayList<ClassInfo>();

	public void createSectionTable() throws IOException {
		createDB(); // creates section wise Tables if not present
		addDbEntry(); // add timetable data for every class
		createTimeTable(); // create cv files for each class
	}

	public void createDB() {
		try {
			Class.forName("org.h2.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/TT", "sa", "sa");
			ResultSet rset = con.getMetaData().getTables(null, null, "CLASS%", null);
			if (rset.next()) {
				tableExists = true;
			}
			if (!tableExists) {
				for (Map.Entry<String, String> entry : classMapping.entrySet()) {
					String sql = createStatement(entry.getValue());
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.executeUpdate();
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addDbEntry() throws IOException {
		for (int indexDefaultValue = 0; indexDefaultValue < 54; indexDefaultValue++) {
			class10.add(new ClassInfo(null, null, null, null));
		}
		for (int indexDefaultValue = 0; indexDefaultValue < 54; indexDefaultValue++) {
			class9.add(new ClassInfo(null, null, null, null));
		}
		for (int indexDefaultValue = 0; indexDefaultValue < 54; indexDefaultValue++) {
			class8.add(new ClassInfo(null, null, null, null));
		}
		for (int indexDefaultValue = 0; indexDefaultValue < 54; indexDefaultValue++) {
			class7.add(new ClassInfo(null, null, null, null));
		}
		for (int indexDefaultValue = 0; indexDefaultValue < 54; indexDefaultValue++) {
			class6.add(new ClassInfo(null, null, null, null));
		}
		for (Map.Entry<String, String> entry : teacherTable.entrySet()) {
			String subject = entry.getKey();
			String fName = entry.getValue();
			String thisLine;
			int count = 0;

			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(fName);

			// FileInputStream fis = new FileInputStream(fName);
			DataInputStream myInput = new DataInputStream(is);
			int i = 0;

			ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
			while ((thisLine = myInput.readLine()) != null) {
				String[] splitData = thisLine.split(",");
				ArrayList<String> data = new ArrayList<String>();
				int mmm = splitData.length;
				for (String dataArray : splitData) {
					data.add(dataArray);
				}
				while (7 - mmm > 0) {
					data.add("");
					mmm++;
				}
				lines.add(data);
			}

			ArrayList<String> dayList = new ArrayList<String>();
			dayList = lines.get(0);
			for (int m = 1; m < lines.size(); m++) {
				ArrayList<String> row = lines.get(m);
				for (int n = 1; n < row.size(); n++) {
					String day = dayList.get(n);
					String time = row.get(0);
					String class_name = row.get(n);
					String table_Name = classMapping.get(class_name);
					if (table_Name == null) {
						class_name = "";
					}
					ClassInfo classInfo = new ClassInfo(time, day, subject, class_name);
					if (table_Name != null) {
						if (!tableExists) {
							try {
								Class.forName("org.h2.Driver").newInstance();
								Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/TT", "sa", "sa");
								String sql = createInsertStatement(table_Name, classInfo.getDay(), classInfo.getTime(),
										subject);
								PreparedStatement stmt = con.prepareStatement(sql);
								stmt.executeUpdate();
								con.close();
							} catch (InstantiationException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						int indexCol = weekNumMapping.get(day);
						int indexRow = timeNumMapping.get(time);
						int relativeIndex = indexCol + indexRow * 6;
						String className = class_name.toLowerCase();
						if (className.equals("10th")) {
							class10.set(relativeIndex, classInfo);
						} else if (className.equals("9th")) {
							class9.set(relativeIndex, classInfo);
						} else if (className.equals("8th")) {
							class8.set(relativeIndex, classInfo);
						} else if (className.equals("7th")) {
							class7.set(relativeIndex, classInfo);
						} else if (className.equals("6th")) {
							class6.set(relativeIndex, classInfo);
						}
					}
				}
			}
		}
	}

	public void createTimeTable() {
		ArrayList<String> headerData = constants.headerData;
		ArrayList<String> rowStartData = constants.rowStartData;
		final String lineSeparator = System.getProperty("line.separator");
		for (Map.Entry<String, String> X : classTable.entrySet()) {
			String path = X.getValue();
			String key = X.getKey().toLowerCase();
			ArrayList<ClassInfo> classData = new ArrayList<ClassInfo>();
			HashMap<String, ArrayList<String>> timeMapping = new HashMap<String, ArrayList<String>>();
			timeMapping.put("--", headerData);

			if (key.equals("10th")) {
				classData = class10;
			} else if (key.equals("9th")) {
				classData = class9;
			} else if (key.equals("8th")) {
				classData = class8;
			} else if (key.equals("7th")) {
				classData = class7;
			} else if (key.equals("6th")) {
				classData = class6;
			}
			// setup the header line
			StringBuilder sb = new StringBuilder();
			sb.append(lineSeparator);

			// append data in a loop
			for (int i = 0; i < classData.size(); i++) {
				ClassInfo c = classData.get(i);
				ArrayList<String> data = new ArrayList<String>();
				String time = c.getTime();
				if (time != null) {
					if (timeMapping.containsKey(time)) {
						int col = weekNumMapping.get(c.getDay());
						data = timeMapping.get(time);
						data.set(col + 1, c.getSubject());
						timeMapping.put(time, data);
					} else {
						for (int nodata = 0; nodata <= 6; nodata++) {
							data.add(" ");
						}
						int col = weekNumMapping.get(c.getDay());
						data.set(0, time);
						data.set(col + 1, (c.getSubject()));
						timeMapping.put(time, data);
					}
				}
			}
			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
			for (int coco = 0; coco < rowStartData.size(); coco++) {
				list.add(timeMapping.get(rowStartData.get(coco)));
			}
			for (ArrayList<String> data : list) {
				for (int k = 0; k < data.size(); k++) {
					sb.append(data.get(k));
					if (k != data.size() - 1) {
						sb.append(",");
					}
				}
				sb.append(lineSeparator);
			}

			try {
				Files.write(Paths.get(path), sb.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String createStatement(String tableName) {
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(id INTEGER PRIMARY KEY AUTO_INCREMENT not NULL, "
				+ " day VARCHAR(255) not NULL, " + " time VARCHAR(255) not NULL, " + " subject VARCHAR(255), "
				+ " PRIMARY KEY ( id ))";

		return sql;
	}

	public String createInsertStatement(String tableName, String day, String time, String subject) {
		String sql = "INSERT INTO " + tableName + "(day, time, subject)" + " values('" + day + "', '" + time + "', '"
				+ subject + "')";
		return sql;
	}

}
