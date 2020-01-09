package com.epoch.demo;

public class JsonTest {

	public static void main(String[] args) {
		String id = "1,2,2";
		System.out.println("==="+id.length());
		String[] strings = id.split(",");
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
