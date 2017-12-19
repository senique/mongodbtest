-- 查询预付款云分销销售数据生成报表
SELECT
	a.report_date,DATE_FORMAT(a.report_date, '%Y-%m') as report_date_month,
	a.sku_id,
	a.order_id,
	s.name,s.status,
	sum( a.quantity ),
	sum( a.sales_amount ),
	a.source_channel,
	b.division,
	CONCAT(Left(b.division, 2), '0000') as province,CONCAT(Left(b.division, 4), '00') as city
FROM
	(SELECT
		c.report_date,
	  c.order_id,
		d.sku_id,
		d.quantity,
		d.payment_price,
		d.payment_price * d.quantity AS sales_amount,
		c.source_channel 
	FROM
		(SELECT
			a.order_id,
			a.report_date,
		 CASE
				#WHEN b.platform_type IS NULL THEN 4 
				WHEN b.platform_type = 1 THEN 5 #天猫销售
				#ELSE 100
				ELSE 4 #预付款进货销售报表（天猫除外）
			END AS source_channel 
		 FROM
			(SELECT
				t.id AS order_id,
				date( t.created_time ) AS report_date 
			 FROM
				vm_channel.channel_purchase_order_info t 
			 WHERE
				t.STATUS IN ( 0, 1 ) 
				AND t.created_time >= date("2017-01-01 00:00:00") 
				AND t.created_time <= date("2017-10-31 23:59:59") 
				#AND t.id NOT IN ( SELECT value FROM vm_channel.channel_purchase_order_attr WHERE CODE = "original_order" ) 
			) a LEFT JOIN ( SELECT attr.order_id, attr.VALUE AS platform_type FROM vm_channel.channel_purchase_order_attr attr WHERE attr.CODE = "platform_type" ) b ON a.order_id = b.order_id 
		) c, vm_channel.channel_purchase_order_sku d 
	WHERE
		c.order_id = d.order_id 
	) a ,
	vm_store.vstore_order_base_info b,
	vm_wares.prod_sku s 
	where a.order_id = b.id
	and a.sku_id = s.id
	and a.sku_id in (57,38,39,7,33,43,5,6,29,11,10,8,27,6947904203530,6947904203554,6947904203691,6947904203714)
	AND b.division LIKE '4116%'
GROUP BY
	a.sku_id,
	a.report_date,
a.source_channel;