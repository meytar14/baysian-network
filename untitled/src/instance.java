import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class instance
{
    private String name;
    private String[] values;
    private HashMap<String,instance> parents;
    private CPT cpt;


    public instance(String name)
    {
        this.name=name;
    }

    public CPT getCpt() {
        return cpt;
    }

    public void setCpt(CPT cpt) {
        this.cpt = cpt;
    }

    public String[] getValues() {
        return values;
    }

    public HashMap<String, instance> getParents() {
        return parents;
    }

    public void setParents(HashMap<String, instance> parents) {
        this.parents = parents;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
