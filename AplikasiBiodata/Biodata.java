package AplikasiBiodata;

// Prakpro2 Modul 9
// https://github.com/nabilaptrsyh/Prakpro2-Modul-9.git

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class Biodata extends JFrame {
    private JTextField txtNama, txtNomorHp, txtAlamat;
    private JRadioButton rdLaki, rdPerempuan;
    private JTextArea txtArea;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnEdit, btnHapus, btnSaveToFile;

    public Biodata() {
        initComponents();
    }
    
        public String getNama() {
        String nama = null;
        return nama;
    }

    public String getNomorHp() {
        String nomorHp = null;
        return nomorHp;
    }

    public String getJenisKelamin() {
        String jenisKelamin = null;
        return jenisKelamin;
    }

    public String getAlamat() {
        String alamat = null;
        return alamat;
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Biodata App");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel panelAtas = new JPanel(new GridLayout(6, 2));
        panelAtas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelAtas.add(new JLabel("Nama"));
        txtNama = new JTextField();
        panelAtas.add(txtNama);

        panelAtas.add(new JLabel("Nomor HP"));
        txtNomorHp = new JTextField();
        panelAtas.add(txtNomorHp);

        panelAtas.add(new JLabel("Jenis Kelamin"));
        ButtonGroup jenisKelaminGroup = new ButtonGroup();
        rdLaki = new JRadioButton("Laki-laki");
        rdPerempuan = new JRadioButton("Perempuan");
        jenisKelaminGroup.add(rdLaki);
        jenisKelaminGroup.add(rdPerempuan);
        JPanel jenisKelaminPanel = new JPanel();
        jenisKelaminPanel.add(rdLaki);
        jenisKelaminPanel.add(rdPerempuan);
        panelAtas.add(jenisKelaminPanel);

        panelAtas.add(new JLabel("Alamat"));
        txtAlamat = new JTextField();
        panelAtas.add(txtAlamat);

        btnSave = new JButton("Simpan");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnSaveToFile = new JButton("Simpan ke File");
        panelAtas.add(btnSave);
        panelAtas.add(btnEdit);
        panelAtas.add(btnHapus);
        panelAtas.add(btnSaveToFile);

        JPanel panelBawah = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nama");
        tableModel.addColumn("Nomor HP");
        tableModel.addColumn("Jenis Kelamin");
        tableModel.addColumn("Alamat");

        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        panelBawah.add(scrollPane, BorderLayout.CENTER);

        add(panelAtas, BorderLayout.NORTH);
        add(panelBawah, BorderLayout.CENTER);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = txtNama.getText();
                String nomorHp = txtNomorHp.getText();
                String jenisKelamin = rdLaki.isSelected() ? "Laki-laki" : "Perempuan";
                String alamat = txtAlamat.getText();

                if (!nama.isEmpty() && !nomorHp.isEmpty() && !alamat.isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menyimpan data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Simpan data ke database
                        try {
                            String url = "jdbc:mysql://localhost:8889/pp2_biodata";
                            String username = "root";
                            String password = "root";

                            Connection connection = DriverManager.getConnection(url, username, password);

                            String query = "INSERT INTO biodata (nama, nomor_hp, jenis_kelamin, alamat) VALUES (?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1, nama);
                            preparedStatement.setString(2, nomorHp);
                            preparedStatement.setString(3, jenisKelamin);
                            preparedStatement.setString(4, alamat);

                            preparedStatement.executeUpdate();

                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        // Tambahkan data ke tabel
                        Object[] rowData = {nama, nomorHp, jenisKelamin, alamat};
                        tableModel.addRow(rowData);

                        txtNama.setText("");
                        txtNomorHp.setText("");
                        txtAlamat.setText("");
                        jenisKelaminGroup.clearSelection();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Semua input harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        // ... (Kode lainnya)

        btnSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        if (!filePath.endsWith(".txt")) {
                            filePath += ".txt";
                        }

                        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                        for (int row = 0; row < tableModel.getRowCount(); row++) {
                            Vector rowData = (Vector) tableModel.getDataVector().elementAt(row);
                            for (int col = 0; col < rowData.size(); col++) {
                                writer.write(rowData.elementAt(col).toString());
                                if (col < rowData.size() - 1) {
                                    writer.write("\t");
                                }
                            }
                            writer.newLine();
                        }
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke file.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        // ... (Existing code)

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();

                if (selectedRow != -1) {
                    String nama = txtNama.getText();
                    String nomorHp = txtNomorHp.getText();
                    String jenisKelamin = rdLaki.isSelected() ? "Laki-laki" : "Perempuan";
                    String alamat = txtAlamat.getText();

                    if (!nama.isEmpty() && !nomorHp.isEmpty() && !alamat.isEmpty()) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin mengedit data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Edit data di database
                            try {
                                String url = "jdbc:mysql://localhost:8889/pp2_biodata";
                                String username = "root";
                                String password = "root";

                                Connection connection = DriverManager.getConnection(url, username, password);

                                String query = "UPDATE biodata SET nama=?, nomor_hp=?, jenis_kelamin=?, alamat=? WHERE nama=?";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, nama);
                                preparedStatement.setString(2, nomorHp);
                                preparedStatement.setString(3, jenisKelamin);
                                preparedStatement.setString(4, alamat);
                                preparedStatement.setString(5, tableModel.getValueAt(selectedRow, 0).toString());

                                preparedStatement.executeUpdate();

                                connection.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            // Edit data di tabel
                            tableModel.setValueAt(nama, selectedRow, 0);
                            tableModel.setValueAt(nomorHp, selectedRow, 1);
                            tableModel.setValueAt(jenisKelamin, selectedRow, 2);
                            tableModel.setValueAt(alamat, selectedRow, 3);

                            txtNama.setText("");
                            txtNomorHp.setText("");
                            txtAlamat.setText("");
                            jenisKelaminGroup.clearSelection();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Semua input harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();

                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Hapus data di database
                        try {
                            String url = "jdbc:mysql://localhost:8889/pp2_biodata";
                            String username = "root";
                            String password = "root";

                            Connection connection = DriverManager.getConnection(url, username, password);

                            String query = "DELETE FROM biodata WHERE nama=?";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1, tableModel.getValueAt(selectedRow, 0).toString());

                            preparedStatement.executeUpdate();

                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        // Hapus data di tabel
                        tableModel.removeRow(selectedRow);

                        txtNama.setText("");
                        txtNomorHp.setText("");
                        txtAlamat.setText("");
                        jenisKelaminGroup.clearSelection();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // ... (Existing code)

        
    }
    
    private void confirmExit() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Hanya menutup jendela saat user menekan "Yes"
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Biodata app = new Biodata();
            app.setVisible(true);
        });
    }
}
