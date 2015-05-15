package com.offroader.orm;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.offroader.core.OffRoaderApp;

/**
 * 数据库DBHelper类的子类，供使用者更容易的使用ORM
 * 
 * @author li.li
 *
 * Mar 28, 2013
 */
public class BaseDBHelper extends DBHelper {
	private static volatile BaseDBHelper instance;
	protected final List<Class<? extends TableAble>> tables;

	public BaseDBHelper(Context context, String dbName, int dbVersion) {
		super(context, dbName, dbVersion);
	}

	{
		this.tables = new LinkedList<Class<? extends TableAble>>();
	}

	public static BaseDBHelper getInstance(String dbName, int dbVersion) {
		if (instance == null) {
			synchronized (BaseDBHelper.class) {
				if (instance == null) {
					instance = new BaseDBHelper(OffRoaderApp.getInstance(), dbName, dbVersion);
				}
			}
		}
		return instance;
	}

	@Override
	public List<Class<? extends TableAble>> tables() {
		return tables;
	}

}
