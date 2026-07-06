package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTest extends BaseTest {

    private hexlet.code.page.UsersPage usersPage;

    @BeforeEach
    public void setUp() {
        loginPage.open(baseUrl);
        loginPage.login("admin", "admin");
        assertTrue(mainPage.isAppBarDisplayed(), "Should be logged in");

        usersPage = new hexlet.code.page.UsersPage(driver);
        usersPage.open(baseUrl);
    }

    @Test
    public void testCreateUser() {
        usersPage.createUser("John", "Doe", "john.doe@example.com");
        assertTrue(usersPage.isUserInList("John"), "New user 'John' should appear in the list");
    }

    @Test
    public void testUserListDisplaysFields() {
        usersPage.createUser("Test", "User", "test.user@example.com");
        assertTrue(usersPage.getUserCount() > 0, "User table should have at least one row");
    }

    @Test
    public void testEditUser() {
        usersPage.createUser("Original", "Name", "original@example.com");
        assertTrue(usersPage.isUserInList("Original"), "User should be created");
        usersPage.clickEditUser("Original");
        assertEquals("Original", usersPage.getFirstNameValue());
        usersPage.editAndSave("Updated", "User");
        assertTrue(usersPage.isUserInList("Updated"), "Updated user name should appear");
    }

    @Test
    public void testDeleteUser() {
        usersPage.createUser("DeleteMe", "Test", "delete@example.com");
        assertTrue(usersPage.isUserInList("DeleteMe"), "User should be created");

        int beforeCount = usersPage.getUserCount();
        usersPage.deleteUser("DeleteMe");

        assertFalse(usersPage.isUserInList("DeleteMe"), "Deleted user should not be in the list");
        assertEquals(beforeCount - 1, usersPage.getUserCount(), "Count should decrease by 1");
    }

    @Test
    public void testBulkDelete() {
        int beforeCount = usersPage.getUserCount();

        usersPage.selectAllUsers();
        usersPage.clickBulkDelete();

        assertEquals(0, usersPage.getUserCount(), "User list should be empty after bulk delete");
    }

    @Test
    public void testEmailValidation() {
        usersPage.clickCreate();
        usersPage.fillFirstName("Validation");
        usersPage.fillLastName("Test");
        usersPage.fillEmail("invalid-email");
        usersPage.clickSave();

        assertTrue(usersPage.hasValidationError() || driver.getCurrentUrl().contains("create"),
                "Should show validation error");
    }
}
