import java.util.Arrays;

class Maze implements Cloneable {

    private final char[] mazeSymbols = { '#', '.', '+', '*'};
    // use symbols to make reading the output easier...
    // 0 - obstacle - '#'
    // 1 - open space - '.'
    // 2 - path taken - '+'
    // 3 - goal - '*'
    private final int[][] maze;

    // 0 - obstacle
    // 1 - open space
    // 2 - path taken
    // 3 - goal
    private final static int[][] DEFAULT_MAZE =
            {{0, 0, 1, 1, 1, 1, 1, 1},
                    {2, 0, 1, 0, 0, 0, 1, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 1, 3, 1, 1},
                    {0, 0, 1, 0, 1, 0, 0, 1},
                    {1, 0, 1, 1, 1, 0, 0, 0},
                    {1, 1, 1, 0, 1, 1, 0, 0}};

    public Maze(int[][] maze) {
        this.maze = maze;
    }

    public Maze() {
        this(DEFAULT_MAZE);
    }


    public Maze clone(){
        int[][] clonedMaze = new int[maze.length][maze[0].length];
        for(int i=0; i<maze.length; i++){
            clonedMaze[i] = Arrays.copyOf(maze[i], maze[i].length);
        }
        return new Maze(clonedMaze);
    }

    // A position is available if: (1) it is inside the bounds of the maze
    // (2) if it is an open space or (3) it is the goal
    private boolean isAvailablePosition(int x, int y) {
        return x >=0 && x<maze.length && y >=0 && y < maze[0].length && (maze[x][y]==3 || maze[x][y]==1);
    }

    public boolean findPathFrom(int x, int y) {
        // when we reach the goal we have solved the problem
        if(maze[x][y]==3) { return true; }

        // add the position to our path changing its value to '2'
        maze[x][y] = 2;

        //try all available neighbours
        //West (row, col-1), east (row, col+1), South (row+1, col) and North (row-1, col)
        // if any of these return true then we have solved the problem
        if(isAvailablePosition(x-1, y) && findPathFrom(x-1, y)) {
            return true;
        }
        if(isAvailablePosition(x+1, y) && findPathFrom(x+1, y)) {
            return true;
        }
        if(isAvailablePosition(x, y+1) && findPathFrom(x, y+1)) {
            return true;
        }
        if(isAvailablePosition(x, y-1) && findPathFrom(x, y-1)) {
            return true;
        }

        //If none of previous positions is valid or matches the goal, it is necessary to revert the
        //temporary state. This reversal or backtrack is what give name to the algorithm: backtracking
        maze[x][y] = 1;

        return false;
    }

    public void print() {
        for(int row = 0; row < maze.length; ++row) {
            for(int col = 0; col < maze[row].length; ++col) {
                System.out.print(mazeSymbols[maze[row][col]]);
            }
            System.out.println();
        }
    }

    public void print(char footprint){
        setFootprint(footprint);
        print();

    }
    private void setFootprint(char footprint){
        mazeSymbols[2] = footprint;
    }

}