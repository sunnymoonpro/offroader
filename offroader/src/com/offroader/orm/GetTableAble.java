package com.offroader.orm;

import java.util.List;

/**
 * 需要建的数据库表
 * 
 * @author li.li
 *
 */
public interface GetTableAble {

	List<Class<? extends TableAble>> tables();

}
