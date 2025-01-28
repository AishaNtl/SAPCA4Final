import java.sql.*;
import java.util.Scanner;

public class SecureApp {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:../data/database.db";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Secure Blog Application");

        // User Login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(loginQuery);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Replace with hashed password validation in real-world apps
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful! Welcome, " + rs.getString("username"));
                // Simulating post creation
                System.out.print("Enter blog post title: ");
                String title = scanner.nextLine();
                System.out.print("Enter blog post content: ");
                String content = scanner.nextLine();

                // Preventing Stored XSS
                String sanitizedTitle = sanitizeInput(title);
                String sanitizedContent = sanitizeInput(content);

                String insertPost = "INSERT INTO blog_posts (author_id, title, content) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertPost);
                pstmt.setInt(1, rs.getInt("id"));
                pstmt.setString(2, sanitizedTitle);
                pstmt.setString(3, sanitizedContent);
                pstmt.execute();
                System.out.println("Blog post created securely.");
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Basic input sanitization
    private static String sanitizeInput(String input) {
        return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}