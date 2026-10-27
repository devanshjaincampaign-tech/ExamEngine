package model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String rollNo;

    public Student(String name, String rollNo){
        this.name=name;
        this.rollNo=rollNo;
    }

    public String getName() {
        return name;
    }

    public String getRollNo(){
        return rollNo;
    }

    @Override 
    public String toString() {
        return name + "(" + rollNo + ")";
    }
}