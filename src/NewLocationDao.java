package photoshare;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewLocationDao {

private static final String GET_USER_STMT = "SELECT " +
      "user_id FROM Users WHERE email = ?";

private static final String NEW_LOC_STMT = "INSERT INTO " +
      "Location (user_id, birthcity, birthstate, birthcountry, currcity, currstate, currcountry) VALUES (?, ?, ?, ?, ?, ?, ?)";

public boolean create(String email, String bc, String bs, String bco, String cc, String cs, String cco) {
    	int usid;
	PreparedStatement stmt = null;
   	Connection conn = null;
   	ResultSet rs = null;
 	try {
	conn = DbConnection.getConnection();
      	stmt = conn.prepareStatement(GET_USER_STMT);
      	stmt.setString(1, email);
      	rs = stmt.executeQuery();
      	if (!rs.next()) {
       	// Theoretically this can't happen, but just in case...
        	return false;
      	}
     	int result = rs.getInt(1);
      	usid=result;
	try { stmt.close(); }
      	catch (Exception e) { }

	stmt = conn.prepareStatement(NEW_LOC_STMT);
     	stmt.setInt(1, usid);
     	stmt.setString(2, bc);
	stmt.setString(3, bs);
	stmt.setString(4, bco);
	stmt.setString(5, cc);
	stmt.setString(6, cs);
	stmt.setString(7, cco);
      
      	stmt.executeUpdate();

      	return true;
   	} 
	catch (SQLException e) {
      	e.printStackTrace();
      	throw new RuntimeException(e);
  	} finally {
      	if (rs != null) {
       	try { rs.close(); }
        	catch (SQLException e) { ; }
        	rs = null;
      	}
      
      	if (stmt != null) {
        	try { stmt.close(); }
        	catch (SQLException e) { ; }
        	stmt = null;
      	}
      
      	if (conn != null) {
        	try { conn.close(); }
        	catch (SQLException e) { ; }
        	conn = null;
      	}
}
}
}

