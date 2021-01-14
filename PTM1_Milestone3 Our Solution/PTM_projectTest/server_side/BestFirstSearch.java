// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.util.*;
import java.util.stream.Collectors;

public class BestFirstSearch implements Searchable {

	// Methods
    @Override
    public List<String> search(MazeProblem mazeProblem) {
        Queue<Node> queue = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();

        int rowSize = mazeProblem.getMaze().length;
        int colSize = mazeProblem.getMaze()[0].length;
        int startRow = mazeProblem.getStartRow();
        int startCol = mazeProblem.getStartColumn();
        int finishRow = mazeProblem.getFinishRow();
        int finishCol = mazeProblem.getFinishColumn();
        int[][] maze = mazeProblem.getMaze();

        Node startNode = new Node(startRow, startCol, maze[startRow][startCol], "none");
        Node finishNode = new Node(finishRow, finishCol, maze[finishRow][finishCol], "none");

        List<Node> cheapestPath = new ArrayList<>();

        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {

            Node currentNode = queue.poll();
            Queue<Node> neighboursQueue = new PriorityQueue<>();

            // initialize neighbors
            Node upNode = null;
            Node downNode = null;
            Node leftNode = null;
            Node rightNode = null;

            if (currentNode.getRow() - 1 >= 0) {
                upNode = new Node(currentNode.getRow() - 1, currentNode.getCol(),
                        maze[currentNode.getRow() - 1][currentNode.getCol()], "Up");
            }
            if (currentNode.getRow() + 1 < rowSize) {
                downNode = new Node(currentNode.getRow() + 1, currentNode.getCol(),
                        maze[currentNode.getRow() + 1][currentNode.getCol()], "Down");
            }
            if (currentNode.getCol() - 1 >= 0) {
                leftNode = new Node(currentNode.getRow(), currentNode.getCol() - 1,
                        maze[currentNode.getRow()][currentNode.getCol() - 1], "Left");
            }
            if (currentNode.getCol() + 1 < colSize) {
                rightNode = new Node(currentNode.getRow(), currentNode.getCol() + 1,
                        maze[currentNode.getRow()][currentNode.getCol() + 1], "Right");
            }

            // add neighbours
            if (upNode != null && !visited.contains(upNode)) {
                neighboursQueue.add(upNode);
                visited.add(upNode);
            }
            if (downNode != null && !visited.contains(downNode)) {
                neighboursQueue.add(downNode);
                visited.add(downNode);
            }
            if (leftNode != null && !visited.contains(leftNode)) {
                neighboursQueue.add(leftNode);
                visited.add(leftNode);
            }
            if (rightNode != null && !visited.contains(rightNode)) {
                neighboursQueue.add(rightNode);
                visited.add(rightNode);
            }

            Node cheapestNeighbour = neighboursQueue.poll();
            cheapestPath.add(cheapestNeighbour);

            if (cheapestNeighbour.equals(finishNode)) {
                break;
            }

            queue.add(cheapestNeighbour);
        }

        return cheapestPath.stream().map(Node::getMovedSide).collect(Collectors.toList());
    }
}
