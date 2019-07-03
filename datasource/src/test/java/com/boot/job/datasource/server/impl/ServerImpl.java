package com.boot.job.datasource.server.impl;

import com.boot.job.datasource.rpc.IRpcService;
import com.boot.job.datasource.server.Server;
import com.boot.job.datasource.service.IHelloService;
import com.boot.job.datasource.service.impl.HelloServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Xingyu Sun
 * @date 2019/6/14 9:27
 */
public class ServerImpl implements Server {

    /**
     * 线程池 接收客户端调用
     **/
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
    /**
     * 服务注册缓存
     **/
    public static final Map<String, Class<?>> serviceRegistry = new HashMap<>(16);

    @Override
    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        try {
            while (true) {
                executor.execute(new ServiceTask(server.accept()));
            }
        } finally {
            server.close();
        }

    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    @Override
    public void register(Class<? extends IRpcService> serviceInterface, Class<? extends IRpcService> impl) {
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    private static class ServiceTask implements Runnable {

        Socket client;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();

                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class<?> serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + "not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                //将执行结果反序列化 通过socket返给客户端
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

}
