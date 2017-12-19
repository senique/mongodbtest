SELECT * FROM row_generator WHERE id = '11';
SELECT * FROM row_column_generator_relation WHERE id = '11';
SELECT * FROM column_generator WHERE id = '11';
#SELECT * FROM column_generator_attr;
SELECT * FROM vm_report.report_templete WHERE id = '11';
#SELECT * FROM vm_report.report_templete_column t WHERE t.templete_id = '11';
#SELECT * FROM vm_report.report_record;
#SELECT * FROM vm_report.report_record_column;

SELECT * FROM vm_report.report_templete;
SELECT * FROM vm_report.report_templete_column;

SELECT * FROM vm_report.row_generator;
SELECT * FROM vm_report.row_column_generator_relation;
SELECT * FROM vm_report.column_generator;
SELECT * FROM vm_report.report_templete;

SELECT * FROM vm_report.report_record t;
SELECT * FROM vm_report.report_record t WHERE t.from_busitype = '11' and t.created_time >DATE_SUB(NOW(),INTERVAL 1 DAY) ORDER BY t.created_time desc;

SELECT * FROM vm_report.report_record t WHERE t.from_busitype = '11' ORDER BY t.created_time desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE from_busitype ='11') ORDER BY t.id desc;
#渠道 日报表
SELECT * FROM vm_report.report_record t WHERE t.templete_id = '21' and t.period_date >= DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d') ORDER BY t.created_time desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE templete_id ='21') ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE templete_id ='21') and t.column_id in (204, 205, 206, 207, 208) ORDER BY t.value desc;

#渠道 月报表
SELECT * FROM vm_report.report_record t WHERE t.templete_id = '22' and t.period_date >= DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m') ORDER BY t.created_time desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE templete_id ='22') ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE templete_id ='22') and t.column_id in (204, 205, 206, 207, 208) ORDER BY t.value desc;

#渠道 全部报表
SELECT * FROM vm_report.report_record t WHERE t.templete_id = '23' ORDER BY t.created_time desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE templete_id ='23') ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record WHERE templete_id ='23') and t.column_id in (204, 205, 206, 207, 208) ORDER BY t.value desc;

SELECT * FROM vm_report.report_record_column t WHERE t.column_id = '205' and t.record_id in (SELECT id FROM vm_report.report_record t WHERE t.templete_id = '23') ORDER BY t.id desc;

##channel_id = 186
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record t WHERE t.templete_id = '21' and t.from_objid='435') ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record t WHERE t.templete_id = '22' and t.from_objid='183') ORDER BY t.id desc;
SELECT * FROM vm_report.report_record_column t WHERE t.record_id in (SELECT id FROM vm_report.report_record t WHERE t.templete_id = '23' and t.from_objid='183') ORDER BY t.id desc;

SELECT id FROM vm_report.report_record t WHERE  t.from_objid='326';
SELECT * FROM vm_report.report_record t WHERE t.templete_id = '21' and t.from_objid='435';

#查看是否有重复数据
SELECT t.period_date,t.from_objid,COUNT(t.id) as cnt FROM vm_report.report_record t WHERE t.templete_id = '21' and t.period_date >= DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d') GROUP BY t.period_date,t.from_objid ORDER BY cnt desc;
SELECT t.period_date,t.from_objid,COUNT(t.id) as cnt FROM vm_report.report_record t WHERE t.templete_id = '22' and t.period_date = DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 0 MONTH),'%Y-%m-01') GROUP BY t.from_objid ORDER BY cnt desc;
SELECT t.period_date,t.from_objid,COUNT(t.id) as cnt FROM vm_report.report_record t WHERE t.templete_id = '23' GROUP BY t.period_date,t.from_objid ORDER BY cnt desc;


SELECT * FROM vm_report.report_record t WHERE t.templete_id = '21' and t.created_time>DATE_SUB(NOW(),INTERVAL 1 HOUR) ORDER BY t.created_time desc;
SELECT t.period_date,count(1)  FROM vm_report.report_record t WHERE t.templete_id = '21' GROUP BY t.period_date order by t.period_date;
SELECT t.period_date,count(1)  FROM vm_report.report_record t WHERE t.templete_id = '22' GROUP BY t.period_date order by t.period_date;


#清除数据 	
#渠道 报表数据
/**
DELETE FROM vm_report.report_record_column WHERE record_id in (SELECT id FROM report_record WHERE from_busitype ='11');
DELETE FROM vm_report.report_record WHERE from_busitype ='11';
**/

SELECT SYSDATE(),now(),DATE('2017-11-11'),DATE_FORMAT(NOW(),'%Y-%m-01'),DATE_FORMAT(now(), '%Y-%m'),DATE_SUB(NOW(),INTERVAL 31 DAY),DATE_SUB(NOW(),INTERVAL 1 HOUR),date('20170101'),TO_DAYS('20180112')-TO_DAYS('20180101');


SELECT r.from_objid, sum(c.value) FROM vm_report.report_record r left JOIN vm_report.report_record_column c on r.id = c.record_id  
WHERE r.templete_id = '21' and c.column_id = 205 
GROUP BY r.from_objid
ORDER BY r.created_time desc;