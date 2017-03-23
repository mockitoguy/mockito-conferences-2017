import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import sun.jvmstat.monitor.MonitorException;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SmartDictionaryTest {

    //2 kinds of typos
    //strict mocks example
    //v2 - warnings, strict stubs
    //DRY, detect unused stubs with runner
    @Mock OnlineWiki wiki;
    @Mock DictionaryHistory history;

    @InjectMocks SmartDictionary dictionary;

    @Rule public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Test public void should_look_up_words() throws Exception {
        //given
        willReturn("Mocking framework").given(wiki).findDescription("mockito");
        willReturn("foo").given(wiki).findDescription("bar");

        //when
        String description = dictionary.lookUp("mockito");

        //then
        assertEquals("Mocking framework", description);
    }

    @Test public void should_keep_history() throws Exception {
        //when
        dictionary.lookUp("mockito");

        //then
        verify(history).lookUpAttempt("mockito");
    }

    @Test public void should_ignore_history_failures() throws Exception {
        //given
        willThrow(new HistoryFailureException()).given(history).lookUpAttempt("mockito");

        //expect no exception thrown
        dictionary.lookUp("mockito");
    }
}