SELECT * from vm_store.vstore_order_express_info t order by t.id desc;
SELECT * from vm_store.vstore_order_express_info t where t.created_time is null;
SELECT * from vm_store.vstore_order_express_info t where t.mail_no like '%跨%' ORDER BY t.sent_time desc,t.created_time desc;
SELECT * FROM vm_store.vstore_order_express_info t where t.sent_time > Date('2017-10-01') and t.created_time < DATE('2017-10-01') ORDER BY t.sent_time desc,t.created_time desc;
SELECT * FROM vm_store.vstore_order_express_info t where t.sent_time > Date('2017-09-01') and t.created_time < DATE('2017-09-01') ORDER BY t.sent_time desc,t.created_time desc;

SELECT t.* FROM vm_store.vstore_order_express_info_sku t where t.express_id in (5975,5978);
SELECT * from vm_report.report_record t where t.templete_id = '1' and t.from_objid in (1620,1608);

SELECT t.* FROM vm_store.vstore_order_payment t order by t.id desc;
SELECT * from vm_cs.device_sales_registration t order by t.id desc;


#报表数据
SELECT t.* from vm_report.report_record t where t.templete_id ='1' ORDER BY t.id desc;
SELECT t.* from vm_report.report_record t where t.templete_id ='1' and t.from_objid in (308,316) ORDER BY t.id desc;
SELECT t.* from vm_report.report_record t where t.templete_id ='1' and t.created_time > DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY t.id desc;
SELECT t.* from vm_report.report_record t where t.templete_id ='1' and DATE_FORMAT(t.period_date, '%Y-%m') = '2017-04' ORDER BY t.id desc;
#统计每日的数据
SELECT t.period_date,count(1) from vm_report.report_record t where t.templete_id ='1' GROUP BY t.period_date ORDER BY t.period_date desc;
SELECT t.from_objid,count(1) as cnt from vm_report.report_record t where t.templete_id ='1' GROUP BY t.from_objid ORDER BY cnt desc;
SELECT t.* from vm_report.report_record t where t.templete_id ='1' and t.from_objid = '237' ORDER BY t.id desc;


SELECT GROUP_CONCAT(t.from_objid separator ','),t.* from vm_report.report_record t where t.template_id in ('1','2','3') and t.created_time > DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY t.id desc;
SELECT GROUP_CONCAT(t.express_id separator ','),t.* FROM vm_store.vstore_order_express_info_sku t where t.id in (5863,5864,5865,5871,5866,5870,5867,5869,5868,5872,5873,5874);
SELECT GROUP_CONCAT(t.order_id separator ','),t.* FROM vm_store.vstore_order_express_info t where t.id in (3318,3319,3320,3527,3529,3531,7794,7795);
SELECT * FROM vm_store.vstore_order_base_info t where t.id in (7804,7805,7807,7807,7803,7808,7809,7810,7811); #and t.source_channel != 10;


#通过订单ID查询报表信息
SELECT * FROM vm_store.vstore_order_base_info t where t.CODE in (136622334,255141221); #and t.source_channel != 10;
SELECT o.source_channel,o.code,e.created_time,e.sent_time,e.id as expressId,e.express_status,e.status FROM vm_store.vstore_order_express_info e,vm_store.vstore_order_base_info o where e.order_id = o.id and o.code in (136622334,255141221,248395962) order by e.created_time desc,e.sent_time desc;
SELECT o.source_channel,o.code,e.id as expressId,e.created_time,e.sent_time,u.id,r.id as recordId,r.from_objid,r.period_date,r.created_time from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and o.code in (136622334,255141221,118856351)
order by r.created_time desc;

#通过报表信息查询订单ID
SELECT t.* from vm_report.report_record t where t.templete_id ='1' ORDER BY t.id desc;
SELECT t.* from vm_report.report_record t where t.templete_id ='1' and t.created_time > DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY t.id desc;
SELECT o.source_channel,o.code,e.created_time,e.sent_time,e.id as expressId,e.express_status,e.status,u.id,r.id as recordId,r.from_objid,r.period_date,r.created_time from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and r.from_objid in (5059,4325);

#查询异常数据
SELECT GROUP_CONCAT(distinct(record_id) separator ',') FROM vm_report.report_record_column t WHERE t.value like '%???%';
SELECT GROUP_CONCAT(t.from_objid separator ',') FROM vm_report.report_record t WHERE t.id in (16588,16590,16593,16598,16599,16601,16603,16604,16989,16993,16996,16998,16999,17001,17004,17005,17008,17009,17011,17033,17040,17041,17042,17043,17044,17045,17061,17063,17064,17065,17066,17067,17068,17069,17071,17072,17073,17074,17075,17077,17076,17083,17357,20890,20891,20892,20893);
SELECT * from vm_store.vstore_order_express_info e where e.id in (4869,4871,4875,4880,4881,4883,4885,4886,5281,5285,5288,5290,5291,5293,5296,5297,5300,5301,5303,5325,5332,5333,5352,5354,5355,5357,5358,5365,5641,9843,10039,10898);


SELECT h.name,c.value,c.column_id,c.record_id FROM vm_report.report_templete_column h, vm_report.report_record_column c 
where h.id = c.column_id
and c.record_id in (17001,16323)
order by c.column_id;

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
