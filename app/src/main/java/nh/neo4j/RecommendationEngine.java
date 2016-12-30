package nh.neo4j;

import org.neo4j.driver.v1.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class RecommendationEngine {

  private final Driver driver;

  public RecommendationEngine(Driver driver) {
    this.driver = driver;
  }

  public void connectConsumerWithProducts(String customerId, String productIds) {
    runInSession(session -> {
      try (Transaction tx = session.beginTransaction()) {
        final Consumer<String> save = productId -> {
          System.out.printf("Connecting %s to '%s'%n", customerId, productId);
          Statement statement = new Statement("MERGE (c:Customer { customerId: {customerId} }) MERGE (p:Product {productId: {productId}}) MERGE (c)-[v:VIEWED]->(p) ON CREATE SET v.lv=timestamp() ON MATCH SET v.lv=timestamp() ",
              Values.parameters("customerId", customerId, "productId", productId));

          tx.run(statement);
        };

        Arrays.stream(productIds.split(","))
            .map(String::trim)
            .forEach(save);
        tx.success();
      }
    });
  }

  public List<Recommendation> getRecommendationsFor(final String referenceProductId) {
    final Statement stmt = new Statement("MATCH (p:Product { productId: {referenceProductId} })<-[:VIEWED]-(c:Customer)," +
        " (c)-[:VIEWED]->(product:Product)" +
        " WITH product.productId as Y, count(product) as count" +
        " WHERE count > 1" +
        " RETURN Y, count" +
        " ORDER BY count DESC",
        Values.parameters("referenceProductId", referenceProductId));

    return withSession(session -> session.run(stmt).list(this::recommendationFromRecord));
  }

  private Recommendation recommendationFromRecord(Record record) {
    return new Recommendation(record.get("Y").asString(), record.get("count").asInt());
  }

  public void setupConstraints() {
    runInSession(session -> {
      session.run("CREATE CONSTRAINT ON (c:Customer) ASSERT c.customerId IS UNIQUE");
      session.run("CREATE CONSTRAINT ON (p:Product) ASSERT p.productId IS UNIQUE");
    });
  }

  private void runInSession(Consumer<Session> con) {
    try (Session session = driver.session()) {
      con.accept(session);
    }
  }

  private <R> R withSession(Function<Session, R> fun) {
    try (Session session = driver.session()) {
      return fun.apply(session);
    }
  }
}
