import java.io.*;
import java.util.*;

public class Making_Felix_Felicis {

	static ArrayList<StageNode> inDegZeroNodes = new ArrayList<>();
    static ArrayList<StageNode> topSort = new ArrayList<>();
    
	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputString = null;
		try {
			inputString = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] data = inputString.split(" ");
		
		int numOfNodes = Integer.parseInt(data[0]) + 1;
        int numOfInput = Integer.parseInt(data[1]);
        
        StageNode[] arrayOfNodes = new StageNode[numOfNodes];
        int[] inDegTracker = new int[numOfNodes];
        MinHeap heap = new MinHeap(numOfNodes);
        
        
        for (int i = 0; i < numOfInput; i++) {
        	try {
				inputString = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		data = inputString.split(" ");
			int predecessor = Integer.parseInt(data[0]);
			int successor = Integer.parseInt(data[1]);
						
			StageNode successorNode = new StageNode(successor);
			successorNode.successor = arrayOfNodes[predecessor];
			arrayOfNodes[predecessor] = successorNode;
		}
        
        inDegTracker[0] = -1;
        for (int i = 0; i < numOfNodes; i++) {
        	StageNode currentNode = arrayOfNodes[i];
        	while (currentNode != null) {
        		inDegTracker[currentNode.value]++;
        		currentNode = currentNode.successor;
        	}
        }
        
        for (int i = 0; i < numOfNodes; i++) {
        	if (inDegTracker[i] == 0) {
        		StageNode node = new StageNode(i);
        		heap.insert(node);
        		
        	}
        }
        
        while(heap.currentLength != 0) {
        	StageNode inDegZeroNode = heap.remove();
        	topSort.add(inDegZeroNode);
        	int vertexIndex = 0;
        	vertexIndex = inDegZeroNode.value;
        	
        	StageNode currentNode = arrayOfNodes[vertexIndex];
        	while (currentNode != null) {
        		inDegTracker[currentNode.value]--;
        		if (inDegTracker[currentNode.value] == 0) {
        			StageNode node = new StageNode(currentNode.value);
        			heap.insert(node);
        		}
        		currentNode = currentNode.successor;
        	}
        }
        
        if (topSort.size() == numOfNodes - 1) {
        	for (int i = 0; i < topSort.size(); i++) {
        		System.out.print(topSort.get(i).value + " ");
        	}
        }
        else {
        	System.out.print("-1");
        }
	}
}

class StageNode {
	int value;
	StageNode successor;
	
	StageNode(int value) {
		this.value = value;
	}
}

class MinHeap {
	StageNode[] heap;
	int currentLength;
	
	MinHeap(int capacity) {
		heap = new StageNode[capacity];
		currentLength = 0;
	}
	
	void insert(StageNode node) {
		heap[currentLength] = node;
		int nodeIndex = currentLength;
		currentLength++;
		if (nodeIndex > 0) {
			while (node.value < heap[(nodeIndex - 1)/2].value) {
				StageNode toSwap = heap[(nodeIndex - 1)/2];
				heap[(nodeIndex - 1)/2] = node;
				heap[nodeIndex] = toSwap;
				nodeIndex = (nodeIndex - 1)/2;
			}
		}
	}
	
	StageNode remove() {
		if (currentLength == 0) {
			return null;
		}
		StageNode toReturn = heap[0];
		heap[0] = heap[currentLength - 1];
		heap[currentLength - 1] = null;
		currentLength--;
		int swapIndex = 0;
		while (heap[swapIndex] != null) {
			StageNode leftChild = heap[swapIndex * 2 + 1];
			StageNode rightChild = heap[swapIndex * 2 + 2];
			StageNode parent = heap[swapIndex];
			if (leftChild != null && rightChild != null) {
				if (leftChild.value < rightChild.value) {
					if (heap[swapIndex].value > leftChild.value) {
						heap[swapIndex] = leftChild;
						heap[swapIndex * 2 + 1] = parent;
						swapIndex = swapIndex * 2 + 1;
					}
					else {
						break;
					}
				}
				else {
					if (heap[swapIndex].value > rightChild.value) {
						heap[swapIndex] = rightChild;
						heap[swapIndex * 2 + 2] = parent;
						swapIndex = swapIndex * 2 + 2;
					}
					else {
						break;
					}
				}
			}
			else if (leftChild != null){
				if (heap[swapIndex].value > leftChild.value) {
					heap[swapIndex] = leftChild;
					heap[swapIndex * 2 + 1] = parent;
					swapIndex = swapIndex * 2 + 1;
				}
				else {
					break;
				}
			}
			else {
				break;
			}
		}		
		
		return toReturn;
	}
}
