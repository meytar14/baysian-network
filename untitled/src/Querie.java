import java.util.HashMap;

public class Querie
{
    private int algorithmNum;
    private HashMap<String,String> p;//<the name of the instace,the value(true/false)>
    private HashMap<String,String> evidence;// <name of the instance,value of the instance>

    public Querie()
    {
        this.algorithmNum=0;
        this.evidence=null;
        this.p=null;
    }

    public int getAlgorithmNum() {
        return algorithmNum;
    }

    public void setAlgorithmNum(int algorithmNum) {
        this.algorithmNum = algorithmNum;
    }

    public HashMap<String, String> getP() {
        return p;
    }

    public void setP(HashMap<String, String> p) {
        this.p = p;
    }

    public HashMap<String, String> getEvidence() {
        return evidence;
    }

    public void setEvidence(HashMap<String, String> evidence) {
        this.evidence = evidence;
    }
}
