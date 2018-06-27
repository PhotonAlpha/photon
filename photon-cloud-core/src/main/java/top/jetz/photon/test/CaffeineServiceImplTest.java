/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 2:08:29 PM
 * @version: V1.0  
 */
package top.jetz.photon.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.jetz.photon.constant.Constant;



@Service
@CacheConfig(cacheNames = {Constant.CACHE_INFO_LIMIT, Constant.CACHE_REQUEST_LIMIT})
public class CaffeineServiceImplTest {
	private final static Logger logger = LoggerFactory.getLogger(CaffeineServiceImplTest.class);
	
	@Autowired
	private CacheManager cacheManager;
	
	public String dummyExecuteScript(final Long id) {
        String res = id.toString();
        System.out.println(res);
        //if editable execute the shell command else get message form cache
        Cache cache = cacheManager.getCache(Constant.CACHE_REQUEST_LIMIT);
        cache.put(res, "123");
        com.github.benmanes.caffeine.cache.Cache nativeCoffeeCache = (com.github.benmanes.caffeine.cache.Cache) cache.getNativeCache();
        System.out.println(nativeCoffeeCache.asMap().toString());
        System.out.println(nativeCoffeeCache.stats());
        System.out.println(nativeCoffeeCache.getIfPresent("123"));
        String infoDto = cache.get(res, String.class);
        System.out.println(infoDto);
        return infoDto;
    }
    
	
	@Cacheable
	public String play(Long appId, String type, String operator) {
	    System.out.println("########################Executing: " + this.getClass().getSimpleName() + ".play(id:"+appId+";type:"+type+";operator:"+operator+");");
        return "Playing(id:"+appId+";type:"+type+";operator:"+operator+")";
	}
	
	@Cacheable(condition = "#content.equals('trombone')")
	public List<String> put(String content) {
		logger.info("Executing: {}.play(\"{}\")", this.getClass().getSimpleName(), content);
		return Arrays.asList("playing " + content + "!");
	}
	
	@CachePut(key = "#key", condition = "#infos.editable = true ", unless = "#result == null")
	public String putKey(final String ...keys) {
		logger.info("Executing: {}.play(\"{}\")", this.getClass().getSimpleName(), keys);
		
		List<String> list = new ArrayList<String>(100);
		try {
    		for(int i = 1; i < 100; i++) {
    			logger.info("start execute command:{}", i);
    			list.add("start execute command:" + i);
    				Thread.sleep(100);
    		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list.toString();
	}
}
