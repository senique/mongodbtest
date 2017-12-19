
SELECT
	t1.channel_id AS channelId,
	MAX( CASE t1.CODE WHEN 1 THEN t1.sumValue ELSE 0 END ) AS userCount新会员人数,
	MAX( CASE t1.CODE WHEN 2 THEN t1.sumValue ELSE 0 END ) AS staffCount新促销员人数,
	MAX( CASE t1.CODE WHEN 3 THEN t1.sumValue ELSE 0 END ) AS orderCount订单总数,
	MAX( CASE t1.CODE WHEN 4 THEN t1.sumValue ELSE 0 END ) AS orderAmount订单总额,
	MAX( CASE t1.CODE WHEN 5 THEN t1.sumValue ELSE 0 END ) AS parttimeCount新兼职促销员人数,
	MAX( CASE t1.CODE WHEN 9 THEN t1.sumValue ELSE 0 END ) AS terminalCount新增门店数量 
FROM
	(
SELECT
	cdr.channel_id,
	cdr.CODE,
	SUM( cdr.VALUE ) AS sumValue 
FROM
	vm_channel.channel_daily_report cdr 
WHERE cdr.STATUS = 1 #AND cdr.channel_id IN ( : channelIds )
	AND cdr.DAY >= '2017-11-11' 
	AND cdr.DAY <= '2017-11-11' 
	AND cdr.CODE IN ( 1, 2, 3, 4, 5, 9 ) 
GROUP BY
	cdr.channel_id,
	cdr.CODE 
	) t1 
GROUP BY
t1.channel_id
order by t1.channel_id
