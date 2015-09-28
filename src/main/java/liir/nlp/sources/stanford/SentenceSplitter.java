package liir.nlp.sources.stanford;


import edu.stanford.nlp.util.Generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by quynhdo on 28/08/15.
 */
public class SentenceSplitter extends liir.nlp.interfaces.preprocessing.SentenceSplitter{

    private final Set<String> sentDelims;
    private final Set<String> delimFollowers;
    private final String[] sentenceFinalFollowers = {")", "]", "\"", "\'", "''", "-RRB-", "-RSB-", "-RCB-"};
    private String sentenceDelimiter = null;
    private String[] sentenceFinalPuncWords = DEFAULT_SENTENCE_DELIMS;
    private static final Pattern wsPattern = Pattern.compile("\\s+");
    public static final String[] DEFAULT_SENTENCE_DELIMS = {".", "?", "!"};
    public static final String NEWLINE_TOKEN = "*NL*";

    public SentenceSplitter() {

        setName("Stanford Sentence Splitter");
        boolean eolIsSignificant = false;
        sentDelims = Generics.newHashSet();
        if (sentenceDelimiter == null) {
            if (sentenceFinalPuncWords != null) {
                sentDelims.addAll(Arrays.asList(sentenceFinalPuncWords));
            }
            delimFollowers = Generics.newHashSet(Arrays.asList(sentenceFinalFollowers));
        } else {
            sentDelims.add(sentenceDelimiter);
            delimFollowers = Generics.newHashSet();
            eolIsSignificant = wsPattern.matcher(sentenceDelimiter).matches();
            if(eolIsSignificant) { // For Stanford English Tokenizer
                sentDelims.add(NEWLINE_TOKEN);
            }
        }



    }
    public void setSentenceFinalPuncWords(String[] sentenceFinalPuncWords) {
        this.sentenceFinalPuncWords = sentenceFinalPuncWords;
    }

    public  List<String[]> process(String[] tokens){
        List <String[]> sentenceList = new ArrayList<String[]>();

        boolean seenBoundary = false;
        List<String> current_sentence = new ArrayList<String>();
        for (int i=0;i<tokens.length; i++)
        {
            if (sentDelims.contains(tokens[i])) {
                seenBoundary = true;
            } else if (seenBoundary && !delimFollowers.contains(tokens[i])) {
                seenBoundary = false;
                String[] arr= new String[current_sentence.size()];
                current_sentence.toArray(arr);
                sentenceList.add(arr);
                current_sentence.clear();
                current_sentence.add(tokens[i]);
                continue;


            }
            if ( ! (wsPattern.matcher(tokens[i]).matches() ||
                    tokens[i].equals(NEWLINE_TOKEN))) {
                current_sentence.add(tokens[i]);
            }
        }

        if (current_sentence.size()!= 0)
        {
            String[] arr= new String[current_sentence.size()];
            current_sentence.toArray(arr);
            sentenceList.add(arr);
        }



        return sentenceList;
    }


    /*
    // both tokenizer and sentence splitter
    public static List<String[]> processs(String text){
        Reader reader = new StringReader(text);
        DocumentPreprocessor processor= new DocumentPreprocessor(reader);
        List <String[]> sentenceList = new ArrayList<String[]>();
        for (List<HasWord> sentence : processor) {
            String[] words = new String[sentence.size()];
            for (int i = 0; i< words.length; i++){
                words[i] = sentence.get(i).toString();
            }
            sentenceList.add(words);
        }

        return sentenceList;
    }

*/
    /*
    public static String processsToXML(String text){
        Reader reader = new StringReader(text);
        DocumentPreprocessor processor= new DocumentPreprocessor(reader);
        Text txt= new Text();

        for (List<HasWord> sentence : processor) {
            String[] words = new String[sentence.size()];
            Sentence se = new Sentence();
            for (int i = 0; i< words.length; i++){
                Word w = new Word();
                w.setStr(sentence.get(i).toString());
                se.add(w);
            }
            txt.add(se);
        }

        return txt.toXMLString();
    }

*/
    /*
    public Text process(List<Word> words) throws  Exception{
        List <Sentence> sentenceList = new ArrayList<Sentence>();

        boolean seenBoundary = false;
        Sentence current_sentence = new Sentence();
        for (int i=0;i<words.size(); i++)
        {
            if (sentDelims.contains(words.get(i).getStr())) {
                seenBoundary = true;
            } else if (seenBoundary && !delimFollowers.contains(words.get(i).getStr())) {
                seenBoundary = false;

                sentenceList.add(new Sentence(current_sentence));
                current_sentence.clear();
                Word w =new Word();
                w.setStr(words.get(i).getStr());
                current_sentence.add(w);
                continue;


            }
            if ( ! (wsPattern.matcher(words.get(i).getStr()).matches() ||
                    words.get(i).getStr().equals(NEWLINE_TOKEN))) {

                Word w =new Word();
                w.setStr(words.get(i).getStr());
                current_sentence.add(w);
            }
        }

        if (current_sentence.size()!= 0)
        {

            sentenceList.add(current_sentence);
        }
        return new Text(sentenceList);
    }

    public  String processToXML(List<Word> words) throws  Exception{
        Text sentenceList = process(words);
        return sentenceList.toXMLString();

    }

*/
    /*
    @Override
    public String[] process(String text) {
        Reader reader = new StringReader(text);
        DocumentPreprocessor processor= new DocumentPreprocessor(reader);
        ArrayList<String> sens=new ArrayList<>();

        for (List<HasWord> sentence : processor) {
            String[] words = new String[sentence.size()];
            Sentence se = new Sentence();
            for (int i = 0; i< words.length; i++){
                Word w = new Word();
                w.setStr(sentence.get(i).toString());
                se.add(w);
            }
            txt.add(se);
        }

        return txt.toXMLString();
    }
    */
}
