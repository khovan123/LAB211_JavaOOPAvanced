package model.main;

import service.UserProgressService;

public class CoursePacket {

    private String coursePacketId;
    private String userId;
    private CourseSegment courseSegment;
    private UserProgressService userProgressService;

    public CoursePacket() {
        this.userProgressService = new UserProgressService();
    }

    public CoursePacket(String coursePacketId, String userId, CourseSegment courseSegment) {
        this.coursePacketId = coursePacketId;
        this.userId = userId;
        this.courseSegment = courseSegment;
        this.userProgressService = new UserProgressService();
    }

    public String getCoursePacketId() {
        return coursePacketId;
    }

    public void setCoursePacketId(String coursePacketId) {
        this.coursePacketId = coursePacketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CourseSegment getCourseSegment() {
        return courseSegment;
    }

    public void setCourseSegment(CourseSegment courseSegment) {
        this.courseSegment = courseSegment;
    }

    public UserProgressService getUserProgressService() {
        return userProgressService;
    }

}
