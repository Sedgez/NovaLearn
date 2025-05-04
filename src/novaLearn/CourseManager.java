package novaLearn;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private static CourseManager instance;
    private final java.util.List<Course> enrolledCourses;

    private CourseManager() {
        enrolledCourses = new ArrayList<>();

        // Mock data (can be replaced later with real sources)
        enrolledCourses.add(new Course("CS101", "Object-Oriented Programming"));
        enrolledCourses.add(new Course("CS102", "Data Structures"));
        enrolledCourses.add(new Course("CS103", "Software Engineering"));
    }

    public static CourseManager getInstance() {
        if (instance == null) {
            instance = new CourseManager();
        }
        return instance;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void removeCourse(String courseCode) {
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course c = enrolledCourses.get(i);
            if (c.getCode().equalsIgnoreCase(courseCode)) {
                enrolledCourses.remove(i);
                break;
            }
        }
    }

    public Course getCourseByCode(String code) {
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course c = enrolledCourses.get(i);
            if (c.getCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
}
