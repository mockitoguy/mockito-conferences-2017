public interface LookUpHistory {
    void lookUpAttempt(String word) throws HistoryFailure;
}
