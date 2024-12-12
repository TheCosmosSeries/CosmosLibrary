package com.tcn.cosmoslibrary.common.lib;

import java.util.LinkedHashMap;

public class HashmapHelper {
	
	public static class Linked {
		public static Object getKeyByIndex(LinkedHashMap<?, ?> map, int index){
		    return map.keySet().toArray()[index];
		}
		
		public static Object getValueByIndex(LinkedHashMap<?, ?> map, int index) {
			return map.get((map.keySet().toArray())[index]);
		}
	}
}
