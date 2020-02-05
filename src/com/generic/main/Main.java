package com.generic.main;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Import (I) or Export (E) Warehouse Contents");
		String response = scanner.nextLine();
		if(response.equalsIgnoreCase("I")) {
			//Call JSON Parser
		}
	}
}
