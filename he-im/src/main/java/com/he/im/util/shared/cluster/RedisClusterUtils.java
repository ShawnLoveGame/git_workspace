package com.he.im.util.shared.cluster;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Set;

public class RedisClusterUtils extends JedisCluster {

	public RedisClusterUtils(Set<HostAndPort> nodes, int timeout) {
		super(nodes, timeout);
	}

	public RedisClusterUtils(Set<HostAndPort> nodes) {
		super(nodes);
	}

	public RedisClusterUtils(Set<HostAndPort> nodes, int timeout,
			int maxRedirections) {
		super(nodes, timeout, maxRedirections);
	}

	public RedisClusterUtils(Set<HostAndPort> nodes,
			final GenericObjectPoolConfig poolConfig) {
		super(nodes, poolConfig);
	}

	public RedisClusterUtils(Set<HostAndPort> nodes, int timeout,
			final GenericObjectPoolConfig poolConfig) {
		super(nodes, timeout, poolConfig);
	}

	public RedisClusterUtils(Set<HostAndPort> jedisClusterNode, int timeout,
			int maxRedirections, final GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, timeout, maxRedirections, poolConfig);
	}

	/**
	 * 参选，如果参选成功将为key续期,如果已有被选举人则判断是否为自己 为自己则续期
	 * 
	 * @param key
	 *            参选项目
	 * @param candidates
	 *            候选人
	 * @param timeOut
	 *            参选成功后的过期时间
	 * @return true 参选成功并续期成功 或 被选举人为自己并续期成功， false 参选失败或被选举人不为自己
	 */
	public boolean electioneer(String key, String candidates, int timeOut) {
		try {
			int setnx = setnx(key, candidates).intValue();
			boolean bool = false;
			if (setnx == 1) {
				expire(key, timeOut);
				bool = true;
			} else {
				String electee = get(key);
				if (electee != null && electee.equals(candidates)) {
					expire(key, timeOut);
					bool = true;
				}
			}
			return bool;
		} catch (Exception e) {
			throw new JedisException(e);
		}
	}

	/**
	 * 订阅指定shardkey下的某些频道
	 * 
	 * @param shardkey
	 *            用于shard定向
	 * @param jedisPubSub
	 *            用于回调
	 * @param channels
	 *            频道名称
	 * @see link http://redis.cn/commands/subscribe.html
	 */
	public void subscribe(String shardkey, JedisPubSub jedisPubSub,
			String... channels) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis.getShard(shardkey).subscribe(jedisPubSub, channels);
//		} catch (Exception e) {
//			throw new JedisException(e);
//		}  
	}

}
