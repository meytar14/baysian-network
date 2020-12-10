import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Main
{
    public static void main(String[] args)
    {
        BufferedReader br;
        FileReader fr;
        try {
            File input = new File("C:\\Users\\meyta\\IdeaProjects\\baysian-network\\untitled\\src\\input1.txt");
            fr = new FileReader(input);
            br=new BufferedReader(fr);
            String line;

            br.readLine();
            line= br.readLine();
            String line2=line.substring(line.indexOf(":")+2);
            String[] instancesNames=line2.split(",");
            HashMap<String,instance> instances=new HashMap<String,instance>();
            for(int i=0;i<instancesNames.length;i++)//insert the instances into "instances"
            {
                instances.put(instancesNames[i],new instance(instancesNames[i]));
            }
            br.readLine();
            //////insert all the date into each instance
            for(int j=0;j<instances.size();j++)
            {
                line=br.readLine();
                String var=line.substring(4);
                instance temp= instances.get(var);
                //insert the values
                line= br.readLine();
                line2=line.substring(line.indexOf(":")+2);
                String[] values=line2.split(",");
                temp.setValues(values);
                //insert parents
                line= br.readLine();
                line2=line.substring(line.indexOf(":")+2);
                if(!line2.equalsIgnoreCase("none")) {
                    String[] parentsNames = line2.split(",");
                    HashMap<String, instance> parents = new HashMap<String, instance>();
                    for (int i = 0; i < parentsNames.length; i++) {
                        parents.put(parentsNames[i], instances.get(parentsNames[i]));
                    }
                    temp.setParents(parents);
                }
                else
                {
                    temp.setParents(null);
                }
                //insert CPT
                br.readLine();//skip the "CPT:" line
                HashMap<HashMap<String,String>,HashMap<String,Double>> cpthm=new HashMap<HashMap<String,String>,HashMap<String,Double>>();//Hashmap to insert later into the cpt
                if(temp.getParents()==null) //if temp doesn't have parents
                {
                    HashMap<String,String>insideCpthm =new HashMap<String, String>();
                    HashMap<String,Double>insideCpthm2 =new HashMap<String, Double>();//<value(true/false..),the p(value)>
                    line = br.readLine();
                    line2=line.substring(1);
                    String[]e= line2.split("=");
                    for(int i=0;i<e.length;i++)
                    {
                        String[] e2=e[i].split(",");
                        insideCpthm2.put(e2[0],Double.parseDouble(e2[1]));
                    }
                    temp.theMissingValue(insideCpthm2);
                    cpthm.put(null,insideCpthm2);
                    CPT cpt=new CPT(cpthm);
                    temp.setCpt(cpt);
                }
                else
                {
                    int numOfOptions=1;
                    for(instance parant:temp.getParents().values())
                    {
                        numOfOptions=numOfOptions*parant.getValues().length;
                    }

                    for(int i=0;i<numOfOptions;i++)
                    {
                        HashMap<String,String>insideCpthm =new HashMap<String, String>();
                        HashMap<String,Double>insideCpthm2 =new HashMap<String, Double>();//<value(true/false..),the p(value)>
                        line = br.readLine();
                        String[]e= line.split("=");
                        String[] parentsValues=e[0].split(",");
                        int count=0;
                        for(instance parent:temp.getParents().values())
                        {
                            insideCpthm.put(parent.getName(),parentsValues[count]);
                            count++;
                        }
                        for(int k=1;k<e.length;k++)
                        {
                            String[] e2=e[k].split(",");
                            insideCpthm2.put(e2[0],Double.parseDouble(e2[1]));
                        }
                        temp.theMissingValue(insideCpthm2);
                        cpthm.put(insideCpthm,insideCpthm2);
                    }
                    CPT cpt=new CPT(cpthm);
                    temp.setCpt(cpt);
                }
                br.readLine();
            }
            br.readLine();

            ////////insert the qeuries
            ArrayList<Querie>queries =new ArrayList<Querie>();
            while((line= br.readLine())!=null)
            {
                Querie temp=new Querie();
                line2=line.substring(line.indexOf("(")+1,line.indexOf("|"));
                String[] e=line2.split("=");
                temp.setpName(e[0]);
                temp.setpValue(e[1]);
                line2=line.substring(line.indexOf("|")+1,line.indexOf(")"));
                e=line2.split(",");
                String[] e2;
                HashMap<String,String> evidence=new HashMap<String, String>();
                for(int i=0;i<e.length;i++)
                {
                    e2=e[i].split("=");
                    evidence.put(e2[0],e2[1]);
                }
                temp.setEvidence(evidence);
                String algo=line.substring(line.indexOf(")")+2);
                temp.setAlgorithmNum(Integer.parseInt(algo));
                queries.add(temp);
            }
            double[][] res=new double[queries.size()][3];
            Iterator<Querie> iter= queries.listIterator();
            Querie temp;
           for(int i=0;i<res.length;i++)
            {
                temp=iter.next();
                res[i][0]=temp.algo1(instances);
                res[i][1]=temp.getCounterAdd();
                res[i][2]=temp.getCounterMul();
            }
            for(int i=0;i<res.length;i++)
            {
                System.out.println(res[i][0]+","+res[i][1]+","+res[i][2]);
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
