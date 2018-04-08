package com.seq.sql;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import com.assertions.Assertions;
import com.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 序列获取工具
 * 
 * @author zhouxy
 *
 */
public class SqlSeqUtil {
	/* 序列管理器 */
	private static SequenceManager seqManger = null;

	/**
	 * 
	 * 初始化序号生成器
	 * 
	 * @param dataSource
	 *            DataSource
	 * 
	 */
	public static void initSqlSeq(DataSource dataSource) {
		Assertions.notNull("dataSource", dataSource);

		if (seqManger == null) {
			seqManger = new SequenceManager();

			// 循环枚举值
			Set<String> keyList = getKeyList();
			for (String key : keyList) {
				if (StringUtils.isBlank(key)) {
					continue;
				}
				seqManger.addSequence(dataSource, key, 50);
			}
		}
	}

	/**
	 * 
	 * 获取seqKeyName.propertieskey名称
	 * 
	 * @return
	 */
	private static Set<String> getKeyList() {
		Set<String> keyList = new HashSet<String>();

		// 获取路径
		String path = FileUtils.getClassPathName("seqKeyName.properties");
		// 读取内容
		Properties properties = FileUtils.readProperties(path);

		Enumeration<Object> enu = properties.elements();
		while (enu.hasMoreElements()) {
			Object value = enu.nextElement();
			if (value == null) {
				continue;
			}
			String valueStr = String.valueOf(value);
			if (StringUtils.isNotBlank(valueStr)) {
				keyList.add(valueStr);
			}
		}
		return keyList;
	}

	/**
	 * 
	 * 获取给定的tableName对应的序列号
	 * 
	 * @param tableName
	 *            String 数据库表名称，唯一
	 * @return long 新序列号
	 */
	public static long get(String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException("参数不合法");
		}
		return seqManger.get(tableName.toString());
	}
}