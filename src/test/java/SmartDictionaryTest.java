import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SmartDictionaryTest {

    OnlineWiki wiki = mock(OnlineWiki.class);
    DictionaryHistory history = mock(DictionaryHistory.class);
    SmartDictionary dictionary = new SmartDictionary(wiki, history);

    @Test
    public void should_look_up_words() throws Exception {
        //given
        given(wiki.findDescription("mockito")).willReturn("Mocking framework");

        //when
        String description = dictionary.lookUp("mockito");

        //then
        assertEquals("Mocking framework", description);
    }

    @Test
    public void should_keep_history() throws Exception {
        //when
        dictionary.lookUp("mockito");

        //then
        verify(history).lookUpAttempt("mockito");
    }

    @Test
    public void should_ignore_history_failures() throws Exception {
        //given
        willThrow(new HistoryFailureException()).given(history).lookUpAttempt("mockito");

        //expect no exception thrown
        dictionary.lookUp("mockito");
    }
}