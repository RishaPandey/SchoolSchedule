# SchoolSchedule

Solution:

The following SchoolSchedule project has been created using JAVA for backend services and Swing UI for creating desktop application.
H2 DB has been configured internally. 

RUN INSTRUCTIONS
----------------
1. Add "src\main\java\com\nda\academy\timetable\School2021.java" as your main class to launch the desktop application.
2. Add 2 Program Arguments : 2 Absolute path of folders where you want app to create your newly created tables for teachers and classes respectively.
  
APPLICATION OUTPUT
----------------
1. 'Database' folder is created in your class path consisting of all CLASS TIME-TABLES for [CLASS 6 - CLASS 10].
    NOTE: In order to visualize our local DB, you can use DbVisualiser tool. (username : sa, password : sa, url : <path till .db file in Database folder> 

2.  CLASS TIME-TABLES are also created in new CSV file, at location provided in 2nd Program Argument.

3.  Maps and creates a new TEACHER TIME-TABLE for all the teachers in new CSV file, at location provided in 1st Program Argument.
    NOTE: Table is updated so that no teacher is idle(Constraint: Since each class can have only 1 Co-teacher, time slots where ongoing classes are less and idle teachers are more, no new class has been alloted for that teacher as co-teacher and so, the teacher is still idle) 
   
4. The max teacher needed so that every class has 1 co-teacher in a class is caluculated while craeting the new time table for teachers.

USER INTERFACE:
----------------
1)On hitting menubar("Teacher Time-Table", "Class Time-Table", "Existing Time-Table"), dropdown opens with respective categories of subject teachers and classes.
  On clicking any sub-menu item, we can see TIME-TABLES on screen.

  MENU 1: "Teacher Time-Table" ---> Newly Created Subject Teachers Time-Table
  MENU 2: "Class Time-Table" ---> Newly Created Class-wise Time-Table
  MENU 3: "Existing Time-Table" ---> Old Subject Teachers Time-Table(provided)

2) Hit the button "Get More Teachers?" to get the value in textbox.



   
