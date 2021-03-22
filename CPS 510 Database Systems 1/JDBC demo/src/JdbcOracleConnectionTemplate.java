import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.*;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;

class JdbcOracleConnectionTemplate extends JFrame implements ActionListener{
	
	private String tableInsert1;
	private String tableInsert2; 
	private String tableInsert3;
	private String tableInsert4;
	private String tableInsert5;
	private String tableInsert6;
	private String tableInsert7;
	private String tableInsert8;
	private String stringResult = "";
	private JTextArea stringResultText;
	private JButton connectButton;
	private JButton makeButton;
	private JButton insertButton;
	private JButton dropQueryButton;
	private JButton genQueryButton;
	private JButton quitButton;
	private Connection con;
	
	private Statement sqlStatement;
	private ResultSet rs;
	
			
	private static final String createTable1 = "CREATE TABLE member(user_name VARCHAR(30) NOT NULL UNIQUE,user_password VARCHAR(30) NOT NULL UNIQUE, user_id NUMBER, user_type CHAR(15), CONSTRAINTS user_pk PRIMARY KEY(user_id))";
	
    private static final String createTable2 = "CREATE TABLE movie( movie_id NUMBER, title VARCHAR(50) NOT NULL UNIQUE, genre VARCHAR(20) NOT NULL, subtitle VARCHAR(20) NOT NULL, synopsis VARCHAR(120) NOT NULL, CONSTRAINTS movie_pk PRIMARY KEY(movie_id))";
	
	private static final String createTable3 = "CREATE TABLE customer(customer_id NUMBER, payment_info VARCHAR(30), CONSTRAINTS customer_fk FOREIGN KEY(customer_id) REFERENCES member(user_id))";
	
	private static final String createTable4 = "CREATE TABLE manager(manager_id NUMBER, manager_discount VARCHAR(20), CONSTRAINTS manager_fk FOREIGN KEY(manager_id) REFERENCES member(user_id))";
	
	private static final String createTable5 = "CREATE TABLE transaction(movie_id NUMBER, user_id NUMBER NOT NULL, transaction_id NUMBER NOT NULL, amount NUMBER NOT NULL, manager_discount VARCHAR(20), date_paid VARCHAR(30) NOT NULL, CONSTRAINTS transaction_pk PRIMARY KEY(transaction_id),  CONSTRAINTS users_fk FOREIGN KEY(user_id) REFERENCES member(user_id), CONSTRAINTS movie_fk FOREIGN KEY(movie_id) REFERENCES movie(movie_id))";
	
	private static final String createTable6 = "CREATE TABLE rating(rating_id NUMBER, movie_id NUMBER, user_id NUMBER NOT NULL, comments VARCHAR(100), rating_out_of_10 NUMBER, CONSTRAINTS rating_pk PRIMARY KEY(rating_id), CONSTRAINTS rating_user_fk FOREIGN KEY(user_id) REFERENCES member(user_id), CONSTRAINTS rating_movies_fk FOREIGN KEY(movie_id) REFERENCES movie(movie_id))";
	
	private static final String createTable7 = "CREATE TABLE purchase( purchase_id NUMBER, transaction_id NUMBER, CONSTRAINTS purchase_pk PRIMARY KEY(purchase_id), CONSTRAINTS p_transaction_fk FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id))";	
	
	private static final String createTable8 = "CREATE TABLE rental(rental_id NUMBER, rental_time VARCHAR(20), transaction_id NUMBER, CONSTRAINTS rental_pk PRIMARY KEY(rental_id), CONSTRAINTS r_transaction_fk FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id))";

	
public static void main(String args[]){
	
		new JdbcOracleConnectionTemplate();
}

public JdbcOracleConnectionTemplate() {
	try{
		 //connect button configuration	
		connectButton = new JButton("Connect");
		connectButton.setBackground(Color.RED);
    	connectButton.setFont(new Font("Arial Black", Font.BOLD, 25));
    	connectButton.setMnemonic(KeyEvent.VK_C);
    	
  
//create tables button
    	makeButton = new JButton("Create Tables");
    	makeButton.setBackground(Color.RED);
    	makeButton.setFont(new Font("Arial Black", Font.BOLD, 25));
    	makeButton.setMnemonic(KeyEvent.VK_C);
    	
//populate tables button    	
    	insertButton = new JButton("Populate Tables");
    	insertButton.setBackground(Color.RED);
    	insertButton.setFont(new Font("Arial Black", Font.BOLD, 25));
    	insertButton.setMnemonic(KeyEvent.VK_C);

//drop tables button   	
    	dropQueryButton = new JButton("Drop Tables");
    	dropQueryButton.setFont(new Font("Arial Black", Font.BOLD, 25));
        dropQueryButton.setBackground(Color.RED);
    	dropQueryButton.setMnemonic(KeyEvent.VK_C);
		
//Query button    	
    	genQueryButton = new JButton("Queries");
    	genQueryButton.setFont(new Font("Arial Black", Font.BOLD, 25));
        genQueryButton.setBackground(Color.RED);
    	genQueryButton.setMnemonic(KeyEvent.VK_C);
    	
//quit button configuration
    	quitButton = new JButton("Quit");
    	quitButton.setPreferredSize(new Dimension(5, 30));
    	quitButton.setBackground(Color.RED);
    	quitButton.setFont(new Font("Arial Black", Font.BOLD, 25));  
    	
 //output area   	
		stringResultText = new JTextArea(25, 60);
		stringResultText.setFont(new Font("Courier New", Font.PLAIN, 12));
		
// button panel		
		JPanel panelArea = new JPanel(new GridLayout(6, 1));
		panelArea.add(connectButton);
		panelArea.add(makeButton);
		panelArea.add(insertButton);
		panelArea.add(dropQueryButton);
		panelArea.add(genQueryButton);
		panelArea.add(quitButton);

		connectButton.addActionListener(this);
		makeButton.addActionListener(this);
		insertButton.addActionListener(this);
		dropQueryButton.addActionListener(this);
		genQueryButton.addActionListener(this);
		quitButton.addActionListener(this);

		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 45));
		JScrollPane scroll = new JScrollPane(stringResultText);
		
//Overall Background	
		panel.add(scroll);
		panel.add(panelArea);
        panel.setBackground(Color.BLACK);

//title
		setContentPane(panel);
		setSize(800, 700);
		setTitle("Movie Store");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
//member table insert 
		tableInsert1 = "INSERT ALL" +
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Artheek', 'koolkid123', 6731,'Customer')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Sameh',  'manager123',3732,'Manager')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Colin', 'hello1234', 2431,'Customer')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Ord','PDFCDF123',4523,'Manager')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Kevin', 'qwerty128', 9235,'Customer')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Manraj', 'shorty148', 6985,'Customer')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Harold','32323232',1000,'Manager')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Mark', 's987h', 2000,'Customer')" + 
							" INTO member (user_name, user_password, user_id, user_type) VALUES('Abel', 'send495', 3000,'Customer')" + 
							" SELECT * FROM DUAL" +
							" COMMIT"; 
//movie table insert
		tableInsert2 = "INSERT ALL" + 
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (1, 'Yugi-oh', 'Action', 'English', 'Using cards to beat opponents')" + 
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (2, 'Joker', 'Thriller', 'French', 'History of Batmans popular villain')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (3, 'Paper Towns', 'Romance', 'Swedish', 'Guy tries to go find his crush')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (4, 'Conjuring', 'Horror', 'English', 'A ghost haunts a family requiring special exorcist')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (5, 'IT', 'Horror', 'Spanish', 'Scary clown terrorizes kids in a neighborhood')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (6, 'Avengers: Endgame', 'Horror', 'English', 'Agroup of super heroes save the world')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (7, 'Tenet', 'Thriller', 'French', 'A secret agent embarks on a dangerous, time-bending mission to prevent the start of World War III.')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (8, 'Inception', 'Thriller', 'English', 'a professional thief who steals information by infiltrating the subconscious of his targets.')" +
							" INTO movie (movie_id, title, genre, subtitle, synopsis) VALUES (9, 'Onward', 'Family', 'English', 'Teenage elf brothers Ian and Barley embark on a magical quest to spend one more day with their late father')" +
							" SELECT * FROM DUAL" +
							" COMMIT"; 

//customer table insert
		tableInsert3 = "INSERT ALL" +
							" INTO customer (customer_id, payment_info) VALUES (6731,'CREDIT')" + 
							" INTO customer (customer_id, payment_info) VALUES (2431, 'DEBIT')" +
							" INTO customer (customer_id, payment_info) VALUES (9235, 'DEBIT')" +
							" INTO customer (customer_id, payment_info) VALUES (6985, 'PAYPAL')" +
							" INTO customer (customer_id, payment_info) VALUES (2000, 'DEBIT')" +
							" INTO customer (customer_id, payment_info) VALUES (3000, 'PAYPAL')" +
							" SELECT * FROM DUAL" +
							" COMMIT"; 
//manager table insert
		tableInsert4 = "INSERT ALL" +
							" INTO manager (manager_id, manager_discount) VALUES (2431, '20%')" + 
							" INTO manager (manager_id, manager_discount) VALUES (4523, '25%')" + 
							" INTO manager (manager_id, manager_discount) VALUES (1000, '10%')" + 
							" SELECT * FROM DUAL" +
							" COMMIT"; 
//transaction table insert
		tableInsert5 = "INSERT ALL" +
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (3,6731,1,7.50,'no discount','OCT.2nd, 2020')" + 
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (3,2431,2,11.25,'20%','OCT.4th, 2020')" + 
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (1,3732,3,10.58,'No discount','Sept.14th, 2020')" + 
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (2,6731,4,15.92,'No discount','AUG.30th, 2020')" + 
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (5,3732,5,9.54,'No discount','SEPT.25th, 2020')" + 
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (4,6985,6,12.31,'No discount','OCT.8th, 2020')" +
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (8,1000,7,11.25,'10%','OCT.18th, 2020')" + 
							" INTO transaction (movie_id, user_id, transaction_id, amount, manager_discount, date_paid) VALUES (9,4523,8,11.25,'25%','NOV.4th, 2020')" + 
							" SELECT * FROM DUAL" +
							" COMMIT"; 
//rating table insert
		tableInsert6 = "INSERT ALL" +
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (1, 2, 6731,'Really good movie!', 8.3)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (2, 2, 2431,'The acting was good', 7.5)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (3, 4, 6731,'It was pretty scary!', 6)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (4, 1, 3732,'AMAZING PLOT and action was great!', 9.8)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (5, 5, 3732,'It could have been scarier!', 5.8)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (6, 1, 2000,'very bad movie!', 1.2)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (7, 2, 3000,'very long movie, almost fell asleep!', 1.2)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (8, 8, 2000,'mediocre at best!', 6.0)" + 
							" INTO rating (rating_id, movie_id, user_id, comments, rating_out_of_10) VALUES (9, 4, 3000,'really scary movie!', 9.5)" + 
							" SELECT * FROM DUAL" +
							" COMMIT"; 

//purchase table insert
		tableInsert7 = "INSERT ALL" +
							" INTO purchase (purchase_id, transaction_id) VALUES (1,3)" + 
							" INTO purchase (purchase_id, transaction_id) VALUES (2,4)" + 
							" SELECT * FROM DUAL" +
							" COMMIT"; 
//rental table insert
		tableInsert8 = "INSERT All" +
							" INTO rental (rental_id, rental_time, transaction_id) VALUES (1,'14 days',2)" + 
							" INTO rental (rental_id, rental_time, transaction_id) VALUES (2,'1 week',1)" +
							" INTO rental (rental_id, rental_time, transaction_id) VALUES (3,'14 days',5)" +
							" INTO rental (rental_id, rental_time, transaction_id) VALUES (4,'10 days',6)" +
							" INTO rental (rental_id, rental_time, transaction_id) VALUES (5,'14 days',7)" +
							" INTO rental (rental_id, rental_time, transaction_id) VALUES (6,'1 week',8)" +
							" SELECT * FROM DUAL" +
							" COMMIT"; 

		

	
		}catch(Exception e){ System.out.println(e);}
}

public void actionPerformed(ActionEvent e){
	//upon clicking the connect button
	if(e.getSource() == connectButton)
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:system/10277041@localhost:1521:xe");
			
					sqlStatement=con.createStatement();
					stringResult+="You have connected to the database.\n\n";
					stringResultText.setText(stringResult);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
	}
	//upon clicking the create tables button

	if(e.getSource() == makeButton)
	{
		try {
			sqlStatement.executeUpdate(createTable1);		
			sqlStatement.executeUpdate(createTable2);
			sqlStatement.executeUpdate(createTable3);
			sqlStatement.executeUpdate(createTable4);
			sqlStatement.executeUpdate(createTable5);
			sqlStatement.executeUpdate(createTable6);
			sqlStatement.executeUpdate(createTable7);
			sqlStatement.executeUpdate(createTable8);
			System.out.println("Tables Created");
			stringResult+="Successfully created 8 tables.\n\n";
			stringResultText.setText(stringResult);
		} catch (SQLException e1) {
			e1.printStackTrace();
			stringResult+="Tables already exist.\n\n";
			stringResultText.setText(stringResult);
		} catch(Exception e2){ System.out.println(e2);}
		
	}
	//upon clicking the populate tables button

	if(e.getSource() == insertButton)
	{
		try {
			sqlStatement.executeUpdate(tableInsert1);
			sqlStatement.executeUpdate(tableInsert2);
			sqlStatement.executeUpdate(tableInsert3);
			sqlStatement.executeUpdate(tableInsert4);
			sqlStatement.executeUpdate(tableInsert5);
			sqlStatement.executeUpdate(tableInsert6);
			sqlStatement.executeUpdate(tableInsert7);
			sqlStatement.executeUpdate(tableInsert8);
			System.out.println("Tables Populated");
			stringResult+="Tables populated successfully.\n\n";
			stringResultText.setText(stringResult);
		} catch (SQLException e1) {
			System.out.println(e1.getErrorCode());
			if (e1.getErrorCode() == 1) {
				stringResult+="Tables are already populated.\n\n";
				stringResultText.setText(stringResult);
			}
			if (e1.getErrorCode() == 942) {
				stringResult+="Tables do not exist.\n\n";
				stringResultText.setText(stringResult);
			}
			e1.printStackTrace();
		}
	}

//upon clicking the drop tables button
	if(e.getSource() == dropQueryButton)
	{
		try {
			sqlStatement.executeUpdate("DROP TABLE rental");
			sqlStatement.executeUpdate("DROP TABLE purchase");
			sqlStatement.executeUpdate("DROP TABLE rating");
			sqlStatement.executeUpdate("DROP TABLE transaction");
			sqlStatement.executeUpdate("DROP TABLE manager");
			sqlStatement.executeUpdate("DROP TABLE customer");
			sqlStatement.executeUpdate("DROP TABLE movie");
			sqlStatement.executeUpdate("DROP TABLE member");

			System.out.println("Tables Dropped");
			stringResult+="Tables dropped successfully.\n\n";
			stringResultText.setText(stringResult);
		} catch (SQLException e1) {
			stringResult+="Tables do not exist.\n\n";
			stringResultText.setText(stringResult);
			e1.printStackTrace();
		}

		
	}
//upon clicking the Queries button
	if(e.getSource() == genQueryButton)
	{
		try {
			
			rs = sqlStatement.executeQuery("SELECT rental.rental_id, transaction.date_paid, rental.rental_time from rental, transaction WHERE rental.transaction_id = transaction.transaction_id and rental.rental_id >=3 ORDER BY transaction.date_paid DESC");
			ResultSetMetaData rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-15s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2), rsmd.getColumnName(3)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-15s %-15s", rs.getString(1), rs.getString(2), rs.getString(3)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("SELECT member.user_name, member.user_type, transaction.amount, transaction.date_paid from member, transaction WHERE member.user_id = transaction.user_id and amount < 13 ORDER BY amount ASC");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-15s %-15s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2), rsmd.getColumnName(3), rsmd.getColumnName(4)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-15s %-15s %-15s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("SELECT member.user_name, rating.comments, rating.rating_out_of_10, movie.title "
					+ "from member, movie, rating "
					+ "WHERE member.user_id = rating.user_id "
					+ "and rating.movie_id = movie.movie_id "
					+ "and movie.subtitle = 'English' "
					+ "ORDER BY movie.title ASC");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-40s %-20s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2), rsmd.getColumnName(3), rsmd.getColumnName(4)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-40s %-20s %-15s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("SELECT 'The count for transaction made is', COUNT(transaction.amount) "
					+ "FROM transaction");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-40s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-40s %-15s", rs.getString(1), rs.getString(2)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("(SELECT movie.title, movie.genre FROM movie) "
					+ "MINUS "
					+ "(SELECT movie.title, movie.genre FROM movie "
					+ "where subtitle = 'English')");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-15s", rs.getString(1), rs.getString(2)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("(SELECT * from transaction "
					+ "where transaction.amount < 10) "
					+ "UNION "
					+ "SELECT * FROM transaction "
					+ "WHERE transaction.manager_discount = 'No discount'");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-15s %-15s %-15s %-20s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2), rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5), rsmd.getColumnName(6)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-15s %-15s %-15s %-20s %-15s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("SELECT transaction_id, date_paid, amount "
					+ "FROM transaction "
					+ "WHERE EXISTS "
					+ "(SELECT movie_id "
					+ "FROM movie "
					+ "where movie.movie_id = transaction.movie_id "
					+ "and movie.subtitle = 'English')");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-15s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2), rsmd.getColumnName(3)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-15s %-15s", rs.getString(1), rs.getString(2), rs.getString(3)) + "\n";
				stringResultText.setText(stringResult);
				}
			
			rs = sqlStatement.executeQuery("SELECT transaction_id, AVG(amount) from transaction "
					+ "GROUP BY transaction_id  "
					+ "HAVING AVG(transaction.amount) > (SELECT AVG(amount) FROM transaction)");
			rsmd = rs.getMetaData();
			stringResult+= "\n\n" + String.format("%-20s %-15s", rsmd.getColumnName(1), rsmd.getColumnName(2)) + "\n";
			stringResultText.setText(stringResult);
			while(rs.next()) {
				stringResult+=String.format("%-20s %-15s", rs.getString(1), rs.getString(2)) + "\n";
				stringResultText.setText(stringResult);
				}
			stringResult+="\n";
			stringResultText.setText(stringResult);
			
		} catch (SQLException e1) {
			stringResult+="Tables do not exist.\n\n";
			stringResultText.setText(stringResult);
			e1.printStackTrace();
		}
	}
	
//upon clicking the Quit button
	if(e.getSource() == quitButton)
	{ 
		int option = JOptionPane.showConfirmDialog(null, "Would you like to Quit?", "Exit", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION)
		{
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}	
	
}

}

