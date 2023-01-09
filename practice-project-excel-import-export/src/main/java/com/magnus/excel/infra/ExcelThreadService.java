package com.magnus.excel.infra;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * @author gaosong
 */
@Service
public class ExcelThreadService {
    private ExecutorService executor;

    private final static int CORE_POOL_SIZE = 20;
    private final static int MAX_POOL_SIZE = 40;
    private final static long KEEP_ALIVE_TIME = 10000L;
    private final static int CAPACITY = 1000;
    private final static String PRE_THREAD = "exprot-pool-%d";


    @PostConstruct
    public void init() {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>(CAPACITY), new ThreadFactoryBuilder().setNameFormat(PRE_THREAD).build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @PreDestroy
    public void destroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

}
