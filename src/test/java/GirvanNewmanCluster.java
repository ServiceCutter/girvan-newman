import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertThat;

import org.gephi.clustering.api.Cluster;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.junit.Test;
import org.openide.util.Lookup;

import cz.cvut.fit.krizeji1.girvan_newman.GirvanNewmanClusterer;

public class GirvanNewmanCluster {

	@Test
	public void testSmallGraph() {
		// Given
		Lookup lookup = Lookup.getDefault();
		ProjectController pc = lookup.lookup(ProjectController.class);
		pc.newProject();
		@SuppressWarnings("unused")
		Workspace workspace = pc.getCurrentWorkspace();
		GraphController controller = Lookup.getDefault().lookup(GraphController.class);
		GraphModel model = controller.getModel();
		UndirectedGraph graph = model.getUndirectedGraph();
		// Add nodes and edges
		Node a = createThreeConnectedNodes(model, graph);
		Node b = createThreeConnectedNodes(model, graph);
		Node c = createThreeConnectedNodes(model, graph);
		Node d = createThreeConnectedNodes(model, graph);
		createEdge(model, graph, a, b, 2f);
		createEdge(model, graph, b, c, 3f);
		createEdge(model, graph, c, d, 3f);
		// When
		GirvanNewmanClusterer clusterer = new GirvanNewmanClusterer();
		clusterer.setPreferredNumberOfClusters(2);
		clusterer.execute(model);
		// Then
		Cluster[] clusters = clusterer.getClusters();
		assertThat(clusters, arrayWithSize(2));
	}

	private void createEdge(GraphModel model, UndirectedGraph graph, Node a, Node b, float weight) {
		Edge edge = model.factory().newEdge(a, b, weight, false);
		graph.addEdge(edge);
	}

	private Node createThreeConnectedNodes(GraphModel model, UndirectedGraph graph) {
		Node a = createNode(model, graph);
		Node b = createNode(model, graph);
		Node c = createNode(model, graph);
		createEdge(model, graph, a, b, 5f);
		createEdge(model, graph, a, c, 5f);
		createEdge(model, graph, b, c, 5f);
		return a;
	}

	private Node createNode(GraphModel model, UndirectedGraph graph) {
		Node node = model.factory().newNode();
		graph.addNode(node);
		return node;
	}

}
