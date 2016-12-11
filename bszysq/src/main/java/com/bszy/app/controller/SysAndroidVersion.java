//package com.bszy.app.controller;
//
//import com.haowai.bean.ChanelVersionBean;
//import com.haowai.bean.VersionResp;
//import com.haowai.common.DataResponse;
//import com.haowai.common.Response;
//import org.nutz.dao.Dao;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.sql.Sql;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
///**
// * @author S.Z.Y
// * @created 2016-03-24 上午11:20
// */
//
//@Controller
//@RequestMapping(value = "/sys")
//public class SysAndroidVersion {
//    @Autowired
//    Dao nutDao;
//
//
//    @RequestMapping(value = "/version/{packageName}/**", method = RequestMethod.GET)
//    @ResponseBody
//    public Response version(@PathVariable String packageName) {
//
//        Sql sql = Sqls.create(" SELECT c.version,c.version_code,c.bb_describe as 'describe' ,c.bb_force as 'force' ,c.url FROM\n" +
//                "(SELECT\n" +
//                "    a.version,\n" +
//                "    a.version_code,\n" +
//                "    a.bb_describe,\n" +
//                "    a.bb_force,\n" +
//                "    a.url\n" +
//                "FROM sys_version2 a\n" +
//                "WHERE a.package = $packageName\n" +
//                "ORDER BY a.upgrade_time DESC\n" +
//                "limit 0,1) AS c\n" +
//                "UNION\n" +
//                "SELECT d.version,d.version_code,d.bb_describe as 'describe',d.bb_force  as 'force',d.url FROM\n" +
//                "(SELECT\n" +
//                "    b.version,\n" +
//                "    b.version_code,\n" +
//                "    b.bb_describe,\n" +
//                "    b.bb_force,\n" +
//                "    b.url\n" +
//                "FROM sys_version2 b\n" +
//                "WHERE b.package =  $packageName AND b.bb_force = 1\n" +
//                "ORDER BY b.upgrade_time DESC\n" +
//                "limit 0,1) AS d ");
//        sql.params().set("packageName", packageName);
//        sql.vars().set("packageName", "'"+packageName+"'");
//        sql.setCallback(Sqls.callback.entities());
//        sql.setEntity(nutDao.getEntity(VersionResp.class));
//        nutDao.execute(sql);
//        VersionResp c = sql.getObject(VersionResp.class);
//        List<VersionResp> versionResps = sql.getList(VersionResp.class);
//
//        if(versionResps != null){
//            if(versionResps.size() == 1){
//                DataResponse data = new DataResponse();
//                data.setData(versionResps.get(0));
//                return data;
//
//
//            }else if(versionResps.size() == 2){
//                VersionResp last = versionResps.get(0);
//                VersionResp older = versionResps.get(1);
//
//                VersionResp respBody =  last.bulidRespBody(older);
//
//                DataResponse data = new DataResponse();
//                data.setData(respBody);
//
//                return data;
//
//            }
//        }
//
//
//        return new Response();
//    }
//
//
//    @RequestMapping("/getChanelVersion")
//    @ResponseBody
//    public Response getChanelVersion(){
//        List<ChanelVersionBean> list = nutDao.query(ChanelVersionBean.class,null);
//        DataResponse data = new DataResponse();
//        data.setData(list);
//        return data;
//    }
//}
