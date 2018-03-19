package com.miot.common.network.mlcc.pojo.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;

public class RespGetGpioAck extends RespBaseAck {
	public RespGetGpioAck() {
		super.chazzType = RespGetGpioAck.class;
		super.clazzTypeDesc = MLCCCodeConfig.MLCCCodeReturn.GET_GPIO_ACK;
	}

	private static final String SPLI = "`";
	private String codeName;
	private String cChn;
	private String cType;
	private String cState;

	private List<OneGpio> OneGpio = null;

	public class OneGpio {
		private String chn;
		private String type;
		private String state;

		public String getChn() {

			return chn;
		}

		public String getType() {
			return type;
		}

		public String getState() {
			return state;
		}

		@Override
		public String toString() {
			return "OneGpio [chn=" + chn + ", type=" + type + ", state="
					+ state + "]";
		}
	}

	private void init(int length) {
		if (OneGpio == null) {
			synchronized (RespGetGpioAck.class) {
				if (OneGpio == null) {
					OneGpio = new ArrayList<OneGpio>(length);
					for (int i = 0; i < length; i++) {
						OneGpio.add(new OneGpio());
					}
				}
			}
		}
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public void setcChn(String cChn) {
		this.cChn = cChn;
		if (cChn == null)
			return;
		String[] cChnArray = cChn.split(SPLI);
		if (cChnArray == null || cChnArray.length == 0) {
			return;
		}
		init(cChnArray.length);

		for (int i = 0; i < this.OneGpio.size(); i++) {
			this.OneGpio.get(i).chn = cChnArray[i];
		}
	}

	public void setcType(String cType) {
		this.cType = cType;
		if (cType == null)
			return;
		String[] cTypeArray = cType.split(SPLI);
		if (cTypeArray == null || cTypeArray.length == 0) {
			return;
		}
		init(cTypeArray.length);

		for (int i = 0; i < this.OneGpio.size(); i++) {
			this.OneGpio.get(i).type = cTypeArray[i];
		}

	}

	public void setcState(String cState) {
		this.cState = cState;
		if (cState == null)
			return;
		String[] cStateArray = cState.split(SPLI);
		if (cStateArray == null || cStateArray.length == 0) {
			return;
		}
		init(cStateArray.length);

		for (int i = 0; i < this.OneGpio.size(); i++) {
			this.OneGpio.get(i).state = cStateArray[i];
		}

	}

	public List<OneGpio> getOneGpio() {
		return OneGpio;
	}

	@Override
	public String toString() {
		return super.toString() + "RespGetGpioAck [codeName=" + codeName
				+ " OneGpio=" + OneGpio + "]";
	}

	@Override
	public synchronized void make(Map<String, String> resultMap) {
		setcChn(cChn);
		setcType(cType);
		setcState(cState);
		super.setResultMap(resultMap);
	}

}
