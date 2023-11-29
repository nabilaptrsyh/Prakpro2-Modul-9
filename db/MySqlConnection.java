package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Kelas untuk mengelola koneksi ke database MySQL.
 */
public class MySqlConnection {

    // URL database
    private final static String DB_URL = "jdbc:mysql://localhost:8889/pp2_biodata";

    // Nama pengguna dan kata sandi untuk mengakses database
    private final static String DB_USER = "root";
    private final static String DB_PASS = "root";

    // Instance tunggal dari kelas ini (Singleton pattern)
    private static MySqlConnection instance;

    /**
     * Metode untuk mendapatkan instance kelas ini (Singleton pattern).
     * @return Instance dari MySqlConnection
     */
    public static MySqlConnection getInstance() {
        if (instance == null) {
            instance = new MySqlConnection();
        }
        return instance;
    }

    /**
     * Metode untuk mendapatkan koneksi ke database.
     * @return Objek Connection ke database MySQL
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            // Memuat driver JDBC untuk MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database menggunakan URL, nama pengguna, dan kata sandi
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            // Menangkap dan mencetak exception jika terjadi kesalahan
            e.printStackTrace();
        }
        return connection;
    }
}
