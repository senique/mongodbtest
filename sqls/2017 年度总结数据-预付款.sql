/* 
	使用说明：需要修改1个地方，指定查询时间（查看注释确认有没有退款的记录）
*/
select  s.id,
(select p.value from vm_wares.prod_catalog p where p.id = s.catalog_id) as '类别',
s.name as '商品名称',
a1.quantity1 AS '销量',a1.sum_price1 AS '销售额',
'' AS '退货退款量','' AS '退货退款额' 
from vm_wares.prod_sku s left join
	(SELECT
		c.report_date,
	  c.order_id,
		c.code,
		c.platform_type,
		#DATE_FORMAT(c.report_date, '%Y-%m') as report_date_month,	
		d.sku_id,
		d.quantity as quantity1,
		d.payment_price,
		(d.payment_price * d.quantity)/100 AS sum_price1
	FROM
		(SELECT
			a.order_id,
			a.code,
			a.report_date,
			b.platform_type
		 FROM
			(SELECT
				t.code,t.division,
				t.id AS order_id,
				date( t.created_time ) AS report_date 
			 FROM
				vm_channel.channel_purchase_order_info t 
			 WHERE t.STATUS = 1
			  #PurchaseOrderStatus: 1-待发货,2-已发货,3-待审核,4-待退款(到20171219未查询到退款的单子),5-已拆单,6-交付分仓发货
				#没有状态为-4的数据，即没有退货的数据 select a.order_status,count(*) from vm_channel.channel_purchase_order_info a group by a.order_status; 
				#and t.order_status = 2
				#and t.order_status in (1,2,3,5,6)
				and ( t.order_status = 1 OR t.order_status = 2 OR t.order_status = 3 OR t.order_status = 5 OR t.order_status = 6 ) 
				AND DATE_FORMAT(t.created_time, '%Y-%m') = '2017-07' 
				#AND t.id NOT IN ( SELECT order_id FROM vm_channel.channel_purchase_order_attr WHERE CODE = "original_order" ) 
			) a LEFT JOIN ( SELECT attr.order_id, attr.VALUE AS platform_type FROM vm_channel.channel_purchase_order_attr attr WHERE attr.CODE = "platform_type" ) b ON a.order_id = b.order_id 
		) c, vm_channel.channel_purchase_order_sku d 
	WHERE
		c.order_id = d.order_id 
	GROUP BY d.sku_id
	) a1 
  on s.id = a1.sku_id
ORDER BY s.id 
