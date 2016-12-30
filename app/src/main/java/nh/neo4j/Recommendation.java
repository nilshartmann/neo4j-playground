package nh.neo4j;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Recommendation {

  private final String productId;
  private final int count;

  public Recommendation(String productId, int count) {
    this.productId = productId;
    this.count = count;
  }

  public String getProductId() {
    return productId;
  }

  public int getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "Recommendation{" +
        "productId='" + productId + '\'' +
        ", count=" + count +
        '}';
  }
}
