package com.seq.sql;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

/**
 * 系列增长器。当指定了默认的系列增长器后，可以不用为每一个sequence配置一个系列增长器。<br/>
 * 指定系列增长器是为了配置不同的增长器模式，如：初始值，步长。<br/>
 */
class SequenceManager {
	/** 序列生成器集合 */
	private Map<String, Sequence> sequenceMap;

	/**
	 * 
	 * 如果没有在map中指定，则出错。
	 * 
	 * @param name
	 *            sequence名字
	 * @return long
	 * @throws RuntimeException 如果没有在map中指定异常
	 * 
	 */
	long get(String name) {
		Sequence sequence = null;
		if (sequenceMap != null) {
			sequence = sequenceMap.get(name);
		}
		if (sequence == null) {
			throw new RuntimeException("Sequence " + name + " undefined!");
		}
		return sequence.get(name);
	}

	/**
	 * 
	 * 添加序列生成器
	 * 
	 * @param dataSource
	 *            DataSource 存放序列生成器数据的数据源
	 * @param name
	 *            序列唯一标识
	 * @param blockSize
	 *            int 获取序列使用个数 不小于5
	 * @return boolean true：添加成功；false：添加失败
	 */
	boolean addSequence(DataSource dataSource, String name, int blockSize) {
		if (dataSource == null || StringUtils.isBlank(name) || blockSize < 5) {
			throw new IllegalArgumentException("参数不合法");
		}
		if (sequenceMap == null) {
			sequenceMap = new HashMap<String, Sequence>();
		} else if (sequenceMap.containsKey(name)) {// 已经存在
			return false;
		}

		// 生成序列生成器
		Sequence seq = instanceSequence(dataSource, name, blockSize);
		// 添加到集合中
		sequenceMap.put(name, seq);

		return true;
	}

	/**
	 * 
	 * 生成序列生成器对象
	 * 
	 * @param dataSource
	 *            DataSource 存放序列生成器数据的数据源
	 * @param name
	 *            序列唯一标识
	 * @param blockSize
	 *            int 获取序列使用个数 不小于5
	 * @return Sequence
	 */
	private Sequence instanceSequence(DataSource dataSource, String name, int blockSize) {
		// 生成序列生成器
		Sequence seq = new Sequence();
		seq.setDataSource(dataSource);
		seq.setBlockSize(blockSize);
		return seq;
	}
}