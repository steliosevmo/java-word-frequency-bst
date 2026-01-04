import java.io.PrintStream;

public interface WordCounter {

        void insert(String w);
        WordFrequency search(String w);
        void remove(String w);
        void load(String filename);
        int getNumTotalWords();
        int getNumDistinctWords();
        int getFrequency(String w);
        WordFrequency getMaxFrequency();
        double getMeanFrequency();
        void addStopWord(String w);
        void removeStopWord(String w);
        void printTreeByWord(PrintStream stream);
        void printΤreeByFrequency(PrintStream stream);
}
