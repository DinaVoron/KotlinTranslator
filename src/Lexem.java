public class Lexem {
    String str;

    public Lexem(String str_) {
        str = str_;
    }

    public String toString() {
        return str;
    }

    public boolean isEqual(Lexem lexem_) {
        if (lexem_.toString().equals(this.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
