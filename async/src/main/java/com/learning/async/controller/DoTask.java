package com.learning.async.controller;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.async.asynctask.AsyncTask;
 
@RestController
@RequestMapping("tasks")
public class DoTask {
     
    @Autowired
    private AsyncTask asyncTask;
     
    @RequestMapping("test1")
    public String test1() throws Exception {
         
        long start = System.currentTimeMillis();
         
        Future<Boolean> a = asyncTask.doTask11();
        Future<Boolean> b = asyncTask.doTask22();
        Future<Boolean> c = asyncTask.doTask33();
        System.out.println("先执行...不等结果...");
        while (!a.isDone() || !b.isDone() || !c.isDone()) {
            if (a.isDone() && b.isDone() && c.isDone()) {
                break;
            }
        }
         
        long end = System.currentTimeMillis();
         
        String times = "任务全部完成，总耗时：" + (end - start) + "毫秒";
        System.out.println(times);
         
        return times;
    }
}