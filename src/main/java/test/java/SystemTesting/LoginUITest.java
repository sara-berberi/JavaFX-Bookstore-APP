package test.java.SystemTesting;

import com.example.bookstorepro.AdministratorFiles.AdministratorGUI;
import com.example.bookstorepro.LogIn;
import com.example.bookstorepro.User;
import com.example.bookstorepro.UserController;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.PointQuery;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class LoginUITest extends ApplicationTest {
    private static UserController userController;

    @BeforeEach
    public void setup() throws Exception {

        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(LogIn.class);
    }

    private static void signUpTestUser(String firstName, String lastName, String email, String username, String role, String password, String verifyPassword) {
        userController = new UserController();
        userController.signUp(firstName, lastName, email, username, role, password, verifyPassword);
    }


    @Override
    public void start(Stage stage) throws Exception {
        // The start method is called by FxToolkit.setupApplication
    }

    @Test
    public void testLoginLibrarian() {
        signUpTestUser( "Sierra", "Mendes", "smendes@gmail.com", "smendes", "librarian", "password", "password");
        userController.saveData();
        clickOn("#usernameField").write("smendes");
        clickOn("#passwordField").write("password");
        clickOn("#loginButton");
        sleep(5000); //  a small delay to allow the GUI to update
        verifyThat("#librariansPane", NodeMatchers.isVisible());
    }

    @Test
    public void testLoginManager() {
        signUpTestUser("Barbie", "Doll", "barb@gmail.com", "barbie", "manager", "password", "password");
        userController.saveData();

        clickOn("#usernameField").write("barbie");
        clickOn("#passwordField").write("password");
        clickOn("#loginButton");
        sleep(5000);
        verifyThat("#managersPane", NodeMatchers.isVisible());
    }

    @Test
    public void testLoginAdmin() {
        signUpTestUser("Sydney", "Sweeney", "sweeney@gmail.com", "sydney", "administrator", "password", "password");
        userController.saveData();

        clickOn("#usernameField").write("sydney");
        clickOn("#passwordField").write("password");
        clickOn("#loginButton");
        sleep(5000);

        verifyThat("#administratorsPane", NodeMatchers.isVisible());
    }
    }


