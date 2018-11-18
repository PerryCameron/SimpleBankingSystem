package simplebankingsystem;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AccountViewTab {
	//static String number = "";

	static void accountSelectMenu(SimpleBankingSystem sbc, TabPane tabPane) {
		// GUI objects
		Stage accountStage = new Stage();
		VBox accountBox = new VBox();
		Button btnFindAccount = new Button("Find Account");
		TextField accountTextField = new TextField();
		Scene accountscene = new Scene(accountBox, 250, 280);  /// main size
		ListView<String> listView = new ListView<String>();
		
		// GUI Properties
		accountBox.setAlignment(Pos.BOTTOM_CENTER);
		accountBox.setSpacing(10);

		// pulls up list of accounts and puts them into listView
		for(int i = 0; i < SimpleBankingSystem.account.size(); i++) {  
			listView.getItems().add("" + SimpleBankingSystem.account.get(i).accountNumber);
		}
		accountBox.getChildren().addAll(accountTextField,listView,btnFindAccount);
		
		// When you click on an item it listView it puts it into the text area
		listView.getSelectionModel().selectedItemProperty().addListener(  
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						accountTextField.setText(new_val);
					}
				});
		
		// when you click the button it checks to make sure account is valid and then opens tab
		btnFindAccount.setOnAction(new EventHandler<ActionEvent>() {   /// event on popup menu button
			@Override   
			public void handle(ActionEvent e) {   //// Pull up Account on tab
				if(!checkForMatch(accountTextField.getText())) {  ///// Error check for matching account number
					accountTextField.clear();
				} else {
					accountViewTab(sbc, tabPane, accountTextField.getText());
					accountStage.close();
				}	
			}  // end of inner class
		});  /// end of button to open account tab once account is selected
		accountStage.setTitle("Account lookup");
		accountStage.setScene(accountscene);
		accountStage.show();
	}

	
	static Tab accountViewTab(SimpleBankingSystem sbc, TabPane tabPane, String accountNumber) {
		// GUI Objects
		Tab accountTab = new Tab();
		GridPane gPane = new GridPane();
		GridPane accountBox4 = new GridPane();
		VBox accountVBox1 = new VBox();
		VBox accountVBox2 = new VBox();
		VBox accountVBox3 = new VBox();
		Label customerHeading = new Label("Customer");
		Label eomHeading = new Label("  End of Month\n Interest and Fees");
		Button appliyMonthlyFees = new Button("Apply");
		Button deleteAccount = new Button("Remove Account");
		Button transactionSubmit = new Button("Submit");
		TextField transactionAmount = new TextField();
		final ToggleGroup group = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Deposit");
		RadioButton rb2 = new RadioButton("Withdrawal");
		accountTab.setGraphic(GuiControl.buildImage("/simplebankingsystem/images/Choose.png"));
		
		// Object Properties
		gPane.setAlignment(Pos.TOP_CENTER);
		gPane.setPadding(new Insets(20,10,10,10));
		gPane.setVgap(20);
		gPane.setHgap(10);
		gPane.setGridLinesVisible(false); 
		accountVBox3.setStyle("-fx-padding: 10;");
		accountVBox2.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");
		accountVBox1.setStyle("-fx-padding: 10;");
		accountBox4.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;" + "-fx-border-color: gray;");

		accountVBox1.setSpacing(10);
		accountVBox1.setPrefWidth(200);
		accountVBox2.setSpacing(10);
		accountVBox3.setSpacing(10);
		
		gPane.add(accountVBox1, 0, 0);
		gPane.add(accountVBox3, 0, 1);
		gPane.add(accountBox4, 1, 0);  // gridPane in a gridPane
		gPane.add(accountVBox2, 1, 1);



			int accountArrayElementNumber = sbc.byAccountNumber(accountNumber);  // gets element number for the account number input
			String customerNumber = "" + SimpleBankingSystem.account.get(accountArrayElementNumber).getCustomerID(); // gets customerID identified with account
			int customerArrayElementNumber = sbc.byCustomerIDNumber(customerNumber); // gets array element associated with customerID

			//////////////////  Account Info Box ////////////
			Label accountTypeLabel = new Label(SimpleBankingSystem.account.get(accountArrayElementNumber).getAccountType() + " Account");
			accountTypeLabel.setFont(Font.font("Arial",FontWeight.BOLD, 20));
			Label balanceTypeLabel = new Label(String.format("Balance: $%.2f", SimpleBankingSystem.account.get(accountArrayElementNumber).getBalance()));
			balanceTypeLabel.setFont(Font.font("Arial", 16));
			accountVBox1.getChildren().add(accountTypeLabel);  // prints Account type on tab
			accountVBox1.getChildren().add(balanceTypeLabel);
			//////////////// EOM and Delete ///////////////
			accountVBox3.setAlignment(Pos.CENTER);
			accountVBox3.getChildren().addAll(eomHeading,appliyMonthlyFees,deleteAccount);

			appliyMonthlyFees.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent e) {
					SimpleBankingSystem.account.get(accountArrayElementNumber).monthlyUpdate();
					balanceTypeLabel.setText(String.format("Balance: $%.2f", SimpleBankingSystem.account.get(accountArrayElementNumber).getBalance()));
				}
			});

			deleteAccount.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent e) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Delete Account #" + accountNumber);
					alert.setContentText("Are you sure you want to delete this account?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						SimpleBankingSystem.account.remove(accountArrayElementNumber);
						accountVBox3.getChildren().clear();
						accountBox4.getChildren().clear();
						Label deleted = new Label("Deleted");
						deleted.setFont(Font.font("Arial",FontWeight.BOLD, 16));
						deleted.setTextFill(Color.web("#ff0000"));
						accountBox4.getChildren().add(deleted);
					} else {
						alert.close();
					}
				}
			});
			/////////////////// Transaction Box /////////////////
			accountBox4.setAlignment(Pos.CENTER);
			accountBox4.setPadding(new Insets(20,10,10,10));
			accountBox4.setVgap(5);
			accountBox4.setHgap(10);
			transactionAmount.setPrefWidth(50);
			rb1.setToggleGroup(group);
			rb1.setSelected(true);
			
			rb2.setToggleGroup(group);
			accountBox4.add(rb1, 0, 0);
			accountBox4.add(rb2, 0, 1);
			accountBox4.add(transactionAmount, 1, 0);
			accountBox4.add(transactionSubmit, 1, 1);
			////////////////////////////////  Deposit and WithDrawl section////////////////////
			transactionSubmit.setOnAction(new EventHandler<ActionEvent>() {   /// submit transaction
				@Override 
				public void handle(ActionEvent e) {
					if(!SimpleBankingSystem.checkForDouble(transactionAmount.getText())) {  /// error checking for double
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Mismatch");
						alert.setContentText("This is not a number.");
						alert.showAndWait();
					}  else {                 /// only if the double passes the smell test
						if(rb1.isSelected()) {     /// the deposit radio button is selected
							System.out.println("Deposit");
							SimpleBankingSystem.depositFunds(accountArrayElementNumber, transactionAmount.getText());
							balanceTypeLabel.setText(String.format("Balance: $%.2f", SimpleBankingSystem.account.get(accountArrayElementNumber).getBalance()));
						}
						if(rb2.isSelected()) {    /// the selected radio button is selected
							System.out.println("Withdrawl");
							SimpleBankingSystem.withdrawFunds(accountArrayElementNumber, transactionAmount.getText());
							balanceTypeLabel.setText(String.format("Balance: $%.2f", SimpleBankingSystem.account.get(accountArrayElementNumber).getBalance()));
						}
					}
					transactionAmount.clear();
				}  // inner class
			});  // end of button to withdrawl or deposit funds
			///////////////////////  Customer Box ////////////////////
			customerHeading.setFont(Font.font("Arial",FontWeight.BOLD, 16));
			accountVBox2.getChildren().add(customerHeading);
			accountVBox2.getChildren().add(new Label("ID: " + customerNumber));
			accountVBox2.getChildren().add(new Label("Name: " + SimpleBankingSystem.customers.get(customerArrayElementNumber).getName()));
			accountVBox2.getChildren().add(new Label("Address: " + SimpleBankingSystem.customers.get(customerArrayElementNumber).getAddress()));
			accountTab.setText(accountNumber);
			accountTab.setContent(gPane);
			tabPane.getTabs().add(accountTab);
			tabPane.getSelectionModel().select(accountTab);
		return accountTab;
	}


	public static boolean checkForMatch(String toCheck) {
		boolean accountExists = false;
		String message = "does not exist!";
		if(SimpleBankingSystem.checkForInteger(toCheck)) {  /// make sure its at least an integer before we try to match it to an account
			for(int i = 0; i < ((List<Account>) SimpleBankingSystem.account).size(); i++) {
				if(SimpleBankingSystem.account.get(i).accountNumber == Integer.parseInt(toCheck)) {
					System.out.println("Found a Match!");
					accountExists = true;
					break;
				} // end if
			} // end for
		} else {
			message = "is not valid!";
		}
		if(!accountExists) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Account #" + toCheck);
			alert.setContentText("Ooops, this account number " + message);
			alert.showAndWait();
			System.out.println("No match found.");
		}
		return accountExists;
	}

}