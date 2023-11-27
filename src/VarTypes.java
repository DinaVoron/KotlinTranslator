import java.util.ArrayList;

public class VarTypes {
    ArrayList<Vars> vars = new ArrayList<>();
    ArrayList<Functions> functions = new ArrayList<>();
    //  +листы
    //ArrayList<Vars> vars = new ArrayList<>();
    // +массивы
    //ArrayList<Vars> vars = new ArrayList<>();

    public void addVar(String name) {
        Vars v = new Vars(name);
        vars.add(v);
    }

    public void addVarType(String name, String type) {
        for (int i = 0; i < vars.size(); ++i){
            if (vars.get(i).name.equals(name)) {
                vars.get(i).type = type;
                break;
            }
        }
    }

    public String getVarType(String name) {
        for (int i = 0; i < vars.size(); ++i){
            if (vars.get(i).name.equals(name)) {
                return vars.get(i).type;
            }
        }
        return "";
    }

    public void addFun(String name) {
        Functions f = new Functions(name);
        functions.add(f);
    }

    public void addFunType(String name, String type) {
        for (int i = 0; i < functions.size(); ++i){
            if (functions.get(i).name.equals(name)) {
                functions.get(i).type = type;
                break;
            }
        }
    }

    public void addFunVar(String name, Vars v) {
        for (int i = 0; i < functions.size(); ++i){
            if (functions.get(i).name.equals(name)) {
                functions.get(i).vars.add(v);
                break;
            }
        }
    }

    public String getFunType(String name) {
        for (int i = 0; i < functions.size(); ++i){
            if (functions.get(i).name.equals(name)) {
                return functions.get(i).type;
            }
        }
        return "";
    }
    public ArrayList<Vars> getFunVars(String name) {
        for (int i = 0; i < functions.size(); ++i){
            if (functions.get(i).name.equals(name)) {
                return functions.get(i).vars;
            }
        }
        return null;
    }

    public String getAllTypes() {
        String table = "-------------------------------------\n";
        table += "Vars:\n";
        for (int i = 0; i < vars.size(); ++i){
            table += vars.get(i).name + "\t | " + vars.get(i).type + "\n";
        }
        table += "Functions:\n";
        for (int i = 0; i < functions.size(); ++i){
            table += functions.get(i).name + "\t | " + functions.get(i).type + "\n";
            table += "Vars: \n";
            for (int j = 0; j < functions.get(i).vars.size(); ++j){
                table += functions.get(i).vars.get(j).name + "\t | " + functions.get(i).vars.get(j).type + "\n";
            }
        }
        return table+"-------------------------------------\n";
    }
}

