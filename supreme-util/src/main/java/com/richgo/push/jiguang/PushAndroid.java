package com.richgo.push.jiguang;

import java.util.Map;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * android设备
 * @author shenkun
 * @date 2017年5月9日
 */
public class PushAndroid extends PushDeviceParent {

	public Platform getPlatform() {
		return Platform.android();
	}

	public Builder setOption(Builder builder) {
		return builder;
	}

	protected Builder setNotification(Builder builder, String alert,
			Map<String, String> map) {

		return builder
				.setNotification(Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().addExtras(map)
										.build()).build());

	}
	
	protected Builder setNotification(Builder builder, String alert,String title,
			Map<String, String> map) {

		return builder
				.setNotification(Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtras(map)
										.build()).build());

	}
	
	protected Builder setNotification(Builder builder, String alert,String title) {

		return builder
				.setNotification(Notification
						.newBuilder()
						.setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title)
										.build()).build());

	}
}
