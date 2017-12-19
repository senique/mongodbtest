/*SELECT
	c.sku_id,
	s.name,s.status,
	#a.created_time,
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month,
	sum( c.quantity ) AS quantity,
	sum( c.quantity * c.payment_price )/100 AS payment_price,
	a.source_channel,a.org_code,
	CASE
		#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
		WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
		#ELSE "其他" 
		ELSE "零售销售报表（米家除外）" #零售销售报表（米家除外）
	END AS source_channel_1,
	a.division,
	CONCAT(Left(a.division, 2), '0000') as province,
	CONCAT(Left(a.division, 4), '00') as city
	,(select name from vm_common.common_division d where CONCAT(Left(a.division, 2), '0000') = d.code and d.version = 11) as pName
	,(select name from vm_common.common_division d where CONCAT(Left(a.division, 4), '00') = d.code and d.version = 11) as cName*/
	-- 零售
SELECT
	a.division,c.sku_id,s.status
	,(select name from vm_common.common_division d where CONCAT(Left(a.division, 2), '0000') = d.code and d.version = 11) as '省'
	,(select name from vm_common.common_division d where CONCAT(Left(a.division, 4), '00') = d.code and d.version = 11) as '市',
	s.name as '商品名称',
	DATE_FORMAT(a.created_time, '%Y-%m') as created_time_month,
	sum( c.quantity ) AS '销量',
	sum( c.quantity * c.payment_price )/100 AS '销售额',
	CASE
		#WHEN a.source_channel IN ( 1, 2, 3, 4, 5, 9 ) THEN "云米零售" 
		WHEN a.source_channel ='10' AND a.org_code = 1580 THEN "米家销售" #米家销售
		#ELSE "其他" 
		ELSE "零售销售报表（米家除外）" #零售销售报表（米家除外）
	END AS source_channel_1,
	CONCAT(Left(a.division, 2), '0000') as province,
	CONCAT(Left(a.division, 4), '00') as city
FROM
	vm_store.vstore_order_base_info a,
	vm_store.vstore_order_payment b,
	vm_store.vstore_order_sku c,
	vm_wares.prod_sku s 
WHERE
	a.id = b.order_id 
	AND ( b.STATUS = 1 OR b.STATUS =- 2 ) 
	AND a.created_time >= '2017-01-01 00:00:00' 
	AND a.created_time <= '2017-11-29 23:59:59' 
	AND c.order_id = a.id 
	AND c.sku_id = s.id
	and c.sku_id in (57,38,39,7,33,43,5,6,29,11,10,8,27,6947904203530,6947904203554,6947904203691,6947904203714)
	#and a.division like '4101%'
	#--AND  a.source_channel ='10' AND a.org_code = 1580#米家销售
	#--AND (a.source_channel <>'10' or a.org_code <> 1580)  #零售销售报表（米家除外）
GROUP BY
  source_channel_1,city,c.sku_id,created_time_month
	ORDER BY source_channel_1,city,c.sku_id,created_time_month;
