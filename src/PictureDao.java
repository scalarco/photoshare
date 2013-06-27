package photoshare;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.String;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 * modified by Sean Calarco
 */
public class PictureDao {

private static final String COUNT_USER_COMMENTS_STMT = "SELECT "+
	"count(commentid) FROM comments WHERE user_id=?";

private static final String COUNT_USER_PICS_STMT = "SELECT "+
	"count(p.picture_id) FROM pictures p, albums a WHERE p.albumid=a.albumid and a.user_id=?";

private static final String GET_FNAME_STMT = "SELECT " +
	"firstname FROM users WHERE user_id=?";

private static final String GET_LNAME_STMT = "SELECT " +
	"lastname FROM users WHERE user_id=?";

private static final String CHECK_WORD_STMT = "SELECT " +
	"word FROM tags WHERE word=?";

private static final String ADD_WORD_STMT = "INSERT INTO " +
	"tags (word) VALUES (?)";

private static final String ADD_TAG_PICTURE_STMT = "INSERT INTO " +
	"has_tag (picture_id, word) VALUES (?, ?)";

private static final String CHECK_NOT_OWNER_STMT="SELECT "+
	"a.user_id FROM Albums a, Pictures p WHERE a.albumid = p.albumid and p.picture_id = ?";

private static final String CREATE_COMMENT_STMT = "INSERT INTO " +
	"Comments (\"user_id\", \"picture_id\", \"date_left\", \"comment\") VALUES (?, ? ,? ,?)";

  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\" FROM Pictures WHERE \"picture_id\" = ?";

  private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "Pictures (\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"albumid\") VALUES (?, ?, ?, ?, ?, ?) Returning picture_id";

  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures ORDER BY \"picture_id\" DESC";

private static final String ALBUM_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures  WHERE \"albumid\" = ? ORDER BY \"picture_id\" DESC";

private static final String TAG_PICTURE_IDS_STMT = "SELECT distinct picture_id FROM has_tag  WHERE word = ? ORDER BY picture_id DESC";

private static final String MY_TAG_PICTURE_IDS_STMT = "SELECT distinct h.picture_id FROM has_tag h, pictures p, albums a  WHERE h.picture_id=p.picture_id and p.albumid=a.albumid and a.user_id=? and word = ?";

private static final String GET_PICTURE_CAP_STMT = "SELECT \"caption\" FROM Pictures WHERE \"picture_id\" =?";

private static final String SHOW_COMMENTS_STMT = "SELECT \"comment\" FROM Comments WHERE \"picture_id\" =?";

private static final String SHOW_TAGS_STMT = "SELECT word FROM has_tag WHERE picture_id =?";

private static final String ADD_LIKE_PICTURE_STMT = "INSERT INTO " +
	"has_liked (user_id, picture_id) VALUES (?, ?)";

private static final String CHECK_LIKE_STMT = "SELECT " +
	"user_id FROM has_liked WHERE picture_id=? and user_id=?";

private static final String SHOW_LIKES_STMT = "SELECT distinct user_id FROM has_liked WHERE picture_id =?";

private static final String GET_TAG_PHOTOS_STMT ="SELECT picture_id FROM has_tag WHERE word=?";

private static final String ORDER_WORDS_STMT = "SELECT distinct word, count(picture_id) FROM has_tag GROUP BY word ORDER BY count(picture_id) DESC";

public static List<String> getPopularWords(){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> wordList=new ArrayList<String>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ORDER_WORDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				wordList.add(rs.getString(1));	
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

		return wordList;
}



public static Integer[][] arrSort(Integer[][] myarr) {
        Arrays.sort(myarr, new Comparator<Integer[]>() {
            public int compare(Integer[] i1, Integer[] i2) {
                Integer j1 = i1[1];
                Integer j2 = i2[1];
                return j1.compareTo(j2);
            }
        });
        return myarr;
    }

public static Integer[][] inOrderScoreList(List<Integer> users, int size){
	Integer[][] tempArr=new Integer[size][2];
	int i=0;
	for (int user: users){
		tempArr[i][0]=user;
		tempArr[i][1]=((getNumUserComments(user))+(getNumUserPics(user)));
		i++;
	} 
	tempArr=arrSort(tempArr);
       Integer[][] reverse = new Integer[size][2];
       for(int k = size-1; k >= 0; k--) {
            for(int j = 1; j >= 0; j--) {
                reverse[size-1-k][1-j] = tempArr[k][1-j];
            }
        }


	return reverse;
	


}


public static int getNumUserComments(int uid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int numC=0;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(COUNT_USER_COMMENTS_STMT);
			stmt.setInt(1, uid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				numC=rs.getInt(1);
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

		return numC;

}


public static int getNumUserPics(int uid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int numP=0;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(COUNT_USER_PICS_STMT);
			stmt.setInt(1, uid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				numP=rs.getInt(1);
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

		return numP;

}


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


public static boolean checkLikeExist(int usid, int pid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int tempId=-1;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_LIKE_STMT);
			stmt.setInt(1, pid);
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

public static void addLike(int usid, int picid){
		if(checkLikeExist(usid, picid)){
		}
		else{
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_LIKE_PICTURE_STMT);
			stmt.setInt(1, usid);
			stmt.setInt(2, picid);
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




public static boolean checkWordExist(String word){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String tempWord="";
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_WORD_STMT);
			stmt.setString(1, word);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tempWord=rs.getString(1);
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

		return (!(tempWord.equals("")));
}

public static void putWord(String word){
		if(checkWordExist(word)){
		}
		else{
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_WORD_STMT);
			stmt.setString(1, word);
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

public static void putTag(int picid, String tag){
		PreparedStatement stmt = null;
		Connection conn = null;
		putWord(tag);
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_TAG_PICTURE_STMT);
			stmt.setInt(1, picid);
			stmt.setString(2, tag);
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


public List<Integer> searchTags(String tags){
	List<String> tagWords = Arrays.asList(tags.split("\\s*,\\s*"));
	List<Integer> picids= new ArrayList<Integer>();
	for(String tagWord: tagWords){
		picids.addAll(getTagPhotos(tagWord));
	}
	return picids;
}

public static List<Integer> getTagPhotos(String word){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Integer> tempList=new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_TAG_PHOTOS_STMT);
			stmt.setString(1, word);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tempList.add(rs.getInt(1));
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

		return tempList;
}

public void addTags(String tags, int picid){

		
		List<String> items = Arrays.asList(tags.split("\\s*,\\s*"));
		for(String item : items){
		putTag(picid, item);
		}

}


public static String getCurrentTimeStamp() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    Calendar cal = Calendar.getInstance();
    String strDate = dateFormat.format(cal.getTime());
    return strDate;
}


public static boolean matchID(int usid,int picid){
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int tempId=0;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_NOT_OWNER_STMT);
			stmt.setInt(1, picid);
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

		return (tempId==usid);
}


public void leaveComment(int usid, int picid, String comm){
	if(matchID(usid, picid)){
		
	}
	else{
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CREATE_COMMENT_STMT);
			if(usid==(-1)){
			stmt.setNull(1, java.sql.Types.INTEGER);
			}
			else{
			stmt.setInt(1, usid);
			}
			stmt.setInt(2, picid);
			stmt.setDate(3, Date.valueOf(getCurrentTimeStamp()));
			stmt.setString(4, comm);
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


public static String grabCap(int id){
PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String cap="";
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_PICTURE_CAP_STMT);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				cap=rs.getString(1);
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

		return cap;
}



  public Picture load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      stmt.setInt(1, id);
			rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
	
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

		return picture;
	}

	public void save(Picture picture, int albumID) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs=null;
		int picid=0;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
			stmt.setString(1, picture.getCaption());
			stmt.setBytes(2, picture.getData());
			stmt.setBytes(3, picture.getThumbdata());
			stmt.setLong(4, picture.getSize());
			stmt.setString(5, picture.getContentType());
			stmt.setInt(6, albumID);
			rs=stmt.executeQuery();
			while (rs.next()){
			picid=rs.getInt(1);
			}	
			rs.close();
			rs=null;
			
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
		addTags(picture.getTags(),picid);
	
		
	}

	public List<Integer> allPicturesIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
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

		return picturesIds;
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

public List<Integer> tagPicturesIds(String word) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> tPicturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(TAG_PICTURE_IDS_STMT);
			stmt.setString(1, word);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tPicturesIds.add(rs.getInt(1));
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

		return tPicturesIds;
	}
	public List<Integer> myTagPicturesIds(String word, int uid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> tPicturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(MY_TAG_PICTURE_IDS_STMT);
			stmt.setInt(1, uid);
			stmt.setString(2, word);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				tPicturesIds.add(rs.getInt(1));
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

		return tPicturesIds;
	}


public List<String> listComments(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<String> commentsList = new ArrayList<String>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SHOW_COMMENTS_STMT);
			stmt.setInt(1, pid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				commentsList.add(rs.getString(1));
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

		return commentsList;
	}

public List<String> listTags(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<String> tagList = new ArrayList<String>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SHOW_TAGS_STMT);
			stmt.setInt(1, pid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tagList.add(rs.getString(1));
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

		return tagList;
	}

public List<Integer> listLikes(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> likeList = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SHOW_LIKES_STMT);
			stmt.setInt(1, pid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				likeList.add(rs.getInt(1));
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

		return likeList;
	}
}