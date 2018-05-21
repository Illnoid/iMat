package matbutik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.util.ResourceBundle;
import java.util.function.Consumer;

import static javafx.application.Application.launch;

public class IMat extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        // To do: make resource bundle/fxml file and link it

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("matbutik/resources/IMat");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("IMat.fxml"), bundle);
        Parent root = loader.load();//FXMLLoader.load(getClass().getResource("IMat.fxml"), bundle);
        Parent shoppingCart = FXMLLoader.load(getClass().getResource("IMatShoppingCart.fxml"), bundle);
        Parent history = FXMLLoader.load(getClass().getResource("IMatHistory.fxml"), bundle);
        Parent account = FXMLLoader.load(getClass().getResource("IMatAccount.fxml"), bundle);
        Parent payment = FXMLLoader.load(getClass().getResource("IMatPayment.fxml"), bundle);
        Parent confirmation = FXMLLoader.load(getClass().getResource("IMatConfirmationPage.fxml"), bundle);



        Scene scene = new Scene(root, 1144, 685);

        ScreenController screenController = ScreenController.getInstance();
        screenController.setMain(scene);

        screenController.addScreen("", root);
        screenController.addScreen("ShoppingCart", shoppingCart);
        screenController.addScreen("History", history);
        screenController.addScreen("Account", account);
        screenController.addScreen("Payment", payment);
        screenController.addScreen("ConfirmationPage", confirmation);


        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        Consumer<Object> widthListenerConsumer = x->{
            IMatController controller = loader.getController();
            double width = controller.getSearchResultScrollPane().getWidth();//-34;
            controller.getSearchResult().setPrefWidth(width);
            controller.getCategories().getTabs().forEach(tab -> ((FlowPane)((ScrollPane)tab.getContent()).getContent()).setPrefWidth(width));
        };
        scene.widthProperty().addListener(widthListenerConsumer::accept);
        ((IMatController)loader.getController()).setWidthUpdateRunnable(()->widthListenerConsumer.accept(null));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                IMatDataHandler.getInstance().shutDown();
            }
        }));
    }
}
