package Dijkstra;

/**
 * Created by Acer Customer on 5/28/2017.
 */
public class Heap {
    private Node[] nodes;
    private int[] nodeIndices;
    private int openIndex;

    public Heap(int sourceKey, int[] cityKeys){
        nodes = new Node[cityKeys.length];
        nodeIndices = new int[nodes.length];

        for(int i = 0; i < cityKeys.length; i++){
            nodeIndices[i] = i;

            if(cityKeys[i] == sourceKey){
                nodes[i] = new Node(cityKeys[i], 0);
                reheapifyUp(i);
            }
            else{
                nodes[i] = new Node(cityKeys[i], Integer.MAX_VALUE);

            }

            this.openIndex++;
        }
    }

    public void reheapifyUp(int index){
        if(index == 0){
            return;
        }
        else{
            int parent = (index - 1)/2;
            System.out.println("Parent = " + parent);
            if(this.nodes[index].getDistanceTo() < this.nodes[parent].getDistanceTo()){
                swapNodes(index, parent);
                reheapifyUp(parent);
            }
        }
    }

    public void reheapifyDown(int index){
        int left = 2*index + 1;
        int right = 2*index + 2;

        // if right & left child exists
        if(right < this.openIndex){
            // check both
            if(this.nodes[left].getDistanceTo() < this.nodes[right].getDistanceTo()){
                if(this.nodes[index].getDistanceTo() > this.nodes[right].getDistanceTo()){
                    swapNodes(index, right);
                    reheapifyDown(right);
                }
            }
            else if(this.nodes[index].getDistanceTo() > this.nodes[left].getDistanceTo()){
                swapNodes(index, left);
                reheapifyDown(left);
            }
        }
        // if only left child exists
        else if(left < this.openIndex){
            // only check left
            if(this.nodes[index].getDistanceTo() > this.nodes[left].getDistanceTo()){
                swapNodes(index, left);
                reheapifyDown(left);
            }
        }
        // node at index has no children (base case)
        else {
            // neither child exists
            return;
        }

        // by the rules of a complete tree, if there is no left child,
        // the node has reached the maximum depth of the tree
    }

    private void swapNodes(int a, int b){
        Node tempNode = this.nodes[a];
        this.nodes[a] = this.nodes[b];
        this.nodes[b] = tempNode;

        this.nodeIndices[nodes[a].getCityKey() - 1] = a;
        this.nodeIndices[nodes[b].getCityKey() - 1] = b;
    }

    public void printNodes(){
        for(Node node: nodes){
            System.out.println("City Key: " + node.getCityKey() + " distanceTo: " + node.getDistanceTo());
        }
    }

    public void printNodeIndices(){
        for(int i = 0; i < this.nodeIndices.length; i++){
            System.out.println("City Key: " + (i + 1) + " Heap index: " + this.nodeIndices[i]);
        }
    }

    public Node pop(){
        this.openIndex--;

        Node frontNode = nodes[0];
        nodes[0] = nodes[openIndex];

        return frontNode;
    }

    public int getSize(){
        return this.nodes.length;
    }

    public void updateDistance(int source, int destination, int distanceToDestination){
        source = source - 1;
        destination = destination - 1;

        int sourceLocationInHeap = nodeIndices[source];
        int destLocationInHeap = nodeIndices[destination];

        int newDistance = nodes[sourceLocationInHeap].getDistanceTo() + distanceToDestination;

        System.out.println("Source Location: " + sourceLocationInHeap);
        System.out.println("Destination Location: " + destLocationInHeap);
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("distance to Predecessor: " + nodes[sourceLocationInHeap].getDistanceTo());
        System.out.println("distance to new city: " + distanceToDestination);

        if(newDistance < nodes[destLocationInHeap].getDistanceTo()){
            nodes[destLocationInHeap].setDistanceTo(newDistance);
        }
    }
}
