package examples;

public class HelloWorld {

	private static String[] myArray = { "one", "two", "three" };
	
	HelloWorld(String[] myArray) {
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("Hello, world");
		
		String[] blarg = { "one", "two", "three"  };
		
		HelloWorld hw = new HelloWorld(blarg);
		
		hw.setMyArray(blarg);
		
		hw.test();
		
		

	}
	
	public static void test() {
		
		System.out.println("myArray.length: " +myArray.length);
		
		for( int i = 0; i < myArray.length; i++ ) {
			
			System.out.println(myArray[i]);
			
		}
		
	}

	public static String[] getMyArray() {
		return myArray;
	}

	public static void setMyArray(String[] myArray) {
		HelloWorld.myArray = myArray;
	}
	
	

}
