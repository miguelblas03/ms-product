package com.company.msproject.service;

import com.company.commoneventlib.enums.ActionEnum;
import com.company.commoneventlib.event.ProductEvent;
import com.company.msproject.entity.Category;
import com.company.msproject.entity.Product;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.event.EventProducer;
import com.company.msproject.service.impl.ProduceEventServiceImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProduceEventServiceImplTest {

    private final EventProducer eventProducer = mock(EventProducer.class);

    private final ProduceEventServiceImpl produceEventService = new ProduceEventServiceImpl(eventProducer);

    @Test
    void buildAndSendEvent() {
        Category category = new Category(1L, "Category A", "Category A", StatusEnum.ACTIVE);
        Product product = new Product(1L, "Product A", "Product A", category, StatusEnum.ACTIVE);

        produceEventService.buildAndSendEvent(product, 2L, ActionEnum.CREATE_PRODUCT);

        verify(eventProducer).produce(any(ProductEvent.class), eq("cloud-update-statistics"));
    }
}
