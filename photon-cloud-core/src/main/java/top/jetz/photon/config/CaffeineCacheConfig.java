/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 2:05:14 PM
 * @version: V1.0  
 */
package top.jetz.photon.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import top.jetz.photon.constant.Constant;

@Configuration
public class CaffeineCacheConfig {
	
	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager manager = new CaffeineCacheManager(Constant.CACHE_REQUEST_LIMIT, Constant.CACHE_INFO_LIMIT);
		manager.setAllowNullValues(false);
		manager.setCaffeine(caffeineCacheBuilder());
		return manager;
	}
	
	Caffeine<Object, Object> caffeineCacheBuilder(){
		return Caffeine.newBuilder()
				.initialCapacity(100)
				.maximumSize(200)
				.expireAfterWrite(10, TimeUnit.MINUTES)
//				.expireAfterWrite(10, TimeUnit.SECONDS)
//				.expireAfterAccess(1, TimeUnit.MINUTES)
//				.weakKeys()
				.weakValues()
				.removalListener(new CustomRemovalListener())
				.recordStats();
	}
	class CustomRemovalListener implements RemovalListener<Object, Object>{
        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            System.out.format("removal listerner called with key [%s], cause [%s], evicted [%S]\n", 
                    key, cause.toString(), cause.wasEvicted());
        }
    }
}
