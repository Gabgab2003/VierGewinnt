public class VierGewinnt {
    private int columns = 7;
    private int rows = 5;
    private Team[][] board;

    VierGewinnt() {
        board = new Team[columns][rows];
    }

    public int place(int col, Team team) {
        if(board[col][0]!=null) {
            return -1;
        }
        if(board[col][rows-1]==null) {
            board[col][rows-1] = team;
            return rows-1;
        }
        for (int i=1;i<rows;i++) {
            if(board[col][i]!=null) {
                board[col][i-1] = team;
                return i-1;
            }
        }
        return -1;
    }

    private boolean areAllEqual(Team... teams) {
        if(teams[0] == null) {
            return false;
        }
        Team checkValue = teams[0];
        for (int i = 1; i < teams.length; i++)
        {
            if(teams[i] == null) {
                return false;
            }
            if (teams[i] != checkValue)
            {
                return false;
            }
        }
        return true;
    }

    public boolean win(int x, int y) {
        for (int i=0;i<4;i++) {
            //check horizontal
            try {
                if(areAllEqual(getBoard()[x-i][y],getBoard()[x-i+1][y],getBoard()[x-i+2][y],getBoard()[x-i+3][y])) {
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {}
            //check vertical
            try {
                if(areAllEqual(getBoard()[x][y-i],getBoard()[x][y-i+1],getBoard()[x][y-i+2],getBoard()[x][y-i+3])) {
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {}
            try {
                if(areAllEqual(getBoard()[x-i][y-i],getBoard()[x-i+1][y-i+1],getBoard()[x-i+2][y-i+2],getBoard()[x-i+3][y-i+3])) {
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {}
            try {
                if(areAllEqual(getBoard()[x-i][y+i], getBoard()[x-i+1][y+i-1], getBoard()[x-i+2][y+i-2], getBoard()[x-i+3][y+i-3])) {
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {}
        }
        return false;
    }

    public Team[][] getBoard() {
        return board;
    }
    public int getColumns() {
        return columns;
    }
    public int getRows() {
        return rows;
    }
}
