      // Nova classe Statement
      abstract class Statement {
         public abstract String value(Customer customer);
      }

      class TextStatement extends Statement {
         @Override
         public String value(Customer customer) {
            StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");
            Enumeration rentals = customer.getRentals().elements();
            while (rentals.hasMoreElements()) {
               Rental each = (Rental) rentals.nextElement();
               result.append("\t").append(each.getMovie().getTitle()).append("\t")
                    .append(each.getCharge()).append("\n");
            }
            result.append("Amount owed is ").append(customer.getTotalCharge()).append("\n");
            result.append("You earned ").append(customer.getTotalFrequentRenterPoints())
                 .append(" frequent renter points");
            return result.toString();
         }
      }

      class HtmlStatement extends Statement {
         @Override
         public String value(Customer customer) {
            Enumeration rentals = customer.getRentals().elements();
            String result = "<H1>Rentals for <EM>" + customer.getName() + "</EM></H1><P>\n";
            while (rentals.hasMoreElements()) {
               Rental each = (Rental) rentals.nextElement();
               result += each.getMovie().getTitle() + ": " +
                       String.valueOf(each.getCharge()) + "<BR>\n";
            }
            // add footer lines
            result +=  "<P>You owe <EM>" + String.valueOf(customer.getTotalCharge()) + "</EM><P>\n";
            result += "On this rental you earned <EM>" +
                  String.valueOf(customer.getTotalFrequentRenterPoints()) +
                  "</EM> frequent renter points<P>";
            return result;
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
