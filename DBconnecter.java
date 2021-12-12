package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBconnecter {
    /**
     * Connecting the wordOccurrences method to the Database. When played takes all the words
     * and puts in into the wordOccurences table and prints them out in the program
     *
     * @return
     * @throws Exception
     * @DriverManager
     * @ArrayList
     * @HashMap
     * @Connection
     */

    public ArrayList<String> selectAll() throws Exception{
        try{
            Connection con = getConnection();
            PreparedStatement select = con.prepareStatement("SELECT * from word");
            ResultSet result = select.executeQuery();

            ArrayList<String> array = new ArrayList<>();
            while(result.next()){
                System.out.println(result.getString("word"));

                array.add(result.getString("word"));
            }
            return array;

        } catch(Exception e){
            System.out.println(e);
        } return null;

    }

    /**
     * Method that lets the user manually enter values into the database.
     *
     * @throws Exception
     */
    public static void insert() throws Exception{
        final String var1 = "banana";
        final String var2 = "Expo";
        final String var3 = "Altamush";
        final String var4 = "whiteboard";
        try{ Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO word(word) VALUES ('"+ var1 +"')");
            posted.executeUpdate();

        }catch (Exception e) {
            System.out.println(e);

        }
    }

    public static void createTable() throws Exception{
        try{Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS listOfAllWords(words varchar(255))");
            create.executeUpdate();

        }catch(Exception e){
            System.out.println(e);
        }finally {
            System.out.println("Inserted completed");
        }
    }

    /**
     * Inserting all the values from the input into the database
     *
     * @param list
     * @throws Exception
     */
    public static void post(HashMap<String, Integer> list) throws Exception{


        for(Map.Entry<String, Integer> set: list.entrySet()) {


            try {
                Connection con = getConnection();
                PreparedStatement posted = con.prepareStatement("INSERT INTO word(word) VALUES ('"+ set.getKey() +"')");


                posted.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);

            } finally {
                System.out.println("Inserted completed");
            }
        }

    }

    /**
     * Connecting the database to the Main method.
     *
     * @return
     * @throws Exception
     */

    public static Connection getConnection() throws Exception{
        try{

            String url = "jdbc:mysql://localhost:3306/wordoccurrences?serverTimezone=UTC";
            String USER = "root";
            String PASS = "4713065Ab!";


            Connection conn = DriverManager.getConnection(url,USER,PASS);
            System.out.println("Connected");
            return conn;

        }catch(Exception e){
            System.out.println(e);
        }
        return null;

    }
}
