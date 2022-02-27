package com.karafmodule.child1module;

import java.util.Scanner;

import org.osgi.service.component.annotations.Component;

@Component
public class ReverseNumber {
	
	public static void reverseNumber() {

	int reverse = 0;
	Scanner scanner = new Scanner(System.in);
	int number = scanner.nextInt();

	while(number!=0)
	{
		int remainder = number % 10;
		reverse = reverse * 10 + remainder;
		number = number / 10;
	}
	System.out.println("The reverse of the given number is: "+reverse);
    }
}

