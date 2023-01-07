package com.example.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class loginPageController {
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    Label username;
    @FXML
    Label usertype;
    @FXML
    TextField user;
    @FXML
    TextField userty;


    @FXML
    public void login(MouseEvent e) throws SQLException, IOException {
        if(user.getOpacity()==1){
            user.setOpacity(0);
            usertype.setOpacity(0);
            userty.setOpacity(0);
            username.setOpacity(0);
        }



        String query = String.format("Select * from user where emailId='%s' AND pass= '%s'", email.getText(), password.getText());
        ResultSet res = Main.connection.execute(query);

        if (res.next()) {
            Main.emailId = res.getString("emailId");

            String userType = res.getString("usertype");
            if (userType.equals("Seller")) {
                AnchorPane sellerPage = FXMLLoader.load((getClass().getResource("sellerPage.fxml")));
                Main.root.getChildren().add(sellerPage);

            } else {
                System.out.println("We are logged in as Buyer");
                productPage productPage = new productPage();

                Header header = new Header();

                AnchorPane productPane = new AnchorPane();
                productPane.getChildren().add(productPage.products());
                productPane.setLayoutX(125);
                productPane.setLayoutY(50);
                Main.root.getChildren().clear();
                Main.root.getChildren().addAll(header.root, productPane);
            }
            System.out.println("the user is present in user table");
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Login ");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.setContentText("Login Failed, please check username or password or Register user");
            dialog.showAndWait();
            if(user.getOpacity()==0){
                user.setOpacity(1);
                usertype.setOpacity(1);
                userty.setOpacity(1);
                username.setOpacity(1);

            }
            else{
                user.setOpacity(0);
                usertype.setOpacity(0);
                userty.setOpacity(0);
                username.setOpacity(0);
            }
        }
    }

    public void register(MouseEvent e) throws SQLException, IOException {

        String query = String.format("Select * from user ");
        ResultSet res = Main.connection.execute(query);

      // res.last();


        //    String query = String.format("Insert Into user values('%s','%s','%s','%s')", email.getText,user.getText(),password.getTest(),usertype.getText());


        int response3 = Main.connection.executeUpdate(query);
        if (response3 > 0) {

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Resgistration ");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.setContentText("Registration Complete..Please login now");
            dialog.showAndWait();
        }
    }
}

