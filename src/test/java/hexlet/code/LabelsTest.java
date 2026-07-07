package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class LabelsTest extends BaseTest {

    private hexlet.code.page.LabelsPage labelsPage;

    @BeforeEach
    public void setUp() {
        loginPage.open(baseUrl);
        loginPage.login("admin", "admin");
        assertTrue(mainPage.isAppBarDisplayed(), "Should be logged in");

        labelsPage = new hexlet.code.page.LabelsPage(driver);
        labelsPage.open(baseUrl);
    }

    @Test
    public void testCreateLabel() {
        labelsPage.createLabel("New Label");
        assertTrue(labelsPage.isLabelInList("New Label"), "New label should appear in the list");
    }

    @Test
    public void testLabelListDisplaysFields() {
        labelsPage.createLabel("Test Label");
        assertTrue(labelsPage.getLabelCount() > 0, "Label table should have at least one row");
    }

    @Test
    public void testEditLabel() {
        labelsPage.createLabel("Original");
        assertTrue(labelsPage.isLabelInList("Original"), "Label should be created");
        labelsPage.clickEditLabel("Original");
        assertEquals("Original", labelsPage.getNameValue());
        labelsPage.editAndSave("Updated");
        assertTrue(labelsPage.isLabelInList("Updated"), "Updated label should appear");
    }

    @Test
    public void testDeleteLabel() {
        labelsPage.createLabel("DeleteMe");
        assertTrue(labelsPage.isLabelInList("DeleteMe"), "Label should be created");
        int beforeCount = labelsPage.getLabelCount();
        labelsPage.deleteLabel("DeleteMe");
        assertFalse(labelsPage.isLabelInList("DeleteMe"), "Deleted label should not be in the list");
        assertEquals(beforeCount - 1, labelsPage.getLabelCount(), "Count should decrease by 1");
    }

    @Test
    public void testBulkDelete() {
        labelsPage.selectAllLabels();
        labelsPage.clickBulkDelete();
        assertEquals(0, labelsPage.getLabelCount(), "Label list should be empty after bulk delete");
    }
}
