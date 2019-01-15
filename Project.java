

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {

    ArrayList<Function> Flist = new ArrayList<Function>();
    String name = "Untitled project";
    double XRange = 10;
    double YRange = 10;
    int Samples = 1000;

    public Project(){

    }

    public void setParameters(int samples, double rangeX, double rangeY){
        XRange = rangeX;
        YRange = rangeY;
        Samples = samples;
    }

    public void addFunction(Function f){
        Flist.add(f);
    }
    public void changeFunctions(ArrayList<Function> f){
       Flist = f;
    }
    public ArrayList<Function> getFunctions(){
        return Flist;
    }

}
