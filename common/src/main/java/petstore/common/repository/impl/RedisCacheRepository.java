package petstore.common.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import petstore.common.config.RedisConfiguration;
import petstore.common.repository.CacheRepository;

@Log4j2
@AutoConfigureAfter(RedisConfiguration.class)
@RequiredArgsConstructor
@Repository
public class RedisCacheRepository implements CacheRepository {
  private static final String SEPARATOR_KEYS = "@";
  private final ObjectMapper mapper;
  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public <T> boolean set(String cacheKey, String hKey, T object) {
    var keyName = getKeyName(cacheKey, hKey);
    try {
      final Boolean existed = redisTemplate.hasKey(keyName);
      redisTemplate.opsForValue().set(keyName, mapper.writeValueAsString(object));
      if (existed == null || !existed) {
        expire(cacheKey, hKey, TIMEOUT_DEFAULT);
      }
      log.info("Cache info saved for {}", cacheKey);
      return true;
    } catch (Exception e) {
      log.error(
          "Unable to put object for key {}: reason: {}, error: {}", keyName, e.getMessage(), e);
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean delete(String cacheKey, String hKey) {
    final String keyName = getKeyName(cacheKey, hKey);
    try {
      return Boolean.TRUE.equals(redisTemplate.delete(keyName));
    } catch (Exception e) {
      log.error(
          "Unable to delete cache item {}: reason: {}, error: {}", keyName, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public long delete(String cacheKey, List<String> hKeys) {
    var listKey = hKeys.stream().map(key -> getKeyName(cacheKey, key)).toList();

    try {
      var result = redisTemplate.delete(listKey);
      return ObjectUtils.isNotEmpty(result) ? result : -1L;
    } catch (Exception e) {
      log.error("Unable to delete cache item {}: reason: {}, error: {}", hKeys, e.getMessage(), e);
    }
    return -1L;
  }

  @Override
  public boolean expire(String cacheKey, String hKey, long ttl) {
    final String keyName = getKeyName(cacheKey, hKey);
    try {
      return BooleanUtils.isTrue(redisTemplate.expire(keyName, ttl, TimeUnit.SECONDS));
    } catch (Exception e) {
      log.warn("Unable to expire cache item {}: reason: {}, error: {}", keyName, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public <T> T find(String cacheKey, String hKey, Class<T> clz) {
    final String keyName = getKeyName(cacheKey, hKey);
    try {
      final String result = String.valueOf(redisTemplate.opsForValue().get(keyName));
      if (result == null) {
        log.warn("Cache item '{}' not exists", keyName);
      } else {
        log.info("Found cached item for '{}'", keyName);
        return mapper.readValue(result, clz);
      }
    } catch (Exception e) {
      log.warn("Unable to find cache item {}: reason: {}, error: {}", keyName, e.getMessage(), e);
    }
    return null;
  }

  /**
   * Generates the cache key by combining the origin cache key and the collection key.
   *
   * @param cacheKey The origin key a.k.a the parent key
   * @param collectionKey The collection key a.k.a the child key
   * @return The combined key for a flattened key
   */
  protected String getKeyName(String cacheKey, String collectionKey) {
    return cacheKey + SEPARATOR_KEYS + collectionKey;
  }
}
