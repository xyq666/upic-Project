package com.upic.common.utils.redis;

import org.springframework.stereotype.Service;

import com.upic.common.utils.redis.service.RedisService;

@Service
public class WebRequestRedisService extends RedisService<Object>{
	  private static final String REDIS_KEY = "TEST_REDIS_KEY";
	@Override
	protected String getRedisKey() {
		return this.REDIS_KEY;
	}

}
