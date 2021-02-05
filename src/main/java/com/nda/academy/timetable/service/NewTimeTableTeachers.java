package com.nda.academy.timetable.service;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.nda.academy.timetable.constants.Constants;
import com.nda.academy.timetable.model.TeacherTimeTable;


public class NewTimeTableTeachers {
	Constants constants = new Constants();
	Map<String, String> classMapping = constants.classMapping;
	Map<String, Integer> weekNumMapping = constants.weekNumMapping;
	Map<String, Integer> timeNumMapping = constants.timeNumMapping;
	Map<String, String> teacherTable = constants.teacherTableFileLoc;
	Map<String, String> newTeacherTable = constants.newteacherTableFileLocFileLoc;
	Map<String, LinkedList<String>> workingMap = new HashMap<String, LinkedList<String>>();
	Map<String, LinkedList<String>> notWorkingMap = new HashMap<String, LinkedList<String>>();
	int countOfExtra = 0;
	int maxExtraTeacherNeeded = Integer.MIN_VALUE;
	ArrayList<TeacherTimeTable> english = new ArrayList<TeacherTimeTable>();
	ArrayList<TeacherTimeTable> hindi = new ArrayList<TeacherTimeTable>();
	ArrayList<TeacherTimeTable> maths = new ArrayList<TeacherTimeTable>();
	ArrayList<TeacherTimeTable> science = new ArrayList<TeacherTimeTable>();
	ArrayList<TeacherTimeTable> kannada = new ArrayList<TeacherTimeTable>();


	public void generateNewTimeTable() throws IOException {
		
		for(int indexDefaultValue = 0; indexDefaultValue <54; indexDefaultValue++ ) {
			english.add(new TeacherTimeTable(null, null, null, null));
		}
		for(int indexDefaultValue = 0; indexDefaultValue <54; indexDefaultValue++ ) {
			hindi.add(new TeacherTimeTable(null, null, null, null));
		}
		for(int indexDefaultValue = 0; indexDefaultValue <54; indexDefaultValue++ ) {
			maths.add(new TeacherTimeTable(null, null, null, null));
		}
		for(int indexDefaultValue = 0; indexDefaultValue <54; indexDefaultValue++ ) {
			science.add(new TeacherTimeTable(null, null, null, null));
		}
		for(int indexDefaultValue = 0; indexDefaultValue <54; indexDefaultValue++ ) {
			kannada.add(new TeacherTimeTable(null, null, null, null));
		}

		for (Map.Entry<String, String> entry : teacherTable.entrySet()) {
			String subject = entry.getKey();
			String fName = entry.getValue();
			String thisLine;
			int count = 0;
			/*
			 * ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			 * InputStream is = classloader.getResourceAsStream(fName);
			 */
			FileInputStream fis = new FileInputStream(fName);
			DataInputStream myInput = new DataInputStream(fis);
			int i = 0;

			ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
			while ((thisLine = myInput.readLine()) != null) {
				//String[] splitData = new String[7];
				String[] splitData = thisLine.split(",");
				ArrayList<String> data = new ArrayList<String>();
				int mmm= splitData.length;	
				for (String dataArray : splitData) {				
					data.add(dataArray);
				}
				while(7-mmm>0) {
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
					String workingKey = day + "," + time;	
					if (table_Name != null) {
						int indexCol = weekNumMapping.get(day);
						int indexRow = timeNumMapping.get(time);
						int relativeIndex = indexCol+indexRow* 6;
						TeacherTimeTable existingTableData = new TeacherTimeTable(subject, class_name, day, time);
						String subjectChk=subject.toLowerCase(); 
						if(subjectChk.equals("english")) {
							english.set(relativeIndex, existingTableData);
						}else if(subjectChk.equals("hindi")) {
							hindi.set(relativeIndex, existingTableData);
						}else if(subjectChk.equals("maths")) {
							maths.set(relativeIndex, existingTableData);
						}else if(subjectChk.equals("science")) {
							science.set(relativeIndex, existingTableData);
						}else if(subjectChk.equals("kannada")) {
							kannada.set(relativeIndex, existingTableData);
						}
						if (workingMap.containsKey(workingKey)) {
							LinkedList<String> newEntry = new LinkedList<String>();
							newEntry = workingMap.get(workingKey);
							newEntry.add(class_name);
							workingMap.put(workingKey, newEntry);
						} else {
							LinkedList<String> newEntry = new LinkedList<String>();
							newEntry.add(class_name);
							workingMap.put(workingKey, newEntry);
						}
					} else {
						if (notWorkingMap.containsKey(workingKey)) {
							if(workingKey.startsWith("Thursday")){
							//System.out.println(workingKey + " for " + subject);	
							}
							LinkedList<String> newEntry = new LinkedList<String>();
							newEntry = notWorkingMap.get(workingKey);
							newEntry.add(subject);
							notWorkingMap.put(workingKey, newEntry);
						} else {
							LinkedList<String> newEntry = new LinkedList<String>();
							newEntry.add(subject);
							notWorkingMap.put(workingKey, newEntry);
						}
					}
				}
			}
		}
		int count = calculateNoCoTeacherClassroomCount();
		createNewTimeTable();
		System.out.println("Current Ongoing classes ::: " + workingMap);
		System.out.println("Idle Teacher no class :::" +notWorkingMap);
		System.out.println("Maximum Teacher needed : " + maxExtraTeacherNeeded);
	}
	
	public int getMaxTeacherNeeded() {
		return maxExtraTeacherNeeded;
	}
	
	public int calculateNoCoTeacherClassroomCount(){		
		int notWorkingCount = 0;
		int workingCount = 0;
		for(Map.Entry<String, LinkedList<String>> entry: notWorkingMap.entrySet()) {
			notWorkingCount += entry.getValue().size();
		}
		for(Map.Entry<String, LinkedList<String>> entry: workingMap.entrySet()) {
			workingCount += entry.getValue().size();
		}
		return workingCount - notWorkingCount;
	}
	
	public void createNewTimeTable(){
		Map<String, String> newTeacherTable = constants.newteacherTableFileLocFileLoc;
		ArrayList<String> headerData = constants.headerData;
		ArrayList<String> rowStartData = constants.rowStartData;
		
		for(Map.Entry<String, LinkedList<String>> entry : notWorkingMap.entrySet()) {
			LinkedList<String> classOngoing = workingMap.get(entry.getKey());
			int num = classOngoing.size() - entry.getValue().size();
			if(num<0) {
				countOfExtra += 0;	
			}else {
			countOfExtra += num ;
			maxExtraTeacherNeeded = Math.max(maxExtraTeacherNeeded, num);
			}
			for(int i =0; i<entry.getValue().size(); i++) {
				String className;
				String subject = entry.getValue().get(i);
				if(i <=classOngoing.size()-1) {
				 className = classOngoing.get(i);	
				}else {
					className = " ";
				}
				String[] arg= entry.getKey().split(",");
				String day = arg[0];
				String time = arg[1];
				TeacherTimeTable newTableData = new TeacherTimeTable(subject, className+" Co", day, time);
				int indexCol = weekNumMapping.get(day);
				int indexRow = timeNumMapping.get(time);
				int relativeIndex = indexCol+indexRow* 6;
				String subjectChk=subject.toLowerCase(); 
				if(subjectChk.equals("english")) {
					english.set(relativeIndex, newTableData);
				}else if(subjectChk.equals("hindi")) {
					hindi.set(relativeIndex, newTableData);
				}else if(subjectChk.equals("maths")) {
					maths.set(relativeIndex, newTableData);
				}else if(subjectChk.equals("science")) {
					science.set(relativeIndex, newTableData);
				}else if(subjectChk.equals("kannada")) {
					kannada.set(relativeIndex, newTableData);
				}
			}
		}

	   final String lineSeparator = System.getProperty("line.separator");
       for(Map.Entry<String, String> subjectTeacher : newTeacherTable.entrySet()) {
		String path = subjectTeacher.getValue();
		String key = subjectTeacher.getKey().toLowerCase();
		ArrayList<TeacherTimeTable> addingTeacherData = new ArrayList<TeacherTimeTable>();
		HashMap<String, ArrayList<String>> timeMapping = new HashMap<String, ArrayList<String>>();
		timeMapping.put("--", headerData);
		
		if(key.equals("english")) {
			addingTeacherData = english;
		}else if(key.equals("hindi")) {
			addingTeacherData = hindi;
		}else if(key.equals("maths")) {
			addingTeacherData = maths;
		}else if(key.equals("science")) {
			addingTeacherData = science;
		}else if(key.equals("kannada")) {
			addingTeacherData = kannada;
		}
		// setup the header line
		StringBuilder sb = new StringBuilder();
		sb.append(lineSeparator);

		//  append data in a loop
		for (int i = 0; i < addingTeacherData.size(); i++) {
			TeacherTimeTable c= addingTeacherData.get(i);
			ArrayList<String> data = new ArrayList<String>();
			String time = c.getTime();
			if(timeMapping.containsKey(time)){
				data = timeMapping.get(time);
				data.add(c.getClassName());
				timeMapping.put(time, data);
			}else {
				data.add(time);
				data.add(c.getClassName());
				timeMapping.put(time, data);
			}
		}
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for(int coco = 0; coco<rowStartData.size(); coco++) {
			list.add(timeMapping.get(rowStartData.get(coco)));	
		}
			for(ArrayList<String> data : list) {
			for(int k = 0; k<data.size(); k++) {
				sb.append(data.get(k));
				if(k != data.size()-1) {
				sb.append(",");	
				}
			}
			sb.append(lineSeparator);
			}
			
		try {
			Files.write(Paths.get(path), sb.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now write to file
		
       }
	}

}
