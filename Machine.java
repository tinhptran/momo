/*
	Name: Tran Phuong Tinh
	Mobile: 0378590684
	Email: tinh.tran.cse@hcmut.edu.vn
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

// Coin-Operated Soda Machine
public class Machine {

	private class Product {
		private String name;
		private int value;

		public Product(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public int getValue() {
			return this.value;
		}
	}

	private final HashSet<Integer> acceptedNotes = new HashSet<Integer>(
			Arrays.asList(10000, 20000, 50000, 100000, 200000));
	private HashMap<Integer, Product> products;
	private int nNotes;
	private int sumNotes;
	private int remainingChange;
	private double baseFreeRate;
	private double freeRate;
	private int budget;
	private int choice;
	private int prevChoice;
	private int nConsecutive;
	private int nSold;

	public Machine() {
		this.products = new HashMap<Integer, Product>();
		this.nNotes = 0;
		this.sumNotes = 0;
		this.remainingChange = 0;
		this.baseFreeRate = 0.0;
		this.freeRate = 0.0;
		this.budget = 50000;
		this.choice = 0;
		this.prevChoice = 0;
		this.nConsecutive = 0; // count the number of consecutive purchases of the same product
		this.nSold = 0; // count the number of sold products
	}

	public void addProduct(Product product) {
		if (this.products == null) {
			this.products = new HashMap<Integer, Product>();
		}
		this.products.put(this.products.size() + 1, product);
	}

	public HashMap<Integer, Product> getProducts() {
		return this.products;
	}

	public int getNNotes() {
		return this.nNotes;
	}

	public void setNNotes(int nNotes) {
		this.nNotes = nNotes;
	}

	public int getSumNotes() {
		return this.sumNotes;
	}

	public void setSumNotes(int sumNotes) {
		this.sumNotes = sumNotes;
	}

	public int getRemainingChange() {
		return this.remainingChange;
	}

	public void setRemainingChange(int remainingChange) {
		this.remainingChange = remainingChange;
	}

	public double getBaseFreeRate() {
		return this.baseFreeRate;
	}

	public void setBaseFreeRate(double baseFreeRate) {
		this.baseFreeRate = baseFreeRate;
	}

	public double getFreeRate() {
		return this.freeRate;
	}

	public void setFreeRate(double freeRate) {
		this.freeRate = freeRate;
	}

	public int getBudget() {
		return this.budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getChoice() {
		return this.choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public int getPrevChoice() {
		return this.prevChoice;
	}

	public void setPrevChoice(int prevChoice) {
		this.prevChoice = prevChoice;
	}

	public int getNConsecutive() {
		return this.nConsecutive;
	}

	public void setNConsecutive(int nConsecutive) {
		this.nConsecutive = nConsecutive;
	}

	public int getNSold() {
		return this.nSold;
	}

	public void setNSold(int nSold) {
		this.nSold = nSold;
	}

	public void resetNotes() {
		this.nNotes = 0;
		this.sumNotes = 0;
		this.remainingChange = 0;
	}

	public void printStep1() {
		System.out.println("\n=============================STEP 1=============================");
		System.out.println("Enter note value (10000, 20000, 50000, 100000, 200000 only)");
		System.out.println("Enter 'ok' to go to Product Selection step");
		System.out.println("Enter 'cancel' to cancel the request and receive a refund if any");
		System.out.println("================================================================");
	}

	public void printWrongNoteValue() {
		System.out.println("\n================================================================");
		System.out.println("Wrong note value!");
		System.out.println("================================================================");
	}

	public void printStep2() {
		System.out.println("\n===========================STEP 2===============================");
		System.out.println("Choose the number corresponding to the product");
		System.out.println("1. Coke");
		System.out.println("2. Pepsi");
		System.out.println("3. Soda");
		System.out.println("Enter 'cancel' to cancel the request and recieve a refund if any");
		System.out.println("================================================================");
	}

	public void printCancel() {
		System.out.println("\n================================================================");
		System.out.println("Request canceled.");
		if (sumNotes > 0) {
			System.out.println("Your refund: " + sumNotes);
		} else {
			System.out.println("No refund.");
		}
		System.out.println("================================================================");
	}

	public void printNotEnoughMoney() {
		System.out.println("\n================================================================");
		System.out.println("You have not paid enough money!");
		System.out.println("================================================================");
	}

	public void printTransaction(Product chosenProduct) {
		System.out.println("\n================================================================");
		System.out.println("Your " + chosenProduct.getName() + " is ready.");
		remainingChange = sumNotes - chosenProduct.getValue();
		if (remainingChange > 0) {
			System.out.println("Your remaining change: " + remainingChange);
		} else {
			System.out.println("No remaining change.");
		}
		System.out.println("================================================================");
	}

	public void printFreeProduct(Product chosenProduct) {
		System.out.println("\n================================================================");
		System.out.println("Congratulations. You have received the " + chosenProduct.getName() + " for free!");
		if (sumNotes > 0) {
			System.out.println("Your refund: " + sumNotes);
		} else {
			System.out.println("No refund.");
		}
		System.out.println("================================================================");
	}

	public void resetDay() {
		freeRate = 0.0;
		budget = 50000;
		choice = 0;
		prevChoice = 0;
		nConsecutive = 0;
		System.out.println("\n================================================================");
		System.out.println("A new day has come.");
		System.out.println("================================================================");
	}

	public void checkEndDay() {
		// assume that 10 products for a day
		if (nSold % 10 == 0) {
			if (budget > 0) {
				baseFreeRate = 0.5;
			} else {
				baseFreeRate = 0.0;
			}

			// reset for a new day
			resetDay();
		}
	}

	public static void main(String[] args) {
		Machine machine = new Machine();
		machine.addProduct(machine.new Product("Coke", 10000));
		machine.addProduct(machine.new Product("Pepsi", 10000));
		machine.addProduct(machine.new Product("Soda", 20000));

		HashMap<Integer, Product> products = machine.getProducts();
		Scanner sc = new Scanner(System.in);
		while (machine.getNSold() < 100) {
			machine.printStep1();
			Boolean isCanceled = false;

			while (machine.getSumNotes() < 500000) {
				System.out.println("Total values you have paid: " + machine.getSumNotes());
				System.out.print("Note value #" + (machine.getNNotes() + 1) + ": ");
				String tmpNote = "";
				int note = 0;
				try {
					tmpNote = sc.nextLine();
					note = Integer.parseInt(tmpNote);
					if (machine.acceptedNotes.contains(note)) {
						if (machine.getSumNotes() + note > 500000) {
							System.out.println("Maximum 500000.");
							System.out.println("Please get back your " + note + " and enter another value.");
						} else {
							machine.setSumNotes(machine.getSumNotes() + note);
							machine.setNNotes(machine.getNNotes() + 1);
						}
					} else {
						machine.printWrongNoteValue();
						machine.printStep1();
					}
				} catch (NumberFormatException e) {
					if (tmpNote.equals("ok")) {
						break;
					} else if (tmpNote.equals("cancel")) {
						machine.printCancel();
						machine.resetNotes();
						isCanceled = true;
						break;
					} else {
						machine.printWrongNoteValue();
						machine.printStep1();
					}
				}
			}

			if (isCanceled) {
				continue;
			}

			machine.setChoice(0);
			while (machine.getChoice() < 1 || machine.getChoice() > 3) {
				machine.printStep2();
				System.out.println("Total values you have paid: " + machine.getSumNotes());
				System.out.print("Your choice: ");
				String tmpChoice = sc.nextLine();
				try {
					machine.setChoice(Integer.parseInt(tmpChoice));
				} catch (NumberFormatException e) {
					if (tmpChoice.equals("cancel")) {
						machine.printCancel();
						machine.resetNotes();
						isCanceled = true;
						break;
					}
				}
			}

			if (isCanceled) {
				continue;
			}

			Product chosenProduct = products.get(machine.getChoice());

			if (machine.getFreeRate() > 0.0) {
				if (machine.getBudget() >= chosenProduct.value) {
					Random random = new Random();
					if (random.nextInt(100) < (int) (machine.getFreeRate() * 100)) {
						machine.printFreeProduct(chosenProduct);
						machine.resetNotes();
						machine.setFreeRate(0.0);
						machine.setBudget(machine.getBudget() - chosenProduct.value);
						machine.setNSold(machine.getNSold() + 1);
						machine.setPrevChoice(0);
						machine.setNConsecutive(0);
						machine.checkEndDay();
						continue;
					}
				}
			}

			if (chosenProduct.getValue() > machine.getSumNotes()) {
				machine.printNotEnoughMoney();
				continue;
			} else {
				machine.printTransaction(chosenProduct);
				machine.resetNotes();
				if (machine.getChoice() == machine.getPrevChoice()) {
					machine.setNConsecutive(machine.getNConsecutive() + 1);
					if (machine.getNConsecutive() == 3) {
						machine.setFreeRate(machine.getBaseFreeRate() + 0.1);
					}
				} else {
					machine.setPrevChoice(machine.getChoice());
					machine.setNConsecutive(1);
					if (machine.getFreeRate() > 0.0) {
						machine.setFreeRate(0.0);
					}
				}
				machine.setNSold(machine.getNSold() + 1);
				machine.checkEndDay();
			}
		}

		System.out.println("Sold out! Please come back again later.");
		sc.close();
	}

}