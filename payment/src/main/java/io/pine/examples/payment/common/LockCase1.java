package io.pine.examples.payment.common;

import redis.clients.jedis.Jedis;

import java.time.LocalTime;

/**
 * @author Frank
 * @sinace 2018/10/25 0025.
 */
public class LockCase1 extends RedisLock {
    public LockCase1(Jedis jedis, String name) {
        super(jedis, name);
    }

    @Override
    public void lock() {
        while(true){
            String result = jedis.set(lockKey, lockValue, LockConstants.NOT_EXIST, LockConstants.SECONDS, 30);
            if(LockConstants.OK.equals(result)){
                System.out.println(Thread.currentThread().getId()+"加锁成功!时间:"+ LocalTime.now());

                //开启定时刷新过期时间
                isOpenExpirationRenewal = true;
                scheduleExpirationRenewal();
                break;
            }

            System.out.println("线程id:"+Thread.currentThread().getId() + "获取锁失败，休眠10秒!时间:"+LocalTime.now());
            //休眠10秒
            sleepBySencond(10);
        }
    }

    @Override
    public void unlock() {
//        String lockValue = jedis.get(lockKey);
//        if (lockValue.equals(lockValue)){
//            jedis.del(lockKey);
//        }
        // 使用lua脚本进行原子删除操作
        String checkAndDelScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        jedis.eval(checkAndDelScript, 1, lockKey, lockValue);
        isOpenExpirationRenewal = false;
    }
}
