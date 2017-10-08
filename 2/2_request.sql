--Сформировать список {каждый_юзер, лучшие_дизайны}/по_каждому_используемому_блоку
--По заданию, финишный состав полей: user_id,design_id (без высветки id_блока,...)
select distinct --быстрее, чем group by
  --у кого
  site.user_id
  --что показываем
  ,case
   --статистики небыло?
   when max_cpm is NULL
   --(небыло) то Первый_дизайн:
   then design_1.site_area_design_1_id
   --иначе Лучший_дизайн (из кэша, с max CPM):
   else design_max.design_best
  end as design_id
from
  --для получения Сайты(user_id). В задании не указано об отсечке при разных status
  site
  --для получения Используемые_блоки(сайт)
  left join (select distinct site_area_id,parent_id from site_area
   where not deleted=1 --Используемый
  ) area
  on area.parent_id=site.site_id
  --для получения Первый_дизайн(блок)
  left join (select distinct site_area_id,site_area_design_1_id from site_area_design_1) design_1
  on design_1.site_area_id=area.site_area_id
  --вычисляем Лучший_дизайн(блок)
  left join (SELECT site_area_id
   --раскодируем максимальный_cpm
   ,LEFT(cpm_did,INSTR(cpm_did, ';')-1) as max_cpm
   --раскодируем лучший_дизайн
   ,CONVERT(RIGHT(cpm_did,LENGTH(cpm_did)-INSTR(cpm_did, ';')),UNSIGNED INT) as design_best
   FROM (select site_area_id,design_id
	,case
	 when sum(view_count)#0
	  --вычисляем Суммарный_CPM(блок,дизайн). Суммарный (т.е. за_все_дни), т.к. в задании есть требование "учитывать все partner_gain, даже нулевые"
	  then CONCAT(sum(partner_gain)/sum(view_count)*1000,';',design_id) -- собрали в 'cpm;did'
	  else CONCAT(0,";",design_id)
	 end as cpm_did
	from stat_design_cache
	group by site_area_id,design_id
	order by CONCAT(sum(partner_gain)/sum(view_count)*1000,';',design_id) desc
   ) b --рассчитываем max_cpm в одном месте, чтобы потом не сталкиваться с сложностями = значений float разной точности (32 и 64 бит)
   GROUP BY site_area_id
   ) design_max
   on area.site_area_id=design_max.site_area_id
--where site.status=1 --только активные_сайты (отключено, т.к. требуемое поведение не указано заданием. Результат тот же)
order by user_id,design_id --финишная сортировка: у кого, что (В задании не указано сортировать по cpm)