package it.ldlife.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.PayInfoDao;
import it.ldlife.pojo.PayInfo;

@Repository("payInfoDaoImpl")
public class PayInfoDaoImpl extends MongoBaseDaoImpl<PayInfo> implements PayInfoDao{

}
