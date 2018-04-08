package com.richgo.seq.sql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 系列增长器
 */
 class Sequence {
	private final static Log log = LogFactory.getLog(Sequence.class);

	private final static String GET_SQL = "select seq_value from sys_seq_value where seq_key= ? for update";
	private final static String NEW_SQL = "insert into sys_seq_value (seq_key,seq_value) values (?,?)";
	private final static String UPDATE_SQL = "update sys_seq_value set seq_value = ?  where seq_key = ? and seq_value = ?  ";

	/** 每次从数据库获取序列之后能使用的序列个数 */
	private int blockSize = 50;
	/** 起始计数 */
	private long startValue = 10000;
	private DataSource dataSource;
	/**
	 * 外面是同步的
	 */
	private Map<String, Step> stepMap = new HashMap<String, Step>();

	/**
	 * 只有一个入口，所以。只需要他同步即可
	 *
	 * @return
	 */
	synchronized long get(String sequenceName) {
		Step step = stepMap.get(sequenceName);
		if (step == null) {
			// 如果没有，则要从数据库中读入
			step = new Step(startValue, startValue + blockSize);
			stepMap.put(sequenceName, step);
		} else {
			if (step.getCurrentValue() < step.getEndValue()) {// 默认为0和0
				return step.incrementAndGet();
			}
		}
		for (int i = 0; i < 5; i++) {// 防止同步获取失败
			if (getNextBlock(sequenceName, step)) {
				return step.incrementAndGet();
			}
		}
		throw new RuntimeException("No more value.");
	}

	/**
	 * 
	 * 获取数据库中预存的最大值，所有序列增加一定要在基础上增加
	 * 
	 * @param sequenceName
	 * @param step
	 * @return
	 */
	private boolean getNextBlock(String sequenceName, Step step) {
		boolean b = false;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			Long value = getPersistenceValue(conn,sequenceName);
			if (value == null) {// 如果没有，就初始化
				try {
					value = newPersistenceValue(conn,sequenceName); // 初始化。返回初始化的值
				} catch (Exception e) {
					log.error("newPersistenceValue error!");
					value = getPersistenceValue(conn,sequenceName); // 如果初始化失败，说明有程序先初始化了
				}
			}

			if (value >= Long.MAX_VALUE || (value + blockSize) > Long.MAX_VALUE) {// 超出最大值
				throw new RuntimeException("No more value.");
			}

			b = saveValue(conn,value, sequenceName) == 1;
			if (b) {
				step.setCurrentValue(value);
				step.setEndValue(value + blockSize);
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				b = false;
			}
			log.error("获取并更新序列操作失败！");
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					log.error("close connection error!", e);
				}
			}
		}
		return b;
	}

	private int saveValue(Connection connection, long value, String sequenceName) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE_SQL);
			statement.setLong(1, value + blockSize);
			statement.setString(2, sequenceName);
			statement.setLong(3, value);
			return statement.executeUpdate();
		} catch (Exception e) {
			log.error("newPersistenceValue error!", e);
			throw new RuntimeException("newPersistenceValue error!", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("close statement error!", e);
				}
			}
		}
	}

	private Long getPersistenceValue(Connection connection, String sequenceName) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(GET_SQL);
			statement.setString(1, sequenceName);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong("seq_value");
			}
		} catch (Exception e) {
			log.error("getPersistenceValue error!", e);
			throw new RuntimeException("getPersistenceValue error!", e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.error("close resultset error!", e);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("close statement error!", e);
				}
			}
		}
		return null;
	}

	private Long newPersistenceValue(Connection connection, String sequenceName) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(NEW_SQL);
			statement.setString(1, sequenceName);
			statement.setLong(2, startValue);

			statement.executeUpdate();
		} catch (Exception e) {
			log.error("newPersistenceValue error!", e);
			throw new RuntimeException("newPersistenceValue error!", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("close statement error!", e);
				}
			}
		}
		return startValue;
	}

	void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	void setStartValue(long startValue) {
		this.startValue = startValue;
	}
}
