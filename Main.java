// Main, does nothing but call MF or run tests

public class Main
{
   private final static boolean RUN_UNIT_TESTS = false;
   private final static boolean PRINT_DETAILS = false;
   
   public static void main(String [] args)
   {
      if(RUN_UNIT_TESTS)
      {
         UnitTests test = new UnitTests(PRINT_DETAILS);
         test.runTests();
      }
      else
      {
         MainFrame hotel = new MainFrame(true, PRINT_DETAILS, PRINT_DETAILS);
         hotel.init();
         //if(hotel.getInitStatus())
         //{
            hotel.playMusic();
            hotel.openCLI();
         //}
      }
   }
}