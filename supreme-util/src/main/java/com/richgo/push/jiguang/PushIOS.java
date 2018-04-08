package com.richgo.push.jiguang;

import java.util.Map;
import java.util.Properties;

import com.richgo.util.FileUtils;

import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 推送到iOS设备
 * 
 * @author shenkun
 * @date 2017年5月9日
 */
public class PushIOS extends PushDeviceParent {


	public Platform getPlatform() {
		return Platform.ios();
	}

	protected Builder setOption(Builder builder) {
		return builder.setOptions(Options.newBuilder()
				.setApnsProduction(getAPNEnv()).build());
	}

	protected Builder setNotification(Builder builder, String alert,
			Map<String, String> map) {

		return builder.setNotification(Notification
				.newBuilder()
				.setAlert(alert)
				.addPlatformNotification(
						IosNotification.newBuilder().addExtras(map).build())
				.build());

	}
	
	protected Builder setNotification(Builder builder, String alert, String title,
			Map<String, String> map) {

		return setNotification(builder,alert,map);

	}
	

}
