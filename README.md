# Text Mining Tests
Simple Java application that implements and applies text mining techniques with a Naive Bayes + TF-IDF. For this sample, I used two datasets:
 * [The UCI's Machine Learning Repository for Sentiment Analysis](https://archive.ics.uci.edu/ml/datasets/Sentiment+Labelled+Sentences).
 * [The UCI's Machine Learning Repository for news classification](https://archive.ics.uci.edu/ml/datasets/Twenty+Newsgroups/).


## Algorithms


### Text Pre-Processing
This software applies the following techniques in the input and training data:
 - Remove punctuation;
 - Remove stopwords;
 - Remove accents.


### TF-IDF: Text Frequency-Inverse Document Frequency 
Term frequency–inverse document frequency, is a numerical statistic that is intended to reflect how important a word is to a document in a collection or corpus. It is often used as a weighting factor in searches of information retrieval, text mining, and user modeling. The tf-idf value increases proportionally to the number of times a word appears in the document and is offset by the frequency of the word in the corpus, which helps to adjust for the fact that some words appear more frequently in general. 
> Source: [Wikipedia](https://en.wikipedia.org/wiki/Tf%E2%80%93idf)

### Naive Bayes
Naive Bayes is a famous probability algorithm used for text mining based on the [Bayes' theorem](https://en.wikipedia.org/wiki/Bayes%27_theorem). It requires a trained file containing the probability of each word to be used in a given context (class). The idea is that, if a text belongs to certain class, it will contains more words used in that class than in others. To classify a entry we must then extract and clear the words of the text (by removing accents, punctuation, etc), and compare it for each class of our trained file. The class that contains the most words in common is the one we choose as the answer. 
> Full description on: [Wikipedia](https://en.wikipedia.org/wiki/Naive_Bayes_classifier)

<hr>

## Simple Demo
### Bad ratings from Black Panther
<img src="https://i.imgur.com/M7JUqAc.gif">

> Source: [Black Panther - Users Reviews](https://www.imdb.com/title/tt1825683/reviews)

### Good ratings from Game of Thrones
<img src="https://i.imgur.com/sHwiD9r.gif">

> Source: [Game of Thrones - Users Reviews](https://www.imdb.com/title/tt0944947/reviews)


<hr>

## Licence

No.