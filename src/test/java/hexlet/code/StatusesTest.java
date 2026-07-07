package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class StatusesTest extends BaseTest {

    private hexlet.code.page.StatusesPage statusesPage;

    @BeforeEach
    public void setUp() {
        loginPage.open(baseUrl);
        loginPage.login("admin", "admin");
        assertTrue(mainPage.isAppBarDisplayed(), "Should be logged in");

        statusesPage = new hexlet.code.page.StatusesPage(driver);
        statusesPage.open(baseUrl);
    }

    @Test
    public void testCreateStatus() {
        statusesPage.createStatus("New Status", "new-status");
        assertTrue(statusesPage.isStatusInList("New Status"), "New status should appear in the list");
    }

    @Test
    public void testStatusListDisplaysFields() {
        statusesPage.createStatus("Test Status", "test-status");
        assertTrue(statusesPage.getStatusCount() > 0, "Status table should have at least one row");
    }

    @Test
    public void testEditStatus() {
        statusesPage.createStatus("Original", "original-slug");
        assertTrue(statusesPage.isStatusInList("Original"), "Status should be created");
        statusesPage.clickEditStatus("Original");
        assertEquals("Original", statusesPage.getNameValue());
        assertEquals("original-slug", statusesPage.getSlugValue());
        statusesPage.editAndSave("Updated", "updated-slug");
        assertTrue(statusesPage.isStatusInList("Updated"), "Updated status should appear");
    }

    @Test
    public void testDeleteStatus() {
        statusesPage.createStatus("DeleteMe", "delete-me");
        assertTrue(statusesPage.isStatusInList("DeleteMe"), "Status should be created");
        int beforeCount = statusesPage.getStatusCount();
        statusesPage.deleteStatus("DeleteMe");
        assertFalse(statusesPage.isStatusInList("DeleteMe"), "Deleted status should not be in the list");
        assertEquals(beforeCount - 1, statusesPage.getStatusCount(), "Count should decrease by 1");
    }

    @Test
    public void testBulkDelete() {
        statusesPage.selectAllStatuses();
        statusesPage.clickBulkDelete();
        assertEquals(0, statusesPage.getStatusCount(), "Status list should be empty after bulk delete");
    }
}
