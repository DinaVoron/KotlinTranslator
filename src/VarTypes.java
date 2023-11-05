import java.util.ArrayList;

public class VarTypes {
    ArrayList<Vars> vars = new ArrayList<>();
    ArrayList<Functions> functions = new ArrayList<>();

    public void addVar(String name) {
        Vars v = new Vars(name);
        vars.add(v);
    }

    public void addVarType(String name, String type) {
        for (int i = 0; i < vars.size(); ++i){
            if (vars.get(i).name == name) {
                vars.get(i).type = type;
                break;
            }
        }
    }

    public String getVarType(String name) {
        for (int i = 0; i < vars.size(); ++i){
            if (vars.get(i).name == name) {
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
        for (int i = 0; i < vars.size(); ++i){
            if (functions.get(i).name == name) {
                functions.get(i).type = type;
                break;
            }
        }
    }

    public void addFunVar(String name, Vars v) {
        for (int i = 0; i < vars.size(); ++i){
            if (functions.get(i).name == name) {
                functions.get(i).vars.add(v);
                break;
            }
        }
    }

    public String getFunType(String name) {
        for (int i = 0; i < vars.size(); ++i){
            if (functions.get(i).name == name) {
                return functions.get(i).type;
            }
        }
        return "";
    }
    public ArrayList<Vars> getFunVars(String name) {
        for (int i = 0; i < vars.size(); ++i){
            if (functions.get(i).name == name) {
                return functions.get(i).vars;
            }
        }
        return null;
    }

}

