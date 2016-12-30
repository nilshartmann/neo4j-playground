package nh.neo4j;

import com.sun.xml.internal.ws.api.config.management.Reconfigurable;
import org.neo4j.driver.v1.*;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Main {

//
//  public Main deleteAllNodes() {
//    session.run("MATCH (n) DETACH DELETE n");
//    return this;
//  }

//  public StatementResult run(String statement) {
//    return session.run(statement);
//  }

//  public Main connect(String customerId, String productIds) {
//    try (Transaction tx = session.beginTransaction()) {
//      final Consumer<String> save = productId -> {
//          System.out.printf("Connecting %s to '%s'%n", customerId, productId);
//          Statement statement = new Statement("MERGE (c:Customer { customerId: {customerId} }) MERGE (p:Product {productId: {productId}}) MERGE (c)-[v:VIEWED]->(p) ON CREATE SET v.lv=timestamp() ON MATCH SET v.lv=timestamp() ",
//              Values.parameters("customerId", customerId, "productId", productId));
//
//          tx.run(statement);
//        };
//
//      Arrays.stream(productIds.split(","))
//          .map(String::trim)
//          .forEach(save);
//      tx.success();
//    }
//    return this;
//  }

//  public void dispose() {
//    session.close();
//    driver.close();
//  }

  public static void main(String[] args) {

    try (Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "abc"))) {
      RecommendationEngine engine = new RecommendationEngine(driver);
      engine.setupConstraints();

      engine.connectConsumerWithProducts("c1", "p1,p2,p3");
      engine.connectConsumerWithProducts("c2", "p1,p2,p3");
      engine.connectConsumerWithProducts("c3", "p1");

      engine.getRecommendationsFor("p1").forEach(System.out::println);
//
//
//      final StatementResult result = engine.getRecommendationsFor("p1");
//      while (result.hasNext()) {
//        Record record = result.next();
//        System.out.println(record.get("Y").asString() + " " + record.get("count"));
//      }

    }


//    Main main = new Main();
//    main.deleteAllNodes();


//    main.connect("c1", "p1,  p2,p3 ,p4,p5,p6,p7,p8,10    ");
//    main.connect("c2", "p1,p3,p5,p7,p9");
//    main.connect("c3", "p1,p3,p4,p6");
//    main.connect("c4", "p3,p6,p8,p9");
//    main.connect("c5", "p1,p2,p9");
//    main.connect("c6", "p2");
//    main.connect("c7", "p3");

//    main.connect("c1", "p1,p2,p3");
//    main.connect("c2", "p1,p2,p3");
//    main.connect("c3", "p1");


//    String s = "MATCH (p:Product { productId: 'p1' })<-[:VIEWED]-(c:Customer),"+
//    " (c)-[:VIEWED]->(product:Product)"+
//    " WITH product.productId as Y, count(product) as count"+
//    " WHERE count > 1"+
//    " RETURN Y, count"+
//    " ORDER BY count DESC";
//
//    final StatementResult result = main.run(s);
//    while (result.hasNext()) {
//      Record record = result.next();
//      System.out.println(record.get("Y").asString() + " " + record.get("count"));
//    }
//
//
//    main.dispose();

////    session.run("MERGE (c:Customer { customerId: 'c123' }) -[:VIEWED]->(p:Product {productId: 'p567'})");
////    session.run("MERGE (c:Customer { customerId: 'c666' }) -[:VIEWED]->(p:Product {productId: 'p567'})");
//    session.run("MERGE (c:Customer { customerId: 'c123' }) MERGE (p:Product {productId: 'p567'}) MERGE (c)-[:VIEWED]->(p)");
//    session.run("MERGE (c:Customer { customerId: 'c666' }) MERGE (p:Product {productId: 'p567'}) MERGE (c)-[:VIEWED]->(p)");
//    session.run("MERGE (c:Customer { customerId: 'c666' }) MERGE (p:Product {productId: 'p999'}) MERGE (c)-[:VIEWED]->(p)");
//    session.run("MERGE (c:Customer { customerId: 'c123' }) MERGE (p:Product {productId: 'p567'}) MERGE (c)-[v:VIEWED]->(p) ON CREATE SET v.lv=timestamp() ON MATCH SET v.lv=timestamp() ");
//    session.run("MERGE (p:Product {productId: 'p567'})");
//    session.run("MERGE (p:Product {productId: 'p567'})");
//
////    session.run( "CREATE (c:Customer { productId: '123', name:'Arthur', title:'King'})" );
//
//
//    StatementResult result = session.run("MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title");
//    while (result.hasNext()) {
//      Record record = result.next();
//      System.out.println(record.get("title").asString() + " " + record.get("name").asString());
//    }
//
//    session.close();
//    driver.close();
  }

}
