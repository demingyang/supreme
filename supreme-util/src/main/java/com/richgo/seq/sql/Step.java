package com.richgo.seq.sql;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 *
 * @author zhouxy
 *
 */
class Step {
	/** 当前序列号 */
	private AtomicLong currentAtomicLong = null;
	/** 当前能使用最大序列=当前序列+能使用个数（ endValue=currentValue+blockSize） */
	private long endValue;

	Step(long currentValue, long endValue) {
		this.endValue = endValue;
		this.currentAtomicLong = new AtomicLong(currentValue);
	}

	/**
	 *
	 * 获取新序列号
	 *
	 * @return long
	 */
	long incrementAndGet() {
		return currentAtomicLong.incrementAndGet();
	}

	void setCurrentValue(long currentValue) {
		this.currentAtomicLong.set(currentValue);
	}

	void setEndValue(long endValue) {
		this.endValue = endValue;
	}

	long getCurrentValue() {
		return currentAtomicLong.get();
	}

	long getEndValue() {
		return endValue;
	}
}