package model;

import exception.InvalidDataException;
import service.UserProgressService;

//this class store coursepacket data, it can change by some action but dont effect on coursesegment
//please use key "new" to create courseSegment in this class, aim to create new address to store data
public class CoursePacket {

    private String coursePacketId;
    private String userId;
    private String courseSegmentId;
    private CourseSegment courseSegment;
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

    public CoursePacket(String coursePacketId, String userId, String courseSegmentId, CourseSegment courseSegment) {
        this.coursePacketId = coursePacketId;
        this.userId = userId;
        this.courseSegmentId = courseSegmentId;
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
