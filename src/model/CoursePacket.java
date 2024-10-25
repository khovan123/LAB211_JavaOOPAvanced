package model;

import exception.InvalidDataException;
import service.UserProgressService;

//this class store coursepacket data, it can change by some action but dont effect on coursesegment
//please use key "new" to create courseSegment in this class, aim to create new address to store data
public class CoursePacket {

    private String coursePacketId;
    private String userId;
    private String courseSegmentId;
    private Course course;
    private UserProgressService userProgressService;

    public CoursePacket() {
        this.userProgressService = new UserProgressService();
    }

    public CoursePacket(String coursePacketId, String userId, String courseSegmentId) {
        this.coursePacketId = coursePacketId;
        this.userId = userId;
        this.courseSegmentId = courseSegmentId;
        this.userProgressService = new UserProgressService();
    }

    public CoursePacket(String coursePacketId, String userId, String courseSegmentId, Course course) {
        this.coursePacketId = coursePacketId;
        this.userId = userId;
        this.courseSegmentId = courseSegmentId;
        this.course = course;
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

    public Course getCourseSegment() {
        return course;
    }

    public void setCourseSegment(Course course) {
        this.course = course;
    }

    public UserProgressService getUserProgressService() {
        return userProgressService;
    }

    public String getCourseSegmentId() {
        return courseSegmentId;
    }

    public void setCourseSegmentId(String courseSegmentId) {
        this.courseSegmentId = courseSegmentId;
    }

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }
}
