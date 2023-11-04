import java.util.ArrayList;

public class Node {
    public String name;

    public TokenLexem tl;

    //ПОЧЕМУ ТАКОЙ СТРАННЫЙ СПИСОК ДЕТЕЙ? УЗЛОВ? ДЕТЬМИ ДОЛЖНЫ БЫТЬ ТОЛЬКО УЗЛЫ
    //public ArrayList<TokenLexem> children = new ArrayList<TokenLexem>();
    public ArrayList<Node> childrenNode;


    //конструктор для листев деерва
    public Node (String str, TokenLexem tl) {
        this.name = str;
        this.tl = tl;
    }

    //конструктор для остальных узлов дерева
    public Node (String str, ArrayList<Node> arr) {
        //this.name = tl;
        this.name = str;
        this.childrenNode = arr;
    }

    public void addNode(Node n) {
        childrenNode.add(n);
    }

    // ТУТ НЕ РАБОТАЕТ
    // @Override
    public String toString(int t) {
        String s = "";
        for (int j = 0; j < t; ++t) s +="   ";
        s += name;
        // System.out.println(statements.get(0).name);
        for (int i = 0; i < childrenNode.size(); ++i) {
            s += "\n"+childrenNode.get(i).toString(t+1);
            //s += "\n    "+statements.children.get(i).getToken().name();
        }
//        for (int i = 0; i < children.size(); ++i) {
//            s += "\n    "+children.get(i).getToken().name();
//        }
        return s;
    }
}
