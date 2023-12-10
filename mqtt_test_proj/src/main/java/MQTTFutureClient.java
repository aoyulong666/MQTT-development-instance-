
import com.alibaba.fastjson.JSONObject;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;
import java.util.UUID;

/**
 * java 开发（采用 Future 模式） 订阅主题
 */
public class MQTTFutureClient {

    // MQTT 服务器主机地址
    private final static String CONNECTION_STRING = "tcp://192.168.10.100:1883";
    private final static boolean CLEAN_START = true;
    // 低耗网络，但是又需要及时获取数据，心跳 30s
    private final static short KEEP_ALIVE = 30;
    public final static long RECONNECTION_ATTEMPT_MAX = 6;
    public final static long RECONNECTION_DELAY = 2000;
    //发送最大缓冲为 2M
    public final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;
    public String publicMQTTFutureClient(JSONObject
                                                 jsBodyParameter) {
// 定义变量
        String jsonStr = "";
// 创建 MQTT 对象
        MQTT mqtt = new MQTT();
        try {
// 自动生成的 MQTT Client Id
            String CLIENT_ID = UUID.randomUUID().toString().replace("-", "");
// 设置 mqtt broker 的 ip 和端口
            mqtt.setHost(CONNECTION_STRING);
// 连接前清空会话信息
            mqtt.setCleanSession(CLEAN_START);
// 设置重新连接的次数
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
// 设置重连的间隔时间
            mqtt.setReconnectDelay(RECONNECTION_DELAY);
// 设置心跳时间
            mqtt.setKeepAlive(KEEP_ALIVE);
// 设置缓冲的大小
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
// 设置客户端 id、用户名和密码
            mqtt.setClientId(CLIENT_ID);
            mqtt.setUserName("admin");
            mqtt.setPassword("password");
// 获取 mqtt 的连接对象 BlockingConnection
            final FutureConnection connection = mqtt.futureConnection();
            connection.connect();
// 订阅主题
            Topic[] subscribeTopics = { new Topic("mqtt/#",
                    QoS.AT_LEAST_ONCE) };
            connection.subscribe(subscribeTopics);
// 发布主题
            String publishTopic = "mqtt/face/1376464";
            connection.publish(publishTopic,
                    jsBodyParameter.toString().getBytes("UTF-8"), QoS.AT_LEAST_ONCE,
                    false);
            Future<Message> futrueMessage = connection.receive();
            Message message = futrueMessage.await();
// 打印订阅的消息
            System.out.println(message.getPayloadBuffer());
            String msg = String.valueOf(message.getPayloadBuffer());
            jsonStr = msg;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return jsonStr;
    }
}
