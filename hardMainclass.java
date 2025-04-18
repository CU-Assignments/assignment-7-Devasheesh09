import java.sql.*;
import java.util.*;

public class StudentApp {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database_name"; // Change to your database
        String username = "your_username"; // Change to your MySQL username
        String password = "your_password"; // Change to your MySQL password

        try {
            StudentController controller = new StudentController(jdbcURL, username, password);
            StudentView view = new StudentView();

            while (true) {
                view.displayMenu();
                int choice = view.getUserChoice();

                switch (choice) {
                    case 1: // Add Student
                        Student newStudent = view.getStudentDetailsFromUser();
                        boolean added = controller.createStudent(newStudent);
                        view.showMessage(added ? "Student added successfully!" : "Error adding student.");
                        break;

                    case 2: // View All Students
                        List<Student> students = controller.readAllStudents();
                        view.displayAllStudents(students);
                        break;

                    case 3: // Update Student
                        int updateID = view.getStudentIDFromUser();
                        Student updatedStudent = view.getStudentDetailsFromUser();
                        updatedStudent.setStudentID(updateID); // Ensure correct student ID is updated
                        boolean updated = controller.updateStudent(updatedStudent);
                        view.showMessage(updated ? "Student updated successfully!" : "Error updating student.");
                        break;

                    case 4: // Delete Student
                        int deleteID = view.getStudentIDFromUser();
                        boolean deleted = controller.deleteStudent(deleteID);
                        view.showMessage(deleted ? "Student deleted successfully!" : "Error deleting student.");
                        break;

                    case 5: // Exit
                        controller.closeConnection();
                        System.out.println("Exiting application...");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
