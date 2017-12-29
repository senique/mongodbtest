-- 零售
/*SELECT
	c.sku_id,s.status,
	s.catalog_id,(select p.value from vm_wares.prod_catalog p where p.id = s.catalog_id) as '类别',s.name as '商品名称',
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month,
	sum( c.quantity ) AS '销量',
	sum( c.quantity * c.payment_price )/100 AS '销售额',
	CASE
		#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
		WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
		#ELSE "其他" 
		ELSE "零售销售报表（米家除外）" #零售销售报表（米家除外）
	END AS source_channel_1
FROM
	vm_store.vstore_order_base_info a,
	vm_store.vstore_order_payment b,
	vm_store.vstore_order_sku c,
	vm_wares.prod_sku s 
WHERE
	a.id = b.order_id 
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND ( b.STATUS = 1 OR b.STATUS = -2 ) 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-01' 
	AND c.order_id = a.id 
	AND c.sku_id = s.id
	#and c.sku_id in (57,38)
	#and a.division like '4101%'
	#--AND  a.source_channel ='10' AND a.org_code = 1580#米家销售
	#--AND (a.source_channel <>'10' or a.org_code <> 1580)  #零售销售报表（米家除外）
GROUP BY
  c.sku_id,created_time_month
	ORDER BY c.sku_id,created_time_month;
	*/
	
	-- 零售
select * from (
SELECT
	c.sku_id,(select p.value from vm_wares.prod_catalog p where p.id = s.catalog_id) as '类别',s.name as '商品名称',
	sum( c.quantity ) AS '销量',
	sum( c.quantity * c.payment_price )/100 AS '销售额',
	
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
	vm_store.vstore_order_sku c,
	vm_wares.prod_sku s 
WHERE
	a.id = b.order_id 
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND b.STATUS = 1 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-01' 
	AND c.order_id = a.id 
	AND c.sku_id = s.id
	#and c.sku_id in (57,38)
	#and a.division like '4101%'
	#--AND  a.source_channel ='10' AND a.org_code = 1580#米家销售
	#--AND (a.source_channel <>'10' or a.org_code <> 1580)  #零售销售报表（米家除外）
GROUP BY
  c.sku_id,created_time_month
) a11 left join (
	SELECT
	c.sku_id,(select p.value from vm_wares.prod_catalog p where p.id = s.catalog_id) as '类别',s.name as '商品名称',
	sum( c.quantity ) AS '销量',
	sum( c.quantity * c.payment_price )/100 AS '销售额',
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
	vm_store.vstore_order_sku c,
	vm_wares.prod_sku s 
WHERE
	a.id = b.order_id 
	#OrderPayStatus: 1-已支付，-2(已退款)和-3(退款中)
	AND ( b.STATUS = -2 OR b.STATUS = -3 ) 
	AND DATE_FORMAT(a.created_time, '%Y-%m') = '2017-01' 
	AND c.order_id = a.id 
	AND c.sku_id = s.id
	#and c.sku_id in (57,38)
	#and a.division like '4101%'
	#--AND  a.source_channel ='10' AND a.org_code = 1580#米家销售
	#--AND (a.source_channel <>'10' or a.org_code <> 1580)  #零售销售报表（米家除外）
GROUP BY
  c.sku_id,created_time_month 
	) a12 on a11.sku_id = a12.sku_id


