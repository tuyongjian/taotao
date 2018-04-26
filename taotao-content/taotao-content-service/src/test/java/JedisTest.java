import org.junit.Test;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by len on 2018/4/26.
 */
public class JedisTest {

    /**
     * 连接单机版本的redis
     */
    @Test
    public void testJedisSingle(){
        Jedis jedis = new Jedis("192.168.92.129",6379);
        jedis.set("mytest","123");
        String result = jedis.get("mytest");
        System.out.println(result);
        jedis.close();
    }

    /**
     * 连接池中获取redis
     */

    @Test
    public void testJedisPool(){
        JedisPool jedisPool = new JedisPool("192.168.92.129",6379);
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("mytest");
        System.out.println(result);
        jedis.close();
    }

    /**
     * 使用集群版本
     */

    @Test
    public void testJedisCluster(){
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.92.129",7001));
        nodes.add(new HostAndPort("192.168.92.129",7002));
        nodes.add(new HostAndPort("192.168.92.129",7003));
        nodes.add(new HostAndPort("192.168.92.129",7004));
        nodes.add(new HostAndPort("192.168.92.129",7005));
        nodes.add(new HostAndPort("192.168.92.129",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("jedisCluster","123456");
        System.out.println(jedisCluster.get("jedisCluster"));
        jedisCluster.close();
    }

}
