CREATE TABLE PersonModel(
	PersonID INT IDENTITY(1,1) PRIMARY KEY,
	FullName NVARCHAR(500),
	DoB DATE,
	Phone NVARCHAR(250),
	Active BIT DEFAULT 1,
	)

CREATE TABLE CoachModel(
	CoachID INT PRIMARY KEY,
	Certificate NVARCHAR(500),
	CONSTRAINT PK_PersonID_CM FOREIGN KEY (CoachID) REFERENCES PersonModel(PersonID)
	)

CREATE TABLE UserModel(
	UserID INT PRIMARY KEY,
	Addventor BIT DEFAULT 1,
	CONSTRAINT PK_PersonID_UM FOREIGN KEY (UserID) REFERENCES PersonModel(PersonID)
	)

CREATE TABLE CourseComboModel(
	ComboID  INT IDENTITY(1,1)  PRIMARY KEY,
	ComboName NVARCHAR(500),
	Sales DECIMAL(10,2),
	)

CREATE TABLE CourseModel(
	CourseID  INT IDENTITY(1,1) PRIMARY KEY,
	CourseName NVARCHAR(500),
	Addventor BIT,
	GenerateDate DATE,
	Price DECIMAL(10,2),
	ComboID INT DEFAULT NULL,
	CoachID INT,
	CONSTRAINT FK_CoachID FOREIGN KEY (CoachID) REFERENCES CoachModel(CoachID),
	CONSTRAINT FK_ComboID FOREIGN KEY (ComboID) REFERENCES CourseComboModel(ComboID),
	)

CREATE TABLE WorkoutModel(
	WorkoutID INT IDENTITY(1,1) PRIMARY KEY,
	WorkoutName NVARCHAR(500),
	Repetition INT,
	Sets INT,
	Duration INT,
	CourseID INT,
	CONSTRAINT FK_CourseID_WorkoutModel FOREIGN KEY (CourseID) REFERENCES CourseModel(CourseID),
	)

CREATE TABLE RegistedCourseModel(
	RegistedCourseID INT IDENTITY(1,1)  PRIMARY KEY,
	RegistedDate DATE,
	FinishRegistedDate DATE,
	CourseID INT,
	UserID INT,
	CONSTRAINT FK_CourseID FOREIGN KEY (CourseID) REFERENCES CourseModel(CourseID),
	CONSTRAINT FK_UserID FOREIGN KEY (UserID) REFERENCES UserModel(UserID),
	)

CREATE TABLE UserProgressModel(
	UserProgressID  INT IDENTITY(1,1)  PRIMARY KEY,
	RegistedCourseID INT UNIQUE,
	CONSTRAINT FK_RegistedCourseID FOREIGN KEY (RegistedCourseID) REFERENCES RegistedCourseModel(RegistedCourseID),
	)
	
CREATE TABLE ScheduleModel(
	ScheduleID  INT IDENTITY(1,1) PRIMARY KEY,
	UserProgressID INT UNIQUE,
	CONSTRAINT FK_UserProgressID FOREIGN KEY (UserProgressID) REFERENCES UserProgressModel(UserProgressID),
	)
	
CREATE TABLE PracticalDayModel(
	PracticalDayID INT IDENTITY(1,1) PRIMARY KEY,
	PracticalDate DATE,
	Done BIT DEFAULT 0,
	ScheduleID INT,
	CONSTRAINT FK_ScheduleID_PDM FOREIGN KEY (ScheduleID) REFERENCES ScheduleModel(ScheduleID),
	)
	
CREATE TABLE RegistedWorkoutModel(
	RegistedWorkoutID  INT IDENTITY(1,1)  PRIMARY KEY,
	WorkoutID INT,
	PracticalDayID INT,
	CONSTRAINT FK_WorkoutID FOREIGN KEY (WorkoutID) REFERENCES WorkoutModel(WorkoutID),
	CONSTRAINT FK_PracticalDayID FOREIGN KEY (PracticalDayID) REFERENCES PracticalDayModel(PracticalDayID),	
	)

CREATE TABLE NutritionModel(
	NutritionID INT IDENTITY(1,1) PRIMARY KEY,
	Calories INT,
	PracticalDayID INT,
	CONSTRAINT FK_PracticalDayID_NM FOREIGN KEY (PracticalDayID) REFERENCES PracticalDayModel(PracticalDayID),
	)