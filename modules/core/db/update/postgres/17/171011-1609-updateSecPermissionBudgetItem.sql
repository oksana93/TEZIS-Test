--Insert permissions for entity tezistest$BudgetItem
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:create',0,(select id from sec_role where name='SimpleUser'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:update',0,(select id from sec_role where name='SimpleUser'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:delete',0,(select id from sec_role where name='SimpleUser'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:create',1,(select id from sec_role where name='Administrators'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:update',1,(select id from sec_role where name='Administrators'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:delete',1,(select id from sec_role where name='Administrators'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:create',1,(select id from sec_role where name='ReferenceEditor'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:update',1,(select id from sec_role where name='ReferenceEditor'));
INSERT INTO sec_permission (id,create_ts,created_by,version,update_ts,updated_by,delete_ts,deleted_by,PERMISSION_TYPE,target,value,role_id) VALUES (newid(),now(),'system',1,now(),null,null,null,20,'tezistest$BudgetItem:delete',1,(select id from sec_role where name='ReferenceEditor'));