      // Nova classe Statement

      // Template Method
      abstract class Statement {
         // Método template principal
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

         // Métodos abstratos para especialização
         protected abstract String headerString(Customer customer);
         protected abstract String eachRentalString(Rental each);
         protected abstract String footerString(Customer customer);
      }


      // Implementação para texto
      class TextStatement extends Statement {
         @Override
         protected String headerString(Customer customer) {
            // Cabeçalho do extrato em texto
            return "Rental Record for " + customer.getName() + "\n";
         }
         @Override
         protected String eachRentalString(Rental each) {
            // Linha de cada aluguel em texto
            return "\t" + each.getMovie().getTitle() + "\t" + each.getCharge() + "\n";
         }
         @Override
         protected String footerString(Customer customer) {
            // Rodapé do extrato em texto
            return "Amount owed is " + customer.getTotalCharge() + "\n" +
                   "You earned " + customer.getTotalFrequentRenterPoints() + " frequent renter points";
         }
      }


      // Implementação para HTML
      class HtmlStatement extends Statement {
         @Override
         protected String headerString(Customer customer) {
            // Cabeçalho do extrato em HTML
            return "<H1>Rentals for <EM>" + customer.getName() + "</EM></H1><P>\n";
         }
         @Override
         protected String eachRentalString(Rental each) {
            // Linha de cada aluguel em HTML
            return each.getMovie().getTitle() + ": " + each.getCharge() + "<BR>\n";
         }
         @Override
         protected String footerString(Customer customer) {
            // Rodapé do extrato em HTML
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
