package com.pizza.wechat.request;

/**
 * 微信多媒体文件上传类型
 * 
 * @ClassName : MediaFileType
 * @author : emmy.cheng
 * @date : 2015年10月23日 下午10:46:44
 */
public enum MediaFileType {
	/**
	 * 图片
	 */
	IMAGE {
		@Override
		public String Type() {
			return "image";
		}
	},
	/**
	 * 语音
	 */
	VOICE {
		@Override
		public String Type() {
			return "voice";
		}
	},
	/**
	 * 封面
	 */
	THUMB {
		@Override
		public String Type() {
			return "thumb";
		}
	},
	/**
	 * 视频
	 */
	VIDEO {
		@Override
		public String Type() {
			return "video";
		}
	};
	/**
	 * 媒体类型
	 * @return
	 */
	public abstract String Type();
}
