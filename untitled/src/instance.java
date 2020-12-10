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
    /*public void helpCpt()
    {
        HashMap<HashMap<String,String>,HashMap<String,Double>> newCpt=new HashMap<HashMap<String,String>,HashMap<String,Double>>();
        HashMap<HashMap<String,String>,HashMap<String,Double>> thisCpt=getCpt().getCpt();
        newCpt.putAll(thisCpt);
        HashMap<String,Double> thisCptVal=thisCpt.values();
        for(String val:values)
        {
            if()
        }
    }*/
    public void theMissingValue(HashMap<String,Double> hm)
    {
        String missingValue="";
        double sum=0;
        for(String val:values)
        {
            if(!hm.containsKey(val))
                missingValue=val;
        }
        for(String val:values)
        {
            if(val!=missingValue)
            {
                sum=sum+hm.get(val);
            }
        }
        hm.put(missingValue,1-sum);
    }
    public double p(HashMap<String,String> combination)
    {
        String thisValue=combination.get(name);
        HashMap<String,String> parentsVal=new HashMap<String, String>();
        double res;
        if(parents==null)
        {
            res=getCpt().getCpt().get(null).get(thisValue);
            return res;
        }
        else {
            for (String parentName : parents.keySet()) {
                parentsVal.put(parentName, combination.get(parentName));
            }
            res = getCpt().getCpt().get(parentsVal).get(thisValue);
            return res;
        }
    }
}
