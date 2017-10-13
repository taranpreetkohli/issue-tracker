package issuetracker;

import issuetracker.db.DBContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MockitoExample {

    @Mock
    ClassToBeMocked mock;

    @Test
    public void ExampleTestForMockito() {
        ClassToBeMocked real = new ClassToBeMocked();
        assertEquals(true, real.methodToBeMocked());

        Mockito.when(mock.methodToBeMocked())
                .thenReturn(false);

        assertEquals(false, mock.methodToBeMocked());
    }

    private class ClassToBeMocked {

        boolean methodToBeMocked() {
            return true;
        }

    }

}
