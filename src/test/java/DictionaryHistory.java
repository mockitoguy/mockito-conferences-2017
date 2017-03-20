public interface DictionaryHistory {
    void lookUpAttempt(String word) throws HistoryFailureException;
}
