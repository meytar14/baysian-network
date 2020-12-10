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
        int counterMul=0;
        int counterAdd=0;
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
            pOfpValue=pOfpValue+res;
            counterAdd++;
        }
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
                    pOfpNotValue = pOfpNotValue + res;
                    counterAdd++;
                }
            }
        }
        setCounterAdd(counterAdd);
        setCounterMul(counterMul);
        double res=pOfpValue/(pOfpNotValue+pOfpValue);
        int IntRes=(int)(res*100000);
        res=IntRes/100000.0;
        return res;
    }
}
