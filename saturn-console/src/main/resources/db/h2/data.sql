INSERT INTO `zk_cluster_info`(`zk_cluster_key`, `alias`, `connect_string`) VALUES('cluster1', '集群1', 'localhost:2181');

INSERT INTO `namespace_zkcluster_mapping`(`namespace`, `name`, `zk_cluster_key`) VALUES('mydomain', '业务组', 'cluster1');

INSERT INTO sys_config(property,value) values('CONSOLE_ZK_CLUSTER_MAPPING','CONSOLE-IT:it_cluster;default:cluster1,it_cluster');

INSERT INTO `user`(`user_name`,`password`) VALUES('admin','admin');
INSERT INTO `user`(`user_name`,`password`) VALUES('guest','guest');
INSERT INTO `user_role`(`user_name`, `role_key`, `need_approval`) VALUES('admin', 'system_admin', '0');

INSERT INTO `role`(`role_key`) VALUES('system_admin');
INSERT INTO `role`(`role_key`) VALUES('namespace_developer');
INSERT INTO `role`(`role_key`) VALUES('namespace_admin');

INSERT INTO `permission`(`permission_key`) VALUES('job:enable');
INSERT INTO `permission`(`permission_key`) VALUES('job:batchEnable');
INSERT INTO `permission`(`permission_key`) VALUES('job:disable');
INSERT INTO `permission`(`permission_key`) VALUES('job:batchDisable');
INSERT INTO `permission`(`permission_key`) VALUES('job:runAtOnce');
INSERT INTO `permission`(`permission_key`) VALUES('job:stopAtOnce');
INSERT INTO `permission`(`permission_key`) VALUES('job:remove');
INSERT INTO `permission`(`permission_key`) VALUES('job:batchRemove');
INSERT INTO `permission`(`permission_key`) VALUES('job:add');
INSERT INTO `permission`(`permission_key`) VALUES('job:copy');
INSERT INTO `permission`(`permission_key`) VALUES('job:import');
INSERT INTO `permission`(`permission_key`) VALUES('job:export');
INSERT INTO `permission`(`permission_key`) VALUES('job:update');
INSERT INTO `permission`(`permission_key`) VALUES('job:batchSetPreferExecutors');
INSERT INTO `permission`(`permission_key`) VALUES('executor:restart');
INSERT INTO `permission`(`permission_key`) VALUES('executor:dump');
INSERT INTO `permission`(`permission_key`) VALUES('executor:extractOrRecoverTraffic');
INSERT INTO `permission`(`permission_key`) VALUES('executor:batchExtractOrRecoverTraffic');
INSERT INTO `permission`(`permission_key`) VALUES('executor:remove');
INSERT INTO `permission`(`permission_key`) VALUES('executor:batchRemove');
INSERT INTO `permission`(`permission_key`) VALUES('executor:shardAllAtOnce');
INSERT INTO `permission`(`permission_key`) VALUES('alarmCenter:setAbnormalJobRead');
INSERT INTO `permission`(`permission_key`) VALUES('alarmCenter:setTimeout4AlarmJobRead');
INSERT INTO `permission`(`permission_key`) VALUES('dashboard:cleanShardingCount');
INSERT INTO `permission`(`permission_key`) VALUES('dashboard:cleanOneJobAnalyse');
INSERT INTO `permission`(`permission_key`) VALUES('dashboard:cleanAllJobAnalyse');
INSERT INTO `permission`(`permission_key`) VALUES('dashboard:cleanOneJobExecutorCount');
INSERT INTO `permission`(`permission_key`) VALUES('registryCenter:addNamespace');
INSERT INTO `permission`(`permission_key`) VALUES('registryCenter:batchMoveNamespaces');
INSERT INTO `permission`(`permission_key`) VALUES('registryCenter:exportNamespaces');
INSERT INTO `permission`(`permission_key`) VALUES('registryCenter:addZkCluster');
INSERT INTO `permission`(`permission_key`) VALUES('systemConfig');
INSERT INTO `permission`(`permission_key`) VALUES('authorizationManage');

INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:enable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:batchEnable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:disable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:batchDisable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:runAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:stopAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:remove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:batchRemove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:add');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:copy');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:import');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:export');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:update');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'job:batchSetPreferExecutors');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:restart');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:dump');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:extractOrRecoverTraffic');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:batchExtractOrRecoverTraffic');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:remove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:batchRemove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'executor:shardAllAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'alarmCenter:setAbnormalJobRead');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'alarmCenter:setTimeout4AlarmJobRead');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'dashboard:cleanShardingCount');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'dashboard:cleanOneJobAnalyse');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'dashboard:cleanAllJobAnalyse');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'dashboard:cleanOneJobExecutorCount');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'registryCenter:addNamespace');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'registryCenter:batchMoveNamespaces');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'registryCenter:exportNamespaces');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'registryCenter:addZkCluster');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'systemConfig');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('system_admin', 'authorizationManage');

INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'job:enable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'job:batchEnable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'job:disable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'job:batchDisable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'job:stopAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'alarmCenter:setAbnormalJobRead');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_developer', 'alarmCenter:setTimeout4AlarmJobRead');

INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:enable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:batchEnable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:disable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:batchDisable');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:runAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:stopAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:remove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:batchRemove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:add');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:copy');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:import');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:export');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:update');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'job:batchSetPreferExecutors');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:restart');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:dump');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:extractOrRecoverTraffic');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:batchExtractOrRecoverTraffic');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:remove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:batchRemove');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'executor:shardAllAtOnce');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'alarmCenter:setAbnormalJobRead');
INSERT INTO `role_permission`(`role_key`, `permission_key`) VALUES('namespace_admin', 'alarmCenter:setTimeout4AlarmJobRead');

-- 3.0.1 update

UPDATE `role` SET `role_name`='系统管理', `is_relating_to_namespace`='0' WHERE `role_key`='system_admin';
UPDATE `role` SET `role_name`='域开发管理', `is_relating_to_namespace`='1' WHERE `role_key`='namespace_developer';
UPDATE `role` SET `role_name`='域管理', `is_relating_to_namespace`='1' WHERE `role_key`='namespace_admin';