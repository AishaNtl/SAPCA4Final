import java.sql.*;
import java.util.Scanner;

public class InsecureApp {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:../data/database.db";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insecure Blog Application");

        // User Login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            Statement stmt = conn.createStatement();

            // Vulnerable to SQL Injection
            String loginQuery = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(loginQuery);

            if (rs.next()) {
                System.out.println("Login successful! Welcome, " + rs.getString("username"));
                // Simulating post creation
                System.out.print("Enter blog post title: ");
                String title = scanner.nextLine();
                System.out.print("Enter blog post content: ");
                String content = scanner.nextLine();

                // Vulnerable to Stored XSS
                String insertPost = "INSERT INTO blog_posts (author_id, title, content) VALUES ("
                        + rs.getInt("id") + ", '" + title + "', '" + content + "')";
                stmt.execute(insertPost);
                System.out.println("Blog post created.");
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}