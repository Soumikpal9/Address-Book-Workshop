package AddressBookWorkshop;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDBService {
	private static AddressBookDBService addBookDB;
	private PreparedStatement addBookDataStatement;
	
	public AddressBookDBService() {}
	
	public static AddressBookDBService getInstance() {
		if(addBookDB == null) {
			addBookDB = new AddressBookDBService();
		}
		return addBookDB;
	}
	
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
		String userName = "root";
		String password = "Resurrection@5";
		Connection connection;
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		return connection;
	}
	
	public List<ContactDetails> readData() {
		String sql = "SELECT * FROM addressbook;";
		List<ContactDetails> addBookList = new ArrayList<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			addBookList = this.getAddressBookData(result);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addBookList;
	}
	
	public List<ContactDetails> getAddressBookData(String firstName) {
		List<ContactDetails> addBookList = null;
		if(this.addBookDataStatement == null) {
			this.prepareStatementForEmployeeData();
		}
		try {
			addBookDataStatement.setString(1, firstName);
			ResultSet result = addBookDataStatement.executeQuery();
			addBookList = this.getAddressBookData(result);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addBookList;
	}

	private List<ContactDetails> getAddressBookData(ResultSet result) {
		List<ContactDetails> addressBookList = new ArrayList<>();
		try {
			while(result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("address");
				String city = result.getString("city");
				String state = result.getString("state");
				String zipcode = result.getString("zipcode");
				String phone = result.getString("phone");
				String email = result.getString("email");
				addressBookList.add(new ContactDetails(firstName, lastName, address, city, state, zipcode, phone, email));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}
	
	private void prepareStatementForEmployeeData() {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM addressbook WHERE name = ?";
			addBookDataStatement = connection.prepareStatement(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int updateData(String firstName, String city) {
		return this.updateAddressBookDataUsingStatement(firstName, city);
	}

	private int updateAddressBookDataUsingStatement(String firstName, String city) {
		String sql = String.format("update addressbook set city = %s where first_name = %s", city, firstName);
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<ContactDetails> getAddressBookForDateRange(LocalDate startDate, LocalDate endDate) {
		String sql = String.format("SELECT * FROM addressbook WHERE start BETWEEN '%s' AND '%s'", Date.valueOf(startDate), Date.valueOf(endDate));
		return this.getAddressBookDataUSingDB(sql);
	}

	private List<ContactDetails> getAddressBookDataUSingDB(String sql) {
		List<ContactDetails> addBookList = new ArrayList<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			addBookList = this.getAddressBookData(result);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addBookList;
	}
	
	public Map<String, Integer> getCountByCity() {
		String sql = "SELECT city, COUNT(city) AS count_city FROM addressbook GROUP BY city";
		Map<String, Integer> cityToContactsMap = new HashMap<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				String city = result.getString("city");
				int count = result.getInt("count_city");
				cityToContactsMap.put(city, count);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return cityToContactsMap;
	}

	public Map<String, Integer> getCountByState() {
		String sql = "SELECT state, COUNT(state) AS count_state FROM addressbook GROUP BY state";
		Map<String, Integer> stateToContactsMap = new HashMap<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				String state = result.getString("state");
				int count = result.getInt("count_state");
				stateToContactsMap.put(state, count);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return stateToContactsMap;
	}
	
	public ContactDetails addContactToBook(String firstName, String lastName, String address, String city, String state, String zipcode, String phone, String email) {
		ContactDetails addBookData = null;
		String sql = String.format("INSERT INTO addressbook (first_name, last_name, address, city, state, zipcode, phone, email) VALUES ('%s', %s, '%s', '%s', '%s', %s, '%s', '%s')", firstName, lastName, address, city, state, zipcode, phone, email);
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet result = statement.getGeneratedKeys();
				if(result.next())	firstName = result.getString("first_name");
			}
			addBookData = new ContactDetails(firstName, lastName, address, city, state, zipcode, phone, email);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addBookData;
	}
}
