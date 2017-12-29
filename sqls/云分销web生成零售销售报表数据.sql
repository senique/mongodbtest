SELECT
	a.sku_id,
	a.sku_name,
	a.catalog_id,
	a.catalog_name,
	ifnull( b.sales_sum, 0 ) AS day_sales_sum,
	ifnull( b.sales_amount, 0 ) AS day_sales_amount,
	ifnull( c.sales_sum, 0 ) AS month_sales_sum,
	ifnull( c.sales_amount, 0 ) AS month_sales_amount,
	ifnull( d.sales_sum, 0 ) AS year_sales_sum,
	ifnull( d.sales_amount, 0 ) AS year_sales_amount 
FROM
	( SELECT * FROM vm_report.wares_retail_sales_report t WHERE date_format( t.report_date, '%Y' ) ='2017' GROUP BY t.sku_id ) a
	LEFT JOIN (
SELECT
	t.sku_id,
	sum( t.sales_sum ) AS sales_sum,
	sum( t.sales_amount ) AS sales_amount 
FROM
	vm_report.wares_retail_sales_report t 
WHERE
	t.report_date ='2017-10-08' 
GROUP BY
	t.sku_id 
	) b ON a.sku_id = b.sku_id
	LEFT JOIN (
SELECT
	t.sku_id,
	sum( t.sales_sum ) AS sales_sum,
	sum( t.sales_amount ) AS sales_amount 
FROM
	vm_report.wares_retail_sales_report t 
WHERE
	date_format( t.report_date, '%Y-%m' ) ='2017-10' 
GROUP BY
	t.sku_id 
	) c ON a.sku_id = c.sku_id
	LEFT JOIN (
SELECT
	t.sku_id,
	sum( t.sales_sum ) AS sales_sum,
	sum( t.sales_amount ) AS sales_amount 
FROM
	vm_report.wares_retail_sales_report t 
WHERE
	date_format( t.report_date, '%Y' ) ='2017'
GROUP BY
	t.sku_id 
	) d ON a.sku_id = d.sku_id 
ORDER BY
	a.record_time DESC
	#{salesYear=2017, salesMonth=2017-10, reportDate=2017-10-08, statYear=2017}