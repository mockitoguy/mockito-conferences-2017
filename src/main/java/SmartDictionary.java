public class SmartDictionary {
    private final OnlineWiki translator;
    private final LookUpHistory history;

    public SmartDictionary(OnlineWiki translator, LookUpHistory history) {
        this.translator = translator;
        this.history = history;
    }

    public String lookUp(String word) {
//        word += "?"; //imagine that this is a typo in argument manipulation logic
        String description = translator.findDescription(word);
        try {
            history.lookUpAttempt(word);
        } catch (HistoryFailure historyFailure) {
            //ignore
        }
        return description;
    }
}
