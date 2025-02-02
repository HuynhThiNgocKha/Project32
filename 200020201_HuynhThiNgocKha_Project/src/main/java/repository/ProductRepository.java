package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductRepository {
    private Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Products");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(0, null, 0, null);
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setPrice(rs.getDouble("Price"));
                product.setImage(rs.getString("Image")); 
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findById(int id) {
        Product product = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product = new Product(id, null, id, null);
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setPrice(rs.getDouble("Price"));
                product.setImage(rs.getString("Image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void save(Product product) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO Products (ProductName, CategoryID, Price, Description, Image) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, product.getProductName());
        stmt.setDouble(3, product.getPrice());
        stmt.setString(5, product.getImage()); 
        stmt.executeUpdate();
    }

    public void update(Product product) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
            "UPDATE Products SET ProductName = ?,  Price = ?,  Image = ? WHERE ProductID = ?");
        stmt.setString(1, product.getProductName());
        stmt.setDouble(3, product.getPrice());
        stmt.setString(5, product.getImage()); // Assuming there's an ImagePath field
        stmt.setInt(6, product.getProductId());
        stmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Products WHERE ProductID = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public void updateProductImage(int productId, String imagePath) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE Products SET Image = ? WHERE ProductID = ?");
            stmt.setString(1, imagePath);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	public void deleteProduct(int id) {
		// TODO Auto-generated method stub
		
	}
}
