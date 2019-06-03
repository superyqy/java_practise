package utility;


import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;


public class RedisRepository {
    private static StringRedisTemplate redisTemplate;
    private static Object lock = new Object();


    //配置redistemplate
    public RedisRepository(String nodes, String password){
        synchronized (lock){
            if(redisTemplate != null) return;

            redisTemplate = new StringRedisTemplate();

            redisTemplate.setConnectionFactory(getJedisConnectionFacotory(
                    this.getJedisCluster(nodes, password),
                    this.getJedistPoolConfig()
            ));
            redisTemplate.afterPropertiesSet();
        }

    }


    public String get(String key){
        String value = "";
        if(redisTemplate.hasKey(key)){
            value = redisTemplate.opsForValue().get(key);
        }

        return value;

    }

    public String getHashKey(String key, String hashKey){
        String value = "";
        if(redisTemplate.hasKey(key)){
            value = redisTemplate.opsForHash().get(key, hashKey).toString();
        }

        return value;
    }


    private JedisConnectionFactory getJedisConnectionFacotory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;

    }

    private JedisPoolConfig getJedistPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(2000);
        jedisPoolConfig.setTestOnBorrow(true);

        return jedisPoolConfig;

    }


    private RedisClusterConfiguration getJedisCluster(String nodes, String password){
        RedisClusterConfiguration redisClusterConfiguration  = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(100);

        List<RedisNode> nodeList = new ArrayList<>();

        String[] cNodes = nodes.split(",");
        //分隔出集群节点
        for(String node: cNodes){
            String[] hp = node.split(":");
            nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }

        redisClusterConfiguration.setClusterNodes(nodeList);

        redisClusterConfiguration.setPassword(password);

        return redisClusterConfiguration;

    }


    public static void main(String[] args){
        String server1 = "127.0.0.1";
        String server2 = "128.0.0.1";
        int port = 6371;
        String password = "test";

        RedisRepository repository = new RedisRepository(server1+":"+port+","+server2+":"+port, password);
        repository.get("tst");
    }

}

