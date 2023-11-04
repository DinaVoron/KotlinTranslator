import java.util.regex.Matcher;
import java.util.regex.Pattern;
public enum Token {

        RAWSTRING("\"\"\".*\"\"\""),
        TO("\\.\\."),
        COMMENT("/\\*.*?\\*/"),
        COMMA(","),
        LBR("\\("),
        RBR("\\)"),
        BREAK("break(?!\\w+)"),
        ABS("abs(?!\\w+)"),
        CEIL("ceil(?!\\w+)"),
        FLOOR("floor(?!\\w+)"),
        MAX("max(?!\\w+)"),
        MIN("min(?!\\w+)"),
        ROUND("round(?!\\w+)"),
        SQRT("sqrt(?!\\w+)"),
        CBRT("cbrt(?!\\w+)"),
        EXP("exp(?!\\w+)"),
        LOG("log(?!\\w+)"),
        POW("pow(?!\\w+)"),
        SIGN("sign(?!\\w+)"),
        CONTINUE("continue(?!\\w+)"),
        SIN("sin(?!\\w+)"),
        COS("cos(?!\\w+)"),
        TAN("tan(?!\\w+)"),
        LCBR("\\{"),
        RCBR("\\}"),
        LSQBR("\\["),
        RSQBR("\\]"),
        COLON(":"),
        NOT("!"),
        AND("&&"),
        OR("\\|\\|"),
        NUM("([0-9]+L|[0-9]+f|[0-9]+\\.[0-9]+|[0-9]+|\\-[0-9]+L|\\-[0-9]+f|\\-[0-9]+\\.[0-9]+|-[0-9]+)"),
        IN("in(?!\\w+)"),
        PLUS("\\+"),
        CASE("->"),
        MINUS("-"),
        MUL("\\*"),
        MOD("\\%"),
        DIV("/"),
        VAR("var(?!\\w+)"),
        VAL("val(?!\\w+)"),
        ISEQUAL("=="),
        ISNOTEQUAL("!=="),
        EQUALS("="),
        IF("if(?!\\w+)"),
        ELSE ("else(?!\\w+)"),
        WHEN("when(?!\\w+)"),
        WHILE("while(?!\\w+)"),
        FOR("for(?!\\w+)"),
        MOREOREQUAL (">="),
        LESSOREQUAL("<="),
        MORE(">"),
        LESS("<"),
        READLINE("readLine(?!\\w+)"),
        IS("is(?!\\w+)"),
        FUNCTION("fun(?!\\w+)"),
        RETURN("return(?!\\w+)"),
        BYTE("Byte(?!\\w+)"),
        SHORT("Short(?!\\w+)"),
        INT("Int(?!\\w+)"),
        LONG("Long(?!\\w+)"),
        FLOAT("Float(?!\\w+)"),
        DOUBLE("Double(?!\\w+)"),
        BOOL("Boolean(?!\\w+)"),
        CHARTYPE("Char(?!\\w+)"),
        STRING("String(?!\\w+)"),
        TRUE("true(?!\\w+)"),
        FALSE("false(?!\\w+)"),
        CHAR("'.'"),
        ARRAYOF("arrayOf(?!\\w+)"),
        LISTOF("listOf(?!\\w+)"),
        POINT("\\."),
        ADD("add(?!\\w+)"),
        REMOVE("remove(?!\\w+)"),
        STR("\".*?\""),
        PRINTLN("(println)(?!\\w+)"),
        PRINT("print(?!\\w+)"),
        ID("\\w+"),
        EOL("®"),
        EOF("\\z");


        private Pattern token;

        Token(String token_) {
            token = Pattern.compile("^" + token_);
        };

        int endOfMatch(String s) {
                Matcher m = token.matcher(s);
                if (m.find()) {
                        return m.end();
                }
                return -1;
        }

        public boolean isEqual(Token token_) {
                if (this.name() != token_.name()) {
                        return false;
                }
                else {
                        return true;
                }
        }
}