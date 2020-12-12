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
    private HashMap<HashMap<String,String>,Double> factor;


    public instance(String name)
    {
        this.name=name;
    }

    public HashMap<HashMap<String, String>, Double> getFactor() {
        return factor;
    }

    public void setFactor(HashMap<HashMap<String, String>, Double> factor) {
        this.factor = factor;
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
    public boolean containsParantsVal(HashMap<String,String> hm,HashMap<String,String>parentsEvidence)
    {
        if(parentsEvidence.isEmpty())
            return true;
        boolean b=true;
        for(String parentName:parentsEvidence.keySet())
        {
           if(!hm.get(parentName).equalsIgnoreCase(parentsEvidence.get(parentName)))
           {
               b=false;
               break;
           }
        }
        return b;
    }
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
    public void factorize(String instanceVal,HashMap<String,String> evidence)
    {
        HashMap<HashMap<String,String>,HashMap<String,Double>> cpt=getCpt().getCpt();
        HashMap<HashMap<String,String>,Double> factor=new HashMap<HashMap<String,String>,Double>();
        if(parents==null)
        {
            if(instanceVal!=null)
            {
                setFactor(null);
                return;
            }
            else {
                for (String val : values) {
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put(name, val);
                    factor.put(temp, cpt.get(null).get(val));
                }
            }
        }
        else if(instanceVal!=null) {
            HashMap<String,String>parentsEvidence=new HashMap<>();
            for(String parant:parents.keySet())
            {
                if(evidence.containsKey(parant))
                {
                    parentsEvidence.put(parant,evidence.get(parant));
                }
            }
            for (HashMap<String, String> hm : cpt.keySet()) {
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.putAll(hm);
                    if (containsParantsVal(temp, parentsEvidence)) {
                        for(String parent:parentsEvidence.keySet())//
                        {
                            temp.remove(parent);
                        }//
                        factor.put(temp, cpt.get(hm).get(instanceVal));
                }
            }
        }
        else
        {
            HashMap<String,String>parentsEvidence=new HashMap<>();
            for(String parant:parents.keySet())
            {
                if(evidence.containsKey(parant))
                {
                    parentsEvidence.put(parant,evidence.get(parant));
                }
            }
            for (HashMap<String, String> hm : cpt.keySet()) {
                for(String val:values)
                {
                    HashMap<String,String>temp=new HashMap<String, String>();
                    temp.putAll(hm);
                    temp.put(name,val);
                    if(containsParantsVal(temp,parentsEvidence)) {
                        for(String parent:parentsEvidence.keySet())//
                        {
                            temp.remove(parent);
                        }//
                        factor.put(temp, cpt.get(hm).get(val));
                    }
                }
            }
        }
        setFactor(factor);
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
