package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *  An applications that reads the input and tell you all the words and the counts
 *
 * @auther altamush
 * @version 1.1
 * @see Map
 * @button
 * @label
 */

public class Main extends Application {
    public static HashMap<String, Integer> words = new HashMap<>();
    Button button;
    Label label;

    /**
     * The Button lets the program grow through
     *
     * @param primaryStage
     * @throws Exception
     */


    @Override
    public void start(Stage primaryStage) throws Exception{

        /**
         * when button is pressed, the action event goes through, the wordOccurrences
         * method goes through
         *
         * @method
         *
         */

        button = new Button();

        label = new Label("By clicking the start button, it will read the input and tell you all the words and their " +
                "word count");
        button.setText("START ");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    label.setText(wordOccurrences());
                    DBconnecter dbconnector = new DBconnecter();
                    dbconnector.post(listAmount());
                    dbconnector.selectAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });
        /**
         * Vbox was used for gui and added the button and label
         *
         * @Vbox
         */

        VBox vbox = new VBox();
        vbox.getChildren().add(button);
        vbox.getChildren().add(label);

        Scene scene = new Scene(vbox, 800,800);




        primaryStage.setTitle("Word Occurrences ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** WordOccurrences method reads input, takes out all the exceptions that is not a
     * word and stores all the values in a HaspMap
     *
     * @throws IOException exception is thrown
     * @see HashMap
     */

    public static String wordOccurrences() throws Exception {
        BufferedReader inputs = new BufferedReader(new FileReader("input.txt"));
        //HashMap<String, Integer> words = new HashMap<>();
        ArrayList<String> order = new ArrayList<>();
        String line = "";
        int counter = 1;
        while((line = inputs.readLine())!= null){
//            line = line.replaceAll("(\\.|,|;|:|\\?|\\!|—)", "");
            line = line.replaceAll("([^A-Za-z ]+)", "");
            line = line.replaceAll("( )+", " ");
            line = line.toLowerCase(Locale.ROOT);
            String[] lineWords = line.split(" ");
            /**
             * keeps adding the words when it comes more than once and apples it as a pair
             * to see the how many counts.
             *
             * @<code></code>
             * @see ArrayList
             */
            for(String word: lineWords){
                if(words.containsKey(word)){
                    words.put(word, words.get(word) + 1);

                }
                else
                    words.put(word, 1);

            }
        }
        List<Map.Entry<String, Integer> >
                list = new LinkedList<>(words.entrySet());
        /**
         * Apples it into a list and shows each word from greatest to least
         *
         * @implNote
         * @see List
         */

        // Sort the list
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        String message = "";
        int count = 0;
        for (Map.Entry<String, Integer> map : list
        ) {
            message += counter+" .-"+ map.getKey()+"=" +map.getValue();
            count++;
            if(count == 1){
                message += "\n";
                count = 0;
            }
            counter++;


        }
        System.out.println(list);
        /**
         * returns all the words in the array
         *
         *  @return returns the words list in an array
         * @see Collections
         */

        return message;


    }
    public static HashMap<String, Integer> listAmount() {
        return words;
    }

    /**
     * Getter for the wordOccurrences method
     *
     * @param args
     * @throws IOException
     */


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
