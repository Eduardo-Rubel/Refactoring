      // Nova classe Statement
      abstract class Statement {
         public String value(Customer customer) {
            StringBuilder result = new StringBuilder(headerString(customer));
            Enumeration rentals = customer.getRentals().elements();
            while (rentals.hasMoreElements()) {
               Rental each = (Rental) rentals.nextElement();
               result.append(eachRentalString(each));
            }
            result.append(footerString(customer));
            return result.toString();
         }

         protected abstract String headerString(Customer customer);
         protected abstract String eachRentalString(Rental each);
         protected abstract String footerString(Customer customer);
      }

      class TextStatement extends Statement {
         @Override
         protected String headerString(Customer customer) {
            return "Rental Record for " + customer.getName() + "\n";
         }
         @Override
         protected String eachRentalString(Rental each) {
            return "\t" + each.getMovie().getTitle() + "\t" + each.getCharge() + "\n";
         }
         @Override
         protected String footerString(Customer customer) {
            return "Amount owed is " + customer.getTotalCharge() + "\n" +
                   "You earned " + customer.getTotalFrequentRenterPoints() + " frequent renter points";
         }
      }

      class HtmlStatement extends Statement {
         @Override
         protected String headerString(Customer customer) {
            return "<H1>Rentals for <EM>" + customer.getName() + "</EM></H1><P>\n";
         }
         @Override
         protected String eachRentalString(Rental each) {
            return each.getMovie().getTitle() + ": " + each.getCharge() + "<BR>\n";
         }
         @Override
         protected String footerString(Customer customer) {
            return "<P>You owe <EM>" + customer.getTotalCharge() + "</EM><P>\n" +
                   "On this rental you earned <EM>" + customer.getTotalFrequentRenterPoints() +
                   "</EM> frequent renter points<P>";
         }
      }
   public String htmlStatement() {
      return new HtmlStatement().value(this);
   }
   public double getTotalCharge() {
      double total = 0;
      Enumeration rentals = _rentals.elements();
      while (rentals.hasMoreElements()) {
         Rental each = (Rental) rentals.nextElement();
         total += each.getCharge();
      }
      return total;
   }

   public int getTotalFrequentRenterPoints() {
      int total = 0;
      Enumeration rentals = _rentals.elements();
      while (rentals.hasMoreElements()) {
         Rental each = (Rental) rentals.nextElement();
         total += each.getFrequentRenterPoints();
      }
      return total;
   }
import java.util.Enumeration;
import java.util.Vector;

public class Customer {

   private String _name;
   private Vector _rentals = new Vector();

   public Customer(String name){
      _name = name;
   }

   public void addRental(Rental arg) {
      _rentals.addElement(arg);
   }

   public String getName (){
      return _name;
   }

   public String statement() {
      return new TextStatement().value(this);
   }
   public Vector getRentals() {
      return _rentals;
   }
}
