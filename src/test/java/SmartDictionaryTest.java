import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * 1. Mistakes that steal productivity
 *  - change String literal in "should_keep_history", run -> failure easy to debug
 *  - introduce bug in code (wrong parameter) run "should_keep_history" -> failure easy to debug
 *  - keep the bug, run "should_look_up_words" -> failure hard to debug
 * 2. Improve debuggability with Strict mocks
 *  - add failing answer
 *  - use and explain 'willReturn' stubbing API
 *  - demonstrate easier debugging
 *  - dark side of strict stubs - the need to add unnecessary 'expectations'
 * 3. Improve debuggability with Mockito v2
 *  - Add annotations, rule and show warnings
 *  - Configure the rule to use strict stubbings
 * 4. Other features of strict stubbing
 *  - add unused stubbing to "should_look_up_words" - clean code
 *  - add verifyNoMoreInteractions(wiki) to "should_look_up_words" - DRY
 *  - can use MockitoJUnitRunner or without rule/runner, too
 */
public class SmartDictionaryTest extends TestBase {

    @Mock OnlineWiki wiki;
    @Mock DictionaryHistory history;
    @InjectMocks SmartDictionary dictionary;

    @Test public void should_look_up_words() throws Exception {
        //given
        willReturn("Mocking framework").given(wiki).findDescription("mockito");

        //when
        String description = dictionary.lookUp("mockito");

        //then
        assertEquals("Mocking framework", description);

        verifyNoMoreInteractions(wiki);
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