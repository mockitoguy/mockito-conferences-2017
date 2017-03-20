import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class SmartDictionaryTest {

    <T> T strictMock(Class<T> typeToMock) {
        return mock(typeToMock, withSettings().defaultAnswer(invocation -> {
            throw new MockitoException("Unstubbed invocation:\n  " + invocation);
        }));
    }

    @Mock OnlineWiki wiki = mock(OnlineWiki.class);
    @Mock LookUpHistory history = mock(LookUpHistory.class);
    @InjectMocks SmartDictionary dictionary;

    @Test
    public void looks_up_words() throws Exception {
        willReturn("Mocking framework").given(wiki).findDescription("mockito");

        //when
        String description = dictionary.lookUp("mockito");

        //then
        assertEquals("Mocking framework", description);
    }

    @Test
    public void keeps_history() throws Exception {
        //when
        dictionary.lookUp("mockito");

        //then
        verify(history).lookUpAttempt("mockito");
    }

    @Test
    public void ignores_history_failures() throws Exception {
        willThrow(new HistoryFailure()).given(history).lookUpAttempt("mockito");

        //expect no exception to be thrown
        dictionary.lookUp("mockito");
    }
}
