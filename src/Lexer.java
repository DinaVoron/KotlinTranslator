public class Lexer {
    boolean end;
    String str;
    Integer length;
    String currChar;

    private Token token;
    private Lexem lexema;

    public void init() {}
    public Lexer(String text) {
        end = false;
        str = text;
        //position = 0;
        length = text.length();
    }

    boolean getNextLexem() {
        if (token == Token.EOF) {
            return false;
        }

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
        System.out.println("Произошла ошибка в ходе работы лексера. Неизвестный символ возле " + lexema + " ...");
        return false;
    }

    public Token getLastToken() {
        return token;
    }

    public Lexem getLastLexem() {
        return lexema;
    }
}
