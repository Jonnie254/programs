package Student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Score {

    Connection conn = MyConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT MAX(id) FROM score")) {
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getDetails(int sid, int semesterNO) {
        try {
            ps = conn.prepareStatement("select * from course where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNO);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.jTextField12.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField16.setText(String.valueOf(rs.getInt(3)));
                Home.jTextCourse1.setText(rs.getString(4));
                Home.jTextCourse2.setText(rs.getString(5));
                Home.jTextCourse3.setText(rs.getString(6));
                Home.jTextCourse4.setText(rs.getString(7));
                Home.jTextCourse5.setText(rs.getString(8));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The student or semester does not exist");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //check if the student exist in the database
    public boolean isIdExists(int id) {
        try {
            ps = conn.prepareStatement("select * from score where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //check whether the student or the semester exists
    public boolean isSidSemesterNoExists(int sid, int semesterNo) {
        try {
            ps = conn.prepareStatement("select * from score where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(int id, int sid, int semester, String course1, String course2,
            String course3, String course4, String course5,
            double score1, double score2, double score3, double score4, double score5, double averange) {
        try {
            String sql = "insert into score values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setString(12, course5);
            ps.setDouble(13, score5);
            ps.setDouble(14, averange);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "scores added successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getScoreValue(JTable table, String searchValue) {
        try {
            String sql = "select * from score where concat(id, student_id, semester)like ? order by id asc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1); // id
                row[1] = rs.getInt(2); // student_id
                row[2] = rs.getInt(3); // semester
                row[3] = rs.getString(4); // course1
                try {
                    row[4] = rs.getDouble(5); // score1
                } catch (NumberFormatException e) {
                    row[4] = 0.0; // Set a default value or handle the error appropriately
                }

                row[5] = rs.getString(6); // course2

                try {
                    row[6] = rs.getDouble(7); // score2
                } catch (NumberFormatException e) {
                    row[6] = 0.0; // Set a default value or handle the error appropriately
                }

                row[7] = rs.getString(8); // course3

                try {
                    row[8] = rs.getDouble(9); // score3
                } catch (NumberFormatException e) {
                    row[8] = 0.0; // Set a default value or handle the error appropriately
                }

                row[9] = rs.getString(10); // course4

                try {
                    row[10] = rs.getDouble(11); // score4
                } catch (NumberFormatException e) {
                    row[10] = 0.0; // Set a default value or handle the error appropriately
                }

                row[11] = rs.getString(12); // course5

                try {
                    row[12] = rs.getDouble(13); // score5
                } catch (NumberFormatException e) {
                    row[12] = 0.0; // Set a default value or handle the error appropriately
                }

                row[13] = rs.getDouble(14); // average

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void update(int id, double score1, double score2, double score3, double score4, double score5, double average) {
        try {
            String sql = "UPDATE score SET score1=?, score2=?, score3=?, score4=?, score5=?, average=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, score1);
            ps.setDouble(2, score2);
            ps.setDouble(3, score3);
            ps.setDouble(4, score4);
            ps.setDouble(5, score5);
            ps.setDouble(6, average);
            ps.setInt(7, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score details updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
