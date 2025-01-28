import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:../data/database.db";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // Create users table
                String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "username TEXT NOT NULL, "
                        + "password TEXT NOT NULL, "
                        + "role TEXT NOT NULL)";
                stmt.execute(createUsersTable);

                // Create blog posts table
                String createPostsTable = "CREATE TABLE IF NOT EXISTS blog_posts ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "author_id INTEGER NOT NULL, "
                        + "title TEXT NOT NULL, "
                        + "content TEXT NOT NULL)";
                stmt.execute(createPostsTable);

                // Insert a default admin user (plaintext password for insecure version)
                String insertAdmin = "INSERT INTO users (username, password, role) "
                        + "VALUES ('admin', 'admin123', 'admin')";
                stmt.execute(insertAdmin);

                System.out.println("Database setup complete.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}