package com.boot.job.datasource.easy;

import com.boot.job.datasource.client.Client;
import com.boot.job.datasource.server.Server;
import com.boot.job.datasource.server.impl.ServerImpl;
import com.boot.job.datasource.service.IHelloService;
import com.boot.job.datasource.service.impl.HelloServiceImpl;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author Xingyu Sun
 * @date 2019/6/14 9:37
 */
public class Tests {

    public static void main(String[] args) throws Exception {
        Server center = new ServerImpl();
        center.register(IHelloService.class,HelloServiceImpl.class);
        center.start();
    }

    @Test
    public void test1(){
        IHelloService service  = Client.getRemoteProxyObj(IHelloService.class, new InetSocketAddress(8080));
        System.out.println(service.sayHi("张三", "新年快乐!"));
    }

    @Test
    public void e(){
        Map<String,Object> map = new HashMap<>();
        map.put("guid","123");
        map.put("name","sxy");
        map.put("type",1);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("guid","123");
        map2.put("name","sxy");
        map2.put("type",1);
        Map<String,Object> map3 = new HashMap<>();
        map3.put("guid","123");
        map3.put("name","sxy");
        map3.put("type",0);
        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map2);
        list.add(map3);
        System.out.println(list);
        Set<Map> set = new HashSet<>(list);
        System.out.println(set);
    }
}
