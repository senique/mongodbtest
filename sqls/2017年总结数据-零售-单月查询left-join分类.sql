select  
(select p.value from vm_wares.prod_catalog p where p.id = s.catalog_id) as '类别',
s.id,s.name as '商品名称',
a1.*
from vm_wares.prod_sku s left join (

select  
#(select p.value from vm_wares.prod_catalog p where p.id = a11.catalog_id) as '类别',
#a11.name as '商品名称',
a11.sku_id,
a11.quantity AS '销量',a11.sum_price AS '销售额',
a12.quantity AS '退货退款量',a12.sum_price AS '退货退款额'
from (

SELECT
	c.sku_id,
	sum( c.quantity ) as quantity,
	sum( c.quantity * c.payment_price )/100 as sum_price,
	
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month,
	CASE
		#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
		WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
		#ELSE "其他" 
		ELSE "零售销售报表（米家除外）" #零售销售报表（米家除外）
	END AS source_channel_1
FROM
	vm_store.vstore_order_base_info a,
	vm_store.vstore_order_payment b,
	vm_store.vstore_order_sku c
WHERE
	a.id = b.order_id 
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND b.STATUS = 1 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-01' 
	AND c.order_id = a.id 
	#AND c.sku_id = s.id
	#and c.sku_id in (57,38)
	#and a.division like '4101%'
	#--AND  a.source_channel ='10' AND a.org_code = 1580#米家销售
	#--AND (a.source_channel <>'10' or a.org_code <> 1580)  #零售销售报表（米家除外）
GROUP BY
  c.sku_id,created_time_month
) a11 left join (

SELECT
	c.sku_id,
	sum( c.quantity ) as quantity,
	sum( c.quantity * c.payment_price )/100 as sum_price,
	
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month,
	CASE
		#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
		WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
		#ELSE "其他" 
		ELSE "零售销售报表（米家除外）" #零售销售报表（米家除外）
	END AS source_channel_1
FROM
	vm_store.vstore_order_base_info a,
	vm_store.vstore_order_payment b,
	vm_store.vstore_order_sku c
WHERE
	a.id = b.order_id 
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND ( b.STATUS = -2 OR b.STATUS = -3 ) 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-01' 
	AND c.order_id = a.id 
	#AND c.sku_id = s.id
	#and c.sku_id in (57,38)
	#and a.division like '4101%'
	#--AND  a.source_channel ='10' AND a.org_code = 1580#米家销售
	#--AND (a.source_channel <>'10' or a.org_code <> 1580)  #零售销售报表（米家除外）
GROUP BY
  c.sku_id,created_time_month
	) a12 on a11.sku_id = a12.sku_id

) a1 on s.id = a1.sku_id
ORDER BY s.id 