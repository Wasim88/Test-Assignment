package EsPoc.EarlySalary.BrokenLink;

public class testingForPractice {
	
	int m1;
	int m2;
	static String name="Wasim";
	
	static void change(){
		String name="Shruti";
		System.out.println("Name has been changed "+ name);
	}
	
	   testingForPractice(int m1,int m2) {
		System.out.println(m1+" "+m2+" "+name);
		
	}
	public static void main(String[] args) {
		testingForPractice.change();

		testingForPractice obj1= new testingForPractice(100,200);
		System.out.println(obj1);
		
		// TODO Auto-generated method stub

	}

}
