package petstore.common.repository;

import java.util.List;

public interface CacheRepository {
  /** Default timeout (TTL) in seconds */
  long TIMEOUT_DEFAULT = 86400; // 1 day

  /**
   * Set a new value into a collection in cache (without reset collection TTL (1 day from first
   * put))
   *
   * @param cacheKey Collection key
   * @param hKey Sub-hash key
   * @param object Target value
   * @return true if succeed; false otherwise
   */
  <T> boolean set(String cacheKey, String hKey, T object);

  /**
   * Delete a specific key from cache database
   *
   * @param cacheKey Collection key
   * @param hKey Sub-hash key
   * @return true if succeed; false otherwise
   */
  boolean delete(String cacheKey, String hKey);

  /**
   * Delete a specific key from cache database
   *
   * @param cacheKey Collection key
   * @param hKeys list of Sub-hash key
   * @return number deleted; -1 otherwise
   */
  long delete(String cacheKey, List<String> hKeys);

  /**
   * Set TTL of a cache collection in seconds
   *
   * @param cacheKey Collection key
   * @param hKey Sub-hash key
   * @param ttl TTL in seconds
   * @return true if succeed; false otherwise
   */
  boolean expire(String cacheKey, String hKey, long ttl);

  /**
   * Find a specific value in cache collection
   *
   * @param cacheKey Collection key
   * @param hKey Sub-hash key
   * @param clz Target class
   * @return Target value if exists; null otherwise
   */
  <T> T find(String cacheKey, String hKey, Class<T> clz);
}
