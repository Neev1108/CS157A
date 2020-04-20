
connect to cs157a;
Create Table HW3.Student(StudentID varchar(5) not null, FirstName varchar(10) not null, LastName varchar(10) not null);

Create Table HW3.Class(ClassID varchar(5) not null, ClassName varchar(10) not null, Desc varchar(20));

Create Table HW3.ClassReq(ClassID varchar(5) not null, PreReqID varchar(5), CoReq char(1));

Create Table HW3.Schedule(StudentID varchar(5) not null, ClassID varchar(5) not null, Semester char(1) not null, Year int);

ALTER TABLE HW3.Student
ADD CONSTRAINT NoDup
PRIMARY KEY(StudentID); 


Alter table HW3.Class
ADD CONSTRAINT primaryKeyClassID
PRIMARY KEY (ClassID);


Alter table HW3.ClassReq
ADD CONSTRAINT classIDForeignKey
FOREIGN KEY(ClassID) REFERENCES HW3.Class(ClassId) 
ON DELETE CASCADE;

Alter table HW3.Schedule
ADD CONSTRAINT foreignKeys
FOREIGN KEY(StudentID) REFERENCES HW3.Student(StudentID)
FOREIGN KEY(ClassID) REFERENCES HW3.Class(ClassID)
ON DELETE CASCADE;


Terminate;
 




	
	 












	 







	 







