public class CodeGenerator {

    public String functions = "";

    public String generate(Node node) {
        String res = "";
        if (node == null) {
            return "";
        }

        if (node.childrenNode != null) {
            for (int i = 0; i < node.childrenNode.size(); i++) {
                Node nodeStatement = node.childrenNode.get(i);
                switch (nodeStatement.name) {
                    case "statement":
                        res += statement(nodeStatement);
                        break;
                    case "EOL":
                        res += "\r\n";
                        break;
                    case "COMMENT":
                        res += getComment(nodeStatement);
                }
            }
        }
        return "using System; \r\nclass Trans { \r\n"
                + functions
                + "static void Main() { \r\n \r\n"
                +  res + "\r\n}\r\n}" ;
    }

    public String statement(Node node) {
        String res = "";

        if (node.childrenNode != null) {
            for (int i = 0; i < node.childrenNode.size(); i++) {
                Node nodeStatement = node.childrenNode.get(i);
                switch (nodeStatement.name) {
                    case "declaration":
                        if (node.childrenNode.get(i).childrenNode.get(2).childrenNode.get(0).name == "array-declaration") {
                            res+= getArray_declaration(nodeStatement) + ";";
                        } else {
                            res += getDeclaration(nodeStatement);
                        }
                        break;
                    case "function-call":
                        res += getFunction_call(nodeStatement)+";";
                        break;
                    case "function-construction":
                        functions += getFunction_construction(nodeStatement) + "\r\n";
                        break;
                    case "assigning":
                        res += getAssigning(nodeStatement)+";";
                        break;
                    case "if-construction":
                        res += getIf_consruction(nodeStatement);
                        break;
                    case "while-construction":
                        res += getWhile_construction(nodeStatement);
                        break;
                    case "switch-statement":
                        res += getSwitch_statement(nodeStatement);
                        break;
                    case "return-statement":
                        res += getReturn(nodeStatement)+";";
                        break;
                    case "for-statement":
                        res += getFor_statement(nodeStatement);
                        break;
                }
            }
        }
        return res;
    }

    public String getArray_declaration(Node node) {
        String code = "";
        String type = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node ch = node.childrenNode.get(i);
            switch (ch.name) {
                case "EQUALS":
                    code += "=";
                    break;
                case "expression":
                    type = getTypeArray(ch.childrenNode.get(0).childrenNode.get(2).childrenNode.get(0));
                    code += "{ " + getActual_params(ch.childrenNode.get(0).childrenNode.get(2)) + " }";
                    break;
                case "simple-declaration":
                    code += "[]" + node.childrenNode.get(0).childrenNode.get(1).tl.getLexem().toString();
                    break;
            }
        }
        return type + " " + code;
    }

    public String getTypeArray(Node node) {
        switch(node.childrenNode.get(0).childrenNode.get(0).childrenNode.get(0).tl.token) {
            case NUM:
                return "float";
            case STR:
                return "string";
            case CHAR:
                return "char";
            case TRUE, FALSE:
                return "bool";
        }
        return "";
    }

    public String getValue(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            code += nodeIns.tl.getLexem().toString();
        }
        return code;
    }

    public String getSign(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "PLUS":
                    code += "+";
                    break;
                case "MINUS":
                    code += "-";
                    break;
                case "MUL":
                    code += "*";
                    break;
                case "MOD":
                    code += "%";
                    break;
                case "DIV":
                    code += "/";
            }
        }
        return code;
    }

    public String getExpressionInside_(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "term":
                    code += getTerm(nodeIns);
                    break;
                case "sign":
                    code += getSign(nodeIns);
                    break;
                case "expression-inside'":
                    code += getExpressionInside_(nodeIns);
                    break;
                case "expression-inside":
                    code += getExpressionInside(nodeIns);
                    break;
                case "expression":
                    code += getExpression(nodeIns);
                    break;
            }
        }
        return code;
    }
    public String getExpressionInside(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "LBR":
                    code += "(";
                    break;
                case "RBR":
                    code += ")";
                    break;
                case "term":
                    code += getTerm(nodeIns);
                    break;
                case "sign":
                    code += getSign(nodeIns);
                    break;
                case "expression-inside'":
                    code += getExpressionInside_(nodeIns);
                    break;
                case "expression-inside":
                    code += getExpressionInside(nodeIns);
                    break;
            }
        }
        return code;
    }

    public String getLogicalSign(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "MORE", "LESS", "MOREOREQUAL", "LESSOREQUAL", "AND", "OR", "ISEQUAL", "ISNOTEQUAL":
                    code += nodeIns.tl.getLexem().toString() + " ";
                    break;
            }
        }
        return code;
    }

    public String getLogicalExpression_(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "logical-sign":
                    code += getLogicalSign(nodeIns);
                    break;
                case "term":
                    code += getTerm(nodeIns);
                    break;
                case "logical-expression":
                    code += getLogicalExpression(nodeIns);
                    break;
                case "logical-expression'":
                    code += getLogicalExpression_(nodeIns);
                    break;
                case "LBR":
                    code += "(";
                    break;
                case "RBR":
                    code += ")";
            }
        }
        return code;
    }
    public String getLogicalExpression(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "logical-sign":
                    code += " "+getLogicalSign(nodeIns);
                    break;
                case "term":
                    code += getTerm(nodeIns);
                    break;
                case "logical-expression":
                    code += getLogicalExpression(nodeIns);
                    break;
                case "logical-expression'":
                    code += " "+getLogicalExpression_(nodeIns);
                    break;
                case "LBR":
                    code += "(";
                    break;
                case "NOT":
                    code += "!";
                    break;
                case "RBR":
                    code += ")";
            }
        }
        return code;
    }
    public String getExpression(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "expression-inside":
                    code += getExpressionInside(nodeIns);
                    break;
                case "logical-expression":
                    code += getLogicalExpression(nodeIns);
                    break;
                case "logical-expression'":
                    code += " "+getLogicalExpression_(nodeIns);
                    break;
                case "LBR":
                    code += "(";
                    break;
                case "RBR":
                    code += ")";
                    break;

            }
        }
        return code;
    }

    public String getActual_param(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "expression":
                    code += getExpression(nodeIns);
                    break;
                case "term":
                    code += getTerm(nodeIns);
            }
        }
        return code;
    }
    public String getActual_params(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "COMMA":
                    code += nodeIns.tl.getLexem().toString()+" ";
                    break;
                case "actual-param":
                    code += getActual_param(nodeIns);
                    break;
                case "actual-params":
                    code += getActual_params(nodeIns);
            }
        }
        return code;
    }
    public String getFunction_call(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "ID":
                    switch (nodeIns.tl.getToken()){
                        case PRINTLN:
                            code += "Console.WriteLine";
                            break;
                        default:
                            code += nodeIns.tl.getLexem().toString();
                            break;
                    }
                    break;
                case"LBR", "RBR":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "actual-params":
                    code += getActual_params(nodeIns);
                    break;
                default:
                    code += nodeIns.tl.getLexem().toString();
                    break;
            }
        }
        return code;
    }
    public String getTerm(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "value":
                    code += getValue(nodeIns);
                    break;
                case "ID":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "LSQBR":
                    code += "[";
                    break;
                case "NUM":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "RSQBR":
                    code += "]";
                    break;
                case "MINUS":
                    code += "-";
                    break;
                case "function-call":
                    code += getFunction_call(nodeIns);
            }
        }
        return code;
    }

    public String getIf_condition(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "logical-expression":
                    code += getLogicalExpression(nodeIns);
                    break;
                case "term":
                    code += getTerm(nodeIns);
                    break;
            }
        }
        return code;
    }
    public String getIf_consruction(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "IF":
                    code += "if ";
                    break;
                case "LBR":
                    code += "(";
                    break;
                case "RBR":
                    code += ")";
                    break;
                case "LCBR":
                    code += " {";
                    break;
                case "RCBR":
                    code += "}";
                    break;
                case "function-block":
                    code += getFunction_block(nodeIns);
                    break;
                case "if-condition":
                    code += getIf_condition(nodeIns);
                    break;
                case "ELSE":
                    code += " else";
                    break;
            }
        }
        return code;
    }
    public String getAssigning(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "ID":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "EQUALS":
                    code += " = ";
                    break;
                case "expression":
                    code += getExpression(nodeIns);
                    break;
                case "term":
                    code += getTerm(nodeIns);
                    break;
            }
        }
        return code;
    }
    public String getDeclaration(Node node) {
        String code = "";
        String type = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "simple-declaration":
                    if (nodeIns.childrenNode.get(0).name == "VAL") {
                        if (node.childrenNode.get(2).name == "term") {
                            if (node.childrenNode.get(2).childrenNode.get(0).name == "value") {
                                switch(node.childrenNode.get(2).childrenNode.get(0).childrenNode.get(0).name) {
                                    case "NUM": //потом должен быть флоат
                                        type = "int";
                                        break;
                                    case "STR":
                                        type = "string";
                                        break;
                                    case "TRUE", "FALSE":
                                        type = "bool";
                                        break;
                                    case "CHAR":
                                        type = "char";
                                        break;
                                    case "MINUS":
                                        type = "int";
                                        break;
                                }
                                code += "const " + type + " ";
                            }
                        }
                        else code += "dynamic ";
                    }
                    else code += "dynamic ";
                    code += nodeIns.childrenNode.get(1).tl.getLexem().toString()+" ";
                    break;
                case "EQUALS" :
                    code += "= ";
                    break;
                case "expression":
                    code += getExpression(nodeIns);
                    break;
                case "term":
                    code += getTerm(nodeIns);

            }
        }
        return code+";";
    }
    public String getSwitch_block(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "EOL":
                    code += "\r\n";
                    break;
                case "switch-command":
                    code += getSwitch_command(nodeIns);
                    break;
                case "switch-block":
                    code += getSwitch_block(nodeIns);
                    break;
            }
        }
        return code;
    }

    public String getSwitch_statement(Node node) {
        String code = "";
        //String value = ""
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "WHEN":
                    code += "switch";
                    break;
                case "LBR":
                    code += "(";
                    break;
                case "RBR":
                    code += ")";
                    break;
                case "ID":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "LCBR":
                    code += "{ ";
                    break;
                case "RCBR":
                    code += "}";
                    break;
                case "switch-block":
                    code += getSwitch_block(nodeIns);
                    break;
            }
        }
        return code;
    }
    public String getSwitch_command(Node node) {
        String code = "";
        String value = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "value":
                    value = nodeIns.childrenNode.get(0).tl.getLexem().toString();
                    break;
                case "LCBR":
                    code += ":";
                    break;
                case "RCBR":
                    code += "break;";
                    break;
                case "CASE":
                    code += "case " + value;
                    break;
                case "function-block":
                    code += getFunction_block(nodeIns);
                    break;
                case "else":
                    code += "default";
                    break;
                case "function-command":
                    code += getFunction_command(nodeIns);
                    break;
            }
        }
        return code;
    }
    public String getFunction_command(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "declaration":
                    code += getDeclaration(nodeIns);
                    break;
                case "assigning":
                    code += getAssigning(nodeIns)+ ";";
                    break;
                case "function-call":
                    code += getFunction_call(nodeIns) + ";";
                    break;

                case "if-construction":
                    code += getIf_consruction(nodeIns);
                    break;
                case "while-construction":
                    code += getWhile_construction(nodeIns);
                    break;
                case "switch-statement":
                    code += getSwitch_statement(nodeIns);
                    break;
                case "return-statement":
                    code += getReturn(nodeIns)+ ";";
                    break;
                case "for-statement":
                    code += getFor_statement(nodeIns);
                    break;
                case "array-statement":
            }
        }
        return code;
    }
    public String getFunction_block(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch(nodeIns.name) {
                case "EOL":
                    code += "\n";
                    break;
                case "function-block":
                    code += getFunction_block(nodeIns);
                    break;
                case "function-command":
                    code += getFunction_command(nodeIns);
                    break;
            }
        }
        return code;
    }

    public String getType(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "BYTE":
                    code += "byte";
                    break;
                case "SHORT":
                    code += "short";
                    break;
                case "INT":
                    code += "int";
                    break;
                case "LONG":
                    code += "long";
                    break;
                case "FLOAT":
                    code += "float";
                    break;
                case "DOUBLE":
                    code += "double";
                    break;
                case "BOOL":
                    code += "bool";
                    break;
                case "CHARTYPE":
                    code += "char";
                    break;
                case "STRING":
                    code += "string";
                    break;
            }
        }
        return code;
    }


    public String getParam(Node node) {
        String code = "";
        code += getType(node.childrenNode.get(2))+" ";
        code += node.childrenNode.get(0).tl.getLexem().toString();
        return code;
    }

    public String getParams(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "param":
                    code += getParam(nodeIns);
                    break;
                case "COMMA":
                    code += nodeIns.tl.getLexem().toString()+" ";
                    break;
                case "params":
                    code += getParams(nodeIns);
                    break;
            }
        }
        return code;
    }

    public String getWhile_construction(Node node) {
            String code = "";
            for (int i = 0; i < node.childrenNode.size(); i++) {
                Node nodeIns = node.childrenNode.get(i);
                switch (nodeIns.name) {
                    case "WHILE", "RBR":
                        code += nodeIns.tl.getLexem().toString()+" ";
                        break;
                    case "LBR", "RCBR", "LCBR":
                        code += nodeIns.tl.getLexem().toString();
                        break;
                    case "if-condition":
                        code += getIf_condition(nodeIns);
                        break;
                    case "function-block":
                        code += getFunction_block(nodeIns);
                        break;
                    case "EOL":
                        code += "\n";
                }
            }
            return code;
        }

    public String getReturn(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "RETURN":
                    code += nodeIns.tl.getLexem().toString()+" ";
                    break;
                case "term":
                    code += getTerm(nodeIns);
                    break;
                case "expression":
                    code += getExpression(nodeIns);
                    break;
            }
        }
        return code;
    }

    public String getFor_command(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "declaration":
                    code += getDeclaration(nodeIns);
                    break;
                case "assigning":
                    code += getAssigning(nodeIns)+ ";";
                    break;
                case "function-call":
                    code += getFunction_call(nodeIns) + ";";
                    break;
                case "if-construction":
                    code += getIf_consruction(nodeIns);
                    break;
                case "while-construction":
                    code += getWhile_construction(nodeIns);
                    break;
                case "switch-statement":
                    code += getSwitch_statement(nodeIns);
                    break;
                case "return-statement":
                    code += getReturn(nodeIns)+ ";";
                    break;
                case "CONTINUE", "BREAK":
                    code += nodeIns.tl.getLexem().toString()+";";
                    break;
            }
        }
        return code;
    }
    public String getArray_statement (Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "ID":
                    code += nodeIns.tl.getLexem().toString();
                    break;
            }
        }
        return code;
    }

    public String getFor_block(Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "EOL":
                    code += "\n";
                    break;
                case "for-block":
                    code += getFor_block(nodeIns);
                    break;
                case "for-command":
                    code += getFor_command(nodeIns);
                    break;
            }
        }
        return code;
    }

    public String getFor_statement (Node node) {
        String code = "";
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "RBR":
                    code += nodeIns.tl.getLexem().toString()+" ";
                    break;
                case "LCBR", "RCBR":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "for-block":
                    code += getFor_block(nodeIns);
                    break;
                case "for-statement":
                    code += getFor_statement(nodeIns);
                    break;
                case "FOR":
                    if (node.childrenNode.get(i+4).tl.getToken() == Token.ID) {
                        code += "foreach ";
                    }
                    if (node.childrenNode.get(i+4).tl.getToken() == Token.NUM) {
                        code += "for ";
                    }
                    break;
                case "LBR": //здесь переписать условие по-сишарповки
                    if (node.childrenNode.get(i+3).tl.getToken() == Token.NUM) {
                        code += nodeIns.tl.getLexem().toString();
                        ++i;
                        nodeIns = node.childrenNode.get(i);
                        String id = nodeIns.tl.getLexem().toString();
                        i += 2;
                        nodeIns = node.childrenNode.get(i);
                        String num1 = nodeIns.tl.getLexem().toString();
                        i += 2;
                        nodeIns = node.childrenNode.get(i);
                        String num2 = nodeIns.tl.getLexem().toString();
                        code += "int " + id + " = " + num1 + "; " + id + " <= " + num2 + "; ++" + id;
                        break;
                    }
                    if (node.childrenNode.get(i+3).tl.getToken() == Token.ID) {
                        code += nodeIns.tl.getLexem().toString();
                        ++i;
                        nodeIns = node.childrenNode.get(i);
                        String id = nodeIns.tl.getLexem().toString();
                        i += 2;
                        nodeIns = node.childrenNode.get(i);
                        String num2 = nodeIns.tl.getLexem().toString();
                        code += "dynamic " + id + " in " + num2;
                        break;
                    }
            }
        }
        return code;
    }
    public String getFunction_construction(Node node) {
        String code = "";
        String type = "";
        Boolean isMain = false;
        for (int i = 0; i < node.childrenNode.size(); i++) {
            Node nodeIns = node.childrenNode.get(i);
            switch (nodeIns.name) {
                case "ID", "RBR":
                    if (nodeIns.tl.getLexem().toString().equals("main")) {
                        isMain = true;
                        code += "Main";
                        break;
                    }
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "LBR", "LCBR", "RCBR":
                    code += nodeIns.tl.getLexem().toString();
                    break;
                case "params":
                    code += getParams(nodeIns);
                    break;
                case "function-block":
                    code += getFunction_block(nodeIns);
                    break;
                case "type":
                    type += getType(nodeIns);
            }
        }
//7
        String code1 = "public static ";
        if(type != "") {
            code1 += type+" ";
        }
        else {
                code1 += "void ";
        }
        return (code1+code);
    }

    public String getComment(Node node) {
        String res = "";
        char[] res_char = node.tl.lexema.toString().toCharArray();
        for (char ch: res_char) {
            if (ch == '®') {
                res += "\r\n";
            } else {
                res += ch;
            }
        }
        return res;
    }

}
