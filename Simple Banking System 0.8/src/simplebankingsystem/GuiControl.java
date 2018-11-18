package simplebankingsystem;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @web http://javafxportal.blogspot.com/
 */
public class GuiControl extends Application {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
        // create array objects
		SimpleBankingSystem sbc = new SimpleBankingSystem();
		//sbc.createAccounts();

		// GUI Objects
		Group root = new Group();
		final Scene scene = new Scene(root, 450, 400, Color.WHITE);
		MenuBar menuBar = MenuBarAndMenus.buildMenuBarWithMenus(primaryStage.widthProperty());
		BorderPane mainPane = new BorderPane();  // the main pain :)
		HBox topMenu = new HBox();
		TabPane tabPane = new TabPane();
		Button chooseAccount = new Button("Choose Account");
		Button create = new Button("Create");
		Button statistics = new Button("Statistics");
        Tab welcomeTab = new Tab();
        StackPane welcomeTab_stack = new StackPane();
        
        // GUI Object Properties
        primaryStage.getIcons().add(new Image("/simplebankingsystem/images/Banks.png"));
        primaryStage.setTitle("Simple Banking System");
		topMenu.setAlignment(Pos.BOTTOM_CENTER);  // aligns nodes to center in HBox
		topMenu.setSpacing(30);  // puts spacing between buttons
		topMenu.setPrefHeight(50);  // forces HBox to be a certain height	
		welcomeTab.setText("Welcome");
		welcomeTab_stack.setAlignment(Pos.CENTER);
		welcomeTab.setContent(welcomeTab_stack);		
		mainPane.setCenter(tabPane);
		mainPane.setTop(topMenu);
		mainPane.prefHeightProperty().bind(scene.heightProperty());
		mainPane.prefWidthProperty().bind(scene.widthProperty());
		welcomeTab.setGraphic(GuiControl.buildImage("/simplebankingsystem/images/Welcome.png"));
		
		topMenu.getChildren().addAll(chooseAccount,create,statistics);
		tabPane.getTabs().add(welcomeTab);
		welcomeTab_stack.getChildren().add(new Label("Welcome to Simple Banking System"));
		
        // Event Handlers 
		chooseAccount.setOnAction(new EventHandler<ActionEvent>() {   /// event on button
			@Override   
			public void handle(ActionEvent e) {
				AccountViewTab.accountSelectMenu(sbc, tabPane);
			}  // inner class close
		});  /// choose account button handler close

		////////////  This Button handles the Create Account Tab
		create.setOnAction(new EventHandler<ActionEvent>() {    
			@Override 
			public void handle(ActionEvent e) {
				Tab createTab = CreateAccountTab.createAccountTab(sbc, create, tabPane);
				tabPane.getTabs().add(createTab);
				tabPane.getSelectionModel().select(createTab);
			}
		});

		//////////// This Button handles the Statistics Tab
		statistics.setOnAction(new EventHandler<ActionEvent>() { 
			@Override 
			public void handle(ActionEvent e) {
				Tab statisticsTab = StatisticsViewTab.viewStatistics();
				tabPane.getTabs().add(statisticsTab);
				tabPane.getSelectionModel().select(statisticsTab);
				statisticsTab.setText("Bank Statistics");
			}
		});

		root.getChildren().addAll(mainPane, menuBar);
		primaryStage.setScene(scene);
		primaryStage.show();
	}  
	
	// packing images so they can be used as icons
    public static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch);
        ImageView imageView = new ImageView();
        //You can set width and height
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }
}  // class end
