package AIToolkit.TextMining;

import java.text.Normalizer;

/**
 * Clears the text for analysis.
 * 
 * @author Luan
 */
public class TextCleaner {

    private String text;

    private TextCleaner(String textInput) {
        this.text = textInput;
        this.text = this.text.toLowerCase();
    }

    /**
     * Creates a new text clear instance based on the given text.
     * 
     * @param inputText The text that mus be cleared.
     * @return A new instance
     */
    public static TextCleaner with(String inputText) {
        return new TextCleaner(inputText);
    }

    /**
     * Removes all accents of the words.
     * @return Current instance.
     */
    public TextCleaner stripAccents() {
        this.text = flattenToAscii(Normalizer.normalize(this.text, Normalizer.Form.NFD));
        return this;
    }

    /**
     * Removes all punctuations.
     * @return Current instance.
     */
    public TextCleaner stripPunctuation() {
        this.text = this.text.replaceAll("\\p{P}|<|>|=", " ");
        return this;
    }

    /**
     * Remove all numbers.
     * @return Current instance
     */
    public TextCleaner stripNumbers() {
        this.text = this.text.replaceAll("[0-9]", " ");
        return this;
    }

    /**
     * Remo common words that doesn't help us understand the meaning of the text.
     * 
     * @param portuguese True for portuguese dictionary.
     * @return  Current instance
     */
    public TextCleaner stripStopWords(boolean portuguese) {
        this.text = this.text.replaceAll(portuguese ? stopwords_pt : stopwords_en, " ");
        return this;
    }

    /**
     * Gets a array of words of the cleared text.
     * @return String[] of words.
     */
    public String[] getWords() {
        return text.split(" ");
    }

    /**
     * Gets the cleared text back.
     * @return String of the text.
     */
    public String getText() {
        return text;
    }

    /**
     * Removes invalid characters from the normilized version of the string when
     * removing characters.
     *
     * https://stackoverflow.com/a/15191508
     *
     * @param string
     * @return
     */
    private static String flattenToAscii(String string) {
        char[] out = new char[string.length()];
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        int j = 0;
        for (int i = 0, n = string.length(); i < n; ++i) {
            char c = string.charAt(i);
            if (c <= '\u007F') {
                out[j++] = c;
            }
        }
        return new String(out);
    }

    private static final String stopwords_en = "( a )|( about )|( above )|( after )|( again )|( against )|( all )|( am )|( an )|( and )|( any )|( are )|( aren't )|( as )|( at )|( be )|( because )|( been )|( before )|( being )|( below )|( between )|( both )|( but )|( by )|( can't )|( cannot )|( could )|( couldn't )|( did )|( didn't )|( do )|( does )|( doesn't )|( doing )|( don't )|( down )|( during )|( each )|( few )|( for )|( from )|( further )|( had )|( hadn't )|( has )|( hasn't )|( have )|( haven't )|( having )|( he )|( he'd )|( he'll )|( he's )|( her )|( here )|( here's )|( hers )|( herself )|( him )|( himself )|( his )|( how )|( how's )|( i )|( i'd )|( i'll )|( i'm )|( i've )|( if )|( in )|( into )|( is )|( isn't )|( it )|( it's )|( its )|( itself )|( let's )|( me )|( more )|( most )|( mustn't )|( my )|( myself )|( no )|( nor )|( not )|( of )|( off )|( on )|( once )|( only )|( or )|( other )|( ought )|( our )|( ours 	ourselves )|( out )|( over )|( own )|( same )|( shan't )|( she )|( she'd )|( she'll )|( she's )|( should )|( shouldn't )|( so )|( some )|( such )|( than )|( that )|( that's )|( the )|( their )|( theirs )|( them )|( themselves )|( then )|( there )|( there's )|( these )|( they )|( they'd )|( they'll )|( they're )|( they've )|( this )|( those )|( through )|( to )|( too )|( under )|( until )|( up )|( very )|( was )|( wasn't )|( we )|( we'd )|( we'll )|( we're )|( we've )|( were )|( weren't )|( what )|( what's )|( when )|( when's )|( where )|( where's )|( which )|( while )|( who )|( who's )|( whom )|( why )|( why's )|( with )|( won't )|( would )|( wouldn't )|( you )|( you'd )|( you'll )|( you're )|( you've )|( your )|( yours )|( yourself )|( yourselves )";

    private static final String stopwords_pt = "( de )|( a )|( o )|( que )|( e )|( do )|( da )|( em )|( um )|( para )|( é )|( com )|( não )|( uma )|( os )|( no )|( se )|( na )|( por )|( mais )|( as )|( dos )|( como )|( mas )|( foi )|( ao )|( ele )|( das )|( tem )|( à )|( seu )|( sua )|( ou )|( ser )|( quando )|( muito )|( há )|( nos )|( já )|( está )|( eu )|( também )|( só )|( pelo )|( pela )|( até )|( isso )|( ela )|( entre )|( era )|( depois )|( sem )|( mesmo )|( aos )|( ter )|( seus )|( quem )|( nas )|( me )|( esse )|( eles )|( estão )|( você )|( tinha )|( foram )|( essa )|( num )|( nem )|( suas )|( meu )|( às )|( minha )|( têm )|( numa )|( pelos )|( elas )|( havia )|( seja )|( qual )|( será )|( nós )|( tenho )|( lhe )|( deles )|( essas )|( esses )|( pelas )|( este )|( fosse )|( dele )|( tu )|( te )|( vocês )|( vos )|( lhes )|( meus )|( minhas )|( teu )|( tua )|( teus )|( tuas )|( nosso )|( nossa )|( nossos )|( nossas )|( dela )|( delas )|( esta )|( estes )|( estas )|( aquele )|( aquela )|( aqueles )|( aquelas )|( isto )|( aquilo )|( estou )|( está )|( estamos )|( estão )|( estive )|( esteve )|( estivemos )|( estiveram )|( estava )|( estávamos )|( estavam )|( estivera )|( estivéramos )|( esteja )|( estejamos )|( estejam )|( estivesse )|( estivéssemos )|( estivessem )|( estiver )|( estivermos )|( estiverem )|( hei )|( há )|( havemos )|( hão )|( houve )|( houvemos )|( houveram )|( houvera )|( houvéramos )|( haja )|( hajamos )|( hajam )|( houvesse )|( houvéssemos )|( houvessem )|( houver )|( houvermos )|( houverem )|( houverei )|( houverá )|( houveremos )|( houverão )|( houveria )|( houveríamos )|( houveriam )|( sou )|( somos )|( são )|( era )|( éramos )|( eram )|( fui )|( foi )|( fomos )|( foram )|( fora )|( fôramos )|( seja )|( sejamos )|( sejam )|( fosse )|( fôssemos )|( fossem )|( for )|( formos )|( forem )|( serei )|( será )|( seremos )|( serão )|( seria )|( seríamos )|( seriam )|( tenho )|( tem )|( temos )|( tém )|( tinha )|( tínhamos )|( tinham )|( tive )|( teve )|( tivemos )|( tiveram )|( tivera )|( tivéramos )|( tenha )|( tenhamos )|( tenham )|( tivesse )|( tivéssemos )|( tivessem )|( tiver )|( tivermos )|( tiverem )|( terei )|( terá )|( teremos )|( terão )|( teria )|( teríamos )|( teriam )";
}
