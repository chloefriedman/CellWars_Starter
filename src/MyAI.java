/**
 * STUDENT FILE
 *
 * Name: Chloe Friedman
 * AI Code Name: unicornAI
 *
 * Strategy Description:
 * Replace this comment with a short explanation of the strategy your AI uses.
 * Your final strategy must be fundamentally different from the sample AIs.
 */
public class MyAI extends CellAI {

    @Override
    public String getAIName() {
        return "unicornAI";
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

    public Location attack(Grid grid)
    {

    }

    public Location defend(Grid grid)
    {
        int numKills = 0;
        int[][] current = new int[grid.getRows()][grid.getCols()];
        int[][] next = new int[grid.getRows()][grid.getCols()];
        int[][] compare = new int[grid.getRows()][grid.getCols()];
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                current[i][j] = grid.getCell(i,j);
                next[i][j] = grid.getCell(i,j);
            }
        }
        for(int i = 0; i < current.length; i++)
        {
            for(int j = 0; j < current[0].length; j++)
            {
                if(current[i][j] != -1 && current[i][j] != getID())
                {
                    int mostCommon = GridFunctions.mostCommonNeighbor(i,j,grid);
                    if(mostCommon != -1 && mostCommon == current[i][j])
                    {
                        next[i][j] = -1;
                        nextGen(next, grid, new Location(i,j));
                    }
                    else
                    {
                        next[i][j] = current[i][j];
                    }
                }
                else
                {
                    next[i][j] = -1;
                }
            }
        }

    }
    public Location oneOffSquare(Grid grid)
    {
        int myID = getID();
        int start = (int)(Math.random()*2);
        if(start == 0)
        {
            for(int i = 0; i < grid.getRows(); i++)
            {
                for(int j = 0; j < grid.getCols(); j++)
                {
                    if(GridFunctions.getNeighbors(i, j,grid) == 1 && grid.getCell(i,j) == myID)
                    {
                        for (int r = i - 1; r <= i + 1; r++) 
                        {
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
                                        {
                                            if(r != grid.getRows())
                                            {
                                                return new Location(r-1,c);
                                            }
                                            else
                                            {
                                                return new Location(r+1,c);
                                            }
                                        }
                                        else if(c == j)
                                        {
                                            if(c != grid.getCols())
                                            {
                                                return new Location(r,c+1);
                                            }
                                            else
                                            {
                                                return new Location(r, c-1);
                                            }
                                        }
                                        else if(c > j)
                                        {
                                            return new Location(r,c+1);
                                        }
                                        else
                                        {
                                            return new Location(r,c-1);
                                        }
                                    
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            for(int i = grid.getRows()-1; i >=0; i--)
            {
                for(int j = grid.getCols()-1; j >= 0; j--)
                {
                    if(GridFunctions.getNeighbors(i, j,grid) == 1 && grid.getCell(i,j) == myID)
                    {
                        for (int r = i - 1; r <= i + 1; r++) 
                        {
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
                                        {
                                            if(r != grid.getRows())
                                            {
                                                return new Location(r-1,c);
                                            }
                                            else
                                            {
                                                return new Location(r+1,c);
                                            }
                                        }
                                        else if(c == j)
                                        {
                                            if(c != grid.getCols())
                                            {
                                                return new Location(r,c+1);
                                            }
                                            else
                                            {
                                                return new Location(r, c-1);
                                            }
                                        }
                                        else if(c > j)
                                        {
                                            return new Location(r,c+1);
                                        }
                                        else
                                        {
                                            return new Location(r,c-1);
                                        }
                                    
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
            return new Location(-1,-1);

    }

    public Location beeHive(Grid grid)
    {
        int myID = getID();
        for(int i = 0; i < grid.getRows()-2; i++)
        {
            for(int j = 0; j < grid.getCols()-3; j++)
            {
                int count = 0;
                Location missing = new Location(-1,-1);
                if(grid.getCell(i,j) == -1)
                {
                    if(grid.getCell(i,j+1) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i,j+1);
                    }
                    if(grid.getCell(i,j+2) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i,j-1);
                    }
                    if(grid.getCell(i+1,j) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i+1,j);
                    }
                    if(grid.getCell(i+1,j+3) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i+1,j+3);
                    }
                    if(grid.getCell(i+2,j+1) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i+2,j+1);
                    }
                    if(grid.getCell(i+2,j+2) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i+2,j+2);
                    }
                    if(count == 5)
                    {
                        return missing;
                    }
                }
            }
        }
        return new Location(-1, -1);
    }

    public void nextGen(int[][] next, Grid grid, Location loc)
    {
        Grid updatedGrid = new Grid(next);

        
        
    }
    




    
}

