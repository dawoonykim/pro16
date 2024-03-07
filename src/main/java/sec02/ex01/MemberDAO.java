package sec02.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private DataSource dataFactory;

	public MemberDAO() {

		try {
			Context ctx = new InitialContext();
			Context envConText = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envConText.lookup("jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	public boolean overlappedID(String id) {
		
		boolean result=false;
		
		try {
			conn=dataFactory.getConnection();
			String query="select decode(count(*),1,'true','false') as result from t_member";
			query+=" where id=?";
			System.out.println("preStatement"+query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			result=Boolean.parseBoolean(rs.getString("result"));
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
