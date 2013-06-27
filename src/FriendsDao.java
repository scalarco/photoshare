package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.String;

public class FriendsDao {

private static final String CHECK_ALREADY_FRIEND_STMT = "SELECT " +
	"friend_user_id FROM friends WHERE friend_user_id=? and person_user_id=?";

private static final String GET_FNAME_STMT = "SELECT " +
	"firstname FROM users WHERE user_id=?";

private static final String GET_LNAME_STMT = "SELECT " +
	"lastname FROM users WHERE user_id=?";

private static final String ALL_USER_IDS_STMT = "SELECT user_id FROM users";

private static final String ALL_FRIEND_IDS_STMT = "SELECT friend_user_id FROM friends WHERE person_user_id=?";

private static final String ADD_FRIEND_STMT = "INSERT INTO " +
	"friends (person_user_id, friend_user_id) VALUES (?, ?)";

private static final String USER_ID_FROM_EMAIL_STMT = "SELECT \"user_id\" FROM Users WHERE \"email\" = ?";
private static final String USER_EMAIL_FROM_ID_STMT = "SELECT \"email\" FROM Users WHERE \"user_id\" = ?";

	public static String getFullName(int uid){
	String temp1=getFirstName(uid);
	String temp2=getLastName(uid);
	return (temp1+" "+temp2);
	}

	public static String getFirstName(int uid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String tempName="";
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_FNAME_STMT);
			stmt.setInt(1, uid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tempName=rs.getString(1);
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

		return tempName;
	}

	public static String getLastName(int uid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String tempName="";
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_LNAME_STMT);
			stmt.setInt(1, uid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tempName=rs.getString(1);
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

		return tempName;
	}

	public static boolean checkAlreadyFriends(int usid, int fid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int tempId=(-1);
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_ALREADY_FRIEND_STMT);
			stmt.setInt(1, fid);
			stmt.setInt(2, usid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tempId=rs.getInt(1);
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

		return (tempId!=(-1));
}
	public void addFriend(String userem, String friendem){		
		if(getUSID(friendem)==-1){
		}
		else{
		
		int pid=getUSID(userem);
		int fid=getUSID(friendem);
		if(checkAlreadyFriends(pid, fid)){	
		}
		else{
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_FRIEND_STMT);
			stmt.setInt(1, pid);
			stmt.setInt(2, fid);
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
		}

	}



	public static int getUSID(String em){
	PreparedStatement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	int id=-1;
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
	public static String getUserEmail(int id){
	PreparedStatement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	String tempEm="";
	try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(USER_EMAIL_FROM_ID_STMT);
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
				tempEm=rs.getString(1);
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

		return tempEm;
	}

	public List<Integer> allFriendIds(String email) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int useid;
		List<Integer> friendIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_FRIEND_IDS_STMT);
			useid=getUSID(email);
			stmt.setInt(1, useid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				friendIds.add(rs.getInt(1));
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

		return friendIds;
	}
	public List<Integer> allUserIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int useid;
		List<Integer> userIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_USER_IDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userIds.add(rs.getInt(1));
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

		return userIds;
	}


}