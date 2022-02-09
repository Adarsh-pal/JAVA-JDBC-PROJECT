import java.io.IOException; 
import java.sql.*;
import java.util.ArrayList;

public class DAO {
	Connection con = null;

	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/Q3wF9QbrBX", "Q3wF9QbrBX", "B9bRsouGAF");
		        
                } catch (Exception e) {
			System.out.println(e);
                        
		}
                
	}


	public boolean isVerified(String user, String pass, char c, String tName) {
		String query = "SELECT * FROM " + tName + " WHERE " + c + "_user LIKE " + "'" + user + "'" + " AND " + c
				+ "_pass LIKE " + "'" + pass + "'" + "";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public String getOName() {
		String query = "SELECT * FROM organisations";
		String name = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			name = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}


	public ArrayList<String> currCustDet(String cName) {
		String query = "SELECT * FROM `S_" + cName+"` ";
		ArrayList<String> list = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String detail = rs.getInt(1)+ rs.getString(2);
				list.add(detail);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String getCName(String user, String compName) {
		String query = "SELECT S_name FROM `O_" + compName + "` WHERE S_user LIKE " + "'" + user + "'" + " ";
		String detail = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			detail = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail;
	}

	public boolean removeCurr(String cname, String compName) {
		boolean isEmpty = true;
		String q1 = "SELECT * FROM `S_" + cname+"` ";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q1);
			if (rs.next()) {
				isEmpty = false;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		if (isEmpty) {
			return false;
		} else {
			String q2 = "DELETE FROM `S_" + cname + "` LIMIT 1";
			String q3 = "UPDATE `O_" + compName + "` SET total_users = total_users+1 WHERE S_name like '" + cname + "' ";
			String q4 = "UPDATE `O_" + compName + "` SET current_users = current_users-1 WHERE S_name like '" + cname
					+ "' ";
			try {
				Statement st = con.createStatement();
				st.executeUpdate(q2);
				st.executeUpdate(q3);
				st.executeUpdate(q4);
			} catch (Exception e) {
				System.out.println(e);
			}

			return true;
		}
	}



	public boolean checkString(String S) {

		for (int i = 0; i < S.length(); i++) {
			if (Character.isDigit(S.charAt(i)) == false) {
				return false;
			}
		}
		    return true;
	}

	public void pause(int i) {
		  try {
              Thread.sleep(i);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
		
	}
	

     public void waitMsg(String msg,int t,int n)throws IOException, InterruptedException {
        System.out.print(msg);
        for (int i = 0; i < n; i++) {
        	this.pause(t);
            System.out.print(".");	
	}
        this.clear();
          
      }

      public static void clear() throws IOException, InterruptedException{  
                  new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();  
      }  

}