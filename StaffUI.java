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
            
            case 1:  //manage Guest
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
                     //* print2(guestList.get(i).toString()); //breaks on TYPE, needs fixing__________________________
                     print2("PLACEHOLDER USER INFO");
                     printLines();
                     print2("Are you sure you wish to delete this user?");
                     print("1 Yes");
                     print("2 No");
                     selection = intCheck(1,2);
                     switch (selection)
                     {
                        case 1: //yes
                           guestList.remove(i);
                           mf.setGuestList(guestList);
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
                     ArrayList <Guest> guestListPrint = mf.getGuestList();
                     for (i= 0; i<guestListPrint.size(); i++)
                     {
                        System.out.println(guestListPrint.get(i).guestRepportToString());
                        System.out.println();
                     }
                     returnQuit();
                     break;
                  case 5: //return to main menu or quit
                     returnQuit();
                     break;
                             
               }
               
            
               break; 
            case 2:  //manage Staff
               this.screenNumber = 2;
               header("Manage staff");
               print("1 register a new staff");
               print("2 change staff details");
               print("3 delete a staff");
               print("4 print a repport of all staff to file");
               print("5 return to main menu or quit"); 
               selection = intCheck(1,5);
               switch (selection)
               {
                  case 1:  //register staff
                     header("Register a new staff");
                     createStaff();
                     returnQuit();
                     break;
                  case 2:  //change staff details
                     header("Edit details of a staff member");
                     changeStaff();
                     break;
                     
                  case 3:  //delete a staff
                     header("Delete a staff");
                     print2("Please type the CPR number of the staff you would like to delete.");
                     int i = 0;
                     ArrayList<Staff> staffList = mf.getStaffList();
                     do
                     {
                        Scanner console = new Scanner(System.in);
                        cpr = cprCheck(console);
                        staffList = mf.getStaffList();
                        for (i = 0; i < staffList.size(); i++)
                        {
                           if (staffList.get(i).getCpr().equals(cpr))
                           {  
                              userID = staffList.get(i).getID();
                              break;
                           }
                        }
                        if (userID == null)
                        {
                           print2("Cpr not found, please try again.");
                        }
                        
                     } while (userID == null);
                     printLines();
                     //* print2(staffList.get(i).toString()); //breaks on TYPE, needs fixing__________________________
                     print2("PLACEHOLDER USER INFO");
                     printLines();
                     print2("Are you sure you wish to delete this user?");
                     print("1 Yes");
                     print("2 No");
                     selection = intCheck(1,2);
                     switch (selection)
                     {
                        case 1: //yes
                           staffList.remove(i);
                           mf.setStaffList(staffList);
                           print2("Staff has been deleted");
                           returnQuit();
                           break;
                        case 2: //no
                           returnQuit();
                           break;
                     }
                     break;
                  case 4:   //print staff report
                     header("Print staff repport");
                     ArrayList<Staff> staffListPrint = mf.getStaffList();
                     for (i=0; i<staffListPrint.size(); i++)
                     {
                        System.out.println(staffListPrint.get(i).staffRepportToString());
                        System.out.println();
                     }
                     returnQuit();
                     break;
                  case 5:  //returnQuit
                     returnQuit();
                     break;
               }
               break;
            case 3:  //manage Room
               header("Manage rooms");
               print("1 Change an existing room");
               print("2 Add a new room out of thin air");
               print("3 Delete a room from existance");
               print("4 Back.");
               selection = intCheck(1,4);
               switch (selection)
               {
                  case 1:
                     header("Change an existing room");
                     changeRoom();
                     break;
                  case 2:
                     header("Add a new room");
                     createRoom();
                     break;
                  case 3:
                     header("Delete a room");
                     deleteRoom();   
                     break;
                  case 4:  //nevermind
                     returnQuit();
                     break;
               }
               break;
            case 4: // manage buching
               header("Manage booking");
               print("1 Create booking");
               print("2 Print booking report");
               print("3 Cancel booking");
               print("4 Back");
               selection = intCheck(1, 4);
               switch (selection)
               {
                  case 1:  // create bookin
                     header("Create booking");
                     createBooking();
                     returnQuit();
                     break;
                  case 2:   //Print booking report
                     header("Print booking report");
                     ArrayList<Booking> bookingList = mf.getBookingList();
                     for (int i = 0; i<bookingList.size(); i++)
                     {
                        System.out.println(bookingList.get(i).toString());
                        System.out.println();
                     }
                     //print("Report has been printed to file");
                     returnQuit();
                     break;
                  case 3:  //Cancel booking
                     header("Cancel booking");
                     //JAN'S REPONSABILITY _____________________________________________________________ALLAHU AKBAR__________________________________________________________
                     returnQuit();
                     break;
                  case 4:
                     returnQuit();
                     break;
               }
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
   
   public void changeStaff()
   {
    
      Scanner console = new Scanner(System.in);
      String nameWiP;
      print2("Please type the CPR number of the staff you would like to edit.");
      int i = 0;
      ArrayList<Staff> staffList = mf.getStaffList();
      do
      {
         
         cpr = cprCheck(console);
         for (i = 0; i < staffList.size(); i++)
         {
            if (staffList.get(i).getCpr().equals(cpr))
            {  
               userID = staffList.get(i).getID();
               break;
            }
         }
         if (userID == null)
         {
            print2("Cpr not found, please try again.");
         }
      } while (userID == null);
      printLines();
      System.out.println(staffList.get(i).staffRepportToString());
      //print2("PLACEHOLD USER INFORMATION");
      printLines();
      firstName = staffList.get(i).getFirstName();
      lastName = staffList.get(i).getLastName();
      fullName = firstName + " " + lastName;
      print2("What of " + fullName + "'s information would you like to change?");
      print("1 First name");
      print("2 Last name");
      print("3 Phone number");
      print("4 CPR");
      print("5 Address");
      print("6 Password");
      print("7 Change yearly vacation days");
      print("8 Change weekly work hours");
      print("9 Change password");
      print("10 Back");
      selection = intCheck(1,10);
      switch (selection)
      {
         case 1:  //change first name
            header("Change " + fullName + "'s first Name");
            print2("Please type the new name for '" + fullName + "'");
            nameWiP = "";
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            nameWiP = console.nextLine();
            nameWiP = nameFixer(nameWiP);
            staffList.get(i).setFirstName(nameWiP);
            mf.setStaffList(staffList); 
            print2("The previous name of '" + fullName + "' has been changed to '" + nameWiP + " " + lastName + "'");
            break;
         case 2:  //change last name
            header("Change " + fullName + "'s last Name");
            print2("Please type the new last name for '" + fullName + "'");
            nameWiP = "";
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            nameWiP = console.nextLine();
            nameWiP = nameFixer(nameWiP);
            staffList.get(i).setLastName(nameWiP);
            mf.setStaffList(staffList); 
            print2("The previous last name of '" + fullName + "' has been changed to '" + firstName + " " + nameWiP + "'");
            break;
         case 3:  //change phone number
            header("Change " + fullName + "'s phone number");
            print2("Please type the new phone number for '" + fullName + "'");
            String phoneNumberWiP = "";
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            phoneNumberWiP = console.nextLine();
            staffList.get(i).setPhoneNumber(phoneNumberWiP);
            mf.setStaffList(staffList); 
            print2("The previous phone number of '" + fullName + "' has been changed to '" + phoneNumberWiP + "'");
            break;
         case 4:  //change CPR??
            print2("Now why would you wanna do that silly?");
            break;
         case 5:  //change address
            header("Change " + fullName + "'s registered address");
            print2("Please type " + fullName + "'s address in three parts.");
            System.out.println();
            console.nextLine(); // fixes the bug of console skipping one input on nextLine
            print2("First, please type the street name and door number of " + fullName + "'s residence");
            address[0] = null;
            address[1] = null;
            address[2] = null;
            address[0] = nameFixer(console.nextLine());
            
            print2("Next, please type the city name of " + fullName + "'s residence.");
            address[1] = nameFixer(console.nextLine());
            
            print2("Lastly, please type the postcode of " + fullName + "'s residence.");
            address[2] = console.nextLine();
            staffList.get(i).setAddress(address);
            mf.setStaffList(staffList); 
            print2("The address of " + fullName + " has been changed to '" + Arrays.toString(address) + "'");
            break;
         case 6:  //change hourly pay
            header("Change " + fullName + "'s hourly salary");
            salary = staffList.get(i).getSalary();
            print2(fullName + " currently earns :" + salary + ",00- please type the amount you would like to change it to");
            salary = intCheck();
            staffList.get(i).setSalary(salary);
            mf.setStaffList(staffList);   
            break;
         case 7:  //change yearly vacation days
            header("Change the amount of vacation days " + fullName + " has yearly");
            vacation = staffList.get(i).getVacation();
            print2(fullName + " currently has " + vacation + " vacation days yearly");
            print2("Please type the amount you would like to change it to");
            vacation = intCheck();
            staffList.get(i).setVacation(vacation);
            mf.setStaffList(staffList); 
            break;
         case 8: //change weekly work hours
            header("Change " + fullName + "'s weekly working hours");
            hours = staffList.get(i).getHours();
            print2(fullName + " currently works " + hours + " hours weekly");
            print2("How many hours would you like to change it to?");
            hours = intCheck(1,45);
            staffList.get(i).setHours(hours);
            mf.setStaffList(staffList);
            break;
         case 9: //change password
            header("Change " + fullName + "'s password");   
            do //Shamelessly stole Teo's password code 
            { 
               print2("Type the new password");
               pass1 = console.next();
               print2("please type the new password a second time");
               pass2 = console.next();
               if ( !pass1.equals(pass2) ) 
               {
                  print("The passwords do not match, please try again");
               }
            } while ( !pass1.equals(pass2) );
            print2("Password modification successful");
            password = pass1;
            staffList.get(i).setPassword(password);
            mf.setStaffList(staffList);
            break;
         case 10: //nothing
            returnQuit();
            break;
      }
   }
      
   public void changeGuest()
   {
      Scanner console = new Scanner(System.in);
      String nameWiP;
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
      System.out.println(guestList.get(i).guestRepportToString());
      //print2("PLACEHOLD USER INFORMATION");
      printLines();
      firstName = guestList.get(i).getFirstName();
      lastName = guestList.get(i).getLastName();
      fullName = firstName + " " + lastName;
      print2("What of " + fullName + "'s information would you like to change?");
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
         case 1:  //change first name
            header("Change " + fullName + "'s first Name");
            print2("Please type the new name for '" + fullName + "'");
            nameWiP = "";
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            nameWiP = console.nextLine();
            nameWiP = nameFixer(nameWiP);
            guestList.get(i).setFirstName(nameWiP);
            mf.setGuestList(guestList); 
            print2("The previous name of '" + fullName + "' has been changed to '" + nameWiP + " " + lastName + "'");
            break;
         case 2:  //change last name
            header("Change " + fullName + "'s last Name");
            print2("Please type the new last name for '" + fullName + "'");
            nameWiP = "";
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            nameWiP = console.nextLine();
            nameWiP = nameFixer(nameWiP);
            guestList.get(i).setLastName(nameWiP);
            mf.setGuestList(guestList); 
            print2("The previous last name of '" + fullName + "' has been changed to '" + firstName + " " + nameWiP + "'");
            break;
         case 3:  //change phone number
            header("Change " + fullName + "'s phone number");
            print2("Please type the new phone number for '" + fullName + "'");
            String phoneNumberWiP = "";
            console.nextLine(); //does nothing but fixes the bug for nextLine input being skipped
            phoneNumberWiP = console.nextLine();
            guestList.get(i).setPhoneNumber(phoneNumberWiP);
            mf.setGuestList(guestList); 
            print2("The previous phone number of '" + fullName + "' has been changed to '" + phoneNumberWiP + "'");
            break;
         case 4:  //change CPR??
            print2("Now why would you wanna do that silly?");
            break;
         case 5:  //change address
            header("Change " + fullName + "'s registered address");
            print2("Please type " + fullName + "'s address in three parts.");
            System.out.println();
            console.nextLine(); // fixes the bug of console skipping one input on nextLine
            print2("First, please type the street name and door number of " + fullName + "'s residence");
            address[0] = null;
            address[1] = null;
            address[2] = null;
            address[0] = nameFixer(console.nextLine());
            
            print2("Next, please type the city name of " + fullName + "'s residence.");
            address[1] = nameFixer(console.nextLine());
            
            print2("Lastly, please type the postcode of " + fullName + "'s residence.");
            address[2] = console.nextLine();
            guestList.get(i).setAddress(address);
            mf.setGuestList(guestList); 
            print2("The address of " + fullName + " has been changed to '" + Arrays.toString(address) + "'");
            break;
         case 6:  //change password
            header("Change " + fullName + "'s password");   
            do //Shamelessly stole Teo's password code 
            { 
               print2("Type the new password");
               pass1 = console.next();
               print2("please type the new password a second time");
               pass2 = console.next();
               if ( !pass1.equals(pass2) ) 
               {
                  print("The passwords do not match, please try again");
               }
            } while ( !pass1.equals(pass2) );
            print2("Password modification successful");
            password = pass1;
            guestList.get(i).setPassword(password);
            mf.setGuestList(guestList);
            break;
         case 7: //nothing
            break;
      }
   }
   
   public void deleteRoom ()
   {
      print2 ( "Select a floor : " );
      int floor = intCheck ( 1, 10 );
      
      ArrayList<Room> roomList = mf.getRoomList();
      
      for ( int i = 0; i < roomList.size(); i ++ )
      {
         if ( roomList.get(i).getFloor() == floor )
         {
            print ( (i+1) + " " + roomList.get(i).toString() );
         }
      }
      
      print2 ( "Select a room : " );
      int roomNo = intCheck ( 1, roomList.size() ) - 1;
      roomList.remove(roomNo);
      mf.setRoomList(roomList);
   }
   
   public void changeRoom()
   {
      print2 ( "Select a floor : " );
      int floor = intCheck ( 1, 10 );
      
      ArrayList<Room> roomList = mf.getRoomList();
      
      for ( int i = 0; i < roomList.size(); i ++ )
      {
         if ( roomList.get(i).getFloor() == floor )
         {
            print ( "Room No. : "  + (i+1) + "\n ");
            System.out.println ( roomList.get(i).toString() );
         }
      }
      
      print2 ( "Select a room : " );
      int roomNo = intCheck ( 1, roomList.size() ) - 1;
      
      print2 ( "What would you like to change ?" );
      print("1 Beds : " + roomList.get(roomNo).getBeds() );
      print("2 Price : " + roomList.get(roomNo).getPrice() );
      print("3 Is booked : " + roomList.get(roomNo).getIsBooked() );
      print("4 Requires cleaning : " + roomList.get(roomNo).getRequiresCleaning() );
      print("5 Back");
      
      int answer;
      selection = intCheck(1,5);
      switch (selection)
      {
         case 1:  //change number of beds
            header("Change number of beds");
            print2("Please type the new amount of beds.");
            int bedsWiP = 0;
            bedsWiP = intCheck(1, 10);
            roomList.get(roomNo).setBeds(bedsWiP);
            mf.setRoomList(roomList); 
            break;
         case 2:  //change price
            header("Change the room's price");
            print2("Please type the new price of the room.");
            int priceWiP = 0;
            priceWiP = intCheck( 100, 100000 );
            roomList.get(roomNo).setPrice(priceWiP);
            mf.setRoomList(roomList); 
            break;
         case 3:  //change isBooked
            header("Change room's booking status");
            print2("Please specify wether the room's booked or not.");
            print("0 - Booked");
            print("1 - Not booked");
            boolean isBookedWiP = false;
            answer = intCheck ( 0, 1 );
            if ( answer == 0 )
            {
               isBookedWiP = false;
            }
            else
            {
               isBookedWiP = true;
            }
            roomList.get(roomNo).setIsBooked(isBookedWiP);
            mf.setRoomList(roomList); 
            break;
         case 4:  //change requiresCleaning
            header("Change room's cleaning status");
            print2("Please specify wether the room needs cleaning or not.");
            print("0 - Does not need cleaning");
            print("1 - Needs cleaning");
            boolean requiresCleaningWiP = false;
            answer = intCheck ( 0, 1 );
            if ( answer == 0 )
            {
               requiresCleaningWiP = false;
            }
            else
            {
               requiresCleaningWiP = true;
            }
            roomList.get(roomNo).setRequiresCleaning(requiresCleaningWiP);
            mf.setRoomList(roomList); 
            break;
         case 5:
            returnQuit();
            break;
      }
      mf.setRoomList(roomList);
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
            print2("Please select the preferred room");
            selection = intCheck();
         } else 
         {
            print2("No matching rooms found for the selected dates and the amount of beds, please try again.");  //How do i make it loop back?
         }
         print2("Please select the preferred room");
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

   public void createRoom()
   {
      print2 ("Please type the ID of the new Room : ");
      roomID = intCheck(101, 1099);
      while ( roomExists(roomID) ) 
      {  
         print2 ("The room already exists, please input another ID : ");
         roomID = intCheck ( 101, 1099 );   
      }
      print2 ("Please type the amount of beds the room will have : ");
      int beds = intCheck ( 1, 20 );
      
      Room r = new Room (roomID, beds);
      ArrayList<Room> roomList = mf.getRoomList();
      roomList.add(r);
      mf.setRoomList(roomList);
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
      print2("First, please type the street name and door number of " + firstName + "'s residence");
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
   public boolean roomExists ( int roomID )
   {
      for ( int i = 0; i < mf.getRoomList().size(); i ++ )
      {
         if ( mf.getRoomList().get(i).getRoomID() == roomID )
         {
            return true;
         }
      }
      return false;
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
      print("3 Manage rooms");
      print("4 Manage booking");
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