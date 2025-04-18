import java.sql.*;
import java.util.Scanner;

public class ProductCRUDOperations {
    
    private static Connection connection = null;

    // Establishing a database connection
    public static void connectToDatabase() {
        try {
            String jdbcURL = "jdbc:mysql://localhost:3306/your_database_name";  // Replace with your database name
            String username = "your_username";  // Replace with your MySQL username
            String password = "your_password";  // Replace with your MySQL password
            
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
    
    // Method to close the database connection
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE: Insert a new product into the Product table
    public static void createProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Product ID:");
        int productId = sc.nextInt();
        sc.nextLine();  // Consume newline
        System.out.println("Enter Product Name:");
        String productName = sc.nextLine();
        System.out.println("Enter Product Price:");
        double price = sc.nextDouble();
        System.out.println("Enter Product Quantity:");
        int quantity = sc.nextInt();

        String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            // Start transaction
            connection.setAutoCommit(false);

            pst.setInt(1, productId);
            pst.setString(2, productName);
            pst.setDouble(3, price);
            pst.setInt(4, quantity);
            
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product added successfully!");
                connection.commit();  // Commit transaction
            } else {
                connection.rollback();  // Rollback transaction in case of failure
                System.out.println("Failed to add product.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("Transaction rolled back.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    // READ: Retrieve all products from the Product table
    public static void readProducts() {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ProductID | ProductName | Price | Quantity");
            while (rs.next()) {
                int productId
