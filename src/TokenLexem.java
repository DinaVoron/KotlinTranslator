import java.util.ArrayList;

public class TokenLexem {
    Lexem lexema;
    Token token;
    ArrayList<TokenLexem> children;
    public TokenLexem(Lexem lexema_, Token token_) {
        lexema = lexema_;
        token = token_;
        children = new ArrayList<>();
    }

    public void addNewChildren(TokenLexem tl) {
        children.add(tl);
    }
    public Lexem getLexem() {
        return lexema;
    }

    public Token getToken() {
        return token;
    }
}
