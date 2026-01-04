public class WordFrequency {
    private String word; //The word we read
    private int times_occured; //counts how many times we read the word

    public WordFrequency(String word) {
        this.word = word;
    }

    public String toString() {
        return "Word:" + word + " occured " + times_occured + " times \n";
    }

    //returns the word
    public String key() {
        return word;
    }

    //increases the times_occured counter
    public void increment_times_occured() {
        times_occured += 1;
    }
    //returns how many times the word occured
    public int getTimes_occured(){
        return times_occured;
    }


}
