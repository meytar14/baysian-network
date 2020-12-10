import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CPT {
    private HashMap<HashMap<String,String>,HashMap<String,Double>> cpt;
    public CPT(HashMap<HashMap<String,String>,HashMap<String,Double>> cpt)
    {
        this.cpt=cpt;
    }

    public HashMap<HashMap<String, String>, HashMap<String, Double>> getCpt() {
        return cpt;
    }

    public void setCpt(HashMap<HashMap<String, String>, HashMap<String, Double>> cpt) {
        this.cpt = cpt;
    }


}
