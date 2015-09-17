package liir.nlp.sources.stanford;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import liir.nlp.representation.Word;

/**
 * Created by quynhdo on 28/08/15.
 */
public class Tokenizer {

    public static List<Word> processs(String str){
        Reader reader = new StringReader(str);
        PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer(reader,
                new CoreLabelTokenFactory(), "");
        ArrayList<Word> arr=new ArrayList<Word>();
        while (ptbt.hasNext()) {
            CoreLabel label = ptbt.next();
            Word w =new Word();
            w.setStr(label.toString());
            arr.add(w);

        }

        return arr;
    }

    public static String processsToXML(String str){
        Reader reader = new StringReader(str);
        PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer(reader,
                new CoreLabelTokenFactory(), "");
        ArrayList<Word> arr=new ArrayList<Word>();


        StringBuilder sb=new StringBuilder();
        sb.append("<text>\n");
        sb.append("\t<sentence>\n");

        while (ptbt.hasNext()) {
            CoreLabel label = ptbt.next();
            Word w = new Word();
            w.setStr(label.toString());
            sb.append(w.toXMLString() );
            sb.append("\n");

        }

        sb.append("\t</sentence>\n");
        sb.append("<text>\n");
        return sb.toString();
    }



}
