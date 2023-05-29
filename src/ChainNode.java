public class ChainNode {
    private Object chainNode;
    private ChainNode down;
    private NumberNode right;

    public ChainNode(Object dataToAdd) {
        chainNode = dataToAdd;
        down = null;
        right = null;
    }

    public Object getChainNode() {
        return chainNode;
    }

    public void setChainNode(Object dataToAdd) {
        chainNode = dataToAdd;
    }

    public ChainNode getDown() {
        return down;
    }

    public void setDown(ChainNode down) {
        this.down = down;
    }

    public NumberNode getRight() {
        return right;
    }

    public void setRight(NumberNode right) {
        this.right = right;
    }
}
