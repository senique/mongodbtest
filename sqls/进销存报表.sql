SELECT * from vm_store.vstore_order_express_info t where t.mail_no like '%跨%' ORDER BY t.sent_time desc,t.created_time desc;
SELECT * FROM vm_store.vstore_order_express_info t where t.sent_time > Date('2017-10-01') and t.created_time < DATE('2017-10-01') ORDER BY t.sent_time desc,t.created_time desc;
SELECT * FROM vm_store.vstore_order_express_info t where t.sent_time > Date('2017-09-01') and t.created_time < DATE('2017-09-01') ORDER BY t.sent_time desc,t.created_time desc;

SELECT * from vm_store.vstore_order_express_info t order by t.id desc;
SELECT * from vm_store.vstore_order_express_info t where t.created_time is null;
SELECT t.* FROM vm_store.vstore_order_express_info_sku t where t.express_id in (5975,5978);

select * from vm_report.report_record t where t.templete_id = '1' and t.from_objid in (1620,
1608);

SELECT t.* FROM vm_store.vstore_order_payment t order by t.id desc;
select * from vm_cs.device_sales_registration t order by t.id desc;

#报表数据
select t.* from vm_report.report_record t where t.templete_id ='1' ORDER BY t.id desc;
select t.* from vm_report.report_record t where t.templete_id ='1' and t.from_objid in (308,316) ORDER BY t.id desc;
select t.* from vm_report.report_record t where t.templete_id ='1' and t.created_time > DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY t.id desc;
select t.* from vm_report.report_record t where t.templete_id ='1' and DATE_FORMAT(t.period_date, '%Y-%m') = '2017-04' ORDER BY t.id desc;
#统计每日的数据
select t.period_date,count(1) from vm_report.report_record t where t.templete_id ='1' GROUP BY t.period_date ORDER BY t.period_date desc;
select t.from_objid,count(1) as cnt from vm_report.report_record t where t.templete_id ='1' GROUP BY t.from_objid ORDER BY cnt desc;
select t.* from vm_report.report_record t where t.templete_id ='1' and t.from_objid = '237' ORDER BY t.id desc;


SELECT t.* FROM vm_store.vstore_order_express_info_sku t where t.id in (54291);
SELECT t.* FROM vm_store.vstore_order_express_info t where t.id in (5979,5980,5981,5982,5983,5984,5984,5984,5984,5985,5986,5987);


select GROUP_CONCAT(t.from_objid separator ','),t.* from vm_report.report_record t where t.template_id in ('1','2','3') and t.created_time > DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY t.id desc;
SELECT GROUP_CONCAT(t.express_id separator ','),t.* FROM vm_store.vstore_order_express_info_sku t where t.id in (5863,5864,5865,5871,5866,5870,5867,5869,5868,5872,5873,5874);
SELECT GROUP_CONCAT(t.order_id separator ','),t.* FROM vm_store.vstore_order_express_info t where t.id in (3318,3319,3320,3527,3529,3531,7794,7795);
SELECT * FROM vm_store.vstore_order_base_info t where t.id in (7804,7805,7807,7807,7803,7808,7809,7810,7811); #and t.source_channel != 10;


#通过订单ID查询报表信息
SELECT * FROM vm_store.vstore_order_base_info t where t.CODE in (136622334,255141221); #and t.source_channel != 10;
SELECT o.source_channel,o.code,e.created_time,e.sent_time,e.id as expressId,e.express_status,e.status FROM vm_store.vstore_order_express_info e,vm_store.vstore_order_base_info o where e.order_id = o.id and o.code in (136622334,255141221) order by e.created_time desc,e.sent_time desc;
SELECT o.source_channel,o.code,e.id as expressId,e.created_time,e.sent_time,u.id,r.id as recordId,r.from_objid,r.period_date,r.created_time from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and o.code in (136622334,255141221)
order by r.created_time desc;
#订单136622334,255141221对应报表id=17001,16323
SELECT * FROM vm_report.report_record t WHERE t.id in (17001,16323);
#数据不对
SELECT h.name,c.value,c.column_id,c.record_id FROM vm_report.report_templete_column h, vm_report.report_record_column c 
where h.id = c.column_id
and c.record_id in (17001,16323)
order by c.column_id;

#通过报表信息查询订单ID
select t.* from vm_report.report_record t where t.templete_id ='1' ORDER BY t.id desc;
select t.* from vm_report.report_record t where t.templete_id ='1' and t.created_time > DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY t.id desc;
select o.source_channel,o.code,e.created_time,e.sent_time,e.id as expressId,e.express_status,e.status,u.id,r.id as recordId,r.from_objid,r.period_date,r.created_time from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and r.from_objid in (5059,4325);

#1608,1612,1589,5862,1620

expressSKUInfo.getId() == 54231L || expressSKUInfo.getId() == 54206L
expInfo.getId() == 51829L || expInfo.getId() == 51904L
{51904=2017-11-02 16:53:00.0, 51123=2017-11-01 16:25:54.0, 51829=2017-11-02 14:23:00.0}
SELECT distinct(t.express_status) FROM vm_store.vstore_order_express_info t where ;

ExpressStatus.PRE_CREATE, ExpressStatus.DELIVERED, ExpressStatus.WAITING_DELIVER, ExpressStatus.RECEIVED, ExpressStatus.THIRD_WAREHOUSE, ExpressStatus.CANCELED_ING, ExpressStatus.CANCELED_CONFIRMED, ExpressStatus., 
/**
    public enum ExpressStatus
    {
        UNKNOWN((byte) -100, "无法识别"),
        PRE_CREATE((byte) 0, "待处理"),
        DELIVERED((byte) 1, "已发货"),
        WAITING_DELIVER((byte) 2, "待发货"),
        RECEIVED((byte) 3, "已签收"),
        THIRD_WAREHOUSE((byte) 4, "推送分仓发货"),
        CANCELED((byte) -1, "已取消发货"),
        CANCELED_ING((byte) -2, "截单待确认"),
        CANCELED_CONFIRMED((byte) -3, "截单成功"),
*/
SELECT t.* FROM vm_store.vstore_order_express_info t where t.express_status = '-1' order by t.id;
