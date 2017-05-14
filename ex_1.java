package ex_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ex_1 {
	static  int MAX=100;
	/*
	 *			(		)		'		|		*
	 *	(		<		=		<		<		<
	 *	)		O	>		>		>		>
	 *	.		<		>		>		>		<
	 *	|		<		>		<		>		<
	 *	*		<		>		>		>		>
	 */
	static char[][] priority = {{'<','=','<','<','<'},
														{'O','>','>','>','>'},
														{'<','>','>','>','<'},
														{'<','>','<','>','<'},
														{'<','>','<','>','>'}
														};
	public static class NFA{
		char S0;//��̬
		char F;//��̬
		String status;//״̬
		String condition;//״̬ת������
		String[][]  move = new String[MAX][MAX];//״̬ת�ƾ���
	}

	void Change_RtoNFA(String r,NFA nfa){
		Stack character = new Stack();
		Stack operator = new Stack();
		operator.push('#');
		for(int i = 0;i < r.length();i++){
			if(isCharacter(r.charAt(i))){
				NFA nfa_p = tom1(r.charAt(i));
				character.push(nfa_p);
			}
			else{
				if(r.charAt(i-1) == '#')
					operator.push(r.charAt(i));
				else{
					int opt1 = operator_Num(r.charAt(i-1));
					int opt2 = operator_Num(r.charAt(i));
					if(priority[opt1][opt2] == '<'){
						NFA nfa_q = tom1(r.charAt(i));
						character.push(nfa_q);
					}else if(priority[opt1][opt2] == '>'){
						NFA a,b;
						if(opt2 == 5){
							a = (NFA) character.pop();
							b = null;
						}else{
							a = (NFA) character.pop();
							b = (NFA) character.pop();
						}
						char opt = (char) operator.pop();
						nfa = Thomson(opt1,a,b);
						character.push(nfa);
					}else if(priority[opt1][opt2] == '='){
							operator.pop();
					}else{
							System.out.println("����ʽ����");
					}
				}
				
			}
		}
	}
	private NFA tom1(char charAt) {
		// TODO Auto-generated method stub
		return null;
	}
	private NFA Thomson(int opt1, NFA a, NFA b) {
		NFA nfa;
		if(opt1 == 3){
			nfa = tom2(a,b);
		}else if(opt1 == 4){
			nfa = tom3(a,b);
		}else if(opt1 == 5){
			nfa = tom4(a);
		}
		return nfa;	
	}
	private NFA tom3(NFA a, NFA b) {
		// a|b
		NFA nfa = null;
		nfa.S0 = 0;
		nfa.F = tom3_Final(a,b);
		for(int i = 0;i <= (int)nfa.F;i++)
			nfa.status+=i;
		nfa.condition = tom3_Condition(a,b);
		
		
		for(int i = 0;i < b.status.length();i ++){
			for(int j = 0;j < b.condition.length();j++)
			if(b.move[i][j] != null && b.status.charAt(i) != '0'){
				b.move[i][j] += a.status.length() - 2;
			}
		}
		for(int i = 0;i < nfa.status.length();i++){
			for(int j = 0;j < nfa.condition.length();j++){
				if(i == 0){
					for(int k = 0;)
				}
			}
		}
		return null;
	}
	
	private String tom3_Condition(NFA a, NFA b) {
		// ��a��b��״̬ת������ȥ��
		String s = a.condition+b.condition;
		ArrayList   list   =   new   ArrayList();   
		   for   (int   i   =   0;i   <  s.length();i++)   
		   {   
		           if   (!list.contains(s.charAt(i)))   
		           {   
		                       list.add(s.charAt(i));   
		           }   
		   }   
		   String condition = list.toString();
		return condition;
	}
	private char tom3_Final(NFA a, NFA b) {
		// a|b����̬
		char Final;
		if(a.status.length() >= b.status.length()){
			Final = (char) (a.status.length() + b.status.length() - 3);//��ʾ�ϳɺ��NFA״̬����ʱ-2����ʾ��̬���ַ�ʱ-3
		}else{
			Final = (char) (b.status.length() + a.status.length() - 3);
		}
		return Final;
	}
	private int operator_Num(char c) {
		// ��������������ȼ����еĴ�������
		if(c == '(') return 1;
		else if(c == ')') return 2;
		else if(c == '*') return 3;
		else if(c == '|') return 4;
		else if(c == '.') return 5;
		return 0;
	}
	private boolean isCharacter(char c) {
		// �ж��Ƿ�Ϊ�ַ�
		if(c > 'A' && c< 'Z' || c > 'a' && c < 'z')
			return  true;
		return false;
	}
}
