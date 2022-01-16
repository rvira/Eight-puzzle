package collegeexpts;
import java.util.*;

class Node {

	public Node parent;
	public int[][] m;
	
	public int x, y;        //Blank tile coordinates
	public int cost;	// Number of misplaced tiles	
	public int level;       // The number of moves so far
	
	
	public Node(int[][] m, int x, int y, int newX, int newY, int level, Node parent) {
		this.parent = parent;
		this.m = new int[m.length][];
		for (int i = 0; i < m.length; i++) {
			this.m[i] = m[i].clone();
		}
		
		// Swap value
		this.m[x][y]       = this.m[x][y] + this.m[newX][newY];
		this.m[newX][newY] = this.m[x][y] - this.m[newX][newY];
		this.m[x][y]       = this.m[x][y] - this.m[newX][newY];
		
		this.cost = Integer.MAX_VALUE;
		this.level = level;
		this.x = newX;
		this.y = newY;
	}
	
}


public class eightpuzzleprob {
	
	public int dimension = 4;
	
	// Bottom, left, top, right
	int[] row = { 1, 0, -1, 0 };
	int[] col = { 0, -1, 0, 1 };
	
	public int cost(int[][] initial, int[][] goal) {
		int count = 0;
		int n = initial.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
        //function to print matrix
	public void printMatrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}
	
        //check boundaries
	public boolean isSafe(int x, int y) {
		return (x >= 0 && x < dimension && y >= 0 && y < dimension);
	}
	
        //print matrix with minimum cost
	public void printMin(Node root) {
		if (root == null) {
			return;
		}
		printMin(root.parent);
		printMatrix(root.m);
		System.out.println();
	}
	
        //function to check given matrix is solvable
	public boolean isSolvable(int[][] m) {
		int count = 0;
		List<Integer> array = new ArrayList<Integer>();
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				array.add(m[i][j]);
			}
		}
		
		Integer[] arr = new Integer[array.size()];
		array.toArray(arr);
		
                
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] != 0 && arr[j] != 0 && arr[i] > arr[j]) {
					count++;
				}
			}
		}
		return count % 2 == 0;
	}
	
	public void solve(int[][] initial, int[][] goal, int x, int y) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(1000, (a, b) -> (a.cost + a.level) - (b.cost + b.level));
		Node root = new Node(initial, x, y, x, y, 0, null);
		root.cost = cost(initial, goal);
		pq.add(root);
		
		while (!pq.isEmpty()) {
			Node min = pq.poll();
			if (min.cost == 0) {
				printMin(min);
				return;
			}
			
			for (int i = 0; i < 4; i++) {
                        if (isSafe(min.x + row[i], min.y + col[i])) {
                            Node child = new Node(min.m, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                            child.cost = cost(child.m, goal);
                            pq.add(child);
                        }
                    }
		}
	}
	
	public static void main(String[] args) {
		int[][] initial = { { 1, 2, 3, 4 }, { 5, 6, 0, 8 }, { 9, 10, 7, 11 }, { 13, 14, 15, 12 } };
		int[][] goal = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
		
		// White tile coordinate
		int x = 1, y = 2;
		
		eightpuzzleprob puzzle = new eightpuzzleprob();
                			
                System.out.println("Initial matix: ");
		if (puzzle.isSolvable(initial)) {
                    puzzle.solve(initial, goal, x, y);
		} 
		else {
                    puzzle.printMatrix(initial);
		    System.out.println("The given initial is impossible to solve");
		}
	}
}