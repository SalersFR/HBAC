package gg.salers.honeybadger.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ClientVersion {

	v18("1.8.x", 47, 8), v19("1.9", 107, 9), v191("1.9.1", 108, 9), v192("1.9.2", 109, 9), v193("1.9.3/4", 110, 9),
	v110("1.10.x", 210, 10), v111("1.11", 315, 11), v1111("1.11.1/2", 316, 11), v112("1.12", 335, 12),
	v1121("1.12.1", 338, 12), v1122("1.12.2", 340, 12), v113("1.13", 393, 13), v1131("1.13.1", 401, 13),
	v1132("1.13.2", 404, 13), v114("1.14", 477, 14), v1141("1.14.1", 480, 14), v1142("1.14.2", 485, 14),
	v1143("1.14.3", 490, 14), v1144("1.14.4", 498, 14), v115("1.15", 573, 15), v1151("1.15.1", 575, 15),
	v1152("1.15.2", 578, 15), v116("1.16", 735, 16), v1161("1.16.1", 736, 16), v1162("1.16.2", 751, 16),
	v1163("1.16.3", 753, 16), v1164("1.16.4/5", 754, 16), v1117("1.17", 755, 17), v11171("1.17.1", 756, 17);

	String version = "not resolved";

	@Setter
	String brand = "vanilla";
	@Setter
	boolean bedrock = false;

	int protocol, intVersion;

	ClientVersion(String string, int protocolVersion, int version) {
		this.version = string;
		this.protocol = protocolVersion;
		this.intVersion = version;
	}

	public static ClientVersion matchProtocol(final int protocol) {
		int closestMatch = 0;
		ClientVersion closestVersion = v18;
		for (final ClientVersion v : values())
			if (v.protocol == protocol)
				return v;
			else if (Math.abs(v.protocol - protocol) > Math.abs(v.protocol - closestMatch)) {
				closestMatch = v.protocol;
				closestVersion = v;
			}
		return closestVersion;
	}

}
