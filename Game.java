import java.util.Random;
public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  
  public Game()
  {
    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), "user.gif");
  }
  
  public void play()
  {
    while (!isGameOver())
    {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0)
      {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
  }
  
  public void handleKeyPress()
  {
    int key = grid.checkLastKeyPressed();
    if (key == 40 && userRow< (grid.getNumRows()-1)){
      grid.setImage(new Location(userRow,0), null);
      userRow+=1;
      
      grid.setImage(new Location(userRow,0), "user.gif");
    }
    else if (key == 38 && userRow>0){
      grid.setImage(new Location(userRow,0), null);
      userRow-=1;
      grid.setImage(new Location(userRow,0), "user.gif");
    }
  }
  
  public void populateRightEdge()
  {
    
      int randomInt = (int) ( Math.random() * grid.getNumRows());
      int onOff= (int)(Math.random()+0.5);
      int quantity = (int)(Math.random()+1);
      System.out.println(onOff);
      Location RightMostloc =new Location(randomInt,grid.getNumCols()-1);
      if(onOff>0){
        
        grid.setImage(RightMostloc, "get.gif");
        
      }else {
        
        grid.setImage(RightMostloc, "avoid.gif");
      }
    
    
  }
  
  public void scrollLeft()
  {
    for(int i =0; i< grid.getNumRows();i++){
      for(int j=0; j< grid.getNumCols(); j++){
        String imageName = grid.getImage(new Location(i,j));
        String nextimageLoc;
        if(imageName != null){
          if(j>=0 && imageName.equals("get.gif")){
            if(j-1>=0){
              nextimageLoc = grid.getImage(new Location(i,j-1));
              if(nextimageLoc == null || !nextimageLoc.equals("user.gif")){
                grid.setImage(new Location(i,j-1), "get.gif"); 
              }
            }
            grid.setImage(new Location(i,j), null);
          }
          else if(j>=0 &&imageName.equals("avoid.gif")){
            if(j-1>=0 ){
              nextimageLoc = grid.getImage(new Location(i,j-1));
              if(nextimageLoc == null || !nextimageLoc.equals("user.gif")){
                grid.setImage(new Location(i,j-1), "avoid.gif"); 
              } 
            }
            grid.setImage(new Location(i,j), null);   
          }
        }
      }
    }  
  }
  /*  public int [] helper(int r, int c){
    //for(int i=0; i<grid.getNumCols();i++){
      
    //}
  }*/
  public void handleCollision(Location loc)
  {
  }
  
  public int getScore()
  {
    return 0;
  }
  
  public void updateTitle()
  {
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver()
  {
    return false;
  }
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args)
  {
    test();
  }
}