public class MathLexer {
    boolean end;
    String str;
    Integer position;
    Integer length;
    String currChar;

    private Token token;
    private Lexem lexema;

    public MathLexer(String text) {
        end = false;
        str = text;
        position = 0;
        length = text.length();
    }

//    String getChar() {
//        return (position < length) ? (Character.toString(str.charAt(position))) : "\r\n";
//    }

    boolean getNextLexem() {
        /*if (token == Token.EOF) {
            return false;
        }*/
        str = str.trim();
        for (Token t : Token.values()) {
            int end = t.endOfMatch(str.toString());
            if (end != -1) {
                token = t;
                lexema = new Lexem(str.substring(0, end));
                str = str.substring(end);
                return true;
            }
        }
        return false;
    }

    public Token getLastToken() {
        return token;
    }

    public Lexem getLastLexem() {
        return lexema;
    }
}
