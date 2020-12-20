package dev.gallon.algorithms;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

    /**
     * @param x the int value (decimal format)
     * @return the first bit set position
     */
    public static int ffs(int x) {
        return (int)((Math.log10(x & -x)) / Math.log10(2)) + 1;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public static int posDiff(int x, int y) {
        int p = ffs(x^y) - 1;
        return (p<<1) | ((x>>p) & 1);
    }

    /**
     * @param nodes
     * @param except a node to remove (null to ignore)
     * @return the first free ID in the current topology
     */
    public static int firstFree(List<Node> nodes, Node except) {
        return nodes.stream()
                .filter((node) -> !node.equals(except))
                .mapToInt(Node::getID)
                .collect(BitSet::new, BitSet::set, BitSet::or).nextClearBit(0);
    }

    /**
     *
     * @param nodes the nodes
     * @param except the node to remove from the node list (null -> ignored)
     * @param min the min id
     * @param max the max id
     * @return the first available id that is not taken by the given nodes in the [min; max] interval. If not fund,
     * returns -1.
     */
    public static int firstFree(List<Node> nodes, Node except, int min, int max) {
        HashMap<Integer, Boolean> takenMap = new HashMap<>();
        for (Node node : nodes) {
            if (node.equals(except)) continue;
            takenMap.put(node.getID(), true);
        }

        for (int id = min; id <= max; id++) {
            if (!takenMap.getOrDefault(id, false)) return id;
        }

        return -1;
    }

    public static Color getColorFromInt(int id) {
        return Color.getIndexedColors().get(id);
    }
}
