package simplebankingsystem;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBarAndMenus {
	static MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty) {
		
		final MenuBar menuBar = new MenuBar();
		final Menu fileMenu = new Menu("File");
		final Menu testMenu = new Menu("Test");
		final Menu helpMenu = new Menu("Help");
	    final MenuItem aboutMenuItem = new MenuItem("About");
	    final MenuItem exit = new MenuItem("Exit");
	    final MenuItem open = new MenuItem("Open");
	    final MenuItem newData = new MenuItem("Create test Accounts");
	    final MenuItem save = new MenuItem("Save");
	    final MenuItem accountTestPrint = new MenuItem("Print Account to console");
	    final MenuItem customerTestPrint = new MenuItem("Print Customer to console");
	    final MenuItem programInfo = new MenuItem("Number of Elements in ArrayLists");
	    
	    save.setGraphic(new ImageView("/simplebankingsystem/images/Save.png"));
	    open.setGraphic(new ImageView("/simplebankingsystem/images/Open.png"));
	    aboutMenuItem.setGraphic(new ImageView("/simplebankingsystem/images/Banks.png"));
	    
		fileMenu.getItems().addAll(open,save);
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(exit);
		testMenu.getItems().addAll(newData,accountTestPrint,customerTestPrint,programInfo);
		helpMenu.getItems().add(aboutMenuItem);
		
		
		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(testMenu);
		menuBar.getMenus().add(helpMenu);
			
	      open.setOnAction(new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent e) {
	        	  SimpleBankingSystem.openAccountObjects();
	        	  SimpleBankingSystem.openCustomerObjects();
	          }
	      });
	      
	      newData.setOnAction(new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent e) {
	        	  SimpleBankingSystem.createAccounts();
              System.out.println("15 Test accounts created.");
	          }
	      });
	      
	      save.setOnAction(new EventHandler<ActionEvent>() {
	    	  @Override public void handle(ActionEvent e) {
	    		  SimpleBankingSystem.saveAccountObjects();
	    		  SimpleBankingSystem.saveCustomerObjects();
	    	  }
	      });
	      
	      accountTestPrint.setOnAction(new EventHandler<ActionEvent>() {
	    	  @Override public void handle(ActionEvent e) {
	    		  for (Account ac: SimpleBankingSystem.account) {
	    				System.out.println(ac);
	    				}
	    	  }
	      });
	      
	      customerTestPrint.setOnAction(new EventHandler<ActionEvent>() {
	    	  @Override public void handle(ActionEvent e) {
	    		  for (Customer cu: SimpleBankingSystem.customers) {
	    				System.out.println(cu);
	    				}
	    	  }
	      });
	      
	      programInfo.setOnAction(new EventHandler<ActionEvent>() {
	    	  @Override public void handle(ActionEvent e) {
	    		  System.out.println("CustomerID set to: " + SimpleBankingSystem.customerID);
	    		  System.out.println("accountNumber set to: " + SimpleBankingSystem.accountNumber);
	    		  System.out.println("customer.size() : " + SimpleBankingSystem.customers.size());
	    		  System.out.println("account.size() : " + SimpleBankingSystem.account.size());
	    	  }
	      });
	      
	      aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent e) {
	        	  Alert alert = new Alert(AlertType.INFORMATION);
	        	  alert.setTitle("Information Dialog");
	        	  alert.setHeaderText(null);
	        	  alert.setContentText("Simple Banking System v 1.0 by Jared Pletcher and Parrish Cameron");
	        	  alert.showAndWait();
	          }
	      });
	     
	      exit.setOnAction(new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent e) {
	              Platform.exit();
	              System.exit(0);
	          }
	      });
	      
        // bind width of menu bar to width of associated stage
		menuBar.prefWidthProperty().bind(menuWidthProperty);
		return menuBar;
	}
}


