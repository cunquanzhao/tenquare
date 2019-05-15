package com.tensquare.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Name: Customer1
 * User: zhaocq
 * Date: 2019/5/9 0009
 * Time: 11:29
 * Description: 消费者
 */
@Component
@RabbitListener(queues = "kudingyu")
public class Customer3 {

    /* *
     * @Author zhaocq
     * @Description //TODO 消费的方法
     * @Date 11:31 2019/5/9 0009
     * @Param []
     * @return void
     **/
    @RabbitHandler
    public void getMessage(String message){
        System.out.println("itkudingyu" + message);
    }
}
