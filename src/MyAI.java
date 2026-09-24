/**
 * STUDENT FILE
 *
 * Name: Chloe Friedman
 * AI Code Name: unicornAI
 *
 * Strategy Description:
 *unicornAI asseses if it has more cells than the enemy and if it does it will focus on killing enemy cells. If it has less cells than the enemy, unicornAI will focus on defending its own cells. If it has the same amount of cells as the enemy, unicornAI will focus on killing enemy cells.
  unicornAI will kill cells by looking for patterns including oscillators and squares to be overpopulated. If there are none, unicornAI will suggest that the AI should kill the cell that will kill the most enemy cells in the next generation
 unicornAI expands by looking for patterns including squares, beehives, and gliders to create. If there are none, unicornAI suggests that the AI should kill cells instead
 if both the attack and defend methods return a location of -1, -1 (no patterns or opportunities), unicornAI will suggest that the AI should place a cell in a random location
 * 
 * 
 *
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
        int enemyCount = countEnemyCells(grid);
        int myCount = countMyCells(grid);
        if(enemyCount < myCount && attack(grid).getRow() != -1)
        {
            return attack(grid);
        }
        else if(myCount < enemyCount && defend(grid).getRow() != -1)
        {
            return defend(grid);
        }
        else if(attack(grid).getRow() == -1 && defend(grid).getRow() != -1)
        {
            return defend(grid);
        }
        else if(attack(grid).getRow() != -1 && defend(grid).getRow() == -1)
        {
            return attack(grid);
        }
        else if(myCount == enemyCount && attack(grid).getRow() != -1)
        {
            return attack(grid);
        }
        else if (myCount == enemyCount && defend(grid).getRow() != -1)
        {
            return defend(grid);
        }
        else
        {
            return new Location(randomInt(grid.getRows()), randomInt(grid.getCols()));
        }
    }

    public Location defend(Grid grid)
    {
        if(oneOffSquare(grid).getRow() != -1)        
        {
            return oneOffSquare(grid);
        }
        else if(glider(grid).getRow() != -1)
        {
            return glider(grid);
        }
        else if(beeHive(grid).getRow() != -1)
        {
            return beeHive(grid);
        }
        else
        {
            return new Location(-1,-1);
        }
    }

    public Location attack(Grid grid)
    {
        int maxNumKills = 1;
        int currentKills = 0;
        Location bestLocation = new Location(-1,-1);
        int[][] current = new int[grid.getRows()][grid.getCols()];
        int[][] next = new int[grid.getRows()][grid.getCols()];
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                current[i][j] = grid.getCell(i,j);
                next[i][j] = grid.getCell(i,j);
            }
        }
        int startingCount = countEnemyCells(grid);
        for(int i = 0; i < current.length; i++)
        {
            for(int j = 0; j < current[0].length; j++)
            {
                if(current[i][j] != -1 && current[i][j] != getID())
                {
                    int mostCommon = GridFunctions.mostCommonNeighbor(i,j,grid);
                    if(mostCommon == current[i][j])
                    {
                        next[i][j] = -1;
                        next = nextGen(next);
                        int newCount = countEnemyCells(new Grid(next));
                        if(newCount < startingCount)
                        {
                            currentKills = startingCount - newCount;
                            if(currentKills > maxNumKills)
                            {
                                maxNumKills = currentKills;
                                bestLocation = new Location(i,j);
                            }
                            else
                            {
                                next[i][j] = current[i][j];
                                next = nextGen(next);
                            }
                        }
                    }
                
                }
                
            }
        }
        if(maxNumKills > 3)
        {
            return bestLocation;
        }
        else if(killOsc(grid).getRow() != -1)
        {
            return killOsc(grid);
        }
        else if(overPopSquare(grid).getRow() != -1)
        {
            return overPopSquare(grid);
        }
        else if(maxNumKills == 1)
        {
            return new Location(-1,-1);
        }
        else
        {
            return bestLocation;
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

                               else if (r >= 0 && c >= 0 && r < grid.getRows() && c < grid.getCols())
                                {
                                    if (grid.getCell(r, c) != -1) 
                                    {
                                        if(r == i)
                                        {
                                            if(r != grid.getRows()-2)
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
                                            if(c != grid.getCols()-2)
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
                                            if(c < grid.getCols() -1)
                                            {
                                                return new Location(r,c+1);
                                            }
                                            else
                                            {
                                                return new Location(r,c-1);
                                            }
                                        }
                                        else
                                        {
                                            if(c > 0)
                                            {
                                                return new Location(r,c-1);
                                            }
                                            else
                                            {
                                                return new Location(r,c+1);
                                            }
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

                                else if (r >= 0 && c >= 0 && r < grid.getRows() && c < grid.getCols())
                                {
                                    if (grid.getCell(r, c) != -1) 
                                    {
                                        if(r == i)
                                        {
                                            if(r != grid.getRows()-2)
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
                                            if(c != grid.getCols()-2)
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
                                            if(c < grid.getCols() -2)
                                            {
                                                return new Location(r,c+1);
                                            }
                                            else
                                            {
                                                return new Location(r,c-1);
                                            }
                                        }
                                        else
                                        {
                                            if(c > 0)
                                            {
                                                return new Location(r,c-1);
                                            }
                                            else
                                            {
                                                return new Location(r,c+1);
                                            }
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
        for(int i = 0; i < grid.getRows()-3; i++)
        {
            for(int j = 0; j < grid.getCols()-4; j++)
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
                        missing = new Location(i,j+2);
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

    public int[][] nextGen(int[][] next)
    {
        for(int i = 0; i < next.length; i++)
        {
            for(int j = 0; j < next[0].length; j++)
            {
                if(next[i][j] != -1)
                {
                    int neighbors = GridFunctions.getNeighbors(i,j,new Grid(next));
                    if(neighbors < 2 || neighbors > 3)
                    {
                        next[i][j] = -1;
                    }
                }
                else
                {
                    int neighbors = GridFunctions.getNeighbors(i,j,new Grid(next));
                    if(neighbors == 3)
                    {
                        next[i][j] = GridFunctions.mostCommonNeighbor(i,j,new Grid(next));
                    }
                }
            }
        }
        return next;
        
    }

    public int countEnemyCells(Grid grid)
    {
        int count = 0;
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                if(grid.getCell(i,j) != -1 && grid.getCell(i,j) != getID())
                {
                    count++;
                }
            }
        }
        return count;
    }
    public int countMyCells(Grid grid)
    {
        int count = 0;
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                if(grid.getCell(i,j) == getID())
                {
                    count++;
                }
            }
        }
        return count;
    }

    
    public Location glider(Grid grid)
    {
        int count = 0;
        int myID = getID();
        Location missing = new Location(-1,-1);
        for(int i = 0; i < grid.getRows()-2; i++)
        {
            missing = new Location(-1,-1);
            for(int j = 0; j < grid.getCols()-2; j++)
            {
                if(grid.getCell(i,j) == -1 && grid.getCell(i,j+1) == -1 && grid.getCell(i+1,j+1) == -1 && grid.getCell(i+2,j) ==-1)
                {
                    if(grid.getCell(i+1,j) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i+1,j);
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

                    if(grid.getCell(i+1,j+2) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i+1,j+2);
                    }

                    if(grid.getCell(i,j+2) == myID)
                    {
                        count++;
                    }
                    else
                    {
                        missing = new Location(i,j+2);
                    }

                    if(count == 4)
                    {
                        return missing;
                    }
                }
                
            }
        }
        return new Location(-1,-1);
    }

    public Location overPopSquare(Grid grid)
    {
        int myID = getID();
        for(int i = 0; i < grid.getRows() - 2; i++)
        {
            for(int j =0; j < grid.getCols() - 1; j++)
            {
                if(grid.getCell(i,j) != -1 && grid.getCell(i,j) != myID && grid.getCell(i,j+1) != -1 && grid.getCell(i,j+1) != myID && grid.getCell(i+1,j) != -1 && grid.getCell(i+1,j) != myID && grid.getCell(i+1,j+1) != -1 && grid.getCell(i+1,j+1) != myID)
                {
                    if(i != grid.getRows() - 3)
                    {
                        return new Location(i+2,j);
                    }
                    else if(i != 0)
                    {
                        return new Location(i-1,j);
                    }
                }
            }
        }
        return new Location(-1,-1);
    }

    public Location killOsc(Grid grid)
    {
        int myID = getID();
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols() -3; j++)
            {
                if(grid.getCell(i,j) != -1 && grid.getCell(i,j) != myID && grid.getCell(i,j+1) != -1 && grid.getCell(i,j+1) != myID && grid.getCell(i,j+2) != -1 && grid.getCell(i,j+2) != myID)
                {
                    return new Location(i,j);
                }
            }
        }
        for(int i = 0; i < grid.getRows() -3; i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                if(grid.getCell(i,j) != -1 && grid.getCell(i,j) != myID && grid.getCell(i+1,j) != -1 && grid.getCell(i+1,j) != myID && grid.getCell(i+2,j) != -1 && grid.getCell(i+2,j) != myID)
                {
                    return new Location(i,j);
                }
            }
        }
        return new Location(-1,-1);
    }

    
}

