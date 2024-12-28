package dompoo.transaction.order;

public class NotEnoughMoney extends Exception {
	
	public NotEnoughMoney(String message) {
		super(message);
	}
}
