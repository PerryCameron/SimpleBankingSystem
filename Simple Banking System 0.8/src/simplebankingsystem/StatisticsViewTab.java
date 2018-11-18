package simplebankingsystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StatisticsViewTab {
	static Tab viewStatistics() {
	Tab statisticsTab = new Tab(); // creates new tab object every time you press the button
	statisticsTab.setText("Bank Statistics");
	statisticsTab.setGraphic(GuiControl.buildImage("/simplebankingsystem/images/Statistics.png"));
	CategoryAxis xAxis = new CategoryAxis();
	xAxis.setLabel("Accounts");

	NumberAxis yAxis = new NumberAxis();
	yAxis.setLabel("US Dollars");

	BarChart<String,Number>  barChart = new BarChart<String,Number> (xAxis, yAxis);

	XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
	dataSeries1.setName("Bank Statistics");
	dataSeries1.getData().add(new XYChart.Data<String, Number>("Bank Assets", SimpleBankingSystem.getTotalBalance()));
	dataSeries1.getData().add(new XYChart.Data<String, Number>("Average", SimpleBankingSystem.getAverageBalance()));
	dataSeries1.getData().add(new XYChart.Data<String, Number>("Largest" , SimpleBankingSystem.getHighestAccountBalance()));
	
	barChart.getData().add(dataSeries1);
	
	xAxis.setTickLabelFill(Color.BLUE);
	VBox statsVBox = new VBox(barChart);


	GridPane statsgrid = new GridPane();

	statsVBox.getChildren().add(new Label(String.format("Bank Assets: $%.2f", SimpleBankingSystem.getTotalBalance())));
	statsVBox.getChildren()
			.add(new Label(String.format("Average Account Balance: $%.2f", SimpleBankingSystem.getAverageBalance())));
	statsVBox.getChildren()
			.add(new Label(String.format("Largest Account Balance: $%.2f", SimpleBankingSystem.getHighestAccountBalance())));
	statsVBox.getChildren().add(
			new Label("Number of Zero Balance Accounts: " + SimpleBankingSystem.getZeroBalanceAccounts()));
	
	statsVBox.getChildren().add(new Label("Number of Gold Accounts: " + SimpleBankingSystem.getNumberOfGoldAccounts()));
	statsVBox.getChildren().add(new Label("Number of Checking Accounts: " + SimpleBankingSystem.getNumberOfCheckingAccounts()));
	statsVBox.getChildren().add(new Label("Number of Regular Accounts: " + SimpleBankingSystem.getNumberOfRegularAccounts()));

	statsVBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
			+ "-fx-border-insets: 10;" + "-fx-border-radius: 15;" + "-fx-border-color: gray;");

	statisticsTab.setContent(statsgrid);
	statsgrid.setAlignment(Pos.CENTER_LEFT);
	statsgrid.setPadding(new Insets(10, 10, 10, 10));
	statsgrid.setVgap(500);
	statsgrid.setHgap(500);
	statsgrid.setGridLinesVisible(false);

	statsgrid.add(statsVBox, 0, 0);
	return statisticsTab;
	}	
}
