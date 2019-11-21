import java.io.*;
import java.util.*;

public class StaffUI extends CLI
{
   private  int spacerVariable = 40;
   
   private static int[] monthList = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
   
   private String firstName;
   private String lastName;
   private String fullName;
   private String cpr;
   private String[] address = new String[3];
   private String phoneNumber;
   private String password;
   private String pass1;
   private String pass2;
   private int hours;
   private double salary;
   private int vacation;
   private int headerLength = 100;
   private String formatSymbol = "\t>";
   private String print2formatSymbol = "\t";
   
   private int bookingID;
   private int roomID;
   private String userID;
   private int startDate;
   private int endDate;
   private int price;
   private boolean hasInternet;
   private int beds;
   private int roomPrice;
   private int selection;
   private MainFrame mf;

   public StaffUI(Staff user, String title, MainFrame mfRef)
   {
      this.title = title;
      this.screenNumber = 10;
      this.loggedUser = user.getLastName();
      this.userAccessLevel = user.getAccessLevel();
      this.seperator = "----------------------------------------------------------------------------------------------------"; // 100 dashes
      this.running = true;
      this.mf = mfRef;
      this.fullName = user.getFirstName()+ " " + user.getLastName();
   }
   
   public StaffUI(Staff user, String title, MainFrame mfRef, int accessLevel)
   {
      this.title = title;
      this.screenNumber = 10;
      this.loggedUser = user.getLastName();
      this.userAccessLevel = accessLevel;
      this.seperator = "----------------------------------------------------------------------------------------------------"; // 100 dashes
      this.running = true;
      this.mf = mfRef;
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
            
            case 1:        //manage Guest
               header("Manage guests");
               print("1 register a new guest");
               print("2 change guest details");
               print("3 delete a guest");
               print("4 print a repport of all guests to file");
               print("5 return to main menu or quit");            
               
               selection = intCheck(1,5);
               switch (selection)
               {
                  case 1:  //create guest
                     header("register a new guest");
                     createGuest();
                     returnQuit();
                     break;
                  case 2:  //change guest
                     header("Edit guest details");
                     changeGuest();
                     returnQuit();
                     break;
                  case 3:  //delete guest 
                     header("Delete a guest");
                     print2("Please type the CPR number of the guest you would like to delete.");
                     int i = 0;
                     ArrayList<Guest> guestList = mf.getGuestList();
                     do
                     {
                        Scanner console = new Scanner(System.in);
                        cpr = cprCheck(console);
                        guestList = mf.getGuestList();
                        for (i = 0; i < guestList.size(); i++)
                        {
                           if (guestList.get(i).getCPR().equals(cpr))
                           {  
                              userID = guestList.get(i).getID();
                              break;
                           }
                        }
                        if (userID == null)
                        {
                           print2("Cpr not found, please try again.");
                        }
                        
                     } while (userID == null);
                     printLines();
                     //* print2(guestList.get(i).toString()); //breaks on TYPE, needs fixing
                     print2("PLACEHOLDER USER INFO");
                     printLines();
                     print2("Are you sure you wish to delete this user?");
                     print("1 Yes");
                     print("2 No");
                     selection = intCheck(1,2);
                     switch (selection)
                     {
                        case 1: //yes
                           //delete user //____________________________________________
                           //mf.guestList.remove(i);
                           print2("Guest has been deleted");
                           returnQuit();
                           break;
                        case 2: //no
                           returnQuit();
                           break;
                     }
                     break;
                  case 4:  //print guest repport
                     header("Print guest repport");
                     
                     print2("Guest repport has been saved to: " + "PLACEHOLDER : LOCATION");
                     returnQuit();
                     break;
                  case 5: //return to main menu or quit
                     returnQuit();
                     break;
                             
               }
               
            
               break; 
            case 2: //manage Staff
               header("Create a new staff");
               createStaff();
               returnQuit();
               break;
            case 3:  //manage Room
               header("Book a room");
               createBooking();
               returnQuit();
               break;
            case 5:
               exit();
               break;
            case 10:
               mainMenu();
               break;
            default:
               print("invalid input, please try again.");
               System.out.println();
               System.out.println();
               mainMenu();
               break;
         }
      }  
   }
   
//    public void main(String[] args)
//    {
//       createGuest();
//       createStaff();
//    }
   public void changeGuest()
   {
      Scanner console = new Scanner(System.in);
      print2("Please type the CPR number of the guest you would like to edit.");
      int i = 0;
      ArrayList<Guest> guestList = mf.getGuestList();
      do
      {
         
         cpr = cprCheck(console);
         guestList = mf.getGuestList();
         for (i = 0; i < guestList.size(); i++)
         {
            if (guestList.get(i).getCPR().equals(cpr))
            {  
               userID = guestList.get(i).getID();
               break;
            }
         }
         if (userID == null)
         {
            print2("Cpr not found, please try again.");
         }
      } while (userID == null);
      printLines();
      print2("PLACEHOLD USER INFORMATION");
      printLines();
      firstName = guestList.get(i).getFirstName();
      print2("What of " + firstName + "'s information would you like to change?");
      print("1 First name");
      print("2 Last name");
      print("3 Phone number");
      print("4 CPR");
      print("5 Address");
      print("6 Password");
      print("7 Nothing");
      selection = intCheck(1,7);
      switch (selection)
      {
         case 1:
            header("Change " + firstName + "'s first Name");
            print2("Please type the new name for '" + firstName + "'");
            String nameWiP;
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            nameWiP = console.nextLine();
            nameWiP = nameFixer(nameWiP);
            print2("The previous first name of '" + firstName + "' has been changed to '" + nameWiP + "'");
            guestList.get(i).setFirstName(nameWiP);
            setUserList(guestList<Guest>);
            break;
         case 2:
            break;
         case 3:
            break;
         case 4:
            break;
         case 5:
            break;
         case 6:
            break;
         case 7:
            returnQuit();
            break;
      }
   }
   
   public void createBooking()
   {
      Scanner console = new Scanner(System.in);
      int selection = 0;
      
      print2("What is the cpr number of the Guest you would like to book a room for?");
      do
      {
         cpr = cprCheck(console);
         ArrayList<Guest> guestList = mf.getGuestList();
         for (int i = 0; i < guestList.size(); i++)
         {
            if (guestList.get(i).getCPR().equals(cpr))
            {  
               userID = guestList.get(i).getID();
               break;
            }
         }
         print2("Cpr not found, please try again.");
      } while (userID == null);
      boolean suitableRoom = false;
      ArrayList<Room> roomList = mf.getRoomList();
      ArrayList<Room> displayRooms = new ArrayList<Room>();
      int selectedRoom = 0;
      while (!suitableRoom)
      {
         print2("How many beds would the guest like to have in his room?");
         beds = intCheck();
         
         int monthTemp;
         int dayTemp;      
         print2("Please type the starting month of the guest's stay");
         monthTemp = intCheck(1,12);
         print2("please type the starting day of guest's stay");
         dayTemp = intCheck(1,monthList[monthTemp-1]);
         startDate = dateNumber(monthTemp, dayTemp);
         
         
         print2("How many days will the guest be staying for?");
         endDate = intCheck();
         endDate = endDate + startDate;
         
         print2("Does the guest wish to have internet access?");
         System.out.println();
         print("1 Yes.");
         print("2 No");
         selection = intCheck();
         
         switch(selection)
         {
            case 1:
               hasInternet = true;
               break;
            case 2:
               hasInternet = false;
               break;
         }
         
         //* get 5 options of rooms that match the beds + isBookable for the duration of stay
         int totalRoomsMatched = 0;
         Room tempRoom;
         for ( int i = 0; i < (roomList.size() < 5 ? roomList.size(): 5); i++)
         {
            //* initialize each toom to room 0-4 ints 
            tempRoom =(roomList.get(i));
            if (tempRoom.getBeds() >= beds && tempRoom.getIsBooked() == false)
            {
               displayRooms.add(tempRoom);
               totalRoomsMatched++;
            }  
         }
         
         if (totalRoomsMatched>0)
         {
            header("Select a room");
            printLines();
            suitableRoom = true;
            
            for (selectedRoom=1; selectedRoom<=totalRoomsMatched; selectedRoom++)
            {
               System.out.println("Selection " + "<" + (selectedRoom) + ">");
               System.out.println();
               System.out.println(displayRooms.get(selectedRoom-1).toString());  
               System.out.println();          
            }
            printLines();
            selection = intCheck();
         } else 
         {
            print2("No matching rooms found for the selected dates and the amount of beds, please try again.");  //How do i make it loop back?
         }
      }
      
      roomID = displayRooms.get(selection-1).getRoomID();
      bookingID = mf.generateBookingID(); 
      roomPrice = displayRooms.get(selectedRoom-2).getPrice();
      Booking guestBooking = new Booking(bookingID,roomID, userID, startDate, endDate, roomPrice, hasInternet);
      mf.createBooking(guestBooking); // in order to save
   }
   
   
   public void createStaff()
   {
      this.screenNumber = 2;
      creationTemplate("Staff");
      print2("How many hours will " + firstName + " be working weekly?");
      hours = intCheck();
      
      print2("What is " + firstName + "'s hourly salary?");
      salary = doubleCheck();
      
      print2("How many vacation days will " + firstName + " have yearly?");
      vacation = intCheck();
      
      int staffID = mf.generateStaffID();
      Staff created = new Staff( firstName, lastName, cpr, "ST", address, phoneNumber, password, staffID, hours, salary, vacation);
      //created.setAddress(address);
      mf.addStaff(created);
   }

   public void createGuest() 
   {
      this.screenNumber = 1;
      creationTemplate("Guest");
      int guestID = mf.generateGuestID();
      Guest created = new Guest(firstName, lastName, cpr, address, phoneNumber, password, guestID);
      mf.addGuest(created);
      System.out.println();
      
   }

   public void creationTemplate(String type)
   {
      Scanner input = new Scanner(System.in);
      Scanner inputAddress = new Scanner(System.in);
      
      print2("Please type the first name of the new " + type + "?");
      firstName = nameFixer(input.next());
      
      print2("Please type the last name of the new " + type +"?");
      lastName = nameFixer(input.next());
      
      print2("Please type the CPR number of the " + type);
      cpr = cprCheck(input);
      
      
      System.out.println();
      print2("Please type the " + type + "'s address in three parts.");
      System.out.println();
      print2("First, please type the stree name and door number of " + firstName + "'s residence");
      address[0] = nameFixer(inputAddress.nextLine());
      
      print2("Next, please type the city name of " + firstName + "'s residence.");
      address[1] = nameFixer(inputAddress.nextLine());
      
      print2("Lastly, please type the postcode of " + firstName + "'s residence.");
      address[2] = nameFixer(inputAddress.nextLine());
      
      print2("Please type the " + type + "'s phone number");
      phoneNumber = phoneNumberCheck(input);
      
      print2("Please create a password for the new " +  type);           
      
      do //Shamelessly stole Teo's password code 
      { 
         print2("Type the password.");
         pass1 = input.next();
         print2("please type the same password a second time");
         pass2 = input.next();
         if ( !pass1.equals(pass2) ) 
         {
            print("The passwords do not match, please try again.");
         }
      } while ( !pass1.equals(pass2) );
      print2("Password creation successful");
      password = pass1;
      System.out.println();  
   }
      
   

//_________________________________________________________METHOD_METHODS___________________________________________

   public static int dateNumber ( int month, int day )
   {
      String[] monthName = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
      int days = 0;
      for ( int i = 0; i < month - 1; i ++ ) 
      {
         days += monthList[i];
      }
      days += day;
      return days;
   } 

  
//_________________________________________________________CHECK METHODS_____________________________________________   
   public double doubleCheck()
   {
      Scanner input = new Scanner(System.in);
      double number;
      
      while (!input.hasNextDouble())
      {
         String shit = input.next();
         print2("Invalid input detected, please only type numbers.");
         
      }
      number = input.nextDouble();
      return number;
   }


   public int intCheck(int min, int max)
   {
      Scanner input = new Scanner(System.in);
      int number;
      
      while (!input.hasNextInt())
      {
         String shit = input.next();
         print2("Invalid input detected, please only type numbers without a comma.");
         
      }
      number = input.nextInt();
      while (!(number>=min && number<=max))
      {
         print2(number + " is out of the acceptable range, plase type a number from " + min + " to " + max);
         while (!input.hasNextInt())
         {
            String shit = input.next();
            print2("Invalid input detected, please only type numbers without a comma.");
            
         }
         number = input.nextInt();
      }   
      return number;
   }   
   
   public int intCheck()
   {
      Scanner input = new Scanner(System.in);
      int number;
      
      while (!input.hasNextInt())
      {
         String shit = input.next();
         print2("Invalid input detected, please only type numbers without a comma.");
      }
      number = input.nextInt();
      return number;
   }
   
   public String phoneNumberCheck(Scanner console)
   {
      String number;
      number = console.next();
   
      while (!(number.length() == 8)) //checking that number length is correct
      {
         if (number.length() > 8)
         {
            print2("Invalid phone number, please input a correct phone number");
            print2("Phone numbers should look something like this: 12345678\n");
            number = console.next();
         }
            
         if (number.length() < 8)
         {
            print2("Invalid phone number, please input a correct phone number");
            print2("Phone numbers should look something like this: 12345678\n");
            number = console.next();
         }
      }
      
      char intCheck;
      for (int i=0; i<8;i++)
      {
         intCheck = number.charAt(i);
         while (!Character.isDigit(intCheck))
         {
            print2("Phone numbers must contain 8 numbers, and no country code.");
            print2("For example '12345678'. Please try again.");
            number = console.next();
         
            while (!(number.length() == 8)) //checking that phone number length is corret.
            {
               if (number.length() > 8)
               {
                  print2("Invalid phone number, please input a correct phone number");
                  print2("Phone numbers should look something like this: 12345678\n");
                  number = console.next();
               }
                     
               if (number.length() < 8)
               {
                  print2("Invalid phone number, please input a correct phone number");
                  print2("Phone numbers should look something like this: 12345678\n");
                  number = console.next();
               }
            }
            intCheck = number.charAt(i);
         }
      }
      return number;
   }
   
   //Check to see that CPR is valid
   public String cprCheck(Scanner console)
   {  
      String cpr = "";
      cpr = console.next();
      
      while (!(cpr.length() == 11)) //checking that CPR number is corret.
      {
         if (cpr.length() > 11)
         {
            print2("CPR number is too long, please input a correct CPR number");
            print2("Cpr should look something like this: 123456-1234\n");
            cpr = console.next();
         }
            
         if (cpr.length() < 11)
         {
            print2("CPR number is too short, please input a correct CPR number");
            print2("Cpr should look something like this: 123456-1234\n");
            cpr = console.next();
         }
      }
      while (cpr.charAt(6) != '-')
      {
         print2("CPR numbers must contain 6 numbers, a dash and the remaining 4 numebers.");
         print2("For example '123456-1234'. Please try again.");
         cpr = console.next();
      }
      char intCheck;
      for (int i=0; i<11;i++)
      {
         if (i==6)
         {
            continue;
         }
         intCheck = cpr.charAt(i);
         while (!Character.isDigit(intCheck))
         {
            print2("CPR numbers must contain 6 numbers, a dash and the remaining 4 numebers.");
            print2("For example '123456-1234'. Please try again.");
            cpr = console.next();
         
            while (!(cpr.length() == 11)) //checking that CPR number is corret.
            {
               if (cpr.length() > 11)
               {
                  print2("CPR number is too long, please input a correct CPR number.");
                  print2("Cpr should look something like this: 123456-1234\n");
                  cpr = console.next();
               }
                     
               if (cpr.length() < 11)
               {
                  print2("CPR number is too short, please input a correct CPR number.");
                  print2("Cpr should look something like this: 123456-1234\n");
                  cpr = console.next();
               }
            }
            while (cpr.charAt(6) != '-')
            {
               print2("CPR numbers must contain 6 numbers, a dash and the remaining 4 numebers.");
               print2("For example '123456-1234'. Please try again.");
               cpr = console.next();
            }
            intCheck = cpr.charAt(i);
         }
            
      }
      return cpr;
   }
//_____________________________________________________NAME FORMAT METHODS_________________________________________   
   public String nameFixer(String name)
   {
      String namePart;
      String fixedName = "";
      String tempString = "";
      char tempChar;
      Scanner nameConsole = new Scanner(name);   
         
      while (nameConsole.hasNext())
      {    
         namePart = nameConsole.next();
      
         for(int i=0;i<namePart.length();i++)
         {
            if (i== 0)
            {
               tempChar = namePart.charAt(i);
               tempString = String.valueOf(tempChar);
               tempString = tempString.toUpperCase();
               fixedName += tempString;
            } else 
            {
            
               tempChar = namePart.charAt(i);
               tempString = String.valueOf(tempChar);
               tempString = tempString.toLowerCase();
               fixedName += tempString;
            }
         }
         fixedName += " ";
      }
      fixedName = fixedName.trim();
      return fixedName;  
   }
//__________________________________________________UI + UI DECOR________________________________________________________   
   public void printLines()
   {
      for (int i = 0; i < headerLength; i++)
      {
         System.out.print("-");
      }
      System.out.println();
   }
     
   public void print(String text)
   {
      System.out.println(formatSymbol + text);
   }
   
   public void print2(String text)
   {
      System.out.println(print2formatSymbol + text);
   }
  
   public void header(String text)
   {
      spacer();
      System.out.println(print2formatSymbol + "HOTEL PLAZA");
      print2("@ " + fullName);
      printLines();
      print2(text);
      //printLines();
      System.out.println();
   }

   public  void spacer()
   {
      for (int i=0; i<spacerVariable; i++)
      {
         System.out.println();
      }
   }
//____________________________________________________MENU + MENU NAVIGATION__________________________________________
   public void returnQuit()
   {
      int choice;
      System.out.println();
      print2("What would you like to do next?");
      System.out.println();
      print("1 Return to main menu");
      print("2 Quit");
      choice = intCheck();
      switch (choice)
      {
         case 1:
            this.screenNumber = 10; //mainMenu's number
            break;
         case 2:
            this.running = false;
            break;
      }
   }
   
   public void mainMenu()
   {
      this.screenNumber = 10; 
      Scanner input = new Scanner(System.in);
      header("Main Menu");
      print("1 Manage guests");
      print("2 Manage staff");
      print("3 Manage bookings");
      print("4 ?");
      print("5 quit");
      screenNumber = intCheck();
   }
   
   public void exit()
   {
      this.running = false;
   }
   
   ////////// Setters & Getters //////////
   
   public void setMFRef(MainFrame mfRef)
   {
      this.mf = mfRef;
   }
}