select o.source_channel,o.code,e.created_time,e.sent_time,u.id,r.from_objid,r.period_date,r.created_time from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and r.from_objid in (5837,5850);


SELECT * FROM vm_report.report_templete_column t where t.name like '%实际零售价%';

SELECT * FROM vm_report.report_templete_column t where t.templete_id IN (1)	ORDER BY t.templete_id;

select t.* from vm_report.report_record t where t.templete_id ='1' and DATE_FORMAT(t.period_date, '%Y-%m') = '2017-04' ORDER BY t.id desc;
select t.* from vm_report.report_record t where t.templete_id ='1' and DATE_FORMAT(t.period_date, '%Y-%m') = '2017-04' ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id from vm_report.report_record t where t.templete_id ='1' and DATE_FORMAT(t.period_date, '%Y-%m') = '2017-04') ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id = '16974' ORDER BY t.column_id;

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


SELECT * FROM vm_report.report_templete_column t where t.templete_id IN (1)	ORDER BY t.templete_id;
SELECT * FROM vm_report.report_record t WHERE t.id = '17001';
SELECT * FROM vm_report.report_record_column t WHERE t.record_id = '17001' ORDER BY t.column_id;

SELECT h.name,c.value,c.column_id,c.record_id FROM vm_report.report_templete_column h, vm_report.report_record_column c 
where h.id = c.column_id
and c.record_id in (17001)
order by c.column_id;

select record_id FROM vm_report.report_record_column t WHERE t.value = '255141221';
SELECT * FROM vm_report.report_record t WHERE t.id in (17001,16323);
SELECT h.name,c.value,c.column_id,c.record_id FROM vm_report.report_templete_column h, vm_report.report_record_column c 
where h.id = c.column_id
and c.record_id in (17001,16323)
order by c.column_id;

#查询异常数据
select distinct(record_id) FROM vm_report.report_record_column t WHERE t.value like '%???%';
select GROUP_CONCAT(distinct(record_id) separator ',') FROM vm_report.report_record_column t WHERE t.value like '%???%';
SELECT GROUP_CONCAT(t.from_objid separator ',') FROM vm_report.report_record t WHERE t.id in (16588,16590,16593,16598,16599,16601,16603,16604,16989,16993,16996,16998,16999,17001,17004,17005,17008,17009,17011,17033,17040,17041,17042,17043,17044,17045,17061,17063,17064,17065,17066,17067,17068,17069,17071,17072,17073,17074,17075,17077,17076,17083,17357,20890,20891,20892,20893);

#通过报表信息查询订单ID
select o.source_channel,o.code,e.created_time,e.sent_time,e.id as expressId,e.express_status,e.status,u.id,r.id as recordId,r.from_objid,r.period_date,r.created_time from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and r.from_objid in (4598,4600,4604,4609,4610,4612,4614,4615,5047,5051,5054,5056,5057,5059,5062,5063,5066,5067,5069,5091,5100,5099,5099,5098,5098,5101,5129,5133,5132,5133,5131,5132,5131,5134,5136,5139,5139,5138,5138,5137,5137,5147,5456,6874,6873,6875,6877)
order by o.code,expressId;

SELECT t.* FROM vm_store.vstore_order_express_info t where t.id in (4869,4871,4875,4880,4881,4883,4885,4886,5281,5285,5288,5290,5291,5293,5296,5297,5300,5301,5303,5325,5332,5332,5332,5332,5332,5333,5352,5354,5354,5354,5354,5354,5354,5355,5357,5358,5358,5358,5358,5358,5358,5365,5641,9843,9843,10039,10898);


select GROUP_CONCAT(r.id separator ',')  from vm_report.report_record r,vm_store.vstore_order_express_info_sku u, vm_store.vstore_order_express_info e, vm_store.vstore_order_base_info o 
where r.from_objid=u.id
and u.express_id=e.id
and e.order_id = o.id
and r.templete_id = '1'
and r.from_objid in (4598,4600,4604,4609,4610,4612,4614,4615,5047,5051,5054,5056,5057,5059,5062,5063,5066,5067,5069,5091,5100,5099,5099,5098,5098,5101,5129,5133,5132,5133,5131,5132,5131,5134,5136,5139,5139,5138,5138,5137,5137,5147,5456,6874,6873,6875,6877);

select * FROM vm_report.report_record_column t WHERE record_id in (761342,761347,761349,761353,761357,761341,761346,761350,761356,761359,761343,761348,761351,761355,761358,761344,761345,761352,761354,761361,761365,761362,761360,761368,761370,761376,761373,761371,761378,761363,761372,761369,761367,761374,761375,761379,761377,761364,761366);
select distinct(record_id) FROM vm_report.report_record_column t WHERE record_id in (761342,761347,761349,761353,761357,761341,761346,761350,761356,761359,761343,761348,761351,761355,761358,761344,761345,761352,761354,761361,761365,761362,761360,761368,761370,761376,761373,761371,761378,761363,761372,761369,761367,761374,761375,761379,761377,761364,761366);


