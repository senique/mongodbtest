package com.mng.utils.sequence;

import java.lang.reflect.Field;
import javax.annotation.Resource;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

public class SaveMongoEventListener extends AbstractMongoEventListener<Object>
{
    @Resource
    private MongoTemplate mongoTemplate;
    
//  below version 1.7.2.RELEASE
//  public void onBeforeConvert(final Object source) {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        if(source != null) {
          ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
              @Override
              public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                  ReflectionUtils.makeAccessible(field);
                  if (field.isAnnotationPresent(GeneratedValue.class)) {
                      field.set(source, getNextSequenceId(source.getClass().getSimpleName()));
                  }
                  if (field.isAnnotationPresent(TemporalValue.class)) {
//                      field.set(source, new SimpleDateFormat("yyyy-MM-dd").format((Date) field.get(source)));
//                      field.set(source, DateUtils.getDateBeginTime((Date) field.get(source)));
                  }
               }
          });
        }
   }
   
 /**
  * 
  * @author：luocj
  * @createtime ： 2017年12月5日 下午3:06:14
  * @description 获得下一个sequenceId
  * @since version 初始于版本 v0.0.1 
  * @param id
  * @return 
  */
 private Long getNextSequenceId(String id) {
     Query query = new Query(Criteria.where("id").is(id));
     Update update = new Update().inc("sequenceId", 1);//自增1
     FindAndModifyOptions options = new FindAndModifyOptions();
     options.upsert(true);
     options.returnNew(true);
     Sequences seqId = mongoTemplate.findAndModify(query, update, options, Sequences.class);
     return seqId.getSequenceId();
 }
 
}
