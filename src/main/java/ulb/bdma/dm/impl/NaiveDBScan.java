package ulb.bdma.dm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ulb.bdma.dm.contract.DBScan;
import ulb.bdma.dm.contract.DistanceMeasurable;
import ulb.bdma.dm.models.ClusterPoint;

/**
 * Implementation of DBScan based on Ester, Martin, Hans-Peter Kriegel, Jörg Sander, and Xiaowei Xu.
 * "A density-based algorithm for discovering clusters in large spatial databases with noise." In
 * Kdd, vol. 96, no. 34, pp. 226-231. 1996.
 *
 * @see <a href="https://www.aaai.org/Papers/KDD/1996/KDD96-037.pdf">A density-based algorithm for
 *     discovering clusters in large spatial databases with noise.</a>
 */
public class NaiveDBScan implements DBScan {
    @Override
    public List<List<DistanceMeasurable>> cluster(
            float epsilon, int minimumPoints, List<DistanceMeasurable> dataPoints) {
        List<List<DistanceMeasurable>> clusters = new ArrayList<>();
        List<ClusterPoint> clusterPoints =
                dataPoints.stream().map(ClusterPoint::new).collect(Collectors.toList());
        for (var clusterPoint : clusterPoints) {
            List<ClusterPoint> neighbours =
                    visitAndGetNeighbours(epsilon, clusterPoints, clusterPoint);
            if (neighbours.size() < minimumPoints) {
                clusterPoint.noise();
            } else {
                List<DistanceMeasurable> newCluster = new ArrayList<>();
                for (var neighbour : neighbours) {
                    if (!neighbour.visited()) {
                        var neighbourOfNeighbours =
                                visitAndGetNeighbours(epsilon, clusterPoints, neighbour);
                    }
                }
            }
        }
        return null;
    }

    private List<ClusterPoint> visitAndGetNeighbours(
            float epsilon, List<ClusterPoint> clusterPoints, ClusterPoint clusterPoint) {
        clusterPoint.visit();
        return clusterPoint.getNearestNeighbours(clusterPoints, epsilon);
    }
}
