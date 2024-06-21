package com.example.studentmanager;

public class Student {
    int student_id;
    String student_name, email, major;

    public Student(int student_id, String student_name, String email, String major) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.email = email;
        this.major = major;
    }
    public int getStudent_id() {
        return student_id;
    }
    public String getStudent_name() {
        return student_name;
    }
    public String getEmail() {
        return email;
    }
    public String getMajor() {
        return major;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMajor(String major) {
        this.major = major;
    }

}
