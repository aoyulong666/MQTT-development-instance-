import com.alibaba.fastjson.JSONObject;
/*
 * service 层调用函数，jsBodyParameter 为传入的需要发布的参数
 */

public class Main {

    /*
     * service 层调用函数，jsBodyParameter 为传入的需要发布的参数
     */
    public static String writeToMqttMessageFuture(JSONObject jsBodyParameter) {
        MQTTFutureClient mqttFutureClient = new MQTTFutureClient();
        String messageJosn = mqttFutureClient.publicMQTTFutureClient(jsBodyParameter);
        return messageJosn;
    }

    public static void main(String[] args) {
        JSONObject jsBodyParameter = new JSONObject();// 定义发布参数变量
        jsBodyParameter.put("keyId", "你好！");// 设置参数值
        String response = writeToMqttMessageFuture(jsBodyParameter);
        System.out.println(response);// 打印订阅的消息
    }

}
