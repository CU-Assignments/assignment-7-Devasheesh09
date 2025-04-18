import java.sql.*;
import java.util.*;

public class StudentController {

    private Connection connection;

    public StudentController(String jdbcURL, String username, String password) throws SQLException {
        // Establish connection to the database
        connection = DriverManager.getConnection(jdbcURL, username, password);
    }

    // Create Student record in the database
    public boolean createStudent(Student student) {
        String sql = "INSERT INTO students (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, student.getStudentID());
            pst.setString(2, student.getName());
            pst.setString(3, student.getDepartment());
            pst.setDouble(4, student.getMarks());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read all students from the database
    public List<Student> readAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int studentID = rs.getInt("StudentID");
                String name = rs.getString("Name");
                String department = rs.getString("Department");
                double marks = rs.getDouble("Marks");
                students.add(new Student(studentID, name, department, marks));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Update a student in the database
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, student.getName());
            pst.setString(2, student.getDepartment());
            pst.setDouble(3, student.getMarks());
            pst.setInt(4, student.getStudentID());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a student by StudentID
    public boolean deleteStudent(int studentID) {
        String sql = "DELETE FROM students WHERE StudentID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, studentID);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
