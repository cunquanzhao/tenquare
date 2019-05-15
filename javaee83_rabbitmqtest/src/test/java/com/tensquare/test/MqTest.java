package com.tensquare.test;

import com.tensquare.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Name: MqTest
 * User: zhaocq
 * Date: 2019/5/9 0009
 * Time: 11:26
 * Description: 消息的生产者
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class MqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /* *
     * @Author zhaocq
     * @Description //TODO 直接模式
     * @Date 11:27 2019/5/9 0009
     * @Param []
     * @return void
     **/
    @Test
    public void test(){
        rabbitTemplate.convertAndSend("itcast","直接模式测试");
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 分列模式
     * @Date 11:52 2019/5/9 0009
     * @Param []
     * @return void
     **/
    @Test
    public void test1(){
        rabbitTemplate.convertAndSend("chuanzhi","","分列模式测试");
    }


    /* *
    * @Author zhaocq
    * @Description //TODO 主题模式
    * @Date 11:52 2019/5/9 0009
    * @Param []
    * @return void
    **/
    @Test
    public void test2(){
        rabbitTemplate.convertAndSend("topic83","goods.abc","主题模式测试");
    }
}
