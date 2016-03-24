import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import edu.jhu.nlp.wikipedia.WikiXMLParser;
import edu.jhu.nlp.wikipedia.WikiXMLParserFactory;

/**
 * Created by thanakorntrakarnvanich on 3/23/16.
 */
public class JapaneseWikiProcessor {

//    private static Tokenizer tokenizer = new Tokenizer();

    private static String rawJapWikiFile = "/Users/thanakorntrakarnvanich/Desktop/W2V/Japanese/jawiki-20160305-pages-articles-multistream.xml";

    private static String outputJapWikiFile = "/Users/thanakorntrakarnvanich/Desktop/W2V/Japanese/jawiki-20160305.txt";

    private static final String INVALID_TOKEN = "INVALID_TOKEN";

    private static final int minimumSentenceLength = 5;

    private static Pattern japCharPattern
            = Pattern.compile("[^\\u3041-\\u3096\\u30A0-\\u30FF\\u3400-\\u4DB5\\u4E00-\\u9FCB\\uF900-\\uFA6A\\u2E80-\\u2FD5\\s]");


    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            rawJapWikiFile = args[0];
            outputJapWikiFile = args[1];
        }
        parse();
    }

    private static void parse() throws IOException {
        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(rawJapWikiFile);

        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputJapWikiFile));

        try {
            wxsp.setPageCallback(new PageCallbackHandler() {
                public void process(WikiPage page) {
                    try {
                        String processedText = processDocument(page.getText());
                        bufferedWriter.write(processedText);
                        bufferedWriter.write(System.lineSeparator());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            wxsp.parse();
        }catch(Exception e) {
            e.printStackTrace();
        }

        bufferedWriter.close();
    }

    private static String processDocument(String text) throws IOException {
        text = text.replaceAll(japCharPattern.pattern(), " ");
        text = text.replaceAll("\\s+", " ");
        return text;
    }

    //    private static void writePage(String text, BufferedWriter bufferedWriter) throws IOException {
//        text = processDocument(text);
//
//        List<String> tokens = tokenize(text);
//
//        if (tokens.size() < minimumSentenceLength) {
//            return;
//        }
//
//        for (String token : tokens) {
//            bufferedWriter.write(token);
//            bufferedWriter.write(" ");
//        }
//        bufferedWriter.write(System.lineSeparator());
//    }

//    private static List<String> tokenize(String text) {
//        List<Token> tokens = tokenizer.tokenize(text);
//        List<String> processedTokens = new ArrayList<String>();
//        for (Token token : tokens) {
//            String processedToken = processToken(token.getSurface());
//            if (!processedToken.equals(INVALID_TOKEN)) {
//                processedTokens.add(processedToken);
//            }
//        }
//        return processedTokens;
//    }

    private static String processToken(String token) {
        // TODO: check if the token contains only japanese character

        return token;
    }

}
