package AIToolkit.Bayes;

import AIToolkit.TextMining.BagOfWords;
import java.util.HashMap;

/**
 * Naive Bayes is a supervisionazed classification algorithm based on the
 * Bayes Theorem that assumes that all features are indepent with each other.
 * 
 * @author Luan
 */
public class NaiveBayes {

    private final BagOfWords bag;

    public NaiveBayes(BagOfWords bag) {
        this.bag = bag;
    }

    public static NaiveBayes with(BagOfWords bag) {
        return new NaiveBayes(bag);
    }

    /**
     * Classifies the input.
     * 
     * @param words The input must be an array of words of the text.
     * @return The detected class.
     */
    public String classify(String[] words) {

        HashMap<String, Double> rank = new HashMap<>();
        double sum = 0;
        for (String className : bag.getCategoriesClasses()) {
            HashMap<String, Double> classData = bag.getCategory(className);

            double p = 1;
            for (String word : words) {
                if (classData.containsKey(word)) {
                    p += classData.get(word);
                }
            }
            p /= bag.getProbabilityOf(className) + 1;
            sum += p;
            rank.put(className, rank.getOrDefault(className, 0.0) + p);
        }

        String max = "";
        double maxPoints = Integer.MIN_VALUE;
        for (String className : rank.keySet()) {
            rank.put(className, rank.get(className) / sum);
            if (rank.get(className) > maxPoints) {
                maxPoints = rank.get(className);
                max = className;
            }
        }

        System.out.println("Classified as: " + max + " (with: " + maxPoints + ")");

        return max;
    }
}
