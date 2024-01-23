import java.util.HashMap;
import java.util.Map;

public class Optimizer {
    Node program;

    Map<String,Integer> var_using;

    public Optimizer(Node root) {

        this.program = root;
        var_using = new HashMap<String, Integer>();
    }

    public Node optimize() {
        optimize_comments();
        count_vars();
        optimize_vars();
        return this.program;
    }

    public void optimize_comments() {
        int i = 0;
        while (i < program.childrenNode.size()) {
            if (program.childrenNode.get(i).name == "COMMENT") {
                program.childrenNode.remove(i);
                if (i + 1 <= program.childrenNode.size()) {
                    if (program.childrenNode.get(i).name == "EOL") {
                        program.childrenNode.remove(i);
                    }
                }
                continue;
            }
            i++;
        }
    }
    public void count_vars() {
        for (int i = 0; i < program.childrenNode.size(); i++) {
            count_vars_rec(program.childrenNode.get(i));
        }
    }

    public void count_vars_rec(Node node) {

        if (node.name == "simple-declaration") {
            var_using.put(node.childrenNode.get(1).tl.lexema.toString(), 0);
            return;
        }

        if (node.name == "ID") {
            Integer value = var_using.get(node.tl.lexema.toString());
            if (value != null) {
                var_using.put(node.tl.lexema.toString(), value + 1);
            }
        }

        if (node.childrenNode != null && node.childrenNode.size() != 0) {
            for (int i = 0; i < node.childrenNode.size(); i++) {
                count_vars_rec(node.childrenNode.get(i));
            }
        }
    }

    public void optimize_vars() {
        optimize_vars_rec(program);
    }

    public void optimize_vars_rec(Node node) {
        int i = 0;

        //если есть дети
        if (node.childrenNode != null && node.childrenNode.size() != 0) {

            //пока не дошли до конца детей
            while (i < node.childrenNode.size()) {


                //если ребенок - выражение
                if (node.childrenNode.get(i).name == "statement" ||
                        node.childrenNode.get(i).name == "function-command" ||
                        node.childrenNode.get(i).name == "for-command") {


                    //если выражение - это объявление
                    if (node.childrenNode.get(i).childrenNode.get(0).name == "declaration") {

                        Node cur = node.childrenNode.get(i).childrenNode.get(0);

                        if (var_using.get(cur.childrenNode.get(0).childrenNode.get(1).tl.lexema.toString()) == 0) {

                            node.childrenNode.remove(i);

                            if (i + 1 <= node.childrenNode.size()) {

                                if (node.childrenNode.get(i).name == "EOL") {
                                    node.childrenNode.remove(i);
                                }

                                if (node.childrenNode.get(i).name == "function-block") {
                                    if (node.childrenNode.get(i).childrenNode.get(0).name == "EOL") {
                                        node.childrenNode.get(i).childrenNode.remove(0);
                                    }
                                }

                            }
                            continue;

                        }


                    }


                }

                optimize_vars_rec(node.childrenNode.get(i));

                i++;

            }
        }
    }

}
