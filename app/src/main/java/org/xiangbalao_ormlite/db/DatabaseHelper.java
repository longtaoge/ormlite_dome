package org.xiangbalao_ormlite.db;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.xiangbalao_ormlite.bean.Article;
import org.xiangbalao_ormlite.bean.Student;
import org.xiangbalao_ormlite.bean.User;

import android.R.bool;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 类名: DatabaseHelper</br>
 * 描述: 数据库操作类 </br>
 * 开发人员： longtaoge</br>
 * 创建时间： 2015-3-22
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    /**
     * 数据库配置文件路径
     */
    private static String DATABASE_PATH_JOURN = Environment.getExternalStorageDirectory() + "/sqlite-test.db-journal";
    
    /**
     * 数据库路径
     */
    private static String DATABASE_PATH = Environment.getExternalStorageDirectory() + "/sqlite-test.db";
    
    /**
     * 数据库名称
     */
    private static final String TABLE_NAME = "sqlite-test.db";
    
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    
    private Context mContext;
    
    private DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 4);
        
        mContext = context;
        // initDtaBasePath();
        try
        { // 创建数据库
        
            File f = new File(DATABASE_PATH);
            if (!f.exists())
            {
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
                onCreate(db);
                db.close();
            }
        }
        catch (Exception e)
        {
        }
        
    }
    
    /*
     * 
     * private void initDtaBasePath()
     * {
     * if (!sdCardIsExsit())
     * {
     * DATABASE_PATH = mContext.getFilesDir().getAbsolutePath() + "/sqlite-test.db";
     * DATABASE_PATH_JOURN = mContext.getFilesDir().getAbsolutePath() + "/sqlite-test.db-journal";
     * }
     * }
     */
    
    /** 
     * 创建表
     * 开发人员：longtaoge
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Article.class);
            TableUtils.createTable(connectionSource, Student.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /** 
     * 更新数据
     * 开发人员：longtaoge
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Article.class, true);
            TableUtils.dropTable(connectionSource, Student.class, true);
            onCreate(database, connectionSource);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    private static DatabaseHelper instance;
    
    /**
     * 单例获取该Helper
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        context = context.getApplicationContext();
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        
        return instance;
    }
    /** 
     * 获取 Dao 对象
     * 开发人员：longtaoge
     */
    @Override
    public synchronized Dao getDao(Class clazz)
        throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();
        
        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }
    
    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();
        
        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
    
    public boolean sdCardIsExsit()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    
    /**
     * 配置数据库路径
     */
    @Override
    public synchronized SQLiteDatabase getWritableDatabase()
    {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
    /**
     * 配置数据库路径
     */
    @Override
    public synchronized SQLiteDatabase getReadableDatabase()
    {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }
    
}
