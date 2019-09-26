SELECT q.id, q.number, q.description, -- General
-- Categories
cat1.id Cat1Id, cat1.description Category1, 
case 
	when cat2.id is null then 0
    else cat2.id
end as Cat2Id,
case 
	when cat2.description is null then "-"
    else cat2.description
end as Category2,
case 
	when cat3.id is null then 0
    else cat3.id
end as Cat3Id,
case 
	when cat3.description is null then "-"
    else cat3.description
end as Category3,

f.id facultyId, f.description faculty, -- Faculties
l.id locationId, l.description location, -- locations
holland1,  
  CASE 
    WHEN holland2 is null then 0
    else holland2
  end as holland2,
case 
	when q.scifield1 is null then 0
    else q.scifield1
end as scifield1, 
case 
	when q.scifield2 is null then 0
    else q.scifield2
end as scifield2,
case 
	when q.scifield3 is null then 0
    else q.scifield3
end as scifield3,
case 
	when q.scifield4 is null then 0
    else q.scifield4
end as scifield4
FROM questions q inner join categories cat1 on cat1.id = q.category left join categories cat2 on cat2.id = q.category2
left join categories cat3 on cat3.id = q.subcategory
inner join questions_faculties qf on qf.qf_quid = q.id inner join faculties f on f.id = qf.qf_fcid
inner join faculties_locations fl on fl.faculty_id = f.id inner join locations l on l.id = fl.location_id
order by q.number, f.description, l.description