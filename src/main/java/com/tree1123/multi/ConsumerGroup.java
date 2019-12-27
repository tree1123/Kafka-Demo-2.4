package com.tree1123.multi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jn
 * @date 2019/12/27
 * 多线程消费kafka   多个消费者
 */
public class ConsumerGroup {

    private List<ConsumerRunnable> consumers;

    public ConsumerGroup(int consumerNum,String bootstrapServers,String groupId,String topic){

        consumers = new ArrayList<>(consumerNum);

        for(int i=0;i < consumerNum;i++){
            ConsumerRunnable ConsumerRunnable = new ConsumerRunnable(bootstrapServers,groupId,topic);
            consumers.add(ConsumerRunnable);
        }
    }

    public void execute(){

        for(ConsumerRunnable consumerRunnable:consumers){
            new Thread(consumerRunnable).start();
        }
    }
}
