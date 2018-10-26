package mr;

/**
 * 蜕变关系的接口
 * @author phantom
 * @date 20181024
 */
public interface MetamorphicRelations {
     /**
      * 该接口跟去随机产生的实验数据，生成原始测试数据
      * @param randomArray 随机产生的数据
      * @return 原始测试数据
      */
     int[] sourceList(int[] randomArray);


}
