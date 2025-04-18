import java.util.*;

public class StudentView {
    private Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println("Choose an operation:");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
    }

    public int getUserChoice() {
        return scanner.nextInt();
    }

    public Student getStudentDetailsFromUser() {
        System.out.println("Enter Student ID:");
        int studentID = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Enter Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Department:");
        String department = scanner.nextLine();
        System.out.println("Enter Marks:");
        double marks = scanner.nextDouble();

        return new Student(studentID, name, department, marks);
    }

    public void displayAllStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public int getStudentIDFromUser() {
        System.out.println("Enter Student ID:");
        return scanner.nextInt();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
