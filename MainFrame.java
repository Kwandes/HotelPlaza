// The controller than controls interaction and information transfer inbetween classes

import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;

// If you wish to print out logs, add sysout in FileManagement

public class MainFrame // MF or motherFucker for short
{
   private ArrayList<Booking> bookingList;
   private ArrayList<Booking> archivedBookingList;
   private ArrayList<Room> roomList;
   private ArrayList<Guest> guestList;
   private ArrayList<Staff> staffList;
   
   private FileManagement file;
   private boolean printLogsToConsole;
   private Properties config;
   private boolean isInitiatedProperly;
   private String appTitle;
   private boolean saveToFile = false;
   
   public MainFrame()
   {  
      this.printLogsToConsole = false;
   
      file = new FileManagement(this, "Logs");
      createLog("New MainFrame has been created", Log.Type.INFO);
   } 

   public MainFrame(boolean printLogs)
   {  
      this.printLogsToConsole = printLogs;
   
      file = new FileManagement(this, "Logs");
      createLog("New MainFrame has been created", Log.Type.INFO);
   }    

   public MainFrame(boolean printLogs, String filePath)
   {  
      this.printLogsToConsole = printLogs;
   
      file = new FileManagement(this, filePath);
      createLog("New MainFrame has been created", Log.Type.INFO);
   }    
   
   // Initialization of all MF stuff. Config, populate data arrays etc
   public void init()
   {
      try
      {  
         ////////// Init MainFrame //////////
         createLog("MainFrame Init started", Log.Type.INFO);
         
         ////////// Load Config //////////
         createLog("Loading config...", Log.Type.INFO);
         config = new Properties();
         config.load(new FileInputStream("config.properties"));
         createLog("Config loaded", Log.Type.INFO);
         this.appTitle = config.getProperty("appTitle", "YEET");
            
         file.setFilePaths(config);
         ////////// Get config and init arrays //////////
         // get config with filepaths etc
         
         // load ALL arrays from file
         createLog("Loading Array Lists...", Log.Type.INFO);
         
         boolean loadStatus = true;
         
         if(!loadBookingList()) loadStatus = false;
         if(!loadArchivedBookingList()) loadStatus = false;
         if(!loadRoomList()) loadStatus = false;
         if(!loadStaffList()) loadStatus = false;
         if(!loadGuestList()) loadStatus = false;
         
         if(loadStatus) createLog("ArrayList setup complete", Log.Type.INFO);
         else createLog("ArrayList setup incomplete", Log.Type.WARNING);
         
         createLog("MainFrame init has completed successfully", Log.Type.INFO);
         
         this.isInitiatedProperly = true;
      }
      catch (Exception e)
      {
         createLog("MainFrame init has NOT completed successfully", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
         this.isInitiatedProperly = false;
      }
      
   }
   
   //////////////////// UI API ////////////////////
   
   ////////// Login //////////
   // Returns a valid user for a given login. If none is found, returns null
   public Guest validateLoginGuest(String phoneNumber, String password)
   {  
      try
      {     
         for(int i = 0; i < guestList.size(); i++)
         {
            if(guestList.get(i).getPhoneNumber().equals(phoneNumber) && guestList.get(i).getPassword().equals(password))
            {
               createLog("Guest " + guestList.get(i).getID() + " has logged in", Log.Type.INFO);
               return guestList.get(i);
            }
         }
      }
      catch (Exception e) {  createLog(e, Log.Type.ERROR); } 
      createLog("Failed Login attempt, phone Number: " + phoneNumber + " , password: " + password, Log.Type.WARNING);
      return null;
   }
   
   public Staff validateLoginStaff(String phoneNumber, String password)
   {  
      try
      {
         for(int i = 0; i < staffList.size(); i++)
         {
            if(staffList.get(i).getPhoneNumber().equals(phoneNumber) && staffList.get(i).getPassword().equals(password))
            {
               createLog("Staff Member " + staffList.get(i).getID() + " has logged in", Log.Type.INFO);
               return staffList.get(i);
            }
         }
      }
      catch (Exception e) {  createLog(e, Log.Type.ERROR); } 
      createLog("Failed Login attempt, phone Number: " + phoneNumber + " , password: " + password, Log.Type.WARNING);
      return null;
   }
   
   ////////// Booking stuff //////////
   public void createBooking(int roomID, int userID, int startDate, int endDate, int roomPrice, boolean hasInternet)
   {
      try
      {
         int bookingID = 1;
         bookingList.add(new Booking(bookingID, roomID, Integer.toString(userID), startDate, endDate, roomPrice, hasInternet));
         
         if(this.saveToFile) file.saveData(new Information(bookingList, null, null, null, null));
         createLog("New Booking created, id: " + bookingList.get(bookingList.size()-1).getBookingID() + " by " + userID, Log.Type.INFO);
      }
      catch (Exception e)
      {
         createLog("Create Booking Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public void replaceBooking(int bookingID, Booking newBooking)
   {
      try
      {
         for(int i = 0; i < bookingList.size(); i++)
         {
            if(bookingList.get(i).getBookingID() == bookingID)
            {
               bookingList.set(i, newBooking);
               file.saveData(new Information(bookingList, null, null, null, null));
               createLog("Booking " + bookingID + "has been modified", Log.Type.INFO);
               break;
            }
         }
      }
      catch (Exception e)
      {
         createLog("Replace Booking Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public ArrayList<Booking> getUsersBookings(int userID)
   {
      ArrayList<Booking> userBookings = new ArrayList<Booking>();
      try
      {
         for(int i = 0; i < bookingList.size(); i++)
         {
            if(bookingList.get(i).getUserID().equals(userID))
            {
               userBookings.add(bookingList.get(i));
            }
         }
      }
      catch (Exception e)
      {
         createLog("Get Users Bookings Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
      return userBookings;
   }   
   
   public void setBookingList(ArrayList<Booking> bookingList)
   {
      try
      {
         this.bookingList = bookingList;
         createLog("Booking List modified", Log.Type.INFO);
         if (this.saveToFile)
         {
            file.saveData(new Information(this.bookingList, null, null, null, null));
            createLog("Booking List saved", Log.Type.INFO);
         }
      }
      catch (Exception e)
      {
         createLog("Set Booking List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public boolean loadBookingList()
   {
      try
      {
         createLog("Loading BookingList...", Log.Type.INFO);
         this.bookingList = file.loadData(new Information(true, false, false, false, false)).bookingList;
         return true;
      }
      catch (Exception e)
      {
         createLog("Load Booking List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
         return false;
      }
   }
   
   public ArrayList<Booking> getBookingList()
   {
      return this.bookingList;
   }
   
   public void removeBooking(int bookingID)
   {
      for (int i = 0 ; i < bookingList.size(); i++)
      {
         if (bookingList.get(i).getBookingID() == bookingID)
         {
            try
            {
               bookingList.remove(i);
               file.saveData(new Information(bookingList, null, null, null, null));
               createLog("Booking " + bookingID + "has been removed", Log.Type.INFO);
            }
            catch (Exception e)
            {
               createLog("Remove Booking Failed", Log.Type.WARNING);
               createLog(e, Log.Type.ERROR);
            }
            break;
         }
      }
   }

   ////////// Archived Bookings //////////
      
   public void setArchivedBookingList(ArrayList<Booking> archivedBookingList)
   {
      try
      {
         this.archivedBookingList = archivedBookingList;
         createLog("Archived Booking List modified", Log.Type.INFO);
         if (this.saveToFile)
         {
            file.saveData(new Information(null, this.archivedBookingList, null, null, null));
            createLog("Archived Booking List saved", Log.Type.INFO);
         }
      }
      catch (Exception e)
      {
         createLog("Set Archive Booking Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public boolean loadArchivedBookingList()
   {
      try
      {
         this.archivedBookingList = file.loadData(new Information(false, true, false, false, false)).archivedBookingList;
         return true;
      }
      catch (Exception e)
      {
         createLog("Load Archived Booking List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
         return false;
      }
   }
   
   public ArrayList<Booking> getArchivedBookingList()
   {
      return this.archivedBookingList;
   }
   
   public void archiveBooking(int bookingID)
   {
      for (int i = 0 ; i < bookingList.size(); i++)
      {
         if (bookingList.get(i).getBookingID() == bookingID)
         {
            try
            {
               archivedBookingList.add(bookingList.get(i));
               bookingList.remove(i);
               file.saveData(new Information(bookingList, archivedBookingList, null, null, null));
               createLog("Booking " + bookingID + "has been archived", Log.Type.INFO);
            }
            catch (Exception e)
            {
               createLog("Archive Booking Failed", Log.Type.WARNING);
               createLog(e, Log.Type.ERROR);
            }
            break;
         }
      }
   }
   
   ////////// Room Stuff //////////
   
   public void requestRoomCleaning(int roomID)
   {
      try
      {
         for(int i = 0; i < roomList.size(); i++)
         {
            if(roomList.get(i).getRoomID() == roomID)
            {
               roomList.get(i).setRequiresCleaning(true);
               file.saveData(new Information(null, null, roomList, null, null));
               createLog("Room " + roomID + "has changed status to : requires cleaning", Log.Type.INFO);
               break;
            }
         }
      }
      catch (Exception e)
      {
         createLog("Request Cleaning Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public void setRoomList(ArrayList<Room> roomList)
   {
      try
      {
         this.roomList = roomList;
         createLog("Room List modified", Log.Type.INFO);
         if (this.saveToFile)
         {
            file.saveData(new Information(null, null, this.roomList, null, null));
            createLog("Room List saved", Log.Type.INFO);
         }
      }
      catch (Exception e)
      {
         createLog("Set Room List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public boolean loadRoomList()
   {
      try
      {
         this.roomList = file.loadData(new Information(false, false, true, false, false)).roomList;
         return true;
      }
      catch (Exception e)
      {
         createLog("Load Room List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
         return false;
      }
   }
   
   public ArrayList<Room> getRoomList()
   {
      return this.roomList;
   }
   
   ////////// Guest staff //////////
   
   public void setGuestList(ArrayList<Guest> guestList)
   {
      try
      {
         this.guestList = guestList;
         createLog("Guest List modified", Log.Type.INFO);
         if (this.saveToFile)
         {
            file.saveData(new Information(null, null, null, this.guestList, null));
            createLog("Guest List saved", Log.Type.INFO);
         }
      }
      catch (Exception e)
      {
         createLog("Set Booking List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public boolean loadGuestList()
   {
      try
      {
         this.guestList = file.loadData(new Information(false, false, false, true, false)).guestList;
         return true;
      }
      catch (Exception e)
      {
         createLog("Load Guest List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
         return false;
      }
   }
   
   public ArrayList<Guest> getGuestList()
   {
      return guestList;
   }
   
   ////////// Staff stuff //////////
   
   public void setStaffList(ArrayList<Staff> staffList)
   {
      try
      {
         this.staffList = staffList;
         createLog("Staff List modified", Log.Type.INFO);
         if (this.saveToFile)
         {
            file.saveData(new Information(null, null, null, null, this.staffList));
            createLog("Staff List saved", Log.Type.INFO);
         }
      }
      catch (Exception e)
      {
         createLog("Set Staff List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public boolean loadStaffList()
   {
      try
      {
         this.staffList = file.loadData(new Information(false, false, false, false, true)).staffList;
         return true;
      }
      catch (Exception e)
      {
         createLog("Load Staff List Failed", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
         return false;
      }
   }
   
   public ArrayList<Staff> getStaffList()
   {
      return this.staffList;
   }
   
   ////////// User Interface //////////
   public void openCLI()
   {  
      LoginUI loginUI = new LoginUI(this.appTitle, this);
      loginUI.display();
      
      Staff staffMember = null;
      Guest guest = null;
      StaffUI staffUI;
      GuestUI guestUI;
      
      createLog("Moving to next UI", Log.Type.INFO);
      try
      {
         if( loginUI.getStaff() != null)
         {
            createLog("Creating StaffUI", Log.Type.INFO);
            staffMember = loginUI.getStaff();
            
            staffUI = new StaffUI(staffMember, this.appTitle, this);
            staffUI.setMFRef(this);
            createLog("StaffUi created", Log.Type.INFO);
            staffUI.display();
         }
         else if ( loginUI.getGuest() != null)
         {
            createLog("Creating GuestUI", Log.Type.INFO);
            guest = loginUI.getGuest();
            
            guestUI = new GuestUI(guest, this.appTitle, this);
            createLog("GuestUi created", Log.Type.INFO);
            guestUI.display();
         }
         else
         {
            createLog("No UI openable OR User has exited the application", Log.Type.WARNING);
         }
      }
      catch (Exception e)
      {
         createLog(e, Log.Type.ERROR);
         createLog("UI failed to create", Log.Type.WARNING);
      }
   }
   
   //////////////////// Extra functionality ////////////////////
   
   ////////// Logging //////////
   public void createLog(String message, Log.Type logType)
   {
      file.appendToFile((new Log(message, logType)).toString(), this.printLogsToConsole);
   }
   public void createLog(Exception e, Log.Type logType)
   {
      file.appendToFile(new Log(e, logType).toString(), this.printLogsToConsole);
   }
   
   ////////// Music //////////
   public void playMusic()
   {
      try
      {
         Musik yes = new Musik(config.getProperty("musicFile"));
         yes.play();
         createLog("Playing " + config.getProperty("musicFile"), Log.Type.INFO);
      }
      catch (Exception e)
      {
         createLog("Unable to play Music", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   public void stopMusic()
   {
      try
      {
         Musik yes = new Musik(config.getProperty("musicFile"));
         yes.stop();
         createLog("Stopping " + config.getProperty("musicFile"), Log.Type.INFO);
      }
      catch (Exception e)
      {
         createLog("Unable to stop Music. Og god what have we done", Log.Type.WARNING);
         createLog(e, Log.Type.ERROR);
      }
   }
   
   ////////// minor Getters and Setter //////////
   
   public boolean getInitStatus()
   {
      return this.isInitiatedProperly;
   }
   
   public void setSaveToFile(Boolean state)
   {
      this.saveToFile = state;
   }
}