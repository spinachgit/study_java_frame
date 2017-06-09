package com.spinach.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spinach.domain.Stock;
import com.spinach.domain.UserInfo;

/**
 * 服务器定时发送信息到客户端的例子
 */
@Controller
//@RequestMapping("/home")
public class HomeController {

	@Autowired
	private SimpMessagingTemplate template;
	private TaskScheduler scheduler = new ConcurrentTaskScheduler();
	private List<Stock> stockPrices = new ArrayList<Stock>();
	private Random rand = new Random(System.currentTimeMillis());

	/**
	 * 处理信息并发送给客户端
	 */
	private void updatePriceAndBroadcast() {
		for (Stock stock : stockPrices) {
			double chgPct = rand.nextDouble() * 5.0;
			if (rand.nextInt(2) == 1)
				chgPct = -chgPct;
			stock.setPrice(stock.getPrice() + (chgPct / 100.0 * stock.getPrice()));
			stock.setTime(new Date());
		}
		template.convertAndSend("/topic/price", stockPrices);
	}

	/**
	 * controller初始化的时候，就初始化一个定时任务
	 */
	@PostConstruct
	private void broadcastTimePeriodically() {
		scheduler.scheduleAtFixedRate(new Runnable() {
			// @Override
			public void run() {
				System.out.println("自动任务执行====================================" + new Date());
				// updatePriceAndBroadcast();
			}
		}, 10000);
	}

	/**
	 * 增加信息
	 */
	@MessageMapping("/addStock")
	public void addStock(Stock stock) throws Exception {
		stockPrices.add(stock);
		updatePriceAndBroadcast();
	}
	
	/*public String sendMessage(UserInfo user) throws Exception {
		Object message = SpringContextUtils.getBean("homeController");
		return "testMessage";
	}*/
	/**
	 * 移除所有
	 */
	@MessageMapping("/removeAllStocks")
	public void removeAllStocks() {
		stockPrices.clear();
		updatePriceAndBroadcast();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("homeController --- home!");
		return "index";
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		System.out.println("homeController --- index!");
		return "index";
	}
	@RequestMapping(value = "message", method = RequestMethod.GET)
	public String message() {
		System.out.println("homeController --- /message!");
		return "/message";
	}
	
}
