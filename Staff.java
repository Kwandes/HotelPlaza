import java.util.*;
public class Staff extends User implements UserManagement
{
   private int hours;           //pr week
   private double salary;       //pr hour
   private int vacation;        //pr year
   
      //Constructer 
   
   public Staff () {} 
      
   public Staff (String firstName, String lastName, String cpr, String type, 
                 String[] address, String phoneNr, String password, String ID, 
                 int hours, double salary, int vacation) 
   {
      this.firstName = firstName;
      this.lastName = lastName;
      this.cpr = cpr;
      this.type = type;
      this.address = address;
      this.phoneNumber = phoneNr;
      this.password = password;
      this.hours = hours;
      this.salary = salary;
      this.vacation = vacation; 
      this.accessLevel = calculateAccessLevel();
      this.ID = ID;
   }
   
   // Explicit Access Level for that sweet security Override and potential backdoor
   public Staff (String firstName, String lastName, String cpr, String type, 
                 String[] address, String phoneNr, String password, String ID, 
                 int hours, double salary, int vacation, int accessLevel) 
   {
      this.firstName = firstName;
      this.lastName = lastName;
      this.cpr = cpr;
      this.type = type;
      this.address = address;
      this.phoneNumber = phoneNr;
      this.password = password;
      this.hours = hours;
      this.salary = salary;
      this.vacation = vacation; 
      this.accessLevel = accessLevel;
      this.ID = ID;
   }
      //Methods
      
   public String staffRepportToString () 
   {
      return    "\tFull name           : " + firstName + " " + lastName +
                "\n\tCpr number          : " + cpr +
                "\n\tAddress             : " + address[0] + ", " + address[1] + ", " + address[2] +   
                "\n\tHours a week        : " + hours + 
                "\n\tSalary pr hour      : " + salary +
                "\n\tVacation days pr/y  : " + vacation + 
                "\n\tMonthly salary      : " + getSalaryMonth();
   }
   
   //@Override
   public void getUserInformation(String ID)
   {
   
   }
   
   //@Override
   public void printStaffReport(User user)
   {
      System.out.println(ID + " " + accessLevel + " " + hours + " " + salary + " " + vacation);
   }
   
   public String fileFormatString ()
   {
      return firstName + " | " + lastName + " | " + cpr + " | " + type + " | " + address[0] + " | " +
             address[1] + " | " + address[2] + " | " + phoneNumber + " | " + password + " | " + ID + " | " + 
             accessLevel + " | " + hours + " | " + salary + " | " + vacation; 
   } 
   
      //Setters
   public int calculateAccessLevel()
   {
      for(int i = 0; i < TYPE.length; i++)
      {
         if( TYPE[i].equals(this.type)) 
            return i;
      }
      return 0;
   }
   
   public void setType (String type) 
   {
      this.type = type; 
   }
   
   public void setHours (int hours) 
   {
      this.hours = hours;
   }
   
   public void setSalary (double salary) 
   {
      this.salary = salary;
   }
   
   public void setVacation (int vacation) 
   {
      this.vacation = vacation;
   }
   
   public void setID (String ID)
   {
      this.ID = ID;
   }
   
   public void setPhoneNumber (String phoneNumber)
   {
      this.phoneNumber = phoneNumber;
   }
   
      //Getters
      
   public String getPhoneNumber()
   {
      return phoneNumber;
   }
   
   public int getHours () 
   {
      return hours;
   }
   
   public double getSalary () 
   {
      return salary;
   }
   
   public double getSalaryMonth () 
   {
      return ((hours * salary) * 4);
   }
   
   public int getVacation () 
   {
      return vacation;
   }
   
   public int getAccessLevel()
   {
      return accessLevel;
   }
   
   public String getID () 
   {
      return ID;
   }
}