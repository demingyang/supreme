package com.richgo.push.jiguang;

import java.util.Map;
import java.util.Properties;

import com.richgo.util.ConfigUtil;
import com.richgo.util.FileUtils;

import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 消息设备父类,默认(android和ios设备同时接收)
 * @author shenkun
 * @date 2017年5月9日
 */
public class PushDeviceParent {

	/**
	 * ios起作用
	 * @return
	 */
	protected boolean getAPNEnv() {

	 
		String online = ConfigUtil.getValue("JiguangOnline");

		if (online.equals("true")) {
			return true;
		}
		return false;
	}
	
	protected Platform getPlatform() {
		return Platform.android_ios();
	}

	/**
	 * ios起作用
	 * @param builder
	 * @return
	 */
	protected Builder setOption(Builder builder) {
		return builder.setOptions(Options.newBuilder()
				.setApnsProduction(getAPNEnv()).build());
	}

	protected Builder setNotification(Builder builder, String alert,
			Map<String, String> map) {

		return builder.setNotification(
				Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtras(map)
										.build())
						.addPlatformNotification(
								IosNotification.newBuilder().addExtras(map)
										.build())
						.build());

	}
	
	protected Builder setNotification(Builder builder, String alert, String title,
			Map<String, String> map) {

		return builder.setNotification(
				Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtras(map)
										.build()).build()).setNotification(
				Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								IosNotification.newBuilder().addExtras(map)
										.build()).build());

	}
	
	protected Builder setNotification(Builder builder, String alert, String title) {

		return builder.setNotification(
				Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title)
										.build()).build()).setNotification(
				Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								IosNotification.newBuilder()
										.build()).build());

	}
	
	/**
	 * TODO 加入批量别名推送
	 * @param builder
	 * @return
	 */
	protected Builder setAudienceTargets(Builder builder) {
		return builder;
	}
	
}
