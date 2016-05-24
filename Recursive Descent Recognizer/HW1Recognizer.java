import java.io.*;
import java.util.Scanner;
//--------------------------------------------
// Khoi Hoang
// Recursive-descent recognizer 
// HW1
// Credit using the example parser written by Dr. Scott Gordon
// to run on Athena (linux) -
// save as: HW1Recognizer.java
// compile: javac HW1Recognizer.java
// execute: java HW1Recognizer
//
// EBNF Grammar is -
// program ::== P {declare} B {statmt} E;
// declare ::= ident{, ident}: V;
// statmt ::= assnmt | ifstmt | loop | read | output
// assnmt ::= ident~ exprsn ;
// ifstmt ::= I comprsn @ {statmt} [% {statmt}] & 
// loop ::= W comprsn L {statmt} T 
// read ::= R ident {, ident } ; 
// output ::= O ident {, ident } ; 
// comprsn ::= ( oprnd opratr oprnd ) 
// exprsn ::= factor {+ factor} 
// factor ::= oprnd {* oprnd} 
// oprnd ::= integer | ident | ( exprsn ) 
// opratr ::= < | = | > | ! 
// ident ::= letter {char} 
// char ::= letter | digit 
// integer ::= digit {digit} 
// letter ::= X | Y | Z 
// digit ::= 0 | 1
// Some example of legal expessions are: PBE;$, PX1,Y0,Z1:V;BE;$, PBI(1>0)@&E;$, PBRX;E;$, PX:V;BRZ;E;$, PBW(0<1)LTE;$
//--------------------------------------------

public class HW1Recognizer{
	static String inputString;
	static int index = 0;
	static int errorflag = 0;
	
	private char token(){ //credit to Dr.Gordon's code
		return (inputString.charAt(index));
	}
	
	private void advancePtr(){ //credit to Dr.Gordon's code
		if (index < (inputString.length()-1))
			index++;
	}
	
	private void match(char T){ //credit to Dr.Gordon's code
		if (T == token())
			advancePtr();
		else
			error();
	}
	
	private void error(){ //credit to Dr.Gordon's code
		System.out.println("Error at position: " + index);
		errorflag = 1;
		advancePtr();
	}
	
	private void program(){ //program
		match('P');
		
		while ((token()=='X') || (token() == 'Y') || (token() == 'Z')) //FIRST(declare)
			declare();
		match('B');
		//FIRST(statmt)
		while ((token()=='X') || (token() == 'Y') || (token() == 'Z') || (token() == 'I') || (token() == 'W') || (token() == 'R') || (token() == 'O'))
			statmt();
		
		match('E');
		match(';');
		//match('$');
	}
	
	private void declare(){ //declare
		ident();
		while (token() == ','){
			if (token() == ',')
				match(',');
			ident();
		}
		match(':');
		match('V');
		match(';');
	}
	
	private void statmt(){ //statmt
		if ((token()=='X') || (token() == 'Y') || (token() == 'Z')) //FIRST(assnmt)
			assnmt();
		else if (token() == 'I') //FIRST(ifstmt)
			ifstmt();
		else if (token() == 'W') //FIRST(loop)
			loop();
		else if (token() == 'R') //FIRST(read))
			read();
		else if (token() == 'O') //FIRST(output)
			output();
		else
			error();
	}
	
	private void assnmt(){ //assnmt
		ident();
		match ('~');
		exprsn();
		match(';');
	}
	
	private void ifstmt(){ //ifstmt
		match('I');
		comprsn();
		match('@');
		//FIRST(statmt)
		while ((token()=='X') || (token() == 'Y') || (token() == 'Z') || (token() == 'I') || (token() == 'W') || (token() == 'R') || (token() == 'O'))
			statmt();
		if (token() == '%'){
			match('%');
			while (token() != '&')
				statmt();
		}
		match('&');
	}
	
	private void loop(){ //loop
		match('W');
		comprsn();
		match('L');
		//FIRST(statmt)
		while ((token()=='X') || (token() == 'Y') || (token() == 'Z') || (token() == 'I') || (token() == 'W') || (token() == 'R') || (token() == 'O'))
			statmt();
		match('T');
	}
	
	private void read(){ //read
		match('R');
		ident();
		while (token() == ','){
			if (token() == ',')
				match(',');
			ident();
		}
		match(';');
	}
	
	private void output(){ //output
		match('O');
		ident();
		while (token() == ','){
			if (token() == ',')
				match(',');
			ident();
		}
		match(';');
	}
	
	private void comprsn(){ //comprsn
		match('(');
		oprnd();
		opratr();
		oprnd();
		match(')');
	}
	
	private void exprsn(){ //exprsn
		factor();
		while (token() == '+'){
			if (token() == '+')
				match('+');
			factor();
		}
	}
	
	private void factor(){ //factor
		oprnd();
		while (token() == '*'){
			if (token() == '*')
				match('*');
			oprnd();
		}
	}
	
	private void oprnd(){ //oprnd
		if ((token()=='0') || (token() == '1')) //FIRST(integer)
			integer();
		else if ((token()=='X') || (token() == 'Y') || (token() == 'Z')) //FIRST(ident)
			ident();
		else if (token() == '('){
			match('(');
			exprsn();
			match(')');
		}
	}
	
	private void opratr(){ //opratr
		if ((token()=='<') || (token() == '=') || (token() == '>') || (token() == '!') ) //FIRST(opratr)
			match(token());
		else
			error();
	}
	
	private void ident(){ //ident
		letter();
		do
			character();
		while ((token()=='X') || (token() == 'Y') || (token() == 'Z') || (token()=='0') || (token() == '1')); //FIRST(char)
			
	}
	
	private void character(){ //char
		if (token()=='0' || token() == '1') //FIRST(digit)
			digit();
		else if ((token()=='X') || (token() == 'Y') || (token() == 'Z')) //FIRST(letter)
			letter();
	}
	
	private void integer(){ //integer
		while ((token()=='0') || (token() == '1')){ //FIRST(digit)
			digit();
		}
	}
	
	private void letter(){ //letter
		if ((token()=='X') || (token() == 'Y') || (token() == 'Z'))
			match(token());
		else
			error();
	}
	
	private void digit(){ //digit
		if (token()=='0' || token() == '1')
			match(token());
		else
			error();
	}
	
	//////////////////////////
	private void start(){ //credit to Dr.Gordon's code
		program();
		match('$');
		
		if (errorflag == 0)
			System.out.println("Legal.\n");
		else 
			System.out.println("Errors found.\n");
	}
	
	public static void main (String[] args) throws IOException{ //credit to Dr.Gordon's code
		HW1Recognizer rec = new HW1Recognizer();
		Scanner input = new Scanner(System.in);
		System.out.print("\n" + "Enter an expression: ");
		inputString = input.nextLine();
		
		rec.start();
	}
}
	
	
	
	
	
	
	
	
