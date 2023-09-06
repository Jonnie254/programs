package Student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class MarkSheet {

    Connection conn = MyConnection.getConnection();
    PreparedStatement ps;

    //check if the student exist in the database
    public boolean isIdExists(int sid) {
        try {
            ps = conn.prepareStatement("select * from score where student_id = ?");
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public void getScoreValue(JTable table, int sid) {
        try {
            String sql = "select * from score where student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sid);
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
            Logger.getLogger(MarkSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public double getCGPA(int sid) {
        double cgpa = 0.0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT AVG(average) FROM score WHERE student_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, sid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cgpa = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkSheet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the ResultSet, PreparedStatement, and any other resources if needed
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MarkSheet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cgpa;
    }
}
