package com.mng.mongo.template.repository;

import com.mng.domain.DataItem;
import com.mng.mongo.template.dao.MongoTemplateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DataItemRepository extends MongoTemplateRepository
{

    @Override
    protected Class<DataItem> getEntityClass() {
        return DataItem.class;
    }

}
