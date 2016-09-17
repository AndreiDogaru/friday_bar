package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
import java.io.*;
import java.util.*;

public class Main extends Application {

    Stage window;
    Scene scene1, scene2;
    TableView<Drink> table;
    ObservableList<Drink> drinks;
    TextField nameTf, priceTf, qtyTf, volTf, alcPerTf, qtyTf2;
    Button add, delete, sell, sellDrinks, cancel;
    Label label1, label2, label3;
    ChoiceBox<String> choiceBox;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        drinks = FXCollections.observableArrayList();
        generateDrinks();

        /*
                            MENU BAR
        */
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(exitItem);
        menuBar.getMenus().add(fileMenu);

        exitItem.setOnAction(e -> exitProgram());

        MenuBar menuBar2 = new MenuBar();
        Menu fileMenu2 = new Menu("File");
        MenuItem exitItem2 = new MenuItem("Exit");
        fileMenu2.getItems().addAll(exitItem2);
        menuBar2.getMenus().add(fileMenu2);

        exitItem2.setOnAction(e -> exitProgram());

        /*
            -------------------SCENE 1-------------------
         */
        BorderPane root = new BorderPane();
        scene1 = new Scene(root, 830, 400, Color.TRANSPARENT);

        table = new TableView<>();
        table.itemsProperty().setValue(drinks);
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Drink, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Drink, Number> priceColumn = new TableColumn<>("Price");
        TableColumn<Drink, Number> qtyColumn = new TableColumn<>("Quantity");
        TableColumn<Drink, Number> volColumn = new TableColumn<>("Volume(in ml)");
        TableColumn<Drink, Number> alcPerColumn = new TableColumn<>("Alcohol percentage");
        table.getColumns().addAll(nameColumn,priceColumn,qtyColumn,volColumn,alcPerColumn);

        //Attach Action Listeners
        nameColumn.setCellValueFactory(e -> e.getValue().nameProperty());
        priceColumn.setCellValueFactory(e -> e.getValue().priceProperty());
        qtyColumn.setCellValueFactory(e -> e.getValue().qtyProperty());
        volColumn.setCellValueFactory(e -> e.getValue().volProperty());
        alcPerColumn.setCellValueFactory(e -> e.getValue().alcPerProperty());

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        qtyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        volColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        alcPerColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Drink, String> event)->{
            ((Drink)event.getTableView().getItems().get(event.getTablePosition().getRow())).setName
                    (event.getNewValue());
            updateFile();
        });

        priceColumn.setOnEditCommit((TableColumn.CellEditEvent<Drink, Number> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrice
                    (((Long)event.getNewValue()));
            updateFile();
        });

        qtyColumn.setOnEditCommit((TableColumn.CellEditEvent<Drink, Number> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setQty
                    (((Long)event.getNewValue()));
            updateFile();
        });

        volColumn.setOnEditCommit((TableColumn.CellEditEvent<Drink, Number> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setVol
                    (((Long)event.getNewValue()));
            updateFile();
        });

        alcPerColumn.setOnEditCommit((TableColumn.CellEditEvent<Drink, Number> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAlcPer
                    (((Long)event.getNewValue()));
            updateFile();
        });

        nameTf = new TextField();
        nameTf.setPromptText("Name");
        nameTf.setMaxWidth(120);
        priceTf = new TextField();
        priceTf.setPromptText("Price");
        priceTf.setMaxWidth(120);
        qtyTf = new TextField();
        qtyTf.setPromptText("Quantity");
        qtyTf.setMaxWidth(120);
        volTf = new TextField();
        volTf.setPromptText("Volume (in ml)");
        volTf.setMaxWidth(120);
        alcPerTf = new TextField();
        alcPerTf.setPromptText("Alcohol percentage");
        alcPerTf.setMaxWidth(120);
        add = new Button("Add");
        delete = new Button("Delete");
        sell = new Button("Sell");
        HBox hBox1 = new HBox(10,nameTf,priceTf,qtyTf,volTf,alcPerTf,add,delete,sell);

        add.setOnAction(e -> addDrink());
        delete.setOnAction(e -> deleteDrink());
        sell.setOnAction(e -> sell());

        root.setTop(menuBar);
        root.setCenter(table);
        root.setBottom(hBox1);

        /*
            -------------------SCENE 2-------------------
         */
        BorderPane root2 = new BorderPane();
        scene2 = new Scene(root2, 400, 400, Color.TRANSPARENT);

        choiceBox = new ChoiceBox<>();
        choiceBox.getSelectionModel().select("Corona");
        for (Drink drink : drinks)
            choiceBox.getItems().add(drink.getName());

        qtyTf2 = new TextField("0");
        qtyTf2.setPromptText("Quantity");
        qtyTf2.setMaxWidth(120);
        label1 = new Label();
        label1.setText("Price ");
        label2 = new Label();

        label3 = new Label();
        label3.setText(" dkk");

        cancel = new Button("Cancel");
        sellDrinks = new Button("Sell drinks");
        cancel.setOnAction(e -> cancel());
        sellDrinks.setOnAction(e -> sellDrinks());

        HBox hBox2 = new HBox(10,choiceBox,qtyTf2);
        HBox hBox3 = new HBox(10,label1,label2,label3);
        HBox hBox4 = new HBox(20,cancel,sellDrinks);

        Region reg1 = new Region();
        reg1.setPrefSize(50,0);
        Region reg2 = new Region();
        reg2.setPrefSize(0,30);

        VBox vBox = new VBox(40,reg2,hBox2,hBox3,hBox4);

        root2.setTop(menuBar2);
        root2.setLeft(reg1);
        root2.setCenter(vBox);
        scene1.getStylesheets().add
                (Main.class.getResource("hotDog.css").toExternalForm());
        scene2.getStylesheets().add
                (Main.class.getResource("hotDog.css").toExternalForm());
        window.setTitle("Friday Bar Drinks");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene1);
        window.show();
    }

    public void generateDrinks()throws Exception{
        Scanner fin = new Scanner(new File("drinks"));
        String name;
        double price, alcPer;
        int qty,vol;
        while(fin.hasNext()){
            name = fin.next();
            price = fin.nextDouble();
            qty = fin.nextInt();
            vol = fin.nextInt();
            alcPer = fin.nextDouble();

            Drink drink = new Drink(name,price,qty,vol,alcPer);
            drinks.add(drink);
        }
    }

    public void updateFile(){
        try {
            PrintStream output = new PrintStream(new File("drinks"));

            for (Drink drink : drinks)
                output.println(drink.getName() + " " + drink.getPrice() + " " +
                               drink.getQty() + " " + drink.getVol() + " " + drink.getAlcPer());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addDrink(){
        String name = nameTf.getText();
        double price = Double.parseDouble(priceTf.getText());
        int qty = Integer.parseInt(qtyTf.getText());
        int vol = Integer.parseInt(volTf.getText());
        double alcPer = Double.parseDouble(alcPerTf.getText());

        Drink drink = new Drink(name,price,qty,vol,alcPer);
        drinks.add(drink);
        choiceBox.getItems().add(name);

        nameTf.clear();
        priceTf.clear();
        qtyTf.clear();
        volTf.clear();
        alcPerTf.clear();

        // Change also the file
        updateFile();
    }

    public void deleteDrink(){
        drinks.removeAll(table.getSelectionModel().getSelectedItems());

        // Change also the file
        updateFile();
    }

    public void sell(){
        window.setScene(scene2);
    }

    public void cancel(){
        window.setScene(scene1);
    }

    public void sellDrinks(){
        int index = 0;
        for (int i = 0; i < drinks.size(); i++) {
            if (choiceBox.getSelectionModel().getSelectedItem().equals(drinks.get(i).getName())) {
                index = i;
                break;
            }
        }
        int qty = Integer.parseInt(String.valueOf(qtyTf2.textProperty().getValue()));
        double price = Double.parseDouble(String.valueOf(drinks.get(index).priceProperty().getValue()));

        if(qty <= drinks.get(index).getQty()) {
            drinks.get(index).setQty(drinks.get(index).getQty() - qty);
            double totalPrice = qty * price;
            label2.setText(String.valueOf(totalPrice));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(String.valueOf(qtyTf2.textProperty().getValue()) + " bottles of " + drinks.get(index).getName() +
                    " has been sold successfully. \nTotal price: " + totalPrice+" dkk");
            alert.show();
            alert.setOnHiding(e -> window.setScene(scene1));
            updateFile();
        }else{
            // The amount requested is bigger than our stock qty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The amount requested is bigger than our stock qty. Please choose again.");
            alert.show();
        }
    }

    private void exitProgram(){
        window.close();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
