package io.pine.examples.payment.common;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.net.URI;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Frank
 * @sinace 2018/10/25 0025.
 */
public class LockCase1Test {
    @Test
    public void testLockCase5() {
        //定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10,
                1, TimeUnit.SECONDS,
                new SynchronousQueue<>());

        String lockDefaultName = "lockName";
        //添加10个线程获取锁
        for (int i = 0; i < 10; i++) {
            final String lockName = lockDefaultName + i;
            pool.submit(() -> {
                try {
                    Jedis jedis = new Jedis(URI.create("redis://10.200.151.10:6379"));
                    LockCase1 lock = new LockCase1(jedis, lockName);
                    lock.lock();

                    //模拟业务执行15秒
                    lock.sleepBySencond(15);

                    lock.unlock();
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }

        //当线程池中的线程数为0时，退出
        while (pool.getPoolSize() != 0) {}
    }
}
