import com.taotao.content.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by len on 2018/4/26.
 */
public class testJedisClientPool {

    @Test
    public void testJedisClientPool(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);

        jedisClient.set("client","hello");
        System.out.println(jedisClient.get("client"));
    }
}
