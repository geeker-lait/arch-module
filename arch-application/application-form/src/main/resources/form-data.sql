use test2;
insert into upload_field_type (type_code, type_name, type_order)
values ('input', '单行文本框', '1');
insert into upload_field_type (type_code, type_name, type_order)
values ('radio', '单选框', '2');
insert into upload_field_type (type_code, type_name, type_order)
values ('checkbox', '多选框', '3');
insert into upload_field_type (type_code, type_name, type_order)
values ('textarea', '多行文本框', '4');
commit;
