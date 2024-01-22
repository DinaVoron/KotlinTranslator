import java.beans.Expression;
import java.lang.reflect.Array;
import java.util.*;

public class Parser {


    Lexer lexer;

    //вот он наш корень дерева
    Node program;

    SemanticAnalyzer sa = new SemanticAnalyzer();

    VarTypes vt = new VarTypes();

    public Parser(Lexer lexer){
        this.lexer = lexer;
    }

    public void parse() throws Exception {
        lexer.getNextLexem();
        if (lexer.getLastToken() == Token.EOF) {
            System.out.println("Файл пуст!");
            return;
        }
        else {
            //массив для накопления statement в нашей программе
            ArrayList<Node> inside = new ArrayList<>();
            //ArrayList<Node> statements = new ArrayList<Node>();
            while (lexer.getLastToken() != Token.EOF) {
                //Token t = lexer.getLastToken();
                //Lexem l = lexer.getLastLexem();

                //Идем ровно по грамматике
                //Получили statement
                Node expStatement = statement();


                //если не получили statement
                if (expStatement == null) {
                    System.out.println("Ошибка! Ожидалось выражение!");
                    return;
                }

                //lexer.getNextLexem();

                //проверяем наличие EOL
                Node expEOL = eol();

                //если нет конца строки
                if (expEOL == null) {
                    System.out.println("Ошибка! Ожидался конец строки!");
                    return;
                }

                inside.add(expStatement);

                inside.add(expEOL);

                expEOL = eol();
                while (expEOL != null) {
                    inside.add(expEOL);
                    expEOL = eol();
                }

                // lexer.getNextLexem();
            }
            inside.add(new Node("EOF", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            program = new Node("program", inside);
        }
    }


    public Node eol() {
        switch (lexer.getLastToken()) {
            case EOL:
                Node node =  new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                lexer.getNextLexem();
                return node;
            default:
                return null;
        }
    }

    public Node id() {
        switch (lexer.getLastToken()) {
            case ID:
                Node node =  new Node(lexer.getLastLexem().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                lexer.getNextLexem();
                return node;
            default:
                return null;
        }
    }

    public Node lsqbr() {
        switch (lexer.getLastToken()) {
            case LSQBR:
                Node node =  new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                lexer.getNextLexem();
                return node;
            default:
                return null;
        }
    }

    public Node rsqbr() {
        switch (lexer.getLastToken()) {
            case RSQBR:
                Node node =  new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                lexer.getNextLexem();
                return node;
            default:
                return null;
        }
    }

    public Node type() {
        ArrayList<Node> insideType = new ArrayList<>();
        switch (lexer.getLastToken()) {
            case BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOL, CHARTYPE, STRING:
                insideType.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                return new Node("type", insideType);
            default:
                return null;
        }
    }


    public Node simpleDeclaration() {
        ArrayList<Node> insideSimpleDeclaration = new ArrayList<>();

        Token last = lexer.getLastToken();

        switch (last) {

            case VAR, VAL:
                ArrayList<Node> inside = new ArrayList<>();
                inside.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));

                lexer.getNextLexem();
                Node expID = id();

                if (expID == null) {
                    System.out.println("Ошибка! Ожиался ID!");
                    return null;
                }

                inside.add(expID);
                if (last == Token.VAL) {
                    sa.addVal(expID.tl.getLexem().toString());
                } else {
                    sa.addVar(expID.tl.getLexem().toString());
                }

                return new Node("simple-declaration", inside);
            default:
                return null;
        }
    }

    public Node actualParam() throws Exception {
        ArrayList<Node> insideParam = new ArrayList<>();
        Node expExp = expression();
        if (expExp == null) {
            return null;
        }
        else {
            insideParam.add(expExp);
            return new Node("actual-param", insideParam);
        }
    }
    public Node actualParams() throws Exception {
        ArrayList<Node> insideParams = new ArrayList<>();
        Node expActParam = actualParam();
        if (expActParam == null) {
            return null;
        } else {
            insideParams.add(expActParam);
            if (lexer.getLastToken() == Token.COMMA) {
                insideParams.add(new Node("COMMA", new TokenLexem(lexer.getLastLexem(),lexer.getLastToken())));
                lexer.getNextLexem();
                Node expActParams = actualParams();
                if (expActParams == null) {
                    System.out.println("Ошибка! Ожидался параметр функции!");
                    return null;
                }
                insideParams.add(expActParams);
                return new Node("actual-params", insideParams);
            } else {
                return new Node("actual-params", insideParams);
            }
        }
    }


    public Node logicalSign() {
        ArrayList<Node> insideLS = new ArrayList<>();
        switch(lexer.getLastToken()) {
            case MORE, LESS, MOREOREQUAL, LESSOREQUAL, AND,  OR, ISEQUAL, ISNOTEQUAL:
                insideLS.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(),lexer.getLastToken())));
                lexer.getNextLexem();
                return new Node ("logical-sign", insideLS);
            default:
                return null;
        }
    }



    public Node term() throws Exception {
        ArrayList<Node> insideTerm = new ArrayList<>();
        //arrForCollection.add(new TokenLexem(lexer.getLastLexem(),lexer.getLastToken()));
        switch(lexer.getLastToken()) {

            case STR, CHAR, NUM, TRUE, FALSE:
                ArrayList<Node> insideValue = new ArrayList<>();
                Node smth = new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                insideValue.add(smth);
                insideTerm.add(new Node("value", insideValue));
                lexer.getNextLexem();
                return new Node("term", insideTerm);

            case MINUS:
                insideTerm.add(new Node("MINUS", new TokenLexem(lexer.getLastLexem(),lexer.getLastToken())));
                lexer.getNextLexem();
//                arrForCollection.add(new TokenLexem(lexer.getLastLexem(),lexer.getLastToken()));
//                switch (lexer.getLastToken()) {
//                    case NUM:
//                        insideTerm.add(new Node("NUM", new TokenLexem(lexer.getLastLexem(),lexer.getLastToken())));
//                        lexer.getNextLexem();
//                        return new Node("term", insideTerm);
//                    case ID:
//                        insideTerm.add(new Node("ID", new TokenLexem(lexer.getLastLexem(),lexer.getLastToken())));
//                        lexer.getNextLexem();
//                        return new Node("term", insideTerm);
//                    default:
//                        return null;
//                }

            case ID:
                if (!sa.checkVariable(lexer.getLastLexem().toString())) {
                    throw new Exception();
                }
                Node id = new Node ("ID", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                insideTerm.add(id);
                lexer.getNextLexem();

                Node expLSQBR = lsqbr();

                if (expLSQBR == null) {

                    //УЗЕЛ ФУНКЦИИ
                    if (lexer.getLastToken() == Token.LBR) {
                        insideTerm.remove(insideTerm.size() - 1);
                        ArrayList<Node> insideFunCall = new ArrayList<>();
                        insideFunCall.add(id);
                        insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        Node expActParam = actualParams();
                        if (expActParam != null) {
                            insideFunCall.add(expActParam);
                        }
                        if (lexer.getLastToken() == Token.RBR) {
                            insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                            lexer.getNextLexem();

                            String name = id.tl.getLexem().toString();
                            ArrayList<String> fc = new ArrayList<>();

                            for (int i = 0; i < actualParam().childrenNode.size(); i++) {
                                if (actualParam().childrenNode.get(i).name.equals("actual-param")) {
                                    if (actualParam().childrenNode.get(i).childrenNode.get(0).childrenNode.get(0).name.equals("value")) {
                                        Token param = actualParam().childrenNode.get(i).childrenNode.get(0).childrenNode.get(0).childrenNode.get(0).tl.getToken();
                                        switch (param) {
                                            case NUM:
                                                fc.add("num");
                                                break;
                                            case STR:
                                                fc.add("str");
                                                break;
                                            case TRUE, FALSE:
                                                fc.add("bool");
                                                break;
                                            case CHARTYPE:
                                                fc.add("char");
                                                break;
                                        }
                                    }
                                } //вот тут вот добавить про id в будущем
                            }

                            //! System.out.println(sa.checkFun(name, fc));

                            Node funCallNode = new Node("function-call", insideFunCall);
                            insideTerm.add(funCallNode);
                            return new Node("term", insideTerm);
                        }
                    }
                    else {
                        return new Node("term", insideTerm);
                    }

                } else {
                    insideTerm.add(expLSQBR);
                    if (lexer.getLastToken() == Token.NUM) {
                        insideTerm.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        Node expRSQBR = rsqbr();

                        if (expRSQBR == null) {
                            System.out.println("Ошибка! Ожидали правую квадратную скобку");
                            return null;
                        }
                        else {
                            insideTerm.add(expRSQBR);
                            return new Node("term", insideTerm);
                        }

                    } else {
                        System.out.println("Ошибка! Ожидали число");
                        return null;
                    }
                }
            default:
                return null;
        }
    }

    public Node sign() {
        switch (lexer.getLastToken()) {
            case PLUS, MINUS, MOD, DIV, MUL:
                ArrayList<Node> insideSign = new ArrayList<>();
                insideSign.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                Node node =  new Node("sign", insideSign);
                lexer.getNextLexem();
                return node;
            default:
                return null;
        }
    }


    public Node expressionInside_() throws Exception {
        ArrayList<Node> insideExpressionInside_ = new ArrayList<>();

        Node expSign = sign();
        if (expSign == null) {

            Node expLogSign = logicalSign();
            if (expLogSign == null) {
                //System.out.println("Ошибка! Ожидался знак!");
                return null;
            }
            insideExpressionInside_.add(expLogSign);
            Node expTerm = logicalExpression();
            if (expTerm == null) {
                System.out.println("Ошибка! Ожидалось выражение!");
                return null;
            }
            insideExpressionInside_.add(expTerm);
            //Node expExpIns = expressionInside_();
            return new Node("logical-expression'", insideExpressionInside_);
        }
        else {
            insideExpressionInside_.add(expSign);
            //!lexer.getNextLexem();
            Node expTerm = expression();
            if (expTerm == null) {
                System.out.println("Ошибка! Ожидалось выражение!");
                return null;
            }
            insideExpressionInside_.add(expTerm);
            Node expExpIns = expressionInside_();
            if (expExpIns == null) {
                return new Node("expression-inside'", insideExpressionInside_);
            }
            insideExpressionInside_.add(expExpIns);
            //lexer.getNextLexem(); //!
            return new Node("expression-inside'", insideExpressionInside_);
        }
    }

    public Node logicalExpression() throws Exception {
        ArrayList<Node> insideLogExpression = new ArrayList<>();
        switch (lexer.getLastToken()) {
            case NOT:
                insideLogExpression.add(new Node("NOT", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expTerm = logicalExpression();
                if (expTerm == null) {
                    System.out.println("Ошибка! Ожидался терм");
                    return null;
                }
                insideLogExpression.add(expTerm);
                return new Node("logical-expression", insideLogExpression);
            case LBR:
                insideLogExpression.add(new Node("LBR", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expLogExp = logicalExpression();
                if (expLogExp == null) {
                    System.out.println("Ошибка! Ожидалось выражение!");
                }
                insideLogExpression.add(expLogExp);
                if (lexer.getLastToken() == Token.RBR) {
                    insideLogExpression.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    return new Node("logical-expression", insideLogExpression);
                } else {
                    System.out.println("Ошибка! Ожидалась правая скобка!");
                    return null;
                }
            default:
                Node expTermDefault = term();
                if (expTermDefault == null) {
                    System.out.println("Ошибка! Ожидался терм");
                    return null;
                }
                insideLogExpression.add(expTermDefault);

                Node expLogSign = logicalSign();
                if (expLogSign == null) {

                    if (lexer.getLastToken() == Token.IS) {

                        insideLogExpression.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        Node expType = type();

                        if (expType == null) {
                            System.out.println("Ошибка! Ожидался тип");
                            return null;
                        }
                        else {
                            insideLogExpression.add(expType);
                            return new Node ("logical-expression", insideLogExpression);
                        }
                    }
                    //ничего страшного, вернем узел с термом
                    return expTermDefault;
                }
                insideLogExpression.add(expLogSign);
                Node expLogExp1 = logicalExpression();
                if (expLogExp1 == null) {
                    System.out.println("Ожидалась правая часть выражения");
                    return null;
                }
                insideLogExpression.add(expLogExp1);
                return new Node("logical-expression", insideLogExpression);
            //insideExpression.add(expInsExp1);
            //return new Node("expression", insideExpression);
        }
    }
    public Node expression_inside() throws Exception {

        ArrayList<Node> insideExpressionInside = new ArrayList<>();

        switch (lexer.getLastToken()) {
            case LBR:
                insideExpressionInside.add(new Node("LBR", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expExpIns = expression_inside();
                if (expExpIns == null) {
                    System.out.println("Ошибка! Ожидалось выражение!");
                    return null;
                }
                else {
                    insideExpressionInside.add(expExpIns);
                    if (expExpIns.name == "logical-expression") {
                        if (lexer.getLastToken() != Token.RBR) {
                            System.out.println("Ошибка! Ожидалась скобка!");
                            return null;
                        }
                        else {
                            insideExpressionInside.add(new Node("RBR", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                            lexer.getNextLexem();
                            return new Node("logical-expression", insideExpressionInside);
                        }
                    }
                    //lexer.getNextLexem();
                    if (lexer.getLastToken() != Token.RBR) {
                        System.out.println("Ошибка! Ожидалась скобка!");
                        return null;
                    }
                    else {
                        Node expExpInside_1 = expressionInside_();
                        if (expExpInside_1 == null) {
                            insideExpressionInside.add(new Node("RBR", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                            lexer.getNextLexem();
                            return new Node("expression-inside", insideExpressionInside);
                        }
                        else {
                            insideExpressionInside.add(expExpInside_1);
                            return new Node("expression-inside", insideExpressionInside);
                        }
                    }
                }
            case NOT:
                insideExpressionInside.add(new Node("NOT", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));

                //давайте вот тут просить логическое выражение
                Node expTermAfterNot = logicalExpression();
                if (expTermAfterNot == null) {
                    System.out.println("Ошибка! Ожидался терм!");
                    return null;
                }
                insideExpressionInside.add(expTermAfterNot);
                return new Node("logical-expression", insideExpressionInside);
            default:
                Node expTerm = term();
                if (expTerm == null) {
                    //System.out.println("Ошибка! Ожидался терм!");
                    return null;
                }
                //lexer.getNextLexem();
                Node expSign = sign();

                if (expSign == null) {
                    // System.out.println("Ошибка! Ожидался знак!");
                    Node expLogSign = logicalSign();
                    if (expLogSign == null) {
                        return expTerm;
                    } else {
                        insideExpressionInside.add(expTerm);
                        insideExpressionInside.add(expLogSign);
                        Node expLogExp = logicalExpression();
                        if (expLogExp == null) {
                            System.out.println("Ошибка! Ожидалась вторая часть логического выражения!");
                            return null;
                        } else {
                            insideExpressionInside.add(expLogExp);
                            return new Node("logical-expression", insideExpressionInside);
//                            ArrayList<Node> insideLogExpr = new ArrayList<>();
//                            insideLogExpr.add(logicalNode);
//                            return new Node("expression", insideLogExpr);
                        }
                    }
                }
                insideExpressionInside.add(expTerm);
                insideExpressionInside.add(expSign);

                //lexer.getNextLexem();
                Node expExp = expression_inside();

                if (expExp == null) {
                    System.out.println("Ошибка! Ожидалась правая часть!");
                    return null;
                }

                if (expExp.childrenNode.get(0).name != "LBR") {
                    if (expExp.name == "term") insideExpressionInside.add(expExp);
                    else {
                        insideExpressionInside.add(expExp.childrenNode.remove(0));
                        if (expExp.childrenNode.size() != 0) {
                            expExp.name = "expression-inside'";
                            insideExpressionInside.add(expExp);
                        }
                    }

                }
                else insideExpressionInside.add(expExp);


                Node expExpIns_ = expressionInside_();

                if (expExpIns_ == null) {
                    return new Node("expression-inside", insideExpressionInside);
                }
                else {
                    //lexer.getNextLexem();
                    insideExpressionInside.add(expExpIns_);
                    return new Node("expression-inside", insideExpressionInside);
                }

        }
        //return null;
    }

    public Node logicalExpression_() throws Exception {
        ArrayList<Node> insideLogExpression_ = new ArrayList<>();
        Node expSign = logicalSign();
        if (expSign == null) {
            return null;
        }
        insideLogExpression_.add(expSign);
        Node expLogExp = logicalExpression();
        if (expLogExp == null) {
            System.out.println("Ошибка! Ожидалась вторая часть логического выражения!");
            return null;
        }
        insideLogExpression_.add(expLogExp);
        return new Node("logical-expression'", insideLogExpression_);
    }


    public Node expression() throws Exception {

        ArrayList<Node> insideExpression = new ArrayList<>();
        switch (lexer.getLastToken()) {
            case LBR:
                insideExpression.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expInsExp = expression_inside();
                if (expInsExp.name == "logical-expression") {

                    if (lexer.getLastToken() == Token.RBR) {
                        insideExpression.add(expInsExp);
                        insideExpression.add(new Node("RBR", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        Node expExpIns_ = expressionInside_();
                        if (expExpIns_ == null) {
                            return new Node("expression", insideExpression);
                        }
                        insideExpression.add(expExpIns_);
                        return new Node("expression", insideExpression);
                    } else {
                        System.out.println("Ошибка! Ожидалась правая скобка!");
                        return null;
                    }

                }

                if (expInsExp == null) {
                    System.out.println("Ошибка! Ожидалось выражение!");
                    return null;
                }
                insideExpression.add(expInsExp);

                if (lexer.getLastToken() == Token.RBR) {
                    insideExpression.add(new Node("RBR", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    Node expExpIns_ = expressionInside_();
                    if (expExpIns_ == null) {
                        return new Node("expression", insideExpression);
                    }
                    insideExpression.add(expExpIns_);
                    return new Node("expression", insideExpression);
                } else {
                    System.out.println("Ошибка! Ожидалась правая скобка!");
                    return null;
                }
            case NOT:
                insideExpression.add(new Node("NOT", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
//!скорее всего, нужно просить logical-expression
                if (lexer.getLastToken() == Token.LBR) {
                    Node expTerm = logicalExpression();
                    if (expTerm == null) {
                        System.out.println("Ошибка! Ожидался терм!");
                    }
                    insideExpression.add(expTerm);
                    Node logExp = new Node("logical-expression", insideExpression);
                    ArrayList<Node> arrLog = new ArrayList<>();
                    arrLog.add(logExp);

                    Node expLogExp_ = logicalExpression_();
                    if (expLogExp_ == null) {
                        return new Node("expression", arrLog);
                    }

                    insideExpression.add(expLogExp_);
                    return new Node("expression", arrLog);
                } else {
                    Node expTerm = term();
                    if (expTerm == null) {
                        System.out.println("Ошибка! Ожидался терм!");
                    }
                    insideExpression.add(expTerm);
                    Node expLogExp_ = logicalExpression_();
                    if (expLogExp_ == null) {
                        Node logExp = new Node("logical-expression", insideExpression);
                        ArrayList<Node> arrL = new ArrayList<>();
                        arrL.add(logExp);
                        return new Node("expression", arrL);
                    }
                    insideExpression.add(expLogExp_);
                    Node logExp = new Node("logical-expression", insideExpression);
                    ArrayList<Node> arrL = new ArrayList<>();
                    arrL.add(logExp);
                    return new Node("expression", arrL);
                }
            default:
                Node expInsExp1 = expression_inside();

                if (expInsExp1 == null) {
// System.out.println("Ошибка! Ожидалось выражение!");
                    return null;
                }

                insideExpression.add(expInsExp1);

                Node expLogExp_ = logicalExpression_();

                if (expLogExp_ != null) {
                    insideExpression.add(expLogExp_);
                }

                if (expInsExp1.name == "term" && expLogExp_ == null) {
                    return expInsExp1;
                }

                return new Node("expression", insideExpression);
        }
    }

    public void addCheckFun(Node node, ArrayList<String> arr) {
        if (!(node == null)) {
            for (int i = 0; i < node.childrenNode.size(); i++) {
                switch (node.childrenNode.get(i).name) {
                    case "actual-param":
                        switch(node.childrenNode.get(i).childrenNode.get(0).childrenNode.get(0).childrenNode.get(0).tl.getToken()) {
                            case NUM:
                                arr.add("num");
                                break;
                            case STR:
                                arr.add("str");
                                break;
                            case TRUE, FALSE:
                                arr.add("bool");
                                break;
                            case CHAR:
                                arr.add("char");
                                break;
                        }
                        break;
                    case "actual-params":
                        addCheckFun(node.childrenNode.get(i), arr);
                        break;
                }
            }
        }
    }


    public Node function_call() throws Exception {
        ArrayList<Node> insideFunCall = new ArrayList<>();
        String id = "";
        switch(lexer.getLastToken()) {
            case ID:
                id = lexer.getLastLexem().toString();
                insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                break;
            default:
                insideFunCall.add(new Node("ID", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                break;
        }

        lexer.getNextLexem();
        switch (lexer.getLastToken()) {
            case LBR:
                insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expActParams = actualParams();
                if (expActParams != null) {
                    insideFunCall.add(expActParams);
                }
                if (lexer.getLastToken() == Token.RBR) {
                    insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();

                    ArrayList<String> fc = new ArrayList<>();

                    if (expActParams != null) {
                        for (int i = 0; i < expActParams.childrenNode.size(); i++) {
                            if (expActParams.childrenNode.get(i).name.equals("actual-param")) {
                                if (expActParams.childrenNode.get(i).childrenNode.get(0).childrenNode.get(0).name.equals("value")) {
                                    Token param = expActParams.childrenNode.get(i).childrenNode.get(0).childrenNode.get(0).childrenNode.get(0).tl.getToken();
                                    switch (param) {
                                        case NUM:
                                            fc.add("num");
                                            break;
                                        case STR:
                                            fc.add("str");
                                            break;
                                        case TRUE, FALSE:
                                            fc.add("bool");
                                            break;
                                        case CHAR:
                                            fc.add("char");
                                            break;
                                    }
                                }
                            } else {
                                if (expActParams.childrenNode.get(i).name.equals("actual-params")) {
                                    addCheckFun(expActParams.childrenNode.get(i), fc);
                                }
                            }
                        }
                    }

                    if (!sa.checkFun(id, fc)) {
                        System.out.println("Ошибка! Функция вызвана не с теми параметрами!");
                        return null;
                    }

                    return new Node("function-call", insideFunCall);
                } else {
                    System.out.println("Ошибка! Ожидали правую скобку");
                    return null;
                }
            case POINT:
                if (!sa.checkVariable(id)) {
                    throw new Exception();
                }
                insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                if (lexer.getLastToken() == Token.ADD) {
                    insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    if (lexer.getLastToken() == Token.LBR) {
                        insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                    } else {
                        System.out.println("Ошибка! Ожидали левую скобку");
                        return null;
                    }
                    Node expValue = value();
                    if (expValue == null) {
                        System.out.println("Ошибка! Ожидали значение");
                        return null;
                    } else {
                        insideFunCall.add(expValue);
                    }
                } else if (lexer.getLastToken() == Token.REMOVE) {
                    insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    if (lexer.getLastToken() == Token.LBR) {
                        insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                    } else {
                        System.out.println("Ошибка! Ожидали левую скобку");
                        return null;
                    }
                    if (lexer.getLastToken() == Token.NUM) {
                        insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                    } else {
                        System.out.println("Ошибка! Ожидали число");
                        return null;
                    }
                } else {
                    System.out.println("Ошибка! Ожидали метод массива");
                    return null;
                }
                if (lexer.getLastToken() == Token.RBR) {
                    insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    return new Node("array-statement", insideFunCall);
                } else {
                    System.out.println("Ошибка! Ожидали правую скобку");
                    return null;
                }
            case EQUALS:
                if (!sa.checkVariable(id)) {
                    throw new Exception();
                }
                if (!sa.checkVal(id)) {
                    throw new Exception();
                }
                insideFunCall.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expExp = expression();
                if (expExp == null) {
                    System.out.println("Ошибка! Ожидалось выражение");
                    return null;
                }
                Node exp = expExp;
                addTypes(exp,id);
                insideFunCall.add(expExp);
                return new Node("assigning", insideFunCall);
            default:
                return null;
        }
    }


    public Node value() throws Exception {
        ArrayList<Node> insideValue = new ArrayList<>();
        switch(lexer.getLastToken()) {
            case MINUS:
                insideValue.add(new Node("MINUS", new TokenLexem(lexer.getLastLexem(),lexer.getLastToken())));
                lexer.getNextLexem();
            case STR, CHAR, NUM, TRUE, FALSE:
                if (insideValue.size() != 0 && lexer.getLastToken() != Token.NUM) {
                    System.out.println("Ошибка! Ожидали число");
                    return null;
                }
                Node smth = new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken()));
                insideValue.add(smth);
                lexer.getNextLexem();
                return new Node("value", insideValue);
            default:
                return null;
        }
    }

    public Node function_command() throws Exception {
        Node expFunCom = statement();
        if (expFunCom != null) {
            expFunCom = expFunCom.childrenNode.get(0);
            if (expFunCom.name == "function-construction") {
                System.out.println("Ошибка! Не ожидали");
                return null;
            }
        }
        else return null;
        ArrayList<Node> insideFunCom = new ArrayList<>();
        insideFunCom.add(expFunCom);
        return new Node("function-command", insideFunCom);
    }

    public Node function_block() throws Exception {
        sa.addLevel();
        ArrayList<Node> insideFunBlock = new ArrayList<>();
        switch (lexer.getLastToken()) {
            case EOL:
                insideFunBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expFunBlock = function_block();
                if (expFunBlock != null) {
                    insideFunBlock.add(expFunBlock);
                }
                if (insideFunBlock == null) return null;
                sa.removeLevel();
                return new Node("function-block", insideFunBlock);
            default:
                if (lexer.getLastToken() != Token.RCBR) {
                    Node expFunCom = function_command();
                    if (expFunCom == null) {
                        return null;
                    }
                    insideFunBlock.add(expFunCom);

                    Node expFunBlock1 = function_block();
                    if (expFunBlock1 != null) {
                        insideFunBlock.add(expFunBlock1);
                    }
                }
                sa.removeLevel();
                return new Node("function-block", insideFunBlock);
        }
    }

    public Node while_() throws Exception {
        ArrayList<Node> insideWhile = new ArrayList<>();
        if (lexer.getLastToken() == Token.WHILE) {
            insideWhile.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            if (lexer.getLastToken() == Token.LBR) {
                insideWhile.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expCon = logicalExpression();
                if (expCon != null) {
                    ArrayList<Node> inIfCon = new ArrayList<>();
                    inIfCon.add(expCon);
                    Node ifCon = new Node("if-condition", inIfCon);
                    insideWhile.add(ifCon);
                    //lexer.getNextLexem();
                    if (lexer.getLastToken() == Token.RBR) {
                        insideWhile.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        Node expEOL = eol();
                        if (expEOL != null) {
                            insideWhile.add(expEOL);
                        }
                        if (lexer.getLastToken() == Token.LCBR) {
                            Node expEOL1 = eol();
                            if (expEOL1 != null) {
                                insideWhile.add(expEOL1);
                            }
                            insideWhile.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                            lexer.getNextLexem();
                            Node expFunBlock = function_block();
                            if (expFunBlock != null) {
                                if (lexer.getLastToken() == Token.RCBR) {
                                    insideWhile.add(expFunBlock);
                                    insideWhile.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                                    lexer.getNextLexem();
                                    insideWhile.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                                    return new Node("while-construction", insideWhile);
                                } else {
                                    System.out.println("Ошибка! Ожидалась правая фигурная скобка!");
                                    return null;
                                }
                            } else {
                                System.out.println("Ошибка! Ожидалось выражение!");
                                return null;
                            }
                        } else {
                            System.out.println("Ошибка! Ожидалась фигурная скобка!");
                            return null;
                        }
                    } else {
                        System.out.println("Ошибка! Ожидалась правая скобка!");
                        return null;
                    }
                } else {
                    System.out.println("Ошибка! Ожидалось условие цикла!");
                    return null;
                }
            } else {
                System.out.println("Ошибка! Ожидалась левая скобка!");
                return null;
            }
        }
        else {
            return null;
        }
    }

    public boolean isTerm(Node n) {
        if (n.name == "term" || n.name == "value" || n.name == "function-call") return true;
        return false;
    }

    public Node return_() throws Exception {
        ArrayList<Node> insideReturn = new ArrayList<>();
        if (lexer.getLastToken() == Token.RETURN) {
            insideReturn.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            Node expTerm = expression();
            if (expTerm != null) {
                insideReturn.add(expTerm);
                return new Node("return-statement", insideReturn);

            } else {
                System.out.println("Ошибка! Ожидался терм!");
                return null;
            }
        } else {
            System.out.println("Ошибка! Ожидался возврат!");
            return null;
        }
    }

    public Node param() throws Exception {
        ArrayList<Node> insideParam = new ArrayList<>();
        Node expID = id();
        sa.addVar(expID.name);
        if (lexer.getLastToken() == Token.COLON) {
            insideParam.add(expID);
            insideParam.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            Node expType = type();
            if (expType != null) {
                insideParam.add(expType);
                return new Node ("param", insideParam);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Node params() throws Exception {
        ArrayList<Node> insideParams = new ArrayList<>();
        Node expParam = param();
        if (expParam == null) {
            return null;
        }
        insideParams.add(expParam);
        if (lexer.getLastToken() == Token.COMMA) {
            insideParams.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            Node expParams = params();
            if (expParams != null) {
                insideParams.add(expParams);
                return new Node("params", insideParams);
            } else {
                System.out.println("Ошибка! Ожидался следующий параметр функции!");
                return null;
            }
        }
        return new Node ("params", insideParams);
    }

    public void findType(Node node, ArrayList<String> par, String id) {
        for (int i = 0; i < node.childrenNode.size(); i++) {
            switch(node.childrenNode.get(i).name) {
                case "param":
                    Vars v = new Vars(node.childrenNode.get(i).childrenNode.get(0).tl.getLexem().toString());
                    v.type = node.childrenNode.get(i).childrenNode.get(2).childrenNode.get(0).name;
                    //System.out.println(v.name+" "+v.type+"\n");
                    vt.addFunVar(id, v);
                    switch (node.childrenNode.get(i).childrenNode.get(2).childrenNode.get(0).name) {
                        case "STRING":
                            par.add("str");
                            break;
                        case "INT", "FLOAT":
                            par.add("num");
                            break;
                        case "BOOL":
                            par.add("bool");
                            break;
                        case "CHARTYPE":
                            par.add("char");
                            break;
                        default:
                            break;
                    }
                    break;
                case "params":
                    findType(node.childrenNode.get(i).childrenNode.get(0), par, id);
                    break;
                default:
                    break;
            }
        }
    }
    public Node function() throws Exception {
        ArrayList<Node> insideFunction = new ArrayList<>();
        if (lexer.getLastToken() == Token.FUNCTION) {
            lexer.getNextLexem();
            if (lexer.getLastToken() == Token.ID) {
                insideFunction.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                String id = lexer.getLastLexem().toString();
                vt.addFun(id);
                lexer.getNextLexem();
                if (lexer.getLastToken() == Token.LBR) {
                    insideFunction.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    Node expParams = params();
                    if (expParams != null) {
                        insideFunction.add(expParams);
                    }
                    if (lexer.getLastToken() == Token.RBR) {
                        insideFunction.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        Node expEol = eol();
                        if (expEol != null) {
                            insideFunction.add(expEol);
                        }
                        if (lexer.getLastToken() == Token.COLON) {
                            insideFunction.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                            lexer.getNextLexem();
                            Node type = type();
                            if (type != null) {
                                vt.addFunType(id, type.childrenNode.get(0).tl.getLexem().toString());
                                insideFunction.add(type);
                            } else {
                                System.out.println("Ошибка! Ожидался тип!");
                                return null;
                            }
                        }
                        ArrayList<String> par = new ArrayList<>();
                        if (expParams != null) {
                            ArrayList<Node> inUse = expParams.childrenNode;
                            for (int i = 0; i < inUse.size(); i++) {
                                switch(inUse.get(i).name) {
                                    case "param":
                                        Vars v = new Vars(expParams.childrenNode.get(i).childrenNode.get(0).tl.getLexem().toString());
                                        v.type = expParams.childrenNode.get(i).childrenNode.get(2).childrenNode.get(0).name;
                                        //System.out.println(v.name+" "+v.type+"\n");
                                        vt.addFunVar(id, v);
                                        switch (expParams.childrenNode.get(i).childrenNode.get(2).childrenNode.get(0).name) {
                                            case "STRING":
                                                par.add("str");
                                                break;
                                            case "INT", "FLOAT":
                                                par.add("num");
                                                break;
                                            case "BOOL":
                                                par.add("bool");
                                                break;
                                            case "CHARTYPE":
                                                par.add("char");
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                    case "params":
                                        findType(inUse.get(i), par, id);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        sa.addFun(id, par); //0
                        sa.addLevel();

//                        if (expParams != null) {
//                            for (int i = 0; i < expParams.childrenNode.size(); i++) {
//                                switch(expParams.childrenNode.get(i).childrenNode.get(0).name) {
//                                    case "param":
//                                        sa.addVar(expParams.childrenNode.get(i).childrenNode.get(0).childrenNode.get(0).name);
//                                    default:
//                                        break;
//                                }
//                            }
//                        }


                        if (lexer.getLastToken() == Token.LCBR) {
                            insideFunction.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));

                            lexer.getNextLexem();
                            Node expFunBlock = function_block();
                            if (expFunBlock != null) {
                                insideFunction.add(expFunBlock);
                            }
                            if (lexer.getLastToken() == Token.RCBR) {
                                insideFunction.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                                lexer.getNextLexem();

                                sa.removeLevel();

                                return new Node("function-construction", insideFunction);
                            } else {
                                System.out.println("Ошибка! Ожидалась фигурная скобка!");
                                return null;
                            }
                        } else {
                            System.out.println("Ошибка! Ожидалась фигурная скобка!");
                            return null;
                        }
                    } else {
                        System.out.println("Ошибка! Ожидалась правая скобка!");
                        return null;
                    }
                } else {
                    System.out.println("Ошибка! Ожидалась левая скобка!");
                    return null;
                }
            } else {
                System.out.println("Ошибка! Ожидалось название функции!");
                return null;
            }
        } else {
            System.out.println("Ошибка! Ожидалось объявление функции!");
            return null;
        }
    }

    public Node switchCommand() throws Exception {
        ArrayList<Node> insSwicthBlock = new ArrayList<>();
        if (lexer.getLastToken() == Token.ELSE) {
            insSwicthBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
        }
        else {
            Node expValue = value();
            if (expValue == null) {
                // System.out.println("Ошибка! Ожидалось значение!");
                return null;
            }
            insSwicthBlock.add(expValue);
        }
        if (lexer.getLastToken() == null) {
            System.out.println("Ошибка! Ожидалось CASE !");
            return null;
        }
        insSwicthBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
        lexer.getNextLexem();
        boolean inLCBR = false;
        if (lexer.getLastToken() == Token.LCBR) {
            inLCBR = true;
            insSwicthBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
        }
        Node expFunBlock = null;
        if (!inLCBR) {
            expFunBlock = function_command();
            if (expFunBlock == null) {
                System.out.println("Ошибка! Ожидался блок функции!");
                return null;
            }
            insSwicthBlock.add(expFunBlock);
            return new Node("switch-command", insSwicthBlock);
        }
        expFunBlock = function_block();
        if (expFunBlock != null) {
            insSwicthBlock.add(expFunBlock);
        }
        if (lexer.getLastToken() != Token.RCBR) {
            System.out.println("Ошибка! Ожидалась правая скобка!");
            return null;
        }
        insSwicthBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
        lexer.getNextLexem();
        return new Node("switch-command", insSwicthBlock);
    }

    public Node switchBlock() throws Exception {
        ArrayList<Node> insSwicthBlock = new ArrayList<>();
        if (lexer.getLastToken() == Token.EOL) {
            insSwicthBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
        }
        Node nodeSw = switchCommand();
        if (nodeSw == null) {
            // System.out.println("Ошибка! Ожидалась switch-command !");
            return null;
        }
        insSwicthBlock.add(nodeSw);
        Node expSB = null;
        if (lexer.getLastToken() == Token.EOL) {
            insSwicthBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            expSB = switchBlock();
        }
        if (expSB != null) {
            insSwicthBlock.add(expSB);
        }
        return new Node("switch-block", insSwicthBlock);
    }


    public Node switchExp() throws Exception {
        ArrayList<Node> insSwicthExp = new ArrayList<>();
        if (lexer.getLastToken() == Token.WHEN) {
            insSwicthExp.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();

            if (lexer.getLastToken() == Token.LBR) {
                insSwicthExp.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                if (lexer.getLastToken() == Token.ID) {
                    if (!sa.checkVariable(lexer.getLastLexem().toString())) {
                        throw new Exception();
                    }
                    insSwicthExp.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    if (lexer.getLastToken() == Token.RBR) {
                        insSwicthExp.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                        lexer.getNextLexem();
                        if (lexer.getLastToken() == Token.LCBR) {
                            insSwicthExp.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                            lexer.getNextLexem();
                            Node expSwitchBlock = switchBlock();
                            if (expSwitchBlock != null) {
                                insSwicthExp.add(expSwitchBlock);
                                if (lexer.getLastToken() == Token.RCBR) {
                                    insSwicthExp.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                                    lexer.getNextLexem();
                                    return new Node("switch-statement", insSwicthExp);
                                }
                            } else {
                                System.out.println("Ошибка! Ожидалось выражение!");
                                return null;
                            }
                        } else {
                            System.out.println("Ошибка! Ожидалась фигурная скобка!");
                            return null;
                        }
                    } else {
                        System.out.println("Ошибка! Ожидалась скобка!");
                        return null;
                    }
                } else {
                    System.out.println("Ошибка! Ожидался терм!");
                    return null;
                }
            } else {
                System.out.println("Ошибка! Ожидалась скобка!");
                return null;
            }
        } else {
            System.out.println("Ошибка! Ожидалось when!");
            return null;
        }
        // WHEN LBR ID RBR LCBR switch-block RCBR
        return null;
    }

    public Node forCommand() throws Exception {
        ArrayList<Node> insForCom = new ArrayList<>();
        Node expSt = statement();
        if (expSt == null) {
            switch (lexer.getLastToken()) {
                case CONTINUE, BREAK:
                    insForCom.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    return new Node("for-command", insForCom);
                default:
                    return null;
            }
        } else {
            if (expSt.name == "function-construction") {
                return null;
            }
            insForCom.add(expSt.childrenNode.get(0));
            return new Node("for-command", insForCom);
        }
    }
    public Node forBlock() throws Exception {
        ArrayList<Node> insideForBlock = new ArrayList<>();
        if (lexer.getLastToken() == Token.EOL) {
            insideForBlock.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            Node expForBlock = forBlock();
            if (expForBlock != null) {
                insideForBlock.add(expForBlock);
            }
            return new Node("for-block", insideForBlock);
        } else {

            Node expForCom = forCommand();
            if (expForCom != null) {
                insideForBlock.add(expForCom);
                Node expBlock = forBlock();
                if (expBlock != null) {
                    insideForBlock.add(expBlock);
                }
                sa.removeLevel();
                return new Node("for-block", insideForBlock);
            } else {
                System.out.println("Ошибка! Ожидалось выражение!");
                return null;
            }
        }
    }
    public Node forStatement() throws Exception {
        ArrayList<Node> insideForSt = new ArrayList<>();
        if (lexer.getLastToken() != Token.FOR) {
            return null;
        }
        insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
        lexer.getNextLexem();
        if (lexer.getLastToken() != Token.LBR) {
            System.out.println("Ошибка! Ожидалась левая скобка!");
            return null;
        }
        insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
        lexer.getNextLexem();
        //!
        if (lexer.getLastToken() != Token.ID) {
            System.out.println("Ошибка! Ожидался идентификатор!");
            return null;
        }
        sa.addLevel();
        sa.addVar(lexer.getLastLexem().toString());
        insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
        lexer.getNextLexem();
        if (lexer.getLastToken() != Token.IN) {
            System.out.println("Ошибка! Ожидался IN!");
            return null;
        }
        insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
        lexer.getNextLexem();

        if (lexer.getLastToken() == Token.ID) {
            insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            if (lexer.getLastToken() != Token.RBR) {
                System.out.println("Ошибка! Ожидалась правая скобка!");
                return null;
            }
            insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            Node expFor = forStatement();
            if (expFor != null) {
                insideForSt.add(expFor);
                return new Node("for-statement", insideForSt);
            }
            else {
                if (lexer.getLastToken() != Token.LCBR ) {
                    System.out.println("Ошибка! Ожидалась левая фигурная скобка!");
                    return null;
                }
                insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                Node expForBlock = forBlock();
                if(expForBlock != null) {
                    insideForSt.add(expForBlock);
                }
                if (lexer.getLastToken() != Token.RCBR ) {
                    System.out.println("Ошибка! Ожидалась правая фигурная скобка!");
                    return null;
                }
                insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                return new Node("for-statement", insideForSt);
            }

        }
        else if (lexer.getLastToken() == Token.NUM) {
            insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            if (lexer.getLastToken() != Token.TO) {
                System.out.println("Ошибка! Ожидалась точка!");
                return null;
            }
            insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            if (lexer.getLastToken() != Token.NUM) {
                System.out.println("Ошибка! Ожидалось число!");
                return null;
            }
            insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            if (lexer.getLastToken() != Token.RBR) {
                System.out.println("Ошибка! Ожидалась правая скобка!");
                return null;
            }
            insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
            lexer.getNextLexem();
            Node expForSt = forStatement();
            if (expForSt != null) {
                insideForSt.add(expForSt);
                return new Node("for-statement", insideForSt);
            }
            else {
                if (lexer.getLastToken() != Token.LCBR) {
                    System.out.println("Ошибка! Ожидалась левая фигурная скобка!");
                    return null;
                }
                insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                Node expForBlock1 = forBlock();
                if (expForBlock1 != null) {
                    insideForSt.add(expForBlock1);
                }
                if (lexer.getLastToken() != Token.RCBR) {
                    System.out.println("Ошибка! Ожидалась правая фигурная скобка!");
                    return null;
                }
                insideForSt.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                return new Node("for-statement", insideForSt);
            }
        }
        else {
            System.out.println("Ошибка! Ожидалось число или ИД!");
            return null;
        }
    }
    public void addTypes(Node exp, String newVar) {
        if (exp.name == "expression") {
            if (exp.childrenNode.get(0).name == "LBR") {
                exp = exp.childrenNode.get(1);
            }
            if (exp.childrenNode.get(0).name == "logical-expression") {
                vt.addVarType(newVar, "BOOL");
            }
            else {
                while (exp.name != "term") {
                    if (exp.childrenNode.get(0).name == "LBR" || exp.childrenNode.get(0).name == "sign") {
                        exp = exp.childrenNode.get(1);
                    }
                    else exp = exp.childrenNode.get(0);
                }
            }
        }
        // добавляем значение переменной
        // добавить (потом..) списки и массивы
        if (exp.name == "term") {
            if (exp.childrenNode.size() <= 2) {
                switch (exp.childrenNode.get(0).name) {
                    case "value":
                        String val;
                        if (exp.childrenNode.get(0).childrenNode.get(0).tl.getToken().toString() == "MINUS")
                            val = exp.childrenNode.get(0).childrenNode.get(1).tl.getToken().toString();
                        else val = exp.childrenNode.get(0).childrenNode.get(0).tl.getToken().toString();
                        switch (val) {
                            case "STR":
                                val = "STRING";
                                break;
                            case "CHAR":
                                val = "CHARTYPE";
                                break;
                            case "TRUE", "FALSE" :
                                val = "BOOL";
                                break;
                            case "NUM":
                                val = exp.childrenNode.get(0).childrenNode.get(0).tl.getLexem().toString();
                                if (val.indexOf(".") != -1) {
                                    if (val.indexOf("f") != -1)
                                        val = "FLOAT";
                                    else val = "DOUBLE";
                                    break;
                                }
                                else {
                                    if (val.length() <= 2) val = "BYTE";
                                    else
                                    if (val.length() <= 4) val = "SHORT";
                                    else
                                    if (val.length() <= 9) val = "INT";
                                    else val = "LONG";
                                }

                        }
                        vt.addVarType(newVar, val);
                        break;
                    case "ID":
                        vt.addVarType(newVar, vt.getVarType(exp.childrenNode.get(0).tl.getLexem().toString()));
                        break;
                    case "function-call":
                        vt.addVarType(newVar, vt.getFunType(exp.childrenNode.get(0).tl.getLexem().toString()));
                        break;
                    case "MINUS":
                        switch(exp.childrenNode.get(1).name) {
                            case "ID":
                                vt.addVarType(newVar, vt.getVarType(exp.childrenNode.get(1).tl.getLexem().toString()));
                                break;
                            case "function-call":
                                vt.addVarType(newVar, vt.getFunType(exp.childrenNode.get(1).tl.getLexem().toString()));
                                break;
                        }
                        break;
                }
            }
        }
    }

    public Node statement() throws Exception {
        ArrayList<Node> insideStatement = new ArrayList<>();


        switch (lexer.getLastToken()) {
            case VAL, VAR:
                Token last = lexer.getLastToken();
                ArrayList<Node> insideDeclaration = new ArrayList<>();

                Node expSimpleDeclaration = simpleDeclaration();

                if (expSimpleDeclaration == null) {
                    System.out.println("Ошибка! Ожидалось объявление!");
                    return null;
                }
                String newVar = expSimpleDeclaration.childrenNode.get(1).tl.getLexem().toString();
                vt.addVar(newVar);

                String val = lexer.getLastLexem().toString(); // здесь должен лежать знак "=" ?
                insideDeclaration.add(expSimpleDeclaration);


                if (lexer.getLastToken() == Token.EQUALS) {

                    if (!sa.checkVal(val)) {
                        return null;
                    }

                    insideDeclaration.add(new Node("EQUALS", new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));

                    lexer.getNextLexem();

                    //после равно получим выражение

                    Node expExpression = expression();
                    //давайте вот тут сохранять токены для логического выражения??

                    Node exp = expExpression;
                    addTypes(exp, newVar);

                    if (expExpression == null) {
                        System.out.println("Ошибка! Ожидалась правая часть выражения!");
                        return null;
                    }

                    insideDeclaration.add(expExpression);
                    insideStatement.add(new Node("declaration", insideDeclaration));
                    //lexer.getNextLexem();
                    return new Node("statement", insideStatement);


                } else {
                    if (last == Token.VAL) {
                        System.out.println("Ошибка! Необходимо инициализировать констатнту!");
                        return null;
                    }
                    Node declaration = new Node("declaration", insideDeclaration);
                    insideStatement.add(declaration);
                    return new Node("statement", insideStatement);
                }
            case ID, ABS, CEIL, FLOOR, MAX, MIN, ROUND, SQRT, CBRT, EXP, LOG, POW, SIGN, PRINT, PRINTLN, READLINE, ARRAYOF, LISTOF: //
                Node expFunCall = function_call();
                if (expFunCall == null) {
                    System.out.println("Ошибка! Ожидался вызов функции!");
                    return null;
                }
                else {
                    insideStatement.add(expFunCall);
                    return new Node("statement", insideStatement);
                }
            case IF:
                ArrayList<Node> insideIf= new ArrayList<>();
                insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                if (lexer.getLastToken() != Token.LBR) {
                    System.out.println("Ошибка! Ожидалась левая скобка!");
                    return null;
                }

                insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();
                Node expIfCond = logicalExpression();

                if (expIfCond == null) {
                    System.out.println("Ошибка! Ожидалось логическое выражение!");
                    return null;
                }

                ArrayList<Node> insideIfCond= new ArrayList<>();
                insideIfCond.add(expIfCond);
                insideIf.add(new Node("if-condition", insideIfCond));

                if (lexer.getLastToken() != Token.RBR) {
                    System.out.println("Ошибка! Ожидалась правая скобка!");
                    return null;
                }
                insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                if (lexer.getLastToken() != Token.LCBR) {
                    System.out.println("Ошибка! Ожидалась левая фигурная скобка!");
                    return null;
                }
                insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                Node expFunBlock = function_block();
                if (expFunBlock != null) {
                    insideIf.add(expFunBlock);
                }

                if (lexer.getLastToken() != Token.RCBR) {
                    System.out.println("Ошибка! Ожидалась правая фигурная скобка!");
                    return null;
                }
                insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                lexer.getNextLexem();

                if (lexer.getLastToken() == Token.ELSE) {
                    insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                    if (lexer.getLastToken() != Token.LCBR) {
                        System.out.println("Ошибка! Ожидалась левая фигурная скобка!");
                        return null;
                    }
                    insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();

                    Node expFunBlock1 = function_block();
                    if (expFunBlock1 != null) {
                        insideIf.add(expFunBlock1);
                    }
                    if (lexer.getLastToken() != Token.RCBR) {
                        System.out.println("Ошибка! Ожидалась правая фигурная скобка!");
                        return null;
                    }
                    insideIf.add(new Node(lexer.getLastToken().toString(), new TokenLexem(lexer.getLastLexem(), lexer.getLastToken())));
                    lexer.getNextLexem();
                }

                Node nodeIf = new Node("if-construction", insideIf);
                insideStatement.add(nodeIf);
                return new Node("statement", insideStatement);

            case WHILE:
                Node expWhile = while_();
                if (expWhile == null) {
                    System.out.println("Ошибка! Ожидался цикл!");
                    return null;
                }
                insideStatement.add(expWhile);
                return new Node("statement", insideStatement);
            case RETURN:
                Node expReturn = return_();
                if (expReturn == null) {
                    return null;
                } else {
                    insideStatement.add(expReturn);
                    return new Node("statement", insideStatement);
                }
            case WHEN:
                Node expSwitch = switchExp();
                if (expSwitch != null) {
                    insideStatement.add(expSwitch);
                    return new Node("statement", insideStatement);
                } else {
                    System.out.println("Ошибка! Ожидалось множественное условие!");
                    return null;
                }
            case FUNCTION:
                Node expFun = function();
                if (expFun != null) {
                    insideStatement.add(expFun);
                    return new Node("statement", insideStatement);
                } else {
                    System.out.println("Ошибка! Ожидалось объявление функции!");
                    return null;
                }
            case FOR:
                Node expFor = forStatement();
                if (expFor == null) {
                    System.out.println("Ошибка! Ожидалось выражение!");
                    return null;
                }
                else {
                    insideStatement.add(expFor);
                    return new Node("statement", insideStatement);
                }
            case CONTINUE, BREAK:
                return null;
            default:
                return null;
        }
    }

    public String print(Node node, int level) {
        String margin = "  ".repeat(level);
        String res = margin + node.name + "\r\n";
        if (node.childrenNode != null) {
            for (int i = 0; i < node.childrenNode.size(); i++) {
                res += margin + "  " + print(node.childrenNode.get(i), level + 1);
            }
        }
        return res;
    }
    public String getVarTypes(){
        return vt.getAllTypes();
    }
    @Override
    public String toString() {
        String result = "";
        Node cur = program;
        result = print(program, 0);
        return result;
    }
}