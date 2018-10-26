package set.mutants;

import lombok.Getter;
import lombok.Setter;

public class Mutant {
    /**
     * 变异体的名字，例如SAN_add1
     */
    @Getter @Setter private String id ;

    /**
     * 变异体的绝对地址
     */
    @Getter @Setter private String fullClassName ;


    /**
     * 构造函数，初始化id和className
     * @param id 变异体的名字
     * @param fullClassName 变异体的全名
     */
    public Mutant(String id,String fullClassName) {
        setId(id);
        setFullClassName(fullClassName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
