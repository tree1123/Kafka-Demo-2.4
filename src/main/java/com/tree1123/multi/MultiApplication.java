package com.tree1123.multi;

/**
 * @author jn
 * @date 2019/12/27
 * 多线程消费kafka 执行类
 */
public class MultiApplication {

    public static void main(String[] args) {

        String bootstrapServers = "kafka01:9092，kafka02:9092";
        String groupId = "test";
        String topic = "test_topic";
        int consumerNum = 3;
        ConsumerGroup cg = new ConsumerGroup(consumerNum,bootstrapServers,groupId,topic);
        cg.execute();
    }
}
