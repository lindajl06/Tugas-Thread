import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // Lokasi file database SQLite
    private static final String URL = "jdbc:sqlite:reading_list.db";

    /**
     * Membuat koneksi ke database.
     * @return Objek Connection
     */
    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Inisialisasi database: membuat tabel jika belum ada.
     */
    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS books ("
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " title TEXT NOT NULL,"
            + " status TEXT NOT NULL" // Status: "To Read", "Reading", "Read"
            + ");";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menambahkan buku ke database.
     */
    public static void addBook(String title, String status) {
        String sql = "INSERT INTO books(title, status) VALUES(?, ?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Mengambil semua buku berdasarkan statusnya.
     */
    public static List<String> getBooksByStatus(String status) {
        List<String> books = new ArrayList<>();
        String sql = "SELECT title FROM books WHERE status = ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            // Loop melalui hasil query
            while (rs.next()) {
                books.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    /**
     * Mengisi database dengan data contoh (jika database masih kosong).
     */
    public static void populateSampleData() {
        // Cek dulu apakah database sudah ada isinya
        String checkSql = "SELECT COUNT(*) AS count FROM books";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkSql)) {
            
            if (rs.next() && rs.getInt("count") == 0) {
                System.out.println("Database kosong, mengisi dengan data contoh...");
                // Buku yang ingin dibaca
                addBook("The Stranger", "To Read");
                addBook("A little life", "To Read");
                addBook("Madonna In a four coat", "To Read");

                // Buku yang sedang dibaca
                addBook("Yellow Face", "Reading");

                // Buku yang sudah dibaca
                addBook("Malice", "Read");
                addBook("Tokyo Zodiac Murder", "Read");
                addBook("Teka Teki rumah aneh", "Read");
                addBook("The Devotion of Mr.X", "Read");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
