SELECT
  s.channel_id,
	IFNULL(SUM(p.payment_price), 0),
	IFNULL(SUM(s.price), 0),
	IFNULL(SUM(df.fee), 0),
	COUNT(1)
FROM
	vm_store.vstore_order_base_info o
LEFT JOIN vm_store.vstore_order_profit_share_channel s ON s.order_id = o.id
AND s. STATUS <>- 1
#AND s.channel_id IN (186)#: profitChannelIds
AND s.type = 0 #: profitType
,
 vm_store.vstore_order_payment p,
 vm_store.order_sku_delivery_fee df
WHERE
	o.id = p.order_id
AND o.id = df.order_id
#AND o.org_code IN : orderChannelIds
AND p. STATUS = 1#: payStatus
#and o.created_time between :begin and :end
#And o.org_code in :orgChannelIds
GROUP BY s.channel_id;


#查看有多少渠道有数据
#SELECT DISTINCT(o.org_code) FROM vm_store.vstore_order_base_info o ;
