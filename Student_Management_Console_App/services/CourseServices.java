package com.aurionpro.services;

import java.util.List;

import com.aurionpro.dao.CourseDao;
import com.aurionpro.model.Course;

public class CourseServices {
	private CourseDao courseDao;

    public CourseServices() {
        this.courseDao = new CourseDao();
    }

    public void addCourse(Course course) {
        courseDao.addCourse(course);
    }
    

    public void showAllCourses() {
    //  courseDao.showAllCourses();
        List<Course> courses = courseDao.showAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No active courses found.");
            return;
        }

        System.out.println("\n======== All Courses: ========");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-12s |\n", "Course ID", "Course Name", "Course Fees");
        System.out.println("--------------------------------------------------------------");

        for (Course course : courses) {
            System.out.printf("| %-10d | %-30s | %-12.2f |\n", 
                              course.getCourseId(), 
                              course.getCourseName(), 
                              course.getTotalFee());
        }

        System.out.println("--------------------------------------------------------------");
    }

    
    public void deleteCourse(int courseId) {
        // Soft delete
        courseDao.deleteCourse(courseId);
    }

    public void viewSubjectsInCourse(int courseId) {
        courseDao.viewSubjectsInCourse(courseId);
    }

    public void viewStudentsInCourse(int courseId) {
        courseDao.viewStudentsInCourse(courseId);
    }

    public void addSubjectToCourse(int courseId, String subjectName) {
        courseDao.addSubjectToCourse(courseId, subjectName);
    }
    
    public void reactivateCourse(String courseName) {
        courseDao.reactivateCourse(courseName);
    }
//    public List<Course> getInactiveCourses() {
//        return courseDao.getAllInactiveCourses();
//    }
    
    public void displayInactiveCourses() {
        List<Course> inactiveCourses = courseDao.getAllInactiveCourses();

        if (inactiveCourses.isEmpty()) {
            System.out.println("No inactive courses found.");
            return;
        }

        System.out.println("\n===== Inactive Courses: =====");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-12s |\n", "Course ID", "Course Name", "Course Fees");
        System.out.println("--------------------------------------------------------------");
        for (Course c : inactiveCourses) {
            System.out.printf("| %-10d | %-30s | %-12.2f |\n",
                    c.getCourseId(),
                    c.getCourseName(),
                    c.getTotalFee());
                  
        }
        System.out.println("--------------------------------------------------------------");
    }
    
    public void searchCourseById(int courseId) {
        Course course = courseDao.getCourseById(courseId);
        if (course == null) {
            System.out.println("No course found with ID: " + courseId);
            return;
        }
        printCourseDetails(course);
    }

    public void searchCourseByName(String name) {
        Course course = courseDao.getCourseByName(name);
        if (course == null) {
            System.out.println("No course found with name: " + name);
            return;
        }
        printCourseDetails(course);
    }

    private void printCourseDetails(Course course) {
    	System.out.println("\n========= Course Details =========");
    	System.out.printf("%-10s | %-20s | %-12s | %-6s\n", "Course ID", "Course Name", "Course Fees", "Active");
    	System.out.println("---------------------------------------------------------------------");
    	System.out.printf("%-10d | %-20s | %-12.2f | %-6s\n",
    	        course.getCourseId(),
    	        course.getCourseName(),
    	        course.getTotalFee(),
    	        course.getIsActive() ? "Yes" : "No");

        // Reuse subject & student view methods
        courseDao.viewSubjectsInCourse(course.getCourseId());
        courseDao.viewStudentsInCourse(course.getCourseId());
    }



}
