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
    while (true)
    {
      grid.pause(100); //pause the game for 100 miliseconds
      handleKeyPress(); // tests if a key was pressed
      int level = 600;
      if(timesGet%100 ==0){
          level -=100;
      }
      
      if (msElapsed % level == 0) //  scroll and populates left every 300 ms?
      {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;// increases in steps of 100
      if(isGameOver()){
        updateTitle();
        break;
      }
    }
  }
  
  public void handleKeyPress()
  {
    int key = grid.checkLastKeyPressed();
    if (key == 40 && userRow< (grid.getNumRows()-1)){ // arrow down
      handleCollision(new Location(userRow,0),"arrowDown");
      grid.setImage(new Location(userRow,0), null);
      userRow+=1;
      grid.setImage(new Location(userRow,0), "user.gif");
    }
    else if (key == 38 && userRow>0){ //arrow up
      handleCollision(new Location(userRow,0),"arrowUp");
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
              handleCollision(new Location(i,j),"get.gif") ;
              if(nextimageLoc == null || !nextimageLoc.equals("user.gif")){
                grid.setImage(new Location(i,j-1), "get.gif"); 
              }
            }
            grid.setImage(new Location(i,j), null);
          }
          else if(j>=0 &&imageName.equals("avoid.gif")){
            if(j-1>=0 ){
              nextimageLoc = grid.getImage(new Location(i,j-1));
              handleCollision(new Location(i,j),"avoid.gif");
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
  
  public void handleCollision(Location loc, String origin)
  {
    //origin will be "arrowUp", "arrowDown","get.gif","avoid.gif"
    // the method will receive the location 'loc' and the origin and operate accordingly 
    String image = grid.getImage(loc);
    Location nexLocation;
    
    if(origin.equals("arrowDown")){
      System.err.println("location: ("+loc.getRow()+","+loc.getCol()+")");
      nexLocation = new Location(loc.getRow()+1,loc.getCol());
      
    }else if(origin.equals("arrowUp")){
      nexLocation = new Location(loc.getRow()-1,loc.getCol());
    }
    else{
      nexLocation = new Location(loc.getRow(),loc.getCol()-1);
    }
    String nextimage = grid.getImage(nexLocation);
   if(origin.equals("arrowDown")){
      if (nextimage!=null && nextimage.equals("get.gif")){
        timesGet++;
      }
      else if(nextimage!=null && nextimage.equals("avoid.gif")){
        timesAvoid++;
      }
    }
    else if(origin.equals("arrowUp")){
      if (nextimage!=null && nextimage.equals("get.gif")){
        timesGet++;
      }
      else if(nextimage!=null && nextimage.equals("avoid.gif")){
        timesAvoid++;
      }
    }
    else if(nextimage != null && nextimage.equals("user.gif") && image.equals("get.gif")){
      System.out.println("collition");
      timesGet++;            
    }else if(nextimage != null && nextimage.equals("user.gif") && image.equals("avoid.gif")){
      timesAvoid+=1;
      System.out.println("times avoid: "+ timesAvoid);
    }
    else{
      timesGet++;
    }
  }
  
  public int [] getScore()
  {
    int [] score = {timesGet, timesAvoid};
    return score;
  }
  
  public void updateTitle()
  {
    if(isGameOver()){
      grid.setTitle("Game Over :( Score: " + getScore()[0]);  
    }
    else{
      grid.setTitle("Score: " + getScore()[0]+"  Lives: "+ (4-getScore()[1]));
    }

  }
  
  public boolean isGameOver()
  {
    return timesAvoid>=4;
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