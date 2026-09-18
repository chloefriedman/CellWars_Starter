/**
 * STUDENT FILE
 *
 * Name: Chloe Friedman
 * AI Code Name: ______________________
 *
 * Strategy Description:
 * Replace this comment with a short explanation of the strategy your AI uses.
 * Your final strategy must be fundamentally different from the sample AIs.
 */
public class MyAI extends CellAI {

    @Override
    public String getAIName() {
        return "MyAI - CHANGE ME";
    }

    @Override
    public Location select(Grid grid) {
        /*
         * Replace this starter strategy.
         *
         * Helpful information:
         *   getID()                     -> your cell ID
         *   grid.getRows()              -> number of rows
         *   grid.getCols()              -> number of columns
         *   grid.getCell(r, c)          -> -1 if dead, otherwise an AI ID
         *   GridFunctions.getNeighbors  -> number of living neighbors
         *   GridFunctions.mostCommonNeighbor -> most common neighboring AI
         *   randomInt(bound)            -> reproducible random integer
         */
        int row = 0;
        int col = 0;
        for(int r = 0; r < grid.getRows(); r++) {
            for(int c = 0; c < grid.getCols(); c++) 
            {
                
            }
        }
        return new Location(row,col);
    }

    public static Location offense(Grid grid)
    {

    }

    public static Location defense(Grid grid)
    {
        
    }
    public static Location oneOffSquare(Grid grid)
    {
        int myID = getID();
        int start = (int)(Math.random()*2);
        if(start == 0)
        {
            for(int i = 0; i < grid.length(); i++)
            {
                for(int j = 0; j < grid[0].length(); j++)
                {
                    if(grid[i][j].getNeighbors() == 1 && grid[i][j] == myID)
                    {
                        return new Location(i,j);
                    }
                }
            }
        }
        else
        {
            for(int i = grid.length-1; i >=0; i--)
            {
                for(int j = grid[0].length; j >= 0; j--)
                {
                    if(grid[i][j].getNeighbors() == 1 && grid[i][j] == myID)
                    {
                        return new Location(i,j);
                    }
            }
        }
        }
            return new Location(-1,-1);

    }




    public static Location finishSquare(Grid grid)
    {
        if(oneOffSquare().getRow() == -1)
        {
            return new Location(-1,-1);
        }
        int myID = getID();
        int start = (int)(Math.random()*2);
        
        for(int i = 0; i < grid.length(); i++)
        {
            for(int j = 0; j < grid[0].length(); j++)
            {
                if(grid[i][j].getNeighbors() == 1 && grid[i][j] == myID)
                {
                     for (int r = i - 1; r <= i + 1; r++) {
                         for (int c = j - 1; c <= j + 1; c++) 
                        {
                             if (r == i && c == j)
                             {
                                 continue;
                             }

                            if (r >= 0 && c >= 0 && r < grid.getRows() && c < grid.getCols())
                            {
                                if (grid.getCell(r, c) != -1) 
                                {
                                    
                                    if(r == i)
                                    
                                }
                            }
                            else
                        }
        }
                }
            }
        }
    }
}
