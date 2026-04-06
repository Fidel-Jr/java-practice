public class practice {
    public static void main(String[] args) {
        int numbers[][] = {{100,200,300,400}, {10,20,30,40}, {1,2,3,4}, {5,15,30,90}};
	  	  
	  	  for(int i=0;i<numbers.length;i++) {
			  
			  for(int j=0;j<numbers[i].length;j++) {
				  System.out.print(numbers[i][j] + " ");
			  }
			  System.out.println();
		  }
	  	  System.out.println();
	  	  for(int i=0;i<numbers.length;i++) {
			  
			  for(int j=0;j<numbers[i].length;j++) {
				  if(numbers[i][j]==15) {
					  System.out.println("Number 15 is found at indices [" + i + "][" + j + "]");
					  numbers[i][j] = 99;
					  System.out.println("15 updated to 99");
					  break;
				  }
			  }
		  }
	  	  System.out.println("\nThe Updated Array:");
	  	  for(int i=0;i<numbers.length;i++) {
			  
			  for(int j=0;j<numbers[i].length;j++) {
				  System.out.print(numbers[i][j] + " ");
			  }
			  System.out.println();
		  }
		
    }
}