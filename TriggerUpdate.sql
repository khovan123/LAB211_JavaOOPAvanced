CREATE TRIGGER tgUpdatePersonModel
ON FitnessCourse.dbo.PersonModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @PersonID NVARCHAR(250);
    DECLARE @FullName NVARCHAR(500);
    DECLARE @DoB DATE;
    DECLARE @Phone NVARCHAR(250);
    
    IF EXISTS ( SELECT 1 FROM inserted i JOIN deleted d ON i.PersonID = d.PersonID WHERE i.FullName <> d.FullName)
    BEGIN
        SELECT @PersonID = i.PersonID, @FullName = i.FullName FROM inserted i JOIN deleted d ON i.PersonID = d.PersonID WHERE i.FullName <> d.FullName;
        UPDATE PersonModel SET FullName = @FullName WHERE PersonID = @PersonID;       
    END
    IF EXISTS (SELECT 1 FROM inserted i JOIN deleted d ON i.PersonID = d.PersonID WHERE CONVERT(DATE,i.DoB) <> d.DoB)
    BEGIN
        SELECT @PersonID = i.PersonID, @DoB = CONVERT(DATE,i.DoB) FROM inserted i JOIN deleted d ON i.PersonID = d.PersonID WHERE CONVERT(DATE,i.DoB) <> d.DoB;
        UPDATE PersonModel SET DoB = @DoB WHERE PersonID = @PersonID;        
    END
    IF EXISTS (
        SELECT 1 FROM inserted i JOIN deleted d ON i.PersonID = d.PersonID WHERE i.Phone <> d.Phone)
    BEGIN
        SELECT @PersonID = i.PersonID, @Phone = i.Phone FROM inserted i JOIN deleted d ON i.PersonID = d.PersonID WHERE i.Phone <> d.Phone;
        UPDATE PersonModel SET Phone = @Phone WHERE PersonID = @PersonID;        
    END
END;

DROP TRIGGER tgUpdatePersonModel

CREATE TRIGGER tgUpdateCoachModel
ON FitnessCourse.dbo.CoachModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @CoachID NVARCHAR(250);
    DECLARE @Certificate NVARCHAR(500);
    
    IF EXISTS (SELECT 1 FROM inserted i JOIN deleted d ON i.CoachID = d.CoachID WHERE i.Certificate <> d.Certificate)
    BEGIN
        SELECT @CoachID = i.CoachID, @Certificate = i.Certificate FROM inserted i JOIN deleted d ON i.CoachID = d.CoachID WHERE i.Certificate <> d.Certificate;
        UPDATE CoachModel SET Certificate = @Certificate WHERE CoachID = @CoachID;
    END
END;

CREATE TRIGGER tgUpdateUserModel
ON FitnessCourse.dbo.UserModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @UserID NVARCHAR(250);
    DECLARE @Addventor BIT;
    
    IF EXISTS (SELECT 1 FROM inserted i JOIN deleted d ON i.UserID = d.UserID WHERE CAST(LOWER(i.Addventor)AS BIT) <> d.Addventor)
    BEGIN
        SELECT @UserID = i.UserID, @Addventor = CAST(LOWER(i.Addventor)AS BIT) FROM inserted i JOIN deleted d ON i.UserID = d.UserID WHERE CAST(LOWER(i.Addventor)AS BIT) <> d.Addventor;
        UPDATE UserModel SET Addventor = @Addventor WHERE UserID = @UserID;
    END
END;

DROP TRIGGER tgUpdateUserModel

CREATE TRIGGER tgCourseComboModel
ON FitnessCourse.dbo.CourseComboModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @ComboID NVARCHAR(250);
    DECLARE @ComboName NVARCHAR(500);
    DECLARE @Sales DECIMAL(10, 2);
    
	IF EXISTS (SELECT 1 FROM inserted i JOIN deleted d ON i.ComboID = d.ComboID WHERE i.ComboName <> d.ComboName
    )
    BEGIN
        SELECT @ComboID = i.ComboID, @ComboName = i.ComboName FROM inserted i JOIN deleted d ON i.ComboID = d.ComboID WHERE i.ComboName <> d.ComboName;
        UPDATE CourseComboModel SET ComboName = @ComboName WHERE ComboID = @ComboID;       
    END

    IF EXISTS (SELECT 1  FROM inserted i JOIN deleted d ON i.ComboID = d.ComboID WHERE CONVERT(DECIMAL(10,2),i.Sales) <> d.Sales)
    BEGIN
        SELECT @ComboID = i.ComboID, @Sales = i.Sales FROM inserted i JOIN deleted d ON i.ComboID = d.ComboID WHERE CONVERT(DECIMAL(10,2),i.Sales) <> d.Sales;
        UPDATE CourseComboModel SET Sales = @Sales WHERE ComboID = @ComboID;       
    END
END;

CREATE TRIGGER tgUpdateCourseModel
ON FitnessCourse.dbo.CourseModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @CourseID NVARCHAR(250);
    DECLARE @CourseName NVARCHAR(500);
    DECLARE @Addventor BIT;
    DECLARE @GenerateDate DATE;
    DECLARE @Price DECIMAL(10, 2);
    DECLARE @ComboID NVARCHAR(250);
    DECLARE @CoachID NVARCHAR(250);
    
    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE i.CourseName <> d.CourseName
    )
    BEGIN
        SELECT @CourseID = i.CourseID, @CourseName = i.CourseName 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE i.CourseName <> d.CourseName;

        UPDATE CourseModel 
        SET CourseName = @CourseName 
        WHERE CourseID = @CourseID;
        
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE CAST(LOWER(i.Addventor) AS BIT) <> d.Addventor
    )
    BEGIN
        SELECT @CourseID = i.CourseID, @Addventor = CAST(LOWER(i.Addventor) AS BIT) 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE CAST(LOWER(i.Addventor) AS BIT) <> d.Addventor;

        UPDATE CourseModel 
        SET Addventor = @Addventor 
        WHERE CourseID = @CourseID;
        
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE CONVERT(DATE, i.GenerateDate) <> d.GenerateDate
    )
    BEGIN
        SELECT @CourseID = i.CourseID, @GenerateDate = CONVERT(DATE, i.GenerateDate) 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE CONVERT(DATE, i.GenerateDate) <> d.GenerateDate;

        UPDATE CourseModel 
        SET GenerateDate = @GenerateDate 
        WHERE CourseID = @CourseID;
        
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE CONVERT(DECIMAL(10,2),i.Price) <> d.Price
    )
    BEGIN
        SELECT @CourseID = i.CourseID, @Price = CONVERT(DECIMAL(10,2),i.Price) 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE CONVERT(DECIMAL(10,2),i.Price) <> d.Price;

        UPDATE CourseModel 
        SET Price = @Price 
        WHERE CourseID = @CourseID;
        
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE i.ComboID <> d.ComboID
    )
    BEGIN
        SELECT @CourseID = i.CourseID, @ComboID = i.ComboID 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE i.ComboID <> d.ComboID;

		IF EXISTS(SELECT 1 FROM CourseComboModel WHERE ComboID = @ComboID)
        UPDATE CourseModel 
        SET ComboID = @ComboID 
        WHERE CourseID = @CourseID;
        
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE i.CoachID <> d.CoachID
    )
    BEGIN
        SELECT @CourseID = i.CourseID, @CoachID = i.CoachID 
        FROM inserted i
        JOIN deleted d ON i.CourseID = d.CourseID
        WHERE i.CoachID <> d.CoachID;

		IF EXISTS (SELECT 1 FROM CoachModel WHERE CoachID = @CoachID)
        UPDATE CourseModel 
        SET CoachID = @CoachID 
        WHERE CourseID = @CourseID;
        
    END
END;

CREATE TRIGGER tgUpdateWorkoutModel
ON FitnessCourse.dbo.WorkoutModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @WorkoutID NVARCHAR(250);
    DECLARE @WorkoutName NVARCHAR(500);
    DECLARE @Repetition INT;
    DECLARE @Sets INT;
    DECLARE @Duration INT;
    DECLARE @CourseID NVARCHAR(250);
    
    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE i.WorkoutName <> d.WorkoutName
    )
    BEGIN
        SELECT @WorkoutID = i.WorkoutID, @WorkoutName = i.WorkoutName 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE i.WorkoutName <> d.WorkoutName;

        UPDATE WorkoutModel 
        SET WorkoutName = @WorkoutName 
        WHERE WorkoutID = @WorkoutID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE CONVERT(INT,i.Repetition) <> d.Repetition
    )
    BEGIN
        SELECT @WorkoutID = i.WorkoutID, @Repetition = CONVERT(INT,i.Repetition) 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE CONVERT(INT,i.Repetition) <> d.Repetition;

        UPDATE WorkoutModel 
        SET Repetition = @Repetition 
        WHERE WorkoutID = @WorkoutID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE CONVERT(INT, i.Sets) <> d.Sets
    )
    BEGIN
        SELECT @WorkoutID = i.WorkoutID, @Sets = CONVERT(INT, i.Sets) 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE CONVERT(INT, i.Sets) <> d.Sets;

        UPDATE WorkoutModel 
        SET Sets = @Sets 
        WHERE WorkoutID = @WorkoutID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE CONVERT(INT, i.Duration) <> d.Duration
    )
    BEGIN
        SELECT @WorkoutID = i.WorkoutID, @Duration = CONVERT(INT, i.Duration) 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE CONVERT(INT, i.Duration) <> d.Duration;

        UPDATE WorkoutModel 
        SET Duration = @Duration 
        WHERE WorkoutID = @WorkoutID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE i.CourseID <> d.CourseID
    )
    BEGIN
        SELECT @WorkoutID = i.WorkoutID, @CourseID = i.CourseID 
        FROM inserted i
        JOIN deleted d ON i.WorkoutID = d.WorkoutID
        WHERE i.CourseID <> d.CourseID;

		IF EXISTS (SELECT 1 FROM CourseModel WHERE CourseID = @CourseID)
        UPDATE WorkoutModel 
        SET CourseID = @CourseID 
        WHERE WorkoutID = @WorkoutID;
    END
END;

CREATE TRIGGER tgUpdateRegistedCourseModel
ON FitnessCourse.dbo.RegistedCourseModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @RegistedCourseID NVARCHAR(250);
    DECLARE @RegistedDate DATE;
    DECLARE @FinishRegistedDate DATE;
    DECLARE @CourseID NVARCHAR(250);
    DECLARE @UserID NVARCHAR(250);

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE CONVERT(DATE, i.RegistedDate) <> d.RegistedDate
    )
    BEGIN
        SELECT @RegistedCourseID = i.RegistedCourseID, @RegistedDate = CONVERT(DATE, i.RegistedDate) 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE CONVERT(DATE, i.RegistedDate) <> d.RegistedDate;

        UPDATE RegistedCourseModel 
        SET RegistedDate = @RegistedDate 
        WHERE RegistedCourseID = @RegistedCourseID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE CONVERT(DATE,i.FinishRegistedDate) <> d.FinishRegistedDate
    )
    BEGIN
        SELECT @RegistedCourseID = i.RegistedCourseID, @FinishRegistedDate = CONVERT(DATE,i.FinishRegistedDate) 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE CONVERT(DATE,i.FinishRegistedDate) <> d.FinishRegistedDate;

        UPDATE RegistedCourseModel 
        SET FinishRegistedDate = @FinishRegistedDate 
        WHERE RegistedCourseID = @RegistedCourseID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE i.CourseID <> d.CourseID
    )
    BEGIN
        SELECT @RegistedCourseID = i.RegistedCourseID, @CourseID = i.CourseID 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE i.CourseID <> d.CourseID;

		IF EXISTS (SELECT 1 FROM CourseModel WHERE CourseID = @CourseID)
        UPDATE RegistedCourseModel 
        SET CourseID = @CourseID 
        WHERE RegistedCourseID = @RegistedCourseID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE i.UserID <> d.UserID
    )
    BEGIN
        SELECT @RegistedCourseID = i.RegistedCourseID, @UserID = i.UserID 
        FROM inserted i
        JOIN deleted d ON i.RegistedCourseID = d.RegistedCourseID
        WHERE i.UserID <> d.UserID;

		IF EXISTS(SELECT 1 FROM UserModel WHERE UserID = @UserID)
        UPDATE RegistedCourseModel
        SET UserID = @UserID 
        WHERE RegistedCourseID = @RegistedCourseID;
    END
END;

CREATE TRIGGER tgUpdateUserProgressModel
ON FitnessCourse.dbo.UserProgressModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @UserProgressID NVARCHAR(250);
    DECLARE @RegistedCourseID NVARCHAR(250);

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.UserProgressID = d.UserProgressID
        WHERE i.RegistedCourseID <> d.RegistedCourseID
    )
    BEGIN
        SELECT @UserProgressID = i.UserProgressID, @RegistedCourseID = i.RegistedCourseID 
        FROM inserted i
        JOIN deleted d ON i.UserProgressID = d.UserProgressID
        WHERE i.RegistedCourseID <> d.RegistedCourseID;

		IF EXISTS (SELECT 1 FROM RegistedCourseModel WHERE RegistedCourseID = @RegistedCourseID)
        UPDATE UserProgressModel 
        SET RegistedCourseID = @RegistedCourseID 
        WHERE UserProgressID = @UserProgressID;
    END
END;

CREATE TRIGGER tgUpdateScheduleModel
ON FitnessCourse.dbo.ScheduleModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @ScheduleID NVARCHAR(250);
    DECLARE @UserProgressID NVARCHAR(250);

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.ScheduleID = d.ScheduleID
        WHERE i.UserProgressID <> d.UserProgressID
    )
    BEGIN
        SELECT @ScheduleID = i.ScheduleID, @UserProgressID = i.UserProgressID 
        FROM inserted i
        JOIN deleted d ON i.ScheduleID = d.ScheduleID
        WHERE i.UserProgressID <> d.UserProgressID;

		IF EXISTS (SELECT 1 FROM UserProgressModel WHERE UserProgressID = @UserProgressID)
        UPDATE ScheduleModel 
        SET UserProgressID = @UserProgressID 
        WHERE ScheduleID = @ScheduleID;
    END
END;

CREATE TRIGGER tgUpdatePracticalDayModel
ON FitnessCourse.dbo.PracticalDayModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @PracticalDayID NVARCHAR(250);
    DECLARE @PracticalDate DATE;
    DECLARE @ScheduleID NVARCHAR(250);

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.PracticalDayID = d.PracticalDayID
        WHERE CONVERT(DATE, i.PracticalDate) <> d.PracticalDate
    )
    BEGIN
        SELECT @PracticalDayID = i.PracticalDayID, @PracticalDate = CONVERT(DATE, i.PracticalDate) 
        FROM inserted i
        JOIN deleted d ON i.PracticalDayID = d.PracticalDayID
        WHERE CONVERT(DATE, i.PracticalDate) <> d.PracticalDate;

        UPDATE PracticalDayModel 
        SET PracticalDate = @PracticalDate 
        WHERE PracticalDayID = @PracticalDayID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.PracticalDayID = d.PracticalDayID
        WHERE i.ScheduleID <> d.ScheduleID
    )
    BEGIN
        SELECT @PracticalDayID = i.PracticalDayID, @ScheduleID = i.ScheduleID 
        FROM inserted i
        JOIN deleted d ON i.PracticalDayID = d.PracticalDayID
        WHERE i.ScheduleID <> d.ScheduleID;

		IF EXISTS (SELECT 1 FROM ScheduleModel WHERE ScheduleID = @ScheduleID)
        UPDATE PracticalDayModel 
        SET ScheduleID = @ScheduleID 
        WHERE PracticalDayID = @PracticalDayID;
    END
END;

CREATE TRIGGER tgUpdateRegistedWorkoutModel
ON FitnessCourse.dbo.RegistedWorkoutModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @RegistedWorkoutID NVARCHAR(250);
    DECLARE @WorkoutID NVARCHAR(250);
    DECLARE @PracticalDayID NVARCHAR(250);

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.RegistedWorkoutID = d.RegistedWorkoutID
        WHERE i.WorkoutID <> d.WorkoutID
    )
    BEGIN
        SELECT @RegistedWorkoutID = i.RegistedWorkoutID, @WorkoutID = i.WorkoutID 
        FROM inserted i
        JOIN deleted d ON i.RegistedWorkoutID = d.RegistedWorkoutID
        WHERE i.WorkoutID <> d.WorkoutID;

		IF EXISTS(SELECT 1 FROM WorkoutModel WHERE WorkoutID = @WorkoutID)
        UPDATE RegistedWorkoutModel 
        SET WorkoutID = @WorkoutID 
        WHERE RegistedWorkoutID = @RegistedWorkoutID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.RegistedWorkoutID = d.RegistedWorkoutID
        WHERE i.PracticalDayID <> d.PracticalDayID
    )
    BEGIN
        SELECT @RegistedWorkoutID = i.RegistedWorkoutID, @PracticalDayID = i.PracticalDayID 
        FROM inserted i
        JOIN deleted d ON i.RegistedWorkoutID = d.RegistedWorkoutID
        WHERE i.PracticalDayID <> d.PracticalDayID;

		IF EXISTS(SELECT 1 FROM PracticalDayModel WHERE PracticalDayID = @PracticalDayID)
        UPDATE RegistedWorkoutModel 
        SET PracticalDayID = @PracticalDayID 
        WHERE RegistedWorkoutID = @RegistedWorkoutID;
    END
END;

CREATE TRIGGER tgUpdateNutritionModel
ON FitnessCourse.dbo.NutritionModel
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @NutritionID NVARCHAR(250);
    DECLARE @Calories INT;
    DECLARE @PracticalDayID NVARCHAR(250);

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.NutritionID = d.NutritionID
        WHERE CONVERT(INT, i.Calories) <> d.Calories
    )
    BEGIN
        SELECT @NutritionID = i.NutritionID, @Calories = CONVERT(INT, i.Calories) 
        FROM inserted i
        JOIN deleted d ON i.NutritionID = d.NutritionID
        WHERE CONVERT(INT, i.Calories) <> d.Calories;

        UPDATE NutritionModel 
        SET Calories = @Calories 
        WHERE NutritionID = @NutritionID;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN deleted d ON i.NutritionID = d.NutritionID
        WHERE i.PracticalDayID <> d.PracticalDayID
    )
    BEGIN
        SELECT @NutritionID = i.NutritionID, @PracticalDayID = i.PracticalDayID 
        FROM inserted i
        JOIN deleted d ON i.NutritionID = d.NutritionID
        WHERE i.PracticalDayID <> d.PracticalDayID;

		IF EXISTS (SELECT 1 FROM PracticalDayModel WHERE PracticalDayID = @PracticalDayID)
        UPDATE NutritionModel 
        SET PracticalDayID = @PracticalDayID 
        WHERE NutritionID = @NutritionID;
    END
END;

