import java.util.ArrayList;

public class SemanticAnalyzer {
    //ArrayList<String> varTableGlobal = new ArrayList<>();
    ArrayList<String> valTableLocal = new ArrayList<>();
    ArrayList<String> varTableLocal = new ArrayList<>();
    ArrayList<Fun> funTableLocal = new ArrayList<>();
    //ArrayList<String> varTableReserved = new ArrayList<>();

    public SemanticAnalyzer() {
        ArrayList<String> first = new ArrayList<>();
        first.add("num");
        funTableLocal.add(new Fun("ABS", first));
        ArrayList<String> second = new ArrayList<>();
        second.add("num");
        funTableLocal.add(new Fun("CEIL", second));
        ArrayList<String> third = new ArrayList<>();
        first.add("num");
        funTableLocal.add(new Fun("FLOOR", third));

    }
    public void addVar(String s) {
        varTableLocal.add(s);
    }

    public boolean checkVal(String s) {
        if (valTableLocal.indexOf(s) != -1) {
            System.out.println("Переменная " + s + " не может быть переопределена");
            return false;
        }
        return true;
    }

    public boolean checkVariable(String s) {
        if (varTableLocal.indexOf(s) == -1 && valTableLocal.indexOf(s) == -1) {
            System.out.println("Переменная " + s + " не объявлена перед использованием");
            return false;
        }
        return true;
    }
    public void addVal(String s) {
        valTableLocal.add(s);
    }

    public void addLevel() {
        valTableLocal.add("*");
        varTableLocal.add("*");
        ArrayList<String> fl = new ArrayList<>();
        funTableLocal.add(new Fun("*", fl));
    }

    public boolean checkFun(String name_, ArrayList<String> types_) {
        for (int i = 0; i < funTableLocal.size(); i++) {
            if (funTableLocal.get(i).name.equals(name_)) {
                if (funTableLocal.get(i).types.size() != types_.size()) {
                    System.out.println("Функция " + name_ + " использована не с теми параметрами!");
                    return false;
                }
                for (int j = 0; j < types_.size(); j++) {
                    if (!types_.get(j).equals(funTableLocal.get(i).types.get(j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        System.out.println("Функция " + name_ + " не была объявлена!");
        return false;
    }

    public void addFun(String name_, ArrayList<String> types_) {
        funTableLocal.add(new Fun(name_, types_));
    }

    public void removeLevel() {
        int indexVar = varTableLocal.lastIndexOf("*");
        int indexVal = valTableLocal.lastIndexOf("*");

        int indexFun = -1;
        for (int i = funTableLocal.size() - 1; i > 0; i--) {
            if (funTableLocal.get(i).name.equals("*")) {
                indexFun = i;
                break;
            }
        }

        if (indexVar != -1) {
            varTableLocal.subList(indexVar, varTableLocal.size()).clear();
        }
        if (indexVal != -1) {
            valTableLocal.subList(indexVal, valTableLocal.size()).clear();
        }
        if (indexFun != -1) {
            funTableLocal.subList(indexFun, funTableLocal.size()).clear();
        }
    }
}
