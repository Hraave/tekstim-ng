import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    public static Scanner scanner = new Scanner(System.in);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Text Adventure");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
        //Menu();

    }

    private static void Menu() {

        System.out.println("1. Start Game\n2. Exit");
        int input = scanner.nextInt();

        if (input == 1) {

            Game game = new Game();
            game.Start();

        } else if (input == 2) {

            System.exit(0);

        }

    }

}
