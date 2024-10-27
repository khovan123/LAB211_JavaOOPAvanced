CREATE OR ALTER TRIGGER tgInsertPersonModel
ON FitnessCourse.dbo.PersonModel
INSTEAD OF INSERT
AS
BEGIN 
    DECLARE @PersonID NVARCHAR(250);
    DECLARE @FullName NVARCHAR(500);
    DECLARE @DoB NVARCHAR(250);
    DECLARE @Phone NVARCHAR(250);
    DECLARE cur1 CURSOR FOR
    SELECT PersonID, FullName, DoB, Phone FROM inserted;
    OPEN cur1;
    FETCH NEXT FROM cur1 INTO @PersonID, @FullName, @DoB, @Phone;
    
    WHILE @@FETCH_STATUS = 0
    BEGIN TRY
        IF NOT EXISTS(SELECT 1 FROM PersonModel WHERE PersonID = @PersonID)
		BEGIN
			INSERT INTO PersonModel(PersonID,FullName,DoB,Phone)
			VALUES (@PersonID,@FullName,CONVERT(DATE,@DoB),@Phone)
		END
        FETCH NEXT FROM cur1 INTO @PersonID, @FullName, @DoB, @Phone;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH    
    CLOSE cur1;
    DEALLOCATE cur1;
END;

CREATE OR ALTER TRIGGER tgInsertUserModel
ON FitnessCourse.dbo.UserModel
INSTEAD OF INSERT
AS
BEGIN 
    DECLARE @UserID NVARCHAR(250);
    DECLARE @Addventor NVARCHAR(5);
    DECLARE cur2 CURSOR FOR
    SELECT UserID, Addventor FROM inserted;
    OPEN cur2;
    FETCH NEXT FROM cur2 INTO @UserID, @Addventor;
 
    WHILE @@FETCH_STATUS = 0
    BEGIN TRY
        IF NOT EXISTS(SELECT 1 FROM UserModel WHERE UserID = @UserID)
		BEGIN
			INSERT INTO UserModel(UserID,Addventor)
			VALUES (@UserID,CAST(LOWER(@Addventor)AS BIT))
		END
        FETCH NEXT FROM cur2 INTO @UserID, @Addventor;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH    
    CLOSE cur2;
    DEALLOCATE cur2;
END;

CREATE OR ALTER TRIGGER tgInsertCourseModel
ON FitnessCourse.dbo.CourseModel
INSTEAD OF INSERT
AS
BEGIN 	
    DECLARE @CourseID NVARCHAR(250);
    DECLARE @CourseName NVARCHAR(500);
	DECLARE @Addventor NVARCHAR(5);
    DECLARE @GenerateDate NVARCHAR(250);
	DECLARE @Price NVARCHAR(20);
    DECLARE @CoachID NVARCHAR(250);
    DECLARE cur3 CURSOR FOR
    SELECT CourseID, CourseName, Addventor, GenerateDate, Price, CoachID FROM inserted;

    OPEN cur3;
    FETCH NEXT FROM cur3 INTO @CourseID, @CourseName, @Addventor, @GenerateDate, @Price, @CoachID;
    
    WHILE @@FETCH_STATUS = 0
    BEGIN TRY
        IF NOT EXISTS(SELECT 1 FROM CourseModel WHERE CourseID = @CourseID)
		BEGIN
			IF EXISTS(SELECT 1 FROM CoachModel WHERE CoachID = @CoachID)
			BEGIN
				INSERT INTO CourseModel(CourseID, CourseName, Addventor,GenerateDate,Price, CoachID)
				VALUES (@CourseID, @CourseName, CAST(LOWER(@Addventor)AS BIT), CONVERT(DATE,@GenerateDate),CONVERT(DECIMAL(10,2),@Price), @CoachID)
			END
		END
        FETCH NEXT FROM cur3 INTO @CourseID, @CourseName, @Addventor, @GenerateDate,@Price, @CoachID;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH    
    CLOSE cur3;
    DEALLOCATE cur3;
END;

CREATE OR ALTER TRIGGER tgInsertWorkoutModel
ON FitnessCourse.dbo.WorkoutModel
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @WorkoutID NVARCHAR(250);
	DECLARE @Repetition NVARCHAR(10);
	DECLARE @Sets NVARCHAR(10);
	DECLARE @Duration NVARCHAR(10);
	DECLARE @CourseID NVARCHAR(250);

	DECLARE cur4 CURSOR FOR
	SELECT WorkoutID, Repetition, Sets, Duration, CourseID FROM inserted
	OPEN cur4;
	FETCH NEXT FROM cur4 INTO @WorkoutID, @Repetition, @Sets, @Duration, @CourseID

	WHILE @@FETCH_STATUS=0
	BEGIN TRY
		IF NOT EXISTS(SELECT 1 FROM WorkoutModel WHERE WorkoutID = @WorkoutID)
		BEGIN
			IF EXISTS(SELECT 1 FROM CourseModel WHERE CourseID = @CourseID)
			BEGIN
				INSERT INTO WorkoutModel(WorkoutID, Repetition, Sets, Duration, CourseID)
				VALUES (@WorkoutID, CONVERT(INT, @Repetition), CONVERT(INT, @Sets), CONVERT(INT, @Duration), @CourseID);
			END
		END
		FETCH NEXT FROM cur4 INTO @WorkoutID, @Repetition, @Sets, @Duration, @CourseID
	END TRY
	BEGIN CATCH
	THROW;
	END CATCH
	CLOSE cur4;
	DEALLOCATE cur4;
END;

CREATE OR ALTER TRIGGER tgInsertRegistedCourseModel
ON FitnessCourse.dbo.RegistedCourseModel
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @RegistedCourseID NVARCHAR(250);
	DECLARE @RegistedDate NVARCHAR(250);
	DECLARE @FinishRegistedDate NVARCHAR(250);
	DECLARE @CourseID NVARCHAR(250);
	DECLARE @UserID NVARCHAR(250);

	DECLARE cur5 CURSOR FOR
	SELECT RegistedCourseID, RegistedDate, FinishRegistedDate, CourseID, UserID FROM inserted
	OPEN cur5;
	FETCH NEXT FROM cur5 INTO @RegistedCourseID, @RegistedDate, @FinishRegistedDate, @CourseID, @UserID;

	WHILE @@FETCH_STATUS=0
	BEGIN TRY
		IF NOT EXISTS(SELECT 1 FROM RegistedCourseModel WHERE RegistedCourseID = @RegistedCourseID)
		BEGIN
			IF EXISTS(SELECT 1 FROM CourseModel WHERE CourseID = @CourseID)
			AND EXISTS(SELECT 1 FROM UserModel WHERE UserID = @UserID)
			BEGIN
				INSERT INTO RegistedCourseModel(RegistedCourseID, RegistedDate, FinishRegistedDate, CourseID, UserID)
				VALUES (@RegistedCourseID,CONVERT(DATE,@RegistedDate),CONVERT(DATE,@FinishRegistedDate), @CourseID, @UserID);
			END
		END
		FETCH NEXT FROM cur5 INTO @RegistedCourseID, @RegistedDate, @FinishRegistedDate, @CourseID, @UserID;
	END TRY
	BEGIN CATCH
	THROW;
	END CATCH
	CLOSE cur5;
    DEALLOCATE cur5;
END;

CREATE OR ALTER TRIGGER tgInsertPracticalDayModel
ON FitnessCourse.dbo.PracticalDayModel
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @PracticalDayID NVARCHAR(250);
	DECLARE @PracticalDate NVARCHAR(250);
	DECLARE @ScheduleID NVARCHAR(250);

	DECLARE cur6 CURSOR FOR
	SELECT PracticalDayID, PracticalDate, ScheduleID FROM inserted
	OPEN cur6;
	FETCH NEXT FROM cur6 INTO @PracticalDayID, @PracticalDate, @ScheduleID;

	WHILE @@FETCH_STATUS=0
	BEGIN TRY
		IF NOT EXISTS(SELECT 1 FROM PracticalDayModel WHERE PracticalDayID = @PracticalDayID)
		BEGIN
			IF EXISTS(SELECT 1 FROM ScheduleModel WHERE ScheduleID = @ScheduleID)
			BEGIN
				INSERT INTO PracticalDayModel(PracticalDayID, PracticalDate, ScheduleID)
				VALUES (@PracticalDayID,CONVERT(DATE,@PracticalDate), @ScheduleID);
			END
		END
		FETCH NEXT FROM cur6 INTO @PracticalDayID, @PracticalDate, @ScheduleID;
	END TRY
	BEGIN CATCH
	THROW;
	END CATCH
	CLOSE cur6;
    DEALLOCATE cur6;
END;

CREATE OR ALTER TRIGGER tgInsertNutritionModel
ON FitnessCourse.dbo.NutritionModel
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @NutritionID NVARCHAR(250);
	DECLARE @Calories NVARCHAR(20);
	DECLARE @PracticalDayID NVARCHAR(250);

	DECLARE cur7 CURSOR FOR
	SELECT NutritionID, Calories, PracticalDayID FROM inserted
	OPEN cur7;
	FETCH NEXT FROM cur7 INTO @NutritionID, @Calories, @PracticalDayID;

	WHILE @@FETCH_STATUS=0
	BEGIN TRY
		IF NOT EXISTS(SELECT 1 FROM NutritionModel WHERE NutritionID = @NutritionID)
		BEGIN
			IF EXISTS(SELECT 1 FROM PracticalDayModel WHERE PracticalDayID = @PracticalDayID)
			BEGIN
				INSERT INTO NutritionModel(NutritionID, Calories, PracticalDayID)
				VALUES (@NutritionID,CONVERT(INT,@Calories), @PracticalDayID);
			END
		END
		FETCH NEXT FROM cur7 INTO @NutritionID, @Calories, @PracticalDayID;
	END TRY
	BEGIN CATCH
	THROW;
	END CATCH
	CLOSE cur7;
    DEALLOCATE cur7;
END;

CREATE OR ALTER TRIGGER tgInsertCourseComboModel
ON FitnessCourse.dbo.CourseComboModel
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @ComboID NVARCHAR(250);
	DECLARE @ComboName NVARCHAR(500);
	DECLARE @Sales NVARCHAR(20);

	DECLARE cur8 CURSOR FOR
	SELECT ComboID, ComboName, Sales FROM inserted
	OPEN cur8;
	FETCH NEXT FROM cur8 INTO @ComboID, @ComboName, @Sales;

	WHILE @@FETCH_STATUS=0
	BEGIN TRY
		IF NOT EXISTS(SELECT 1 FROM CourseComboModel WHERE ComboID = @ComboID)
		BEGIN
				INSERT INTO CourseComboModel(ComboID, ComboName, Sales)
				VALUES (@ComboID,@ComboName, CONVERT(DECIMAL(10,2),@Sales));
		END
		FETCH NEXT FROM cur8 INTO @ComboID, @ComboName, @Sales;
	END TRY
	BEGIN CATCH
	THROW;
	END CATCH
	CLOSE cur8;
    DEALLOCATE cur8;
END;