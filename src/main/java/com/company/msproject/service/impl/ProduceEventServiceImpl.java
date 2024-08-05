package com.company.msproject.service.impl;

import com.company.commoneventlib.enums.ActionEnum;
import com.company.commoneventlib.event.ProductEvent;
import com.company.msproject.entity.Product;
import com.company.msproject.event.EventProducer;
import com.company.msproject.service.ProduceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduceEventServiceImpl implements ProduceEventService {

    private final EventProducer eventProducer;

    @Async
    @Override
    public void buildAndSendEvent(Product product, Long previousCategoryId, ActionEnum action) {
        ProductEvent productEvent = new ProductEvent();
        productEvent.setId(product.getId());
        productEvent.setName(product.getName());
        productEvent.setDescription(product.getDescription());
        productEvent.setPreviousCategoryId(previousCategoryId);
        productEvent.setCurrentCategoryId(product.getCategory().getId());
        productEvent.setStatus(product.getStatus().name());
        productEvent.setAction(action);
        eventProducer.produce(productEvent, "cloud-update-statistics");
    }
}
