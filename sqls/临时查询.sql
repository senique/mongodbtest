#delete from vm_report.wares_retail_sales_report

select * from vm_report.wares_retail_sales_report t ;
select * from vm_report.wares_retail_sales_report t where t.catalog_id = 306 ;
select * from vm_report.wares_retail_sales_report t order by t.updated_time;
select distinct(date_format(t.report_date, "%Y-%m-%d")) from vm_report.wares_retail_sales_report t order by date_format(t.report_date, "%Y-%m-%d") ;

select * from vm_store.vstore_order_base_info t where t.source_channel <> 10 and DATE_FORMAT(t.created_time, '%Y-%m') = '2017-03'


SELECT SYSDATE(),now(),DATE('2017-11-11'),DATE_FORMAT(NOW(),'%Y-%m-01'),DATE_FORMAT(now(), '%Y-%m'),DATE_SUB(NOW(),INTERVAL 31 DAY),DATE_SUB(NOW(),INTERVAL 1 HOUR),date('20170101'),TO_DAYS('20180112')-TO_DAYS('20180101');

select * from vm_wares.prod_spu;
select * from vm_wares.prod_spu;
select * from vm_wares.prod_catalog t where t.id in (416,418,412,304,306) ;
select * from vm_wares.prod_catalog t where t.parent_id in (304, 412,302) ;
select * from vm_wares.prod_catalog t order by t.created_time desc;

create table vm_report.wares_retail_sales_report_bak20171219 as (select * from vm_report.wares_retail_sales_report t);
select * from vm_report.wares_retail_sales_report_bak20171219 t ;


select * from vm_report.wares_retail_sales_report t  where t.catalog_name is null ;
select * from vm_report.wares_retail_sales_report t  where t.catalog_id in (416,418) order by t.catalog_id;
select * from vm_report.wares_retail_sales_report t  where t.catalog_name is null ;

select * from vm_store.vstore_order_base_info a;
select * from vm_store.vstore_order_payment b;
select * from vm_store.vstore_order_sku c;
select * from vm_wares.prod_sku s ;

select p.value from vm_wares.prod_catalog p ;

select distinct(a.deal_phase) from vm_store.vstore_order_base_info a order by a.deal_phase;
select distinct(b.status) from vm_store.vstore_order_payment b order by b.status;
select * from vm_store.vstore_order_payment b where b.status = -3;
select * from vm_store.vstore_order_base_info a where a.source_channel =9;
select distinct(DATE_FORMAT(a.created_time, '%Y-%m')) from vm_store.vstore_order_base_info a where a.source_channel =9;
select distinct(DATE_FORMAT(a.created_time, '%Y-%m')) from vm_store.vstore_order_base_info a where a.source_channel ='10' AND a.org_code = 1580 

select * from vm_channel.channel_attr ca where ca.value = 1 and ca.code = '02';
select * from vm_store.vstore_order_base_info a where a.org_code in (select ca.channel_id from vm_channel.channel_attr ca where ca.value = 1 and ca.code = '02');
select distinct(a.source_channel) from vm_store.vstore_order_base_info a where a.org_code in (select ca.channel_id from vm_channel.channel_attr ca where ca.value = 1 and ca.code = '02');



select * from vm_channel.channel_purchase_order_info;
select distinct(a.status) from vm_channel.channel_purchase_order_info a order by a.order_status; 
select a.status,count(*) from vm_channel.channel_purchase_order_info a group by a.status; 
select distinct(a.order_status) from vm_channel.channel_purchase_order_info a order by a.order_status; 
select a.order_status,count(*) from vm_channel.channel_purchase_order_info a group by a.order_status; 

select * from vm_wares.prod_sku;



select * from vm_store.vstore_order_base_info t;
select * from vm_store.vstore_order_sku t ;
select * from vm_store.vstore_order_express_info t;


select t.id,count(t.spu_id) from vm_wares.prod_sku t group by t.id;
select t.order_id,count(*) from vm_store.vstore_order_sku t group by t.order_id;
select t.sku_id,count(t.order_id) from vm_store.vstore_order_sku t group by t.sku_id;
select t.type,count(*) from vm_store.vstore_order_sku  t group by t.type;
select distinct(t.type ) from vm_store.vstore_order_sku t ;

select * from vm_store.vstore_order_sku o, vm_wares.prod_sku s where o.sku_id = s.id;

select * from vm_wares.prod_sku t where t.name like '%V1%';
select * from vm_wares.prod_sku t where t.type = 4;
select * from vm_wares.prod_sku_prop t where t.sku_id in (111811090301,
111811090302,
111811090401,
111811090402);
select * from vm_wares.prod_sku_prop_value t where t.sku_prop_id in (9787508673563644);

select * from vm_store.vstore_order_sku t where t.sku_id in (44,
47,
52,
7) order by t.order_id;

select * from vm_store.vstore_order_base_info t where t.id in (select t.order_id from vm_store.vstore_order_sku t where t.sku_id in (44,
47,
52,
7)) order by t.code;


select * from vm_wares.prod_sku t where t.type = 4;
select * from vm_store.vstore_order_sku t where t.sku_id in (select id from vm_wares.prod_sku t where t.type = 4);
select * from vm_store.vstore_order_sku t where t.sku_id in (111811090302, 111811090301, 111811090402, 111811090401);
select * from vm_wares.prod_sku t where t.id in (7,39,44,1118675,1118674,111811090302, 111811090301, 111811090402, 111811090401);
select * from vm_store.vstore_order_sku t where t.sku_id in (7,39,44,1118675,1118674);

select o.linkman_name,o.linkman_phone
	,(select name from vm_common.common_division dv where CONCAT(Left(o.division, 2), '0000') = dv.code and dv.version = 11) as  '省'
	,(select name from vm_common.common_division dv where CONCAT(Left(o.division, 4), '00') = dv.code and dv.version = 11) as '市'
	,(select name from vm_common.common_division dv where o.division = dv.code and dv.version = 11) as '区/县',
	o.address,s.name,os.sku_id,o.code,o.created_time as '下单时间',p.pay_time as '支付时间',o.deal_phase
from vm_store.vstore_order_base_info o,
vm_store.vstore_order_sku os,
vm_wares.prod_sku s,
vm_store.vstore_order_payment p
where o.id= os.order_id
and os.sku_id = s.id
and o.id = p.order_id	
and o.created_time >= DATE_FORMAT('2017-12-11','%Y-%m-%d') 
and o.created_time <= DATE_FORMAT('2017-12-18','%Y-%m-%d') 
#9,已支付;20,交付分仓发货;21,待发货;22,已发货;29,已签收;30,预约安装中;31,已预约安装;32,待上门安装;100,已结单
and o.deal_phase in (9,20,21,22,29,30,31,32,100)
and s.id in (7,39,44,1118675,1118674,111811090302, 111811090301, 111811090402, 111811090401)
order by os.sku_id;


select * from vm_user.user_attr order by user_id;

select DATE_FORMAT(t.created_time, '%Y-%m') as created_time_month from vm_user.user_wechat_info t;
select DATE_FORMAT(t.created_time, '%Y-%m') as created_time_month from vm_user.user_viomi_info t;

select DATE_FORMAT(t.created_time, '%Y-%m') as created_time_month,count(*)  from vm_user.user_wechat_info t group by created_time_month order by created_time_month;
select DATE_FORMAT(t.created_time, '%Y-%m') as created_time_month,count(*)  from vm_user.user_viomi_info t group by created_time_month order by created_time_month;

