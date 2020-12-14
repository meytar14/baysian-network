import java.util.*;

public class Querie
{
    private int algorithmNum;
    private String pName;//the name of the instace
    private String pValue;  // the value(true/false...)
    private HashMap<String,String> evidence;// <name of the instance,value of the instance>
    private double result;
    private int counterMul;
    private  int counterAdd;

    public Querie()
    {
        this.algorithmNum=0;
        this.evidence=null;
        this.pName=null;
        this.pValue=null;
        this.result=-1;
        this.counterAdd=0;
        this.counterMul=0;
    }

    public int getCounterAdd() {
        return counterAdd;
    }

    public void setCounterAdd(int counterAdd) {
        this.counterAdd = counterAdd;
    }

    public int getCounterMul() {
        return counterMul;
    }

    public void setCounterMul(int counterMul) {
        this.counterMul = counterMul;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getAlgorithmNum() {
        return algorithmNum;
    }

    public void setAlgorithmNum(int algorithmNum) {
        this.algorithmNum = algorithmNum;
    }

    public String getpName() {
        return pName;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public HashMap<String, String> getEvidence() {
        return evidence;
    }

    public void setEvidence(HashMap<String, String> evidence) {
        this.evidence = evidence;
    }

    public double algo(HashMap<String,instance> instances)
    {
        switch (algorithmNum)
        {
            case 1:
                return algo1(instances);
            case 2:
                return algo2(instances);
            case 3:
                return algo3(instances);
            default:
                return algo1(instances);
        }
    }
    public LinkedList<HashMap<String,String>> allCombinations(LinkedList<instance> notEvidence ,LinkedList<HashMap<String,String>> res)//returns all the combinations of the different values of "not evidence" with the values of res.
    {
        if(notEvidence.isEmpty())
            return res;
        else
            {
                LinkedList<HashMap<String, String>> res2 = new LinkedList<HashMap<String, String>>();
                instance i=notEvidence.poll();
                for(String val:i.getValues())
                {
                    Iterator<HashMap<String,String>> iter=res.listIterator();
                    while (iter.hasNext())
                    {
                        HashMap<String, String> temp = new HashMap<String, String>();
                        temp.putAll(iter.next());
                        temp.put(i.getName(),val);
                        res2.add(temp);
                    }
                }
                return allCombinations(notEvidence,res2);
            }
    }

    public double algo1(HashMap<String,instance> instances)
    {
        if(instances.get(pName).getCpt().getCpt().containsKey(evidence))
        {
            double res=instances.get(pName).getCpt().getCpt().get(evidence).get(pValue);
            int IntRes=(int)(res*100000);
            res=IntRes/100000.0;
            return res;
        }
        LinkedList<instance> notEvidence=new LinkedList<instance>();
        for(instance i:instances.values())
        {
            if(!evidence.containsKey(i.getName()))
            {
                if(!i.getName().equalsIgnoreCase(pName)) {
                    notEvidence.add(i);
                }
            }
        }

        HashMap<String,String> evidence2=new HashMap<String, String>();
        evidence2.putAll(evidence);
        evidence2.put(pName,pValue);
        LinkedList<HashMap<String,String>> L=new LinkedList<HashMap<String,String>>();
        L.add(evidence2);
        LinkedList<HashMap<String,String>> Combinations=allCombinations(notEvidence,L);
        //////calculation of the p(pValue)
        double pOfpValue=0;
        Iterator<HashMap<String,String>> iter=Combinations.listIterator();
        HashMap<String,String> temp;
        while (iter.hasNext())
        {
            temp=iter.next();
            double res=1;
            for(instance i:instances.values())
            {
                res=res*i.p(temp);
                counterMul++;
            }
            counterMul--;//because in the first time we multiply by 1
            pOfpValue=pOfpValue+res;
            counterAdd++;
        }
        counterAdd--;//because in the first time we add 0
        //////calculation of the p(not pValue)
        double pOfpNotValue=0;
        for(String val:instances.get(pName).getValues()) {
            if (!val.equalsIgnoreCase(pValue)) {
                for (instance i : instances.values()) {
                    if (!evidence.containsKey(i.getName())) {
                        if (!i.getName().equalsIgnoreCase(pName)) {
                            notEvidence.add(i);
                        }
                    }
                }
                HashMap<String, String> evidence3 = new HashMap<String, String>();
                evidence3.putAll(evidence);
                evidence3.put(pName, val);
                LinkedList<HashMap<String, String>> L2 = new LinkedList<HashMap<String, String>>();
                L2.add(evidence3);
                LinkedList<HashMap<String, String>> Combinations2 = allCombinations(notEvidence, L2);
                Iterator<HashMap<String, String>> iter2 = Combinations2.listIterator();
                HashMap<String, String> temp2;
                while (iter2.hasNext()) {
                    temp2 = iter2.next();
                    double res = 1;
                    for (instance i : instances.values()) {
                        res = res * i.p(temp2);
                        counterMul++;
                    }
                    counterMul--;//because in the first time we multiply by 1
                    pOfpNotValue = pOfpNotValue + res;
                    counterAdd++;
                }
                counterAdd--;//because in the first time we add 0
            }
        }
        double res=pOfpValue/(pOfpNotValue+pOfpValue);
        counterAdd++;
        int IntRes=(int)(res*100000);
        res=IntRes/100000.0;
        setCounterAdd(counterAdd);
        setCounterMul(counterMul);
        return res;
    }

    public LinkedList<HashMap<String,String>> allcombinations2(LinkedList<instance> instances,LinkedList<HashMap<String,String>> L)
    {
        if(instances.isEmpty())
        {
            return L;
        }
        else
        {
            LinkedList<HashMap<String, String>> L2 = new LinkedList<HashMap<String, String>>();
            instance i=instances.poll();
            for(String val:i.getValues())
            {
                Iterator<HashMap<String,String>> iter=L.listIterator();
                while (iter.hasNext())
                {
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.putAll(iter.next());
                    temp.put(i.getName(),val);
                    L2.add(temp);
                }
            }
            return allCombinations(instances,L2);
        }
    }
    public double pFactor(HashMap<HashMap<String,String>,Double> factor,HashMap<String,String> combination)
    {
        HashMap<String,String> factorkeys=new HashMap<>();
        Iterator<HashMap<String,String>> iter=factor.keySet().iterator();
        HashMap<String,String> fhm=iter.next();
        Set<String>fkeys=fhm.keySet();//a set of the names of instances in factor
        for(String name:fkeys)
        {
            factorkeys.put(name,combination.get(name));
        }
        return factor.get(factorkeys);
    }

    public HashMap<HashMap<String,String>,Double> eliminate(HashMap<HashMap<String,String>,Double> f,String name)
    {
        int counterAdd=0;
        HashMap<HashMap<String,String>,Double> newf=new HashMap<HashMap<String,String>,Double>();
        for(HashMap<String,String> hm:f.keySet())
        {
            double v=f.get(hm);
            HashMap<String,String>temp=new HashMap<>();
            temp.putAll(hm);
            temp.remove(name);
            if(newf.containsKey(temp))
            {
                newf.replace(temp,newf.get(temp)+v);
                counterAdd++;
            }
            else
            {
                newf.put(temp,v);
            }
        }
        setCounterAdd(getCounterAdd()+counterAdd);
        return newf;
    }
    public HashMap<HashMap<String,String>,Double> join(HashMap<HashMap<String,String>,Double> a,HashMap<HashMap<String,String>,Double> b,HashMap<String,instance> instances)
    {
        int counterMul=0;
        LinkedList<instance> bothInstances=new LinkedList<>();
        HashSet<instance> bothInstancesSet=new HashSet<>();
        Iterator<HashMap<String,String>> iter=a.keySet().iterator();
        HashMap<String,String> ahm=iter.next();
        Set<String>akeys=ahm.keySet();//a set of the names of instances in factor "a"
        iter=b.keySet().iterator();
        HashMap<String,String> bhm=iter.next();
        Set<String>bkeys=bhm.keySet();//a set of the names of instances in factor "b"
        for(String name:akeys)//insert all the instances that are in factor "a" to bothInstancesSet
        {
            bothInstancesSet.add(instances.get(name));
        }
        for(String name:bkeys)//insert all the instances that are in factor "b" to bothInstancesSet
        {
            bothInstancesSet.add(instances.get(name));
        }
        for(instance i:bothInstancesSet)//insert all the instances that are in  "bothInstancesSet" to "bothInstances"
        {
            bothInstances.add(i);
        }
        HashMap<String,String> hm=new HashMap<>();
        LinkedList<HashMap<String,String>> L=new LinkedList<HashMap<String,String>> ();
        L.add(hm);
        LinkedList<HashMap<String,String>> combinations=allcombinations2(bothInstances,L);
        HashMap<HashMap<String,String>,Double> join=new HashMap<HashMap<String,String>,Double>();
        for(HashMap<String,String> combination:combinations)
        {
            join.put(combination,pFactor(a,combination)*pFactor(b,combination));
            counterMul++;
        }
        setCounterMul(getCounterMul()+counterMul);
        return join;
    }
    public double algo2(HashMap<String,instance> instances)
    {
        if(instances.get(pName).getCpt().getCpt().containsKey(evidence))
        {
            double res=instances.get(pName).getCpt().getCpt().get(evidence).get(pValue);
            int IntRes=(int)(res*100000);
            res=IntRes/100000.0;
            return res;
        }
        HashMap<String,String> evidence2=new HashMap<String, String>();
        evidence2.putAll(evidence);
        evidence2.put(pName,pValue);
       // LinkedList<instance> notEvidence=new LinkedList<instance>();
        ArrayList<String>notEvidenceNames=new ArrayList<>();


        for(instance i:instances.values())
        {
            if(!evidence2.containsKey(i.getName()))
            {
                   // notEvidence.add(i);
                    notEvidenceNames.add(i.getName());
            }
        }
        notEvidenceNames.sort(Comparator.naturalOrder());//sorting this array by alphabet order
        HashMap<String,HashMap<HashMap<String,String>,Double>> factors=new HashMap<String,HashMap<HashMap<String,String>,Double>>();
        for(instance i:instances.values())//factorize all the instances
        {
            if (evidence.containsKey(i.getName()))
            {
                i.factorize(evidence.get(i.getName()),evidence);
                if(i.getFactor()!=null) {
                    factors.put(i.getName(), i.getFactor());
                }
            }
            else {
                i.factorize(null,evidence);
                if(i.getFactor()!=null) {
                    factors.put(i.getName(), i.getFactor());
                }
            }
        }


        //while (!notEvidence.isEmpty())
        //{
        for(int i=0;i<notEvidenceNames.size();i++) {
            //instance temp = notEvidence.poll();
            instance temp =instances.get(notEvidenceNames.get(i));
            LinkedList<HashMap<HashMap<String, String>, Double>> factorsWithTemp = new LinkedList<>();//list of all the factors that contains temp
            LinkedList<String> factorsWithTempNames = new LinkedList<>();
            for (String factor : factors.keySet())//isert to "factorsWithTemp" from factors that contains temp
            {
                Iterator<HashMap<String, String>> iter = factors.get(factor).keySet().iterator();
                HashMap<String, String> hm = iter.next();
                if (hm.keySet().contains(temp.getName())) {
                    factorsWithTemp.add(factors.get(factor));
                    factorsWithTempNames.add(factor);
                }
            }
            while (!factorsWithTempNames.isEmpty())//remove the factors that we isert to "factorsWithTemp" from "factors"
            {
                String n = factorsWithTempNames.poll();
                factors.remove(n);
            }
            HashMap<HashMap<String, String>, Double> joinedFactor = factorsWithTemp.poll();
            HashMap<HashMap<String, String>, Double> f;
            while (!factorsWithTemp.isEmpty()) {
                f = factorsWithTemp.poll();
                joinedFactor = join(joinedFactor, f, instances);
            }
            factors.put(temp.getName(), eliminate(joinedFactor, temp.getName()));
        }
        //}
        LinkedList<HashMap<HashMap<String,String>,Double>> lastFactors=new LinkedList<>();
        for(HashMap<HashMap<String,String>,Double> f:factors.values())
        {
            lastFactors.add(f);
        }
        HashMap<HashMap<String,String>,Double>joinedFactor2=lastFactors.poll();
        HashMap<HashMap<String,String>,Double> f;
        while (!lastFactors.isEmpty())
        {
            f=lastFactors.poll();
            joinedFactor2=join(joinedFactor2,f,instances);
        }
        //Iterator<HashMap<HashMap<String,String>,Double>>it=factors.values().iterator();
        //HashMap<HashMap<String,String>,Double>finalFactor=it.next();
        HashMap<String,String> queryInstance=new HashMap<>();
        queryInstance.put(pName,pValue);
        double pVal=joinedFactor2.get(queryInstance);
        double pval_And_pNotVal=0;
        for(double v:joinedFactor2.values())
        {
            pval_And_pNotVal=pval_And_pNotVal+v;
        }
        double res=pVal/pval_And_pNotVal;
        int IntRes=(int)(res*100000);
        res=IntRes/100000.0;
        return res;
    }

    public double algo3(HashMap<String,instance> instances)
    {
        if(instances.get(pName).getCpt().getCpt().containsKey(evidence))
        {
            double res=instances.get(pName).getCpt().getCpt().get(evidence).get(pValue);
            int IntRes=(int)(res*100000);
            res=IntRes/100000.0;
            return res;
        }
        HashMap<String,String> evidence2=new HashMap<String, String>();
        evidence2.putAll(evidence);
        evidence2.put(pName,pValue);
        // LinkedList<instance> notEvidence=new LinkedList<instance>();
        ArrayList<String>notEvidenceNames=new ArrayList<>();


        for(instance i:instances.values())
        {
            if(!evidence2.containsKey(i.getName()))
            {
                // notEvidence.add(i);
                notEvidenceNames.add(i.getName());
            }
        }
        //notEvidenceNames.sort(Comparator.naturalOrder());//sorting this array by alphabet order

        HashMap<String,HashMap<HashMap<String,String>,Double>> factors=new HashMap<String,HashMap<HashMap<String,String>,Double>>();
        for(instance i:instances.values())//factorize all the instances
        {
            if (evidence.containsKey(i.getName()))
            {
                i.factorize(evidence.get(i.getName()),evidence);
                if(i.getFactor()!=null) {
                    factors.put(i.getName(), i.getFactor());
                }
            }
            else {
                i.factorize(null,evidence);
                if(i.getFactor()!=null) {
                    factors.put(i.getName(), i.getFactor());
                }
            }
        }

        Collections.sort(notEvidenceNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int x=Integer.valueOf(factors.get(o1).size()).compareTo(factors.get(o2).size());
                return -x;
            }
        });

        //while (!notEvidence.isEmpty())
        //{
        for(int i=0;i<notEvidenceNames.size();i++) {
            //instance temp = notEvidence.poll();
            instance temp =instances.get(notEvidenceNames.get(i));
            LinkedList<HashMap<HashMap<String, String>, Double>> factorsWithTemp = new LinkedList<>();//list of all the factors that contains temp
            LinkedList<String> factorsWithTempNames = new LinkedList<>();
            for (String factor : factors.keySet())//isert to "factorsWithTemp" from factors that contains temp
            {
                Iterator<HashMap<String, String>> iter = factors.get(factor).keySet().iterator();
                HashMap<String, String> hm = iter.next();
                if (hm.keySet().contains(temp.getName())) {
                    factorsWithTemp.add(factors.get(factor));
                    factorsWithTempNames.add(factor);
                }
            }
            while (!factorsWithTempNames.isEmpty())//remove the factors that we isert to "factorsWithTemp" from "factors"
            {
                String n = factorsWithTempNames.poll();
                factors.remove(n);
            }
            HashMap<HashMap<String, String>, Double> joinedFactor = factorsWithTemp.poll();
            HashMap<HashMap<String, String>, Double> f;
            while (!factorsWithTemp.isEmpty()) {
                f = factorsWithTemp.poll();
                joinedFactor = join(joinedFactor, f, instances);
            }
            factors.put(temp.getName(), eliminate(joinedFactor, temp.getName()));
        }
        //}
        LinkedList<HashMap<HashMap<String,String>,Double>> lastFactors=new LinkedList<>();
        for(HashMap<HashMap<String,String>,Double> f:factors.values())
        {
            lastFactors.add(f);
        }
        HashMap<HashMap<String,String>,Double>joinedFactor2=lastFactors.poll();
        HashMap<HashMap<String,String>,Double> f;
        while (!lastFactors.isEmpty())
        {
            f=lastFactors.poll();
            joinedFactor2=join(joinedFactor2,f,instances);
        }
        //Iterator<HashMap<HashMap<String,String>,Double>>it=factors.values().iterator();
        //HashMap<HashMap<String,String>,Double>finalFactor=it.next();
        HashMap<String,String> queryInstance=new HashMap<>();
        queryInstance.put(pName,pValue);
        double pVal=joinedFactor2.get(queryInstance);
        double pval_And_pNotVal=0;
        for(double v:joinedFactor2.values())
        {
            pval_And_pNotVal=pval_And_pNotVal+v;
        }
        double res=pVal/pval_And_pNotVal;
        int IntRes=(int)(res*100000);
        res=IntRes/100000.0;
        return res;
    }


}
