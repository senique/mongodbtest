/* 
	使用说明：需要修改2个地方，修改source_channel指定来源和指定查询时间，共修改4处
*/
select  s.id,
(select p.value from vm_wares.prod_catalog p where p.id = s.catalog_id) as '类别',
s.name as '商品名称',
a1.quantity1 AS '销量',a1.sum_price1 AS '销售额',
a1.quantity2 AS '退货退款量',a1.sum_price2 AS '退货退款额' 
from vm_wares.prod_sku s left join (

select  
#(select p.value from vm_wares.prod_catalog p where p.id = a11.catalog_id) as '类别',
#a11.name as '商品名称',
a11.sku_id,
a11.quantity AS quantity1,a11.sum_price AS sum_price1,
a12.quantity AS quantity2,a12.sum_price AS sum_price2
from (

SELECT
	c.sku_id,
	sum( c.quantity ) as quantity,
	sum( c.quantity * c.payment_price )/100 as sum_price,
	
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month
	#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
	#WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
FROM
	vm_store.vstore_order_base_info a,
	vm_store.vstore_order_payment b,
	vm_store.vstore_order_sku c
WHERE
	a.id = b.order_id 
	#source_channel: 1-微信商城;云米商城APP-2,3;冰箱-9;米家-10 & org_code = 1580
	and a.source_channel = 1
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND b.STATUS = 1 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-12' 
	AND c.order_id = a.id 
GROUP BY
  c.sku_id,created_time_month
) a11 left join (

SELECT
	c.sku_id,
	sum( c.quantity ) as quantity,
	sum( c.quantity * c.payment_price )/100 as sum_price,
	
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month
	#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
	#WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
FROM
	vm_store.vstore_order_base_info a,
	vm_store.vstore_order_payment b,
	vm_store.vstore_order_sku c
WHERE
	a.id = b.order_id 
	#source_channel: 1-微信商城;云米商城APP-2,3;冰箱-9;米家-10 & org_code = 1580
	and a.source_channel = 1
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND ( b.STATUS = -2 OR b.STATUS = -3 ) 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-12' 
	AND c.order_id = a.id 
GROUP BY
  c.sku_id,created_time_month
	) a12 on a11.sku_id = a12.sku_id

) a1 on s.id = a1.sku_id
ORDER BY s.id 