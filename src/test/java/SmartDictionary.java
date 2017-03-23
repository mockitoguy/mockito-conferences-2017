public class SmartDictionary {
    private final OnlineWiki wiki;
    private final DictionaryHistory history;

    public SmartDictionary(OnlineWiki wiki, DictionaryHistory history) {
        this.wiki = wiki;
        this.history = history;
    }

    public String lookUp(String word) {
//        word += "?"; // <- example of argument problem
        String description = wiki.findDescription(word);
        try {
            history.lookUpAttempt(word);
        } catch (HistoryFailureException e) {
            //ignore
        }
        return description;
    }
}
