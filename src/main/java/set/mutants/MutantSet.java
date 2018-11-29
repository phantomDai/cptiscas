package set.mutants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

public class MutantSet {
    /**
     * 存放变异体信息的列表
     */
    private List<Mutant> mutants;

    /**
     * 构造函数，根据指定的测试对象以及测试场景生成相应的变异体集合
     * @param objectName 测试对象
     * @param scenario 测试场景：1，顺序顺序；2，并发并发；3，顺序并发；4，并发顺序
     */
    public MutantSet(String objectName, String scenario) {
        readFile(objectName,scenario);
    }

    /**
     * 根据指定的测试对象以及测试场景读取mutantsinfo目录下的文件
     * @param objectName 测试对象的名字
     * @param scenario 测试场景
     * @return 文件中变异体的名字
     */
    private void readFile(String objectName, String scenario){
        mutants = new ArrayList<Mutant>();

        //对应文件的绝对路径
        String path = System.getProperty("user.dir") + separator + "mutantsinfo" + separator + objectName + separator + scenario;

        File file = new File(path);

        //文件不存在抛出异常，存在则读取文件内容
        if (!file.exists()) {
            System.out.println("文件不存在");
        }else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String temp = "";
                while ((temp = br.readLine()) != null){
                    String fullname = "";
                    fullname = "mutants." + objectName + "." + temp + "." + objectName ;
                    Mutant mutant = new Mutant(temp,fullname);
                    mutants.add(mutant);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void add(Mutant mutant) {
        mutants.add(mutant);
    }

    public Mutant get(int index){
        Mutant mutant = null ;
        mutant = mutants.get(index);
        return mutant;
    }

    public boolean isEmpty(){
        return mutants.isEmpty();
    }

    public Mutant remove(int index){
        return mutants.remove(index);
    }

    public int numOfMutants(){
        return mutants.size();
    }

    public void printMutant(){
        for (int i = 0; i < mutants.size(); i++) {
            System.out.println(mutants.get(i).getFullClassName());
        }
    }

    public String getMutantFullName(int index){
        Mutant mutant = null ;
        mutant = mutants.get(index);
        return mutant.getFullClassName();
    }
    public String getMutantID(String mutantFullName){
        for (int i = 0; i < mutants.size(); i++) {
            if (mutantFullName.equals(mutants.get(i).getId())){
                return String.valueOf(i);
            }
        }
        return String.valueOf(-1) ;
    }

    public String getMutantID(int index){
        Mutant mutant = mutants.get(index);
        return mutant.getId();
    }


    public int size(){ return mutants.size(); }


    public static void main(String[] args) {
        MutantSet set = new MutantSet("SimpleLinear", "sequentialAndsequential");
        System.out.println(set.size());
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.getMutantFullName(i) + "\n");
        }

    }

}
