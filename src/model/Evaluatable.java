package model;

public interface Evaluatable{
    boolean validateAnswer(String answer);
    int getMarks();
}