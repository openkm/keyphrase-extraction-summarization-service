package com.openkm.kea.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.openkm.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.kea.stemmers.Stemmer;
import com.openkm.kea.stopwords.Stopwords;

/**
 * Class that demonstrates how Kea works. Alternatively use the command line as
 * described in readme
 *
 * @author Olena Medelyan
 *
 */
public class ModelBuilder {

    private static Logger log = LoggerFactory.getLogger(ModelBuilder.class);
    private KEAModelBuilder km;
    private KEAKeyphraseExtractor ke;

    private void setOptionsTraining(String trainingFilesPath, String modelFilePath,
                                    String vocabularyFilePath, String vocabularyType, String documentEncoding, String lang,
                                    Stemmer stemmer, Stopwords stopwords) {
        km = new KEAModelBuilder();

        // A. required arguments (no defaults):

        // 1. Name of the directory -- give the path to your directory with
        // documents and keyphrases
        // documents should be in txt format with an extention "txt"
        // keyphrases with the same name as documents, but extension "key"
        // one keyphrase per line!
        km.setDirName(trainingFilesPath);

        // 2. Name of the model -- give the path to where the model is to be
        // stored and its name
        km.setModelName(modelFilePath);

        // 3. Name of the vocabulary -- name of the file (without extension)
        // that is stored in VOCABULARIES
        // or "none" if no Vocabulary is used (free keyphrase extraction).
        km.setVocabulary(vocabularyFilePath);

        // 4. Format of the vocabulary in 3. Leave empty if vocabulary = "none",
        // use "skos" or "txt" otherwise.
        km.setVocabularyFormat(vocabularyType);

        // B. optional arguments if you want to change the defaults
        // 5. Encoding of the document
        km.setEncoding(documentEncoding);

        // 6. Language of the document -- use "es" for Spanish, "fr" for French
        // or other languages as specified in your "skos" vocabulary
        km.setDocumentLanguage(lang); // es for Spanish, fr for French

        // 7. Stemmer -- adjust if you use a different language than English or
        // if you want to alterate results
        // (We have obtained better results for Spanish and French with
        // NoStemmer)
        km.setStemmer(stemmer);

        // 8. Stopwords -- adjust if you use a different language than English!
        km.setStopwords(stopwords);

        // 9. Maximum length of a keyphrase
        km.setMaxPhraseLength(5);

        // 10. Minimum length of a keyphrase
        km.setMinPhraseLength(1);

        // 11. Minumum occurrence of a phrase in the document -- use 2 for long
        // documents!
        km.setMinNumOccur(2);

        // Optional: turn off the keyphrase frequency feature
        // km.setUseKFrequency(false);

    }

    private void setOptionsTesting(String m_testdir, String trainingFilesPath, String modelFilePath,
                                   String vocabularyFilePath, String vocabularyType, String documentEncoding, String lang,
                                   Stemmer stemmer, Stopwords stopwords) {
        ke = new KEAKeyphraseExtractor(stopwords);

        // A. required arguments (no defaults):

        // 1. Name of the directory -- give the path to your directory with
        // documents
        // documents should be in txt format with an extention "txt".
        // Note: keyphrases with the same name as documents, but extension "key"
        // one keyphrase per line!

        ke.setDirName(m_testdir);

        // 2. Name of the model -- give the path to the model
        ke.setModelName(modelFilePath);

        // 3. Name of the vocabulary -- name of the file (without extension)
        // that is stored in VOCABULARIES
        // or "none" if no Vocabulary is used (free keyphrase extraction).
        ke.setVocabulary(vocabularyFilePath);

        // 4. Format of the vocabulary in 3. Leave empty if vocabulary = "none",
        // use "skos" or "txt" otherwise.
        ke.setVocabularyFormat(vocabularyType);

        // B. optional arguments if you want to change the defaults
        // 5. Encoding of the document
        ke.setEncoding(documentEncoding);

        // 6. Language of the document -- use "es" for Spanish, "fr" for French
        // or other languages as specified in your "skos" vocabulary
        ke.setDocumentLanguage(lang); // es for Spanish, fr for French

        // 7. Stemmer -- adjust if you use a different language than English or
        // want to alterate results
        // (We have obtained better results for Spanish and French with
        // NoStemmer)
        ke.setStemmer(stemmer);

        // 8. Stopwords
        ke.setStopwords(stopwords);

        // 9. Number of Keyphrases to extract
        ke.setNumPhrases(10);

        // 10. Set to true, if you want to compute global dictionaries from the
        // test collection
        ke.setBuildGlobal(false);
    }

    /**
     * createModel
     */
    private void createModel(Stopwords stopwords) {
        try {
            km.buildModel(km.collectStems(), stopwords);
            km.saveModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * extractKeyphrases
     */
    private void extractKeyphrases() {
        try {
            ke.loadModel();
            ke.extractKeyphrases(ke.collectStems());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Example file on how to use KEA from your Java code with sample documents
     * included in this package.
     *
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String sourceFolder = "";
        String trainingFilesPath = "";
        String vocabularyFile = "";
        String modelFilePath = "";
        String vocabularyFilePath = "";
        String stemerClassName = "";
        String vocabularyType = "";
        String documentEncoding = "";
        String lang = "";
        String stopwordsClassName = "";
        String stopwordsFile = "";
        String testingFoder = "";

        // Testing minor number of arguments
        if (args.length < 10) {
            log.info("Incorrect syntax, some arguments missings");
            log.info("java -Xmx512m -jar thesaurus.jar sourceFolder trainingFolder vocabularyFile vocabularyType "
                    + "stopwordFile modelFileName porterStemmerClass stopwordClass language documentEncoding ");
            System.exit(0);
        } else {
            sourceFolder = args[0];
            trainingFilesPath = sourceFolder + File.separator + args[1];
            vocabularyFile = args[2];
            vocabularyType = args[3];
            stopwordsFile = args[4];
            modelFilePath = sourceFolder + File.separator + args[5];
            stemerClassName = args[6];
            stopwordsClassName = args[7];
            lang = args[8];
            documentEncoding = args[9];
            if (args.length == 11) {
                testingFoder = sourceFolder + File.separator + args[10];
            }
            vocabularyFilePath = sourceFolder + File.separator + vocabularyFile;

            log.info("* Source folder: " + sourceFolder); // The parameter only exists from command line
        }

        createModel(trainingFilesPath, modelFilePath, vocabularyFilePath, stemerClassName, vocabularyType, documentEncoding,
                lang, stopwordsClassName, stopwordsFile, testingFoder);
    }

    public static void createModel(String trainingFilesPath, String modelFilePath, String vocabularyFilePath, String stemerClassName, String vocabularyType,
                                   String documentEncoding, String lang, String stopwordsClassName, String stopwordsFile, String testingFoder) {
        log.info("* Training folder: " + trainingFilesPath);
        log.info("* Vocabulary file: " + vocabularyFilePath);
        log.info("* Vocabulary type: " + vocabularyType);
        log.info("* Stopwords file: " + stopwordsFile);
        log.info("* Model file: " + modelFilePath);
        log.info("* Stemmer class: " + stemerClassName);
        log.info("* Stopword class: " + stopwordsClassName);
        log.info("* Language: " + lang);
        log.info("* Document encoding: " + documentEncoding);
        log.info("* Testing folder: " + testingFoder);

        ModelBuilder modelBuilder = new ModelBuilder();

        Stemmer stemmer = null;
        if (stemerClassName != null) {
            try {
                Class clazz = Class.forName(stemerClassName);
                stemmer = (Stemmer) clazz.newInstance();
            } catch (Exception e) {
                log.error("Error creating class instance", e);
            }
        }

        Stopwords stopwords = null;
        if (stopwordsClassName != null) {
            try {
                Class clazz = Class.forName(stopwordsClassName);
                stopwords = (Stopwords) clazz.newInstance();
            } catch (Exception e) {
                log.error("Error creating class instance", e);
            }
        }

        // to create a model from manually indexed documents,
        log.info("Creating the model... ");
        modelBuilder.setOptionsTraining(trainingFilesPath, modelFilePath, vocabularyFilePath, vocabularyType, documentEncoding, lang, stemmer, stopwords);
        modelBuilder.createModel(stopwords);
        log.info("Model created... ");

        // Testing folder is optional
        if (!testingFoder.equals("")) {
            // to extract keyphrases from new documents,
            log.info("Extracting keyphrases from test documents... ");

            modelBuilder.setOptionsTesting(testingFoder, trainingFilesPath, modelFilePath, vocabularyFilePath, vocabularyType, documentEncoding, lang, stemmer, stopwords);
            modelBuilder.extractKeyphrases();
            log.info("Look into " + testingFoder + " to see the results");
            log.info("and compare them to " + testingFoder + "/manual_keyphrases/.");
        }
    }
}
