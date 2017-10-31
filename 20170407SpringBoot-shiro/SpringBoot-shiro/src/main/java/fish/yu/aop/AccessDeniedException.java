package fish.yu.aop;

public class AccessDeniedException extends RuntimeException {
	
	
	 public AccessDeniedException(String message){
	        super(message); //这句话的意思是调用AccessDeniedException的有参构造方法。
	 } 
	
}
