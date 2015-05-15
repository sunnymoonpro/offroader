package com.offroader.orm;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.offroader.core.OffRoaderApp;
import com.offroader.utils.LogUtils;

/**
 * 静态Helper类，用于建立、更新和打开数据库
 * 
 * @author li.li
 *
 */
public abstract class DBHelper extends OrmLiteSqliteOpenHelper implements GetTableAble {

	/**
	 * ormlite框架使用
	 * @param context
	 */
	public DBHelper(Context context, String dbName, int dbVersion) {
		super(context, dbName, null, dbVersion);

	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {

			for (Class<?> clazz : tables()) {

				TableUtils.createTable(connectionSource, clazz);
			}
			//LogUtils.info("创建ORM数据库成功" + getDatabaseName());
		} catch (SQLException e) {
			LogUtils.error("创建ORM数据库失败|" + e.getMessage(), e);
		}
	}

	//	@Override
	//	public String getDatabaseName() {
	//		// TODO Auto-generated method stub
	//		return super.getDatabaseName();
	//	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {

			for (Class<?> clazz : tables()) {
				TableUtils.createTableIfNotExists(connectionSource, clazz);
			}
			//LogUtils.info("更新ORM数据库成功" + getDatabaseName() + "|" + oldVersion + "|" + newVersion);

		} catch (SQLException e) {
			LogUtils.error("更新ORM数据库失败|" + e.getMessage(), e);
		}

	}

	//	@Override
	//	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	//			/**
	//			 * 执行数据库的降级操作
	//			 * 1、只有新版本比旧版本低的时候才会执行
	//			 * 2、如果不执行降级操作，会抛出异常
	//			 */
	//			LogUtils.info("#############ORM数据库降级了##############:" + oldVersion + "|" + newVersion);
	//		}
	//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T getHelper(Class clazz) {

		return (T) OpenHelperManager.getHelper(OffRoaderApp.getInstance(), clazz);
	}

	@Override
	public abstract List<Class<? extends TableAble>> tables();

}
