
public class Seat {

	private String seatNumber;
	private String seatClass;
	private boolean isWindow;
	private boolean isAisle;
	private boolean hasTable;
	private double price;
	private String eMail;

	public Seat(String seatNumber, String seatClass, boolean isWindow, boolean isAisle, boolean hasTable, double price,
			String eMail) {
		this.seatNumber = seatNumber;
		this.seatClass = seatClass;
		this.isWindow = isWindow;
		this.isAisle = isAisle;
		this.hasTable = hasTable;
		this.price = price;
		this.eMail = eMail;
	}

	@Override
	public String toString() {
		String result = seatNumber + " | " + seatClass + " | "
				+ (isWindow == true ? "Window seat	    " : "Not a window seat") + " | "
				+ (isAisle == true ? "Aisle seat	" : "Not an aisle seat") + " | "
				+ (hasTable == true ? "Seat with table   " + " | " : "Seat with no table" + " | ");
		return result;
	}

	public String toSave() {
		String result = seatNumber + " " + seatClass + " " + isWindow + " " + isAisle + " " + hasTable + " " + price
				+ " " + eMail;
		return result;
	}
	
	public void bookSeat(String eMail) throws Exception {
		if (!isReserved()) {
			this.eMail = eMail;
		} else {
			throw new Exception("\nThe seat you are trying to book is already reserved");
		}

	}

	public void cancel() {
		if (this.eMail.equals(eMail)) {
			eMail = "";
		}

	}

	public boolean isReserved() {
		if (eMail.equals("")) {
			return false;
		} else {
			return true;

		}

	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public double getPrice() {
		return price;
	}
	
	public boolean getIsWindow() {
		return isWindow;
	}


	public boolean getIsAisle() {
		return isAisle;
	}

	public boolean getHasTable() {
		return hasTable;
	}
	
	
	public String getSeatType() {
		return seatClass;
	}

}
