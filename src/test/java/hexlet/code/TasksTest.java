package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TasksTest extends BaseTest {

    private hexlet.code.page.TasksPage tasksPage;

    @BeforeEach
    public void setUp() {
        loginPage.open(baseUrl);
        loginPage.login("admin", "admin");
        assertTrue(mainPage.isAppBarDisplayed());

        tasksPage = new hexlet.code.page.TasksPage(driver);
        tasksPage.open(baseUrl);
    }

    @Test
    public void testCreateTask() {
        tasksPage.createTask("New Task");
        assertTrue(tasksPage.isTaskOnBoard("New Task"));
    }

    @Test
    public void testTasksDisplayed() {
        assertTrue(tasksPage.getTaskCount() > 0);
    }

    @Test
    public void testEditTask() {
        tasksPage.createTask("Task to Edit");
        tasksPage.clickEditTask("Task to Edit");
        assertEquals("Task to Edit", tasksPage.getTitleValue());
        tasksPage.editAndSave("Edited Task");
        assertTrue(tasksPage.isTaskOnBoard("Edited Task"));
    }

    @Test
    public void testDeleteTask() {
        tasksPage.createTask("Task to Delete");
        assertTrue(tasksPage.isTaskOnBoard("Task to Delete"));
        tasksPage.deleteTask("Task to Delete");
        assertFalse(tasksPage.isTaskOnBoard("Task to Delete"));
    }
}
