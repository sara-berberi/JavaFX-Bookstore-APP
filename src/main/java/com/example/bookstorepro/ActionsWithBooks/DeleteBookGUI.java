package com.example.bookstorepro.ActionsWithBooks;
import com.example.bookstorepro.AdministratorFiles.AdministratorGUI;
import com.example.bookstorepro.Database.DB;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteBookGUI extends Application {
    private static TextField bookNameField;
    private static TextField ISBNField;
    public static String label = "";
    static GridPane grid = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        deleteBookInterface(grid);

        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void deleteBookInterface(GridPane grid){
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label bookNameLabel = new Label("Book Name:");
        grid.add(bookNameLabel, 0, 0);

        bookNameField = new TextField();
        bookNameField.setId("bookNameField");
        grid.add(bookNameField, 1, 0);

        Label ISBNLabel = new Label("ISBN:");
        grid.add(ISBNLabel, 0, 2);

        ISBNField = new TextField();
        ISBNField.setId("ISBNField");
        grid.add(ISBNField, 1, 2);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 0,4);
        Button addButton = new Button("Delete Book");
        addButton.setId("addButton");
        addButton.setOnAction(e -> {
                    if(deleteBook(bookNameField.getText(), ISBNField.getText())){
                        actionTarget.setText(":( Book deleted successfully");
                        label = ":( Book deleted successfully";
                    }
                    else{
                        actionTarget.setText("Failed to delete this book.");
                        label = "Failed to delete this book.";
                    }
                }
        );
        grid.add(addButton, 1, 9);
    }

    public static boolean deleteBook(String bookName, String ISBN) {
        if(bookName.isEmpty() || ISBN.isEmpty()){
            return false;
        }
        int status;

        try (Connection con = DB.getConnection()) {

            PreparedStatement statement = con.prepareStatement( "DELETE FROM booklist WHERE bookname = '"+bookName+"' and ISBN='"+ISBN+"';");
            System.out.println(statement);
            status = statement.executeUpdate();
            System.out.println(status);

            if(status >0) {
                System.out.println(":( Book deleted successfully");
                label = ":( Book deleted successfully";
                if(AdministratorGUI.tableview != null){
                    AdministratorGUI.tableview.refresh();
                    return true;
                }
                return true;
            }
            else {
                System.out.println("Failed to delete this book.");
                label = "Failed to delete this book.";
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public
    static void main(String[] args) {
        launch(args);
    }
}