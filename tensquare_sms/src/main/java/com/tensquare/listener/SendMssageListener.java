package com.tensquare.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Name: SendMssageListener
 * User: zhaocq
 * Date: 2019/5/11 0011
 * Time: 9:01
 * Description: 短信发送的监听器
 */
@Component
@RabbitListener(queues = "sms")
public class SendMssageListener {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private Environment env;




    /* *
     * @Author zhaocq
     * @Description //TODO 发送短信的任务
     * @Date 9:02 2019/5/11 0011
     * @Param []
     * @return void
     **/
    @RabbitHandler
    public void sendms(Map<String,String> map){
        System.out.println("手机号：" + map.get("mobile"));
        System.out.println("验证码：" + map.get("code"));
        String  mobile = map.get("mobile");
        String  checkcode = map.get("code");
        String templateCode =env.getProperty("aliyun.sms.template_code");
        String signName = env.getProperty("aliyun.sms.sign_name");
        // 调用 通信平台 给指定的手机发送验证码
        try {
            smsUtil.sendSms(mobile,templateCode,signName,"{\"checkcode\":\""+checkcode+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
