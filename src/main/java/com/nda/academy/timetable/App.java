package com.nda.academy.timetable;
import java.io.IOException;

import com.nda.academy.timetable.constants.Constants;
import com.nda.academy.timetable.service.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ClassWiseTable readcsv = new ClassWiseTable();
        NewTimeTableTeachers newTimeTableTeachers = new NewTimeTableTeachers();
        try {
			readcsv.createSectionTable();
			newTimeTableTeachers.generateNewTimeTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
