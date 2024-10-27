CREATE TABLE PersonModel(
	PersonID NVARCHAR(250) PRIMARY KEY,
	FullName NVARCHAR(500),
	DoB DATE,
	Phone NVARCHAR(250),
	Active BIT DEFAULT 1,
	CHECK (PersonID LIKE '[CU][0-9]%'),
	CHECK (DoB < GETDATE()),
	CHECK (Phone LIKE '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
	)

CREATE TABLE CoachModel(
	CoachID NVARCHAR(250) PRIMARY KEY,
	Certificate NVARCHAR(500),
	CHECK (CoachID LIKE 'C[0-9]%'),
	CONSTRAINT PK_PersonID_CM FOREIGN KEY (CoachID) REFERENCES PersonModel(PersonID)
	)

CREATE TABLE UserModel(
	UserID NVARCHAR(250) PRIMARY KEY,
	Addventor BIT DEFAULT 1,
	CHECK (UserID LIKE 'U[0-9]%'),
	CONSTRAINT PK_PersonID_UM FOREIGN KEY (UserID) REFERENCES PersonModel(PersonID)
	)

CREATE TABLE CourseComboModel(
	ComboID NVARCHAR(250) PRIMARY KEY,
	ComboName NVARCHAR(500),
	Sales DECIMAL(10,2),
	CHECK (ComboID LIKE 'CB[0-9]%'),
	CHECK (Sales > 0)
	)

CREATE TABLE CourseModel(
	CourseID NVARCHAR(250) PRIMARY KEY,
	CourseName NVARCHAR(500),
	Addventor BIT,
	GenerateDate DATE,
	Price DECIMAL(10,2),
	ComboID NVARCHAR(250) UNIQUE DEFAULT NULL,
	CoachID NVARCHAR(250),
	CHECK (CourseID LIKE 'CS[0-9]%'),
	CHECK (CoachID LIKE 'C[0-9]%'),
	CONSTRAINT FK_CoachID FOREIGN KEY (CoachID) REFERENCES CoachModel(CoachID),
	CONSTRAINT FK_ComboID FOREIGN KEY (ComboID) REFERENCES CourseComboModel(ComboID),
	CHECK (Price > 0),
	)

CREATE TABLE WorkoutModel(
	WorkoutID NVARCHAR(250) PRIMARY KEY,
	WorkoutName NVARCHAR(500),
	Repetition INT,
	Sets INT,
	Duration INT,
	CourseID NVARCHAR(250),
	CONSTRAINT FK_CourseID_WorkoutModel FOREIGN KEY (CourseID) REFERENCES CourseModel(CourseID),
	CHECK (WorkoutID LIKE 'WK[0-9]%'),
	CHECK (Repetition > 0),
	CHECK (Sets > 0),
	CHECK (Duration > 0),
	)

CREATE TABLE RegistedCourseModel(
	RegistedCourseID  NVARCHAR(250) PRIMARY KEY,
	RegistedDate DATE,
	FinishRegistedDate DATE,
	CourseID NVARCHAR(250),
	UserID NVARCHAR(250),
	CONSTRAINT FK_CourseID FOREIGN KEY (CourseID) REFERENCES CourseModel(CourseID),
	CONSTRAINT FK_UserID FOREIGN KEY (UserID) REFERENCES UserModel(UserID),
	CHECK (RegistedCourseID LIKE 'RC[0-9]%'),
	CHECK (CourseID LIKE 'CS[0-9]%'),
	CHECK (UserID LIKe 'U[0-9]%'),
	CHECK (FinishRegistedDate > RegistedDate),
	)

CREATE TABLE UserProgressModel(
	UserProgressID NVARCHAR(250) PRIMARY KEY,
	RegistedCourseID NVARCHAR(250) UNIQUE,
	CONSTRAINT FK_RegistedCourseID FOREIGN KEY (RegistedCourseID) REFERENCES RegistedCourseModel(RegistedCourseID),
	CHECK (UserProgressID LIKE 'UP[0-9]%'),
	CHECK (RegistedCourseID LIKE 'RC[0-9]%'),
	)
	
CREATE TABLE ScheduleModel(
	ScheduleID NVARCHAR(250) PRIMARY KEY,
	UserProgressID NVARCHAR(250) UNIQUE,
	CONSTRAINT FK_UserProgressID FOREIGN KEY (UserProgressID) REFERENCES UserProgressModel(UserProgressID),
	CHECK (ScheduleID LIKE 'SD[0-9]%'),
	CHECK (UserProgressID LIKE 'UP[0-9]%'),
	)
	
CREATE TABLE PracticalDayModel(
	PracticalDayID NVARCHAR(250) PRIMARY KEY,
	PracticalDate DATE,
	ScheduleID NVARCHAR(250),
	CONSTRAINT FK_ScheduleID_PDM FOREIGN KEY (ScheduleID) REFERENCES ScheduleModel(ScheduleID),
	CHECK (PracticalDayID LIKE 'PD[0-9]%'),	
	CHECK (ScheduleID LIKE 'SD[0-9]%'),
	)
	
CREATE TABLE RegistedWorkoutModel(
	RegistedWorkoutID NVARCHAR(250) PRIMARY KEY,
	WorkoutID NVARCHAR(250),
	PracticalDayID NVARCHAR(250),
	CONSTRAINT FK_WorkoutID FOREIGN KEY (WorkoutID) REFERENCES WorkoutModel(WorkoutID),
	CONSTRAINT FK_PracticalDayID FOREIGN KEY (PracticalDayID) REFERENCES PracticalDayModel(PracticalDayID),
	CHECK (RegistedWorkoutID LIKE 'RW[0-9]%'),	
	CHECK (PracticalDayID LIKE 'PD[0-9]%'),	
	CHECK (WorkoutID LIKE 'WK[0-9]%'),	
	)

CREATE TABLE NutritionModel(
	NutritionID NVARCHAR(250) PRIMARY KEY,
	Calories INT,
	PracticalDayID NVARCHAR(250),
	CONSTRAINT FK_PracticalDayID_NM FOREIGN KEY (PracticalDayID) REFERENCES PracticalDayModel(PracticalDayID),
	CHECK (NutritionID LIKE 'NT[0-9]%'),	
	CHECK (PracticalDayID LIKE 'PD[0-9]%'),
	CHECK (Calories > 0),
	)


DROP TABLE NutritionModel
DROP TABLE RegistedWorkoutModel
DROP TABLE PracticalDayModel
DROP TABLE ScheduleModel
DROP TABLE UserProgressModel
DROP TABLE RegistedCourseModel
DROP TABLE WorkoutModel
DROP TABLE CourseModel
DROP TABLE CourseComboModel
DROP TABLE UserModel
DROP TABLE CoachModel
DROP TABLE PersonModel
