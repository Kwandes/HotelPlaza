import java.util.*;
import java.io.*;

public class GuestUI extends CLI
{   
   private int size = 70;
   private String[] error = { addText("Too long", size), addText("Too short", size), 
                              addText("Cannot contain spaces", size), addText("Only numbers", size), 
                              addText("Only letters", size), addText("Invalid answer", size), 
                              addText("Cannot contain numbers", size), addText("The password didnt match, Try again", size)};
   private Scanner in = new Scanner(System.in);
   private Scanner in2 = new Scanner(System.in); //bug issue with the scanners, had to make an extra.
   private Guest guest;
   private MainFrame mf;
   private int IDCounter;
   
   public GuestUI(Guest user, String title, MainFrame mfRef) throws Exception
   {
      this.title = title;
      this.screenNumber = 2;
      this.loggedUser = user.getLastName();
      this.userAccessLevel = 0; // Cannot be more than 0 for security reasons
      this.seperator = print(size); 
      this.running = true;
      this.guest = user;
      this.mf = mfRef;
      this.IDCounter = 0;
   }
   
   public int guestMenu() 
   {
      System.out.println(" > 1 Book a Room ");
      System.out.println(" > 2 Check Bookings ");
      System.out.println(" > 3 Extend Booking ");
      System.out.println(" > 4 Change Info ");
      System.out.println(" > 5 Log out ");
      
      int choice = Integer.parseInt(check("\tPlease select 1 - 5 : ", 0, 6));
      return choice;
   }
   
   public void display()
   {
      while(running)
      {
         int choice = guestMenu();
         switch(screenNumber)
         {
            // actual display
            // listen to inputs, show different screens depending on input
            // each "screen" has a specific screen Number
            // screens choosing example below
            case 1:
               mainMenu();
               break;
            case 2:
               bookRoom();
               break;
            case 3: 
               seeBookings(this.guest.getID(), 1);
               break;
            case 4:
               seeBookings(this.guest.getID(), 2);
               break;
            case 5:
               this.guest = changeInfo(this.guest);
               break;
            case 99:
               exit();
         }
      }  
   }
   
   public void bookRoom() 
   {
      print();
      printText("- BOOK ROOM -", size);
      print();
   }
   
   public void seeBookings(String guestID, int extendOrSee)
   {
      if ( extendOrSee == 2 ) //So it doesnt double print the line when you extend booking
      {
         print();
      }
      printText("- BOOKINGS -", size);
      print();
      
      int numberOfBookings = 0;
      ArrayList<String> bookingID = new ArrayList<>();
      ArrayList<Booking> bookings = mf.getBookingList();
      
      for ( Booking book : bookings )  // smart. Stack overflow?
      {
         String booking = Integer.toString(book.getBookingID());
         if ( booking.contains( guestID ) )  //checking if the booking matches the guestID
         {
            bookingID.add( booking );  //adds booking that matches to new arraylist
         }
      }
      if ( bookingID.isEmpty() ) //if no bookings are found
      {
         System.out.print("Sorry, there are no active bookings");
      }
      else 
      {
         System.out.println("Here are your bookings : ");
         for ( String ID : bookingID )
         {
            System.out.println(ID);
            numberOfBookings++;
         }
      }
      if ( extendOrSee == 1 )
      {
         in.next(); //just so it doenst skipp back to menu before you get to see bookings
         display();
      } 
      else if ( extendOrSee == 2 )
      {
         System.out.println("Now select a booking that you want to extend");
         int choice = Integer.parseInt(check("\tPlease select 1 - " + numberOfBookings, 0, numberOfBookings + 1));
      }
   }
   
   public void extendBooking (int extendBooking)
   {
      print();
      printText("- EXTEND BOOKING -", size);
      print();      
   }
   
   public Guest changeInfo (Guest guest) 
   {
      String firstName = guest.getFirstName();
      String lastName = guest.getLastName();
      String cpr = guest.getCpr();
      String[] address = guest.getAddress();
      String phoneNr = guest.getPhoneNumber();
      String password = guest.getPassword();
      String pass1;
      String pass2;
      
      print();
      printText("- CHANGE INFO -", size);
      print();
      
      System.out.println("\n\t > 1 First Name " +
                         "\n\t > 2 Last Name " + 
                         "\n\t > 3 Cpr " +
                         "\n\t > 4 Address " +
                         "\n\t > 5 Phonenumber " +
                         "\n\t > 6 Password ");
      
      int choice = Integer.parseInt(check("\tPlease select 1 - 6 : ", 0, 7));
      
      switch ( choice ) 
      {
         case 1: 
            System.out.print("\tFirst name : ");
            firstName = checkName(0);
            print();
            break;
         case 2:
            System.out.print("\tLast name  : ");
            lastName = checkName(3);
            print();
            break;
         case 3:
            System.out.print("\tMembers cpr Nr : ");
            cpr = checkCpr();
            print();
            break;
         case 4:
            System.out.print("\tAddress - Street name : ");
            String streetName = checkAddress();
            print();
            
            System.out.print("\tAddress - Street number : ");
            String streetNumber = check("\tPlease write Street number (ex. 41) : ", 0, 1000);
            streetName += streetNumber;
            address[0] = streetName;
            print();
            
            System.out.print("\tAddress - City : ");
            String city = checkName(9);
            address[1] = city;
            print();
            
            System.out.print("\tAddress - Postcode : ");
            String postCode = check("\tPlease write Postcode (ex. 2200) : ", 999, 10000);
            address[2] = postCode;
            print();
            break;
         case 5:
            System.out.print("\tPhonenumber : ");
            phoneNr = check("\tPlease write your Phonenumber : ", 999999, 100000000);
            print();
            break;
         case 6:
            do {
               System.out.print("\tCreate password : ");
               pass1 = in.next();
               System.out.print("\tVerify password : ");
               pass2 = in.next();
               if ( !pass1.equals(pass2) ) 
               {
                  errorMessage(7);
               }
            } while ( !pass1.equals(pass2) );
            print();
            password = pass1;
      }
      
      Guest newUser = new Guest ( firstName, lastName, cpr, address, phoneNr, password, 0); // Get ID via guest.getID(). The ID shouldn't change when changing info
      // mf.replaceGuest(guest.getID(), newUser); in order to save
      return newUser;
   }
   
   public void registerGuest () 
   {
      String firstName;
      String lastName;
      String cpr;
      String[] address = new String[3];
      String phoneNr;
      String password;
      String pass1;
      String pass2;
      this.IDCounter++; // Don't increment before the user creation is succesful
      
      print();
      printText("- REGISTER GUEST -", size);
      print();
      
      System.out.print("\tFirst name : ");
      firstName = checkName(0);
      print();
      
      System.out.print("\tLast name  : ");
      lastName = checkName(3);
      print();
      
      System.out.print("\tMembers cpr Nr : ");
      cpr = checkCpr();
      print();
      
      System.out.print("\tAddress - Street name : ");
      String streetName = checkAddress();
      print();
      
      System.out.print("\tAddress - Street number : ");
      String streetNumber = check("\tPlease write Street number (ex. 41) : ", 0, 999);
      streetName += streetNumber;
      address[0] = streetName;
      print();
      
      System.out.print("\tAddress - City : ");
      String city = checkName(9);
      address[1] = city;
      print();
      
      System.out.print("\tAddress - Postcode : ");
      String postCode = check("\tPlease write Postcode (ex. 2200) : ", 999, 9999);
      address[2] = postCode;
      print();
      
      System.out.print("\tPhonenumber : ");
      phoneNr = check("\tPlease write your Phonenumber : ", 1000000, 99999999);
      print();
                 
      do {
         System.out.print("\tCreate password : ");
         pass1 = in.next();
         System.out.print("\tVerify password : ");
         pass2 = in.next();
         if ( !pass1.equals(pass2) ) 
         {
            errorMessage(7);
         }
      } while ( !pass1.equals(pass2) );
      print();
      password = pass1;
      
      int guestID = mf.generateGuestID(); // this is how you will get a new Guest ID
      Guest Teo = new Guest (firstName, lastName, cpr, address, phoneNr, password, this.IDCounter);   // Double check the contructors, right now IDCounter is passed for a guestDays parameter in Guest
      System.out.println("\n" + Teo.toString());
   }
   
   public String check (String question, int min, int max)
   {
      String input = null;
      boolean isValid = false;
      int a = 0;
      
      while ( !isValid ) 
      {
         isValid = true;
         if ( a == 1 )
         {
            System.out.print("\tPlease write a number " + (min + 1) + " - " + max + " : " +
                             "\n\t( 0 for returning to menu ) ");
         }
         else 
         { 
            System.out.println("\n\t( 0 for returning to menu )");
            a++; 
         }
         if ( in.hasNextInt() ) 
         {
            input = in.next();
            if ( Integer.parseInt(input) == 0 ) 
            {
               display();
            }
            else if ( Integer.parseInt(input) > min && Integer.parseInt(input) < max ) 
            {
               isValid = true; 
            } 
            else 
            {
               errorMessage(5);
               isValid = false;
            }
         } 
         else 
         {
            errorMessage(5);
            isValid = false;
            in.next();
         }
      }
      return input;
   }
   
   public String checkAddress () 
   {
      String input = "";
      boolean isValid = false;
      int a = 0; 
      
      while ( !isValid ) 
      {
         isValid = true;
         if ( a == 1 ) 
         {
            System.out.print("\tPlease write street name : ");
         }
         else { a++; }
         
         input = in.nextLine();
         input = input.trim();
         
         if ( input.length() < 5 )
         {
            errorMessage(1);
            input = "";
         }
         
         for ( int i = 0; i < input.length(); i++ ) 
         {
            if ( Character.isDigit(input.charAt(i)) )
            {
               errorMessage(6);
               input = "";
            }
         }
         if ( input.length() < 3 ) //checking 
         {
            isValid = false;
         }
      }
      
      String inputWith = "";
      inputWith += Character.toUpperCase(input.charAt(0));  //This is just to make the first letter
      for ( int i = 1; i < input.length(); i++ )            //in the names UpperCase.
      {  
         inputWith += Character.toLowerCase(input.charAt(i));
      }
      inputWith += " ";
      
      return inputWith;
   }
   
   public String checkName (int commentNumber) 
   {
      String input = "";
      boolean isValid = false;
      
      while ( !isValid ) 
      {
         isValid = true;
         
         switch ( commentNumber )
         {
            case 0:
               commentNumber++;
               break;
            case 1:
               System.out.print("\tPlease write your first name : ");
               break;
            case 2:
               System.out.print("\tPlease write your last name : ");
               break;
            case 3:
               commentNumber--;
               break;
            case 7:  
               System.out.print("\tPlease write street name : ");
               break;
            case 8:
               commentNumber--;
               break;
            case 9:
               commentNumber++;
               break;
            case 10:
               System.out.print("\tPlease write City name : ");
         }
         
         if ( commentNumber == 9 || commentNumber == 10 )  //if this is removed there will be an issue with 
         {                                                 //getting the city name.
            input = in2.nextLine();                        //i thik java created a bug that could not be fixed.
         }                                                 //so i made a completely new scanner to be sure it 
         else                                              //hadnt obtained a value already.
         {
            input = in.nextLine();
         }
         input = input.trim();
         
         System.out.println(input);
         
         if ( input.contains(" ") )
         {
            input = "";
            errorMessage(2);
         } 
         else if ( input.length() > 11 ) 
         {
            errorMessage(0);
            input = "";
         }
         else if ( input.length() < 3 )
         {
            errorMessage(1);
            input = "";
         }
                  
         for ( int i = 0; i < input.length(); i++ )
         {
            if ( Character.isDigit(input.charAt(i)) )
            {
               errorMessage(4);
               input = "";
            }
         }
         if ( input.length() < 3 ) 
         {
            isValid = false;
         }
      }
      
      String inputWith = "";
      inputWith += Character.toUpperCase(input.charAt(0));  //This is just to make the first letter
                                                            //in the names UpperCase and the rest are
      for ( int i = 1; i < input.length(); i++ )            //LowerCase at the same time.
      {
         inputWith += Character.toLowerCase(input.charAt(i));
      }
      
      return inputWith;
   }
   
   public String checkCpr () 
   {
      String input = "";
      boolean isValid = false;
      int a = 0;
      
      while ( !isValid ) 
      {
         isValid = true;
         if ( a == 1 ) 
         {
            System.out.print("\tPlease write your CPR (ex. 2208972041) : ");
         }
         else { a++; } 
         
         input = in.nextLine();
         input = input.trim();
         
         if ( input.contains(" ") )
         {
            input = "";
            errorMessage(2);
         } 
         else if ( input.length() == 10 )
         {
            for ( int i = 0; i < 10; i++ ) 
            {
               if ( !Character.isDigit(input.charAt(i)) )
               {
                  errorMessage(3);
                  input = "";
                  break;
               }
            }
         } 
         else if ( input.length() < 10 )
         {
            errorMessage(1);
            input = "";
         }
         else 
         {
            errorMessage(0);
            input = "";
         }
         
         if ( input.length() < 3 ) 
         {
            isValid = false;
         }
      }
      return input;
   }
   
   public void printText (String text, int numberOfSpaces) 
   {
      int out = (numberOfSpaces - text.length()) / 2;
      for(int i = 0; i < out; i++ ) {
         System.out.print(" ");
      }
      System.out.println(text);
   }
   
   public String addText (String text, int numberOfSpaces) 
   {
      String line = "";
      int out = (numberOfSpaces - text.length()) / 2;
      for(int i = 0; i < out; i++ ) {
         line += " ";
      }
      line += text;
      return line;
   }
   
   public String print (int numberOfChar) 
   {
      String fullString = "";
      for(int i = 0; i < numberOfChar; i++ ) {
         fullString += "-";
      }
      return fullString;
   }
   
   public void print () 
   {
      System.out.println(print(size));
   }
   
   public void errorMessage (int message) 
   {
      print();
      System.out.println(error[message]);
      print();
   }
   
   public void mainMenu()
   {
      this.screenNumber = 1;
   }
   
   public void exit()
   {
      this.running = false;
   }
}