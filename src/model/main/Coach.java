
package model.main;

import service.CourseService;
import service.UserService;

public class Coach{
   private String coachId;
   private String coachName;
   CourseService courseService;
   UserService userService;

    public Coach(String coachId, String coachName, CourseService courseService, UserService userService) {
    }

    public String getCoachId() {
        return coachId;
    }


    public void setCoachId(String coachId) {
        this.coachId = coachId;

    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }this.coachId = coachId;
        this.coachName = coachName;
        this.courseService = courseService;
        this.userService = userService;
    }
}
