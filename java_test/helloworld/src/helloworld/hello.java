package helloworld;

class Box{
	int height;
	int width;
	
	int get_tiji() {
		return height*width;
	}
	
	void printer(int i) {
		int result = i*height;
		System.out.println(result);
	}
	
}


class tester{
	int param_one;
	int param_two;
	String param_three;
	boolean param_four = false;
	
	//构造函数
	public tester(int i, int j, String a, boolean test) {
		param_one = i;
		param_two = j;
		param_three = a;
		
		if(test){
			param_four = test;
		}
	}
	
}

public class hello {
	public static void main(String[] args) {
		System.out.println("test");
		Box newbox = new Box();
		newbox.width=100;
		newbox.height=2;
		int a=newbox.get_tiji();
		System.out.println(a);
		newbox.printer(1000);
		//
		//
		tester newtester = new tester(1,2,"a",true);
		System.out.println(newtester.param_one);
		System.out.println(newtester.param_two);
		System.out.println(newtester.param_three);
		System.out.println(newtester.param_four);
		
	}

}

