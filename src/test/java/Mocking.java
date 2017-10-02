import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class Mocking {
    static Answer strict() {
        return new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new RuntimeException("Unexpected invocation:\n  " + invocation);
            }
        };
    }
}
