import liir.nlp.core.representation.Text;
import liir.nlp.sources.stanford.SentenceSplitter;
import liir.nlp.sources.stanford.Tokenizer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by quynhdo on 18/09/15.
 */
public class Test {

    public static void main(String[] args){
        String text= "Mary is a crazy girl. She dances every day from 1 to 8.";
        Tokenizer tk =new Tokenizer();
        SentenceSplitter ss = new SentenceSplitter();

        String[] tokens=tk.process(text);
        List<String[]> sens=ss.process(tokens);

        for (String[] s : sens)

        System.out.println( Arrays.toString(s));

        Text txt =ss.processToText(tokens);
        System.out.println(txt.toXMLString());
    }
}
