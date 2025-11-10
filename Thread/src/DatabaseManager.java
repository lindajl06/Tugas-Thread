import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3307/db_thread";
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS books ("
                + " id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + " title VARCHAR(255) NOT NULL,"
                + " `status` VARCHAR(50) NOT NULL"
                + ");";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("SQL Syntax Error (initializeDatabase): " + e.getMessage());
        }
    }

    public static void addBook(String title, String status) {
        String sql = "INSERT INTO books(title, `status`) VALUES(?, ?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Syntax Error (addBook): " + e.getMessage());
        }
    }

    public static List<String> getBooksByStatus(String status) {
        List<String> books = new ArrayList<>();

        String sql = "SELECT title FROM books WHERE `status` = ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                books.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Syntax Error (getBooksByStatus): " + e.getMessage());
        }
        return books;
    }

    public static void populateSampleData() {
        String checkSql = "SELECT COUNT(*) AS count FROM books";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkSql)) {
            
            if (rs.next() && rs.getInt("count") == 0) {
                System.out.println("Database kosong, mengisi dengan data contoh...");
                addBook("The Stranger", "To Read");
                addBook("A little life", "To Read");
                addBook("Madonna In a four coat", "To Read");
                addBook("Yellow Face", "Reading");
                addBook("Malice", "Read");
                addBook("Tokyo Zodiac Murder", "Read");
                addBook("Teka Teki rumah aneh", "Read");
                addBook("The Devotion of Mr.X", "Read");
            } else {
                System.out.println("Database sudah terisi, tidak perlu di-populate.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error (populateSampleData): " + e.getMessage());
        }
    }
}