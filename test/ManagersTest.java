import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void shouldBeNotNullWhenCallGetDefault(){
        Managers managers = new Managers();
        managers.getDefault();
        assertTrue(managers.getDefault() instanceof InMemoryTaskManager);
    }
}