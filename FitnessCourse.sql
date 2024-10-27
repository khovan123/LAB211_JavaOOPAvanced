CREATE TABLE PersonModel(
	PersonID NVARCHAR(250) PRIMARY KEY,
	FullName NVARCHAR(500),
	DoB DATE,
	Phone NVARCHAR(250),
	CHECK (PersonID LIKE '[CE][0-9]%'),
	CHECK (DoB < GETDATE()),
	CHECK (Phone LIKE '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
	)

CREATE TABLE CoachModel(
	PersonID NVARCHAR(250) PRIMARY KEY,
	Certificate NVARCHAR(500),
	CONSTRAINT PK_PersonID_CM FOREIGN KEY (PersonID) REFERENCES PersonModel(PersonID)
	)

CREATE TABLE UserModel(
	PersonID NVARCHAR(250) PRIMARY KEY ,
	CONSTRAINT PK_PersonID_UM FOREIGN KEY (PersonID) REFERENCES PersonModel(PersonID)
	)

CREATE TABLE CourseModel(
	CourseID NVARCHAR(250) PRIMARY KEY,
	CourseName NVARCHAR(500),
	GenerateDate DATE,
	CoachID NVARCHAR(250),
	CONSTRAINT FK_CoachID FOREIGN KEY (CoachID) REFERENCES CoachModel(PersonID),
	)

CREATE TABLE WorkoutModel(
	WorkoutID NVARCHAR(250) PRIMARY KEY,
	WorkoutName NVARCHAR(500),
	Repetition INT,
	Sets INT,
	Duration INT,
	CourseID NVARCHAR(250),
	CONSTRAINT FK_CourseID_WorkoutModel FOREIGN KEY (CourseID) REFERENCES CourseModel(CourseID),
	)

CREATE TABLE RegistedCourseModel(
	RegistedCourseID  NVARCHAR(250) PRIMARY KEY,
	CourseID NVARCHAR(250),
	RegistrationDate DATE,
	UserID NVARCHAR(250),
	CONSTRAINT FK_CourseID FOREIGN KEY (CourseID) REFERENCES CourseModel(CourseID),
	CONSTRAINT FK_UserID FOREIGN KEY (UserID) REFERENCES UserModel(PersonID),
	)

CREATE TABLE UserProgressModel(
	UserProgressID NVARCHAR(250) PRIMARY KEY,
	RegistedCourseID NVARCHAR(250) UNIQUE,
	CONSTRAINT FK_RegistedCourseID FOREIGN KEY (RegistedCourseID) REFERENCES RegistedCourseModel(RegistedCourseID),
	)

CREATE TABLE ScheduleModel(
	ScheduleID NVARCHAR(250) PRIMARY KEY,
	UserProgressID NVARCHAR(250) UNIQUE,
	CONSTRAINT FK_UserProgressID FOREIGN KEY (UserProgressID) REFERENCES UserProgressModel(UserProgressID),
	)

CREATE TABLE PracticalDayModel(
	PracticalDayID NVARCHAR(250) PRIMARY KEY,
	PracticalDate DATE,
	ScheduleID NVARCHAR(250),
	CONSTRAINT FK_ScheduleID_PDM FOREIGN KEY (ScheduleID) REFERENCES ScheduleModel(ScheduleID),
	)

CREATE TABLE RegistedWorkoutModel(
	RegistedWorkoutID NVARCHAR(250) PRIMARY KEY,
	WorkoutID NVARCHAR(250),
	PracticalDayID NVARCHAR(250),
	CONSTRAINT FK_WorkoutID FOREIGN KEY (WorkoutID) REFERENCES WorkoutModel(WorkoutID),
	CONSTRAINT FK_PracticalDayID FOREIGN KEY (PracticalDayID) REFERENCES PracticalDayModel(PracticalDayID),
	)

CREATE TABLE NutritionModel(
	NutritionID NVARCHAR(250) PRIMARY KEY,
	PracticalDayID NVARCHAR(250),
	Calories INT,
	CONSTRAINT FK_PracticalDayID_NM FOREIGN KEY (PracticalDayID) REFERENCES PracticalDayModel(PracticalDayID),
	)

DROP TABLE NutritionModel
DROP TABLE RegistedWorkoutModel
DROP TABLE PracticalDayModel
DROP TABLE ScheduleModel
DROP TABLE UserProgressModel
DROP TABLE RegistedCourseModel
DROP TABLE WorkoutModel
DROP TABLE CourseModel
DROP TABLE UserModel
DROP TABLE CoachModel
DROP TABLE PersonModel

CREATE OR ALTER TRIGGER tgInsert
ON FitnessCourse.dbo.PersonModel
INSTEAD OF INSERT
AS
BEGIN 
    DECLARE @PersonID NVARCHAR(250);
    DECLARE @FullName NVARCHAR(500);
    DECLARE @DoB NVARCHAR(250);
    DECLARE @Phone NVARCHAR(250);
    DECLARE cur CURSOR FOR
    SELECT PersonID, FullName, DoB, Phone FROM inserted;

    OPEN cur;
    FETCH NEXT FROM cur INTO @PersonID, @FullName, @DoB, @Phone;
    
    WHILE @@FETCH_STATUS = 0
    BEGIN TRY
        IF NOT EXISTS(SELECT 1 FROM PersonModel WHERE PersonID = @PersonID)
		BEGIN
			INSERT INTO PersonModel(PersonID,FullName,DoB,Phone)
			VALUES (@PersonID,@FullName,CONVERT(DATE,@DoB),@Phone)
		END
		ELSE
        FETCH NEXT FROM cur INTO @PersonID, @FullName, @DoB, @Phone;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH    
    CLOSE cur;
    DEALLOCATE cur;
END
