package AIToolkit.TextMining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Representation of a bag of words file, containing the frequence of words
 * for each class of document.
 * 
 * @author Luan
 */
public class BagOfWords {

    private final HashMap<String, Double> categoriesProbability;
    private final HashMap<String, HashMap<String, Double>> categories;
    private final double totalVocabulary;

    private BagOfWords() {
        categories = new HashMap<>();
        categoriesProbability = new HashMap<>();
        totalVocabulary = 0;
    }

    private BagOfWords(HashMap<String, HashMap<String, Double>> categories,
            HashMap<String, Double> categoriesProbability,
            double totalVocabulary) {
        this.categories = categories;
        this.categoriesProbability = categoriesProbability;
        this.totalVocabulary = totalVocabulary;
    }

    public Set<String> getCategoriesClasses() {
        return categories.keySet();
    }

    public HashMap<String, Double> getCategory(String name) {
        return categories.get(name);
    }

    public double getProbabilityOf(String className) {
        return categoriesProbability.get(className);
    }

    public double getTotalVocabulary() {
        return totalVocabulary;
    }

    public static BagOfWords instanciateFromFile(File bagOfWordsLocation) throws Exception {
        HashMap<String, HashMap<String, Double>> categories = new HashMap<>();
        HashMap<String, Double> categoriesProbability = new HashMap<>();
        double totalVocabulary = 0;

        BufferedReader br = new BufferedReader(new FileReader(bagOfWordsLocation));
        String line;
        boolean isHeader = true;
        ArrayList<String> words = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String items[] = line.split(";");
            String className = items[0];

            if (isHeader) {
                for (int i = 1; i < items.length; i++) {
                    words.add(items[i]);
                }
                totalVocabulary = items.length - 1;
                isHeader = false;
            } else {
                HashMap<String, Double> categoryData = new HashMap<>();

                for (int i = 1; i < items.length; i++) {
                    if (!items[i].equals("0.0")) {
                        double value = Double.parseDouble(items[i]);
                        categoryData.put(words.get(i - 1), value);

                        categoriesProbability.put(className,
                                categoriesProbability.getOrDefault(className, 0.0)
                                + value
                        );
                    }
                }


                categories.put(className, categoryData);
            }
        }

        return new BagOfWords(categories, categoriesProbability, totalVocabulary);
    }

    public static class Creator {

        private final HashMap<String, HashMap<String, Double>> categories;

        public Creator() {
            this.categories = new HashMap<>();
        }

        public Creator addToInstance(File location, String dataCategory) throws Exception {
            if (!categories.containsKey(dataCategory)) {
                categories.put(dataCategory, new HashMap<>());
            }
            HashMap<String, Double> words = categories.get(dataCategory);

            BufferedReader br = new BufferedReader(new FileReader(location));
            String line;
            while ((line = br.readLine()) != null) {
                String[] cWords = TextCleaner.with(line)
                        .stripAccents()
                        .stripPunctuation()
                        .stripNumbers()
                        .stripStopWords(false)
                        .getWords();

                for (String word : cWords) {
                    word = word.trim();
                    if (!word.equals("")) {
                        words.put(word, words.getOrDefault(word, 0.0) + 1);
                    }
                }
            }

            return this;
        }

        public Creator addToInstance(String text, String dataCategory) {
            if (!categories.containsKey(dataCategory)) {
                categories.put(dataCategory, new HashMap<>());
            }
            HashMap<String, Double> words = categories.get(dataCategory);

            String[] cWords = TextCleaner.with(text)
                    .stripAccents()
                    .stripPunctuation()
                    .stripNumbers()
                    .stripStopWords(false)
                    .getWords();

            for (String word : cWords) {
                word = word.trim();
                if (!word.equals("")) {
                    words.put(word, words.getOrDefault(word, 0.0) + 1);
                }
            }

            return this;
        }

        public BagOfWords saveToFileAndInstanciate(File location) throws Exception {
            StringBuilder doc = new StringBuilder();

            int totalExamples = 0;
            // We need to gather all the different words from instance and count
            // the amount of examples containing the word: 
            HashMap<String, Integer> allWords = new HashMap<>();
            for (String category : categories.keySet()) {

                for (String word : categories.get(category).keySet()) {
                    allWords.put(word, allWords.getOrDefault(word, 0) + 1);
                }
                totalExamples++;
            }

            //First thing we must do is write the header:
            doc.append("category;");
            for (String word : allWords.keySet()) {
                doc.append(word).append(";");
            }

            doc.append("\r\n");

            // First we must figure how many words we got for each 
            // class:
            HashMap<String, Double> totalTerms = new HashMap<>();
            for (String category : categories.keySet()) {
                for (String word : categories.get(category).keySet()) {
                    totalTerms.put(category,
                            totalTerms.getOrDefault(category, 0.0)
                            + categories.get(category).get(word));
                }
            }

            // Now, for each word, we got to count the TF IDF of them
            // for every class:
            HashMap<String, ArrayList<Double>> tfidfs = new HashMap<>();
            for (String word : allWords.keySet()) {
                if (!tfidfs.containsKey(word)) {
                    tfidfs.put(word, new ArrayList<>());
                }
                double[] wordTfidf = new double[categories.size()];
                double sumTfidf = 0;
                int i = 0;

                for (String category : categories.keySet()) {
                    double tfidf = tf(categories.get(category).getOrDefault(word, 1.0),
                            totalTerms.getOrDefault(category, 1.0))
                            * idf(totalExamples, allWords.get(word));
                    wordTfidf[i++] = tfidf;
                    sumTfidf += tfidf;
                }

                for (i = 0; i < categories.keySet().size(); i++) {
                    tfidfs.get(word).add(sumTfidf == 0 ? 0 : wordTfidf[i] / sumTfidf);
                }
            }

            int i = 0;
            for (String category : categories.keySet()) {
                doc.append(category).append(";");

                for (String word : allWords.keySet()) {
                    doc.append(tfidfs.get(word).get(i)).append(";");
                }
                i++;
                doc.append("\r\n");
            }

            // Finally, we save the file to disk:
            BufferedWriter bw = new BufferedWriter(new FileWriter(location));

            bw.write(doc.toString());
            bw.close();

            return BagOfWords.instanciateFromFile(location);
        }

        private double tf(double termFrequence, double totalTerms) {
            return (termFrequence / totalTerms);
        }

        private double idf(double totalExamples, double termInExamples) {
            return Math.log(totalExamples / termInExamples);
        }
    }
}
