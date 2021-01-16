SHOW CREATE TABLE unichain_product_center.`promotion_statistics`;

-- 基础统计明细表(unichain_report_view.base_promotion_member_info)：合并会员信息以及产品统计为大表
DROP TABLE IF EXISTS unichain_report_view.base_promotion_member_info;
CREATE VIEW unichain_report_view.base_promotion_member_info AS
SELECT 
ps.account_id as account_id,
ps.account_phone as account_phone,
ps.ctime as psctime,
ps.deleted as psdeleted,
ps.device_id as device_id,
ps.device_typ as device_typ,
ps.ip as ip,
ps.mac as mac,
ps.product_id as product_id,
ps.product_name as product_name,
ps.promotion_cost as promotion_cost,
ps.promotion_typ as promotion_typ,
ps.source as source,
ps.state as state,
ps.statistics_typ as statistics_typ,
ps.typ as pstyp,

mi.app_id as app_id,
mi.mactime as memaccctime,
mi.madeleted as madeleted,
mi.mbid as mbid,
mi.mbctime as memctime,

mi.mbcost as memcost,
mi.mbname as memname

FROM unichain_product_center.promotion_statistics ps LEFT OUTER JOIN 

(SELECT ma.account_id AS account_id,ma.app_id AS app_id,ma.ctime AS mactime,ma.deleted AS madeleted,ma.member_code AS mbid,mb.ctime AS mbctime,mb.cost AS mbcost,mb.name AS mbname 
	FROM unichain_membership.member mb LEFT OUTER JOIN unichain_membership.member_account ma ON mb.id = ma.member_no) mi 

ON ps.account_id = mi.account_id;


-- 基础汇总表(unichain_report_view.base_common_index)：输出基础的统计指标
-- 渠道名称,上线时间,   <UV点击数,注册数,登录数,申请产品人数,申请产品次数> 区分手机操作系统IOS -> <总计-IOS-安卓-其他>,    注册/UV,登陆/注册,总申请次数/总注册,申请次数/申请人数,支出/申请次数,平均UV价格(元),平均注册价格(元),优化后注册数,平均优化百分比,总支出(元),独立IP数
DROP TABLE IF EXISTS unichain_report_view.base_common_index;
CREATE TABLE unichain_report_view.base_common_index AS
SELECT
product_id as 产品ID,
product_name as 产品名称,
source as 渠道ID,
promotion_typ as 推广方式,
psctime as 日期,

concat(dis_ip_click_total,'/',click_total) as UV数,
concat(dis_ip_register_total,'/',register_total) as 注册数,
concat(dis_ip_download_total,'/',download_total) as 下载数,
concat(dis_ip_login_total,'/',login_total) as 登陆数,
concat(dis_ip_apply_total,'/',apply_total) as 申请数,

concat(ios_ip_click_total,'/',ios_click_total) as iosUV数,
concat(ios_ip_register_total,'/',ios_register_total) as ios注册数,
concat(ios_ip_download_total,'/',ios_download_total) as ios下载数,
concat(ios_ip_login_total,'/',ios_login_total) as ios登陆数,
concat(ios_ip_apply_total,'/',ios_apply_total) as ios申请数,

concat(android_ip_click_total,'/',android_click_total) as androidUV数,
concat(android_ip_register_total,'/',android_register_total) as android注册数,
concat(android_ip_download_total,'/',android_download_total) as android下载数,
concat(android_ip_login_total,'/',android_login_total) as android登陆数,
concat(android_ip_apply_total,'/',android_apply_total) as android申请数,

concat(otheros_ip_click_total,'/',otheros_click_total) as otherosUV数,
concat(otheros_ip_register_total,'/',otheros_register_total) as otheros注册数,
concat(otheros_ip_download_total,'/',otheros_download_total) as otheros下载数,
concat(otheros_ip_login_total,'/',otheros_login_total) as otheros登陆数,
concat(otheros_ip_apply_total,'/',otheros_apply_total) as otheros申请数,

case promotion_typ when 'uv' then promotion_cost*dis_ip_click_total when 'cpa' then promotion_cost*dis_ip_register_total when 'cps' then promotion_cost*dis_ip_apply_total else 0 end as 总支出

FROM (
  SELECT 
  product_id,
  MAX(product_name) as product_name,
  source,
  max(ctime) as psctime,
  
  count(distinct(case typ when 1 then ip else null end)) as dis_ip_click_total,
  count(distinct(case typ when 2 then ip else null end)) as dis_ip_register_total,
  count(distinct(case typ when 3 then ip else null end)) as dis_ip_download_total,
  count(distinct(case typ when 4 then ip else null end)) as dis_ip_login_total,
  count(distinct(case typ when 5 then ip else null end)) as dis_ip_apply_total,

  sum(case typ when 1 then 1 else 0 end) as click_total,
  sum(case typ when 2 then 1 else 0 end) as register_total,
  sum(case typ when 3 then 1 else 0 end) as download_total,
  sum(case typ when 4 then 1 else 0 end) as login_total,
  sum(case typ when 5 then 1 else 0 end) as apply_total,

  count(distinct(case when typ=1 and device_typ='ios' then ip else null end)) as ios_ip_click_total,
  count(distinct(case when typ=2 and device_typ='ios' then ip else null end)) as ios_ip_register_total,
  count(distinct(case when typ=3 and device_typ='ios' then ip else null end)) as ios_ip_download_total,
  count(distinct(case when typ=4 and device_typ='ios' then ip else null end)) as ios_ip_login_total,
  count(distinct(case when typ=5 and device_typ='ios' then ip else null end)) as ios_ip_apply_total,

  sum(case when typ=1 and device_typ='ios' then 1 else 0 end) as ios_click_total,
  sum(case when typ=2 and device_typ='ios' then 1 else 0 end) as ios_register_total,
  sum(case when typ=3 and device_typ='ios' then 1 else 0 end) as ios_download_total,
  sum(case when typ=4 and device_typ='ios' then 1 else 0 end) as ios_login_total,
  sum(case when typ=5 and device_typ='ios' then 1 else 0 end) as ios_apply_total,
 
  count(distinct(case when typ=1 and device_typ='android' then ip else null end)) as android_ip_click_total,
  count(distinct(case when typ=2 and device_typ='android' then ip else null end)) as android_ip_register_total,
  count(distinct(case when typ=3 and device_typ='android' then ip else null end)) as android_ip_download_total,
  count(distinct(case when typ=4 and device_typ='android' then ip else null end)) as android_ip_login_total,
  count(distinct(case when typ=5 and device_typ='android' then ip else null end)) as android_ip_apply_total,

  sum(case when typ=1 and device_typ='android' then 1 else 0 end) as android_click_total,
  sum(case when typ=2 and device_typ='android' then 1 else 0 end) as android_register_total,
  sum(case when typ=3 and device_typ='android' then 1 else 0 end) as android_download_total,
  sum(case when typ=4 and device_typ='android' then 1 else 0 end) as android_login_total,
  sum(case when typ=5 and device_typ='android' then 1 else 0 end) as android_apply_total,

  count(distinct(case when typ=1 and device_typ='other' then ip else null end)) as otheros_ip_click_total,
  count(distinct(case when typ=2 and device_typ='other' then ip else null end)) as otheros_ip_register_total,
  count(distinct(case when typ=3 and device_typ='other' then ip else null end)) as otheros_ip_download_total,
  count(distinct(case when typ=4 and device_typ='other' then ip else null end)) as otheros_ip_login_total,
  count(distinct(case when typ=5 and device_typ='other' then ip else null end)) as otheros_ip_apply_total,

  sum(case when typ=1 and device_typ='other' then 1 else 0 end) as otheros_click_total,
  sum(case when typ=2 and device_typ='other' then 1 else 0 end) as otheros_register_total,
  sum(case when typ=3 and device_typ='other' then 1 else 0 end) as otheros_download_total,
  sum(case when typ=4 and device_typ='other' then 1 else 0 end) as otheros_login_total,
  sum(case when typ=5 and device_typ='other' then 1 else 0 end) as otheros_apply_total,
  
  max(promotion_typ) as promotion_typ,
  max(promotion_cost) as promotion_cost
  
  FROM unichain_product_center.promotion_statistics
  GROUP BY product_id,source,substring(ctime, 1, 10)) a
;


-- 会员转换统计表(unichain_report_view.base_member_index)：输出会员转换统计指标
-- 
DROP TABLE IF EXISTS unichain_report_view.base_member_index;
CREATE TABLE unichain_report_view.base_member_index AS
SELECT
  product_id as 产品ID,
  product_name as 产品名称,
  source as 渠道ID,
  mbctime as 会员注册时间,
  account_ctime as 账户注册时间,
  member_total as 会员总数,
  account_total as 注册总数,
  current_day_member_num as 当天会员注册数,
  account_to_member_percentage as 注册转换率,
  avg_account_money as 平均注册收益,
  mb_total_money as 会员总收益
  FROM(
    SELECT 
    product_id,
    MAX(product_name) as product_name,
    source,
    max(mbctime) as mbctime,
    min(account_ctime) as account_ctime,
    
    count(mbid) as member_total,
    count(a.account_id) as account_total,
    count(case when substring(mbctime,0,10) = substring(account_ctime,0,10) then mbid else null end) as current_day_member_num,
    
    
    round(count(mbid)/count(account_id),4)*100 as account_to_member_percentage,
    round(sum(mbcost)/count(account_id),2) as avg_account_money,
    
    sum(mbcost) as mb_total_money
    FROM(
      SELECT ps.account_id as account_id,ps.account_phone as account_phone,ps.product_id as product_id,ps.product_name as product_name, ps.source as source,ps.account_ctime,mi.mbid as mbid,mi.mbctime as mbctime,mi.mbcost as mbcost,mi.mbname as mbname
        FROM
        (SELECT account_id,max(account_phone) as account_phone,max(product_id) as product_id,max(product_name) as product_name,max(source) as source,min(ctime) as account_ctime
          FROM unichain_product_center.promotion_statistics GROUP BY account_id)ps 
        LEFT OUTER JOIN 
        (SELECT ma.account_id AS account_id,ma.app_id AS app_id,ma.ctime AS mactime,ma.deleted AS madeleted,ma.member_code AS mbid,mb.ctime AS mbctime,mb.cost AS mbcost,mb.name AS mbname 
          FROM unichain_membership.member mb LEFT OUTER JOIN unichain_membership.member_account ma ON mb.id = ma.member_no) mi 
        ON ps.account_id = mi.account_id) a
    GROUP by product_id,source,substring(mbctime,0,10)
  )a;