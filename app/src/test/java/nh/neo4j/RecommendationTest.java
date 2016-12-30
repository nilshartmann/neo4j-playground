package nh.neo4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * http://neo4j.com/docs/java-reference/current/
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class RecommendationTest  {
  private Driver driver;

  private RecommendationEngine engine;

  @Rule
  public Neo4jRule neo4j = new Neo4jRule();

  @Before
  public void setUp() throws Exception {
    driver = GraphDatabase.driver( neo4j.boltURI() , Config.build().withEncryptionLevel( Config.EncryptionLevel.NONE ).toConfig() );
    engine = new RecommendationEngine(driver);
    engine.setupConstraints();
  }

  @After
  public void destroyTestDatabase() {
    driver.close();
    engine = null;
  }

  @Test
  public void testMe() throws Exception {

    engine.connectConsumerWithProducts("c1", "p1,p2,p3");
    engine.connectConsumerWithProducts("c2", "p1,p2,p3");
    engine.connectConsumerWithProducts("c3", "p1");

    final List<Recommendation> recommendations = engine.getRecommendationsFor("p1");
    assertThat(recommendations, not(nullValue()));
    assertThat(recommendations, hasSize(2));


//
//    try(
//
//
//    final Transaction transaction = graphDatabase.beginTx();
//
//    main.run("CREATE CONSTRAINT ON (c:Customer) ASSERT c.customerId IS UNIQUE");
//    main.run("CREATE CONSTRAINT ON (p:Product) ASSERT p.productId IS UNIQUE");
  }

  private void run(String stmt) {
    try (Session session = driver.session()) {
      session.run(stmt);
    }
  }
}
