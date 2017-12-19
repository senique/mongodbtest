	/*a.report_date,
	DATE_FORMAT(a.report_date, '%Y-%m') as report_date_month,
	a.sku_id,
	a.order_id,
	s.name,s.status,
	sum( a.quantity ) as quantity,
	sum( a.sales_amount )/100 as payment_price,
	a.platform_type,a.source_channel_1,
	a.code,a.division,
	a.province,a.city
	,(select name from vm_common.common_division dv where CONCAT(Left(a.division, 2), '0000') = dv.code and dv.version = 11) as pName
	,(select name from vm_common.common_division dv where CONCAT(Left(a.division, 4), '00') = dv.code and dv.version = 11) as cName*/
-- 查询预付款云分销销售数据生成报表
SELECT
  a.division,a.sku_id,s.status
	,(select name from vm_common.common_division dv where CONCAT(Left(a.division, 2), '0000') = dv.code and dv.version = 11) as  '省'
	,(select name from vm_common.common_division dv where CONCAT(Left(a.division, 4), '00') = dv.code and dv.version = 11) as '市',
	s.name as '商品名称',
	DATE_FORMAT(a.report_date, '%Y-%m') as report_date_month,	
	sum( a.quantity ) as '销量',
	sum( a.sales_amount )/100 as '销售额',
	a.source_channel_1,
	a.province,a.city
FROM
	(SELECT
		c.report_date,
	  c.order_id,
		c.code,
		c.division,c.province,c.city,
		c.platform_type,
		d.sku_id,
		d.quantity,
		d.payment_price,
		d.payment_price * d.quantity AS sales_amount,
		c.source_channel_1 
	FROM
		(SELECT
			a.order_id,
			a.code,a.division,
			CONCAT(Left(a.division, 2), '0000') as province,
			CONCAT(Left(a.division, 4), '00') as city,
			a.report_date,
			b.platform_type,
		 CASE
				#WHEN b.platform_type IS NULL THEN 4 
				WHEN b.platform_type = 1 THEN '天猫销售' #5天猫销售
				#ELSE 100
				ELSE '预付款进货销售报表（天猫除外）' #4预付款进货销售报表（天猫除外）
			END AS source_channel_1 
		 FROM
			(SELECT
				t.code,t.division,
				t.id AS order_id,
				date( t.created_time ) AS report_date 
			 FROM
				vm_channel.channel_purchase_order_info t 
			 WHERE t.STATUS = 1
				and t.order_status = 2
				AND t.created_time >= "2017-01-01 00:00:00" 
				AND t.created_time <= "2017-11-29 23:59:59" 
				#AND t.id NOT IN ( SELECT order_id FROM vm_channel.channel_purchase_order_attr WHERE CODE = "original_order" ) 
			) a LEFT JOIN ( SELECT attr.order_id, attr.VALUE AS platform_type FROM vm_channel.channel_purchase_order_attr attr WHERE attr.CODE = "platform_type" ) b ON a.order_id = b.order_id 
		) c, vm_channel.channel_purchase_order_sku d 
	WHERE
		c.order_id = d.order_id 
		#and c.platform_type = 1 #5天猫销售
		#and c.platform_type <> 1 #4预付款进货销售报表（天猫除外）
	) a ,
	vm_wares.prod_sku s 
	where a.sku_id = s.id
	and a.sku_id in (57,38,39,7,33,43,5,6,29,11,10,8,27,6947904203530,6947904203554,6947904203691,6947904203714)
	#and a.sku_id = '7'
	#AND a.city LIKE '500100'
GROUP BY
	a.source_channel_1,a.city,a.sku_id,report_date_month