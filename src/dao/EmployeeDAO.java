package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDAO {
	public List<Employee> findAll() {
		Connection conn = null;
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
			
			// JDBC ドライバを読み込み
			Class.forName("org.h2.Driver");
			
			// データベースへ接続
			conn = DriverManager.getConnection(
					"jdbc:h2:~/Documets/workspace/example/test", "sa", "");
			
			// SELECT 文を準備
			String sql = "SELECT ID, NAME, AGE FROM EMPLOYEE";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SELECT 文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
			
			// 結果表に格納されたレコードの内容を
			// Employee インスタンスに設定し、ArrayList インスタンスに追加
			while(rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				int age = rs.getInt("AGE");
				Employee employee = new Employee(id, name, age);
				empList.add(employee);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if(conn != null) {
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return empList;
	}
}
