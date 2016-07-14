package com.pizza.wechat.request;

/**
 * 微信多媒体素材类型
 * 
 * @ClassName : MediaFileType
 * @author : emmy.cheng
 * @date : 2015年10月23日 下午10:46:44
 */
public enum MediaType {
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
	 * 视频
	 */
	VIDEO {
		@Override
		public String Type() {
			return "video";
		}
	},
	/**
	 * 图文
	 */
	NEWS {
		@Override
		public String Type() {
			return "news";
		}
	};
	/**
	 * 素材类型
	 * 
	 * @return
	 */
	public abstract String Type();
}
