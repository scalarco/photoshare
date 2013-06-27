package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlbumsDao {

/* Collaborated with Nikhil Mathew to get this YOU_MAY_LIKE_STMT */
private static final String YOU_MAY_LIKE_STMT ="SELECT p.picture_id, (count(t.word)/count(t2.word)) as ratio FROM pictures p, has_tag t, has_tag t2 WHERE " +
			"t.picture_id = p.picture_id and t2.picture_id = p.picture_id " +
			"and t.word in (SELECT t3.word FROM has_tag t3, pictures p2, albums a, users u WHERE t3.picture_id = p2.picture_id and p2.albumid = a.albumid and a.user_id = u.user_id and u.email = ? GROUP BY t3.word ORDER BY count(t3.word) DESC limit 5) " +
			"and p.picture_id not in (SELECT p3.picture_id FROM pictures p3, albums a2, users u2 WHERE p3.albumid = a2.albumid and a2.user_id = u2.user_id and u2.email = ?) " +
			"GROUP BY p.picture_id ORDER BY ratio DESC";
  
  private static final String NEW_ALBUM_STMT = "INSERT INTO " +
      "Albums (\"user_id\", \"name\", \"date_created\") VALUES (?, ?, ?)";

  private static final String ALL_ALBUM_IDS_STMT = "SELECT \"albumid\" FROM Albums WHERE \"user_id\" = ? ORDER BY \"albumid\" DESC";
	
private static final String ALBUM_NAME_STMT = "SELECT \"name\" FROM Albums WHERE \"albumid\" = ?";
  
private static final String USER_ID_FROM_EMAIL_STMT = "SELECT \"user_id\" FROM Users WHERE \"email\" = ?";

private static final String OWNER_ID_STMT ="SELECT user_id FROM albums WHERE albumid=?";

private static final String DELETE_FROM_HASTAG_STMT = "DELETE FROM has_tag WHERE picture_id=?";

private static final String DELETE_FROM_HASLIKE_STMT = "DELETE FROM has_liked WHERE picture_id=?";

private static final String DELETE_FROM_COMMENTS_STMT = "DELETE FROM comments WHERE picture_id=?";

private static final String DELETE_FROM_PICTURES_STMT = "DELETE FROM pictures WHERE picture_id=?";

private static final String ALBUM_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures  WHERE \"albumid\" = ? ORDER BY \"picture_id\" DESC";

private static final String DELETE_FROM_ALBUMS_STMT ="DELETE FROM albums WHERE albumid=?";

private static final String GET_AID_PID_STMT = "SELECT albumid FROM pictures WHERE picture_id=?";

	public List<Integer> recommendPicturesIds(String em) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> rPicturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(YOU_MAY_LIKE_STMT);
			stmt.setString(1, em);
			stmt.setString(2, em);
			rs = stmt.executeQuery();
			while (rs.next()) {
				rPicturesIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return rPicturesIds;
	}



	public static int getAlbId(int pid){
	PreparedStatement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	int id=-1;
	try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(GET_AID_PID_STMT);
		stmt.setInt(1, pid);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
				id=rs.getInt(1);
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return id;
	}


	public List<Integer> albumPicturesIds(int aid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> aPicturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALBUM_PICTURE_IDS_STMT);
			stmt.setInt(1, aid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				aPicturesIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return aPicturesIds;
	}

	public void deleteHasliked(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_FROM_HASLIKE_STMT);
			stmt.setInt(1, pid);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public void deleteHastag(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_FROM_HASTAG_STMT);
			stmt.setInt(1, pid);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public void deleteComments(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_FROM_COMMENTS_STMT);
			stmt.setInt(1, pid);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public void deletePicture(int pid) {
		deleteHasliked(pid);
		deleteHastag(pid);
		deleteComments(pid);
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_FROM_PICTURES_STMT);
			stmt.setInt(1, pid);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}



	public void deleteAlbum(int aid) {
		List<Integer> albumPics=albumPicturesIds(aid);
		for(int albumPic: albumPics){
			deletePicture(albumPic);
		} 
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_FROM_ALBUMS_STMT);
			stmt.setInt(1, aid);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}


public static String getCurrentTimeStamp() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    Calendar cal = Calendar.getInstance();
    String strDate = dateFormat.format(cal.getTime());
    return strDate;
}

public static int getUSID(String em){
	PreparedStatement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	int id=0;
	try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(USER_ID_FROM_EMAIL_STMT);
		stmt.setString(1, em);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
				id=rs.getInt(1);
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return id;
	}

	public static int getOwnerId(int aid){
	PreparedStatement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	int id=-1;
	try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(OWNER_ID_STMT);
		stmt.setInt(1, aid);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
				id=rs.getInt(1);
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return id;
	}



	public void create(int usid, String n) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(NEW_ALBUM_STMT);
			stmt.setInt(1, usid);
			stmt.setString(2, n);
			stmt.setDate(3, Date.valueOf(getCurrentTimeStamp()));
			
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public List<Integer> allAlbumsIds(String email) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int useid;
		List<Integer> albumIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_ALBUM_IDS_STMT);
			useid=getUSID(email);
			stmt.setInt(1, useid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				albumIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return albumIds;
	}

public String getAlbumName(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String name="";
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALBUM_NAME_STMT);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				name=rs.getString(1);
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return name;
	}

}
