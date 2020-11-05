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
import java.util.List;

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
}
