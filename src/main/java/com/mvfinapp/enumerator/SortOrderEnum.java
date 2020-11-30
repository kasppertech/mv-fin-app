package com.mvfinapp.enumerator;


public enum SortOrderEnum {

	ASC(1), DESC(-1);

	private int key;

	SortOrderEnum(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}

	public SortOrderEnum getByKey(int key) {
		for (SortOrderEnum e : SortOrderEnum.values()) {
			if (e.getKey() == key) {
				return e;
			}
		}

		return null;
	}

}

