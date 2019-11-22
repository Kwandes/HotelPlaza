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
   private ArrayList<Booking> bookings; 
   private static ArrayList<Room> roomList;
   private Guest guest;
   private MainFrame mf;
   private int IDCounter;
   
   public GuestUI(Guest user, String title, MainFrame mfRef) throws Exception
   {
      this.title = title;
      this.screenNumber = 1;
      this.loggedUser = user.getLastName();
      this.userAccessLevel = 0; // Cannot be more than 0 for security reasons
      this.seperator = print(size); 
      this.running = true;
      this.guest = user;
      this.mf = mfRef;
      this.IDCounter = 0;
   }
   
   public void mainMenu() 
   {
      System.out.println(" > 1 Check Bookings ");
      System.out.println(" > 2 Extend Booking ");
      System.out.println(" > 3 Change Info ");
      System.out.println(" > 4 Log out ");
      
      System.out.println("Please select one of the options");
      screenNumber = 1 + Integer.parseInt(check("\tPlease select ", 0, 5));
   }
   
   public void display()
   {
      while(running)
      {         
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
               seeBookings(this.guest.getID(), 1); //See Booking
               break;
            case 3:
               seeBookings(this.guest.getID(), 2); //Extend Booking
               break;
            case 4:
               this.guest = changeInfo(this.guest);
               mf.replaceGuest(guest.getID(), guest);
               break;
            case 5:
               exit();
         }
      }  
   }
   
   public void seeBookings(String guestID, int extendOrSee)
   {
      print();
      printText("- BOOKINGS -", size);
      print();
      
      int numberOfBookings = 0;
      ArrayList<Integer> bookingID = new ArrayList<>();
      roomList = mf.getRoomList();
      bookings = mf.getBookingList();//static array of Bookings are in place
      
      for ( Booking book : bookings )  // smart. Stack overflow?
      {
         int booking = book.getBookingID();
         if ( Integer.toString(booking).contains( guestID ) )  //checking if the booking matches the guestID
         {
            bookingID.add(booking);  //adds booking that matches to new arraylist
         }
      }
      if ( bookingID.isEmpty() ) //if no bookings are found
      {
         System.out.print("Sorry, there are no active bookings");
         extendOrSee = 1;
      }
      else 
      {
         System.out.println("Here are your bookings : ");
         for ( int ID : bookingID )
         {
            numberOfBookings++;
            System.out.println("\t> Booking nr " + numberOfBookings + "\n" +
                                bookings.get(findBooking(bookingID.get(ID))).toString()); //Prints booking info
         }
      }
      if ( extendOrSee == 1 )
      {
         screenNumber = 1;
         in2.nextLine(); //just so it doenst skipp back to menu before you get to see bookings
      } 
      else if ( extendOrSee == 2 )
      {
         System.out.println("Now select a booking that you want to extend : ");
         int choice = Integer.parseInt(check("\tPlease select 1 - " + numberOfBookings, 0, numberOfBookings + 1));
         int bookingChoice = findBooking(bookingID.get(choice));
         Booking chosenBooking = bookings.get(bookingChoice);
         int roomID = chosenBooking.getRoomID();
         
         System.out.println("\tYou chose booking nr " + choice + "\n" +
                            chosenBooking.toString() + "\n");
         
         System.out.println("\tHow many Extra days would you like? ");
         int extendDays = Integer.parseInt(check("\tPlease select ", 0, 32));
         int startDate = chosenBooking.getStartDate();
         int endDate = chosenBooking.getEndDate() + extendDays;
         
         boolean possible = isBookable(roomID, startDate, endDate);
         
         if ( possible ) 
         {
            System.out.println("Great, booking is possible." +
                               "\nNew booking info : \n" + chosenBooking.toString() );
            
            System.out.println("Do you agree with the new booking?");
            int yesNo = Integer.parseInt(check("Please write (1-yes, 2-no) ", 0, 3));
            if ( yesNo == 1 )
            {
               chosenBooking.setEndDate(endDate);
               mf.replaceBooking(bookingChoice, chosenBooking);
            }
            else 
            {
               possible = false;
            }
         }
         
         if ( !possible )
         {
            System.out.println("Sorry, the room is not available for the extended amount of time." +
                               "\n\t> 1 Back to Menu" +
                               "\n\t> 2 Select new Date");
            int goAgain = Integer.parseInt(check ("Please select 1 - 2 : ", 0, 3));
            if ( goAgain == 1 ) 
            {
               mainMenu();
            }
            else 
            {
               screenNumber = 3;
            }
         }
      }
   }
   
   // Checks if a room is available for a given period of time.  
   public static boolean isBookable ( int roomID, int startDate, int endDate ) 
   {
      int roomPos = findRoom ( roomID );
      int[] calendar = roomList.get(roomPos).getCalendar();
      for ( int i = startDate - 1; i < endDate; i ++ ) {
         if ( calendar[i] != 0 )
         {
            return false;
         }
      }
      return true;
   }

   // Finds a room by ID.
   public static int findRoom ( int roomID ) 
   {
      int roomPos = -1;
      for ( int i = 0; i < roomList.size(); i ++ )
      {
         if ( roomList.get(i).getRoomID() == roomID )
         {
            roomPos = i;
            break;
         }
      }
      return roomPos;
   }
   
   // Find a booking by ID/Count
   public int findBooking ( int bookingID ) 
   {
      int bookingPos = -1;
      for ( int i = 0; i < bookings.size(); i ++ )
      {
         if ( bookings.get(i).getBookingID() == bookingID )
         {
            bookingPos = i;
            break;
         }
      }
      return bookingPos;
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
      
      System.out.println("\t > 1 First Name " +
                         "\n\t > 2 Last Name " + 
                         "\n\t > 3 Address " +
                         "\n\t > 4 Phonenumber " +
                         "\n\t > 5 Password ");
      
      int choice = Integer.parseInt(check("\tPlease select ", 0, 6));
      
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
         case 4:
            System.out.print("\tPhonenumber : ");
            phoneNr = check("\tPlease write your Phonenumber : ", 999999, 100000000);
            print();
            break;
         case 5:
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
      screenNumber = 1;
      printText("- USER UPDATED - ", size);
      print();
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
         System.out.print("\tCreate password : wew ");
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
            System.out.print(question + (min + 1) + " - " + (max - 1) + " ");
         }
         else 
         { 
            a++; 
         }
         if ( in.hasNextInt() ) 
         {
            input = in.next();
            if ( Integer.parseInt(input) == 0 ) 
            {
               mainMenu();
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
      in.nextLine();
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
         
         input = in2.nextLine();
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
      
      in.nextLine();
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
      
      in.nextLine();
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
      in.nextLine();
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
   
   public void exit()
   {
      this.running = false;
   }
}