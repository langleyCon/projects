package gitFit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	private String url = "jdbc:sqlite:/Users/Connor/Documents/CS364/GitFit.db";
	
	private Connection connection;
	
	public Database() {
		
	}
	
	public void connect() throws SQLException{
		connection = DriverManager.getConnection(url);
	}
	
	public void disconnect() throws SQLException{
		connection.close();
	}
	
	public ResultSet query(String query) throws SQLException{
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		return results;
		
	}
	
	public ResultSet login(String username, String pass) throws SQLException{
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE Username = ? AND Password = ?");
		stmt.setString(1, username);
		stmt.setString(2, pass);
		ResultSet results = stmt.executeQuery();
		return results;
	}
	
	public void createUser(User user) throws SQLException{
		String insert = "INSERT INTO User (Username, Password, FirstName, LastName) VALUES (?,?,?,?) ";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getPassword());
		stmt.setString(3, user.getFirstName());
		stmt.setString(4, user.getLastName());
		int numRow = stmt.executeUpdate();
	}
	
	public void createProgram(User user, Program program) throws SQLException {
		String insert = "INSERT INTO Programs (ProgramName, ProgramType, Username) VALUES (?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setString(1, program.getProgramName());
		stmt.setInt(2, program.getProgramType());
		stmt.setString(3, user.getUsername());
		int r = stmt.executeUpdate();
	}
	
	public int getProgramId(Program program) throws SQLException{
		int id = 0;
		String selectId = "SELECT ProgramId FROM Programs WHERE ProgramName = ?";
		PreparedStatement stmt = connection.prepareStatement(selectId);
		stmt.setString(1, program.getProgramName());
		ResultSet results = stmt.executeQuery();
		id = results.getInt("ProgramId");
		return id;
	}
	
	public void createCal(Calisthenics cal, Program program) throws SQLException{
		String insert = "INSERT INTO Calisthenics (Pushup, Pullup, Situp, Squat, Dips, ProgramId)"
						+ "VALUES (?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setInt(1, cal.getPushups());
		stmt.setInt(2, cal.getPullups());
		stmt.setInt(3, cal.getSitups());
		stmt.setInt(4, cal.getSquats());
		stmt.setInt(5, cal.getDips());
		stmt.setInt(6, program.getProgramId());
		int i = stmt.executeUpdate();
	}
	
	public void createRun(Run run, Program program) throws SQLException{
		String insert = "INSERT INTO Runs (DesiredTime, DesiredDistance, ProgramId) VALUES (?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setInt(1, run.getTime());
		stmt.setInt(2, run.getDistance());
		stmt.setInt(3, program.getProgramId());
		int i = stmt.executeUpdate();
	}
	
	public void createGoalType(GoalType goal, User user) throws SQLException {
		String insert = "INSERT INTO Goals (GoalType, Username) VALUES (?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setInt(1, goal.getGoalType());
		stmt.setString(2, goal.getUsername());
		int i = stmt.executeUpdate();
	}
	
	public void createRunGoal(User user, RunGoal goal) throws SQLException{
		String insert = "INSERT INTO RunGoal (GoalName, Username, TimeGoal, DistanceGoal) VALUES (?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setString(1, goal.getName());
		stmt.setString(2, goal.getUsername());
		stmt.setInt(3, goal.getTime());
		stmt.setInt(4, goal.getDistance());
		int i = stmt.executeUpdate();
		
	}
	
	public int getRunGoalId(RunGoal goal) throws SQLException {
		String select = "SELECT RunGId FROM RunGoal WHERE GoalName = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, goal.getName());
		ResultSet results = stmt.executeQuery();
		return results.getInt("RunGId");
	}
	
	public void createCaliGoal(CaliGoal goal) throws SQLException {
		String insert;
		
		switch(goal.getType()) {
		case 1:
			insert = "INSERT INTO CalisthenicsGoal (CalisthenicName, Username, PushupGoal, CalisthenicsType) VALUES (?,?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(insert);
			stmt.setString(1, goal.getName());
			stmt.setString(2, goal.getUsername());
			stmt.setInt(3, goal.getPushGoal());
			stmt.setInt(4, goal.getType());
			int i = stmt.executeUpdate();
			break;
		case 2:
			insert = "INSERT INTO CalisthenicsGoal (CalisthenicName, Username, SitupGoal, CalisthenicsType) VALUES (?,?,?,?)";
			PreparedStatement stmt2 = connection.prepareStatement(insert);
			stmt2.setString(1, goal.getName());
			stmt2.setString(2, goal.getUsername());
			stmt2.setInt(3, goal.getSitGoal());
			stmt2.setInt(4, goal.getType());
			stmt2.executeUpdate();
			break;
		case 3:
			insert = "INSERT INTO CalisthenicsGoal (CalisthenicName, Username, PullupGoal, CalisthenicsType) VALUES (?,?,?,?)";
			PreparedStatement stmt3 = connection.prepareStatement(insert);
			stmt3.setString(1, goal.getName());
			stmt3.setString(2, goal.getUsername());
			stmt3.setInt(3, goal.getPullGoal());
			stmt3.setInt(4, goal.getType());
			stmt3.executeUpdate();
			break;
		case 4:
			insert = "INSERT INTO CalisthenicsGoal (CalisthenicName, Username, SquatGoal, CalisthenicsType) VALUES (?,?,?,?)";
			PreparedStatement stmt4 = connection.prepareStatement(insert);
			stmt4.setString(1, goal.getName());
			stmt4.setString(2, goal.getUsername());
			stmt4.setInt(3, goal.getSquatGoal());
			stmt4.setInt(4, goal.getType());
			stmt4.executeUpdate();
			break;
		case 5:
			insert = "INSERT INTO CalisthenicsGoal (CalisthenicName, Username, DipsGoal, CalisthenicsType) VALUES (?,?,?,?)";
			PreparedStatement stmt5 = connection.prepareStatement(insert);
			stmt5.setString(1, goal.getName());
			stmt5.setString(2, goal.getUsername());
			stmt5.setInt(3, goal.getDipGoal());
			stmt5.setInt(4, goal.getType());
			stmt5.executeUpdate();
			break;
		}
		
	}
	
	public int getProgramCount(User user, int type) throws SQLException{
		String select = ("SELECT count(ProgramType) AS Count FROM Programs WHERE Username = ? AND ProgramType = ?");
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		stmt.setInt(2, type);
		ResultSet set = stmt.executeQuery();
		return set.getInt("Count");
	}
	
	public ResultSet getRunWorkouts(User user) throws SQLException{
		String select = "SELECT * FROM Programs NATURAL JOIN Runs WHERE Username = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		ResultSet set = stmt.executeQuery();
		return set;
	}
	
	public ResultSet getCaliWorkouts(User user) throws SQLException{
		String select = "SELECT * FROM Programs NATURAL JOIN Calisthenics WHERE Username = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		ResultSet set = stmt.executeQuery();
		return set;
	}

	public void addCaliHistory(CaliHistory cal, User user) throws SQLException{
		String insert = "INSERT INTO CalisthenicsHistory (Username, PushupH, PullupH"
				+ ",SitupH,SquatH,DipsH, CalisDate) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setString(1, user.getUsername());
		stmt.setInt(2, cal.getPush());
		stmt.setInt(3, cal.getPull());
		stmt.setInt(4, cal.getSit());
		stmt.setInt(5, cal.getSquat());
		stmt.setInt(6, cal.getDip());
		stmt.setString(7, cal.getDate());
		stmt.executeUpdate();
		
	}

	public void createRunHis(User user, RunHis newHis) throws SQLException{
		String insert = "INSERT INTO RunHistory (Username, TimeH, DistanceH, Date) VALUES (?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(insert);
		stmt.setString(1, user.getUsername());
		stmt.setInt(2, newHis.getTime());
		stmt.setInt(3, newHis.getDis());
		stmt.setString(4, newHis.getDate());
		stmt.executeUpdate();
		
	}
	
	public int runGoalCount(String table, User user) throws SQLException{
		String count = "SELECT count(*) AS Count FROM " + table +" WHERE Username = ?";
		PreparedStatement stmt = connection.prepareStatement(count);
		stmt.setString(1, user.getUsername());
		ResultSet set = stmt.executeQuery();
		return set.getInt("Count");
		
	}

	public ResultSet getRunGoals(User user) throws SQLException{
		String select = "SELECT * FROM RunGoal WHERE Username = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		
		
		return stmt.executeQuery();
	}
	
	public ResultSet completedRunGoal(int id, User user) throws SQLException{
		String com = "SELECT Date "
						+ "FROM RunHistory "
						+ "WHERE DistanceH >= (SELECT DistanceGoal "
											 + "FROM RunGoal "
											 + "WHERE RunGId = ? "
											 + "LIMIT 1) "
						+ "AND TimeH >= (SELECT TimeGoal "
									   + "FROM RunGoal "
									   + "WHERE RunGId = ? "
									   + "LIMIT 1) "
						 + "AND Username = ? "
						+ "LIMIT 1";

		PreparedStatement stmt = connection.prepareStatement(com);
		stmt.setInt(1, id);
		stmt.setInt(2, id);
		stmt.setString(3, user.getUsername());
		return stmt.executeQuery();
		
	}

	public ResultSet runCheck(User user) throws SQLException{
		String select = "SELECT ProgramName "
						+ "FROM RunGoal NATURAL JOIN User "
							  + "NATURAL JOIN Programs "
							  + "NATURAL JOIN Runs "
						+ "WHERE DistanceGoal <= DesiredDistance AND "
							   + "TimeGoal <= DesiredTime AND "
							   + "Username = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		return stmt.executeQuery();
	}
	
	public int caliGoalCount(int type, User user) throws SQLException{
		String count = "SELECT count(*) AS Count "
				       + "FROM CalisthenicsGoal "
				       + "WHERE Username = ? "
				       + "AND CalisthenicsType = ?";
		PreparedStatement stmt = connection.prepareStatement(count);
		stmt.setString(1, user.getUsername());
		stmt.setInt(2, type);
		ResultSet set = stmt.executeQuery();
		return set.getInt("Count");
		
	}
	
	public ResultSet getCaliGoals(User user, int type) throws SQLException{
		String select = "SELECT * "
				     	 + "FROM CalisthenicsGoal "
				     	 + "WHERE Username = ? "
				     	 + "AND CalisthenicsType = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		stmt.setInt(2, type);
		return stmt.executeQuery();
	}
	
	public ResultSet completedCali(String pre, int id, User user ) throws SQLException{
		String select = "SELECT CalisDate "
							+ "FROM CalisthenicsHistory "
							+ "WHERE " + pre + "H >= (SELECT "+ pre +"Goal "
														+ "FROM CalisthenicsGoal "
														+ "WHERE CalisthenicsGId = ? "
														+ "LIMIT 1) "
							+ " AND Username = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setInt(1, id);
		stmt.setString(2, user.getUsername());
		return stmt.executeQuery();
	}
	
	public ResultSet caliCheck(String pre, User user) throws SQLException{
		String select = "SELECT ProgramName "
						 + "FROM CalisthenicsGoal NATURAL JOIN "
						 	   + "User NATURAL JOIN Programs "
						 	   + "NATURAL JOIN Calisthenics "
						 + "WHERE "+ pre+"Goal <= " + pre+" AND Username = ?";
		PreparedStatement stmt = connection.prepareStatement(select);
		stmt.setString(1, user.getUsername());
		return stmt.executeQuery();
	}


		
}



/* See if Completed Query
 * 
 * SELECT CalisDate 
    FROM CalisthenicsHistory
    WHERE PushupH = (SELECT PushupGoal 
                      FROM CalisthenicsGoal
                      WHERE ID = 'Aly.H'
                      LIMIT 1)
 */

/*
 * Select ProgramName
    From RunGoal Natural Join User Natural Join Programs Natural Join Runs
    Where DistanceGoal = DesiredDistance AND TimeGoal = DesiredTime AND username = username
 */













