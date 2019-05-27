package com.mod.loan.consumer;

import com.alibaba.fastjson.JSONObject;
import com.mod.loan.service.ReportRecycleRepayStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ReportRecycleRepayStatConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ReportRecycleRepayStatConsumer.class);

    private final ReportRecycleRepayStatService reportRecycleRepayStatService;

    @Autowired
    public ReportRecycleRepayStatConsumer(ReportRecycleRepayStatService reportRecycleRepayStatService) {
        this.reportRecycleRepayStatService = reportRecycleRepayStatService;
    }

    @RabbitListener(queues = "queue_recycle_repay_stat", containerFactory = "recycle_repay_stat")
    @RabbitHandler
    public void queue_recycle_repay_stat(Message mess) {
        try {
            String msg = new String(mess.getBody(), StandardCharsets.UTF_8);
            logger.info("queue_recycle_repay_stat msg:" + msg);

            JSONObject data = JSONObject.parseObject(msg);
            String recycleDate = data.getString("recycleDate");
            String recycledId = data.getString("recycledId");

            reportRecycleRepayStatService.decreaseNotReturnCnt(Long.valueOf(recycledId), recycleDate);
        } catch (Exception e) {
            logger.error("queue_recycle_repay_stat error", e);
        }
    }

    @Bean("recycle_repay_stat")
    public SimpleRabbitListenerContainerFactory pointTaskContainerFactoryLoan(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(1);
        factory.setConcurrentConsumers(1);
        return factory;
    }
}
