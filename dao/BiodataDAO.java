package dao;

import AplikasiBiodata.Biodata;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BiodataDAO {
    private Connection connection;

    public BiodataDAO() {
        try {
            // Gantilah informasi koneksi sesuai dengan database Anda
            String url = "jdbc:mysql://localhost:8889/pp2_biodata";
            String username = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void simpanBiodata(Biodata biodata) {
        try {
            String query = "INSERT INTO biodata (nama, nomor_hp, jenis_kelamin, alamat) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, biodata.getNama());
            preparedStatement.setString(2, biodata.getNomorHp());
            preparedStatement.setString(3, biodata.getJenisKelamin());
            preparedStatement.setString(4, biodata.getAlamat());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

