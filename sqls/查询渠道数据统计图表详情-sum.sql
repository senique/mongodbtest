select 
sum(orderCount),
sum(orderAmount),
sum(userCount),
sum(staffCount),
sum(parttimeCount),
sum(terminalCount) 
from (
SELECT
	t1.channel_id AS channelId,
	MAX( CASE t1.CODE WHEN 1 THEN t1.sumValue ELSE 0 END ) AS userCount,
	MAX( CASE t1.CODE WHEN 2 THEN t1.sumValue ELSE 0 END ) AS staffCount,
	MAX( CASE t1.CODE WHEN 3 THEN t1.sumValue ELSE 0 END ) AS orderCount,
	MAX( CASE t1.CODE WHEN 4 THEN t1.sumValue ELSE 0 END ) AS orderAmount,
	MAX( CASE t1.CODE WHEN 5 THEN t1.sumValue ELSE 0 END ) AS parttimeCount,
	MAX( CASE t1.CODE WHEN 9 THEN t1.sumValue ELSE 0 END ) AS terminalCount 
FROM
	(
SELECT
	cdr.channel_id,
	cdr.CODE,
	SUM( cdr.VALUE ) AS sumValue 
FROM
	vm_channel.channel_daily_report cdr 
WHERE cdr.STATUS = 1 #AND cdr.channel_id IN ( : channelIds )
	AND cdr.DAY >= '2017-11-11 00:00:00' 
	AND cdr.DAY <= '2017-11-11 23:59:59' 
	AND cdr.CODE IN ( 1, 2, 3, 4, 5, 9 ) 
GROUP BY
	cdr.channel_id,
	cdr.CODE 
	) t1 
GROUP BY
t1.channel_id
order by t1.channel_id
) rt
;
