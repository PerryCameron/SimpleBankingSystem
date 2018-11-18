package simplebankingsystem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class CreateAccountTab {

	protected static int accountNumber;
	protected static String accountTypeSelected;
    protected static int customerID;
    protected static String newBalance = "0.0";
    protected static int customerObjectSelect = 0;
    protected static boolean accountSelected = false;
    protected static boolean customerSelected = false;
    protected static boolean balanceSelected = false;
    
	static Tab createAccountTab(SimpleBankingSystem sbc, Button create, TabPane tabPane) {
		newBalance = "0.0";
		accountNumber = SimpleBankingSystem.getNewAccountNumber();
		Tab createTab = new Tab();
		GridPane agrid = new GridPane();
		Button selectUser = new Button("Select");
		TextField firstDepositTextField = new TextField();
		VBox customerEditVBox = new VBox();
		VBox accountDetailsVBox = new VBox();
		VBox accountEditNewVBox = new VBox();
		VBox customerSelectedVBox = new VBox();
		HBox textAreaAndButtonHBox = new HBox();
		Label accountLabel = new Label("Account number: " + accountNumber);
		Label accountBalance = new Label("Balance: $0.00");
		Label accountType = new Label("Account Type: Null");
		Label setInitialBalance = new Label("Initial Balance: ");
		RadioButton rb3 = new RadioButton("New Customer");
		RadioButton rb4 = new RadioButton("Existing Customer");
		ListView<String> accountTypesListView = new ListView<>();
		Line line = new Line();
		final ToggleGroup groupC = new ToggleGroup();
		
		createTab.setGraphic(GuiControl.buildImage("/simplebankingsystem/images/Create.png"));


		// createTab.setGraphic(new ImageView(icon));
		rb3.setToggleGroup(groupC);
		rb3.setSelected(true);
		rb4.setToggleGroup(groupC);
		line.setStartX(220); 
		line.setStartY(110); 
		line.setEndX(0); 
		line.setEndY(110);
		line.setStyle("-fx-stroke-line-cap: round;" + "-fx-stroke-width: 2;" + "-fx-stroke: deepskyblue;");

		firstDepositTextField.setPrefWidth(70);

		accountTypesListView.getItems().addAll("Regular","Gold","Checking");

		textAreaAndButtonHBox.getChildren().addAll(setInitialBalance,firstDepositTextField);
		accountDetailsVBox.getChildren().addAll(accountLabel, accountBalance, accountType);
		accountEditNewVBox.getChildren().addAll(textAreaAndButtonHBox,line, accountTypesListView); ////this one				
		customerEditVBox.getChildren().addAll(rb3,rb4,selectUser);

		customerSelectedVBox.setAlignment(Pos.CENTER);  // aligns nodes to center in HBox
		customerSelectedVBox.setSpacing(10);
		customerEditVBox.setSpacing(5);
		textAreaAndButtonHBox.setSpacing(10);
		accountEditNewVBox.setSpacing(10);

		customerSelectedVBox.setAlignment(Pos.BOTTOM_CENTER);
		textAreaAndButtonHBox.setAlignment(Pos.CENTER);
		textAreaAndButtonHBox.setStyle("-fx-padding: 2;");

		customerSelectedVBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");

		accountDetailsVBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");

		customerEditVBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");

		accountEditNewVBox.setStyle("-fx-padding: 5;");

		createTab.setContent(agrid);

		agrid.setAlignment(Pos.TOP_LEFT);
		agrid.setPadding(new Insets(10,10,10,10));
		agrid.setVgap(2);
		agrid.setHgap(2);
		agrid.setGridLinesVisible(false);  //////////////////// Grid lines for testing /////////////////
		agrid.add(accountDetailsVBox,0,0);
		agrid.add(accountEditNewVBox,0,1);
		agrid.add(customerEditVBox,1,0);
		agrid.add(customerSelectedVBox, 1, 1);
		// CustomTab tab = new CustomTab();
		createTab.setOnClosed(new EventHandler<Event>() {  // enables create button if tab is closed
			@Override
			public void handle(Event t) {
				create.setDisable(false);
			}
		});

		accountTypesListView.getSelectionModel().selectedItemProperty().addListener(  
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						accountType.setText("Account Type: " + new_val);
						accountTypeSelected = new_val;
						System.out.println("Create Account: Account type " + new_val + " selected.");
						accountSelected = true;
					}
				});
		
		selectUser.setOnAction(new EventHandler<ActionEvent>() {   /// submit transaction
			@Override 
			public void handle(ActionEvent e) {

					if(rb3.isSelected()) {     /// the deposit radio button is selected
						NewCustomerSelectMenu(sbc,customerSelectedVBox,createTab,tabPane, create);
					}
					if(rb4.isSelected()) {    /// Existing
						ExistingCustomerSelectMenu(sbc,customerSelectedVBox,createTab,tabPane,create);
					}
			}
		});  // end of button to withdrawl or deposit funds


		firstDepositTextField.textProperty().addListener(new ChangeListener<String>() {  /// awesome!!
			@Override
			public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {	
				accountBalance.setText("Balance: $" + newValue);
                newBalance = newValue;
			}
		});

		// createTab.setId("create");
		createTab.setText("Create Account");
		create.setDisable(true);  // disables create button if tab is open (we only want to be able to open one create tab)
		return createTab;
	}  // createTab method end


	static int ExistingCustomerSelectMenu(SimpleBankingSystem sbc, VBox customerSelectedVBox,Tab createTab, TabPane tabPane, Button create) {
		// GUI objects
		Stage accountStage = new Stage();
		VBox customerBox = new VBox();
		HBox buttonHBox = new HBox();
		Label customerIDFinal = new Label();
		Label customerNameFinal = new Label();
		Label customerAddressFinal = new Label();
		Button btnSelectCustomer = new Button("Select Customer");
		Button btnForward = new Button(">");
		Button btnReverse = new Button("<");
		Label customerName = new Label("Name: " + SimpleBankingSystem.customers.get(customerObjectSelect).getName());
		Label customerAddress = new Label("Address: " + SimpleBankingSystem.customers.get(customerObjectSelect).getAddress());
		Label customerID = new Label("Customer ID: " + SimpleBankingSystem.customers.get(customerObjectSelect).customerID);
		Scene accountscene = new Scene(customerBox, 250, 280);  /// main size
		Button createNewAccountBtn = new Button("Create Account");
					
		// GUI Properties
		customerBox.setAlignment(Pos.BOTTOM_CENTER);
		customerBox.setSpacing(10);
		buttonHBox.getChildren().addAll(btnReverse,btnForward,btnSelectCustomer);
		customerBox.getChildren().addAll(customerID,customerName,customerAddress,buttonHBox);
		customerBox.setAlignment(Pos.CENTER);
		customerBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.setSpacing(10);
		customerBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");
		buttonHBox.setStyle("-fx-padding: 10;");
		
		btnForward.setOnAction(new EventHandler<ActionEvent>() {   /// event on button
			@Override   
			public void handle(ActionEvent e) {
				if(customerObjectSelect < SimpleBankingSystem.customers.size() -1) {
					customerObjectSelect++;
					customerName.setText("Name: " + SimpleBankingSystem.customers.get(customerObjectSelect).getName());
					customerAddress.setText("Address: " + SimpleBankingSystem.customers.get(customerObjectSelect).getAddress());
					customerID.setText("Customer ID: " + SimpleBankingSystem.customers.get(customerObjectSelect).customerID);
				}
			}  // inner class close
		});  /// choose account button handler close
		
		btnReverse.setOnAction(new EventHandler<ActionEvent>() {   /// event on button
			@Override   
			public void handle(ActionEvent e) {
				if(customerObjectSelect > 0) {
					customerObjectSelect--;
					customerName.setText("Name: " + SimpleBankingSystem.customers.get(customerObjectSelect).getName());
					customerAddress.setText("Address: " + SimpleBankingSystem.customers.get(customerObjectSelect).getAddress());
					customerID.setText("Customer ID: " + SimpleBankingSystem.customers.get(customerObjectSelect).customerID);
				}
			}  // inner class close
		});  /// choose account button handler close
		
		btnSelectCustomer.setOnAction(new EventHandler<ActionEvent>() {   /// event on button
			@Override   
				public void handle(ActionEvent e) {
				    customerSelectedVBox.getChildren().clear();
					customerIDFinal.setText("Customer ID: " + SimpleBankingSystem.customers.get(customerObjectSelect).customerID);	
					customerNameFinal.setText("Name: " + SimpleBankingSystem.customers.get(customerObjectSelect).getName());
					customerAddressFinal.setText("Address: " + SimpleBankingSystem.customers.get(customerObjectSelect).getAddress());;
					customerSelectedVBox.getChildren().addAll(customerIDFinal,customerNameFinal,customerAddressFinal,createNewAccountBtn);
					accountStage.close();
					customerSelected = true;
			}  // inner class close
		}); 
		
		createNewAccountBtn.setOnAction(new EventHandler<ActionEvent>() {    
			@Override 
			public void handle(ActionEvent e) {
            createNewAccount(tabPane, createTab, create, sbc, SimpleBankingSystem.customers.get(customerObjectSelect).customerID);
			}
		});

		accountStage.setTitle("Account lookup");
		accountStage.setScene(accountscene);
		accountStage.show();
		return 0;
	}

	static int NewCustomerSelectMenu(SimpleBankingSystem sbc, VBox customerSelectedVBox, Tab createTab, TabPane tabPane, Button create) {
		int newCustomerNumber = SimpleBankingSystem.getNewCustomerNumber();
		// GUI objects
		Stage accountStage = new Stage();
		VBox customerBox = new VBox();
		HBox nameHBox = new HBox();
		HBox addressHBox = new HBox();
		Button btnCreateCustomer = new Button("Create Customer");
		Button createNewAccountBtn = new Button("Create Account");
		TextField nameTextField = new TextField();
		TextField addressTextField = new TextField();
		Label customerID = new Label("Customer ID: " + newCustomerNumber);
		Label customerName = new Label("Name:");
		Label customerAddress = new Label("Address:");
		Label customerIDFinal = new Label();
		Label customerNameFinal = new Label();
		Label customerAddressFinal = new Label();		
		Scene customerScene = new Scene(customerBox, 250, 280);  /// main size

		addressHBox.getChildren().addAll(customerAddress,addressTextField);
		nameHBox.getChildren().addAll(customerName,nameTextField);
		customerBox.getChildren().addAll(customerID,nameHBox , addressHBox, btnCreateCustomer);

		// GUI Properties
		nameHBox.setAlignment(Pos.CENTER);
		nameHBox.setSpacing(10);
		addressHBox.setAlignment(Pos.CENTER);
		addressHBox.setSpacing(10);
		customerBox.setAlignment(Pos.CENTER);
		customerBox.setSpacing(10);
		customerBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");

		btnCreateCustomer.setOnAction(new EventHandler<ActionEvent>() {    
			@Override 
			public void handle(ActionEvent e) {
				customerSelectedVBox.getChildren().clear();
				customerIDFinal.setText("Customer ID: " + newCustomerNumber);	
				customerNameFinal.setText("Name: " + nameTextField.getText());
				customerAddressFinal.setText("Address: " + addressTextField.getText());
				SimpleBankingSystem.customers.add(new Customer(nameTextField.getText(), addressTextField.getText(), newCustomerNumber));
				customerSelectedVBox.getChildren().addAll(customerIDFinal,customerNameFinal,customerAddressFinal,createNewAccountBtn);
				accountStage.close();
				customerSelected = true;
			}  // selectUser createNewAccountBtn
		});

		createNewAccountBtn.setOnAction(new EventHandler<ActionEvent>() {    
			@Override 
			public void handle(ActionEvent e) {
				createNewAccount(tabPane, createTab, create, sbc, newCustomerNumber);
			}
		});

		accountStage.setTitle("Create Customer");
		accountStage.setScene(customerScene);
		accountStage.show();
		return 0;
	}

	public static void createNewAccount(TabPane tabPane, Tab createTab, Button create, SimpleBankingSystem sbc, int newCustomerNumber) {
		if(SimpleBankingSystem.checkForDouble(newBalance)) {  // our balance is good
			Double newBalanceDouble = Double.parseDouble(newBalance);
			System.out.println("Deposit: " + newBalance);
			balanceSelected = true;

			if (!customerSelected) {
				popUpAlert("Missing Fields!", "You need to select a Customer!");
			} 
			else if (!accountSelected) {
				popUpAlert("Missing Fields!", "You need to select an account type!");
			} 
			else if (!balanceSelected) {
				popUpAlert("Missing Fields!", "You need to enter a starting balance!");
			} else {
				if(accountTypeSelected.equals("Gold")) {
					System.out.println("Gold account created");
					SimpleBankingSystem.account.add(new Gold(accountNumber, newBalanceDouble , SimpleBankingSystem.customers.get(newCustomerNumber -1), 0.05));
				} else if (accountTypeSelected.equals("Regular")) {
					System.out.println("Regular account created");
					SimpleBankingSystem.account.add(new Regular(accountNumber, newBalanceDouble, SimpleBankingSystem.customers.get(newCustomerNumber -1), 0.06, 0.0));
				} else {
					System.out.println("Checking account created");
					SimpleBankingSystem.account.add(new Checking(accountNumber, newBalanceDouble , SimpleBankingSystem.customers.get(newCustomerNumber -1), 3.0));	
				}
				accountSelected = false;
				customerSelected = false;
				balanceSelected = false;				
				tabPane.getTabs().remove(createTab);
				create.setDisable(false);
				AccountViewTab.accountViewTab(sbc, tabPane, "" + accountNumber);
			}
		} else {
			popUpAlert("Mismatch Error", "Your initial deposit must be a valid number!");
		}
	}

	public static void popUpAlert(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

} // EOClass
