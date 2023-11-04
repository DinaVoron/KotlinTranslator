import java.lang.reflect.Array;
import java.util.ArrayList;

public class Testing {
    //массив для предполагемых результатов работы лексера
    ArrayList<ArrayList<TokenLexem>> tlArray;
    //массив предполагаемых строк
    ArrayList<String> strArray;

    //конструктор, где мы заполним класс нужными строками и результатами
    public Testing() {
        tlArray = new ArrayList<>();
        strArray = new ArrayList<>();
        ArrayList<TokenLexem> first = new ArrayList<>();
        ArrayList<TokenLexem> second = new ArrayList<>();
        ArrayList<TokenLexem> third = new ArrayList<>();
        ArrayList<TokenLexem> fourth = new ArrayList<>();
        ArrayList<TokenLexem> fifth = new ArrayList<>();
        ArrayList<TokenLexem> sixth = new ArrayList<>();
        ArrayList<TokenLexem> seventh = new ArrayList<>();
        ArrayList<TokenLexem> eighth = new ArrayList<>();
        ArrayList<TokenLexem> ninth = new ArrayList<>();
        ArrayList<TokenLexem> tenth = new ArrayList<>();
        ArrayList<TokenLexem> eleventh = new ArrayList<>();
        ArrayList<TokenLexem> twelfth = new ArrayList<>();
        ArrayList<TokenLexem> thirteenth = new ArrayList<>();
        ArrayList<TokenLexem> fourteenth = new ArrayList<>();
        ArrayList<TokenLexem> fifteenth = new ArrayList<>();
        ArrayList<TokenLexem> sixteenth = new ArrayList<>();
        ArrayList<TokenLexem> seventeenth = new ArrayList<>();
        ArrayList<TokenLexem> eighteenth = new ArrayList<>();
        ArrayList<TokenLexem> nineteenth = new ArrayList<>();
        ArrayList<TokenLexem> twentieth = new ArrayList<>();
        ArrayList<TokenLexem> twentyFirst = new ArrayList<>();
        ArrayList<TokenLexem> twentySecond = new ArrayList<>();
        ArrayList<TokenLexem> twentyThird = new ArrayList<>();
        ArrayList<TokenLexem> twentyFourth = new ArrayList<>();
        ArrayList<TokenLexem> twentyFifth = new ArrayList<>();
        ArrayList<TokenLexem> twentySixth = new ArrayList<>();
        ArrayList<TokenLexem> twentySeventh = new ArrayList<>();
        ArrayList<TokenLexem> twentyEighth = new ArrayList<>();
        ArrayList<TokenLexem> twentyNinth = new ArrayList<>();
        ArrayList<TokenLexem> thirtieth = new ArrayList<>();
        ArrayList<TokenLexem> thirtyFirst = new ArrayList<>();
        ArrayList<TokenLexem> thirtySecond = new ArrayList<>();
        ArrayList<TokenLexem> thirtyThird = new ArrayList<>();
        ArrayList<TokenLexem> thirtyFourth = new ArrayList<>();

        first.add(new TokenLexem(new Lexem("val"), Token.VAL));
        first.add(new TokenLexem(new Lexem("name"), Token.ID));
        first.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        first.add(new TokenLexem(new Lexem("\"MyName\""), Token.STR));
        first.add(new TokenLexem(new Lexem("®"),Token.EOL));
        first.add(new TokenLexem(new Lexem(""),Token.EOF));

        second.add(new TokenLexem(new Lexem("var"), Token.VAR));
        second.add(new TokenLexem(new Lexem("health"), Token.ID));
        second.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        second.add(new TokenLexem(new Lexem("100"), Token.NUM));
        second.add(new TokenLexem(new Lexem("®"), Token.EOL));
        second.add(new TokenLexem(new Lexem(""), Token.EOF));

        third.add(new TokenLexem(new Lexem("if"), Token.IF));
        third.add(new TokenLexem(new Lexem("("), Token.LBR));
        third.add(new TokenLexem(new Lexem("a"), Token.ID));
        third.add(new TokenLexem(new Lexem(">"), Token.MORE));
        third.add(new TokenLexem(new Lexem("b"), Token.ID));
        third.add(new TokenLexem(new Lexem(")"), Token.RBR));
        third.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        third.add(new TokenLexem(new Lexem("maxNum"), Token.ID));
        third.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        third.add(new TokenLexem(new Lexem("a"), Token.ID));
        third.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        third.add(new TokenLexem(new Lexem("else"), Token.ELSE));
        third.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        third.add(new TokenLexem(new Lexem("maxNum"), Token.ID));
        third.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        third.add(new TokenLexem(new Lexem("b"), Token.ID));
        third.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        third.add(new TokenLexem(new Lexem("®"), Token.EOL));
        third.add(new TokenLexem(new Lexem(""), Token.EOF));

        fourth.add(new TokenLexem(new Lexem("fun"), Token.FUNCTION));
        fourth.add(new TokenLexem(new Lexem("main"), Token.ID));
        fourth.add(new TokenLexem(new Lexem("("), Token.LBR));
        fourth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        fourth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        fourth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        fourth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        fourth.add(new TokenLexem(new Lexem(""), Token.EOF));

        fifth.add(new TokenLexem(new Lexem("fun"), Token.FUNCTION));
        fifth.add(new TokenLexem(new Lexem("printHello"), Token.ID));
        fifth.add(new TokenLexem(new Lexem("("), Token.LBR));
        fifth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        fifth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        fifth.add(new TokenLexem(new Lexem("println"), Token.PRINTLN));
        fifth.add(new TokenLexem(new Lexem("("), Token.LBR));
        fifth.add(new TokenLexem(new Lexem("\"Привет!\""), Token.STR));
        fifth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        fifth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        fifth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        fifth.add(new TokenLexem(new Lexem(""), Token.EOF));

        sixth.add(new TokenLexem(new Lexem("fun"), Token.FUNCTION));
        sixth.add(new TokenLexem(new Lexem("printHelloName"), Token.ID));
        sixth.add(new TokenLexem(new Lexem("("), Token.LBR));
        sixth.add(new TokenLexem(new Lexem("name"), Token.ID));
        sixth.add(new TokenLexem(new Lexem(":"), Token.COLON));
        sixth.add(new TokenLexem(new Lexem("String"), Token.STRING));
        sixth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        sixth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        sixth.add(new TokenLexem(new Lexem("println"), Token.PRINTLN));
        sixth.add(new TokenLexem(new Lexem("("), Token.LBR));
        sixth.add(new TokenLexem(new Lexem("\"Привет, это \""), Token.STR));
        sixth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        sixth.add(new TokenLexem(new Lexem("name"), Token.ID));
        sixth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        sixth.add(new TokenLexem(new Lexem("\" ?\""), Token.STR));
        sixth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        sixth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        sixth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixth.add(new TokenLexem(new Lexem(""), Token.EOF));

        seventh.add(new TokenLexem(new Lexem("fun"), Token.FUNCTION));
        seventh.add(new TokenLexem(new Lexem("sum"), Token.ID));
        seventh.add(new TokenLexem(new Lexem("("), Token.LBR));
        seventh.add(new TokenLexem(new Lexem("x"), Token.ID));
        seventh.add(new TokenLexem(new Lexem(":"), Token.COLON));
        seventh.add(new TokenLexem(new Lexem("Int"), Token.INT));
        seventh.add(new TokenLexem(new Lexem(")"), Token.RBR));
        seventh.add(new TokenLexem(new Lexem(":"), Token.COLON));
        seventh.add(new TokenLexem(new Lexem("Int"), Token.INT));
        seventh.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        seventh.add(new TokenLexem(new Lexem("return"), Token.RETURN));
        seventh.add(new TokenLexem(new Lexem("x"), Token.ID));
        seventh.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        seventh.add(new TokenLexem(new Lexem("1"), Token.NUM));
        seventh.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        seventh.add(new TokenLexem(new Lexem("®"), Token.EOL));
        seventh.add(new TokenLexem(new Lexem(""), Token.EOF));

        eighth.add(new TokenLexem(new Lexem("fun"), Token.FUNCTION));
        eighth.add(new TokenLexem(new Lexem("sum"), Token.ID));
        eighth.add(new TokenLexem(new Lexem("("), Token.LBR));
        eighth.add(new TokenLexem(new Lexem("x"), Token.ID));
        eighth.add(new TokenLexem(new Lexem(":"), Token.COLON));
        eighth.add(new TokenLexem(new Lexem("Double"), Token.DOUBLE));
        eighth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        eighth.add(new TokenLexem(new Lexem("y"), Token.ID));
        eighth.add(new TokenLexem(new Lexem(":"), Token.COLON));
        eighth.add(new TokenLexem(new Lexem("Double"), Token.DOUBLE));
        eighth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        eighth.add(new TokenLexem(new Lexem(":"), Token.COLON));
        eighth.add(new TokenLexem(new Lexem("Double"), Token.DOUBLE));
        eighth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        eighth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        eighth.add(new TokenLexem(new Lexem("x"), Token.ID));
        eighth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        eighth.add(new TokenLexem(new Lexem("x"), Token.ID));
        eighth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        eighth.add(new TokenLexem(new Lexem("y"), Token.ID));
        eighth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        eighth.add(new TokenLexem(new Lexem("return"), Token.RETURN));
        eighth.add(new TokenLexem(new Lexem("x"), Token.ID));
        eighth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        eighth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        eighth.add(new TokenLexem(new Lexem(""), Token.EOF));

        ninth.add(new TokenLexem(new Lexem("var"), Token.VAR));
        ninth.add(new TokenLexem(new Lexem("s"), Token.ID));
        ninth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        ninth.add(new TokenLexem(new Lexem("("), Token.LBR));
        ninth.add(new TokenLexem(new Lexem("a"), Token.ID));
        ninth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        ninth.add(new TokenLexem(new Lexem("b"), Token.ID));
        ninth.add(new TokenLexem(new Lexem("/"), Token.DIV));
        ninth.add(new TokenLexem(new Lexem("("), Token.LBR));
        ninth.add(new TokenLexem(new Lexem("x"), Token.ID));
        ninth.add(new TokenLexem(new Lexem("*"), Token.MUL));
        ninth.add(new TokenLexem(new Lexem("y"), Token.ID));
        ninth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        ninth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        ninth.add(new TokenLexem(new Lexem("%"), Token.MOD));
        ninth.add(new TokenLexem(new Lexem("2"), Token.NUM));
        ninth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        ninth.add(new TokenLexem(new Lexem(""), Token.EOF));

        tenth.add(new TokenLexem(new Lexem("s"), Token.ID));
        tenth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        tenth.add(new TokenLexem(new Lexem("log"), Token.LOG));
        tenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        tenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        tenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        tenth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        tenth.add(new TokenLexem(new Lexem("sin"), Token.SIN));
        tenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        tenth.add(new TokenLexem(new Lexem("cos"), Token.COS));
        tenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        tenth.add(new TokenLexem(new Lexem("y"), Token.ID));
        tenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        tenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        tenth.add(new TokenLexem(new Lexem("-"), Token.MINUS));
        tenth.add(new TokenLexem(new Lexem("tan"), Token.TAN));
        tenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        tenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        tenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        tenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        tenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        eleventh.add(new TokenLexem(new Lexem("s"), Token.ID));
        eleventh.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        eleventh.add(new TokenLexem(new Lexem("pow"), Token.POW));
        eleventh.add(new TokenLexem(new Lexem("("), Token.LBR));
        eleventh.add(new TokenLexem(new Lexem("x"), Token.ID));
        eleventh.add(new TokenLexem(new Lexem(","), Token.COMMA));
        eleventh.add(new TokenLexem(new Lexem("2"), Token.NUM));
        eleventh.add(new TokenLexem(new Lexem(")"), Token.RBR));
        eleventh.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        eleventh.add(new TokenLexem(new Lexem("exp"), Token.EXP));
        eleventh.add(new TokenLexem(new Lexem("("), Token.LBR));
        eleventh.add(new TokenLexem(new Lexem("x"), Token.ID));
        eleventh.add(new TokenLexem(new Lexem(")"), Token.RBR));
        eleventh.add(new TokenLexem(new Lexem("®"), Token.EOL));
        eleventh.add(new TokenLexem(new Lexem(""), Token.EOF));

        twelfth.add(new TokenLexem(new Lexem("s"), Token.ID));
        twelfth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        twelfth.add(new TokenLexem(new Lexem("abs"), Token.ABS));
        twelfth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twelfth.add(new TokenLexem(new Lexem("x"), Token.ID));
        twelfth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twelfth.add(new TokenLexem(new Lexem("-"), Token.MINUS));
        twelfth.add(new TokenLexem(new Lexem("ceil"), Token.CEIL));
        twelfth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twelfth.add(new TokenLexem(new Lexem("s"), Token.ID));
        twelfth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twelfth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        twelfth.add(new TokenLexem(new Lexem("floor"), Token.FLOOR));
        twelfth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twelfth.add(new TokenLexem(new Lexem("s"), Token.ID));
        twelfth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twelfth.add(new TokenLexem(new Lexem("*"), Token.MUL));
        twelfth.add(new TokenLexem(new Lexem("round"), Token.ROUND));
        twelfth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twelfth.add(new TokenLexem(new Lexem("s"), Token.ID));
        twelfth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twelfth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twelfth.add(new TokenLexem(new Lexem(""), Token.EOF));

        thirteenth.add(new TokenLexem(new Lexem("s"), Token.ID));
        thirteenth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        thirteenth.add(new TokenLexem(new Lexem("sqrt"), Token.SQRT));
        thirteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        thirteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        thirteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        thirteenth.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        thirteenth.add(new TokenLexem(new Lexem("cbrt"), Token.CBRT));
        thirteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        thirteenth.add(new TokenLexem(new Lexem("y"), Token.ID));
        thirteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        thirteenth.add(new TokenLexem(new Lexem("-"), Token.MINUS));
        thirteenth.add(new TokenLexem(new Lexem("sign"), Token.SIGN));
        thirteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        thirteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        thirteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        thirteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        thirteenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        fourteenth.add(new TokenLexem(new Lexem("s"), Token.ID));
        fourteenth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        fourteenth.add(new TokenLexem(new Lexem("min"), Token.MIN));
        fourteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        fourteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        fourteenth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        fourteenth.add(new TokenLexem(new Lexem("y"), Token.ID));
        fourteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        fourteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        fourteenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        fifteenth.add(new TokenLexem(new Lexem("s"), Token.ID));
        fifteenth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        fifteenth.add(new TokenLexem(new Lexem("max"), Token.MAX));
        fifteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        fifteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        fifteenth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        fifteenth.add(new TokenLexem(new Lexem("y"), Token.ID));
        fifteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        fifteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        fifteenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        sixteenth.add(new TokenLexem(new Lexem("when"), Token.WHEN));
        sixteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        sixteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        sixteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        sixteenth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem("1"), Token.NUM));
        sixteenth.add(new TokenLexem(new Lexem("->"), Token.CASE));
        sixteenth.add(new TokenLexem(new Lexem("print"), Token.PRINT));
        sixteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        sixteenth.add(new TokenLexem(new Lexem("\"x == 1\""), Token.STR));
        sixteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem("2"), Token.NUM));
        sixteenth.add(new TokenLexem(new Lexem("->"), Token.CASE));
        sixteenth.add(new TokenLexem(new Lexem("print"), Token.PRINT));
        sixteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        sixteenth.add(new TokenLexem(new Lexem("\"x == 2\""), Token.STR));
        sixteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem("else"), Token.ELSE));
        sixteenth.add(new TokenLexem(new Lexem("->"), Token.CASE));
        sixteenth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem("print"), Token.PRINT));
        sixteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        sixteenth.add(new TokenLexem(new Lexem("\"x is neither 1 nor 2\""), Token.STR));
        sixteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        sixteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        sixteenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        seventeenth.add(new TokenLexem(new Lexem("for"), Token.FOR));
        seventeenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        seventeenth.add(new TokenLexem(new Lexem("i"), Token.ID));
        seventeenth.add(new TokenLexem(new Lexem("in"), Token.IN));
        seventeenth.add(new TokenLexem(new Lexem("1"), Token.NUM));
        seventeenth.add(new TokenLexem(new Lexem(".."), Token.TO));
        seventeenth.add(new TokenLexem(new Lexem("3"), Token.NUM));
        seventeenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        seventeenth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        seventeenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        seventeenth.add(new TokenLexem(new Lexem("if"), Token.IF));
        seventeenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        seventeenth.add(new TokenLexem(new Lexem("i"), Token.ID));
        seventeenth.add(new TokenLexem(new Lexem("is"), Token.IS));
        seventeenth.add(new TokenLexem(new Lexem("Byte"), Token.BYTE));
        seventeenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        seventeenth.add(new TokenLexem(new Lexem("continue"), Token.CONTINUE));
        seventeenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        seventeenth.add(new TokenLexem(new Lexem("if"), Token.IF));
        seventeenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        seventeenth.add(new TokenLexem(new Lexem("i"), Token.ID));
        seventeenth.add(new TokenLexem(new Lexem("is"), Token.IS));
        seventeenth.add(new TokenLexem(new Lexem("Float"), Token.FLOAT));
        seventeenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        seventeenth.add(new TokenLexem(new Lexem("break"), Token.BREAK));
        seventeenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        seventeenth.add(new TokenLexem(new Lexem("println"), Token.PRINTLN));
        seventeenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        seventeenth.add(new TokenLexem(new Lexem("i"), Token.ID));
        seventeenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        seventeenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        seventeenth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        seventeenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        seventeenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        eighteenth.add(new TokenLexem(new Lexem("for"), Token.FOR));
        eighteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        eighteenth.add(new TokenLexem(new Lexem("item"), Token.ID));
        eighteenth.add(new TokenLexem(new Lexem("in"), Token.IN));
        eighteenth.add(new TokenLexem(new Lexem("collection"), Token.ID));
        eighteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        eighteenth.add(new TokenLexem(new Lexem("print"), Token.PRINT));
        eighteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        eighteenth.add(new TokenLexem(new Lexem("item"), Token.ID));
        eighteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        eighteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        eighteenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        nineteenth.add(new TokenLexem(new Lexem("while"), Token.WHILE));
        nineteenth.add(new TokenLexem(new Lexem("("), Token.LBR));
        nineteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        nineteenth.add(new TokenLexem(new Lexem(">"), Token.MORE));
        nineteenth.add(new TokenLexem(new Lexem("0"), Token.NUM));
        nineteenth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        nineteenth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        nineteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        nineteenth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        nineteenth.add(new TokenLexem(new Lexem("x"), Token.ID));
        nineteenth.add(new TokenLexem(new Lexem("-"), Token.MINUS));
        nineteenth.add(new TokenLexem(new Lexem("1"), Token.NUM));
        nineteenth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        nineteenth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        nineteenth.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentieth.add(new TokenLexem(new Lexem("val"), Token.VAL));
        twentieth.add(new TokenLexem(new Lexem("myArray"), Token.ID));
        twentieth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        twentieth.add(new TokenLexem(new Lexem("arrayOf"), Token.ARRAYOF));
        twentieth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentieth.add(new TokenLexem(new Lexem("1"), Token.NUM));
        twentieth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        twentieth.add(new TokenLexem(new Lexem("2"), Token.NUM));
        twentieth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        twentieth.add(new TokenLexem(new Lexem("3"), Token.NUM));
        twentieth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        twentieth.add(new TokenLexem(new Lexem("4"), Token.NUM));
        twentieth.add(new TokenLexem(new Lexem(","), Token.COMMA));
        twentieth.add(new TokenLexem(new Lexem("5"), Token.NUM));
        twentieth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentieth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentieth.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentyFirst.add(new TokenLexem(new Lexem("myArray"), Token.ID));
        twentyFirst.add(new TokenLexem(new Lexem("."), Token.POINT));
        twentyFirst.add(new TokenLexem(new Lexem("add"), Token.ADD));
        twentyFirst.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentyFirst.add(new TokenLexem(new Lexem("x"), Token.ID));
        twentyFirst.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentyFirst.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyFirst.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentySecond.add(new TokenLexem(new Lexem("myArray"), Token.ID));
        twentySecond.add(new TokenLexem(new Lexem("."), Token.POINT));
        twentySecond.add(new TokenLexem(new Lexem("remove"), Token.REMOVE));
        twentySecond.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentySecond.add(new TokenLexem(new Lexem("1"), Token.NUM));
        twentySecond.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentySecond.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentySecond.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentyThird.add(new TokenLexem(new Lexem("val"), Token.VAL));
        twentyThird.add(new TokenLexem(new Lexem("myLovelyCats"), Token.ID));
        twentyThird.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        twentyThird.add(new TokenLexem(new Lexem("listOf"), Token.LISTOF));
        twentyThird.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentyThird.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyThird.add(new TokenLexem(new Lexem("\"Мурзик\""), Token.STR));
        twentyThird.add(new TokenLexem(new Lexem(","), Token.COMMA));
        twentyThird.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyThird.add(new TokenLexem(new Lexem("\"Рыжик\""), Token.STR));
        twentyThird.add(new TokenLexem(new Lexem(","), Token.COMMA));
        twentyThird.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyThird.add(new TokenLexem(new Lexem("\"Барсик\""), Token.STR));
        twentyThird.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyThird.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentyThird.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyThird.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentyFourth.add(new TokenLexem(new Lexem("/* comment */"), Token.COMMENT));
        twentyFourth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyFourth.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentyFifth.add(new TokenLexem(new Lexem("val"), Token.VAL));
        twentyFifth.add(new TokenLexem(new Lexem("m"), Token.ID));
        twentyFifth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        twentyFifth.add(new TokenLexem(new Lexem("'m'"), Token.CHAR));
        twentyFifth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyFifth.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentySixth.add(new TokenLexem(new Lexem("if"), Token.IF));
        twentySixth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentySixth.add(new TokenLexem(new Lexem("!"), Token.NOT));
        twentySixth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentySixth.add(new TokenLexem(new Lexem("x"), Token.ID));
        twentySixth.add(new TokenLexem(new Lexem("<"), Token.LESS));
        twentySixth.add(new TokenLexem(new Lexem("5"), Token.NUM));
        twentySixth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentySixth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentySixth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        twentySixth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        twentySixth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentySixth.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentySeventh.add(new TokenLexem(new Lexem("if"), Token.IF));
        twentySeventh.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentySeventh.add(new TokenLexem(new Lexem("x"), Token.ID));
        twentySeventh.add(new TokenLexem(new Lexem("<="), Token.LESSOREQUAL));
        twentySeventh.add(new TokenLexem(new Lexem("5.9"), Token.NUM));
        twentySeventh.add(new TokenLexem(new Lexem("&&"), Token.AND));
        twentySeventh.add(new TokenLexem(new Lexem("y"), Token.ID));
        twentySeventh.add(new TokenLexem(new Lexem(">="), Token.MOREOREQUAL));
        twentySeventh.add(new TokenLexem(new Lexem("10"), Token.NUM));
        twentySeventh.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentySeventh.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        twentySeventh.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        twentySeventh.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentySeventh.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentyEighth.add(new TokenLexem(new Lexem("if"), Token.IF));
        twentyEighth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentyEighth.add(new TokenLexem(new Lexem("x"), Token.ID));
        twentyEighth.add(new TokenLexem(new Lexem("||"), Token.OR));
        twentyEighth.add(new TokenLexem(new Lexem("y"), Token.ID));
        twentyEighth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentyEighth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        twentyEighth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        twentyEighth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyEighth.add(new TokenLexem(new Lexem(""), Token.EOF));

        twentyNinth.add(new TokenLexem(new Lexem("if"), Token.IF));
        twentyNinth.add(new TokenLexem(new Lexem("("), Token.LBR));
        twentyNinth.add(new TokenLexem(new Lexem("m"), Token.ID));
        twentyNinth.add(new TokenLexem(new Lexem("=="), Token.ISEQUAL));
        twentyNinth.add(new TokenLexem(new Lexem("0"), Token.NUM));
        twentyNinth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        twentyNinth.add(new TokenLexem(new Lexem("{"), Token.LCBR));
        twentyNinth.add(new TokenLexem(new Lexem("}"), Token.RCBR));
        twentyNinth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        twentyNinth.add(new TokenLexem(new Lexem(""), Token.EOF));

        thirtieth.add(new TokenLexem(new Lexem("val"), Token.VAL));
        thirtieth.add(new TokenLexem(new Lexem("email"), Token.ID));
        thirtieth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        thirtieth.add(new TokenLexem(new Lexem("readLine"), Token.READLINE));
        thirtieth.add(new TokenLexem(new Lexem("("), Token.LBR));
        thirtieth.add(new TokenLexem(new Lexem(")"), Token.RBR));
        thirtieth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        thirtieth.add(new TokenLexem(new Lexem(""), Token.EOF));

        thirtyFirst.add(new TokenLexem(new Lexem("println"), Token.PRINTLN));
        thirtyFirst.add(new TokenLexem(new Lexem("("), Token.LBR));
        thirtyFirst.add(new TokenLexem(new Lexem("myArray"), Token.ID));
        thirtyFirst.add(new TokenLexem(new Lexem("["), Token.LSQBR));
        thirtyFirst.add(new TokenLexem(new Lexem("2"), Token.NUM));
        thirtyFirst.add(new TokenLexem(new Lexem("]"), Token.RSQBR));
        thirtyFirst.add(new TokenLexem(new Lexem(")"), Token.RBR));
        thirtyFirst.add(new TokenLexem(new Lexem("®"), Token.EOL));
        thirtyFirst.add(new TokenLexem(new Lexem(""), Token.EOF));

        thirtySecond.add(new TokenLexem(new Lexem("myLovelyCats"), Token.ID));
        thirtySecond.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        thirtySecond.add(new TokenLexem(new Lexem("myLovelyCats"), Token.ID));
        thirtySecond.add(new TokenLexem(new Lexem("+"), Token.PLUS));
        thirtySecond.add(new TokenLexem(new Lexem("\"Васька\""), Token.STR));
        thirtySecond.add(new TokenLexem(new Lexem("®"), Token.EOL));
        thirtySecond.add(new TokenLexem(new Lexem(""), Token.EOF));

        thirtyThird.add(new TokenLexem(new Lexem("myLovelyCats"), Token.ID));
        thirtyThird.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        thirtyThird.add(new TokenLexem(new Lexem("myLovelyCats"), Token.ID));
        thirtyThird.add(new TokenLexem(new Lexem("-"), Token.MINUS));
        thirtyThird.add(new TokenLexem(new Lexem("\"Васька\""), Token.STR));
        thirtyThird.add(new TokenLexem(new Lexem("®"), Token.EOL));
        thirtyThird.add(new TokenLexem(new Lexem(""), Token.EOF));

        thirtyFourth.add(new TokenLexem(new Lexem("longString"), Token.ID));
        thirtyFourth.add(new TokenLexem(new Lexem("="), Token.EQUALS));
        thirtyFourth.add(new TokenLexem(new Lexem("\"\"\"this ® is ® a ® long ® str\"\"\""), Token.RAWSTRING));
        thirtyFourth.add(new TokenLexem(new Lexem("®"), Token.EOL));
        thirtyFourth.add(new TokenLexem(new Lexem(""), Token.EOF));

        tlArray.add(first);
        tlArray.add(second);
        tlArray.add(third);
        tlArray.add(fourth);
        tlArray.add(fifth);
        tlArray.add(sixth);
        tlArray.add(seventh);
        tlArray.add(eighth);
        tlArray.add(ninth);
        tlArray.add(tenth);
        tlArray.add(eleventh);
        tlArray.add(twelfth);
        tlArray.add(thirteenth);
        tlArray.add(fourteenth);
        tlArray.add(fifteenth);
        tlArray.add(sixteenth);
        tlArray.add(seventeenth);
        tlArray.add(eighteenth);
        tlArray.add(nineteenth);
        tlArray.add(twentieth);
        tlArray.add(twentyFirst);
        tlArray.add(twentySecond);
        tlArray.add(twentyThird);
        tlArray.add(twentyFourth);
        tlArray.add(twentyFifth);
        tlArray.add(twentySixth);
        tlArray.add(twentySeventh);
        tlArray.add(twentyEighth);
        tlArray.add(twentyNinth);
        tlArray.add(thirtieth);
        tlArray.add(thirtyFirst);
        tlArray.add(thirtySecond);
        tlArray.add(thirtyThird);
        tlArray.add(thirtyFourth);

        strArray.add("val name = \"MyName\"®");
        strArray.add("var health = 100®");
        strArray.add("if (a > b) {maxNum = a} else {maxNum = b}®");
        strArray.add("fun main() { }®");
        strArray.add("fun printHello() { println(\"Привет!\") }®");
        strArray.add("fun printHelloName(name: String) { println(\"Привет, это \" + name + \" ?\") }®");
        strArray.add("fun sum(x: Int) : Int { return x+1 }®");
        strArray.add("fun sum(x: Double, y: Double) : Double {® x=x+y ® return x }®");
        strArray.add("var s = (a + b / (x*y)) % 2®");
        strArray.add("s = log(x) + sin(cos(y)) - tan(x)®");
        strArray.add("s = pow(x, 2)+exp(x)®");
        strArray.add("s = abs(x)-ceil(s) + floor(s)*round(s)®");
        strArray.add("s = sqrt(x)+cbrt(y)-sign(x)®");
        strArray.add("s = min(x, y)®");
        strArray.add("s = max(x, y)®");
        strArray.add("when (x) {® 1 -> print(\"x == 1\") ® 2 -> print(\"x == 2\") ® else -> { ® print(\"x is neither 1 nor 2\")® }® }®");
        strArray.add("for (i in 1..3) {® if (i is Byte) continue® if (i is Float) break ® println(i)® }®");
        strArray.add("for (item in collection) print(item)®");
        strArray.add("while (x > 0) { x = x-1}®");
        strArray.add("val myArray = arrayOf(1, 2, 3, 4, 5)®");
        strArray.add("myArray.add(x)®");
        strArray.add("myArray.remove(1)®");
        strArray.add("val myLovelyCats = listOf( ® \"Мурзик\", ® \"Рыжик\", ® \"Барсик\" ® )®");
        strArray.add("/* comment */®");
        strArray.add("val m = 'm'®");
        strArray.add("if (!(x < 5)) { }®");
        strArray.add("if (x <= 5.9 && y >= 10) { }®");
        strArray.add("if (x || y) { }®");
        strArray.add("if (m == 0) {}®");
        strArray.add("val email = readLine()®");
        strArray.add("println(myArray[2])®");
        strArray.add("myLovelyCats = myLovelyCats + \"Васька\"®");
        strArray.add("myLovelyCats = myLovelyCats - \"Васька\"®");
        strArray.add("longString = \"\"\"this ® is ® a ® long ® str\"\"\"®");

    }

    //функция для проверки равенства двух массивов с токенами и лексемами
    public boolean isEqual(ArrayList<TokenLexem> first, ArrayList<TokenLexem> second) {
        if (first.size() != second.size()) {
            return false;
        }
        else {
            for (int i = 0; i < first.size(); i++) {
                if (!(first.get(i).getLexem().isEqual(second.get(i).getLexem()))) {
                    return false;
                }
                if (!(first.get(i).getToken().isEqual(second.get(i).getToken()))) {
                    return false;
                }
            }
            return true;
        }
    }


    public void test() {
        for (int i = 0; i < strArray.size(); i++) {
            Lexer lexer = new Lexer(strArray.get(i));
            ArrayList<TokenLexem> result = new ArrayList<>();
            while (lexer.getNextLexem()) {
                TokenLexem tk = new TokenLexem(lexer.getLastLexem(), lexer.getLastToken());
                result.add(tk);
            }
            if (isEqual(tlArray.get(i),result)) {
                System.out.println("Test " + i + " is successful");
            }
            else {
                System.out.println("Test " + i + " is failed");
            }
        }
    }
}
