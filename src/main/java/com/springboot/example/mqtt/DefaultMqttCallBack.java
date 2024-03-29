package com.springboot.example.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MQTT 消息订阅回调
 * <a href="https://www.emqx.io/docs/zh/v4.4/development/java.html#paho-java-%E4%BD%BF%E7%94%A8%E7%A4%BA%E4%BE%8B">...</a>
 *
 * @author zhangyonghong
 * @date 2021/6/13
 */
public class DefaultMqttCallBack implements MqttCallback {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultMqttCallBack.class);

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        LOG.info(">>>>> CONNECTION_LOST");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe 后得到的消息会执行到这里面
        LOG.info(">>>>> MESSAGE_ARRIVED, TOPIC: [{}], QOS: [{}], MESSAGE: [{}]", topic, message.getQos(), new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOG.info(">>>>> DELIVERY_COMPLETE: [{}]", token.isComplete());
    }
}
