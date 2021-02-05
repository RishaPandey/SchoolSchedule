package com.nda.academy.timetable.constants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Constants {
	public Map<String, String> classMapping;

	public Map<String, Integer> weekNumMapping;
	public Map<String, Integer> timeNumMapping;
	public Map<String, String> teacherTableFileLoc;
	public Map<String, String> newteacherTableFileLocFileLoc;
	public Map<String, String> classTableFileLoc;
	public ArrayList<String> headerData;
	public ArrayList<String> rowStartData;
	public Map<String, String> oldteacherTableFileLoc;

	public Constants() {
		classMapping = new HashMap<String, String>();
		weekNumMapping = new HashMap<String, Integer>();
		timeNumMapping = new HashMap<String, Integer>();
		
		
		classMapping.put("10th", "Class10");
		classMapping.put("9th", "Class9");
		classMapping.put("8th", "Class8");
		classMapping.put("7th", "Class7");
		classMapping.put("6th", "Class6");
		
		
		weekNumMapping.put("Monday", 0);
		weekNumMapping.put("Tuesday", 1);
		weekNumMapping.put("Wednesday", 2);
		weekNumMapping.put("Thursday", 3);
		weekNumMapping.put("Friday", 4);
		weekNumMapping.put("Saturday", 5);
		
		
		timeNumMapping.put("8:00 AM", 0);
		timeNumMapping.put("9:00 AM", 1);
		timeNumMapping.put("10:00 AM", 2);
		timeNumMapping.put("11:00 AM", 3);
		timeNumMapping.put("12:00 PM", 4);
		timeNumMapping.put("1:00 PM", 5);
		timeNumMapping.put("2:00 PM", 6);
		timeNumMapping.put("3:00 PM", 7);
		timeNumMapping.put("4:00 PM", 8);

		
		teacherTableFileLoc = new HashMap<String, String>();
		teacherTableFileLoc.put("English",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - English.csv");
		teacherTableFileLoc.put("Maths",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Maths.csv");
		teacherTableFileLoc.put("Hindi",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Hindi.csv");
		teacherTableFileLoc.put("Science",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Science.csv");
		teacherTableFileLoc.put("Kannada",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Kannada.csv");

		
		newteacherTableFileLocFileLoc = new HashMap<String, String>();
		newteacherTableFileLocFileLoc.put("English",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\SUBJECT\\Teacher wise class timetable - EnglishNew.csv");
		newteacherTableFileLocFileLoc.put("Maths",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\SUBJECT\\Teacher wise class timetable - MathsNew.csv");
		newteacherTableFileLocFileLoc.put("Hindi",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\SUBJECT\\Teacher wise class timetable - HindiNew.csv");
		newteacherTableFileLocFileLoc.put("Science",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\SUBJECT\\Teacher wise class timetable - ScienceNew.csv");
		newteacherTableFileLocFileLoc.put("Kannada",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\SUBJECT\\Teacher wise class timetable - KannadaNew.csv");

		
		classTableFileLoc = new HashMap<String, String>();
		classTableFileLoc.put("10th",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\CLASS\\Class10.csv");
		classTableFileLoc.put("9th",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\CLASS\\Class9.csv");
		classTableFileLoc.put("8th",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\CLASS\\Class8.csv");
		classTableFileLoc.put("7th",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\CLASS\\Class7.csv");
		classTableFileLoc.put("6th",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\CLASS\\Class6.csv");
		
		
		oldteacherTableFileLoc = new HashMap<String, String>();
		oldteacherTableFileLoc.put("Original English",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - English.csv");
		oldteacherTableFileLoc.put("Original Maths",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Maths.csv");
		oldteacherTableFileLoc.put("Original Hindi",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Hindi.csv");
		oldteacherTableFileLoc.put("Original Science",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Science.csv");
		oldteacherTableFileLoc.put("Original Kannada",
				"C:\\Users\\rishpandey\\Downloads\\backend_assignment\\assignment\\Teacher wise class timetable - Kannada.csv");

		headerData = new ArrayList<String>();
		headerData.add("--");
		headerData.add("Monday");
		headerData.add("Tuesday");
		headerData.add("Wednesday");
		headerData.add("Thursday");
		headerData.add("Friday");
		headerData.add("Saturday");

		rowStartData = new ArrayList<String>();
		rowStartData.add("--");
		rowStartData.add("8:00 AM");
		rowStartData.add("9:00 AM");
		rowStartData.add("10:00 AM");
		rowStartData.add("11:00 AM");
		rowStartData.add("12:00 PM");
		rowStartData.add("1:00 PM");
		rowStartData.add("2:00 PM");
		rowStartData.add("3:00 PM");
		rowStartData.add("4:00 PM");
	}

}
