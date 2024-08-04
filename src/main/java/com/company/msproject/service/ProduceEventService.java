package com.company.msproject.service;

import com.company.commoneventlib.enums.ActionEnum;
import com.company.msproject.entity.Product;

public interface ProduceEventService {

    void buildAndSendEvent(Product product, Long previousCategoryId, ActionEnum action);
}
