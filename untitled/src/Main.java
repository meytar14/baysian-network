import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
           /* while((line=br.readLine())!=null)
            {
                System.out.println(line);
            }*/
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

            for(int j=0;j<instances.size();j++)//insert all the date into each instance
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
                HashMap<String[],Double> cpthm=new HashMap<String[],Double>();//Hashmap to insert later into the cpt
                if(temp.getParents()==null) //if temp doesn't have parents
                {
                    line = br.readLine();
                    line2 = line.substring(line.indexOf(",") + 1);
                    String[] empty={"empty"};
                    cpthm.put(empty,Double.parseDouble(line2));
                    CPT cpt=new CPT(cpthm);
                    temp.setCpt(cpt);
                }
                else
                {
                    for(int i=0;i<Math.pow(2,temp.getParents().size());i++)
                    {
                        line = br.readLine();
                        String[] e=line.split(",=true,");
                        double p=Double.parseDouble(e[1]);
                        String[] parentValues=e[0].split(",");
                        cpthm.put(parentValues,p);
                    }
                    CPT cpt=new CPT(cpthm);
                    temp.setCpt(cpt);
                }
                br.readLine();



            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
